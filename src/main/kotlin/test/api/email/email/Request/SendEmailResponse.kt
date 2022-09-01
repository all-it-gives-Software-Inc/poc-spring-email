package test.api.email.email.Response;

import java.io.Serializable

class SendEmailRequest(
        email: String,
        conteudo: String

        ) : Serializable {

    var email: String
    var conteudo: String


    init {
        this.email = email
        this.conteudo = conteudo
    }
}