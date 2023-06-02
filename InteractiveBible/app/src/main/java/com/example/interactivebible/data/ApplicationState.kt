package com.example.interactivebible.data

import android.text.SpannableStringBuilder
import java.util.*
import java.util.Optional.empty

data class ApplicationState(
    val text_data_pre: SpannableStringBuilder = SpannableStringBuilder(),
    val image: Optional<Any> = empty(),
    val text_data_pro: Optional<String>,
    val isExpanded: Boolean = false
    )

data class UIData(
    val uidata: Array<ApplicationState> = arrayOf<ApplicationState>()
)