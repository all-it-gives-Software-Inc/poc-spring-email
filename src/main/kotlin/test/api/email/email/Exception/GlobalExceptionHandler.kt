package test.api.email.email.Exception


import org.apache.commons.lang3.exception.ExceptionUtils
import org.slf4j.LoggerFactory
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.http.converter.HttpMessageNotReadableException
import org.springframework.validation.BindingResult
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import test.api.email.email.MessageUtil
import java.util.ArrayList

@ControllerAdvice
class GlobalExceptionHandler : ResponseEntityExceptionHandler() {
    protected override fun handleHttpMessageNotReadable(
            ex: HttpMessageNotReadableException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        LOGGERS.error(ex.message, ex)
        val userMessage: String = MessageUtil.getMessage(API_INVALID_REQUEST)
        val technicalMessage: String = if (ex.cause != null) ex.cause.toString() else ex.toString()
        val errors = listOf(Error(userMessage, technicalMessage))
        val apiError = ApiError(HttpStatus.BAD_REQUEST, userMessage, errors)
        return ResponseEntity<Any>(apiError, HttpHeaders(), apiError.httpStatus)
    }

    protected override fun handleMethodArgumentNotValid(
            ex: MethodArgumentNotValidException,
            headers: HttpHeaders,
            status: HttpStatus,
            request: WebRequest
    ): ResponseEntity<Any> {
        LOGGERS.error(ex.message, ex)
        val errors: List<Error> = createErrorList(ex.getBindingResult())
        val apiError = ApiError(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(), errors)
        return ResponseEntity<Any>(apiError, HttpHeaders(), apiError.httpStatus)
    }


    @ExceptionHandler(ServiceException::class)
    private fun serviceException(ex: ServiceException): ResponseEntity<Any> {
        val httpStatus: HttpStatus = HttpStatus.BAD_REQUEST
        LOGGERS.error(ex.message, ex)
        val userMessage: String = ex.message
        val technicalMessage = ExceptionUtils.getRootCauseMessage(ex) + " / " + ExceptionUtils.getStackTrace(ex)
        val errors = listOf(Error(userMessage, technicalMessage))
        val apiError = ApiError(httpStatus, userMessage, errors)
        return ResponseEntity<Any>(apiError, HttpHeaders(), apiError.httpStatus)
    }


    @ExceptionHandler(Throwable::class)
    private fun genericException(ex: Throwable): ResponseEntity<Any> {
        LOGGERS.error(ex.message, ex)
        val userMessage: String = MessageUtil.getMessage("api.error.generic")
        val technicalMessage = ExceptionUtils.getRootCauseMessage(ex) + " / " + ExceptionUtils.getStackTrace(ex)
        val errors = listOf(Error(userMessage, technicalMessage))
        val apiError = ApiError(HttpStatus.INTERNAL_SERVER_ERROR, userMessage, errors)
        return ResponseEntity<Any>(apiError, HttpHeaders(), apiError.httpStatus)
    }

    private fun createErrorList(bindingResult: BindingResult): List<Error> {
        val errors: MutableList<Error> = ArrayList()
        for (fieldError in bindingResult.getFieldErrors()) {
            val userMessage: String = fieldError.getField() + ": " + fieldError.getDefaultMessage()
            val technicalMessage: String = fieldError.toString()
            errors.add(Error(userMessage, technicalMessage))
        }
        return errors
    }


    class Error(private val userMessage: String, private val technicalMessage: String)
    companion object {
        private val LOGGERS = LoggerFactory.getLogger(GlobalExceptionHandler::class.java)
        private const val API_INVALID_REQUEST = "api.invalid.request"
    }
}