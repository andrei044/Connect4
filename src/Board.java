import java.util.Arrays;
import java.util.Scanner;

public class Board {
    private static final int rows=6;
    private static final int columns=7;
    private int[][] board=new int[rows][columns];
    private boolean turn=false;
    public Board() {
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                board[i][j]=0;
            }
        }
    }
    public void resetBoard() {
        for(int i=0;i<rows;i++){
            for(int j=0;j<columns;j++){
                board[i][j]=0;
            }
        }
        turn=false;
    }
    @Override
    public String toString() {
        String x="";
        //for(int i=rows-1;i>=0;i--)
        for(int i=0;i<rows;i++)
        {
            x=x+'|';
            for(int j=0;j<columns;j++){

                    x=x+board[i][j];

                x=x+'|';
            }
            x=x+'\n';
        }
        return x;
    }

    public int checkWin(int x,int y){
        boolean checkDraw=true;
        for(int i=0;i<columns;i++){
            if(board[rows-1][i]==0)
                checkDraw=false;
        }
        if(checkDraw==true) {
            //System.out.println("draw\n");
            return 0;
        }

        int player = board[x][y];  // get the player number for the piece at (x, y)
        //System.out.println("x:"+x+' '+"y:"+y+' '+"player:"+player+'\n');
        // check for a horizontal win
        for (int i = 0; i < columns - 3; i++) {
            if (board[x][i] == player && board[x][i+1] == player && board[x][i+2] == player && board[x][i+3] == player) {
                return 1;
            }
        }

        // check for a vertical win
        for (int i = 0; i < rows - 3; i++) {
            if (board[i][y] == player && board[i+1][y] == player && board[i+2][y] == player && board[i+3][y] == player) {
                return 1;
            }
        }

        // check for a diagonal win (top-left to bottom-right)
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 0; j < columns - 3; j++) {
                if (board[i][j] == player && board[i+1][j+1] == player && board[i+2][j+2] == player && board[i+3][j+3] == player) {
                    return 1;
                }
            }
        }

        // check for a diagonal win (top-right to bottom-left)
        for (int i = 0; i < rows - 3; i++) {
            for (int j = 3; j < columns; j++) {
                if (board[i][j] == player && board[i+1][j-1] == player && board[i+2][j-2] == player && board[i+3][j-3] == player) {
                    return 1;
                }
            }
        }

        // if no win was found
        return -1;
    }

    public void putPiece(int x,int y){
        board[x][y]=turn? 2:1;
        turn=!turn;
    }

    public int getFirstFreeCell(int C){
        int i=rows-1;
        while(i>=0 && board[i][C]==0){
            i--;
        }
        i++;
        if(i<rows){
            //board[i][C]=turn? 2:1;
            return i;
            //turn=!turn;
        }
        else
            System.err.println("full column\n");
        return -1;
    }
    public int addPiece(int C) {
        int i=rows-1;
        while(i>=0 && board[i][C]==0){
            i--;
        }
        i++;
        if(i<rows){
            board[i][C]=turn? 2:1;
            if(checkWin(i,C)==1 || checkWin(i,C)==0){
                System.out.println("WIN "+board[i][C]+'\n');
                return -1;
            }
            turn=!turn;
            return 1;

        }
        else
            System.err.println("full column\n");
        return 0;
    }
    public void play(){
        System.out.println(this.toString());
        Scanner in=new Scanner(System.in);
        int inp=in.nextInt();
        while(inp!=-1)
        {
            if(inp==10){
                this.resetBoard();

            }else{
                int rez=this.addPiece(inp);
                if(rez==-1)
                    break;
                else if(rez==1)
                    turn=!turn;
            }
            System.out.println(this.toString());
            inp=in.nextInt();
        }
    }

    public boolean isTurn() {
        return turn;
    }

    /*public int getScore(){

    }

    public int minMax(int depth,boolean maximizingPlayer){
        if(depth==0 && checkWin(0,0)==0){
            return this.getScore();
        }
        if(maximizingPlayer){
            int val=-1000000000;
            for()
        }
    }*/
}
