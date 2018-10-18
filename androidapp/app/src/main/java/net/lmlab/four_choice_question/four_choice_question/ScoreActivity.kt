package net.lmlab.four_choice_question.four_choice_question

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView

class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        val score = intent.getStringExtra("score")
        val textView = findViewById<TextView>(R.id.textView)
        textView.text = "You've got $score!!"
    }

    fun onClick(p0: View?) {
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
