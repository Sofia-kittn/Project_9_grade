package com.example.progectmood

data class EmotionNote(
    val id: Int = 0,
    val date: Long = 0,
    val emotionLevel: Int = 0,
    val note: String = "",
    val tags: List<String> = emptyList()
) {
    fun getPreview(): String{
        return if(note.length > 50){
            note.substring(0, 47) + "..."
        }else{
            note
        }
    }

    fun findTag(tag: String){
    }
}