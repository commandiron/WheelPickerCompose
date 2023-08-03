package com.commandiron.wheel_picker_compose.extentions

inline fun String.replaceFirstCharIf(
    condition: () -> Boolean,
    transform: (Char) -> CharSequence
): String = if (condition()) {
    replaceFirstChar(transform)
} else {
    this
}