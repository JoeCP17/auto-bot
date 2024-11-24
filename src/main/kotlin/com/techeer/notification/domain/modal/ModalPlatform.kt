package com.techeer.notification.domain.modal

// TODO : DB연동 이후, DB에서 ModalPlatform을 가져오도록 수정 ( 유연하지 못함 )
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
