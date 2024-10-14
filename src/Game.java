import java.awt.Color;
import java.util.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import edu.macalester.graphics.CanvasWindow;
import edu.macalester.graphics.GraphicsText;
import edu.macalester.graphics.Line;
import edu.macalester.graphics.events.Key;
import edu.macalester.graphics.ui.Button;
import edu.macalester.graphics.FontStyle;

public class Game {
    private static final int CANVAS_WIDTH = 600;
    private static final int CANVAS_HEIGHT = 800;
    private static final Color black = new Color(0,0,0);
    private static final Color red  = new Color(225,0,0);

    private String guessWord;
    private CanvasWindow canvas;
    private GraphicsText prompt;
    private GraphicsText miniPrompt;
    private String assignedWord;
    private ArrayList<WordleSquare> listOfSquares = new ArrayList<WordleSquare>();
    private int letterCount;
    private int rounds;
    private int yOfBoxes;
    private Button playAgain;
    private WordList list;
    private GraphicsText wordExist;
    private Dictionary dictionary;
    private Answer box;
    private GraphicsText time;
    private double startTime;
    private double endTime;
    private boolean runTimer;
    private int totalPoints;
    private GraphicsText showPoints;
    private int highScore;
    private double recordTime;
    private GraphicsText showHighScore;
    private WinScreen winScreen;
    

    /**
    * Creates the game wordle.
    */
    public Game() {
        canvas = new CanvasWindow("Wordle!", CANVAS_WIDTH, CANVAS_HEIGHT);
        list = new WordList();
        dictionary = new Dictionary();
        highScore = 0;
        recordTime = 1000000000;
        reset();
        createControls();
    }

    /**
    * This method creates the controls and checks the conditions for the game Wordle.
    */
    private void createControls(){
        canvas.onKeyDown((key) -> { if(((key.getKey() == Key.RETURN_OR_ENTER))&& letterCount == 5 && wordExist()){
            checkLine();
            calcPoints();
            letterCount = 0;
            removeBoxesFromList();
            yOfBoxes = yOfBoxes + 120;
            rounds = rounds + 1;
            if( rounds < 5 && !checkWin()){
                makeBoxes(5, yOfBoxes);
            }
            if(rounds == 5 && !checkWin()){
                runTimer = false;
                box.addToCanvas(canvas);
                calcHighScore();
            }
    
            guessWord = "";
        }});

        canvas.animate(() -> {
            if(runTimer){
                setTime();
            }
        });
      
        canvas.onKeyDown((key) -> {if((key.getKey() == Key.DELETE_OR_BACKSPACE)){
            wordExist.setText(" ");
            deleteFunction();
        }});

        canvas.onKeyDown((key) -> { if(checkIfValidLetter(key.getKey().toString()) && letterCount < 5){
            createGuessedWord(key.getKey().toString().toUpperCase());
            setLetter(letterCount, key.getKey().toString().toUpperCase());
            letterCount++;
        }

        playAgain.onClick(() -> { removeWord(); reset();});
            canvas.add(playAgain);
        });
    }

     /**
    * This method checks if the player has won the game and if so adds the winning screan to the canvas.
    * @return a boolean if the user wins the game.
    */
    private boolean checkWin(){
        if(guessWord.toLowerCase().equals(assignedWord)){
            runTimer = false;
            calcHighScore();
            calcRecordTime();
            winScreen.addToCanvas(canvas);
            winScreen.setPoints(totalPoints);
            winScreen.setRounds(rounds);
            winScreen.setTime((int)(endTime - startTime));
            winScreen.setHighScore(highScore);
            winScreen.setRecordTime((int)recordTime);
            return true;
        }else{
            runTimer = true;
            return false;
        }
    }

    /**
    * This method creates the game board for wordle.
    */
    private void createBoard(){
        playAgain = new Button("New Game");
        playAgain.setCenter(300,130);

       Line leftLine = new Line(5,5,5,775);
        leftLine.setStrokeColor(black);
        leftLine.setStroked(true);
        leftLine.setStrokeWidth( 5);
        canvas.add(leftLine);

        Line rightLine = new Line(595,5,595,775);
        rightLine.setStrokeColor(black);
        rightLine.setStroked(true);
        rightLine.setStrokeWidth( 5);
        canvas.add(rightLine);

        Line bottomLine = new Line(5,775,595,775);
        bottomLine.setStrokeColor(black);
        bottomLine.setStroked(true);
        bottomLine.setStrokeWidth( 5);
        canvas.add(bottomLine);

       Line topLine = new Line(5,5,595,5);
        topLine.setStrokeColor(black);
        topLine.setStroked(true);
        topLine.setStrokeWidth( 5);
        canvas.add(topLine);

        prompt = new GraphicsText("Guess The Word!",200,60);
        prompt.setFillColor(black);
        prompt.setFilled(true);
        prompt.setFont(FontStyle.BOLD,20);
        canvas.add(prompt);

        wordExist = new GraphicsText("",170,160);
        wordExist.setFontSize(20);
        wordExist.setFillColor(red);
        wordExist.setFilled(true);
        canvas.add(wordExist);

        miniPrompt = new GraphicsText("Each box is a letter, the word is 5 letters long!",120,100);
        miniPrompt.setFont(FontStyle.BOLD,15);
        miniPrompt.setFillColor(black);
        miniPrompt.setFilled(true);
        canvas.add(miniPrompt);

        showHighScore = new GraphicsText("HIGH SCORE: " + highScore, 411, 55);
        showHighScore.setFont(FontStyle.BOLD, 15);
        showHighScore.setFillColor(black);
        showHighScore.setFilled(true);
        canvas.add(showHighScore);

        showPoints = new GraphicsText("POINTS: ", 450, 35);
        showPoints.setFont(FontStyle.BOLD,15);
        showPoints.setFillColor(black);
        showPoints.setFilled(true);
        canvas.add(showPoints);
        time = new GraphicsText("TIME: ",20,35);
        time.setFont(FontStyle.BOLD,15);
        time.setFillColor(black);
        time.setFilled(true);
        canvas.add(time);

        makeBoxes(5, yOfBoxes);
    }

    /**
    * This method resets the game.
    */
    private void reset(){
        runTimer = true;
        assignedWord = list.returnRandomWord();
        guessWord = "";
        letterCount = 0;
        rounds = 0;
        yOfBoxes = 185;
        listOfSquares.clear();
        box = new Answer(235,400,assignedWord );
        winScreen = new WinScreen(205,245);
        canvas.removeAll();
        createBoard();
        startTime = System.currentTimeMillis();
        totalPoints = 3000;
    }
    /**
    * This method removes the randomly generated word from the list of words so while playing the game 
    * you do not get the same word multiple times.
    */
    private void removeWord(){
        list.removeWord(assignedWord);
    }

    /**
    * This method checks if a word exists in regards to our dictionary.
    * @return a boolean based off of if the players guessed word exists in our dictionary.
    */
    private boolean wordExist(){
        if (dictionary.contains(guessWord.toLowerCase())){
            return true;
        }else{
            wordExist.setText("This word does not exist");
            return false;
        }
    }

    /**
    * This method sets a boxes letter to being what the user typed.
     * @param i is an integer representing the current box being changed
     * @param letter is the letter being passed to the box.
    */
    private void setLetter(int i, String letter){
        listOfSquares.get(i).setTypedLetter(letter,canvas);
    }

    /**
    * This method creates the delete function so someone can delete a letter from a box.
    */
    private void deleteFunction(){
        if (letterCount > 0){
            letterCount = letterCount - 1;
            WordleSquare current = listOfSquares.get(letterCount);
            current.removeLetter(canvas);
            // ysf wuz here
            guessWord = guessWord.substring(0,guessWord.length()-1);
        }
    }

    /**
    * This method checks if what the user is clicking on the keyboard is a letter.
    * @return a boolean if the key pressed is a letter.
    * @param s is a string of the key pressed.
    */
    private boolean checkIfValidLetter(String s){
        if(Character.isLetter(s.charAt(0)) && (s.length() < 2)){
            return true;
        }else{
            return false;
        }
    }

    /**
    * This method makes the wordle boxes on the screen.
    */
    private void makeBoxes(int boxes, int y){
        int x = 13;
       int i = 0;
        while(listOfSquares.size() < boxes){
            WordleSquare newSquare = new WordleSquare(x, y,i, assignedWord);
            listOfSquares.add(newSquare);
            newSquare.addToCanvas(canvas);
            x = x + 120;
            i++;
        }
    }

    /**
    * This method sets the current time on the board.
    */
    private void setTime(){
            endTime = System.currentTimeMillis();
            double timeInSeconds = (endTime - startTime) / 1000;
            double minutes = timeInSeconds / 60;
            double seconds = timeInSeconds % 60;
            if (timeInSeconds < 60){
                time.setText("TIME: " + (int)timeInSeconds + " SECONDS");
            }else{
                time.setText("TIME: " + (int)minutes + " MINUTES " + (int)seconds + " SECONDS");
            }
    }

    /**
    * This method calculates the total points someone achieves while playing, on the last round if everything has been wrong you get 0 points.
    */
    private void calcPoints(){
        for(WordleSquare s: listOfSquares){
            if(rounds == 0 && guessWord.toLowerCase().equals(assignedWord)){
                totalPoints = 5000;
                break;
            }
            if(s.isYellow()){
                totalPoints = totalPoints - (37 * rounds);
            }else 
            if (!s.isGreen() && !s.isYellow()){
                totalPoints = totalPoints - (75 * rounds);
            }
            if(totalPoints < 0){
                totalPoints = 0;
            }
        }
            showPoints.setText("POINTS: " + totalPoints);
        }

    /**
    * This method calculates the highscore by comparing the amount of points someone achieved and the previous high score.
    */
    private void calcHighScore() {
        if (totalPoints > highScore) {
            highScore = totalPoints;
            showHighScore.setText("HIGH SCORE " + highScore);
        }
    } 

     /**
    * This method calculates the record time by comparing the amount of time it took to get an answer right versus the previous personal record.
    */
    private void calcRecordTime() {
        endTime = System.currentTimeMillis();
        double currentTime = endTime - startTime;
        if (recordTime > currentTime) {
            recordTime = currentTime;
        }
    }

    /**
    * This method removes the current boxes from the list.
    */
    private void removeBoxesFromList(){
        listOfSquares.clear();
    }

    /**
    * This method goes through the current boxes and checks each one to see if it was correct.
    */
    private void checkLine(){
        for (WordleSquare s: listOfSquares){
            s.checkSquare(canvas);
        }
        correctRepeats();
    }

    /**
    * This method checks for repeating letters with a given word.
    * @return a the repeating letter.
    * @param word which is the word being checked for repeating letters.
    */
    private char checkForRepeats(String word){
        Map<Character, Integer> letterCountMap = new HashMap<>();
        List<Character> letters = new ArrayList<>();
        for(int i = 0; i < word.length(); i++){
            letters.add(word.charAt(i));
        }

        letters.forEach(s -> {
            letterCountMap.putIfAbsent(s,1);
            letterCountMap.computeIfPresent(s, (k, v)-> v + 1);
        });

        for(char letter : word.toLowerCase().toCharArray()){
            if (letterCountMap.get(letter) > 2){
                return letter;
            }
        }
        return '*';
    }

    /**
    * This method goes through the current boxes that are set, checks for repeating letters and corrects them if they are the wrong color.
    */
    private void correctRepeats(){
        String repeatingAssignedLetter = String.valueOf(checkForRepeats(assignedWord));
        String repeatingGuessedLetter = String.valueOf(checkForRepeats(guessWord.toLowerCase()));
        String guessWordAltered = "";

        if(!repeatingAssignedLetter.equals(repeatingGuessedLetter)){
            for (int i = 0; i < guessWord.length(); i++){
                if(String.valueOf(guessWord.toLowerCase().charAt(i)).equals(repeatingGuessedLetter) && !listOfSquares.get(i).isGreen()){
                    listOfSquares.get(i).setRed(canvas);
                    StringBuilder sb = new StringBuilder(guessWord);
                    sb.deleteCharAt(guessWord.toLowerCase().indexOf(repeatingGuessedLetter));
                    guessWordAltered = sb.toString().toUpperCase();
                    repeatingGuessedLetter = String.valueOf(checkForRepeats(guessWordAltered.toLowerCase()));
                    break;
                }
            }
            if(!repeatingAssignedLetter.equals(repeatingGuessedLetter)){
                if(!repeatingAssignedLetter.equals(repeatingGuessedLetter)){
                    for (int i = 0; i < guessWordAltered.length(); i++){
                        if(String.valueOf(guessWordAltered.toLowerCase().charAt(i)).equals(repeatingGuessedLetter) && !listOfSquares.get(i).isGreen()){
                            listOfSquares.get(i+1).setRed(canvas);
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
    * This method creates the users gussed word by adding each letter to a string.
    */
    private void createGuessedWord(String s){
        guessWord = guessWord + s;
    }

    /**
    * This runs the worlde game.
    */
    public static void main(String[] args) {
       new Game(); 
    }
}
