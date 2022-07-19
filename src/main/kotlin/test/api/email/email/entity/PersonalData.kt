package test.api.email.email.entity

import lombok.*
import java.util.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "API_PERSONAL_DATA")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
class PersonalData {
    @Id
    val id: Long? = null
    @Column(unique = true)
    val cpfCpnj: @NotEmpty(message = "The user firstName cannot be empty") @NotNull String? = null
    val firstName: @NotEmpty(message = "The user firstName cannot be empty") @NotNull String? = null
    val lastName: @NotEmpty(message = "The lastName cannot be empty") @NotNull String? = null
    val bithDate: @NotNull Date? = null
}