import java.util.Date;

public class TokenBucket {
    private final double tokencapacity;
    private double tokens;
    private final double timerate;
    private double timestamp;

    TokenBucket(double capacity, long rate) {
        this.tokencapacity = capacity;
        this.timerate = rate;
        this.tokens = tokencapacity;
        this.timestamp = new Date().getTime();
    }

    public double getTokens() {
        if (this.tokens < tokencapacity) {
            long now = new Date().getTime();
            double delta = now  - timestamp;
            this.tokens = Math.min(tokencapacity, tokens + (tokencapacity * delta/timerate));
            this.timestamp = now;
        }
        return this.tokens;
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
