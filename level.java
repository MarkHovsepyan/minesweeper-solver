import java.lang.Math;

public class level {
    public int width;
    public int height;
    public int bombs;
    public int[][] board;
    public boolean[][] flag;
    public boolean[][] covered;

    public level(int w, int h, int b) {
        this.width = w;
        this.height = h;
        this.bombs = b;

        int[][] board = new int[this.width][this.height];
        boolean[][] flag= new boolean[this.width][this.height];
        boolean[][] covered = new boolean[this.width][this.height];

        generate();
    }

    public void getLevel() {
        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) System.out.println(board[i][j]);
            System.out.println();
        }
    }

    public boolean IndexExists(int index, int range) {
        if (index >= 0 && index < range) return true;
        return false;
    }

    public void generate() {

        for (int i = 0; i < this.width; i++) {
            for (int j = 0; j < this.height; j++) {
                board[i][j] = 0;
                covered[i][j] = true;
                flag[i][j] = false;
            }
        }


        int bomb_x, bomb_y;


        for (int i = 0; i < this.bombs; i++) {
            bomb_x = (int)(Math.random() * this.width + 0);
            bomb_y = (int)(Math.random() * this.height + 0);

            if(board[bomb_x][bomb_y] != 9) {
                if(IndexExists(bomb_x + 1,this.width) && IndexExists(bomb_y + 1,this.height) && board[bomb_x + 1][bomb_y + 1] != 9) ++board[bomb_x + 1][bomb_y + 1];
                if(IndexExists(bomb_x + 1,this.width) && IndexExists(bomb_y,this.height) && board[bomb_x + 1][bomb_y] != 9) ++board[bomb_x + 1][bomb_y];
                if(IndexExists(bomb_x + 1,this.width) && IndexExists(bomb_y - 1,this.height) && board[bomb_x + 1][bomb_y - 1] != 9) ++board[bomb_x + 1][bomb_y - 1];
                if(IndexExists(bomb_x - 1,this.width) && IndexExists(bomb_y + 1,this.height) && board[bomb_x - 1][bomb_y + 1] != 9) ++board[bomb_x - 1][bomb_y + 1];
                if(IndexExists(bomb_x - 1,this.width) && IndexExists(bomb_y,this.height) && board[bomb_x - 1][bomb_y] != 9) ++board[bomb_x - 1][bomb_y];
                if(IndexExists(bomb_x - 1,this.width) && IndexExists(bomb_y - 1,this.height) && board[bomb_x - 1][bomb_y - 1] != 9) ++board[bomb_x - 1][bomb_y - 1];
                if(IndexExists(bomb_x,this.width) && IndexExists(bomb_y + 1,this.height) && board[bomb_x][bomb_y + 1] != 9) ++board[bomb_x][bomb_y + 1];
                if(IndexExists(bomb_x,this.width) && IndexExists(bomb_y - 1,this.height) && board[bomb_x][bomb_y - 1] != 9) ++board[bomb_x][bomb_y - 1];


            }
            else {--i; continue;}
        }


    }
}
