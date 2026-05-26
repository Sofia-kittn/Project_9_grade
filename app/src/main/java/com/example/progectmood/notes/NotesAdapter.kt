package com.example.progectmood.notes

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.progectmood.R
import com.example.progectmood.db.EmotionLevel
import java.text.DateFormat
import java.util.Date

class NotesAdapter(
    private val notes: List<Note>,
    private val onClick: (Note) -> Unit
) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val emotion: TextView = view.findViewById(R.id.text_emotion)
        val note: TextView = view.findViewById(R.id.text_note)
        val date: TextView = view.findViewById(R.id.text_date)
        val tags: TextView = view.findViewById(R.id.text_tags)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {

        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_note, parent, false)

        return NoteViewHolder(view)
    }

    override fun getItemCount(): Int = notes.size

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

        val current = notes[position]

        val emotionText = when (current.emotionLevel) {

            EmotionLevel.BAD -> "😢 Bad"

            EmotionLevel.MEH -> "😐 Meh"

            EmotionLevel.OK -> "😊 Ok"

            EmotionLevel.NICE -> "😄 Nice"

            EmotionLevel.GOOD -> "🎉 Good"
        }

        holder.emotion.text = emotionText

        holder.tags.text = current.tags.joinToString(" ") { "#$it" }

        holder.note.text = current.note ?: ""

        val formattedDate = DateFormat.getDateInstance()
            .format(Date(current.date))

        holder.date.text = formattedDate

        holder.itemView.setOnClickListener {
            onClick(current)
        }
    }
}