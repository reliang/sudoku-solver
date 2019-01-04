# Sudoku Solver

A program that solves a 6x6 Sudoku using purely logical methods. 
Takes in txt files encoding a 6x6 Sudoku and prints out the solved Sudoku.
Does not take any guesses.
If a Sudoku could not be solved by a logical process of elimination, it prints out the grid that it is stuck on.

## Instructions

Simply run SudokuSolver.java using

```
java SudokuSolver (filename)
```
For example,

```
java SudokuSolver sudokuExample.txt
```

The txt file should be a number file with 6 rows and 6 characters per row.
Type the numbers in the Sudoku as usual, and leave a space for each empty box in the Sudoku.
If the input is invalid, the program throws an exception. 
The format should look like the provided sudokuExample.txt:

```
16   5
  52  
5   3 
 4   1
  41  
3   54
```
Running the command line with sudokuExample.txt would give

```
> java SudokuSolver sudokuExample.txt
16   5
  52  
5   3 
 4   1
  41  
3   54

---DONE---

162345
435216
516432
243561
654123
321654
```

A failed attempt would look like

```
> java SudokuSolver sudokuTest.txt
 3  45
 5 6  
     6
4   2 
3 6   
 2    

---FAILED---

 3  45
 5 6  
 1   6
46  2 
346   
 2   
 ```
 
 Both sudokuExample.txt and sudokuTest.txt are provided in the files.
