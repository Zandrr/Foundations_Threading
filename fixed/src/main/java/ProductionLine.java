import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.Lock;

public class ProductionLine {

   private List<Product> products;
   private Lock lock = new ReentrantLock();

   public ProductionLine() {
     products = new LinkedList<Product>();
   }

   public int size() {
    lock.lock();
    try {
     return products.size();
    }finally {
      lock.unlock();
    }
   }

   public void append(Product p) {
    lock.lock();
    try{
     products.add(p);
    }finally{
      lock.unlock();
    }
   }

   public Product retrieve() {
    lock.lock();
    try{
     return products.remove(0);
    }finally{
      lock.unlock();
    }
   }

}
