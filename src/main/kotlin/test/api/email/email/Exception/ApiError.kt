package test.api.email.email.Exception

import com.fasterxml.jackson.annotation.JsonFormat
import org.springframework.http.HttpStatus
import test.api.email.email.Enum.ZoneEnum
import java.time.LocalDateTime
import java.time.ZoneId

class ApiError(httpStatus: HttpStatus, message: String, errors: List<GlobalExceptionHandler.Error>) {
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd hh:mm:ss")
    val dateTime: LocalDateTime
    val httpStatus: HttpStatus
    val message: String
    val errors: List<GlobalExceptionHandler.Error>

    init {
        dateTime = LocalDateTime.now(ZoneId.of(ZoneEnum.AMERICA_SAO_PAULO.value))
        this.httpStatus = httpStatus
        this.message = message
        this.errors = errors
    }
}