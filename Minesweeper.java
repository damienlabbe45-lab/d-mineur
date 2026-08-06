import java.util.List;
import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

public class Minesweeper {

    public static char[][] generateChar( int rows , int cols, String difficult ){
        char [] [] grid = new char[rows] [cols];
        for(char[] row: grid){
            Arrays.fill(row, ' ');
        }
        int dif = 0 if(difficult.equalsIgnoreCase("facile")) else 7;
        int totalMines = rows * cols/8 + dif;
        int placed = 0;

        while (placed < totalMines) {
            int r = ThreadLocalRandom.current().nextInt(0, rows);
            int c = ThreadLocalRandom.current().nextInt(0, cols);
            if (grid[r][c] != 'O') {
                grid[r][c] = 'M';
                placed++;
            }
        }

        return grid;
    }
    public static void main(String[] args) {
        if( args.length > 0) throw new IllegalArgumentException(" pas d'arguments");
    }
}
