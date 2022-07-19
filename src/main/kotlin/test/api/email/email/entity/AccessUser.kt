package test.api.email.email.entity

import lombok.*
import javax.persistence.*
import javax.validation.constraints.NotEmpty
import javax.validation.constraints.NotNull

@Entity(name = "API_ACCESS")
@Data
@Builder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString
class AccessUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null

    @OneToOne
    val personalData: PersonalData? = null

    @Column(unique = true)
    val userName: @NotEmpty(message = "The user name cannot be empty") @NotNull String? = null

    @Column(unique = true)
    val email: @NotEmpty(message = "The user email cannot be empty") @NotNull String? = null

    val role: @NotEmpty(message = "The role cannot be empty") @NotNull String? = null
}
