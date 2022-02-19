import java.util.Date;

public class TokenBucket {
    private final double tokencapacity;
    private double tokens;
    private final long timerate;
    private long timestamp;

    TokenBucket(double capacity, long rate) {
        this.tokencapacity = capacity;
        this.timerate = rate;
        this.tokens = tokencapacity;
        this.timestamp = new Date().getTime();
    }

    public double getTokens() {
        if (tokens < tokencapacity) {
            long now = new Date().getTime();
            double delta = timerate / 1000 * (now / 1000 - timestamp / 1000);
            this.tokens = Math.min(tokencapacity, (tokens + delta));
            this.timestamp = now;
        }
        return tokens;
    }

    public boolean Consume(int count) {
        double availTokens = getTokens();
        if (count <= availTokens) {
            this.tokens = availTokens - count;
            return true;
        } else {
            return false;
        }
    }

}
