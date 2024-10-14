import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.FontStyle;

public class Answer {
    private static final Color black  = new Color(0,0,0);
    private static final Color grey  = new Color(128,128,128);
    private static final Color white  = new Color(255,255,255);

    private GraphicsGroup box;

    /*
     * Creates a graphics group that is a box that contains an answer
     */
    public Answer(int xOfObject, int yOfObject, String answer){
        this.box = new GraphicsGroup();
        Rectangle back = new Rectangle(xOfObject,yOfObject,120,50);
        back.setStrokeColor(black);
        back.setStroked(true);
        back.setStrokeWidth(5);
        back.setFillColor(grey);
        back.setFilled(true);
        GraphicsText word = new GraphicsText(answer.toUpperCase(),xOfObject+25,yOfObject+30);
        word.setFillColor(white);
        word.setFilled(true);
        word.setFont(FontStyle.BOLD,20);
        box.add(back);
        box.add(word);
    }

    /*
     * Adds the graphics group to the given canvas.
     * @param canvas a given canvas window to add the box to.
     */
    public void addToCanvas(CanvasWindow canvas){
        canvas.add(box);
    }
    
}
