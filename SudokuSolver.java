/*  Name: Reshu Catherine Liang
 *
 *  Execution: java SudokuSolver sudokuExample.txt
 * 
 *  Solves 6x6 Sudokus
 *
 */

import java.util.ArrayList;

public class SudokuSolver {
    
    // checks if Sudoku is valid by going through each row, column and 2-by-3
    public void check(Grid grid) {
        for (int i = 0; i < grid.getSize(); i++) {
            checkRepeats(grid.row(i));
            checkRepeats(grid.col(i));
            // goes through top left box, the two middle ones, and bottom right
            checkRepeats(grid.twoByThree(i, i));
            // goes through top right, the two middle ones, and bottom left
            checkRepeats(grid.twoByThree(i, 5 - i));
        }
    }
    
    // checks for repeats in an array of 6 numbers
    public void checkRepeats(int[] arr) {
        for (int i = 0; i < arr.length; i++) {
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[i] != 0 && arr[j] != 0 && arr[i] == arr[j]) {
                    throw new RuntimeException("ERROR: Input sudoku is " + 
                                               "not valid");
                }
            }
        }
    }
        
    // resets the possibleSolutions array and fills it with numbers 1-6
    public void resetSolutions(ArrayList<Integer> solutions) {
        solutions.clear();
        for (int i = 1; i < 7; i++) {
            solutions.add(i);
        }
    }
    
    // does process of elimination to find possible solution
    public void poe(int[] arr, ArrayList<Integer> solutions) {
        for (int i = 0; i < 6; i++) {
            for (int j = 0; j < solutions.size(); j++) {
                if (arr[i] == solutions.get(j)) {
                    solutions.remove(j);
                }
            }
        }
    }
    
    // goes through each blank and solves Sudoku
    // returns true if succeeds
    public boolean solve(Grid grid) {
        grid.nextEmptyBox();
        // marks the first empty box
        int markRow = grid.getCurrentRow();
        int markCol = grid.getCurrentCol();
        while (grid.getCurrentRow() != -1) {            
            // if a solution is found, record location of next empty box
            if (findSolution(grid)) {
                grid.nextEmptyBox();
                markRow = grid.getCurrentRow();
                markCol = grid.getCurrentCol();
            // if a solution isn't found, go to next empty box
            } else {
                grid.nextEmptyBox();
                // if it's been a whole round and we still don't find a solution
                // Sudoku cannot be solved by simple logic
                if (markRow == grid.getCurrentRow() && 
                    markCol == grid.getCurrentCol()) {
                    return false;
                }
            }
        }
        return true;
    }
    
    // allows you to see the grid after a certain number of steps
    public void step(Grid grid, int step) {
        for (int i = 0; i < step; i++) {
            grid.nextEmptyBox();
            findSolution(grid);
        }
    }
    
    // tries process of elimination, fills in solution if succeeds
    // returns true if a solution is found
    public boolean findSolution(Grid grid) {
        ArrayList<Integer> solutions = new ArrayList<Integer>();
        resetSolutions(solutions);
        poe(grid.row(), solutions);
        poe(grid.col(), solutions);
        poe(grid.twoByThree(), solutions);
        if (solutions.size() == 1) {
            int solution = solutions.get(0);
            grid.set(solution);
            return true;
        }
        return false;
    }
    
    public static void main(String[] args) {
        // Usage: java SudokuSolver sudokuExample.txt
        Grid grid = new Grid(args[0]);
        SudokuSolver s = new SudokuSolver();
        // checks if input Sudoku is valid
        s.check(grid);
        System.out.println(grid.toString());
        
        // prints grid after a specific number of steps
        /*
        s.step(grid, 30);
        System.out.println(grid.toString());
        */
        
        // prints grid and reveals if solver succeeded or failed
        if (s.solve(grid)) {
            System.out.println("---DONE---");
        } else {
            System.out.println("---FAILED---");
        }
        System.out.println();
        System.out.println(grid.toString());
    }
}
