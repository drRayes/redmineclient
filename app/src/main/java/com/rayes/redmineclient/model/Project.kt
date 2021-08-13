package com.rayes.redmineclient.model

import java.lang.StringBuilder

data class Project (
    val id: Int,
    val name: String,
    val identifier: String,
    val description: String,
    val isPublic: Boolean,
    val status: Int,
    val createdOn: String,
    val updatedOn: String
) {
    override fun toString(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append("id: $id \n")
        stringBuilder.append("name: $name \n")
        stringBuilder.append("identifier: $identifier \n")
        stringBuilder.append("description: $description \n")
        stringBuilder.append("isPublic: $isPublic \n")
        stringBuilder.append("status: $status \n")
        stringBuilder.append("createdOn: $createdOn \n")
        stringBuilder.append("updatedOn: $updatedOn \n")

        return stringBuilder.toString()
    }
}