// JUnit Test
import org.junit.jupiter.api.*;
import static org.junit.jupiter.api.Assertions.*;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

public class CalculatorTest {
    private static Calculator calcOperation;

    @BeforeAll
    public static void setup() throws Exception {
        Registry registry = LocateRegistry.getRegistry("localhost", 1099);
        calcOperation = (Calculator) registry.lookup("CalculatorService");
    }

    @BeforeEach
    public void clearStackIfNeeded() throws Exception {
    	boolean isStackEmpty= calcOperation.isEmpty();
        while (!isStackEmpty) {
            try { calcOperation.pop(); } 
            catch (Exception ignored) { break; }
        }
    }

    @Test
    public void pushAndPopOperation() throws Exception {
        calcOperation.pushValue(77);
        assertFalse(calcOperation.isEmpty());
        assertEquals(77, calcOperation.pop());
        assertTrue(calcOperation.isEmpty());
    }
    // Test Case for GCD Operation
    @Test
    public void toTestGCDOperation() throws Exception {
        calcOperation.pushValue(121);
        calcOperation.pushValue(33);
        calcOperation.pushValue(99);
        calcOperation.pushOperation("gcd");
        assertEquals(11, calcOperation.pop());
    }
    // Test Case for Max Operation
    @Test
    public void toTestMaxOperation() throws Exception {
        calcOperation.pushValue(28);
        calcOperation.pushValue(35);
        calcOperation.pushValue(97);
        calcOperation.pushOperation("max");
        assertEquals(97, calcOperation.pop());
    }
    
    // Test Case for Min Operation 
    @Test
    public void toTestMinoperation() throws Exception {
        calcOperation.pushValue(84);
        calcOperation.pushValue(36);
        calcOperation.pushValue(121);
        calcOperation.pushOperation("min");
        assertEquals(36, calcOperation.pop());
    }
    // Test Case for LCM Operation
    @Test
    public void toTestLCMoperation() throws Exception {
        calcOperation.pushValue(16);
        calcOperation.pushValue(124);
        calcOperation.pushValue(111);
        calcOperation.pushOperation("lcm"); 
        assertEquals(55,056, calcOperation.pop());
    }
    // Test Case for Delay Operation
    @Test
    public void toTestdelayPop() throws Exception {
        calcOperation.pushValue(14);
        long start = System.currentTimeMillis();
        int result = calcOperation.delayPop(100);
        long elapsed = System.currentTimeMillis() - start;
        assertEquals(14, result);
        assertTrue(elapsed >= 80, "delay should be at least ~80ms");
    }
}
