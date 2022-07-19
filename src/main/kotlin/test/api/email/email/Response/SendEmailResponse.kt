package test.api.email.email.Response;

import java.io.Serializable

class SendEmailResponse(
        informacao: String,

        ) : Serializable {

    var informacao: String


    init {
        this.informacao = informacao
    }
}