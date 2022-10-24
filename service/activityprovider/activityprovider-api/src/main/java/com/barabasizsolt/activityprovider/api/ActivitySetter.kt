package com.barabasizsolt.activityprovider.api

import android.app.Activity

interface ActivitySetter {
    fun set(activity: Activity)
    fun clear()
}