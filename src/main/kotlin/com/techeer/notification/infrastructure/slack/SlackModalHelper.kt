package com.techeer.notification.infrastructure.slack

import com.slack.api.model.block.Blocks.input
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.element.BlockElements.datePicker
import com.slack.api.model.block.element.BlockElements.plainTextInput
import com.slack.api.model.view.View
import com.slack.api.model.view.Views.*


object SlackModalHelper {

    /**
     * 일정 신청 Slack Modal Template
     * 포함항목 : 캘린더, 신청 메시지
     */
    fun slackScheduleModalTemplate(): View = View.builder()
        .type("modal")
        .callbackId("schedule_request_modal")
        .title(viewTitle {
            it.type("plain_text").text("일정신청").emoji(true)
        })
        .submit(viewSubmit {
            it.type("plain_text").text("제출").emoji(true)
        })
        .close(viewClose {
            it.type("plain_text").text("닫기").emoji(true)
        })
        .blocks(listOf(
            input { block ->
                block.blockId("date_picker_block")
                    .element(datePicker {
                        it.actionId("date_picker_action")
                            .placeholder(plainText("날짜를 선택하세요", true))
                    })
                    .label(plainText("캘린더", true))
            },
            input { block ->
                block.blockId("message_input_block")
                    .element(plainTextInput {
                        it.actionId("message_input_action")
                            .multiline(true)
                            .placeholder(plainText("신청 메시지를 입력하세요", false))
                    })
                    .label(plainText("신청 메시지", true))
            }
        ))
        .build()
}
