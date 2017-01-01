package cosc241_p0_jatripp0;
import java.io.*;
import java.util.Scanner;
import java.util.regex.*;
import java.util.ArrayList;

/**
 *The Game class handles the responsibilities of creating the SuDoku game from a given input file 
 * and then rewriting the game to the input file once it has been solved by the SudokuSolver class.
 * @author Johnathan Tripp
 */
public class Game {
    
    private SudokuSolver solver;
    //the REGEX string which will be used to locate each line that holds values for the SuDoku puzzle.
    private final String REGEX = "\\|\\|\\s*\\d*\\s*\\|\\s*\\d*\\s*\\|\\s*\\d*\\s*\\|\\|\\s*\\d*\\s*\\|\\s*\\d*\\s*\\|\\s*\\d*\\s*\\|\\|\\s*\\d*\\s*\\|\\s*\\d*\\s*\\|\\s*\\d*\\s*\\|\\|";
    //defines the location of the input text file for the sudoku game.
    private final String gameLocation;
    private Scanner file;
    private FileWriter writer;
    private BufferedWriter out;
    //creates a simple 9x9 grid to hold only the 81 available spaces for the actual Sudoku game.
    private int[][] gameGrid;
    //an ArrayList which will temporarily hold the game before it is written back to the template file.
    private final ArrayList<String> gameTemplate;
    private long elapsedTime;

    /**
     * The constructor of the Game class.
     * Initializes the simple 9x9 game grid and the ArrayList which will
     * temporarily hold the game before being written back to the template file.
     * @param gameLocation the path of the template file from which the game will
     * be created.
     */
    public Game(String gameLocation) {
        this.gameLocation = gameLocation;
        gameGrid = new int[9][9];
        gameTemplate = new ArrayList<>();
    }
    
    /**
     * This method attempts to read in the SuDoku input template file and assigns it to a Scanner.
     * An error message will be print in the event that a IOException is caught.
     */
    public void readGame() {

        try{
            file = new Scanner(new File(gameLocation));
        }
        /*
        Prints an error message informing the user that the file was not found and instructs the user to check the name and location of the input file.
        The program then exits because there is nothing more that can be done.
        */
        catch(IOException e){
            System.out.println("Error: FileNotFound " + gameLocation + " could not be found.\nPlease check that the input file name and location matches\nthe file path: COSC241_P0_jatripp0\\" + gameLocation + " and try again.");
            System.exit(0);
        }
    }
    
    /**
     * This method utilizes a REGEX pattern to search for each relevant line in the template
     * and then, using a matcher, locates each spot on the SuDoku puzzle and extracts that number
     * (0 if blank) to a simple 9x9 grid.
     * @return the 9x9 grid representing the SuDoku game which contains the input file values.
     */
    public int[][] writeToSimpleGrid() {
        
        String gridRow;
        Pattern p = Pattern.compile(REGEX);
        Pattern spotPattern = Pattern.compile("\\s+\\d*\\s+");
        Matcher m,f;
        int i,j=0,result;
        
        while(file.hasNextLine()){
            gridRow = file.nextLine();
            m = p.matcher(gridRow);
            if(m.matches()){
                    f = spotPattern.matcher(gridRow);
                    i = 0;
                    String str;
                    while(f.find()){
                        //temporarily store each subsequence of the gridRow string to place into the gameGrid array
                        str = gridRow.subSequence(f.start(), f.end()).toString();
                        //attempt to parse the string sequence to an integer
                        try{
                            result = Integer.parseInt(str.trim());
                        }
                        //catch the NumberFormatException if the string sequence is blank or contains whitespace
                        catch(NumberFormatException e){
                            result = 0;
                        }
                        gameGrid[i][j] = result;
                        //increment the row index of gameGrid
                        i++;
                    }
                    //increment the column index of gameGrid
                    j++;
            }
        }
        file.close();
        
        return gameGrid;
    }
    
    /**
     * This method will execute the necessary methods to begin a game of SuDoku using the given input file. 
     * This game will then be passed to the solver class to complete the game.
     * @throws IOException in situations where the input file cannot be located.
     */
    public void create() throws IOException {
        readGame();
        writeToSimpleGrid();
        printGameGrid();
    }
    
    /**
     * This method will execute the necessary steps to solve the game of SuDoku
     * using the simple 9x9 grid and the SudokuSolver class.
     * This method also calculates the time the SudokuSolver class takes to solve
     * the game. Currently, the execution time is very fast (0-1 milliseconds)
     * because the SudokuSolver does not do much yet besides solve spaces with 
     * only one possible number.
     */
    public void solve(){
        solver = new SudokuSolver(gameGrid);
        long startTime = System.currentTimeMillis();
        gameGrid = solver.testPossibleNumbers();
        long stopTime = System.currentTimeMillis();
        elapsedTime = stopTime - startTime;
        System.out.println("\n");
        printGameGrid();
        System.out.println("Computation Time: " + elapsedTime + " Milliseconds");
        System.out.println("See output file " + gameLocation);
        System.out.println("See README.txt for details on resetting the game.");
    }
    
    /**
     * Prints out the current state of the SuDoku board for demonstration of the
     * changes made by this program in solving the SuDoku game.
     */
    public void printGameGrid(){
        for(int j=0; j<9; j++){
            for(int i=0; i<9; i++){
                System.out.print(gameGrid[i][j]);
            }
            System.out.println();
        }
    }
    
    /**
     * This method utilizes a FileWriter and BufferedWriter to rewrite the 
     * SuDokuTemplate file once the game has been solved.
     */
    public void finish(){
        
        String gridRow;
        Pattern p = Pattern.compile(REGEX);
        Matcher m;
        int i,j=0;
        
        try{
            file = new Scanner(new File(gameLocation));
            while(file.hasNextLine()){
                gridRow = file.nextLine();
                m = p.matcher(gridRow);
                if(m.matches()){
                    
                    //move to the next column on the board
                    i = 0;
                    //replaces the string for each line with numbers on the board
                    gameTemplate.add("|| " + gameGrid[i][j] + " | " + gameGrid[i+1][j] + " | " + gameGrid[i+2][j] + " || " + gameGrid[i+3][j] + " | " + gameGrid[i+4][j] + " | " + gameGrid[i+5][j] + " || " + gameGrid[i+6][j] + " | " + gameGrid[i+7][j] + " | " + gameGrid[i+8][j] + " ||");
                    
                    /*
                    Here I attempted to replace the old number spaces with the solved numbers
                    Unfortunately, I was unable to find the correct way to do this with the pattern
                    matcher I was using in time, so I opted to replicate the string at each line where the
                    number spaces are located. The desired result is achieved, though the solution is clearly 
                    more verbose and far less elegant.
                    
                    while(f.find()){
                        f.replaceFirst(" " + gameGrid[i][j] + " ");
                        i++;
                        f.reset();
                    }*/
                    
                    //move the scanner to the next row on the board
                    j++;
                }
                //if the next line does not match the REGEX, add the line as-is to the ArrayList
                else{
                    gameTemplate.add(gridRow);
                }
            }
            //initialize the FieWriter and BufferedWriter
            writer = new FileWriter(new File(gameLocation));
            out = new BufferedWriter(writer);
            //write each line in the ArrayList to the output file
            for(String s : gameTemplate){
                out.write(s);
                out.newLine();
            }
            out.write("Computation Time: " + elapsedTime + " Milliseconds");
            file.close();
            out.close();
        }
        catch(IOException e){
            System.out.println("Error: FileNotFound " + gameLocation + " could not be found.\nPlease check that the input file name and location matches\nthe file path: COSC241_P0_jatripp0\\" + gameLocation + " and try again.");
            System.exit(0);
        }
    }
}
