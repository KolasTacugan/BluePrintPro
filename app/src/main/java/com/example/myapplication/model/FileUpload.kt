package com.example.myapplication.model

import kotlinx.serialization.Serializable

@Serializable
data class FileUpload(
    val userID: String,
    val bpURL: String,
    val bpName: String,
    val bpPrice: String,
    val designStyles: List<String>
)
