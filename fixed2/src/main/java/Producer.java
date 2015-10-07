import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {

  private ProductionLine queue;
  private int id;
  private int count = 0;
  private static Lock lock = new ReentrantLock();


  public Producer(int id, ProductionLine queue) {
    this.id    = id;
    this.queue = queue;
  }

  public void run() {
    while (count < 20) {
      lock.lock();
      try{

        if (queue.size() < 10) {
          Product p;
          lock.lock();
          try{
            p = new Product();
          }finally{
            lock.unlock();
          }
          String msg = "Producer %d Produced: %s on iteration %d";
          System.out.println(String.format(msg, id, p, count));
          queue.append(p);
          count++;
        }
      }finally{
        lock.unlock();
      }
    }
    Product p = new Product();
    p.productionDone();
    queue.append(p);
    String msg = "Producer %d is done. Shutting down.";
    System.out.println(String.format(msg, id));
  }

}
