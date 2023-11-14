package com.ddn.goorm.api.team

import com.ddn.goorm.admin.annotation.AuthAccount
import com.ddn.goorm.api.account.dto.response.TokenRes
import com.ddn.goorm.api.member.MemberApiService
import com.ddn.goorm.api.team.dto.request.TeamCreateReq
import com.ddn.goorm.api.team.dto.request.TeamJoinReq
import com.ddn.goorm.api.team.dto.response.TeamJoinRes
import com.ddn.goorm.api.team.dto.response.TeamRes
import com.ddn.goorm.common.enums.Role
import com.ddn.goorm.common.response.ResponseCode
import com.ddn.goorm.common.response.SuccessResponse
import com.ddn.goorm.domains.account.Account
import com.ddn.goorm.domains.group.team.Team
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/teams")
class TeamController (
    private val teamApiService: TeamApiService,
    private val memberApiService: MemberApiService
) {
    @PostMapping("/join")
    fun teamAuthenticationCreate (
        @AuthAccount account: Account,
        @RequestBody req: TeamJoinReq
    ) : ResponseEntity<TeamJoinRes> {
        return ResponseEntity (
            teamApiService.createTeamAuthenticateToken(account, req.team),
            HttpStatus.CREATED
        )
    }

    @GetMapping
    fun teamListFindByAccount (
        @AuthAccount account: Account
    ) : ResponseEntity<List<TeamRes>> {
        return ResponseEntity.ok(teamApiService.findTeamList(account))
    }

    @PostMapping
    fun teamCreate (
        @AuthAccount account: Account,
        @RequestBody req: TeamCreateReq
    ) : ResponseEntity<SuccessResponse> {
        val team: Team = teamApiService.createTeam(account, req)
        memberApiService.createMember(account, team, Role.ROLE_LEADER)
        return ResponseEntity (
            SuccessResponse(ResponseCode.CREATED.code, ResponseCode.CREATED.status, "팀이 정상적으로 생성되었습니다."),
            HttpStatus.CREATED
        )
    }

    /*
    @PostMapping("/invite")
    fun teamMemberCreateByAccount (

    ): Response

     */
}