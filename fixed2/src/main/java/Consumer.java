import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Consumer implements Runnable {

  public static ConcurrentHashMap<Integer,Product> products =
    new ConcurrentHashMap<Integer,Product>();

  private static Lock lock = new ReentrantLock();


  private int id;
  private ProductionLine queue;

  public Consumer(int id, ProductionLine queue) {
    this.id    = id;
    this.queue = queue;
  }

  public void run() {
    while (true) {
      lock.lock();
      try{ 
        if (queue.size() > 0) {
          Product p = queue.retrieve();
          if (p.isDone()) {
            String msg = "Consumer %d received done notification. Goodbye.";
            System.out.println(String.format(msg, id));
            return;
          } else {
            products.put(p.id(), p);
            String msg = "Consumer %d Consumed: %s";
            System.out.println(String.format(msg, id, p));
          }
        }
      }finally{
        lock.unlock();
      }
    }
  }
}
