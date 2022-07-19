package test.api.email.email.services

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.mail.SimpleMailMessage
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.stereotype.Service
import test.api.email.email.Exception.ServiceException
import test.api.email.email.Response.SendEmailResponse
import test.api.email.email.dto.TokenDTO
import test.api.email.email.entity.AppCodigoAcesso
import test.api.email.email.form.TokenForm
import test.api.email.email.repository.AccessUserRepository
import test.api.email.email.repository.AppCodigoAcessoRepository
import java.time.LocalDateTime
import java.util.*
import kotlin.random.Random
@Service
class EmailService(
        @Autowired private val mailSender: JavaMailSender,
        @Autowired private val accessUserRepository: AccessUserRepository,
        @Autowired
        private val  appCodigoAcessoRepository: AppCodigoAcessoRepository,
) {

    @Value("\${spring.mail.username}")
    lateinit var email: String

    fun sendRecoveryPasswordEmail(destinatario: String): SendEmailResponse {

        val randomString = createRandomCode()
        accessUserRepository.findAppUsuarioByEmail(destinatario).orElseThrow { ServiceException("Usuário $destinatario não encontrado! Contatar Suporte para mais informações") }
        val message = provideEmail(destinatario,"Seu código para recuperação de senha: $randomString")
        val now = LocalDateTime.now()

        appCodigoAcessoRepository.save(AppCodigoAcesso(randomString, now.plusMinutes(5L), now, destinatario))
        return try {
            mailSender.send(message)
            SendEmailResponse("Email enviado com sucesso!")
        } catch (e: Exception) {
            e.printStackTrace()
            return SendEmailResponse("Erro ao enviar email.")
        }
    }

    private fun createRandomCode(): String {
        val charPool: List<Char> = ('a'..'z') + ('A'..'Z') + ('0'..'9')

        val randomString = (1..6).map { i -> Random.nextInt(0, charPool.size) }.map(charPool::get).joinToString("");
        return randomString
    }

//    fun validationAccessCode(code: String, email: String): TokenDTO {
//
//        val usuario = accessUserRepository.findAppUsuarioByEmail(email).orElseThrow { ServiceException("Usuário $email não encontrado! Contatar Suporte para mais informações") }
//        val findByCodigoAndEmail = appCodigoAcessoRepository.findByCodigoAndEmailAndValidadeAfter(code, email, LocalDateTime.now())
//
//        if (findByCodigoAndEmail.isPresent) {
//            val tokenForm = TokenForm(usuario.personalData?.cpfCpnj, findByLogin.senha)
//            val message = provideEmail(email, "Sua solicitação de recuperação e alteração de senha foi executada com sucesso!")
//            try {
//                mailSender.send(message)
//                return accessoService.loginByCode(tokenForm);
//            }catch (e: Exception) {
//                throw ServiceException("Código expirado!")
//            }
//
//        } else {
//            throw ServiceException("Usuario nao encontrado!")
//        }
//
//    }

    private fun provideEmail(destinatario: String, description: String): SimpleMailMessage {
        val message = SimpleMailMessage()
        message.setText(description)
        message.setSubject("NoReply")
        message.setTo(destinatario)
        message.setFrom(email)
        return message
    }
}