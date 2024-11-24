package com.techeer.notification.infrastructure.slack

import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.chat.ChatPostMessageRequest
import com.techeer.notification.domain.notification.Notification
import com.techeer.notification.domain.notification.NotificationService
import org.springframework.stereotype.Service

@Service
class SlackNotificationService(
    private val methodsClient: MethodsClient
) : NotificationService{

    override fun sendNotification(
        notification:Notification
    ) {
        // DM 채널 조회
        val userDirectChannelId = openDirectMessageChannel(notification.userId)

        val chatMessageRequest = ChatPostMessageRequest.builder()
            .channel(userDirectChannelId)
            .text(notification.message)
            .build()

        val chatMessageResponse = methodsClient.chatPostMessage(chatMessageRequest)

        if (!chatMessageResponse.isOk) {
            throw IllegalStateException("Slack Message Send Failed. : ${chatMessageResponse.error}")
        }
    }


    private fun openDirectMessageChannel(
        userId: String
    ): String {
        val conversationsOpenResponse = methodsClient.conversationsOpen { req ->
            req.users(listOf(userId))
        }

        if (!conversationsOpenResponse.isOk
            || conversationsOpenResponse.channel?.id == null
            ) {
            throw IllegalStateException("Failed to open DM channel: ${conversationsOpenResponse.error}")
        }

        return conversationsOpenResponse.channel.id
    }
}
