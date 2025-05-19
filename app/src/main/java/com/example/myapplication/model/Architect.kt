package com.example.myapplication.model

data class Architect(
    val username: String,
    val password: String,
    val firstname: String,
    val lastname: String,
    val phone: String,
    val designStyles: List<String>,
    val specializations: List<String>,
    val laborCost: String,
    val serviceLocation: String,
    val city: String,
    val experience: String,
    val prcLicense: String
)
