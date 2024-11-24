package com.techeer.notification.infrastructure.slack

import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.views.ViewsOpenRequest
import com.techeer.notification.domain.modal.ModalView
import com.techeer.notification.domain.modal.ModalService
import org.springframework.stereotype.Service


/**
 * @see <a href="https://api.slack.com/methods/views.open"> views.open API </a>
 */
@Service
class SlackModalService(
    private val methodsClient: MethodsClient
) : ModalService {

    override fun openModalView(
        modalView: ModalView
    ) {
        val slackScheduleModalTemplate = SlackModalHelper.findModalTemplate(
            modalView.modalType
        )

        val viewsOpenRequest = ViewsOpenRequest.builder()
            .triggerId(modalView.triggerId)
            .view(slackScheduleModalTemplate)
            .build()

        val viewsOpenResponse = methodsClient.viewsOpen(viewsOpenRequest)

        if (!viewsOpenResponse.isOk) {
            throw IllegalStateException("Slack Modal Open Failed. ${viewsOpenResponse.error}")
        }
    }
}
