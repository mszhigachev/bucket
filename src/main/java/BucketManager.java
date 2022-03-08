import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class BucketManager{
    private List<Config> config;
    List<TokenBucket> bucketList;

    BucketManager(){
        this.config  = new ArrayList<>();
        this.bucketList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "BucketManager{" +
                "bucketList=" + bucketList +
                '}';
    }
    public boolean consume(int count){
        boolean consumed = bucketList.stream().allMatch(bucket -> count <= bucket.getTokens());
        if(consumed){
            bucketList.forEach(bucket -> bucket.consume(count));
            return true;
        }
        return false;
    }

    public static class Builder{
        private BucketManager manager;
        Builder(){
            manager = new BucketManager();
        }

        public Builder addConfiguration(Config config){
            manager.config.add(config);
            return this;
        }
        public BucketManager build() throws Exception {
            if(manager.config.size() > 0){
                for(int i=0;i<manager.config.size();i++){
                    manager.bucketList.add(new TokenBucket(manager.config.get(i).getCapacity(),manager.config.get(i).getRate()));
                }

                return manager;
            }
          throw new Exception("Empty config list");
        }
    }
}

class TokenBucket {
    private final double tokencapacity;
    private double tokens;
    private final double rate;
    private long timestamp;

    TokenBucket(double capacity, long rate) {
        this.tokencapacity = capacity;
        this.rate = capacity/rate;
        this.tokens = tokencapacity;
        this.timestamp = new Date().getTime();
    }

    public double getTokens() {
        if (this.tokens < tokencapacity) {
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
