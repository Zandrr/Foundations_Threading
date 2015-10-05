import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.*;

public class ProductionLine {

   private List<Product> products;
   private Lock lock = new ReentrantLock();
   private Condition notFull = lock.newCondition();
   private Condition notEmpty = lock.newCondition();

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

   public void append(Product p) throws InterruptedException {
    lock.lock();
    try{
      while(products.size() >= 10){
        notFull.await();
      }
     products.add(p);
     notEmpty.signal();
    }finally{
      lock.unlock();
    }
   }

   public Product retrieve() throws InterruptedException {
    Product product;
    lock.lock();
    try{
      while(products.isEmpty()){
        notEmpty.await();
      }
     product = products.remove(0);
     notFull.signal();
    }finally{
      lock.unlock();
    }
      return product;
   }

}
