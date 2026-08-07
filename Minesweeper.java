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

    public static int inputChoice(Scanner input, String text, int numbermax, int numbermin) {
        System.out.println(text);
		int choiceuser = inputInt(input);
		while(choiceuser < numbermin || choiceuser > numbermax) choiceuser = inputInt(input);
        //sert juste à enlever tout buffer du scanner
        input.nextLine();
		return choiceuser -1;
	}

    public static char[][] countMines(char [] [] grid, int row, int col){
        if(grid [row][col] == '_')grid [row] [col] = '1';
        else if(grid [row][col] != 'X')grid [row] [col] = (char) (grid [row] [col] + 1);
        return grid;
    }

    public static char[][] totalMines(char [] [] grid, int row, int col){
        if(row > 0){
           countMines(grid, row - 1, col);
            if(col > 0 )countMines(grid, row -  1 , col - 1);
            if(col + 1< grid[0].length)countMines(grid, row -  1 , col + 1);
        }
        if(row + 1 < grid.length){
            countMines(grid, row + 1, col);
            if(col + 1< grid[0].length)countMines(grid, row + 1, col + 1);
            if(col > 0) countMines(grid, row + 1, col - 1);
        }
        if(col > 0){
            countMines(grid, row, col - 1);
        }
        if(col + 1< grid[0].length){
            countMines(grid, row, col + 1);
        }
        return grid;
    }

    public static char[][] createGrid(int rows , int cols){
        char [] [] grid = new char[rows] [cols];
        for(char[] row: grid){
            Arrays.fill(row, ' ');
        }
        return grid;
    }

    public static int formulaMines(int rows,int cols, String difficult){
        int dif =  difficult.equalsIgnoreCase("facile") ? 0 : 7;
        return (rows + cols)/2 + dif;
    }

    public static int revealCase(char[][] gridUser, char[][] gridSol, int row, int col) {
    // Si la case cible est un vide
    if (gridSol[row][col] == '_') {
        gridUser[row][col] = '_';
        int count = 1; // La case actuelle est découverte
        // Parcourt les 8 voisins autour de (row, col)
        for (int dr = -1; dr <= 1; dr++) {
            for (int dc = -1; dc <= 1; dc++) {
                int r = row + dr;
                int c = col + dc;
                // Vérification des limites de la grille
                if (r >= 0 && r < gridUser.length && c >= 0 && c < gridUser[0].length && gridUser[r][c] == ' ') {
                    count += revealCase(gridUser, gridSol, r, c);
                }
            }
        }
        return count;
    } else {
        // Case chiffre (frontière) : on la révèle simplement
        gridUser[row][col] = gridSol[row][col];
        return 1;
    }
}

    public static void print(char[][] grid){
        int rows = grid.length;
        int cols = grid[0].length;

        // Entête des colonnes (ex: 1 2 3 4...)
        System.out.print("  ");
        for (int c = 1; c <= cols; c++) {
            System.out.printf("%2d ", c);
        }
        System.out.println();

        // Entête des lignes + contenu
        for (int r = 0; r < rows; r++) {
            System.out.printf("%2d ", r + 1);
            for (int c = 0; c < cols; c++) {
                System.out.printf(" %c ", grid[r][c]);
            }
            System.out.println();
        }
    
    }

    public static char[][] generateSolution( int rows , int cols, String difficult ){
        char [] [] grid = createGrid(rows, cols);
        for(char[] row: grid){
            Arrays.fill(row, '_');
        }
        int totalMines = formulaMines(rows,cols, difficult);
        int placed = 0;

        while (placed < totalMines) {
            //faire des nombres aléatoires localement et donne un int
            int r = ThreadLocalRandom.current().nextInt(0, rows);
            int c = ThreadLocalRandom.current().nextInt(0, cols);

            if (grid[r][c] != 'X') {
                grid[r][c] = 'X';
                totalMines(grid, r, c);
                placed++;
            }

        }
        return grid;
    }
    
    public static void game( int rows , int cols, String difficult, Scanner input ){
        char [] [] gridSols = generateSolution(rows,cols,difficult);
        char [] [] gridUser = createGrid(rows, cols);
        int number = 0;
        char value = ' ';

        while(value != 'X' && number != (rows * cols - formulaMines(rows, cols, difficult))){
            //permet d'afficher correctement la grille de l'utilisateur
            print(gridUser);
            int r = inputChoice(input, 
                "veillez indiquer le numéro de ligne entre 1 et " + rows, 
                rows,1);
            int c = inputChoice(input, 
                "veillez indiquer le numéro de colonne entre 1 et " + cols, 
                cols,1);
            value = gridSols [r][c] ;

            if (gridUser[r][c] != value){
                number+= revealCase(gridUser,gridSols,r,c);
            }
            
        }
        print(gridSols);
        if(value != 'X')System.out.println("Vous avez gagné  ^^ !!!!!");
        else System.out.println("Vous avez marché sur une mine et vous avez explosé .....");
    }

    public static void lauchGame(Scanner input){
       int rows = inputChoice(input, "Combien de lignes vous voulez entre 4 et 45", 45,4) + 1;
       int cols = inputChoice(input, "Combien de colonnes vous voulez entre 4 et 45", 45,4) + 1;
       game(rows,cols , inputString(input), input);
    }

    public static void main(String[] args) {
        if( args.length > 0) throw new IllegalArgumentException(" pas d'arguments");
        Scanner input = new Scanner(System.in);
        lauchGame(input);
        input.close();
        System.out.println("Merci d'avoir jouer");
    }
}
