package com.barabasizsolt.activityprovider.api

import android.app.Activity

interface ActivityProvider {
    fun get(): Activity
}