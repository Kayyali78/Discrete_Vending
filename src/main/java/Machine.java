import java.util.Arrays;

public class Machine {
    State state;
    double continuousTime = 0.0;
    int[] outCounts = new int[3];
    InputPair inputPair;


    /*
    This program is a simulation of a coffee machine. It takes in a single input at a time consisting of nickels, dimes, and quarters.
    It outputs a coffee for every 100 cents and gives change in the form of quarters, dimes, and nickels.
     */
    public void main(String[] args){
        state = new State(0,0,0,0);
        startSimulation();
        //state is declared at 0,0,0,0
        //lambda before delta
        //inputPair is taken in, time elapsed is used to change the time value
    }

    private void startSimulation(){
        if (inputPair.continuousTime < continuousTime){
            deltaExternal(inputPair);
        } else if (inputPair.continuousTime == continuousTime){
            deltaConfluent(inputPair);
        } else {
            deltaInternal();
        }
    }

    private double timeAdvance(){
        if (state.v>0){
            return 2.0;
        } else {
            return Double.POSITIVE_INFINITY;
        }
    }

    private void lambda(){
        //sout coffee for 100 value AND combination of q,n,d of change representing v%100
        while (state.v>=100){
            System.out.println("Coffee dispensed");
            state.v-=100;
            change();
        }
    }

    private void deltaInternal(){
        change();
        timeAdvance();
    }

    private void deltaExternal(InputPair input){
        takeInput(input);
        change();
        timeAdvance();
    }

    private void deltaConfluent(InputPair input){
        //if next internal event is before next external event



    }

    private void takeInput(InputPair input) {
        if (input.input == 'n'){
            state.n += 1;
            state.v += 5;
        } else if (input.input == 'd'){
            state.d += 1;
            state.v += 10;
        } else if (input.input == 'q'){
            state.q += 1;
            state.v += 25;
        }
    }

    private void change(){
        while (state.v>0) {
            if (state.v >= 25 && state.q !=0) {
                state.v -= 25;
                state.q -= 1;
                outCounts[2] ++;
            } else if (state.v >= 10 && state.d != 0) {
                state.v -= 10;
                state.d -= 1;
                outCounts[1] ++;
            } else if (state.v >= 5 && state.n != 0) {
                state.v -= 5;
                state.n -= 1;
                outCounts[0] ++;
            } else {
                break;
            }
        }
        System.out.println("Change is: ");
        System.out.println("Quarters: " + outCounts[2]);
        System.out.println("Dimes: " + outCounts[1]);
        System.out.println("Nickels: " + outCounts[0]);

        state.v = 0;
        Arrays.fill(outCounts, 0);
    }

}
