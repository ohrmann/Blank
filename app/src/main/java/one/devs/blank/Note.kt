package one.devs.blank

import java.time.LocalDateTime

data class Note(
    val id: Int,
    val title: String,
    val content: String,
    val creationDate: LocalDateTime,
    val modificationDate: LocalDateTime
)