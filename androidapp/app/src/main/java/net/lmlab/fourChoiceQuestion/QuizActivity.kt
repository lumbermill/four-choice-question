package net.lmlab.fourChoiceQuestion

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

// クイズ画面
class QuizActivity : AppCompatActivity() {
    // 画面全体を覆う透明なボタン
    private lateinit var overlay:Button
    // 現在表示している問題の位置
    private var index: Int = 0
    // 現在の得点
    private var score: Int = 0
    // 現在表示している問題の正解
    private var correctAnswer: String = ""
    // 現在の問題集（=問題の配列）
    private var questions: ArrayList<ArrayList<String>>? = arrayListOf(arrayListOf(""))
    // 正解の音
    private lateinit var soundCorrect:MediaPlayer
    // 不正解の音
    private lateinit var soundWrong:MediaPlayer

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // 正解、不正解の画像を表示するボタンを取得します
        overlay = findViewById(R.id.overlay)

        // 正解の時、不正解の時の音を準備します
        soundCorrect = MediaPlayer.create(this, R.raw.se_maoudamashii_chime13)
        soundWrong = MediaPlayer.create(this, R.raw.se_maoudamashii_onepoint33)

        // メイン画面で指定された問題集のタイトルを取得します
        val name = intent.getStringExtra("name")
        // タイトルに一致する問題集本体(=問題の配列)を取得します
        questions = Context.questions[name]

        if (questions == null) {
            // 問題が取得できなかったとき
            // * Context.ktに直接問題が定義されている今回は、ここに到達することはありません
            //   しかし、外部から問題を読んできて表示するような場合、こうしたエラー処理が大切です
            update(arrayListOf("wrong","","",""))
        }
        questions?.let {
            // 問題集が取得できた時、
            // 現在位置とスコアを0にリセットして、最初から問題を開始します
            index = 0
            score = 0
            update(it[index])
        }
    }

    // 選択肢(四択)を配列で受け取って、クイズ画面を更新するメソッド
    private fun update(options: ArrayList<String>){
        // 正解・不正解の表示を消去します
        overlay.isEnabled = false
        overlay.setBackgroundResource(0)

        // 画像を表示するビューと四択を表示するボタンを取得します
        val imageView = findViewById<ImageView>(R.id.imageView)
        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)
        val btn4 = findViewById<Button>(R.id.button4)

        // データの先頭にあるのが正解です
        correctAnswer = options[0]
        // 正解と一致する名前の画像を表示します
        val i = resources.getIdentifier(correctAnswer, "drawable" , packageName)
        imageView.setImageResource(i)

        // 選択肢はいつもかき混ぜます
        // * そのままだと一番上が常に正解になってしまいます
        val shuffled = options.shuffled()
        // ボタンに選択肢を表示します
        btn1.text = shuffled[0]
        btn2.text = shuffled[1]
        btn3.text = shuffled[2]
        btn4.text = shuffled[3]
    }

    // 四択のボタンがタップされた時に呼び出されます
    fun onClick(p0: View?) {
        // ボタンに書かれた(=選択された)答えを取得します
        val answer = (p0 as Button).text as String
        // 正解・不正解を表示します
        overlay.isEnabled = true
        if (answer == correctAnswer) {
            // 正解の場合
            // 正解画像(o)をセットします
            overlay.setBackgroundResource(R.drawable.correct)
            // 正解の音を鳴らします
            soundCorrect.start()
            // スコアを1加算します
            score += 1
        } else {
            // 不正解の場合
            // 不正解画像(x)をセットします
            overlay.setBackgroundResource(R.drawable.wrong)
            // 不正解の音を鳴らします
            soundWrong.start()
        }
    }

    // 正解・不正解のボタン（画面全体）がタップされた時に呼び出されます
    fun onClickOverlay(p0: View?) {
        // 問題の現在位置を次に進めます
        index += 1
        questions?.let {
            if (index >= it.size) {
                // 現在位置が問題の最後を過ぎていたら(=最後の問題が終わったら)
                // スコア表示画面に遷移します
                val intent = Intent(this, ScoreActivity::class.java)
                if (score == it.size) {
                    // 全問正解の場合のメッセージをセットします
                    intent.putExtra("score", "perfect")
                } else {
                    // それ以外(1問以上間違った時)のメッセージをセットします
                    intent.putExtra("score", "$score / ${it.size}")
                }
                startActivity(intent)
            } else {
                // 次の問題がある場合
                // 次の問題を表示します
                update(it[index])
            }
        }
    }
}
