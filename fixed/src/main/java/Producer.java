import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class Producer implements Runnable {


  private static Lock lock = new ReentrantLock();
  private ProductionLine queue;
  private int id;

  public Producer(int id, ProductionLine queue) {
    this.id    = id;
    this.queue = queue;
  }

  public void run() {
    for(int count = 0; count<20; count++){
          Product p;
          lock.lock();
          try{
            p = new Product(); // make each producer take a turn on creating products.  One lock shared between all threads (static).
          }finally{
            lock.unlock();
          }
          String msg = "Producer %d Produced: %s on iteration %d";
          System.out.println(String.format(msg, id, p, count));
          try{
            queue.append(p);
          }catch(InterruptedException e){
            //print
          }
    }
    Product p = new Product();
    p.productionDone();
        try{
          queue.append(p);
        }catch(InterruptedException e){
          //print
        }

    String msg = "Producer %d is done. Shutting down.";
    System.out.println(String.format(msg, id));
  }

}
