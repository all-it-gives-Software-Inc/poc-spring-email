package test.api.email.email.form

class TokenForm(
        login: String?,
        password: String?
) {
    var login: String?
    var password: String?

    init {
        this.login = login
        this.password = password
    }
}