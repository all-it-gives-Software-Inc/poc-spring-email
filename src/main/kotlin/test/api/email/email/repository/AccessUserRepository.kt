package test.api.email.email.repository

import org.springframework.data.jpa.repository.JpaRepository
import test.api.email.email.entity.AccessUser
import java.util.*

interface AccessUserRepository : JpaRepository<AccessUser?, Long?> {
    fun findAppUsuarioByEmail(email: String): Optional<AccessUser>

}
