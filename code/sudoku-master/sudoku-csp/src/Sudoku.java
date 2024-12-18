import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class Sudoku {
  private Field[][] board;

  Sudoku(String filename) {
    this.board = readsudoku(filename);
  }

  @Override
  public String toString() {
    String output = "╔═══════╦═══════╦═══════╗\n";
		for(int i=0;i<9;i++){
      if(i == 3 || i == 6) {
		  	output += "╠═══════╬═══════╬═══════╣\n";
		  }
      output += "║ ";
		  for(int j=0;j<9;j++){
		   	if(j == 3 || j == 6) {
          output += "║ ";
		   	}
         output += board[i][j] + " ";
		  }
		  
      output += "║\n";
	  }
    output += "╚═══════╩═══════╩═══════╝\n";
    return output;
  }

  /**
	 * Reads sudoku from file
	 * @param filename
	 * @return 2d int array of the sudoku
	 */
	public static Field[][] readsudoku(String filename) {
		assert filename != null && filename != "" : "Invalid filename";
		String line = "";
		Field[][] grid = new Field[9][9];
		try {
		FileInputStream inputStream = new FileInputStream(filename);
        Scanner scanner = new Scanner(inputStream);
        for(int i = 0; i < 9; i++) {
        	if(scanner.hasNext()) {
        		line = scanner.nextLine();
        		for(int j = 0; j < 9; j++) {
              int numValue = Character.getNumericValue(line.charAt(j));
              if(numValue == 0) {
                grid[i][j] = new Field();
              } else if (numValue != -1) {
                grid[i][j] = new Field(numValue);
        			}
        		}
        	}
        }
        scanner.close();
		}
		catch (FileNotFoundException e) {
			System.out.println("error opening file: "+filename);
		}
    addNeighbours(grid);
		return grid;
	}

  /**
   * Adds a list of neighbours to each field, i.e., arcs to be satisfied
   * @param grid
   */
  private static void addNeighbours(Field[][] grid) {
    for(int row = 0; row < 9; row++){
      for(int col = 0; col < 9; col++){
        ArrayList<Field> neighbours = new ArrayList<>();
        
        //Add all neighbours from the same row
        for(int i = 0; i < 9; i++){
          //Don't add itself to it's neighbours
          if(i==row) continue;
          neighbours.add(grid[i][col]);
        }

        //Add all neighbours from the same column
        for(int i = 0; i < 9; i++){
          //Don't add itself to it's neighbours
          if(i==col) continue;
          neighbours.add(grid[row][i]);
        }

        int box = getBox(row,col);
        //Add all neighbours from the same box
        for(int i = 0; i < 3; i++){
          for(int j = 0; j< 3; j++){
            int rowOnGrid = box - (box % 3);
            int colOnGrid = (box % 3) * 3;
            //Don't add itself to it's neighbours
            if(i+rowOnGrid==row && j+colOnGrid == col || neighbours.contains(grid[i+rowOnGrid][j+rowOnGrid])) continue;
            neighbours.add(grid[i+rowOnGrid][j+colOnGrid]);
          }
        }

        grid[row][col].setNeighbours(neighbours);
      }
    }
  }

  //Helper function to get the box of a field
  private static int getBox(int row, int col){
    int box = 0;
    if(row < 3){
      if(col < 3) box = 0;
      else if(col < 6){box = 1;}
      else if(col < 9) {box = 2;}
    }
    else if(row < 6){
      if(col < 3) box = 3;
      else if(col < 6){box = 4;}
      else if(col < 9) {box = 5;}
    }
    else if(row < 9){
      if(col < 3) box = 6;
      else if(col < 6){box = 7;}
      else if(col < 9) {box = 8;}
    }

    return box;
  }

  /**
	 * Generates fileformat output
	 */
	public String toFileString(){
    String output = "";
    for (int i = 0; i < board.length; i++) {
      for (int j = 0; j < board[0].length; j++) {
        output += board[i][j].getValue();
      }
      output += "\n";
    }
    return output;
	}

  public Field[][] getBoard(){
    return board;
  }
}
