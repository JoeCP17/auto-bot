package com.techeer.notification.domain.notification

interface NotificationService {
    fun sendNotification(notification: Notification)
}
