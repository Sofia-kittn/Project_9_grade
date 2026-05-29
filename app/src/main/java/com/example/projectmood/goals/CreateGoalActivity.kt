package com.example.projectmood.goals

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.projectmood.db.AppDatabase
import kotlinx.coroutines.Dispatchers

import com.example.projectmood.R
import kotlinx.coroutines.launch

class CreateGoalActivity : AppCompatActivity() {

    private var currentGoalId: Int = -1
    private var currentGoalDate: Long = -1L

    override fun onCreate(savedInstanceState: Bundle?) {

        enableEdgeToEdge()

        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_create_goal)

        val buttonDelete: Button =
            findViewById(R.id.button_del_goal)

        val buttonSave: Button =
            findViewById(R.id.button_save_goal)

        val backArrow: ImageButton =
            findViewById(R.id.back_arrow)

        val goalText: EditText =
            findViewById(R.id.goal_text)

        val db = AppDatabase.getDatabase(this)

        currentGoalId =
            intent.getIntExtra("goal_id", -1)

        if (currentGoalId != -1) {

            lifecycleScope.launch(Dispatchers.IO) {

                val goal =
                    db.goalDao().getById(currentGoalId)

                goal?.let {
                    runOnUiThread {
                        goalText.setText(it.title)
                        currentGoalDate = it.date
                    }
                }
            }
        }

        buttonSave.setOnClickListener {

            val title =
                goalText.text.toString().trim()

            if (title.isEmpty()) {

                Toast.makeText(
                    this,
                    "Введите цель",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            val goal = Goal(

                uid =
                    if (currentGoalId == -1)
                        0
                    else
                        currentGoalId,

                title = title,

                isCompleted = false,

                date =
                    if (currentGoalDate == -1L)
                        System.currentTimeMillis()
                    else
                        currentGoalDate
            )

            lifecycleScope.launch(Dispatchers.IO) {

                if (currentGoalId == -1) {

                    db.goalDao().insertAll(goal)

                } else {

                    db.goalDao().updateGoal(goal)
                }

                runOnUiThread {

                    Toast.makeText(
                        this@CreateGoalActivity,
                        "Goal saved",
                        Toast.LENGTH_SHORT
                    ).show()

                    finish()
                }
            }
        }

        buttonDelete.setOnClickListener {

            if (currentGoalId != -1) {

                lifecycleScope.launch(Dispatchers.IO) {

                    db.goalDao()
                        .deleteById(currentGoalId)

                    runOnUiThread {

                        Toast.makeText(
                            this@CreateGoalActivity,
                            "Goal deleted",
                            Toast.LENGTH_SHORT
                        ).show()

                        finish()
                    }
                }

            } else {

                finish()
            }
        }

        backArrow.setOnClickListener {
            finish()
        }
    }
}