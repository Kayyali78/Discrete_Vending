import java.util.Arrays;
import java.util.Scanner;

public class Machine {
    State state = new State();
    ;
    double continuousTime = 0.0, elapsedTime = 0.0;
    int[] outCounts = new int[3];
    InputPair inputPair;
    int coffeeCost = 100;

    /*
    This program is a simulation of a coffee machine. It takes in a single input at a time consisting of nickels, dimes, and quarters.
    It outputs a coffee for every 100 cents and gives change in the form of quarters, dimes, and nickels.
     */

    public static void main(String[] args) {
        Machine machine = new Machine();
        machine.startSimulation();
    }

    private void startSimulation() {
        boolean loop = true;
        while (loop) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Enter an input pair (format: continuousTime,discreteTime,inputChar): ");

            // Read and parse input
            String input = scanner.next().trim();
            String[] inputParts = input.split(",");

            if (inputParts.length != 3) {
                System.out.println("Invalid input format. Please provide input in the correct format.");
                return;
            }

            try {
                double continuous = Double.parseDouble(inputParts[0]);
                int discrete = Integer.parseInt(inputParts[1]);
                char inputChar = inputParts[2].charAt(0);

                inputPair = new InputPair(continuous, discrete, inputChar);
                if (continuousTime == 0.0) {
                    continuousTime = inputPair.getContinuousTime();
                    deltaExternal(inputPair);
                } else if (inputPair.getContinuousTime() < continuousTime) {
                    System.out.println("InputPair continuous time is less than current continuous time.");
                    deltaExternal(inputPair);
                } else if (inputPair.getContinuousTime() == continuousTime) {
                    System.out.println("InputPair continuous time is equal to current continuous time.");
                    deltaConfluent(inputPair);
                } else if ((inputPair.getContinuousTime() == 0) && (inputPair.getDiscreteTime() == 0) && (inputPair.getInput() == 'x')) {
                    System.out.println("Ending simulation.");
                    change();
                    loop = false;
                } else {
                    System.out.println("InputPair continuous time is greater than current continuous time.");
                    deltaInternal();
                }
            } catch (NumberFormatException | StringIndexOutOfBoundsException e) {
                System.out.println("Invalid input values. Please provide valid numeric values and a character.");
            }
        }
    }

    private void timeAdvance() {
        if (state.v > 0){
            continuousTime = inputPair.continuousTime + 2.0;
            System.out.println("Time advanced to: " + continuousTime);
        } else {
            continuousTime = Double.POSITIVE_INFINITY;
            System.out.println("Time advanced to: " + continuousTime);
        }
    }

    private void lambda(){
        //sout coffee for 100 value AND combination of q,n,d of change representing v%100
        while (state.v>=coffeeCost){
            System.out.println("Coffee dispensed");
            state.v-=coffeeCost;
        }
        change();
    }

    private void deltaInternal(){
        System.out.println("In deltaInternal");
        lambda();
    }

    private void deltaExternal(InputPair input){
        System.out.println("In deltaExternal");
        takeInput(input);
        timeAdvance();
    }

    private void deltaConfluent(InputPair input){
        System.out.println("In deltaConfluent");
        deltaInternal();
        deltaExternal(input);
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
        System.out.println("State is: "+ state);
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
