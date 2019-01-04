/*  Name: Reshu Catherine Liang
 *
 *  Execution: Grid g = new Grid(String file);
 * 
 *  Creates a grid using a Sudoku textfile
 *
 */

public class Grid {
    private String file;
    private int[][] grid;
    private int size = 6;
    private int currentRow = -1;
    private int currentCol = -1;
    
    // contructor
    // creates a Sudoku grid with file
    public Grid(String file) {
        In inStream = new In(file);
        this.file = file;
        
        if (this.getRowCount() != size) {
            throw new RuntimeException("ERROR: Row count does not equal " +
                                       size);
        }
        for (int i = 0; i < this.getRowCount(); i++) {
            if (this.getColumnCount(i) != size) {
                throw new RuntimeException("ERROR: Column count does not " + 
                                           "equal " + size);
            }
        }
        
        grid = new int[size][size];
        // goes through every row and reads the line using inStream
        for (int i = 0; i < size; i++) {
            String str = inStream.readLine();
            // goes through every character in the line and stores it in array
            for (int j = 0; j < size; j++) {
                // space is converted into 0
                if (str.charAt(j) == ' ') {
                    grid[i][j] = 0;
                } else if (str.charAt(j) >= '1' && 
                           str.charAt(i) <= '6') {
                    grid[i][j] = str.charAt(j) - '0';
                } else {
                    throw new RuntimeException("ERROR: Sudoku contains " +
                                               "invalid characters");
                }
            }
        }
    }
    
    // returns row count
    public int getRowCount() {
        In inStream = new In(file);
        String str = inStream.readAll();
        int rowCount = 0;
        // counts row number by the number of '\n'
        for (int i = 0; i < str.length(); i++) {
            if (str.charAt(i) == '\n') {
                rowCount += 1;
            }
        }
        // in case the last row doesn't end with '\n'
        if (str.charAt(str.length() - 1) != '\n') {
            rowCount += 1;
        }
        return rowCount;
    }
    
    // returns the column count of one row
    public int getColumnCount(int row) {
        In inStream = new In(file);
        String str = "";
        for (int i = 0; i <= row; i++) {
            str = inStream.readLine();
        }
        return str.length();
    }
    
    // returns a specified row in an array
    public int[] row(int i) {
        if (i < 0 || i >= size) {
            throw new IllegalArgumentException("ERROR: row number is invalid");
        }
        int[] arr = new int[size];
        for (int j = 0; j < size; j++) {
            arr[j] = grid[i][j];
        }
        return arr;
    }
    
    // returns the current row
    public int[] row() {
        return row(currentRow);
    }
    
    // returns a specified column in an array
    public int[] col(int j) {
        if (j < 0 || j >= size) {
            throw new IllegalArgumentException("ERROR: col number is invalid");
        }
        int[] arr = new int[size];
        for (int i = 0; i < size; i++) {
            arr[i] = grid[i][j];
        }
        return arr;
    }
    
    // returns the current column
    public int[] col() {
        return col(currentCol);
    }
    
    // returns the 2 by 3 box in an array
    public int[] twoByThree(int i, int j) {
        int[] arr = new int[size];
        int[] rows;
        int[] columns;
        if (i == 0 || i == 1) {
            int[] x = {0, 1};
            rows = x;
        } else if (i == 2 || i == 3) {
            int[] x = {2, 3};
            rows = x;
        } else if (i == 4 || i == 5) {
            int[] x = {4, 5};
            rows = x;
        // is it none of these rows?
        } else {
            throw new IllegalArgumentException("ERROR: row number is " +
                                               "invalid");
        }
        if (j >= 0 && j <= 2) {
            int[] y = {0, 1, 2}; 
            columns = y;
        } else if (j >= 3 && j <= 5) {
            int[] y = {3, 4, 5};
            columns = y;
        } else {
            throw new IllegalArgumentException("ERROR: col number is " +
                                               "invalid");
        }
        for (int index = 0; index < 3; index++) {
            arr[index] = grid[rows[0]][columns[index]];
            arr[index + 3] = grid[rows[1]][columns[index]];
        }
        return arr;
    }
    
    // returns the current twoByThree
    public int[] twoByThree() {
        return twoByThree(currentRow, currentCol);
    }
    
    // prints the 2D array
    public String toString() {
        String str = "";
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0) {
                    str += " ";
                } else {
                    str += grid[i][j];
                }
            }
            str += "\n";
        }
        return str;
    }
    
    // goes to the next empty box
    public void nextEmptyBox() {
        // if there isn't a current box, start with the first box
        if (currentRow == -1) {
            search(0, 0);
            return;
        }
        // if there is a current row and column, start there
        // if it succeeds, return
        if (search(currentRow, currentCol + 1)) {
            return;
        }
        // if it fails, restart from beginning
        if (search(0, 0)) {
            return;
        }
        // if it fails again, set currentRow to -1 to show grid is full
        currentRow = -1;
    }
    
    // helper function for nextEmptyBox()
    // takes in where to start search, returns true if next empty box is found
    private boolean search(int row, int column) {
        // for the row of the given box, only consider what is right of box
        int i = row;
        for (int j = column; j < size; j++) {
            if (grid[i][j] == 0) {
                currentRow = i;
                currentCol = j;
                return true;
            }
        }
        // for the rest of the rows, box can be any column
        for (i = row + 1; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (grid[i][j] == 0) {
                    currentRow = i;
                    currentCol = j;
                    return true;
                }
            }
        }
        return false;
    }
    
    // gets value of currentRow
    public int getCurrentRow() {
        return currentRow;
    }
    
    public int getCurrentCol() {
        return currentCol;
    }
    
    // sets value of current box
    public void set(int i) {
        grid[currentRow][currentCol] = i;
    }
    
    // gets the grid size
    public int getSize() {
        return size;
    }
    
    public static void main(String[] args) {
        // Usage: java Grid sudokuExample.txt
        Grid x = new Grid(args[0]);
        System.out.println(x.toString());
        System.out.println(x.getRowCount());
        int[] arr = x.twoByThree(2, 4);
        for (int i = 0; i < 6; i++) {
            System.out.print(arr[i]);
        }
        System.out.println();
        for (int i = 0; i < 30; i++) {
            x.nextEmptyBox();
            System.out.println(x.currentRow + ", " + x.currentCol);
        }
    }
}
