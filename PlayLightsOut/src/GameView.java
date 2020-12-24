import javax.swing.*;
import java.awt.*;

/**
 * Provides the current view of the entire Game. It extends
 * <b>JFrame</b> and lays out a matrix of <b>GridButton</b> (the actual game) and 
 * two instances of JButton. The action listener for the buttons is the controller.

*/
public class GameView extends JFrame {

    private JPanel playBoard;
    private GameModel gameModel;
    private GridButton[][] board;

    private JCheckBox solutionBox;
    private JLabel stepsTaken;


    /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController 
     *            the controller
    */
    public GameView(GameModel gameModel, GameController gameController) {
        
        // Initializes the JFrame used to store the JPanels, which consists of JButtons and GridButtons
        JFrame f = new JFrame("LightsOut");
        f.setLayout(new FlowLayout());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        

        this.gameModel = gameModel;

        board = new GridButton[gameModel.getHeight()][gameModel.getWidth()]; // Creates a 2D array of GridButtons
        playBoard = new JPanel(new GridLayout(gameModel.getHeight(),gameModel.getWidth())); // JPanel used to store all GridButtons

        JPanel buttons = new JPanel(new GridLayout(4,1));

        // Loops through the gameModel's Dimensions
        for (int i = 0 ; i < gameModel.getHeight(); i++){
            for (int j = 0 ; j < gameModel.getWidth(); j++){
                board[i][j] = new GridButton(i,j);                  // Creates a new GridButton instance
                // Sets the action command at position (i,j) for the GameController to recognize which GridButton has been clicked.
                board[i][j].setActionCommand("(" + i + ", " + j + ")");  
                board[i][j].addActionListener(gameController); // Adds its actionListener
               
                playBoard.add(board[i][j]);

            }
        }

        // Registering the RESET button to JPanel
        JButton resetButton = new JButton("Reset");
        resetButton.addActionListener(gameController);
        buttons.add(resetButton);

        // Registering the RANDOMIZE button to JPanel
        JButton randomizeButton = new JButton("Randomize");
        randomizeButton.addActionListener(gameController);
        buttons.add(randomizeButton);

        // Registering the SOLUTION button to JPanel
        solutionBox = new JCheckBox("Solution");
        solutionBox.addItemListener(gameController);
        buttons.add(solutionBox);

        // Registering the QUIT button to JPanel
        JButton quitButton = new JButton("Quit");
        quitButton.addActionListener(gameController);
        buttons.add(quitButton);

        
        // Adds both JPanels to the JFrame
        f.add(playBoard, BorderLayout.CENTER);
        f.add(buttons, BorderLayout.EAST);
        

        // Lastly, creates a new JLabel to measure the amount of Steps Taken and adds it to the JFrame
        stepsTaken = new JLabel(String.valueOf("Steps Taken: " + gameModel.getNumberOfSteps()));
        f.add(stepsTaken, BorderLayout.SOUTH);
        
        f.pack();
        f.setVisible(true);
    }
    

    /**
     * updates the status of the board's GridButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){  
        boolean isOn = false;
        boolean isClicked = false;

       
        if (solutionShown()) {
            gameModel.setSolution();
            System.out.println("Showing Solution...");
            System.out.println(gameModel.getSolution());
        } 

        for (int i =0; i < gameModel.getHeight(); i++) {
            for (int j =0; j < gameModel.getWidth(); j++) {
                isOn = false;
                isClicked = false;
                if (gameModel.isON(i,j)) {
                    isOn = true;
                } 
                if (gameModel.solutionSelects(i, j)) {
                    isClicked = true;
                }
                board[i][j].setState(isOn, isClicked);
            }
        }

        stepsTaken.setText("Steps Taken: " + gameModel.getNumberOfSteps()); // Updates steps counter
    }   

    /**
     * returns true if the ``solution'' checkbox
     * is checked
     *
     * @return the status of the ``solution'' checkbox
    */
    public boolean solutionShown(){
        return solutionBox.isSelected();
    }

}
