public class InputPair {
    double continuousTime;
    double discreteTime;
    double time_elapse;
    protected char input;

    public InputPair(char input, double continuousTime, int discreteTime) {
        this.input = input;
        this.continuousTime = continuousTime;
        this.discreteTime = discreteTime;
    }

    public int getInput() {
        return input;
    }

    public double getContinuousTime() {
        return continuousTime;
    }

    public double getDiscreteTime() {
        return discreteTime;
    }

    public double getTimeElapse() {
        return time_elapse;
    }

}
