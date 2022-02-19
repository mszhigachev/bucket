import org.junit.Test;
import static org.junit.Assert.*;

public class ConsumerTest {
    @Test
    public void testCheckConsumeGretterCap(){
        TokenBucket cons = new TokenBucket(1,1000);
        assertFalse(cons.Consume(2));
    }
    @Test
    public void testCheckConsumePos(){
        TokenBucket cons = new TokenBucket(1,1000);
        assertTrue(cons.Consume(1));
    }
    @Test
    public void testCheckConsumeSleepPos() throws InterruptedException {
        TokenBucket cons = new TokenBucket(1,1000);
        cons.Consume(1);
        Thread.sleep(1001);
        assertTrue(cons.Consume(1));
    }
    @Test
    public void testCheckConsumeSleepNeg() throws InterruptedException {
        TokenBucket cons = new TokenBucket(1,1000);
        cons.Consume(1);
        Thread.sleep(980);
        assertFalse(cons.Consume(1));
    }

}
