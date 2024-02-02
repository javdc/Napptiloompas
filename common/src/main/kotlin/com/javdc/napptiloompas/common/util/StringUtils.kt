package com.javdc.napptiloompas.common.util

fun String.takeIfNotBlank() = takeIf { it.isNotBlank() }