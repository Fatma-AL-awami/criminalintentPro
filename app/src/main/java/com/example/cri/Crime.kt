package com.example.cri

import androidx.room.Entity
import androidx.room.PrimaryKey
import java.util.*


//@PrimaryKey

@Entity
data class Crime(@PrimaryKey val id: UUID = UUID.randomUUID(),
                 var title: String = "",
                 var date: Date = Date(),
                 var isSolved: Boolean = false)
    {

}