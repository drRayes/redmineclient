package com.rayes.redmineclient.model

import androidx.databinding.BaseObservable
import androidx.databinding.Bindable
import java.io.Serializable

class Issue : BaseObservable() {
    @get:Bindable
    var id: Int = 0
        set(value) {
            field = value
            notifyPropertyChanged(id)
        }
    lateinit var project: Project
    var tracker: IdNameTemplate = IdNameTemplate(0, "")
    var status: IdNameTemplate = IdNameTemplate(0, "")
    var priority: IdNameTemplate = IdNameTemplate(0, "")
    var author: IdNameTemplate = IdNameTemplate(0, "")
    @get:Bindable
    var subject: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var description: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var startDate: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var dueDate: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var doneRatio: String = ""
        set(value) {
            field = value
            notifyChange()
        }
//    @get:Bindable
//    var isPrivate: Boolean = false
//        set(value) {
//            field = value
//            notifyChange()
//        }
    @get:Bindable
    var estimatedHours: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var totalEstimatedHours: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var createdOn: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var updatedOn: String = ""
        set(value) {
            field = value
            notifyChange()
        }
    @get:Bindable
    var closeOn: String = ""
        set(value) {
            field = value
            notifyChange()
        }
}