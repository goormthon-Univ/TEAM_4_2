package com.ddn.goorm.domains.group.member

import com.ddn.goorm.domains.account.Account
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findMemberByAccountAndTeam_Id(account: Account, team: Long): Optional<Member>

    fun findAllByAccount(account: Account): List<Member>

}