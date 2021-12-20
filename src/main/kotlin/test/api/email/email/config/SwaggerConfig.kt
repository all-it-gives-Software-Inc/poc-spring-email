package br.com.ideaz.ezatto.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import springfox.documentation.builders.ApiInfoBuilder
import springfox.documentation.builders.PathSelectors
import springfox.documentation.builders.RequestHandlerSelectors
import springfox.documentation.service.Contact
import springfox.documentation.spi.DocumentationType
import springfox.documentation.spring.web.plugins.Docket
import springfox.documentation.swagger2.annotations.EnableSwagger2

@Configuration
@EnableSwagger2
class SwaggerConfig {

    @Bean
    fun api(): Docket{
        var docket = Docket(DocumentationType.SWAGGER_2)
        docket
            .select()
            .apis(RequestHandlerSelectors.basePackage("br.com.Email.Email.rest"))
            .paths(PathSelectors.any())
            .build()
            .apiInfo(this.informacoesApi().build())

        return docket
    }

    fun informacoesApi() : ApiInfoBuilder {
        var apiInfoBuilder = ApiInfoBuilder()
        apiInfoBuilder.title("Email")
        apiInfoBuilder.description("API Email")
        apiInfoBuilder.version("1.0")
        apiInfoBuilder.termsOfServiceUrl("Sistema de Email.")
        apiInfoBuilder.contact(this.contato())

        return apiInfoBuilder

    }

    private fun contato(): Contact {
        return Contact(
            "Email",
            "",
            "Email@Email.com.br")
    }
}
