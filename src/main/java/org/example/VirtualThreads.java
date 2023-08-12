import java.time.Duration;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.IntStream;

/**
 * try JEP 444: Virtual Threads (will be added to JAVA 21)
 * Virtual threads are a lightweight implementation of threads that is provided by the JDK rather than the OS.
 * They are a form of user-mode threads
 * 
 * @author Mohamed Elsawy
 */

public class VirtualThreads {

    public static void main(String[] args) throws InterruptedException {
        // create a new unstarted virtual thread using Thread Builder
        Thread vThread = Thread.ofVirtual().name("vThread1").unstarted(() ->
                System.out.println("1. Hello from " + Thread.currentThread().toString()));
        vThread.start();
        System.out.println("virtual? " + vThread.isVirtual());
        vThread.join();

        // create and start virtual thread using Thread Builder
        vThread = Thread.ofVirtual().name("vThread2").start(() ->
                System.out.println("2. Hello from " + Thread.currentThread().toString()));
        vThread.join();

        // another way to create and then start a virtual thread
        vThread = Thread.startVirtualThread(() ->
                System.out.println("3. Hello from " + Thread.currentThread().toString()));
        vThread.join();


        try (ExecutorService vThreadPerTaskExecutor = Executors.newVirtualThreadPerTaskExecutor()) {
            IntStream.range(0, 10_000).forEach(i -> {
                        vThreadPerTaskExecutor.submit(() -> {
                            Thread.sleep(Duration.ofSeconds(1));
                            System.out.println("4. Hello from " + Thread.currentThread().toString());
                            return i;
                        });
                    }
            );
        }

        
        // virtual thread use different ForkJoinPool than default ForkJoinPool(commonPool) used by CompletableFuture
        CompletableFuture.runAsync(() ->{
            System.out.println("5. Hello from " + Thread.currentThread().toString());
        }).join();
        
        // you can also create new ForkJoinPool
        try (var executor = Executors.newWorkStealingPool()){
            executor.submit(() ->{
                System.out.println("6. Hello from " + Thread.currentThread().toString());
            });
        }
        
    }
}