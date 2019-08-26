package life;

import java.util.Random;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner sc  = new Scanner(System.in);
        int size =0 , gen=0;
        long seed=0;

        //Input size and seed
        size = sc.nextInt();
        seed = sc.nextLong();
        gen = sc.nextInt();
        sc.close();

        //Creates universe with random starting positions using seed with the specified size.
        Universe uni = new Universe(size, seed);
        Generator generator = new Generator(uni, gen);
        generator.run();
        //Display the final state
        uni.displayArray();
    }
}

class Universe {
    private char[][] uni;
    private int size;
    private long seed;

    Universe(int size, long seed){
        this.seed = seed;
        this.size = size;
        uni = new char[size][size];
        fillArray();
    }

    private void fillArray(){
        Random rand = new Random(seed);
        for(int i=0; i<size; i++){
            for(int j = 0; j<size; j++){
                if(rand.nextBoolean() == true){
                    uni[j][i] = 'O';
                } else {
                    uni[j][i] = ' ';
                }
            }
        }
    }

    public void displayArray(){
        for(int i=0; i<size; i++){
            for(int j = 0; j<size; j++) {
                System.out.print(uni[j][i]);
                if(j==(size-1) && i!=size-1)    //If EOL, but not last line print newline
                    System.out.print("\n");
            }
        }
    }

    public int getSize(){
        return size;
    }

    public char[][] getUni(){
        return uni;
    }

    public void setUni(char[][] grid){
        this.uni = grid;
    }
}

class Generator{
    Universe curGen;
    char[][] nxtUni;
    int uniSize;
    int generations;

    Generator(Universe uni, int gen){
        curGen = uni;
        this.generations = gen;
        uniSize = curGen.getSize();
        nxtUni = new char[uniSize][uniSize];
    }

    //Start the generation machine
    public void run(){
        if(generations<1)
            return;
        char[][] grid = curGen.getUni();
        for(int i = 0; i<generations; i++){     // How many generations are going to be ran
            for(int y=1; y<uniSize; y++){  //Iterating through 2D Array
                for(int x = 0; x<uniSize; x++) {
                    nxtUni[y][x] = isAlive(grid, x, y);
                }
            }
        }
        curGen.setUni(nxtUni);
    }

    // Given a position, it will check the adjacent cells to see whether the cell is alive or dead.
    private char isAlive(char[][] grid, int x, int y) {
        char alive = 'O';
        int dead = 0;

        //TODO -- Can this not be ugly?
        //Checks touching cells for dead cells
        if (grid[(Math.floorMod((y - 1), uniSize))][x] != 'O')
            dead++;
        if (grid[Math.floorMod((y - 1), uniSize)][Math.floorMod((x + 1), uniSize)] != 'O')
            dead++;
        if(grid[y][Math.floorMod((x+1), uniSize)] !='O')
            dead++;
        if(grid[Math.floorMod((y+1), uniSize)][Math.floorMod((x+1), uniSize)] !='O')
            dead++;
        if(grid[Math.floorMod((y+1), uniSize)][x] !='O')
            dead++;
        if(grid[Math.floorMod((y+1), uniSize)][Math.floorMod((x-1), uniSize)] !='O')
            dead++;
        if(grid[y][Math.floorMod((x-1), uniSize)] != 'O')
            dead++;
        if(grid[Math.floorMod((y-1), uniSize)][Math.floorMod((x-1), uniSize)] != 'O')
            dead++;

        // If more than 6 dead - Boredom. If less than 5 dead - Overpop
        if(dead<5 || dead>6)
            alive = ' ';
        if(dead==5 && grid[y][x]==' '){
            alive = 'O';
        }
        return alive;
    }
}
