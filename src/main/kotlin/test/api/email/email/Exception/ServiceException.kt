package test.api.email.email.Exception

import java.lang.Exception
import java.util.*


open class ServiceException : Exception {
    override var message: String = ""
    private var technicalMessage: String = ""

    constructor(message: String?) {
        if (message != null) {
            this.message = message
        }
        if (message != null) {
            technicalMessage = message
        }
    }

    constructor(message: String, technicalMessage: Array<StackTraceElement?>?) {
        this.message = message
        this.technicalMessage = Arrays.toString(technicalMessage)
    }
}