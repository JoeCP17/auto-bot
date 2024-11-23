package com.techeer.notification.infrastructure.slack.config

import com.slack.api.Slack
import com.slack.api.methods.MethodsClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class SlackConfig(
    @Value("\${slack.notification.bot-token}")
    private val token: String
) {
    @Bean
    fun methodsClient(): MethodsClient {
        val slack = Slack.getInstance()
        return slack.methods(token)
    }
}
