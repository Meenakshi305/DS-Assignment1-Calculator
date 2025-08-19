import java.rmi.Remote;
import java.rmi.RemoteException;

public interface Calculator extends Remote {
    // Push a value into the stack
    void pushValue(int val) throws RemoteException;

    // Push an operation ("min", "max", "lcm", "gcd") 
    void pushOperation(String operator) throws RemoteException;

    // It Pop's the top value from the stack
    int pop() throws RemoteException;

    // Check if the stack is empty
    boolean isEmpty() throws RemoteException;

    // Wait for  milliseconds and then pop the top value from the stack
    int delayPop(int millis) throws RemoteException;
}
