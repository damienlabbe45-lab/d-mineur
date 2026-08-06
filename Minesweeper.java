import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Minesweeper {

    public static int inputInt(Scanner input){
        while(!input.hasNextInt()){
            input.next();
        }
        return input.nextInt();
    }

    

    public static void game( int rows , int cols, String difficult ){
        char [] [] gridSols = generateChar(rows,cols,difficult);
        char [] [] gridUser = grid(rows, cols);
        System.out.println(Arrays.deepToString(gridUser));
        
    }

    public static char[][] givevalue(char [] [] grid, int row, int col){
        if(grid [row][col] == ' ')grid [row] [col] = '1';
        else if(grid [row][col] != 'O')grid [row] [col] = (char) (grid [row] [col] + 1);
        return grid;
    }

    public static char[][] bombs(char [] [] grid, int row, int col){
        if(row > 0){
           givevalue(grid, row - 1, col);
            if(col > 0 )givevalue(grid, row -  1 , col - 1);
            if(col + 1< grid[0].length)givevalue(grid, row -  1 , col + 1);
        }
        if(row + 1 < grid.length){
            givevalue(grid, row + 1, col);
            if(col + 1< grid[0].length)givevalue(grid, row + 1, col + 1);
            if(col > 0) givevalue(grid, row + 1, col - 1);
        }
        if(col > 0){
            givevalue(grid, row, col - 1);
        }
        if(col + 1< grid[0].length){
            givevalue(grid, row, col + 1);
        }
        return grid;
    }

    public static char[][] grid(int rows , int cols){
        char [] [] grid = new char[rows] [cols];
        for(char[] row: grid){
            Arrays.fill(row, ' ');
        }
        return grid;
    }

    public static char[][] generateChar( int rows , int cols, String difficult ){
        char [] [] grid = grid(rows, cols);
        int dif =  difficult.equalsIgnoreCase("facile") ? 0 : 7;
        int totalMines = (rows + cols)/2 + dif;
        int placed = 0;
        while (placed < totalMines) {
            int r = ThreadLocalRandom.current().nextInt(0, rows);
            int c = ThreadLocalRandom.current().nextInt(0, cols);
            if (grid[r][c] != 'O') {
                grid[r][c] = 'O';
                bombs(grid, r, c);
                placed++;
            }
        }
        System.out.println(Arrays.deepToString(grid));
        return grid;
    }
    public static void main(String[] args) {
        if( args.length > 0) throw new IllegalArgumentException(" pas d'arguments");
    }
}
