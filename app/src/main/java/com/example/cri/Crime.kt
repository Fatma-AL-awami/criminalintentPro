package com.example.cri

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.sql.Time
import java.util.*


//@PrimaryKey

@Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false,
                 var suspect: String = "")
    {
        val photoFileName
            get() = "IMG_$id.jpg"
}




