package com.barabasizsolt.api

class DataLayerException(override val message: String?, override val cause: Throwable? = null) : RuntimeException(message, cause)