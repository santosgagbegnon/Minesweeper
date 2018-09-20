import java.util.Random;

public class GameModel {
    private Random generator;
    private int heigthOfGame;
    private DotInfo[][] model;
    private int numberOfMines;
    private int numberOfSteps;
    private int numberUncovered;
    private int numberFlagged;
    private int widthOfGame;
    /**
     * Constructor to initialize the model to a given size of board.
     * 
     * @param width
     *            the width of the board
     * 
     * @param heigth
     *            the heigth of the board
     * 
     * @param numberOfMines
     *            the number of mines to hide in the board
     */
    public GameModel(int width, int heigth, int numberOfMines) {
        int minesAdded = 0;
        this.widthOfGame = width;
        this.heigthOfGame  = heigth;
        this.numberOfMines = numberOfMines;
        this.generator = new Random();
        this.model = new DotInfo[width][heigth];
        this.numberOfSteps = 0;
        this.numberUncovered = 0;
        this.numberFlagged = 0;

        //Populates model with DotInfos
        for(int i = 0; i < width; i++){
            for(int j = 0; j < heigth; j++){
                this.model[i][j] = new DotInfo(i,j);
 
            }
        }

        //Adds # of mines requested in random locations
        while(minesAdded < this.numberOfMines){
            int randomI = generator.nextInt(widthOfGame);
            int randomJ = generator.nextInt(heigthOfGame);

            //If the location is not already mined, a mine is placed and the counter for mined is increased.
            if(!model[randomI][randomJ].isMined()){
                model[randomI][randomJ].setMined();
              //  System.out.println("RandomI: " + randomI + " RandomJ: " + randomJ);


                //Once a mine is placed, all of its neighbours are notified and their #ofneighbouring
                //mines is incremented by one
                for(int i = randomI - 1; i < randomI + 2; i++){
                    for(int j = randomJ - 1; j < randomJ + 2; j++){


                        //Checks if the position is out of bounds (for corner places) &
                        //ensures we are not looking at the current position (randomI, randomJ)
                        if((i>=0 && i < widthOfGame) &&(j>=0 && j < heigthOfGame) && (i != randomI || j != randomJ)){

                            int new_neighbours = model[i][j].getNeighbooringMines() + 1;
                            model[i][j].setNeighbooringMines(new_neighbours);

                        }
                    }

                }
                //Increase total mine count
                minesAdded++;

            }

        }

    }
 
    /**
     * Resets the model to (re)start a game. The previous game (if there is one)
     * is cleared up . 
     */
    public void reset(){    
        GameModel newGameModel = new GameModel(this.widthOfGame, this.heigthOfGame, this.numberOfMines);
        this.model = newGameModel.model;
        this.numberOfSteps = 0;
        this.numberUncovered = 0;
        this.numberFlagged = 0;
    }


    /**
     * Getter method for the heigth of the game
     * 
     * @return the value of the attribute heigthOfGame
     */   
    public int getHeigth(){
        return this.heigthOfGame;
        
    }

    /**
     * Getter method for the width of the game
     * 
     * @return the value of the attribute widthOfGame
     */   
    public int getWidth(){
        
        return this.widthOfGame;

    }



    /**
     * returns true if the dot at location (i,j) is mined, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isMined(int i, int j){
        return model[i][j].isMined();
    }

    /**
     * returns true if the dot  at location (i,j) has 
     * been clicked, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean hasBeenClicked(int i, int j){
        
        return model[i][j].hasBeenClicked();

    }

  /**
     * returns true if the dot  at location (i,j) has zero mined 
     * neighboor, false otherwise
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isBlank(int i, int j){
        if(model[i][j].getNeighbooringMines() == 0 && !model[i][j].isMined()){
            return true;
        }

        return false;
        
    }
    /**
     * returns true if the dot is covered, false otherwise
    * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the status of the dot at location (i,j)
     */   
    public boolean isCovered(int i, int j){
        return model[i][j].isCovered();
        
    }

    /**
     * returns the number of neighbooring mines os the dot  
     * at location (i,j)
     *
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     * @return the number of neighbooring mines at location (i,j)
     */   
    public int getNeighbooringMines(int i, int j){
        return this.model[i][j].getNeighbooringMines();

    }
    /**
     * Sets the status of the dot at location (i,j) to uncovered
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void uncover(int i, int j){
        this.model[i][j].uncover();
        this.numberUncovered++;
    
    }

    /**
     * Sets the status of the dot at location (i,j) to clicked
     * 
     * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     */   
    public void click(int i, int j){
        this.model[i][j].click();

        

    }
     /**
     * Uncover all remaining covered dot
     */   
    public void uncoverAll(){
        int length = this.model.length;
        for(int i = 0; i < length; i++){
            for(int j = 0; j < this.model[i].length; j++)
            if(this.model[i][j].isCovered()){
                this.model[i][j].uncover();
            }
        }

    }

 

    /**
     * Getter method for the current number of steps
     * 
     * @return the current number of steps
     */   
    public int getNumberOfSteps(){
        return this.numberOfSteps;
        
    }

    /**
     * Getter method for the model's dotInfo reference
     * at location (i,j)
     *
      * @param i
     *            the x coordinate of the dot
     * @param j
     *            the y coordinate of the dot
     *
     * @return model[i][j]
     */   
    public DotInfo get(int i, int j) {
        return model[i][j];
    }
   /**
     * The metod <b>step</b> updates the number of steps. It must be called 
     * once the model has been updated after the payer selected a new square.
     */
     public void step(){
        this.numberOfSteps++;
    }

   /**
     * The metod <b>isFinished</b> returns true iff the game is finished, that
     * is, all the nonmined dots are uncovered.
     *
     * @return true if the game is finished, false otherwise
     */
    public boolean isFinished(){
        for(int i = 0; i < this.model.length; i++){
            for(int j = 0; j < this.model[i].length; j++){
                //Checks to see if a non-mined step is still dovered
                if(this.model[i][j].isCovered() && !this.model[i][j].isMined()){
                    return false;
                }
            }
        }

        return true;
        
    }

   /**
     * Builds a String representation of the model
     *
     * @return String representation of the model
     */
    public String toString(){
        return "Height of game: " + this.heigthOfGame + "\n"+ "Width of game: "  + this.widthOfGame +"\n" + "# of mines: " + this.numberOfMines + "\n" + "# of steps: " 
        + this.numberOfSteps + "\n" + "# of uncovered: " + this.numberUncovered;

    }

    public boolean isFlagged(int i, int j){
        return this.model[i][j].isFlagged();
    }

    public void setFlagged(int i, int j){
        this.model[i][j].setFlagged();

        if(this.model[i][j].isFlagged()){
            numberFlagged++;
        }
        else{
            numberFlagged--;
        }
    }

    public int getNumberFlagged() {
        return this.numberFlagged;
    }

    public int getNumberOfMines(){
        return this.numberOfMines;
    }
    
}
