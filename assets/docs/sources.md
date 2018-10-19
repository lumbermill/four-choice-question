# 四択クイズアプリサンプルコード
## Context.kt
```
package net.lmlab.fourChoiceQuestion

class Context {
    companion object {
        // クイズの問題をここに定義します。
        // クイズは表示された絵を見て、それに該当する答えを四つの選択肢の中から選ぶものです
        // 一つの問題は長さが4の配列から成り、表示される絵は常に先頭のものとします（先頭が正解）
        // 問題を複数個集めた配列が一つの問題集となります
        // 問題集のタイトルをキー、問題集本体（=問題の配列）を値としたハッシュ(questions)を定義します
        val questions: HashMap<String, ArrayList<ArrayList<String>>> = hashMapOf(
            "animal" to arrayListOf(
                arrayListOf("monkey", "money", "monk", "mosquito"),
                arrayListOf("horse", "bonito", "mouse", "sheep"),
                arrayListOf("deer", "bear", "lion", "cow")
            ),
            "fish" to arrayListOf(
                arrayListOf("tuna", "octopus", "turtle", "mango")
            )
        )
    }
}
```

## MainActivity.kt
```
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
```

## QuizActivity.kt
```
package net.lmlab.fourChoiceQuestion

import android.content.Intent
import android.media.MediaPlayer
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

// クイズ画面
class QuizActivity : AppCompatActivity(), View.OnClickListener  {
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
    override fun onClick(p0: View?) {
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
```

## ScoreActivity.kt
```
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
```

## activity_main.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".MainActivity">

    <ListView
            android:layout_width="368dp"
            android:layout_height="495dp"
            app:layout_constraintStart_toStartOf="parent" android:layout_marginLeft="8dp"
            android:layout_marginStart="8dp" app:layout_constraintTop_toTopOf="parent" android:layout_marginTop="8dp"
            app:layout_constraintEnd_toEndOf="parent" android:layout_marginEnd="8dp" android:layout_marginRight="8dp"
            android:layout_marginBottom="8dp" app:layout_constraintBottom_toBottomOf="parent"
            android:id="@+id/listview"/>

</android.support.constraint.ConstraintLayout>
```

## activity_quiz.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".QuizActivity">

    <Button
            android:text="@string/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/button1"
            android:layout_marginBottom="8dp" app:layout_constraintBottom_toTopOf="@+id/button2"
            app:layout_constraintStart_toStartOf="@+id/button2"
            app:layout_constraintEnd_toEndOf="@+id/button2" android:onClick="onClick"/>
    <Button
            android:text="@string/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/button2"
            android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toTopOf="@+id/button3"
            app:layout_constraintStart_toStartOf="@+id/button3"
            app:layout_constraintEnd_toEndOf="@+id/button3" android:onClick="onClick"/>
    <Button
            android:text="@string/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/button3" android:layout_marginBottom="8dp"
            app:layout_constraintBottom_toTopOf="@+id/button4"
            app:layout_constraintStart_toStartOf="@+id/button4"
            app:layout_constraintEnd_toEndOf="@+id/button4" android:onClick="onClick"/>
    <Button
            android:text="@string/button"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/button4"
            app:layout_constraintBottom_toBottomOf="parent" android:layout_marginBottom="32dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            android:onClick="onClick"/>
    <ImageView
            android:layout_width="382dp"
            android:layout_height="196dp" tools:srcCompat="@tools:sample/avatars"
            android:id="@+id/imageView" android:layout_marginBottom="32dp"
            app:layout_constraintBottom_toTopOf="@+id/button1" app:layout_constraintTop_toTopOf="parent"
            android:layout_marginTop="8dp" app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            android:scaleType="centerInside" android:contentDescription="@string/app_name"/>
    <Button
            android:id="@+id/overlay"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintStart_toStartOf="parent" app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toBottomOf="parent"
            android:background="@drawable/correct" android:onClick="onClickOverlay"
            android:layout_width="0dp" android:layout_height="0dp"
            app:layout_constraintDimensionRatio="1"/>
</android.support.constraint.ConstraintLayout>
```

## activity_score.xml
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
        xmlns:android="http://schemas.android.com/apk/res/android"
        xmlns:tools="http://schemas.android.com/tools"
        xmlns:app="http://schemas.android.com/apk/res-auto"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        tools:context=".ScoreActivity">

    <Button
            android:text="@string/back"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/button"
            app:layout_constraintBottom_toBottomOf="parent" android:layout_marginBottom="8dp"
            app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            android:onClick="onClick"/>
    <TextView
            android:text="@string/app_name"
            android:layout_width="wrap_content"
            android:layout_height="wrap_content"
            android:id="@+id/textView" app:layout_constraintStart_toStartOf="parent"
            app:layout_constraintEnd_toEndOf="parent"
            app:layout_constraintBottom_toTopOf="@+id/button" app:layout_constraintTop_toTopOf="parent"
            android:layout_marginTop="24dp" android:textAppearance="@style/TextAppearance.AppCompat.Large"/>
</android.support.constraint.ConstraintLayout>
```

## strings.xml
```
<resources>
    <string name="app_name">four-choice-question</string>
    <string name="button">button</string>
    <string name="back">Back to Title</string>
</resources>
```

## その他のリソース
- res/drawable (画像)
  - correct.png
  - deer.png
  - horse.png
  - monkey.png
  - octopus.png
  - sunfish.png
  - tuna.png
  - wrong.png
- res/raw (音声)
  - se_maoudamashii_chime13.mp3
  - se_maoudamashii_onepoint33.mp3

## 生成情報
2018-10-19 12:30:26 +0900 880ddafefd56d5dce4dfe451999f3522d687bbeb
  
Generated by assets/gen-doc.rb
