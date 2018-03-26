//Main.kt
import com.google.gson.Gson
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

fun main(args: Array<String>) {
    val server = embeddedServer(Netty, port = 8080) {
        routing {
            get("/") {
                call.respondText("Hello World!", ContentType.Text.Plain)
            }
            get("/demo") {
                call.respondText("HELLO WORLD!")
            }
            get("/api/ping/{count?}") {
                var count: Int = Integer.valueOf(call.parameters["count"]?: "1")
                if (count < 1) {
                    count = 1
                }
                var obj = Array<Entry>(count, {i -> Entry("$i: Hello, World!")})
                val gson = Gson()
                var str = gson.toJson(obj)
                call.response.header("Access-Control-Allow-Origin", "*")
                call.respondText(str, ContentType.Application.Json)

            }
        }
    }
    server.start(wait = true)
}

data class Entry(val message: String)