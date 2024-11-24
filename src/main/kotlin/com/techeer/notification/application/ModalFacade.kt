package com.techeer.notification.application

import com.techeer.notification.application.dto.ModalCommand
import com.techeer.notification.domain.modal.ModalService
import com.techeer.notification.domain.modal.ModalType
import com.techeer.notification.domain.notification.Notification
import com.techeer.notification.domain.notification.NotificationService
import org.springframework.stereotype.Component

@Component
class ModalFacade(
    private val modelService: ModalService,
    private val notificationService: NotificationService
) {

    fun openModal(openModalCommand: ModalCommand.OpenModalCommand) {
        // command Text를 통한 ModalType 탐색
        val modalType = ModalType.findBy(openModalCommand.command)

        modelService.openModalView(
            ModalCommand.of(
                openModalCommand = openModalCommand,
                modalType = modalType
            )
        )
    }

    fun submit(
        submitModalCommand: ModalCommand.SubmitModalCommand
    ) {
        // TODO : 전달받은 데이터를 기반으로, 데이터 처리를 진행한다.

        // TODO : 데이터 처리가 완료될 경우, UserID를 기반으로 메시지를 생성한다.

        // TODO : Notification Service를 통해 메시지를 전송한다.
        notificationService.sendNotification(
            Notification.from(
                submitModalCommand = submitModalCommand
            )
        )
    }
}
