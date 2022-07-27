package test.api.email.email.rest.response

import lombok.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
class ResponseBodyAccessUser {

    val id: Long? = null

    val username: @NotEmpty(message = "The user name cannot be empty") @NotNull String? = null

    val email: @NotEmpty(message = "The user email cannot be empty") @NotNull String? = null

    val role: @NotEmpty(message = "The role cannot be empty") @NotNull String? = null
}