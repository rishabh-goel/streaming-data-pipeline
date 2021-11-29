import io.netty.util.internal.shaded.org.jctools.queues.MessagePassingQueue.Consumer
import java.nio.file.StandardWatchEventKinds.{ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY}
import java.io.File
import java.io.IOException
import java.nio.file.ClosedWatchServiceException
import java.nio.file.FileSystems
import java.nio.file.Path
import java.nio.file.Paths
import java.nio.file.WatchEvent
import java.nio.file.WatchKey
import java.nio.file.WatchService
import java.util.ArrayList
import java.util.Collections
import java.util.List;
object Watcher extends App {

//  val watchService = FileSystems.getDefault.newWatchService()
//  Paths.get("/Users/ameykasbe/Desktop/kafka-spark/documents").register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)

//  while(true) {
//    val key = watchService.take()
//    key.pollEvents().forEach( event => {
//      print(event)
//    })
//    key.reset()
//  }

  while(true) {
    val watchService = FileSystems.getDefault.newWatchService()
    Paths.get("/Users/ameykasbe/Desktop/kafka-spark/documents").register(watchService, ENTRY_CREATE, ENTRY_DELETE, ENTRY_MODIFY)
    val key = watchService.take()

    if(!key.pollEvents().isEmpty){
      print("Hello World")
    }
  }
}