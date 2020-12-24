import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.awt.event.ItemEvent;
import javax.swing.*;
import java.awt.*;

/**
 * The Model of the game. It is a listener
 * of the view, and has a method <b>play</b> which computes the next
 * step of the game, and  updates model and view.
 *
*/
public class GameController implements ActionListener, ItemListener {

    private GameModel gameModel;
    private GameView gameView;


    /**
     * Constructor used for initializing the controller. It creates the game's view 
     * and the game's model instances
     * 
     * @param width
     *            the width of the board on which the game will be played
     * @param height
     *            the height of the board on which the game will be played
     */
    public GameController(int width, int height) {
        gameModel = new GameModel(width,height); // Creates gameModel and gameView
        gameView = new GameView(gameModel, this);
        update();
        
    }

    /**
     * Callback used when the user clicks a button (reset, 
     * random or quit)
     *
     * @param e the ActionEvent
    */
    public void actionPerformed(ActionEvent e) {
        
        if (e.getActionCommand().equals("Reset")) { // Catches reset command
            System.out.println("Resetting...");
            gameModel.reset();
        }

        if (e.getActionCommand().equals("Randomize")) {  // Catches randomize command
            System.out.println("Randomizing...");
            gameModel.randomize();
        }

        if (e.getActionCommand().equals("Quit")) {  // Catches quit command
            System.out.println("Quitting...");
            System.exit(0);
        }

        // Loop through the cells and recognizes which cell has been clicked

        for (int i = 0; i < gameModel.getHeight(); i++) {
            for (int j = 0; j < gameModel.getWidth(); j++) {
                if (e.getActionCommand().equals("(" + i + ", " + j + ")")) { // Catches the click at position (i,j)
                    gameModel.click(i,j); // Calls click() from GameModel
                }  
            }
        }

        if (gameModel.isFinished()) {
           JOptionPane.showMessageDialog(new JFrame(), "Congratulations, you've won in " + gameModel.getNumberOfSteps() + " steps! Click OK to reset & play again or to quit");
        }

        update();

    }



    /**
     * Callback used when the user select/unselects
     * a checkbox
     *
     * @param e the ItemEvent
     *            
    */
    public void  itemStateChanged(ItemEvent e){
        if (gameView.solutionShown()) {
            gameModel.setSolution();
            System.out.println("Solution Called");
            update();
        } else {
            update();
        }
        
    }

    public GameModel getModel() {
        return gameModel;
    }

    public GameView getView() {
        return gameView;
    }

    private void update() {
        gameView.update();
    }

}
