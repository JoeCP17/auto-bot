package com.techeer.notification.infrastructure.slack

import com.slack.api.model.block.Blocks.input
import com.slack.api.model.block.composition.BlockCompositions.option
import com.slack.api.model.block.composition.BlockCompositions.plainText
import com.slack.api.model.block.element.BlockElements.*
import com.slack.api.model.view.View
import com.slack.api.model.view.Views.*
import com.techeer.notification.domain.modal.ModalType


object SlackModalHelper {

    /**
     * Model Type에 따라, Slack Modal Template을 반환
     */
    fun findModalTemplate(modalType: ModalType): View {
        return when (modalType) {
            ModalType.SCHEDULE -> slackScheduleModalTemplate()
        }
    }


    /**
     * 일정 신청 Slack Modal Template
     * 포함항목 : 캘린더, 신청 메시지
     */
    private fun slackScheduleModalTemplate(): View = View.builder()
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
            // 날짜 선택
            input { block ->
                block.blockId("date_picker_block")
                    .element(datePicker {
                        it.actionId("date_picker_action")
                            .placeholder(plainText("날짜를 선택하세요", true))
                    })
                    .label(plainText("날짜", true))
            },
            // 신청 시간 (1H 단위)
            input { block ->
                block.blockId("time_picker_block")
                    .element(staticSelect { it ->
                        it.actionId("time_picker_action")
                            .placeholder(plainText("시간을 선택하세요", true))
                            .options((0..23).map { hour ->
                                option {
                                    it.text(plainText("${hour}:00", true))
                                        .value("${hour}:00")
                                }
                            })
                    })
                    .label(plainText("신청 시간 (1H 단위)", true))
            },
            // 신청 대상 기입
            input { block ->
                block.blockId("target_input_block")
                    .element(usersSelect {
                        it.actionId("target_user_action")
                            .placeholder(plainText("신청 대상을 입력하세요", true))
                    })
                    .label(plainText("신청 대상", true))
            },
            // 신청 사유 기입
            input { block ->
                block.blockId("reason_input_block")
                    .element(plainTextInput {
                        it.actionId("reason_input_action")
                            .multiline(true)
                            .placeholder(plainText("신청 사유를 입력하세요", false))
                    })
                    .label(plainText("신청 사유", true))
            }
        ))
        .build()
}
