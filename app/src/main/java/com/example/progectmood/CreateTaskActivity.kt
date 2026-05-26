package com.example.progectmood

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity

class CreateTaskActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        enableEdgeToEdge()
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        val buttonDel: Button = findViewById(R.id.button_del_goal)
        val buttonSave: Button = findViewById(R.id.button_save_goal)

        buttonSave.setOnClickListener {
            Toast.makeText(this, "Your goal was added", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }

        buttonDel.setOnClickListener {
            Toast.makeText(this, "Goal was deleated", Toast.LENGTH_SHORT).show()
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
        }
    }
}