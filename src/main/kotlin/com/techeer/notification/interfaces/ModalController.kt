package com.techeer.notification.interfaces

import com.techeer.notification.application.ModalFacade
import com.techeer.notification.application.dto.ModalCommand
import com.techeer.notification.interfaces.dto.ModalRequest
import org.springframework.http.MediaType
import org.springframework.util.MultiValueMap
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1")
class ModalController(
    private val modalFacade: ModalFacade
) {

    /**
     * 슬랙 내, Modal을 열어준다.
     * Slack의 Slash Command로 요청을 보낼 경우 triggerId를 받아서 해당 유저에게 Modal을 열어준다.
     *
     * @see <a href="https://api.slack.com/interactivity/slash-commands#creating_commands"> Slack Slash Command Docs</a>
     */
    @PostMapping(
        "/modal",
        consumes = [MediaType.APPLICATION_FORM_URLENCODED_VALUE],
        produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun openModal(
        @RequestBody openModalRequestData: MultiValueMap<String, Any>
    ) {
        val openModalRequest = ModalRequest.OpenModalRequest.from(openModalRequestData)

         modalFacade.openModal(
            ModalCommand.fromOpenModalCommand(
                modalRequest = openModalRequest
            )
        )
    }

    /**
     * 슬랙 내, Modal에서 제출을 누를 경우 Modal 내 입력받은 데이터를 기반으로 처리를 진행한다.
     * @see <a href="https://api.slack.com/interactivity"> Slack Interactivity Docs</a>
     */
    @PostMapping("/submit")
    fun submitModal(
        @RequestBody submitModalRequestData: MultiValueMap<String, Any>
    ) {
        val submitModalRequest = ModalRequest.SubmitModalRequest.from(submitModalRequestData)

        modalFacade.submit(
            ModalCommand.fromSubmitModalCommand(
                modalSubmitRequest = submitModalRequest
            )
        )
    }
}
