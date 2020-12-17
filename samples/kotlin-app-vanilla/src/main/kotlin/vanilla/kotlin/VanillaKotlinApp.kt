package vanilla.kotlin

class VanillaKotlinApp(private val args: Array<String>) {
    fun run() {
        println("Starting simple kotlin app with args: $args")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            VanillaKotlinApp(args).run()
        }
    }
}