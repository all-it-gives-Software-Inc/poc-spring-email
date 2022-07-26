package test.api.email.email.rest

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import test.api.email.email.entity.AccessUser
import test.api.email.email.entity.AppCodigoAcesso
import test.api.email.email.rest.requests.AccessCodePostRequestBody
import test.api.email.email.rest.response.ResponseBodyGenerateAccessCode
import java.util.*

@FeignClient(name = "AUTH", url = "https://fast-lake-01538.herokuapp.com/auth/")
interface AuthRest {

    @RequestMapping("user?email={email}")
    fun getUserByEmail(@PathVariable("email") email: String): Optional<AccessUser>

    @RequestMapping("generateAccessCode")
    fun generateAccessCode(@RequestBody request: AccessCodePostRequestBody): Optional<ResponseBodyGenerateAccessCode>
}
