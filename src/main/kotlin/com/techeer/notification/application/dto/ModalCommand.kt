package com.techeer.notification.application.dto

import com.techeer.notification.domain.modal.ModalPlatform
import com.techeer.notification.domain.modal.ModalType
import com.techeer.notification.domain.modal.ModalView
import com.techeer.notification.interfaces.dto.ModalRequest
import org.springframework.util.StringUtils
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter

class ModalCommand {

    data class OpenModalCommand(
        val triggerId: String,
        val userId: String,
        val channelId: String,
        val command: String,
    ) {
        init {
            require(StringUtils.hasText(triggerId)) { "'triggerId' must not be null, empty" }
            require(StringUtils.hasText(userId)) { "'userId' must not be null, empty" }
            require(StringUtils.hasText(channelId)) { "'channelId' must not be null, empty" }
            require(StringUtils.hasText(command)) { "'command' must not be null, empty" }
        }
    }

    data class SubmitModalCommand(
        val triggerId: String,
        val requestUserId: String,
        val selectedUserId: String,
        val targetUserId: String,
        val selectedDateTime: LocalDateTime?,
        val reason: String?
    ) {
        init {
            require(StringUtils.hasText(triggerId)) { "'triggerId' must not be null, empty" }
            require(StringUtils.hasText(requestUserId)) { "'requestUserId' must not be null, empty" }
            require(StringUtils.hasText(selectedUserId)) { "'selectedUserId' must not be null, empty" }
            require(StringUtils.hasText(targetUserId)) { "'targetUserId' must not be null, empty" }
        }
    }


    companion object {
        fun fromOpenModalCommand(
            modalRequest: ModalRequest.OpenModalRequest,
        ): OpenModalCommand {
            return OpenModalCommand(
                triggerId = modalRequest.triggerId,
                userId = modalRequest.userId,
                channelId = modalRequest.channelId,
                command = modalRequest.command
            )
        }

        fun fromSubmitModalCommand(
            modalSubmitRequest: ModalRequest.SubmitModalRequest,
        ): SubmitModalCommand {
            return SubmitModalCommand(
                triggerId = modalSubmitRequest.triggerId,
                requestUserId = modalSubmitRequest.requestUserId,
                selectedUserId = modalSubmitRequest.selectedUserId,
                targetUserId = modalSubmitRequest.selectedUserId,
                // TODO : Extension Function으로 분리
                selectedDateTime = parse(
                    LocalDate.parse(modalSubmitRequest.selectedDate!!),
                    modalSubmitRequest.selectedTime!!
                ),
                reason = modalSubmitRequest.reason
            )
        }


        fun of(
            openModalCommand: OpenModalCommand,
            modalPlatform: ModalPlatform = ModalPlatform.SLACK,
            modalType: ModalType
        ): ModalView {
            return ModalView(
                triggerId = openModalCommand.triggerId,
                modalPlatform = modalPlatform,
                modalType = modalType
            )
        }

        private fun parse(date: LocalDate, time: String): LocalDateTime {
            val formatter = DateTimeFormatter.ofPattern("H:mm") // H:mm은 1자리 또는 2자리 시간 지원
            return LocalDateTime.of(date, LocalTime.parse(time, formatter))
        }
    }
}
