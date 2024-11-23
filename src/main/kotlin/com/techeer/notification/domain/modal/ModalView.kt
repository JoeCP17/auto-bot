package com.techeer.notification.domain.modal

import io.micrometer.common.util.StringUtils

class ModalView(
    val triggerId: String,
    val modalPlatform: ModalPlatform,
    val modalType: ModalType
) {

    init {
        require(StringUtils.isNotEmpty(triggerId)) { "'triggerId' must not be null, empty" }
    }
}
