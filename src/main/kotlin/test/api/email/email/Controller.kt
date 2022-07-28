package test.api.email.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.web.bind.annotation.*
import test.api.email.email.Response.SendEmailResponse
import test.api.email.email.services.EmailService

@RestController
/**
 * Anotação que informa ao springboot o caminho que da URL que essa classe vai responder (Ex: https://localhost:8080/api
 */
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
class Controller(
        @Autowired private val emailService: EmailService,
) {


    @CrossOrigin(origins = ["*"])
    @PostMapping("/email-send")
    fun sendMail(
            @RequestParam(value = "email", required = true) destinatario: String,
    ): SendEmailResponse {
        return emailService.sendRecoveryPasswordEmail(destinatario)
    }

    @CrossOrigin(origins = ["*"])
    @PostMapping("/email-validate-send")
    fun sendValidateAccountMail(
            @RequestParam(value = "email", required = true) destinatario: String,
    ): SendEmailResponse {
        return emailService.sendValidateAccountEmail(destinatario)
    }
}

