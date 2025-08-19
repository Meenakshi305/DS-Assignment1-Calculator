import java.rmi.server.UnicastRemoteObject;
import java.rmi.RemoteException;
import java.util.Stack;

/**
 * Thread-safe implementation that maintains a single shared stack for all clients.
 * All public methods are synchronized to ensure correctness with concurrent clients.
 */
public class CalculatorImplementation extends UnicastRemoteObject implements Calculator {

    private final Stack<Integer> stack;

    protected CalculatorImplementation() throws RemoteException {
        super();
        this.stack = new Stack<>();
    }

    @Override
    public synchronized void pushValue(int val) throws RemoteException {
        stack.push(val);
        System.out.println("the value that is pushed (" + val + ")  and the length of the stack is : " + stack.size());
    }

    @Override
    public synchronized void pushOperation(String operator) throws RemoteException {

        if (stack.isEmpty()) {
            System.out.println("the provided (" + operator + ") cannot be performed because the stack is empty");
            return;
        }

        int result = stack.pop();
        while (!stack.isEmpty()) {
            int next = stack.pop();
            switch (operator) {
                case "min":
                    result = Math.min(result, next);
                    break;
                case "max":
                    result = Math.max(result, next);
                    break;
                case "gcd":
                    result = gcd(result, next);
                    break;
                case "lcm":
                    result = lcm(result, next);
                    break;
                default:
                    throw new RemoteException("Please provide a  valid Operation: " + operator);
            }
        }
        stack.push(result);
        System.out.println("The operation used (" + operator + ") and the result pushed: " + result);
    }

    @Override
    public synchronized int pop() throws RemoteException {
        if (stack.isEmpty()) {
            throw new RemoteException("Stack is empty");
        }
        int val = stack.pop();
        System.out.println("the value that is poped " + val + " and length of the stack is: " + stack.size() + " ");
        return val;
    }

    @Override
    public synchronized boolean isEmpty() throws RemoteException {
    	boolean isStackEmpty = stack.isEmpty();
        return isStackEmpty;
    }

    @Override
    public int delayPop(int millis) throws RemoteException {
        try {
            Thread.sleep(millis);
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            throw new RemoteException("Delay interrupted", ex);
        }

        synchronized (this) {
            return pop();
        }
    }

    private int gcd(int num1, int num2) {
        num1 = Math.abs(num1);
        num2 = Math.abs(num2);
        while (num2 != 0) {
            int value = num2;
            num2 = num1 % num2;
            num1 = value;
        }
        return num1;
    }

    private int lcm(int num1, int num2) {
        num1 = Math.abs(num1);
        num2 = Math.abs(num2);
        if (num1 == 0 || num2 == 0) return 0;
        return (num1 / gcd(num1, num2)) * num2;
    }
}
