package com.techeer.notification.infrastructure.slack

import com.slack.api.methods.MethodsClient
import com.techeer.notification.domain.notification.NotificationService
import org.springframework.stereotype.Service

@Service
class SlackNotificationService(
    private val methodsClient: MethodsClient
) : NotificationService{

    override fun sendNotification() {
        TODO("Not yet implemented")
    }
}
