import kotlinx.coroutines.*
import java.util.concurrent.Executors

suspend fun handleCustomDispatcher() {
    println("ThreadID1 = ${Thread.currentThread().id}")
    val threadPool1 = Executors.newScheduledThreadPool(1) { runnable ->
        val t = Thread(runnable, "CustomThreadPool")
        t.isDaemon = true
        t
    }.asCoroutineDispatcher()

    val threadPool2 = Executors.newScheduledThreadPool(1) { runnable ->
        val t = Thread(runnable, "CustomThreadPool")
        t.isDaemon = true
        t
    }.asCoroutineDispatcher()

    CoroutineScope(threadPool1 + SupervisorJob()).launch(Dispatchers.Default) {
        println("ThreadID2 = ${Thread.currentThread().id}")
        withContext(threadPool2) {
            println("ThreadID3 = ${Thread.currentThread().id}")
        }
        println("ThreadID4 = ${Thread.currentThread().id}")
    }.join()
}