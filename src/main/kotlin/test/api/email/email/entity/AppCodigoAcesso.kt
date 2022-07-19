package test.api.email.email.entity

import java.time.LocalDateTime
import javax.persistence.Column
import javax.persistence.Entity
import javax.persistence.Id
import javax.persistence.Table

@Entity
@Table(name = "APP_CODIGO_ACESSO")
class AppCodigoAcesso(

        @Column(name = "CODIGO") var codigo: String? = null,

        @Column(name = "VALIDADE") var validade: LocalDateTime? = null,

        @Column(name = "DT_CADASTRO") var dtCadastro: LocalDateTime? = null,

        @Id
        @Column(unique = true, name = "EMAIL") var email: String? = null,
)