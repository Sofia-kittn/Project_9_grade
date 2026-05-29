package com.example.projectmood.db

import androidx.room.TypeConverter


class Converters {

    @TypeConverter
    fun fromTagList(tags: List<String>): String {
        return tags.joinToString(",")
    }

    @TypeConverter
    fun toTagList(data: String): List<String> {
        return if (data.isEmpty()) emptyList()
        else data.split(",")
    }

    @TypeConverter
    fun fromEmotionLevel(level: EmotionLevel): String {
        return level.name
    }

    @TypeConverter
    fun toEmotionLevel(value: String): EmotionLevel {
        return EmotionLevel.valueOf(value)
    }
}