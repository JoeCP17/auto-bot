package com.techeer.notification.domain.modal

/**
 * Modal의 종류를 표현하는 enum 클래스
 * 해당 Enum 타입을 기점으로, Modal Template을 생성합니다.
 * Schedule 선택시, SlackModalHelper내 ScheduleApplyModal를 가져온다.
 */
enum class ModalType(
    private val commandText: String
) {
    SCHEDULE("/일정신청")
    ,
    ;


    companion object {
        fun findBy(commandText: String): ModalType {
            return entries.firstOrNull { it.commandText == commandText }
                ?: throw IllegalArgumentException("'$commandText' is not a valid.")
        }
    }
}
