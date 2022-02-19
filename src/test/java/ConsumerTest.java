import org.junit.Test;

import static org.junit.Assert.*;

public class ConsumerTest {
    @Test
    public void testCheckConsumeGretterCap() {
        TokenBucket cons = new TokenBucket(1, 100);
        assertFalse(cons.Consume(2));
    }

    @Test
    public void testCheckConsumePos() {
        TokenBucket cons = new TokenBucket(1, 100);
        assertTrue(cons.Consume(1));
    }

    @Test
    public void testCheckConsumeSleepPos() throws InterruptedException {
        TokenBucket cons = new TokenBucket(1, 100);
        cons.Consume(1);
        Thread.sleep(101);
        assertTrue(cons.Consume(1));
    }

    @Test
    public void testCheckConsumeSleepNeg() throws InterruptedException {
        TokenBucket cons = new TokenBucket(10, 100);
        cons.Consume(10);
        Thread.sleep(80);
        assertFalse(cons.Consume(10));
    }
}
