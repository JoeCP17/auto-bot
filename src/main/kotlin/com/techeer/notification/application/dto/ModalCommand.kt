package com.techeer.notification.application.dto

import com.techeer.notification.domain.modal.ModalPlatform
import com.techeer.notification.domain.modal.ModalType
import com.techeer.notification.domain.modal.ModalView
import com.techeer.notification.interfaces.dto.ModalRequest
import org.springframework.util.StringUtils

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
    }
}
