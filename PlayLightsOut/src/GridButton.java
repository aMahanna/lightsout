import java.util.Random;

import java.awt.Color;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.BorderFactory;
import javax.swing.border.Border;

public class GridButton extends JButton {


    private int column;
    private int row;

    private boolean isOn; 
    private boolean isClicked;

    boolean selected = false;

    public static final int NUM_COLOURS = 4;
    
    // Image type
    private int type;

    private static final ImageIcon[] icons = new ImageIcon[NUM_COLOURS];

    private final int ICON0 = 0; // DEFAULT ON 
    private final int ICON1 = 1; // DEFAULT OFF
    private final int ICON2 = 2; // DEFAULT SOLUTION ORANGE
    private final int ICON3 = 3; // DEFAULT SOLUTION BLACK

    /**
     * Constructor used for initializing a GridButton at a specific
     * Board location.
     * 
     * @param column
     *            the column of this Cell
     * @param row
     *            the row of this Cell
     */
    public GridButton(int column, int row) {
        if (column > -1 && row > -1) {
            this.column = column; // Initializes column and row
            this.row = row;
            this.type = ICON1; // Sets the type to the OFF 
            setIcon(getImageIcon()); // Gets the icon of the initial gridButton (off)
            setBorder(null);
            setBorderPainted(false); 
        }
    }

    private ImageIcon getImageIcon() {
        if (icons[type] == null) {
            icons[type] = new ImageIcon("../icons/" + getIconFileName()); // I copied this from Cell.java from Puzzler
        } 
        return icons[type]; // returns the icon
    }

    private String getIconFileName() {
        // Fetches the png depending on the type
        switch(type) {
            case 0 : return "Light-0.png"; // REGULAR ON ORANGE
            case 1 : return "Light-1.png"; // OFF 
            case 2 : return "Light-2.png"; // SOLUTION BRIGHT ORANGE
            case 3 : return "Light-3.png"; // SOLUTION BLACK ORANGE
            default: System.out.println("Error"); return "";
        }
    }


   /**
    * sets the icon of the button to reflect the
    * state specified by the parameters
    * @param isOn true if that location is ON
    * @param isClicked true if that location is
    * tapped in the model's current solution
    */
    public void setState(boolean isOn, boolean isClicked) {
        if (isOn && isClicked) {  // SOLUTION ORANGE
            this.setType(2);
        } else if (!isClicked && isOn) { // NORMAL ORANGE
            this.setType(0);    
        } else if (!isOn && !isClicked) { // OFF
            this.setType(1); 
        } else {
            this.setType(3); // BLACK
        }
    }


    public void setType(int type) {
        this.type = type;
        setIcon(getImageIcon());
    }


    /**
     * Getter method for the attribute row.
     * 
     * @return the value of the attribute row
    */
    public int getRow() {
        return row;
    }

    /**
     * Getter method for the attribute column.
     * 
     * @return the value of the attribute column
    */
    public int getColumn() {
        return column;
    }
}
