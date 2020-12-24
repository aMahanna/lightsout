import java.util.ArrayList;


/**
 * Implements the <b> solve </b> method to compute solutions for a Lights Out board.
 * It also contains the main().  
*/
public class LightsOut {

    /**
     * The method <b>solve</b> finds all the 
     * solutions to the <b>Lights Out</b> game 
     * for an initially completely ``off'' board 
     * of size <b>widthxheight</b>, using a  
     * Breadth-First Search algorithm. 
     *
     * During the computation of the solution, the 
     * method prints out a message each time a new 
     * solution  is found, along with the total time 
     * it took (in milliseconds) to find that solution.
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     * @return An <b>ArrayList</b> containing all the valid solutions to the problem
     */
    public static ArrayList<Solution> solve(int width, int height){
        Solution current; // Initializes current Solution
        Solution copyCurrent; // Initializes the copy Solution, used in the branching of all possible combinations
        
        ArrayList<Solution> solutions = new ArrayList<Solution>(); // Keeps all successful and ready solutions heres

        // Creates the queue to hold all un-ready solutions
        ArrayListSolutionQueue partialSolutions = new ArrayListSolutionQueue(); 

        partialSolutions.enqueue(new Solution(width, height)); // Enqueues the first un-ready solution

        long startTime = System.currentTimeMillis();
        long endTime;
        while (!partialSolutions.isEmpty()) { // While there are partial solutions left
            current = partialSolutions.dequeue(); // Remove the first in line
           
            if (current.isSuccessful() && current.isReady()) { // Check if the solution is successful (also calling isReady() inside)
                endTime = System.currentTimeMillis();
                System.out.println("Solution found in " + (endTime - startTime) + " ms.");
                solutions.add(current); // Adds it to the ArrayList if so
                
            } else if (!current.isReady()){ 
                copyCurrent = new Solution(current); // Creates a copy of the current Solution

                // This condition will extend the solution out to both possible branches 
                if (current.stillPossible(true) && copyCurrent.stillPossible(false)) { 
                    current.setNext(true);
                    copyCurrent.setNext(false);
                    partialSolutions.enqueue(current);
                    partialSolutions.enqueue(copyCurrent);
                } else { // If there is only one extendable branch, then it will finish it here.
                    
                    if(current.finish()) { // Calls finish() and checks if it is successful
                        endTime = System.currentTimeMillis();
                        System.out.println("Solution found in " + (endTime - startTime) + " ms.");
                        solutions.add(current); // Adds the finished solution to the arrayList
    
                    }
                }

            }
        }

        return solutions; 
        
    }

    /**
     * <b>main</b> method  calls the method <b>solve</b> 
     * and then prints out the number of solutions found,
     * as well as the details of each solution.
     *
     * The <b>width</b> and <b>height</b> used by the 
     * main are passed as runtime parameters to
     * the program. If no runtime parameters are passed 
     * to the program, or if the parameters are incorrect,
     * then the default values are used.
     *
     * @param args
     *  Strings array of runtime parameters
     */
    public static void main(String[] args) {
        int width;
        int height;
        
        if (args.length != 2) { // Checks if user input is more/less than 2 values
            width = 3;
            height = 3;

        } else { // Converts string input to integer
            width = Integer.parseInt(args[0]);
            height = Integer.parseInt(args[1]);
        }

        if (width <= 0) { // Checks if width or height is invalid
            width = 3;
        }
        if (height <= 0) {
            height = 3;
        }

        ArrayList<Solution> solutions = solve(width, height); // Calls solve() method and stores it
        for (Solution elem: solutions) { // Prints all solutions
            System.out.println("*************");
            System.out.println(elem);
        }
        
        // Prints an end statement depending on the amount of solutions
        if (solutions.size() > 1) {
            System.out.println("In a board of " + width + " x " + height + ": " + solutions.size() + " solutions.");
        } else if (solutions.size() == 1) {
            System.out.println("In a board of " + width + " x " + height + ": 1 solution.");
        } else {
            System.out.println("In a board of " + width + " x " + height + ": No solution.");
        }
    }
}