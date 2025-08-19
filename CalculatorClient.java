import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorClient {
    public static void main(String[] args) {
        String host = (args.length > 0) ? args[0] : "localhost";
        int port = (args.length > 1) ? Integer.parseInt(args[1]) : 1099;

        try {
            Registry registry = LocateRegistry.getRegistry(host, port);
            Calculator calc = (Calculator) registry.lookup("CalculatorService");
  
         // Testing the GCD Operation 
            calc.pushValue(25);
            calc.pushValue(50);
            calc.pushValue(75);
            calc.pushOperation("gcd"); 
            System.out.println("The result for GCD Operation: " + calc.pop());

            // Testing the Max Operation 
            calc.pushValue(32);
            calc.pushValue(76);
            calc.pushValue(59);
            calc.pushOperation("max"); 
            System.out.println("The result for Max Operation: " + calc.pop());

            // Testing the LCM Operation 
            calc.pushValue(42);
            calc.pushValue(6);
            calc.pushOperation("lcm"); 
            System.out.println("The result for LCM Operation: " + calc.pop());

            // Testing the DelayPop Operation
            calc.pushValue(72);
            System.out.println("The result for delayPop(1000): " + calc.delayPop(1000));


        } catch (Exception ex) {
            System.err.println(" found an issue: " + ex.getMessage());
            ex.printStackTrace();
        }
    }
}
