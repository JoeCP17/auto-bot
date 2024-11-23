package com.techeer.notification.domain.modal

enum class ModalPlatform(
    private val platFormName: String
) {
    SLACK("slack")
    ,
    ;

    companion object {
        fun findBy(platFormName: String): ModalPlatform {
            return entries.firstOrNull { it.platFormName == platFormName }
                ?: throw IllegalArgumentException("'${platFormName}' is not a valid.")
        }
    }
}
