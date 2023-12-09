@Volatile
var counter = 0

val lock = Object()

fun startPrintTask() {
    val t1 = Thread(PrintTask(0))
    val t2 = Thread(PrintTask(1))
    val t3 = Thread(PrintTask(2))
    t1.start()
    t2.start()
    t3.start()
}

class PrintTask(private val threadId: Int) : Runnable {

    override fun run() {
        while (counter <= 100) {
            synchronized(lock) {
                if (counter <= 100) {
                    if (counter % 3 == threadId) {
                        println("ThreadId=$threadId, counter=${counter}")
                        counter++
                    }
                    lock.notifyAll()
                }
            }
        }
    }
}