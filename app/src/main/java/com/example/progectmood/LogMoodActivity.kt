package com.example.progectmood

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.progectmood.db.AppDatabase
import com.example.progectmood.db.EmotionLevel
import com.example.progectmood.notes.Note
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class LogMoodActivity : AppCompatActivity() {
    private var selectedEmotion = EmotionLevel.OK
    private var currentNoteId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_mood)

        val buttonDel: Button = findViewById(R.id.button_del_mood)
        val buttonSave: Button = findViewById(R.id.button_save_mood)
        val backArrow: ImageButton = findViewById(R.id.back_arrow)

        val noteText: EditText = findViewById(R.id.note_text)
        val tagsText: EditText = findViewById(R.id.tags)
        val emotionRadioGroup: RadioGroup = findViewById(R.id.emotions_group)

        val db = AppDatabase.getDatabase(this)

        currentNoteId = intent.getIntExtra("note_id", -1)

        if (currentNoteId != -1) {

            lifecycleScope.launch(Dispatchers.IO) {
                val note = db.noteDao().getById(currentNoteId)

                note?.let {

                    runOnUiThread {
                        noteText.setText(it.note)
                        tagsText.setText(it.tags.joinToString())

                        selectedEmotion = it.emotionLevel

                        when (it.emotionLevel) {
                            EmotionLevel.BAD ->
                                emotionRadioGroup.check(R.id.emotion_bad)

                            EmotionLevel.MEH ->
                                emotionRadioGroup.check(R.id.emotion_meh)

                            EmotionLevel.OK ->
                                emotionRadioGroup.check(R.id.emotion_ok)

                            EmotionLevel.NICE ->
                                emotionRadioGroup.check(R.id.emotion_nice)

                            EmotionLevel.GOOD ->
                                emotionRadioGroup.check(R.id.emotion_good)
                        }
                    }
                }
            }
        } else {
            emotionRadioGroup.check(R.id.emotion_ok)
        }

        buttonSave.setOnClickListener {
            val noteContent = noteText.text.toString()

            val tagsList = tagsText.text.toString() // TODO: fix that shit, you can type anything
                .split(",")
                .map { it.trim() }
                .filter { it.isNotEmpty() }

            val note = Note(
                uid = if (currentNoteId == -1) 0 else currentNoteId,
                date = System.currentTimeMillis(),
                emotionLevel = selectedEmotion,
                note = noteContent,
                tags = tagsList
            )

            lifecycleScope.launch(Dispatchers.IO) {

                if (currentNoteId == -1) {
                    db.noteDao().insertAll(note)
                } else {
                    db.noteDao().updateNote(note)
                }

                runOnUiThread {
                    Toast.makeText(
                        this@LogMoodActivity,
                        "Note saved",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }

        buttonDel.setOnClickListener {
            if (currentNoteId != -1) {

                lifecycleScope.launch(Dispatchers.IO) {

                    db.noteDao().deleteById(currentNoteId)

                    runOnUiThread {

                        Toast.makeText(
                            this@LogMoodActivity,
                            "Note deleted",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                }

            } else {
                finish()
            }
        }

        emotionRadioGroup.setOnCheckedChangeListener { _, id ->
            selectedEmotion = when (id) {

                R.id.emotion_bad -> EmotionLevel.BAD

                R.id.emotion_meh -> EmotionLevel.MEH

                R.id.emotion_ok -> EmotionLevel.OK

                R.id.emotion_nice -> EmotionLevel.NICE

                R.id.emotion_good -> EmotionLevel.GOOD

                else -> EmotionLevel.OK
            }
        }

        backArrow.setOnClickListener {
            finish()
        }
    }
}