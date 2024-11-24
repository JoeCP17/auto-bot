package com.techeer.notification.interfaces.dto

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.jetbrains.annotations.NotNull
import org.springframework.util.MultiValueMap
import java.time.LocalDate

class ModalRequest {

    // TODO : NotNull이 굳이 필요할까?
    data class OpenModalRequest(
        @NotNull
        val triggerId: String,

        @NotNull
        val userId: String,

        @NotNull
        val channelId: String,

        @NotNull
        val command: String,
    ) {
        companion object {
            fun from(
                slashCommandPayload: MultiValueMap<String, Any>
            ): OpenModalRequest {
                return OpenModalRequest(
                    triggerId = slashCommandPayload.getFirst("trigger_id") as String,
                    userId = slashCommandPayload.getFirst("user_id") as String,
                    channelId = slashCommandPayload.getFirst("channel_id") as String,
                    command = slashCommandPayload.getFirst("command") as String
                )
            }
        }
    }

    data class SubmitModalRequest(
        val triggerId: String,

        val requestUserId: String,

        val selectedUserId: String,

        val selectedDate: String?,

        val selectedTime: String?,

        val reason: String?
    ) {
        init {
            // 요청받은 날짜가 존재 할 경우, 만약 현 시간보다 이전일 경우 예외 처리
            if (selectedDate != null) {
                val now = LocalDate.now()
                val selected = LocalDate.parse(selectedDate)
                require(now.isBefore(selected)) { "예약 시간은 현재 시간 이후로 설정해주세요." }
            }
        }

        companion object {
            fun from(
                submitModalRequestData: MultiValueMap<String, Any>
            ): SubmitModalRequest {
                val payloadJson = submitModalRequestData["payload"]?.first() as String
                val payload = jacksonObjectMapper().readValue(
                    payloadJson,
                    Map::class.java
                ) as Map<*, *>

                val triggerId = payload["trigger_id"] as String
                val requestUserId = (payload["user"] as Map<*, *>)["id"] as String

                val viewState =
                    ((payload["view"] as Map<*, *>)["state"] as Map<*, *>)["values"] as Map<*, *>

                val selectedDate =
                    ((viewState["date_picker_block"] as Map<*, *>)["date_picker_action"] as Map<*, *>)["selected_date"] as String?

                val timePickerBlock = viewState["time_picker_block"] as Map<*, *>
                val selectedTime =
                    ((timePickerBlock["time_picker_action"] as Map<*, *>)["selected_option"] as Map<*, *>)["value"] as String?

                val targetInputBlock = viewState["target_input_block"] as Map<*, *>
                val selectedUserId =
                    (targetInputBlock["target_user_action"] as Map<*, *>)["selected_user"] as String

                val reasonInputBlock = viewState["reason_input_block"] as Map<*, *>
                val rawReason =
                    (reasonInputBlock["reason_input_action"] as Map<*, *>)["value"] as String
                val decodedReason = decodeUnicode(rawReason)

                return SubmitModalRequest(
                    triggerId = triggerId,
                    requestUserId = requestUserId,
                    selectedUserId = selectedUserId,
                    selectedDate = selectedDate,
                    selectedTime = selectedTime,
                    reason = decodedReason
                )
            }

            // TODO : 좀 더 유연하게 바꾸기 정규표현식 사용 X
            private fun decodeUnicode(input: String): String {
                return input.replace("\\\\u([0-9A-Fa-f]{4})".toRegex()) { match ->
                    val codePoint = match.groupValues[1].toInt(16)
                    codePoint.toChar().toString()
                }
            }
        }
    }
}
