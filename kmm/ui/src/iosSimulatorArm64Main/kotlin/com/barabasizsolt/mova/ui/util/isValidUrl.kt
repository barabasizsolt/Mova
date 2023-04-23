package com.barabasizsolt.mova.ui.util

import platform.Foundation.NSURL
import platform.UIKit.UIApplication

actual fun String.isValidUrl(): Boolean {
    val nsUrl = NSURL.URLWithString(this)
    return if (nsUrl == null) false else UIApplication.sharedApplication.canOpenURL(nsUrl)
}