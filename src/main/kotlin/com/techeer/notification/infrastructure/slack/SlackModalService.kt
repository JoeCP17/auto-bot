package com.techeer.notification.infrastructure.slack

import com.slack.api.methods.MethodsClient
import com.slack.api.methods.request.views.ViewsOpenRequest
import com.techeer.notification.domain.modal.ModalView
import com.techeer.notification.domain.modal.ModalService
import org.springframework.stereotype.Service


/**
 * Slack ViewsOpen
 * @see <a href="https://api.slack.com/methods/views.open"> views.open API </a>
 * @see <a href="https://api.slack.com/interactivity/slash-commands#creating_commands"> Slack Slash Command </a>
 *
 */
@Service
class SlackModalService(
    private val methodsClient: MethodsClient
) : ModalService {

    override fun openModalView(
        modalView: ModalView
    ) {
        val slackScheduleModalTemplate = SlackModalHelper.slackScheduleModalTemplate()

        val viewsOpenRequest = ViewsOpenRequest.builder()
            .triggerId(modalView.triggerId)
            .view(slackScheduleModalTemplate)
            .build()

        methodsClient.viewsOpen(viewsOpenRequest)
    }

}
