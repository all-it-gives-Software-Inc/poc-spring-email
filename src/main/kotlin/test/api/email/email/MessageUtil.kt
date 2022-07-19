package test.api.email.email

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.MessageSource
import org.springframework.context.i18n.LocaleContextHolder
import org.springframework.stereotype.Component
import javax.annotation.PostConstruct

@Component
class MessageUtil {
    @Autowired
    private val messageSource: MessageSource? = null
    @PostConstruct
    private fun initStaticMessageSource() {
        staticMessageSource = messageSource
    }

    companion object {
        private var staticMessageSource: MessageSource? = null
        fun getMessage(messageKey: String?): String {
            return staticMessageSource!!.getMessage(messageKey!!, null, LocaleContextHolder.getLocale())
        }
    }
}