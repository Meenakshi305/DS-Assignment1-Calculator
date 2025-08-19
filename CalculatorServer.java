import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorServer {
    public static void main(String[] args) {
        try {
            // Start / get registry on port 1099
            Registry registry;
            try {
                registry = LocateRegistry.createRegistry(1099);
                System.out.println("The Server rmiregistry started on port 1099");
            } catch (Exception e) {
                registry = LocateRegistry.getRegistry(1099);
                System.out.println("The Server rmiregistry already running on port 1099");
            }

            // Bind service
            CalculatorImplementation service = new CalculatorImplementation();
            registry.rebind("CalculatorService", service);
            System.out.println("The Server is ready.");
        } catch (Exception e) {
            System.err.println(" Found an Exception: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
