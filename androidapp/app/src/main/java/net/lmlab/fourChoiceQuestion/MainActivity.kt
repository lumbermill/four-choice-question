package net.lmlab.fourChoiceQuestion

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val listView = findViewById<ListView>(R.id.listview)
        val drills = Context.questions.keys.toTypedArray()
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, drills)
        listView.setOnItemClickListener { _, _, i, _ ->
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("name", drills[i])
            startActivity(intent)
        }
    }
}
