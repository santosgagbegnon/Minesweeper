import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.LinkedList;
import javax.swing.*;

@SuppressWarnings("unchecked")
public class GameController implements ActionListener {
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
     * @param numberOfMines
     *            the number of mines hidden in the board
     */
    public GameController(int width, int height, int numberOfMines) {
        this.gameModel = new GameModel(width, height, numberOfMines);
        this.gameView = new GameView(gameModel, this);
    }

    /**
     * Callback used when the user clicks a button (reset or quit)
     *
     * @param e
     *            the ActionEvent
     */

    public void actionPerformed(ActionEvent e) {
        if(e.getActionCommand().equals("Reset")){
            reset();
            gameView.update();

        }
        else if(e.getActionCommand().equals("Quit")){
            System.exit(0);
        }
        else if (e.getSource() instanceof DotButton){
            DotButton currentSquare = (DotButton) e.getSource();

            if (e.getModifiers() == 17){
                play(currentSquare.getColumn(), currentSquare.getRow(), true);


            }
            else {
                play(currentSquare.getColumn(), currentSquare.getRow(), false);

            }
            

        }

    }

    /**
     * resets the game
     */
    private void reset(){
        gameModel.reset();

    }

    /**
     * <b>play</b> is the method called when the user clicks on a square.
     * If that square is not already clicked, then it applies the logic
     * of the game to uncover that square, and possibly end the game if
     * that square was mined, or possibly uncover some other squares. 
     * It then checks if the game
     * is finished, and if so, congratulates the player, showing the number of
     * moves, and gives to options: start a new game, or exit
     * @param width
     *            the selected column
     * @param heigth
     *            the selected line
     */
    private void play(int width, int heigth, boolean flagging){
        if(!gameModel.hasBeenClicked(width,heigth)){
            if(!flagging){
                if(!gameModel.isFlagged(width,heigth)){
                    gameModel.uncover(width,heigth);
                    gameModel.click(width,heigth);

                    if(gameModel.isMined(width,heigth)){
                        gameModel.uncoverAll();
                        gameModel.step();
                        gameView.update();

                        if(gameModel.isFinished()){
                            String message = "Uh oh! You lost at " + gameModel.getNumberOfSteps() + " steps.";
                            Object[] options ={"Play again", "Quit"};
                            int choice = JOptionPane.showOptionDialog(gameView,message,"Boom!",JOptionPane.YES_NO_CANCEL_OPTION,
                            JOptionPane.QUESTION_MESSAGE,null,options,options[1] );

                            if(choice == 0){
                                reset();
                                gameView.update();
                            }
                            else if(choice == 1){
                                reset();
                                System.exit(0);
                            }

                        }
                    }
                    else if(!gameModel.isMined(width,heigth)){
                        if(gameModel.isBlank(width,heigth)){
                            clearZone(gameModel.get(width,heigth));

                        }

                        gameModel.step();
                        gameView.update();

                        if(gameModel.isFinished()){
                            gameModel.uncoverAll();
                            if(gameModel.isFinished()){
                                String message = "Congrats! You won in " + gameModel.getNumberOfSteps() + " steps.";
                                Object[] options ={"Play again", "Quit"};
                                int choice = JOptionPane.showOptionDialog(gameView,message,"Winner!!",JOptionPane.YES_NO_CANCEL_OPTION,
                                JOptionPane.QUESTION_MESSAGE,null,options,options[1] );

                            if(choice == 0){
                                reset();
                                gameView.update();
                            }
                            else if (choice == 1){
                                reset();
                                System.exit(0);
                            }

                        }
                        }
                    }
                }
            }
            else {
                gameModel.setFlagged(width,heigth);
                gameView.update();

            }
        }

        

    }

   /**
     * <b>clearZone</b> is the method that computes which new dots should be ``uncovered'' 
     * when a new square with no mine in its neighborood has been selected
     * @param initialDot
     *      the DotInfo object corresponding to the selected DotButton that
     * had zero neighbouring mines
     */
    private void clearZone(DotInfo initialDot) {
        DotInfo currentDot;
        int capacity = gameModel.getWidth() * gameModel.getHeigth();
        GenericArrayStack floodStack = new GenericArrayStack(capacity);

        floodStack.push(initialDot);

        while(!floodStack.isEmpty()){
            currentDot = (DotInfo) floodStack.pop();

            for(int i = currentDot.getX() - 1; i <= currentDot.getX() + 1; i ++){
                for(int j = currentDot.getY() - 1; j <= currentDot.getY() + 1; j++ ){
                    if((i != currentDot.getX() || j != currentDot.getY()) && (i >= 0 && i < gameModel.getWidth() && (j >= 0 && j < gameModel.getHeigth()))){
                        if(gameModel.isBlank(i,j) && gameModel.isCovered(i,j)){

                            gameModel.uncover(i,j);
                            floodStack.push(gameModel.get(i,j));

                        }
                    }

                }
            }


        }


    }



}
