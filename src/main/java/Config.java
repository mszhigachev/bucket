public class Config {
    private final double capacity;
    private final long rate;

    Config(double capacity,long rate){
        this.capacity = capacity;
        this.rate = rate;
    }

    public double getCapacity() {
        return this.capacity;
    }

    public long getRate() {
        return this.rate;
    }
}
