package net.lmlab.four_choice_question.four_choice_question

class Context {
    companion object {
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