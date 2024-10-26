package com.github.coutinhonobre.roboletric

object TextProvider {

    var text: String = ""

    fun getTextRandom(): String {
        if (text.isNotEmpty()) {
            return text
        }
        return listOf(
            "Texto 1",
            "Texto 2",
            "Texto 3",
            "Texto 4",
            "Texto 5",
            "Texto 6",
            "Texto 7",
            "Texto 8",
            "Texto 9",
            "Texto 10"
        ).random()
    }
}