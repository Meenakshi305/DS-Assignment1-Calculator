import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class MultiClientTest {
    public static void main(String[] args) throws InterruptedException {
        int clients = 5;
        Thread[] threads = new Thread[clients];

        for (int i = 0; i <= clients; i++) {
            final int id = i;
            threads[i] = new Thread(() -> {
                try {
                    Registry registry = LocateRegistry.getRegistry("localhost", 1099);
                    Calculator calc = (Calculator) registry.lookup("CalculatorService");

                    calc.pushValue(121 + id);
                    calc.pushValue(204 + id);
                    calc.pushValue(78 + id);
                    calc.pushValue(21 + id);
                    calc.pushValue(76 + id);
                    calc.pushOperation("min");

                    int result = calc.pop();
                    if (result == Integer.MIN_VALUE) {
                        System.out.println("[Client-" + id + "] Stack was empty, no result.");
                    } else {
                        System.out.println("[Client-" + id + "] Result: " + result);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }, "Client-" + i);
            threads[i].start();
        }

        for (Thread t : threads) t.join();
        System.out.println("Multi-client test completed.");
    }
}
