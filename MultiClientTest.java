import static org.junit.jupiter.api.Assertions.*;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import org.junit.jupiter.api.Test;

class MultiClientTest {

    @Test
    void testMultiClientOperations() throws InterruptedException {
        int clients = 3;
        Thread[] threads = new Thread[clients];
        final int[] results = new int[clients];

        for (int i = 0; i < clients; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                    Calculator calc = (Calculator) registry.lookup("CalculatorService");

                    calc.pushValue(121 + id);
                    calc.pushValue(204 + id);
                    calc.pushValue(111 + id);
                    calc.pushOperation("min");
                    try {
                        results[id] = calc.pop();
                        System.out.println("Result: " + results[id]);
                    } catch (Exception e) {
                        System.out.println("[ Stack was empty, no result found.");
                        results[id] = -1; 
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    results[id] = -1; 
                }
            }, "Client-" + i);
            threads[i].start();
        }

        for (Thread t : threads) t.join();

        boolean success = false;
        for (int r : results) if (r != -1) success = true;
        assertTrue(success, "No client could get a result due to empty stack.");

        System.out.println("Multi-client JUnit test completed.");
    }
}
