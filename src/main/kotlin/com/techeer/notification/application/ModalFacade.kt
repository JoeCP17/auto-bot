package com.techeer.notification.application

import com.techeer.notification.application.dto.ModalCommand
import com.techeer.notification.domain.modal.ModalService
import com.techeer.notification.domain.modal.ModalType
import org.springframework.stereotype.Component

@Component
class ModalFacade(
    private val modelService: ModalService
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
}
