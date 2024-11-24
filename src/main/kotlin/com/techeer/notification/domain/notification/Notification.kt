package com.techeer.notification.domain.notification

import com.techeer.notification.application.dto.ModalCommand
import org.springframework.util.StringUtils
import java.time.format.DateTimeFormatter

data class Notification(
    val userId: String,
    val triggerId: String,
    val message: String
) {
    companion object {
        private const val SCHEDULE_NOTIFICATION_TEMPLATE = "<@%s>님이 예약을 신청했습니다. 약속일자는 `%s` 시간은 `%s` 입니다."

        fun from(
            submitModalCommand: ModalCommand.SubmitModalCommand
        ): Notification {
            // 날짜와 시간 분리
            val dateTime = submitModalCommand.selectedDateTime
            val date = dateTime!!.toLocalDate().toString() // 날짜 부분만 추출
            val time = dateTime.toLocalTime().format(DateTimeFormatter.ofPattern("HH:mm"))

            // 메시지 생성
            var message = String.format(
                SCHEDULE_NOTIFICATION_TEMPLATE,
                submitModalCommand.requestUserId,
                date,
                time
            )

            // 사유가 존재할 경우 메시지에 추가
            if (StringUtils.hasText(submitModalCommand.reason)) {
                message += "\n```${submitModalCommand.reason}```"
            }

            // Notification 객체 반환
            return Notification(
                userId = submitModalCommand.selectedUserId,
                triggerId = submitModalCommand.triggerId,
                message = message
            )
        }
    }
}
