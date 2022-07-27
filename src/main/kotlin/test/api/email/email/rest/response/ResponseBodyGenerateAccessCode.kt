package test.api.email.email.rest.response

import lombok.AllArgsConstructor
import lombok.Data
import lombok.NoArgsConstructor

@AllArgsConstructor
@NoArgsConstructor
@Data
class ResponseBodyGenerateAccessCode {
    val code: String? = null
}