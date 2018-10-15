# Androidでクイズアプリ構築
2018.10.16

Android Studioのインストールについては、別紙(setup.key)を参照してください。
本解説は、Android Studio 3.2.1をベースに作成しています。
スクリーンショットが一部、3.1.4ベースになっていますが、相違点がある場合は明記します。

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

MainActivity.kt 上で右クリックし「File -> Kotlin File/Class」を選択し、
QuizActivitiy.ktを作成します。

TODO: QuizActivity.ktに書き足すのは何？

## ボタンを作る

TODO: activity_quiz.xmlの作り方？編集の仕方


## 画像を配置する
必要な画像は、自分たちで集めて貰ってもOKですが、著作権に注意してください。
今回のサンプルアプリで使用している画像は、以下のURLからダウンロードできます。
（URLが長いので、まずはgithub.comにアクセスしたあと、four-choice-questionリポジトリを検索した方が確実かもしれません）

https://github.com/lumbermill/four-choice-question/tree/master/assets/images

以下のような著作権フリーの素材から選んできても構いません。

https://www.irasutoya.com/


画像を取得したら

TODO: drawableに置く必然性はある？res/imagesとか？


## クイズのデータを作成する

TODO: Context.ktの作成


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


## 正誤判定を実装する


## 用語の解説
プロジェクト？

ビルド？

Kotlin

Java

クラス？

配列？

ハッシュ
