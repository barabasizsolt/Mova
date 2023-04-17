package com.barabasizsolt.mova.ui.util

import android.webkit.URLUtil

fun String.isValidUrl(): Boolean = URLUtil.isValidUrl(this)

fun String.isSvg(): Boolean = this.takeLast(n = 3) == "svg"