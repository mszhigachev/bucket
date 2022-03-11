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
        Config config1 = new Config(32,100);
        Config config2 = new Config(22,100);
        Config config3 = new Config(14,100);
        Config config4 = new Config(24,100);
        Config config5 = new Config(210,100);
        Config config6 = new Config(250,100);
        Config config7 = new Config(210,100);
        Config config8 = new Config(120,100);

        BucketManager multiBucket = new BucketManager
                .Builder()
                .addConfiguration(config)
                .addConfiguration(config1)
                .addConfiguration(config2)
                .addConfiguration(config3)
                .addConfiguration(config4)
                .addConfiguration(config5)
                .addConfiguration(config6)
                .addConfiguration(config7)
                .addConfiguration(config8)
                .build();
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
        multiBucket.consume(10);
        Thread.sleep(150);
        assertTrue(multiBucket.consume(10));
    }

    @Test
    public void testMultiBucketSleepNeg() throws Exception {
        Config config = new Config(10,100);
        Config configg = new Config(20,100);
        BucketManager multiBucket = new BucketManager.Builder().addConfiguration(config).addConfiguration(configg).build();
//        System.out.println(multiBucket.toString());
        multiBucket.consume(10);
        assertFalse(multiBucket.consume(10));
    }
}
