package com.rayes.redmineclient.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import kotlinx.serialization.Serializable

@Serializable
data class User(val random: String = "default") : BaseObservable() {
    @get:Bindable
    var username: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    @get:Bindable
    var password: String = ""
        set(value) {
            field = value
            notifyChange()
        }

    lateinit var authenticity_token: String

    lateinit var csrf_token: String
}