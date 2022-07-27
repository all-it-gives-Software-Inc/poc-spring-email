package test.api.email.email.rest.requests

import io.swagger.v3.oas.annotations.media.Schema
import lombok.Data

@Data
class AccessCodePostRequestBody() {

    @Schema(description = "This is the email of the access code", example = "fulano@mail.com")
    var email: String? = null

    constructor(email: String) : this() {
        this.email = email;
    }
}