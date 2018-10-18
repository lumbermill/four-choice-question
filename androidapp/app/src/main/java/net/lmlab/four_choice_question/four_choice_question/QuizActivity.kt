package net.lmlab.four_choice_question.four_choice_question

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class QuizActivity : AppCompatActivity(), View.OnClickListener  {
    private lateinit var overlay:Button
    private var index: Int = 0
    private var score: Int = 0
    private var correctAnswer: String = ""
    private var questions: ArrayList<ArrayList<String>>? = arrayListOf(arrayListOf(""))
    private lateinit var soundCorrect:MediaPlayer
    private lateinit var soundWrong:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        soundCorrect = MediaPlayer.create(this, R.raw.se_maoudamashii_chime13)
        soundWrong = MediaPlayer.create(this, R.raw.se_maoudamashii_onepoint33)

        val name = intent.getStringExtra("name")
        questions = Context.questions[name]

        if (questions == null) {
            update(arrayListOf("wrong","","",""))
        }
        questions?.let {
            index = 0
            score = 0
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
        correctAnswer = options[0]
        val i = resources.getIdentifier(correctAnswer, "drawable" , packageName)
        imageView.setImageResource(i)

        // 選択肢はいつもかき混ぜる
        val shuffled = options.shuffled()
        btn1.text = shuffled[0]
        btn2.text = shuffled[1]
        btn3.text = shuffled[2]
        btn4.text = shuffled[3]

        //soundCorrect.prepare()
        //soundCorrect.prepare()
    }

    override fun onClick(p0: View?) {
        val answer = (p0 as Button).text as String
        overlay.isEnabled = true
        if (answer == correctAnswer) {
            overlay.setBackgroundResource(R.drawable.correct)
            soundCorrect.start()
            score += 1
        } else {
            overlay.setBackgroundResource(R.drawable.wrong)
            soundWrong.start()
        }
    }

    fun onClickOverkay(p0: View?) {
        index += 1
        questions?.let {
            if (index >= it.size) {
                // スコア表示画面
                val intent = Intent(this, ScoreActivity::class.java)
                if (score == it.size) {
                    intent.putExtra("score", "perfect")
                } else {
                    intent.putExtra("score", "$score / ${it.size}")
                }
                startActivity(intent)
            } else {
                // 次の画面
                update(it[index])
            }
        }
    }
}
