package test.api.email.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.MediaType
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
/**
 * Anotação que informa ao springboot o caminho que da URL que essa classe vai responder (Ex: https://localhost:8080/api
 */
@RequestMapping("/api", produces = [MediaType.APPLICATION_JSON_VALUE])
class Controller(
    @Autowired private val mailSender: JavaMailSender,
) {


    @GetMapping("/email-send")
    fun sendMail(): String {
        val message = SimpleMailMessage()
        message.setText("Hello from Spring Boot Application")
        message.setTo("joao4018@gmail.com")
        message.setFrom("joaodev4018@gmail.com")
        return try {
            mailSender.send(message)
            "Email enviado com sucesso!"
        } catch (e: Exception) {
            e.printStackTrace()
            "Erro ao enviar email."
        }
    }
}

