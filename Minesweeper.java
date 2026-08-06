import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;
import java.util.Scanner;

public class Minesweeper {

    public static int inputInt(Scanner input){
       while(!input.hasNextInt()) input.next();
		return input.nextInt();
    }

    public static String inputString(Scanner input){
        System.out.println("Veillez indiquer si vous voulez un mode facile ou pas");
        return input.nextLine();
    }

    public static int inputChoice(Scanner input, String text, int number) {
        System.out.println(text);
		int choiceuser = inputInt(input);
		while(choiceuser < 4 || choiceuser > number) choiceuser = inputInt(input);
        input.nextLine();
		return choiceuser -1;
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
    
    public static void game( int rows , int cols, String difficult, Scanner input ){
        char [] [] gridSols = generateChar(rows,cols,difficult);
        char [] [] gridUser = grid(rows, cols);
        int number = 0;
        char value = ' ';
        while(value != 'O' || number == (rows * cols - (rows + cols)/2)){
            System.out.println(Arrays.deepToString(gridUser));
            int r = inputChoice(input, 
                "veillez indiquer le numéro de ligne entre 1 et " + rows, 
                rows);
            int c = inputChoice(input, 
                "veillez indiquer le numéro de colonne entre 1 et " + cols, 
                cols);
            value = gridSols [r][c] ;
            gridUser[r][c]= value;
        }
        if(value != 'O')System.out.println("Vous avez gagné  ^^ !!!!!");
        else System.out.println("Vous avez marché sur une mine et vous avez explosé .....");
    }

    public static void weeper(Scanner input){
       int rows = inputChoice(input, "Combien de lignes vous voulez entre 4 et 20", 20);
       int cols = inputChoice(input, "Combien de colonnes vous voulez entre 4 et 20", 20);
       game(rows,cols , inputString(input), input);
    }

    public static void main(String[] args) {
        if( args.length > 0) throw new IllegalArgumentException(" pas d'arguments");
        Scanner input = new Scanner(System.in);
        weeper(input);
        input.close();
    }
}
