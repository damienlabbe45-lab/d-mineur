import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Minesweeper {

    public static char[][] givevalue(char [] [] grid, int row, int col){
        if(grid [row][col] == ' ')grid [row] [col] = '1';
        else if(grid [row][col] != 'O')grid [row] [col] = (char) ( (int) grid [row] [col] + 1);
        return grid;
    }

    public static char[][] bombs(char [] [] grid, int row, int col){
        if(row > 0){
           grid = givevalue(grid, row - 1, col);
            if(col > 0 )grid = givevalue(grid, row -  1 , col - 1);
            if(col < grid[0].length)grid = givevalue(grid, row -  1 , col - 1);
        }
        if(row < grid.length){
            grid = givevalue(grid, row + 1, col);
            if(col < grid[0].length)grid = givevalue(grid, row + 1, col + 1);
            if(col > 0) grid = givevalue(grid, row + 1, col - 1);
        }
        if(col > 0){
            grid = givevalue(grid, row, col - 1);
        }
        if(col < grid[0].length){
            grid = givevalue(grid, row, col + 1);
        }
        return grid;
    }

    public static char[][] generateChar( int rows , int cols, String difficult ){
        char [] [] grid = new char[rows] [cols];
        for(char[] row: grid){
            Arrays.fill(row, ' ');
        }
        int dif =  difficult.equalsIgnoreCase("facile") ? 0 : 7;
        int totalMines = (rows + cols)/2 + dif;
        int placed = 0;

        while (placed < totalMines) {
            int r = ThreadLocalRandom.current().nextInt(0, rows);
            int c = ThreadLocalRandom.current().nextInt(0, cols);
            if (grid[r][c] != 'O') {
                grid[r][c] = 'O';
                grid = bombs(grid, r, c);
                placed++;
            }
        }

        return grid;
    }
    public static void main(String[] args) {
        if( args.length > 0) throw new IllegalArgumentException(" pas d'arguments");
    }
}
