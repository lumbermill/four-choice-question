# Androidでクイズアプリ構築
2018.10.16

Android Studioのインストールについては、別紙(setup.key)を参照してください。
本解説は、Android Studio 3.2.1をベースに作成しています。
スクリーンショットが一部、3.1.4ベースになっていますが、相違点がある場合は明記します。

## 作成するプロジェクト
今回はクイズアプリを作成します。下記は最初の画面です。
クイズのカテゴリーが表示されています。カテゴリーを選択するとクイズ画面に遷移します。

![アプリ画面](assets/screenshots/device-01.png)

画面下部の4つの選択肢から画像に適した物を選びます。

![アプリ画面](assets/screenshots/device-02.png)

正解すると下記のように「◯」印が表示されます。

![アプリ画面](assets/screenshots/device-03.png)

不正解の場合、「x」印が表示されます。

![アプリ画面](assets/screenshots/device-04.png)

「◯」「x」印が表示された状態でタップすると次のクイズへ遷移します。
以上が一連の流れです。

## 新しいプロジェクトの作成
Android Studioを起動します。「Start a new Android Studio project」を選んで新しいプロジェクトを作成（開始）しましょう。

![起動画面](assets/screenshots/16.png)

アプリの名前などを決定します。半角の英語で設定してください。

![起動画面](assets/screenshots/17.png)

「Target Android Device」などは表示されているままで、そのまま進んでください。
アクティビティは「Empty Activity」を選びます。

![起動画面](assets/screenshots/19.png)

この後、プロジェクトの作成と初回のビルドが開始されます。少し待ちましょう。
（この間に、どんなボタンがあるか等、色々覗いて回りましょう）

ビルド完了後、「Code -> Convert Java to Kotlin」と操作して、
プロジェクトをKotlinに変換します。

## アプリケーションの実行

USBケーブルで開発用のマシンとAndroidデバイスを接続してください。
「Connected Devices」の欄にデバイスが表示されていれば、問題ありません。
初回はAndroid端末側で操作が必要なこともありますので、うまくいかない場合は、
Android側の画面も確認して、何か指示やエラーが出ていないかを確認してください。

デバイスが表示されていれば「Ok」をクリックすると、Androidにプログラムが転送され、
アプリが起動します（画面の真ん中にHello world!と表示されます）。

## 新しい画面を追加する

MainActivity.kt 上で右クリックし「File -> Activity -> Empty Activity」を選択し、QuizActivitiy.ktとactivity_quiz.xmlを作成します。


## ボタンを作る

activity_quiz.xmlを編集して、以下のようなレイアウトを作成します。
Androidの端末は様々な画面サイズのものが存在するため、どの画面で見ても使いやすい配置を心がける必要があります。
そのため、画面の座標(x,y)や大きさ(width,height)を直接していするのではなく、相対的な指定、例えば、
画面の端からどのくらい、とか手前のボタンからどのくらい、といった指定を繰り返してボタンや画像を配置することになります。

![起動画面](assets/screenshots/studio-01.png)


## 画像を配置する
必要な画像は、自分たちで集めて貰ってもOKですが、著作権に注意してください。
今回のサンプルアプリで使用している画像は、以下のURLからダウンロードできます。
（URLが長いので、まずはgithub.comにアクセスしたあと、four-choice-questionリポジトリを検索した方が確実かもしれません）

https://github.com/lumbermill/four-choice-question/tree/master/assets/images

以下のような著作権フリーの素材から選んできても構いません。

https://www.irasutoya.com/


画像を取得したらres/drawable/に画像をコピーします。



## クイズのデータを作成する
プログラムの他にデータ（クイズの問題）が必要です。
TODO: Context.ktの作成
「File -> New -> Scratch File」メニューから「Kotlin」を選択します。

```java
print("Hello, World.")

var animals = arrayListOf("hedgehog","dog","turkey")
print(animals)
print(animals[1])
// 存在しない番号はエラーになる
print(animals[3])

// 追加
animals.add("cow")
print(animals)

// 配列を追加する
var ape = arrayListOf("gorilla", "pongo")
animals.addAll(ape)
print(animals)

// 上書き
animals[2] = "bird"
print(animals)

// 中身を検索して削除
animals.remove("dog")
print(animals)

// インデックスを指定して削除
animals.removeAt(1)
print(animals)

var points = hashMapOf("taro" to 3, "jiro" to 5)
print(points)
print(points["taro"])
// 存在しないキーだとnullが返ってくる
print(points["saburo"])

// 追加
points["siro"] = 7
print(points)

// 上書き
points["jiro"] = 4
print(points)

// キーを指定して削除
points.remove("jiro")
print(points)
```

```
class Context {
    companion object {
        val questions: HashMap<String, ArrayList<ArrayList<String>>> = hashMapOf(
                "animal" to arrayListOf(
                        arrayListOf("hedgehog","dog","turkey","cow"),
                        arrayListOf("cow","shrimp","squid","horse"),
                        arrayListOf("pig","bonito","crab","squid"),
                        arrayListOf("cat","monkey","mouse","deer"),
                        arrayListOf("dog","mouse","deer","panda"),
                        arrayListOf("monkey","money","monk","mosquito"),
                        arrayListOf("mouse","monkey","crab","tuna"),
                        arrayListOf("giraffe","deer","tuna","squid"),
                        arrayListOf("bird","sheep","shrimp","bonito"),
                        arrayListOf("horse","bonito","mouse","sheep"),
                        arrayListOf("deer","bear","lion","cow"),
                        arrayListOf("panda","bird","monkey","punk")
                ),
                "fish" to arrayListOf(
                        arrayListOf("","","","")
                )
        )
    }
}
```


## MainActivity作成
### 画面作成
activity_main.xmlを編集します。
画面一杯にListViewを表示するため、`layout_width`と`layout_height`に`match_parent`を指定してください。
`android:id="@+id/xxxx"`で、プログラムから参照する際のidを指定します。

```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/constraint_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".MainActivity">

	<ListView
        android:id="@+id/list_view"
        android:layout_width="match_parent"
        android:layout_height="match_parent" />

</android.support.constraint.ConstraintLayout>
```

### ListViewをプログラムから編集する
MainActivity.ktを編集します。
findViewById<>()という関数を使用し、xmlとkotlinのListViewを紐付けます。

```
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // xmlのListViewを取得します
        val listView = findViewById<ListView>(R.id.list_view)
        // Context.ktで作成したquestionsのキーの名前を取得します
        val items = Context.questions.keys.toTypedArray()
        // simple_list_item_1というListViewのテンプレートを使用します
        val adapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, items)
        listView.adapter = adapter
        // listViewのアイテムをクリックした時の動きを登録します
        listView.setOnItemClickListener { adapterView, view, i, l ->
            // Intentを使いQuizActivityを表示します
            val intent = Intent(this, QuizActivity::class.java)
            // ListViewの何番目がクリックされたのかをidという名前でquizActivityに渡します
            intent.putExtra("id", i)
            startActivity(intent)
        }
    }
}
```

## QuizActivity作成
### 画面作成
```
<?xml version="1.0" encoding="utf-8"?>
<android.support.constraint.ConstraintLayout
    xmlns:android="http://schemas.android.com/apk/res/android"
    xmlns:app="http://schemas.android.com/apk/res-auto"
    xmlns:tools="http://schemas.android.com/tools"
    android:id="@+id/constraint_layout"
    android:layout_width="match_parent"
    android:layout_height="match_parent"
    tools:context=".QuizActivity">

    <Button
        android:id="@+id/next"
        android:layout_width="match_parent"
        android:layout_height="match_parent"
        android:background="#00ffffff"
        android:text="" />

    <android.support.constraint.ConstraintLayout
        android:layout_width="match_parent"
        android:layout_height="match_parent">
        <ImageView
            android:id="@+id/imageView"
            android:layout_width="300dp"
            android:layout_height="300dp"
            android:layout_marginTop="20dp"
            tools:ignore="MissingConstraints"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            tools:srcCompat="@tools:sample/backgrounds/scenic" />

        <ImageView
            android:id="@+id/judge"
            android:layout_width="300dp"
            android:layout_height="300dp"
            android:layout_marginTop="20dp"
            tools:ignore="MissingConstraints"
            app:layout_constraintTop_toTopOf="parent"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            tools:srcCompat="@tools:sample/backgrounds/scenic" />

        <Button
            android:id="@+id/button1"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="Button"
            android:textColor="@color/colorButtonChar"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/imageView" />

        <Button
            android:id="@+id/button2"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="Button"
            android:textColor="@color/colorButtonChar"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/button1" />

        <Button
            android:id="@+id/button3"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="Button"
            android:textColor="@color/colorButtonChar"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/button2" />

        <Button
            android:id="@+id/button4"
            android:layout_width="200dp"
            android:layout_height="wrap_content"
            android:layout_marginTop="8dp"
            android:text="Button"
            android:textColor="@color/colorButtonChar"
            app:layout_constraintLeft_toLeftOf="parent"
            app:layout_constraintRight_toRightOf="parent"
            app:layout_constraintTop_toBottomOf="@+id/button3" />
    </android.support.constraint.ConstraintLayout>
</android.support.constraint.ConstraintLayout>
```


ボタンを下記のように配置することもできます

```
<Button
    android:id="@+id/button1"
    android:layout_width="140dp"
    android:layout_height="75dp"
    android:layout_marginTop="8dp"
    android:text="Button"
    android:textColor="@color/colorButtonChar"
    app:layout_constraintHorizontal_bias="0.18"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/imageView" />

<Button
    android:id="@+id/button2"
    android:layout_width="140dp"
    android:layout_height="75dp"
    android:layout_marginTop="8dp"
    android:text="Button"
    android:textColor="@color/colorButtonChar"
    app:layout_constraintHorizontal_bias="0.827"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/imageView" />

<Button
    android:id="@+id/button3"
    android:layout_width="140dp"
    android:layout_height="75dp"
    android:layout_marginTop="16dp"
    android:text="Button"
    android:textColor="@color/colorButtonChar"
    app:layout_constraintHorizontal_bias="0.18"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/button1" />

<Button
    android:id="@+id/button4"
    android:layout_width="140dp"
    android:layout_height="75dp"
    android:layout_marginTop="16dp"
    android:text="Button"
    android:textColor="@color/colorButtonChar"
    app:layout_constraintHorizontal_bias="0.827"
    app:layout_constraintLeft_toLeftOf="parent"
    app:layout_constraintRight_toRightOf="parent"
    app:layout_constraintTop_toBottomOf="@+id/button2" />
```


### 問題の正誤判定プログラム作成

```
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageView

class QuizActivity : AppCompatActivity(), View.OnClickListener {
    // 使用する問題を入れておく変数
    private lateinit var questions: ArrayList<ArrayList<String>>

    // 問題の数をカウントする変数
    private var index: Int = 0

    // 次の問題を表示するためのボタン
    private lateinit var next: Button

    // マル・バツを表示するImageView
    private lateinit var judge: ImageView

    // 正解を入れておく変数
    private var correct: String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_quiz)

        // MainActivityから`id`を取得
        val id = intent.getIntExtra("id", 0)
        // Context.ktで作成したquestionsのキーの名前を取得します
        val keys = Context.questions.keys.toTypedArray()
        // idとキーの名前から問題を取得します
        questions = Context.questions[keys[id]]!!
        // 毎回違う順番で問題を出すため、取得した問題の順番をシャッフルします
        questions.shuffle()

        next = findViewById(R.id.next)
        next.setOnClickListener {
            if (questions.count() <= index) {
                // アクティビティを終了します
                finish()
            } else {
                changeView(questions[index])
            }
        }

        judge = findViewById(R.id.judge)

        changeView(questions[index])
    }

    /**
     * 表示する問題を入れ替える関数
     * @param question
     */
    private fun changeView(question: ArrayList<String>) {
        // 問題用の画像を表示するImageViewを取得します
        val imageView = findViewById<ImageView>(R.id.imageView)

        // 選択肢1〜4までのボタンを取得します
        val btn1 = findViewById<Button>(R.id.button1)
        val btn2 = findViewById<Button>(R.id.button2)
        val btn3 = findViewById<Button>(R.id.button3)
        val btn4 = findViewById<Button>(R.id.button4)

        // 次の問題を表示するボタンを無効にし、マル・バツを透明にします
        next.isEnabled = false
        judge.alpha = 0f

        // 問題の正解を入れておきます
        // 配列の0番目を正解とします
        correct = question[0]
        // 正解と同じ名前の画像を取得し、ImageViewに表示します
        val resId = resources.getIdentifier(correct, "drawable", packageName)
        imageView.setImageResource(resId)

        // 問題をシャッフルしボタンに表示します
        question.shuffle()
        btn1.text = question[0]
        btn2.text = question[1]
        btn3.text = question[2]
        btn4.text = question[3]

        // ボタンをタップした時の動作を登録します
        btn1.setOnClickListener(this)
        btn2.setOnClickListener(this)
        btn3.setOnClickListener(this)
        btn4.setOnClickListener(this)
    }

    /**
     * 選択肢のボタンをタップした際に呼び出される関数
     */
    override fun onClick(p0: View?) {
        // タップされたボタンの文字を取得します
        val answer = (p0 as Button).text as String

        // 正誤判定をし、マル・バツの画像をjudgeにセットします
        if (answer == correct) {
            judge.setImageResource(R.drawable.correct)
        } else {
            judge.setImageResource(R.drawable.wrong)
        }

        // 問題のカウントを+1します
        index += 1
        // 透明にしていたマル・バツの画像を表示します
        judge.alpha = 1f
        // 次の問題を表示するボタンを有効にします
        next.isEnabled = true
    }
}
```


## 用語の解説
プロジェクト：
期限や目標が設定されている特定の目的や目的物のこと。

ビルド：
ソースコードのコンパイルなどを行い、実行可能なファイルを作成すること。

Kotlin：
Javaコードと相互運用できるよう設計されたプログラミング言語。Androidの公式言語に追加されている。

Java：
C言語の構文を主に引き継いだプログラミング言語。OSに依存することなくどんな環境でもソフトを動かすことができ、多くの開発現場で使用される。

コンパイル：
ソースコードを解析し、コンピュータが直接実行可能な形式のプログラムに変換すること。

クラス：
オブジェクト指向プログラミングでのオブジェクトの設計図(雛形)のこと。
何らかの階級や分類を表す名称としても使われる。

配列：
複数のデータを連続的に並べたデータ構造。各データをその配列の要素といい、自然数などの添字(インデックス)で識別される。

ハッシュ：
ある一つのデータからそのデータを代表する数値を得るための操作のこと。その数値を得るための関数をハッシュ関数といい、そこから得られた数値のことをハッシュ値と言う。
