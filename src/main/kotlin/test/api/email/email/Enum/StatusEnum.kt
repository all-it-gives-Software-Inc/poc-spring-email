package test.api.email.email.Enum

import java.lang.AssertionError

enum class StatusEnum(val value: Long) {
    INATIVO(2L), ATIVO(1L), PENDENTE(3L);

    companion object {
        fun parse(value: Long?): StatusEnum? {
            if (value == null) {
                return null
            }
            for (e in values()) {
                if (e.value === value) {
                    return e
                }
            }
            throw AssertionError(value)
        }
    }
}