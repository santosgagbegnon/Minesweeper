import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class GameView extends JFrame {
    private DotButton[][] board;
    private GameModel gameModel; 
    private JLabel nbreOfStepsLabel;
    private JLabel differenceLabel; 

   /**
     * Constructor used for initializing the Frame
     * 
     * @param gameModel
     *            the model of the game (already initialized)
     * @param gameController
     *            the controller
     */

    public GameView(GameModel gameModel, GameController gameController){
        super("Minesweeper");
        JButton reset_button;
        JButton quit_button;
        JPanel boardPanel;
        JPanel bottomPanel;


        boardPanel = new JPanel();
        bottomPanel = new JPanel();
        reset_button = new JButton("Reset");
        reset_button.addActionListener(gameController);
        quit_button = new JButton("Quit");
        quit_button.addActionListener(gameController);


        this.gameModel = gameModel;
        this.board = new DotButton[gameModel.getWidth()][gameModel.getHeigth()];
        this.differenceLabel = new JLabel("Number of Unfounded Mines: " + gameModel.getNumberOfMines());

        this.nbreOfStepsLabel = new JLabel("Number of Steps: " + gameModel.getNumberOfSteps());

        //Creating the DotButtons
        for(int j = 0; j < gameModel.getHeigth(); j++){
            for(int i = 0; i < gameModel.getWidth(); i++){
                board[i][j] = new DotButton(i,j,11);
                board[i][j].addActionListener(gameController);
                boardPanel.add(board[i][j]);
            }
        }
       
        boardPanel.setLayout(new GridLayout(gameModel.getHeigth(), gameModel.getWidth(),1,1));
        this.add(boardPanel);
        this.setSize(600,400);
        this.setResizable(false);

      
        // Make Lower Panel with buttons and counter
        this.add(bottomPanel, "South");    
        bottomPanel.setPreferredSize(new Dimension(20, 35));
        bottomPanel.add(this.differenceLabel);
        bottomPanel.add(this.nbreOfStepsLabel);
        bottomPanel.add(reset_button);
        bottomPanel.add(quit_button);
        bottomPanel.setBackground(Color.white);

        //Generic Operation Additions
        this.setBackground(Color.white);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setLocationRelativeTo(null);

    }
    /**
     * update the status of the board's DotButton instances based 
     * on the current game model, then redraws the view
     */

    public void update(){
        this.nbreOfStepsLabel.setText("Number of steps: " + gameModel.getNumberOfSteps());
        this.differenceLabel.setText("Number of Unfounded Mines: " + (gameModel.getNumberOfMines() - gameModel.getNumberFlagged()));

        for(int i = 0; i < gameModel.getWidth(); i ++){
            for(int j = 0; j < gameModel.getHeigth(); j++){
                if(!gameModel.isCovered(i,j)){
                    board[i][j].setIconNumber(getIcon(i,j));

                }
                else if (gameModel.isFlagged(i,j)){
                    board[i][j].setIconNumber(12);
                }
                else {
                    board[i][j].setIconNumber(11);
                }
                
            }
        }

    


       
    }

    /**
     * returns the icon value that must be used for a given dot 
     * in the game
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the icon to use for the dot at location (i,j)
     */   
    private int getIcon(int i, int j){
        if(gameModel.isMined(i,j) && !gameModel.hasBeenClicked(i,j)){
            return 9;
        }
        else if(gameModel.isMined(i,j) && gameModel.hasBeenClicked(i,j)){
            return 10;
        }
        else{
            return gameModel.getNeighbooringMines(i,j);

        }
        

    }


}
