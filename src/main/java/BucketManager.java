import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class BucketManager {
    private List<Config> configs;
    private List<TokenBucket> bucketList;

    BucketManager() {
        this.configs = new ArrayList<>();
        this.bucketList = new ArrayList<>();
    }

    public boolean consume(int count) {
        if (bucketList.stream().allMatch(bucket -> count <= bucket.getTokens())) {
            bucketList.forEach(bucket -> bucket.consume(count));
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        return "BucketManager{" +
                "bucketList=" + bucketList +
                '}';
    }

    public static class Builder {
        private BucketManager manager;

        Builder() {
            manager = new BucketManager();
        }

        public Builder addConfiguration(Config config) {
            manager.configs.add(config);
            return this;
        }

        public BucketManager build() throws Exception {
            if (manager.configs.size() > 0) {
                this.manager.bucketList = manager
                        .configs
                        .stream()
                        .map(config->new TokenBucket(config.getCapacity(),config.getRate()))
                        .collect(Collectors.toList());
                return this.manager;
            }
            throw new Exception("Empty config list");
        }
    }
}

class TokenBucket {
    private final double tokencapacity;
    private final double rate;
    private double tokens;
    private long timestamp;

    TokenBucket(double capacity, long rate) {
        this.tokencapacity = capacity;
        this.rate = capacity / rate;
        this.tokens = capacity;
        this.timestamp = new Date().getTime();
    }

    public double getTokens() {
        if (tokens < tokencapacity) {
            long now = new Date().getTime();
            double delta = rate * (now - timestamp);
            this.tokens = Math.min(tokencapacity, tokens + delta);
            this.timestamp = now;
        }
        return this.tokens;
    }

    public boolean consume(int count) {
        if (count <= getTokens()) {
            this.tokens = tokens - count;
            return true;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "TokenBucket{" +
                "tokencapacity=" + tokencapacity +
                ", tokens=" + tokens +
                ", rate=" + rate +
                ", timestamp=" + timestamp +
                '}';
    }
}
