public class DotInfo {
    private  boolean covered;
    private boolean mined;
    private boolean wasClicked;
    private int neighbooringMines;
    private int x;
    private int y;
    private boolean flagged;
    /**
     * Constructor, used to initialize the instance variables
     * 
     * @param x
     *            the x coordinate
     * @param y
     *            the y coordinate
     */
    public DotInfo(int x, int y){
        this.x = x;
        this.y = y;
        this.covered = true;
        this.mined = false;
        this.wasClicked = false;
        this.neighbooringMines = 0;
        this.flagged = false;
    }
    /**
     * Getter method for the attribute x.
     * 
     * @return the value of the attribute x
     */
    public int getX(){
        return this.x;

    }
    /**
     * Getter method for the attribute y.
     * 
     * @return the value of the attribute y
     */
    public int getY(){
        return this.y;
    }
    /**
     * Setter for mined
     */
    public void setMined() {
        this.mined = true;
        
    }

    /**
     * Getter for mined
     *
     * @return mined
     */
    public boolean isMined() {
        return this.mined;

    }
    /**
     * Setter for covered
     */
    public void uncover() {
        this.covered = false;
    
    }

    /**
     * Getter for covered
     *
     * @return covered
     */
    public boolean isCovered(){
        return this.covered;
    }



    /**
     * Setter for wasClicked
     */
    public void click() {
        this.wasClicked = true;
    }

    /**
     * Getter for wasClicked
     *
     * @return wasClicked
     */
    public boolean hasBeenClicked() {
        return this.wasClicked;

    }


    /**
     * Setter for neighbooringMines
     *
     * @param neighbooringMines
     *          number of neighbooring mines
     */
    public void setNeighbooringMines(int neighbooringMines) {
        this.neighbooringMines = neighbooringMines;

    }

    /**
     * Get for neighbooringMines
     *
     * @return neighbooringMines
     */
    public int getNeighbooringMines() {
       
        return this.neighbooringMines;


    }

    public void setFlagged(){
        if(this.flagged == true){
            this.flagged = false;
        }
        else if(this.flagged == false){
            this.flagged = true;
        }
    }

    public boolean isFlagged(){
        return this.flagged;

    }

 }
