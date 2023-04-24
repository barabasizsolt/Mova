package ui.util

import android.webkit.URLUtil

actual fun String.isValidUrl(): Boolean = URLUtil.isValidUrl(this)