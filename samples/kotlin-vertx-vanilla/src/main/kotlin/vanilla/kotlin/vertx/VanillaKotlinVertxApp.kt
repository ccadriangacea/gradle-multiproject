package vanilla.kotlin.vertx

import io.vertx.core.Vertx

class VanillaKotlinVertxApp(private val args: Array<String>) {
    private val vertx = Vertx.vertx()
    fun run() {
        println("Starting simple vertx[$vertx] app with args: $args")
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            VanillaKotlinVertxApp(args).run()
        }
    }
}
