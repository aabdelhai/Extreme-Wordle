import java.awt.Color;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsGroup;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Rectangle;
import edu.macalester.graphics.FontStyle;

public class WinScreen {
    private static final Color black  = new Color(0,0,0);
    private static final Color grey  = new Color(128,128,128);
    private static final Color white  = new Color(255,255,255);

    private GraphicsGroup box;
    private GraphicsText showTimer;
    private GraphicsText showPoints;
    private GraphicsText showRounds;
    private GraphicsText showHighScore;
    private GraphicsText youWin;
    private GraphicsText recordTime;

    /*
    * Creates a win screen containing the users points, rounds, highscore, and the time it took.
    */
     public WinScreen(int xOfObject, int yOfObject){
        this.box = new GraphicsGroup();
        Rectangle back = new Rectangle(xOfObject,yOfObject,250,450);

        back.setStrokeColor(black);
        back.setStroked(true);
        back.setStrokeWidth(5);
        back.setFillColor(grey);
        back.setFilled(true);

        showPoints = new GraphicsText("POINTS: ",xOfObject+30,yOfObject+120);
        showPoints.setFillColor(white);
        showPoints.setFilled(true);
        showPoints.setFont(FontStyle.BOLD,20);

        youWin = new GraphicsText("YOU WIN!",xOfObject+30,yOfObject+70);
        youWin.setFillColor(white);
        youWin.setFilled(true);
        youWin.setFont(FontStyle.BOLD,30);

        showTimer = new GraphicsText("TIME: ",xOfObject+30,yOfObject+170);
        showTimer.setFillColor(white);
        showTimer.setFilled(true);
        showTimer.setFont(FontStyle.BOLD,20);

        showRounds = new GraphicsText("POINTS: ",xOfObject+30,yOfObject+230);
        showRounds.setFillColor(white);
        showRounds.setFilled(true);
        showRounds.setFont(FontStyle.BOLD,20);

        showHighScore = new GraphicsText("HIGHSCORE:  ",xOfObject+30,yOfObject+280);
        showHighScore.setFillColor(white);
        showHighScore.setFilled(true);
        showHighScore.setFont(FontStyle.BOLD,20);

        recordTime = new GraphicsText("RECORD TIME:  ",xOfObject+30,yOfObject+330);
        recordTime.setFillColor(white);
        recordTime.setFilled(true);
        recordTime.setFont(FontStyle.BOLD,20);

        box.add(back);
        box.add(showPoints);
        box.add(showRounds);
        box.add(showTimer);
        box.add(showHighScore);
        box.add(youWin);
        box.add(recordTime);
    }

    /*
     * Sets the time for the win screen and puts it in legible writing
     * @param time is the time.
     */
    public void setTime(int time){
        double timeInSeconds = time / 1000;
        double minutes = timeInSeconds / 60;
        double seconds = timeInSeconds % 60;
        if (timeInSeconds < 60){
            showTimer.setText("TIME: " + (int)timeInSeconds + " SECONDS");
        }else{
            showTimer.setText("TIME: " + (int)minutes + " MINUTES " + System.lineSeparator() + "         " + (int)seconds + " SECONDS");
            }
    }

    /*
     * Sets the record time for the win screen and puts it in legible writing
     * @param time is the time.
     */
    public void setRecordTime(int time){
        double timeInSeconds = time / 1000;
        double minutes = timeInSeconds / 60;
        double seconds = timeInSeconds % 60;
        if (timeInSeconds < 60){
            recordTime.setText("RECORD TIME: " + System.lineSeparator() +  (int)timeInSeconds + " SECONDS");
        }else{
            recordTime.setText("RECORD TIME: " + System.lineSeparator() + (int)minutes + " MINUTES " + System.lineSeparator() + (int)seconds + " SECONDS");
            }
    }

    /*
     * Sets the points for the win screen
     * @param points is the users total points in the round
     */
    public void setPoints(int points){
        showPoints.setText("POINTS: " + points);
    }

    /*
     * Sets the rounds for the win screen
     * @param rounds is the amount of rounds it took the user to win.
     */
    public void setRounds(int rounds){
        showRounds.setText("ROUNDS: " + rounds);
    }

    /*
     * Sets the highScore for the win screen
     * @param highScore is the users high score.
     */
    public void setHighScore(int highScore){
        showHighScore.setText("HIGHSCORE: " + highScore);
    }

    /*
     * Adds the graphics group to the canvas.
     * @param canvas is the canvas you are adding this graphics group to
     */
    public void addToCanvas(CanvasWindow canvas){
        canvas.add(box);
    }
}
