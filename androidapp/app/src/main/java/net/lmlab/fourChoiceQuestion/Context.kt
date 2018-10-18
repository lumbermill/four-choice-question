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