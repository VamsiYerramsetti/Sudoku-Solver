import java.util.ArrayList;
import java.util.Arrays;

public class Game {
  private Sudoku sudoku;

  Game(Sudoku sudoku) {
    this.sudoku = sudoku;
  }

  public void showSudoku() {
    System.out.println(sudoku);
  }

  /**
   * Implementation of the AC-3 algorithm
   * 
   * @return true if the constraints can be satisfied, else false
   */
  public boolean solve() {
    AC3 ac3 = new AC3(sudoku.getBoard());
    return ac3.solve();
  }

  /**
   * Checks the validity of a sudoku solution
   * 
   * @return true if the sudoku solution is correct
   */
  public boolean validSolution() {
    //Check all rows
    for(int row = 0; row < 9; row++){
      ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
      for(int col = 0; col < 9; col++){
        if(!numbers.remove(Integer.valueOf(sudoku.getBoard()[row][col].getValue()))){
          return false;
        }
      }

      if(numbers.size() > 0) return false;
    }

    //Check all columns
    for(int col = 0; col < 9; col++){
      ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
      for(int row = 0; row < 9; row++){
        if(!numbers.remove(Integer.valueOf(sudoku.getBoard()[row][col].getValue()))){
          return false;
        }
      }

      if(numbers.size() > 0) return false;
    }

    //Check all 9 boxes
    for(int box = 0; box < 9; box++){
      ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6,7,8,9));
      for(int row = 0; row < 3; row++){
        for(int col = 0; col < 3; col++){
          int rowOnGrid = box - (box % 3);
          int colOnGrid = (box % 3) * 3;
          if(!numbers.remove(Integer.valueOf(sudoku.getBoard()[rowOnGrid+row][colOnGrid+col].getValue()))){
            return false;
          }
      }
      }

      if(numbers.size() > 0) return false;
    }

    //Everything passed
    return true;
  }
}
