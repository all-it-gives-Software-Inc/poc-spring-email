package test.api.email.email.Enum

import java.lang.AssertionError

enum class ZoneEnum(val value: String) {
    AMERICA_SAO_PAULO("America/Sao_Paulo"), AMERICA_SAO_PAULO_SHORT("BET");

    companion object {
        fun parse(value: String?): ZoneEnum? {
            if (value == null) {
                return null
            }
            for (e in values()) {
                if (e.value.equals(value, ignoreCase = true)) {
                    return e
                }
            }
            throw AssertionError(value)
        }
    }
}