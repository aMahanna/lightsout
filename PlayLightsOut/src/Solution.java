/**
 * The class <b>Solution</b> is used
 * to store a (partial) solution to the game
*/
public class Solution {


    /**
     * our board. board[i][j] is true is in this
     * solution, the cell (j,i) is tapped
     */
    private boolean[][] board;

    /**
     *  width of the game
     */
    private int width;

    /**
     * height of the game
     */
    private int height;
    
    /**
     * how far along have we constructed that solution.
     * values range between 0 and height*width-1
     */
    private int currentIndex;
    private int[][] switchCountArray;



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

        this.width = width;
        this.height = height;

        board = new boolean[height][width];
        currentIndex = 0;
        switchCountArray = new int[height][width];

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

        this.width = other.width;
        this.height = other.height;
        this.currentIndex = other.currentIndex;
        this.switchCountArray = new int[height][width];


        board = new boolean[height][width];

        for(int i = 0; i < currentIndex; i++){
            board[i/width][i%width] = other.board[i/width][i%width];
        } 

        for(int i =0; i < height;i++) {
            for(int j = 0; j < width; j++) {
                this.switchCountArray[i][j] = other.switchCountArray[i][j];
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

        if(other == null) {
            return false;
        }
        if(this.getClass() != other.getClass()) {
            return false;
        }

        Solution otherSolution = (Solution) other;

        if(width != otherSolution.width ||
            height != otherSolution.height ||
            currentIndex != otherSolution.currentIndex) {
            return false;
        }

        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width; j++) {
                if(board[i][j] != otherSolution.board[i][j]){
                    return false;
                }
            }
        }

        return true;

    }


    /** 
    * returns <b>true</b> if the solution 
    * has been entirely specified
    *
    * @return
    * true if the solution is fully specified
    */
    public boolean isReady(){
        return currentIndex == width*height;
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

        if(currentIndex >= width*height) {
            System.out.println("Board already full");
            return;
        }
        board[currentIndex/width][currentIndex%width] = nextValue;
        currentIndex++;
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

        if(currentIndex < width*height) {
            System.out.println("Board not finished");
            return false;
        }

        for(int i = 0; i < height ; i++){
            for(int j = 0; j < width; j++) {
                if(!oddNeighborhood(i,j)){
                    return false;
                }
            }
        }
        return true;
    }


   /**
    * this method ensure that add <b>nextValue</b> at the
    * currentIndex does not make the current solution
    * impossible. It assumes that the Solution was
    * built with a series of setNext on which 
    * stillPossible was always true.
    * @param nextValue
    *         The boolean value to add at currentIndex
    * @return true if the board is not known to be
    * impossible (which does not mean that the board
    * is possible!)
    */
    public boolean stillPossible(boolean nextValue) {

        if(currentIndex >= width*height) {
            System.out.println("Board already full");
            return false;
        }

        int i = currentIndex/width;
        int j = currentIndex%width;
        boolean before = board[i][j];
        boolean possible = true;

        board[i][j] = nextValue;
        
        if((i > 0) && (!oddNeighborhood(i-1,j))){
            possible = false;
        }
        if(possible && (i == (height-1))) {
            if((j > 0) && (!oddNeighborhood(i,j-1))){
                possible = false;
            }
            if(possible && (j == (width-1))&& (!oddNeighborhood(i,j))){
                possible = false;            
            }
        }
        board[i][j] = before;
        return possible;
    }


    /**
    * this method attempts to finish the board. 
    * It assumes that the Solution was
    * built with a series of setNext on which 
    * stillPossible was always true. It cannot
    * be called if the board can be extended 
    * with both true and false and still be 
    * possible.
    *
    * @return true if the board can be finished.
    * the board is also completed
    */
    public boolean finish(){


        int i = currentIndex/width;
        int j = currentIndex%width;
        
/*
        if(i == 0 && height > 1) {
            System.out.println("First line incomplete, can't proceed");
            return false;
        }
*/

        while(currentIndex < height*width) {
            if(i < height - 1 ) {
                setNext(!oddNeighborhood(i-1,j));
                i = currentIndex/width;
                j = currentIndex%width;
            } else { //last raw
                if(j == 0){
                    setNext(!oddNeighborhood(i-1,j));
                } else {
                   if((height > 1) && oddNeighborhood(i-1,j) != oddNeighborhood(i,j-1)){
                     return false;
                   }
                   setNext(!oddNeighborhood(i,j-1));
                } 
                i = currentIndex/width;
                j = currentIndex%width;
            }
        }
        if(!oddNeighborhood(height-1,width-1)){
            return false;
        }
        // here we should return true because we could
        // successfully finish the board. However, as a
        // precaution, if someone called the method on
        // a board that was unfinishable before calling
        // the method, we do a complete check
        
        if(!isSuccessful()) {
            System.out.println("Warning, method called incorrectly");
            return false;
        }
       
        return true;

    }

    /**
     * checks if board[i][j] and its neighborhood
     * have an odd number of values ``true''
     */

    private boolean oddNeighborhood(int i, int j) {
        
        if(i < 0 || i > height - 1 || j < 0 || j > width - 1) {
            return false;
        }

        int total = 0;
        if(board[i][j]){
            total++;
        }
        if((i > 0) && (board[i-1][j])) {
            total++;
        }
        if((i < height -1 ) && (board[i+1][j])) {
            total++;
        }
        if((j > 0) && (board[i][j-1])) {
            total++;
        }
        if((j < (width - 1)) && (board[i][j+1])) {
            total++;
        }
        return (total%2)== 1 ;                
    }

    /**
     * returns a string representation of the solution
     *
     * @return
     *      the string representation
     */
    public String toString() {
        StringBuffer out = new StringBuffer();
        out.append("[");
        for(int i = 0; i < height; i++){
            out.append("[");
            for(int j = 0; j < width ; j++) {
                if (j>0) {
                    out.append(",");
                }
                out.append(board[i][j]);
            }
            out.append("]"+(i < height -1 ? ",\n" :""));
        }
        out.append("]");
        return out.toString();
    }

    public boolean stillPossible(boolean nextValue, GameModel model) {
        if (width != model.getWidth() || height != model.getHeight()) {
            throw new IllegalArgumentException("GameModel board does not have the same dimensions as the object instance of Solution. Try again");
            
        }

        Solution copySol = new Solution(width, height); // Creates a copy Solution 
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                copySol.board[i][j] = model.isON(i,j); // Copies all the model state to the solution board

                if (copySol.currentIndex < this.currentIndex) { // 
                    if (this.board[i][j] == true) { // Copies the amount of specifications from the initial solution
                        copySol.setNext(true);

                    } else {
                        copySol.setNext(false);    
                    }
                }
            }
        }


        int switchCount = 0; // Similar to its array version, this stores the number of selections for one slot
        int lastSpecifiedY = (currentIndex/width)-1;
        int lastSpecifiedX = currentIndex%width ;
    
        
        copySol.setNext(nextValue);

        // If this is the case, then there is no box that currently has all of its surroundings specified
        if (lastSpecifiedY < 0) { 
            return true; // Returns true because we cannot confirm that the solution will NOT work
        } else {

            if (model.isON(lastSpecifiedY,lastSpecifiedX)) {
                switchCount++;
            }

            if (copySol.board[lastSpecifiedY][lastSpecifiedX] == true) { 
                switchCount++; // Increments count if the targeted box is true
            }
            
            if (lastSpecifiedY-1 >= 0 && lastSpecifiedY-1 < height && copySol.board[lastSpecifiedY-1][lastSpecifiedX] == true) { 
                switchCount++; // Increments count if the top neighbouring box is true
            }

            if (lastSpecifiedX-1 >= 0 && lastSpecifiedX-1 < width && copySol.board[lastSpecifiedY][lastSpecifiedX-1] == true) { 
                switchCount++; // Increments count if the left neighbouring box is true
            }

            if (lastSpecifiedY+1 >= 0 && lastSpecifiedY+1 < height && copySol.board[lastSpecifiedY+1][lastSpecifiedX] == true) { 
                switchCount++; // Increments count if the bottom neighbouring box is true
            }
            
            if (lastSpecifiedX+1 >= 0 && lastSpecifiedX+1 < width && copySol.board[lastSpecifiedY][lastSpecifiedX+1] == true) { 
                switchCount++; // Increments count if the right neighbouring box is true
            }

            if (switchCount % 2 == 0) { // Returns false if the switchCount is pair
                return false; // Determines that doing setNext(nextValue) would not lead to a successfull solution
            } else {
                return true; // So far so good!
            }
        }

    }

    public boolean finish(GameModel model) {

        if (width != model.getWidth() || height != model.getHeight()) {
            throw new IllegalArgumentException("GameModel board does not have the same dimensions as the object instance of Solution. Try again");
    
        }


         // Loop runs until the Solution is ready
        while (!this.isReady()) {
            if (this.stillPossible(true,model)) { // Sets next to true if the solution is still possible
                this.setNext(true);
            } else if (this.stillPossible(false,model)) { // Sets next to false if the solution is still possible
                this.setNext(false);
            } else {
                return false; // Returns false if the solution is no longer successful at its last specification
            }

        }

        return this.isSuccessful(model);
    }

    public boolean isSuccessful(GameModel model){

        if (!this.isReady()) {
            return false; // Returns false if the solution is not ready
        }
        int topX,topY;
        int leftX,leftY;
        int rightX,rightY;
        int botX,botY;

        // Loops through the board
        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {

                if (model.isON(y,x)) {              
                    switchCountArray[y][x] +=1;                
                }

                if (this.board[y][x] == true) { // Only targets boxes that are on (true)
                    switchCountArray[y][x] += 1; // Increments its integer count

                    /*
                        This process increments the int count of all the surrounding boxes
                        that are valid (do not cross the borders of the solution board)
                    */
                    topX = x;
                    topY = y - 1;

                    if (topY >= 0 && topY < height) { // Top box valid ?
                        switchCountArray[topY][x] += 1;
                    }

                    leftX = x - 1;
                    leftY = y;

                    if (leftX >= 0 && leftX < width) { // Left box valid?
                        switchCountArray[y][leftX] += 1;
                    }

                    botX = x;
                    botY = y + 1;

                    if (botY >= 0 && botY < height) { // Bottom box valid? 
                        switchCountArray[botY][x] += 1;
                    }

                    rightX = x + 1;
                    rightY = y;

                    if (rightX >= 0 && rightX < width) { // Right box valid? 
                        switchCountArray[y][rightX] += 1;
                    }

                    if (y-1 >= 0 && y-1 < height && switchCountArray[y-1][x] % 2 == 0) {
                        return false;
                    }


                } 

            }
        }

        // This nested loop goes through the switchCount array
        for (int i = 0; i < height; i++) { 
            for (int j = 0; j < width; j++) {
                // If an integer in the array is not odd, the Solution is not successful
                if (switchCountArray[i][j] % 2 == 0) { 
                    return false;
                    
                }
            }
        }

        // Returns true if the odd number test has passed
        return true; 
       

    }


    public int getSize() {
        int tapCount = 0; 
        if (this.isReady()) {
            
            for (boolean[] elem: board) {
                for (boolean val: elem) {
                    if (val) {
                        tapCount++; // Increments tapCount if value in ON
                    }
                }
            }
            return tapCount;
        } else {
            return -1;
        }

    }

    public boolean get(int i, int j) {
        boolean value = false;
        try {
            value = board[j][i]; // Row j Column i
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Not a valid index for the board");
            System.exit(1);
        }

        return value;
    }
}

