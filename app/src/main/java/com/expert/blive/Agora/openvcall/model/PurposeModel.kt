package com.example.battle.agora.openvcall.model

data class PurposeModel(
    val details: List<PurposeList>,
    val message: String,
    val success: String
)

data class PurposeList(
    val created: String,
    val id: String,
    val title: String,
    val updated: String
)