package test.api.email.email

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.netflix.eureka.EnableEurekaClient
import org.springframework.cloud.openfeign.EnableFeignClients

@EnableFeignClients
@SpringBootApplication
@EnableEurekaClient
class EmailApplication

fun main(args: Array<String>) {
	runApplication<EmailApplication>(*args)

}
