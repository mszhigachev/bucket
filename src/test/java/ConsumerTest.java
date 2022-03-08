import org.junit.Test;

import static org.junit.Assert.*;

public class ConsumerTest {
    @Test
    public void testCheckConsumeGretterCap() {
        TokenBucket cons = new TokenBucket(1, 100);
        assertFalse(cons.consume(2));
    }

    @Test
    public void testCheckConsumePos() {
        TokenBucket cons = new TokenBucket(1, 100);
        assertTrue(cons.consume(1));
    }

    @Test
    public void testCheckConsumeSleepPos() throws InterruptedException {
        TokenBucket cons = new TokenBucket(1, 100);
        cons.consume(1);
        Thread.sleep(101);
        assertTrue(cons.consume(1));
    }

    @Test
    public void testCheckConsumeSleepNeg() throws InterruptedException {
        TokenBucket cons = new TokenBucket(1, 100);
        cons.consume(1);
        Thread.sleep(80);
        assertFalse(cons.consume(1));
    }

    @Test
    public void testMultiBucketGretterCapNeg() throws Exception {
        Config config = new Config(10,100);
        Config configg = new Config(20,100);
        BucketManager multiBucket = new BucketManager.Builder().addConfiguration(config).addConfiguration(configg).build();
//        System.out.println(multiBucket.toString());
        assertFalse(multiBucket.consume(21));
    }

    @Test
    public void testMultiBucketLessCapNeg() throws Exception {
        Config config = new Config(10,100);
        Config configg = new Config(20,100);
        BucketManager multiBucket = new BucketManager.Builder().addConfiguration(config).addConfiguration(configg).build();
//        System.out.println(multiBucket.toString());
        assertFalse(multiBucket.consume(19));
    }

    @Test
    public void testMultiBucketLessCapPos() throws Exception {
        Config config = new Config(10,100);
        Config configg = new Config(20,100);
        BucketManager multiBucket = new BucketManager.Builder().addConfiguration(config).addConfiguration(configg).build();
//        System.out.println(multiBucket.toString());
        assertTrue(multiBucket.consume(9));
    }

    @Test
    public void testMultiBucketSleepPos() throws Exception {
        Config config = new Config(10,100);
        Config configg = new Config(20,100);
        BucketManager multiBucket = new BucketManager.Builder().addConfiguration(config).addConfiguration(configg).build();
//        System.out.println(multiBucket.toString());
        assertTrue(multiBucket.consume(10));
        Thread.sleep(150);
        assertTrue(multiBucket.consume(10));
    }

    @Test
    public void testMultiBucketSleepNeg() throws Exception {
        Config config = new Config(10,100);
        Config configg = new Config(20,100);
        BucketManager multiBucket = new BucketManager.Builder().addConfiguration(config).addConfiguration(configg).build();
//        System.out.println(multiBucket.toString());
        assertTrue(multiBucket.consume(10));
        Thread.sleep(50);
        assertFalse(multiBucket.consume(10));
    }
}
