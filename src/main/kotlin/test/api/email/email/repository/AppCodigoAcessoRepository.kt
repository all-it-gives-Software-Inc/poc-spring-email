package test.api.email.email.repository;

import org.springframework.data.jpa.repository.JpaRepository
import test.api.email.email.entity.AppCodigoAcesso
import java.time.LocalDateTime
import java.util.*

interface AppCodigoAcessoRepository : JpaRepository<AppCodigoAcesso, Long> {
    fun findByCodigoAndEmailAndValidadeAfter(codigo: String, email: String, date: LocalDateTime): Optional<AppCodigoAcesso>


//    @Modifying
//    @Query(value = "update AccessUser u set u.firstname = ?1 where u.id = ?2", nativeQuery = true)
//    Optional<AccessUser> updateUser(Long personalData, Long userId);
}
