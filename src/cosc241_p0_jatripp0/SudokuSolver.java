package cosc241_p0_jatripp0;
import java.util.ArrayList;
/**
 *The SudokuSolver class is responsible for finding the solution to the game of
 * SuDoku that comes from the template file.
 * @author Johnathan Tripp
 */
public class SudokuSolver {
    
    private final int[][] gameBoard;
    private final ArrayList<Integer> possibleNumbers;
    
    /**
     * Constructor for the SudokuSolver class
     * Initializes the ArrayList which will hold the count of possible numbers
     * for each space on the game board.
     * @param gameBoard the game board to be solved
     */
    public SudokuSolver(int[][] gameBoard){
        
        this.gameBoard = gameBoard;
        possibleNumbers = new ArrayList<>();
    }
    
    /**
     * For each space on the game board, determines how many possible numbers may
     * be placed in each space. If the count of possible numbers is 1, that number
     * is automatically placed on the game board.
     * @return the altered game board
     */
    public int[][] testPossibleNumbers(){
        for(int i=0; i<9; i++)
            for(int j=0; j<9; j++){
                if(gameBoard[i][j] == 0)
                {
                    int k = 1;
                    possibleNumbers.clear();
                    while(k <= 9){
                        if(isValidRow(k,i) && isValidColumn(k,j) && isValidBlock(k,i,j)){
                            possibleNumbers.add(k);
                        }
                        k++;
                    }
                    if(possibleNumbers.size() == 1){
                        gameBoard[i][j] = possibleNumbers.get(0);
                    }
                }
            }
        return gameBoard;
    }
    
    /**
     * This method accepts a number as parameter as well as the current row index
     * and tests to see if the number is valid within the row being tested.
     * @param num the number to be tested
     * @param row the row index currently being tested
     * @return whether the number being tested is valid for the given row
     */
    public boolean isValidRow(int num, int row){
        for(int i=0; i<9; i++){
            if(num == gameBoard[i][row])
                return false;
        }
        return true;
    }
    
    /**
     * This method accepts a number as parameter as well as the current column index
     * and tests to see if the number is valid within the column being tested.
     * @param num the number to be tested
     * @param column the column index currently being tested
     * @return whether the number being tested is valid for the given column
     */
    public boolean isValidColumn(int num, int column){
        for(int j=0; j<9; j++){
            if(num == gameBoard[column][j])
                return false;
        }
        return true;
    }
    
    /**
     * This method accepts a number as parameter as well as the index of the 
     * number currently stored on the game board and tests to see if the number
     * being tested is valid within the 9x9 block to which the space belongs.
     * @param num the number to be tested
     * @param column the column index of the space being tested
     * @param row the row index of the space being tested
     * @return whether the number being tested is valid for the given block
     */
    public boolean isValidBlock(int num, int column, int row){
        //block 1
        if(row <= 2 && column <= 2){
            for(int i=0; i<3; i++)
                for(int j=0; j<3; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 2
        else if(row <= 2 && column > 2 && column <= 5){
            for(int i=3; i<6; i++)
                for(int j=0; j<3; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 3
        else if(row <= 2 && column > 5 && column <= 8){
            for(int i=6; i<9; i++)
                for(int j=0; j<3; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 4
        else if(row > 2 && row <=5 && column <= 2){
            for(int i=0; i<3; i++)
                for(int j=3; j<6; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 5
        else if(row > 2 && row <=5 && column > 2 && column <= 5){
            for(int i=3; i<6; i++)
                for(int j=3; j<6; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 6
        else if(row > 2 && row <=5 && column > 5 && column <= 8){
            for(int i=6; i<9; i++)
                for(int j=3; j<6; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 7
        else if(row > 5 && row <= 8 && column <= 2){
            for(int i=0; i<3; i++)
                for(int j=6; j<9; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 8
        else if(row > 5 && row <= 8 && column > 2 && column <= 5){
            for(int i=3; i<6; i++)
                for(int j=6; j<9; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        //block 9
        else if(row > 5 && row <= 8 && column > 2 && column <= 5){
            for(int i=6; i<9; i++)
                for(int j=6; j<9; j++)
                    if(num == gameBoard[i][j])
                        return false;
        }
        
        return true;
    }
}
