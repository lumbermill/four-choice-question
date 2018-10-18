package net.lmlab.four_choice_question.four_choice_question

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class QuizActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var overlay:Button
    private var index: Int = 0
    private var correct_answer: String = ""
    private var questions: ArrayList<ArrayList<String>>? = arrayListOf(arrayListOf(""))

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        val name = intent.getStringExtra("name")
        questions = Context.questions[name]

        if (questions == null) {
            update(arrayListOf("wrong","","",""))
        }
        questions?.let {
            index = 0
            update(it[index])
        }
    }

    fun update(options: ArrayList<String>){
        overlay = findViewById<Button>(R.id.overlay)
        overlay.isEnabled = false
        overlay.setBackgroundResource(0)

        val imageView = findViewById<ImageView>(R.id.imageView)
        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)
        val btn4 = findViewById<Button>(R.id.button4)

        // データの先頭にあるのが答え -> 画像を表示
        correct_answer = options[0]
        val i = resources.getIdentifier(correct_answer, "drawable" , packageName)
        imageView.setImageResource(i)

        // 選択肢はいつもかき混ぜる
        options.shuffle()
        btn1.text = options[0]
        btn2.text = options[1]
        btn3.text = options[2]
        btn4.text = options[3]
    }

    override fun onClick(p0: View?) {
        val answer = (p0 as Button).text as String

        overlay.isEnabled = true
        if (answer == correct_answer) {
            overlay.setBackgroundResource(R.drawable.correct)
        } else {
            overlay.setBackgroundResource(R.drawable.wrong)
        }
//        #next.isEnabled = true
//        judge.alpha = 1f

    }

    fun onClickOverkay(p0: View?) {
        index += 1
        questions?.let {
            if (index >= it.size) {
                // TODO: スコア表示画面に？
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            } else {
                update(it[index])
            }
        }
    }
}
