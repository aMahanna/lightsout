/**
 * The Model of the game. It operates the LightOuts board.
*/
public class GameModel {

	private boolean[][] board; // Boolean array for board
	private int width;
	private int height;

	private Solution shortestSolution; // Stores shortestSolution here

	private int numSteps;

	public GameModel(int width, int height) {

		try {
			board = new boolean[height][width]; // Initializes board with height and width
			this.width = width;
			this.height = height;
			numSteps = 0;
			shortestSolution = new Solution(width,height); // Creates a new Solution to the board
			
		} catch (ArrayIndexOutOfBoundsException e) { // Catches an exception if the inputs are not valid
			System.out.println("Not a valid dimension for the board. Try again");
			System.exit(1);
		}
	}

	public int getHeight() {
		return height;
	}

	public int getWidth() {
		return width;
	}

	public boolean isON(int i, int j) {

		boolean value = false;
		try {
			value = board[i][j];  // ROW i & COLUMN j
		} catch (ArrayIndexOutOfBoundsException e) { // Catches an exception if the inputs are not valid
			System.out.println("Not a valid index position for the board. Try again");
			System.exit(1);
		}	

		return value;	
		
	}

	public void reset() {
		board = new boolean[height][width]; // Re-initializes the board
		numSteps = 0; // Resets number of steps
	}

	public void set(int i, int j, boolean value) {


		try {
			board[j][i] = value; // Column i ROW j 
		} catch (ArrayIndexOutOfBoundsException e) { // Catches an exception if the inputs are not valid
			System.out.println("Not a valid index position for the board. Try again");
			System.exit(1);
		}
	}

	public void click(int i, int j) {

		board[i][j] = !board[i][j]; // row i column j 

		try {
			board[i+1][j] = !board[i+1][j];
		} catch (ArrayIndexOutOfBoundsException e) {} 

		try {
			board[i][j+1] = !board[i][j+1];
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			board[i-1][j] = !board[i-1][j];
		} catch (ArrayIndexOutOfBoundsException e) {}

		try {
			board[i][j-1] = !board[i][j-1];
		} catch (ArrayIndexOutOfBoundsException e) {}

		numSteps++;
	}

	public int getNumberOfSteps() {
		return numSteps;
	}

	public boolean isFinished() {
		for (boolean[] elem: board) {
			for(boolean val: elem) {
				if (!val) {
					return false; // Loops through the board array and returns false if one value is OFF
				}
			}
		} 

		return true;
	}

	public void randomize() {
		
		do {
			this.shuffle(); // Calls private method shuffle() right below 
		} while (LightsOut.solve(this).size() == 0); // Loops while there are no solutions to the GameModel (uses solve(GameModel))

	}

	private void shuffle() {

		long randomBinary;
		for (int i = 0; i < height; i++) {
			for (int j = 0; j < width; j++) {
				randomBinary = Math.round(Math.random()); // Creates a number either 0 or 1
				if (randomBinary == 1) { // If 1, assign ON
					board[i][j] = true;
				} else {
					board[i][j] = false; // If 0, assign OFF
				}
				
			}
		}
	}
	
	public void setSolution() {
		shortestSolution = LightsOut.solveShortest(this); // Calls LightsOut to find the shortest solution 
	}
	

	public boolean solutionSelects(int i, int j) {
		
		boolean value = false;

		if (!shortestSolution.isSuccessful(this)) { // Returns false if the solution is not successfull with this GameModel
			return value;
		}

        try {
            value = shortestSolution.get(j,i); // Returns the value of the solution at COLUMN J ROW I (THE "SWITCH" WAS AS INSTRUCTED)
        } catch (ArrayIndexOutOfBoundsException e) { // Catches an exception if the inputs are not valid
            System.out.println("Not a valid index for the board");
            System.exit(1);
        }

        return value;
	}

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

    /**
     * Displays the solution in the terminal for the user to follow
    */
    public Solution getSolution() {
    	return shortestSolution; 
    }
}
