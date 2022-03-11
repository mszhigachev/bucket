import java.awt.image.AreaAveragingScaleFilter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public final class BucketManager {
    private final List<Config> configs;
    private final List<TokenBucket> bucketList;

    private BucketManager(List<Config> configs, List<TokenBucket> bucketList) {
        this.configs = configs;
        this.bucketList = bucketList;
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
        private List<Config> configs;
        private List<TokenBucket> bucketList;

        Builder() {
            this.configs = new ArrayList<>();
            this.bucketList = new ArrayList<>();
        }

        public Builder addConfiguration(Config config) {
            this.configs.add(config);
            return this;
        }

        public BucketManager build() throws Exception {
            if (this.configs.size() > 0) {
                this.bucketList = configs
                        .stream()
                        .map(config->new TokenBucket(config.getCapacity(),config.getRate()))
                        .collect(Collectors.toList());
                return new BucketManager(configs,bucketList);
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
