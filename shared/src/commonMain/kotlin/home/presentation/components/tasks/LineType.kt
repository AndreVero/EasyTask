package home.presentation.components.tasks

sealed interface LineType {
    object Normal : LineType
    object FiveStep : LineType
    object TenStep : LineType
}