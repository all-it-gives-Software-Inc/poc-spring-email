package test.api.email.email.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import test.api.email.email.Exception.ServiceException
import test.api.email.email.Response.SendEmailResponse
import test.api.email.email.rest.AuthRest
import test.api.email.email.rest.requests.AccessCodePostRequestBody
import test.api.email.email.rest.response.ResponseBodyGenerateAccessCode


@Service
class EmailService(
        @Autowired private val mailSender: JavaMailSender,
        @Autowired private val authRest: AuthRest
) {

    @Value("\${spring.mail.username}")
    lateinit var email: String

    fun sendRecoveryPasswordEmail(destinatario: String): SendEmailResponse {

        authRest.getUserByEmail(destinatario).orElseThrow { ServiceException("Usuário $destinatario não encontrado! Contatar Suporte para mais informações") }
        val codigoAcesso : ResponseBodyGenerateAccessCode = authRest
                .generateAccessCode(AccessCodePostRequestBody(destinatario))
                .orElseThrow { ServiceException("Usuário $destinatario não encontrado! Contatar Suporte para mais informações") }
        val message = provideEmail(destinatario,"Seu código para recuperação de senha: ${codigoAcesso.code}")

        return try {
            mailSender.send(message)
            SendEmailResponse("Email enviado com sucesso!")
        } catch (e: Exception) {
            e.printStackTrace()
            return SendEmailResponse("Erro ao enviar email.")
        }
    }

    fun sendValidateAccountEmail(destinatario: String, conteudo: String): SendEmailResponse {
        return try {
//            authRest.getUserByEmail(destinatario).orElseThrow { ServiceException("Usuário $destinatario não encontrado! Contatar Suporte para mais informações") }
            val message = provideEmail(destinatario,conteudo)
            mailSender.send(message)
            SendEmailResponse("Email enviado com sucesso!")
        } catch (e: Exception) {
            e.printStackTrace()
            SendEmailResponse("Erro ao enviar email.")
        }
    }

    private fun provideEmail(destinatario: String, description: String): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setText(description)
        message.setSubject("NoReply")
        message.setTo(destinatario)
        message.setFrom("joaodeb4018@gmail.com")
        return message
    }

}

