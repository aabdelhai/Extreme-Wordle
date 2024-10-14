import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;

public class WordleSquare {
    private static final double width = 90;
    private static final double height = 90;
    private static final Color white = new Color(255,255,255);
    private static final Color red  = new Color(139,0,0);
    private static final Color yellow  = new Color(255,213,128);
    private static final Color green  = new Color(79,121,66);

    private Rectangle square;
    private String letter;
    private String word;
    private GraphicsText assignedLetter;
    private GraphicsText typedLetter;
    private boolean isGreen;
    private boolean isYellow;
    private boolean isRed;

    /**
    * Creates a singular square for the game wordle.
    */
    public WordleSquare(double x, double y,int index, String word){
        square = new Rectangle(x, y, width, height);
        square.setFillColor(white);
        square.setStrokeWidth(3);
        square.setStroked(true);
        this.word = word;
        letter = word.substring(index, index + 1);
        assignedLetter = new GraphicsText(letter,x + 45,y + 45);
        typedLetter = new GraphicsText("",x + 30,y + 75);
        typedLetter.setFontSize(50);
    }

    /**
    * @return a string of the set typed letter.
    */
    public String getTypedLetter(){
        return typedLetter.getText();
    }

    /**
    * @return a rectangle object of the square.
    */
    public Rectangle getSquare(){
        return square;
    }

    /**
    * Removes the letter from the box.
    * @param canvas is the canvas where this is happening.
    */
    public void removeLetter(CanvasWindow canvas){
        typedLetter.setText("  ");
        canvas.add(typedLetter);
    }

    /**
    * Sets the letter for the box.
    * @param canvas is the canvas where this is happening.
    * @param s is the string version of the typed letter.
    */
    public void setTypedLetter(String s, CanvasWindow canvas){
        typedLetter.setText(s); 
        canvas.add(typedLetter);
    }

    /**
    * Checks if a letter is in the correct spot, in the word, or not at all and sets the color and variables based off of this 
    * @param canvas is the canvas where this is happening.
    */
    public void checkSquare(CanvasWindow canvas){
        if (assignedLetter.getText().equals(typedLetter.getText().toLowerCase())){
            square.setFillColor(green);
            canvas.add(typedLetter);
            isGreen = true;
            isYellow = false;
            isRed = false;

        }else if (word.contains(typedLetter.getText().toLowerCase())){
            square.setFillColor(yellow);
            canvas.add(typedLetter);
            isGreen = false;
            isYellow = true;
            isRed = false;
        }
        else{
            square.setFillColor(red);
            canvas.add(typedLetter);
            isGreen = false;
            isYellow = false;
            isRed = true;
        }
    }

    /**
     * Manually sets the box to red
    * @param canvas is the canvas where this is happening.
    */
    public void setRed(CanvasWindow canvas){
        square.setFillColor(red);
        canvas.add(typedLetter);
        isYellow = false;
        isGreen = false;
        isRed = true;
    }

     /**
     * Manually sets the box to green
    * @param canvas is the canvas where this is happening.
    */
    public void setGreen(CanvasWindow canvas){
        square.setFillColor(green);
        canvas.add(typedLetter);
        isYellow = false;
        isGreen = true;
        isRed = false;
    }

    /**
    * @return a boolean if the square is yellow.
    */
    public boolean isYellow(){
       return isYellow;
    }

    /**
    * @return a boolean if the square is green.
    */
    public boolean isGreen(){
        return isGreen;
    }

    /**
    * @return a boolean if the square is red.
    */
    public boolean isRed(){
        return isRed;
    }

    /**
     * Adds the box to canvas
    * @param canvas is the canvas where this is happening.
    */
    public void addToCanvas(CanvasWindow canvas) {
        canvas.add(square);
    }
}
