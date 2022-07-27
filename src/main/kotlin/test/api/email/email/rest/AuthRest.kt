package test.api.email.email.rest

import org.springframework.cloud.openfeign.FeignClient
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import test.api.email.email.entity.AccessUser
import java.util.*

@FeignClient(name = "GATEWAY", url = "https://GATEWAY")
interface AuthRest {

    @RequestMapping("auth/user?email={email}")
    fun getUserByEmail(@PathVariable("email") email: String): Optional<AccessUser>
}
