/**
 * Used to store a partial solution to the game
*/
public class Solution {


    // 2D Boolean array to store the boolean values of a Solution
    private boolean[][] board;
     
    // This array stores the amount of times each value has been "selected"
    private int[][] switchCountArray;

    // Used to store the formal parameters of the class constructor
    private int rowLength;
    private int colLength;
    
    // Initiates the x and y positions of the board, used for setNext()
    private int xPos = -1;
    private int yPos;
    private int setCounter; // Initiates the setCounter, used to determine how many times a board has been specified 

    /**
     * Constructor. Creates an instance of Solution 
     * for a board of size <b>widthxheight</b>. That 
     * solution does not have any board position
     * value explicitly specified yet.
     *
     * @param width
     *  the width of the board
     * @param height
     *  the height of the board
     */
    public Solution(int width, int height) {
        // Variables used in other methods
        rowLength = width; // Stores the width
        colLength = height; // Stores the height

        board = new boolean[height][width]; // Initializes the boolean array
        switchCountArray = new int[height][width]; // Initializes the integer array of same size
        
    }

   /**
     * Constructor. Creates an instance of Solution 
     * wich is a deep copy of the instance received
     * as parameter. 
     *
     * @param other
     *  Instance of solution to deep-copy
     */
    
    public Solution(Solution other) {

        setCounter = 0;
        rowLength = other.rowLength; // Copies the row and column length
        colLength = other.colLength;

        board = new boolean[other.colLength][other.rowLength]; // Initializes the boolean array
        switchCountArray = new int[other.colLength][other.rowLength]; // Initializes the integer array of same size


        for (int y = 0; y < other.colLength; y++) {
            for (int x = 0; x < other.rowLength; x++) {

                if (this.setCounter >= other.setCounter) { // Breaks if the amount of specifications have been reached
                    break;
                }
                else if (other.board[y][x] == true) { // Sets to true
                    this.setNext(true);

                } else {
                    this.setNext(false);  // Specify the false box, eventually sets deepCopy to ready  
                }
            }   
        }

    }


    /**
     * returns <b>true</b> if and only the parameter 
     * <b>other</b> is referencing an instance of a 
     * Solution which is the ``same'' as  this 
     * instance of Solution (its board as the same
     * values and it is completed to the same degree)
     *
     * @param other
     *  referenced object to compare
     */

    public boolean equals(Object other){

        Solution otherSol = (Solution) other;

        if (otherSol == null) {
            return false; // Returns false if the other Solution is initially null
        }

        // Returns false if the solutions are not of same size
        if (colLength != otherSol.colLength || rowLength != otherSol.rowLength) {
            return false;
        } else if (otherSol.setCounter != this.setCounter) {
            return false; // Returns false if the number of specifications are not identical
        } else {

            // Nested loop goes through the arrays (same size)
            for (int y = 0; y < colLength; y++) {
                for (int x = 0; x < rowLength; x++) {
                    if (board[y][x] != otherSol.board[y][x]) { 
                        return false; // Returns false if the arrays do not match
                    }
                }
            }

            return true; // Returns true if the tests have passed

        }
        
    }
    


    /** 
    * returns <b>true</b> if the solution 
    * has been entirely specified
    *
    * @return
    * true if the solution is fully specified
    */
    public boolean isReady(){

        // Verifies that the setCounter is equal or bigger than the number of elements in the board
        if (setCounter >= (rowLength * colLength)) {
            return true;
        }
        return false; 
        
    }

    /** 
    * specifies the ``next'' value of the 
    * solution. 
    * The first call to setNext specifies 
    * the value of the board location (1,1), 
    * the second call specifies the value
    *  of the board location (1,2) etc. 
    *
    * If <b>setNext</b> is called more times 
    * than there are positions on the board, 
    * an error message is printed out and the 
    * call is ignored.
    *
    * @param nextValue
    *  the boolean value of the next position
    *  of the solution
    */
    public void setNext(boolean nextValue) {

        setCounter++; // Integer used to verify that the right amount of specifications have been made
        
        xPos++; // Increments the X index for the next value assignment (Goes from left to right)
        
        // If the X index has reached the row length (Out of Bounds exception);
        if (xPos == rowLength) { 
            xPos = 0; // Reset column index to 0
            yPos++; // Increment row index 
            // There will not be an OutofBounds exception for yPos because setNext stops if setCounter exceeds the board dimension
                    
        }
        
        /*  Prints an error if setCounter surpasses the number of specifications needed or if the
            table has no rows or columns.
        */

        if ((setCounter > (rowLength * colLength)) || colLength == 0 || rowLength == 0) { 
            System.out.println("!: '" + nextValue + "' was not assigned. The number of specifications has been maxed."); 
            System.out.println("Set Counter for " + this + " currently at " + setCounter);
            System.out.println();
            return;
        }
        
        else { 

            board[yPos][xPos] = nextValue; // Assigns nextValue to the board's first available box
            
            
        }
        
    }

    
    /**
    * returns <b>true</b> if the solution is completely 
    * specified and is indeed working, that is, if it 
    * will bring a board of the specified dimensions 
    * from being  entirely ``off'' to being  entirely 
    * ``on''.
    *
    * @return
    *  true if the solution is completely specified
    * and works
    */
    public boolean isSuccessful(){
        // Each pair of indexes are for the Top, Bottom, Left and Right possible boxes around an element
        int topX;  
        int topY;

        int leftX;
        int leftY;

        int botX;
        int botY;

        int rightX;
        int rightY;

        if (!this.isReady()) {
            return false; // Returns false if the solution is not ready
        }

        // Loops through the board
        for (int y = 0; y < colLength; y++) {
            for (int x = 0; x < rowLength; x++) {

                if (board[y][x] == true) { // Only targets boxes that are on (true)
                    switchCountArray[y][x] += 1; // Increments its integer count

                    /*
                        This process increments the int count of all the surrounding boxes
                        that are valid (do not cross the borders of the solution board)
                    */
                    topX = x;
                    topY = y - 1;

                    if (topY >= 0 && topY < colLength) { // Top box valid ?
                        switchCountArray[topY][x] += 1;
                    }

                    leftX = x - 1;
                    leftY = y;

                    if (leftX >= 0 && leftX < rowLength) { // Left box valid?
                        switchCountArray[y][leftX] += 1;
                    }

                    botX = x;
                    botY = y + 1;

                    if (botY >= 0 && botY < colLength) { // Bottom box valid? 
                        switchCountArray[botY][x] += 1;
                    }

                    rightX = x + 1;
                    rightY = y;

                    if (rightX >= 0 && rightX < rowLength) { // Right box valid? 
                        switchCountArray[y][rightX] += 1;
                    }
                } 
            }
        }

        // This nested loop goes through the switchCount array
        for (int i = 0; i < colLength; i++) { 
            for (int j = 0; j < rowLength; j++) {
                // If an integer in the array is not odd, the Solution is not successful
                if (switchCountArray[i][j] % 2 == 0) { 
                    return false;
                    
                }
            }
        }

        // Returns true if the odd number test has passed
        return true; 
       
    }


    /**
     * returns a string representation of the solution
     *
     * @return
     *      the string representation
     */
    public String toString() {
    
        String display = "\n"; 

        for (boolean[] col : board) { // Loops through the column "sub-arrays"
           for (boolean row : col) // Loops through the row elements found in each sub-array
           {
                display += row + " "; // Displays elements in table-style
           }
            display += "\n";
        }
            
        return display;

    }
    
    /**
    * returns <b>true</b> if extending the solution to nextValue
    * is still considered as a successful solution (above the working line)
    * 
    * @return
    *  true if the solution is still successful
    *
    */
    public boolean stillPossible(boolean nextValue) {
        Solution possibleSolution = new Solution(this); // Deep Copy of the instance Solution we want to evaluate
        possibleSolution.setNext(nextValue);

        if (possibleSolution.isReady()) {
            return possibleSolution.isSuccessful();
        }

        // Stores the coordinates of the last box that has all of its surrounding boxes specified
        int xCoor = possibleSolution.xPos;
        int yCoor = possibleSolution.yPos - 1;

        int switchCount = 0; // Similar to its array version, this stores the number of selections for one slot

        // Initializes all possible positions around the last box whom all of its neighbours are specified 
        int topX = xCoor; 
        int topY = yCoor - 1;

        int leftX = xCoor - 1;
        int leftY = yCoor;

        int botX = xCoor;
        int botY = yCoor + 1;

        int rightX = xCoor + 1;
        int rightY = yCoor;



        // If this is the case, then there is no box that currently has all of its surroundings specified
        if (yCoor < 0) { 
            return true; // Returns true because we cannot confirm that the solution will not work
        } else {

            if (possibleSolution.board[yCoor][xCoor] == true) { 
                switchCount++; // Increments count if the targeted box is true
            }
            
            if (topY >= 0 && topY < colLength && possibleSolution.board[topY][topX] == true) { 
                switchCount++; // Increments count if the top neighbouring box is true
            }

            if (leftX >= 0 && leftX < rowLength && possibleSolution.board[leftY][leftX] == true) { 
                switchCount++; // Increments count if the left neighbouring box is true
            }

            if (botY >= 0 && botY < colLength && possibleSolution.board[botY][botX] == true) { 
                switchCount++; // Increments count if the bottom neighbouring box is true
            }
            
            if (rightX >= 0 && rightX < rowLength && possibleSolution.board[rightY][rightX] == true) { 
                switchCount++; // Increments count if the right neighbouring box is true
            }

            if (switchCount % 2 == 0) { // Returns false if the switchCount is pair
                return false; // Determines that doing setNext(nextValue) would not lead to a successfull solution
            } else {
                return true; // So far so good!
            }

            
        }


    }

    /**
    * returns <b>true</b> if extending the solution to its end
    * is considered as a successful solution
    * 
    * @return
    *  true if the finished solution is successful
    *
    */
    public boolean finish() {

        // Loop runs until the Solution is ready
        while (!this.isReady()) {
            if (this.stillPossible(true)) { // Sets next to true if the solution is still possible
                this.setNext(true);
            } else if (this.stillPossible(false)) { // Sets next to false if the solution is still possible
                this.setNext(false);
            } else {
                return false; // Returns false if the solution is no longer successful at its last specification
            }

        }
        return true;   
    }
}
