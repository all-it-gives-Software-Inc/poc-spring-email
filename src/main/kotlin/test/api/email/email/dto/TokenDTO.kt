package test.api.email.email.dto

import test.api.email.email.Enum.StatusEnum
import java.io.Serializable

class TokenDTO(
        acessoId: Long?,
        login: String?,
        status: Long?,
        token: String?,
        nomeUsuario: String?,
        nomeCliente: String?,
        roles: Array<String?>?,
        dominioId: Long?
): Serializable {
    var status: StatusEnum?
    var token: String?
    var nomeUsuario: String?
    var nomeCliente: String?
    var roles: Array<String?>?
    var dominioId: Long?

    init {
        this.status = StatusEnum.parse(status)
        this.token = token
        this.nomeUsuario = nomeUsuario
        this.nomeCliente = nomeCliente
        this.roles = roles
        this.dominioId = dominioId
    }
}