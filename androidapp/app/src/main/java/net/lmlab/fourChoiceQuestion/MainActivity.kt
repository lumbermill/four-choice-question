package net.lmlab.fourChoiceQuestion

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

// メイン画面
// 問題集の一覧を表示します
class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // レイアウトで追加したリストビューを呼び出します
        val listView = findViewById<ListView>(R.id.listview)
        // Context.ktで定義した問題のタイトルを取得します
        val titles = Context.questions.keys.toTypedArray()
        //
        listView.adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, titles)
        // リストの項目がタップされた際の動作を定義します
        listView.setOnItemClickListener { _, _, i, _ ->
            // タップされた項目のタイトルをクイズ画面に渡して、クイズ画面に遷移します
            val intent = Intent(this, QuizActivity::class.java)
            intent.putExtra("name", titles[i])
            startActivity(intent)
        }
    }
}
