package arunkbabu90.chat


data class Message(val msg: String = "",
                   val name: String = "") {
    companion object {
        const val TYPE_YOU = 4000
    }
}