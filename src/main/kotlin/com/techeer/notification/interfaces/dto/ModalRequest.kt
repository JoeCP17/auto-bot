package com.techeer.notification.interfaces.dto

import com.fasterxml.jackson.annotation.JsonIgnoreProperties
import com.fasterxml.jackson.annotation.JsonProperty
import org.jetbrains.annotations.NotNull
import org.springframework.util.MultiValueMap

class ModalRequest {

    @JsonIgnoreProperties(ignoreUnknown = true)
    data class OpenModalRequest(
        @NotNull
        @JsonProperty("trigger_id")
        val triggerId: String,

        @NotNull
        @JsonProperty("user_id")
        val userId: String,

        @NotNull
        @JsonProperty("channel_id")
        val channelId: String,

        @NotNull
        @JsonProperty("command")
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
}
