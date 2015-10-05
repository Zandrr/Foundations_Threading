import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {


  private Lock lock = new ReentrantLock();
  private ProductionLine queue;
  private int id;

  public Producer(int id, ProductionLine queue) {
    this.id    = id;
    this.queue = queue;
  }

  public void run() {
    for(int count = 0; count<20; count++){
      Boolean enqueued = false;
      while(!enqueued){
        if (queue.size() < 10) {
          Product p = new Product();
          String msg = "Producer %d Produced: %s on iteration %d";
          System.out.println(String.format(msg, id, p, count));
            queue.append(p);
            enqueued = true;
        }else{
          try{
            Thread.sleep(100);
          }catch (InterruptedException e) {
            Thread.currentThread().interrupt();
          }
        }
      }
    }
    Product p = new Product();
    p.productionDone();
          queue.append(p);

    String msg = "Producer %d is done. Shutting down.";
    System.out.println(String.format(msg, id));
  }

}
