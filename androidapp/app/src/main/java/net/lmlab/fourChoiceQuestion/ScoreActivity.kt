package net.lmlab.fourChoiceQuestion

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.TextView

// スコア画面
class ScoreActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_score)

        // クイズ画面から渡されてきたスコア(文字列)を取得します
        val score = intent.getStringExtra("score")
        // スコアを表示するテキストビューを取得します
        val textView = findViewById<TextView>(R.id.textView)
        // スコアをテキストビューにセットします
        textView.text = "You've got $score!!"
    }

    // ボタンがタップされた時に呼び出されます
    fun onClick(p0: View?) {
        // メイン画面に遷移します
        val intent = Intent(this, MainActivity::class.java)
        startActivity(intent)
    }
}
