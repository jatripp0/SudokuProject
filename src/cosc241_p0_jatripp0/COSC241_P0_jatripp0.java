package cosc241_p0_jatripp0;

import java.io.IOException;

/**
 * This is a project to solve a game of SuDoku from an input file as a template.
 * This project contains two classes, Game and SudokuSolver whose responsibilities
 * are to handle the file input/output and processing of the puzzle to find a
 * solution, respectively.
 * @author Johnathan Tripp
 * @version 1.0
 */
public class COSC241_P0_jatripp0 {
   
    public static void main(String[] args) throws IOException {
        
        Game game = new Game("SuDokuTemplate.txt");
        game.create();
        game.solve();
        game.finish();
    }
    
}
