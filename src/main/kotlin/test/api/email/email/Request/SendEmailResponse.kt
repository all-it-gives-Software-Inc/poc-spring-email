package test.api.email.email.Response;

import java.io.Serializable

class SendEmailRequest(
        email: String,

        ) : Serializable {

    var email: String


    init {
        this.email = email
    }
}