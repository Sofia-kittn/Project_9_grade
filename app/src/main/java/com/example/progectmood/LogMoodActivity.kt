package com.example.progectmood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.RadioGroup
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class LogMoodActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_log_mood)

        val buttonDel: Button = findViewById(R.id.button_del_mood)
        val buttonSave: Button = findViewById(R.id.button_save_mood)
        val backArrow: android.widget.ImageButton = findViewById(R.id.back_arrow)
        val noteText: EditText = findViewById(R.id.note_text)
        val tags: EditText = findViewById(R.id.tags)
        val emotionRadioGroup: RadioGroup = findViewById(R.id.emotions_group)

        var selectedEmotion: String? = null

        buttonSave.setOnClickListener {
            val note = EmotionNote()

            val db = dbHelper(this)
            db.addNote(note)

            Toast.makeText(this, "Your mood was logged", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonDel.setOnClickListener {
            Toast.makeText(this, "Note was deleated", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        emotionRadioGroup.setOnCheckedChangeListener { group, id ->
            selectedEmotion = when(id){
                R.id.emotion1 -> "Bad"
                R.id.emotion2 -> "Meh"
                R.id.emotion3 -> "Neutral"
                R.id.emotion4 -> "Nice"
                R.id.emotion5 -> "Good"

                else -> null
            }

            if (selectedEmotion != null){
                Toast.makeText(this, "Your emotion is $selectedEmotion", Toast.LENGTH_SHORT).show()
            }
        }

        backArrow.setOnClickListener {
            finish()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}