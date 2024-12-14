public class Pawn extends Piece{

    public Pawn(boolean isWhite){
        super(isWhite);
        v=1;
    }
    public Pawn copy(){
        Pawn p=new Pawn(isWhite);
        p.didMove=this.didMove;
        return p;
    }
    public void setMove(int row,int column) {
        this.moves[row][column]=true;
    }
    @Override
    public void reCheckMoves(Piece[][] board, int row, int column,int wkl,int bkl,int lastMove) {
        this.moves=new boolean[8][8];
        if(isWhite) {
            if (row + 1 < 8)
                if (board[row + 1][column] == null)
                    this.moves[row + 1][column] = true;
            if (!this.didMove)
                if (board[row + 2][column] == null&&board[row+1][column]==null)
                    this.moves[row + 2][column] = true;
            if (column + 1 < 8 && row + 1 <8)
                if(board[row+1][column+1]!=null&&!board[row+1][column+1].isWhite())
                    this.moves[row+1][column+1]=true;
            if (column -1>=0 && row + 1 <8)
                if(board[row+1][column-1]!=null&&!board[row+1][column-1].isWhite())
                    this.moves[row+1][column-1]=true;
        }
        else{
            if (row -1>=0)
                if (board[row -1][column] == null)
                    this.moves[row -1][column] = true;
            if (!this.didMove)
                if (board[row -2][column] == null&&board[row-1][column]==null)
                    this.moves[row -2][column] = true;
            if (column + 1 < 8 && row -1 >=0)
                if(board[row-1][column+1]!=null&&board[row-1][column+1].isWhite())
                    this.moves[row-1][column+1]=true;
            if (column -1>=0 && row -1>=0)
                if(board[row-1][column-1]!=null&&board[row-1][column-1].isWhite())
                    this.moves[row-1][column-1]=true;
        }
        if (this.isWhite()) {
            if (row == 4) {
                if (column + 1 < 8 && board[4][column + 1] instanceof Pawn && !board[4][column + 1].isWhite() && lastMove == 6 * 1000 + (column + 1) * 100 + 4 * 10 + (column + 1))
                    this.moves[5][column + 1] = true;
                if (column - 1 >= 0 && board[4][column - 1] instanceof Pawn && !board[4][column - 1].isWhite() && lastMove == 6 * 1000 + (column - 1) * 100 + 4 * 10 + (column - 1))
                    this.moves[5][column - 1] = true;
            }
        }
        else  {
            if (row == 3) {
                if (column + 1 < 8 && board[3][column + 1] instanceof Pawn && board[3][column + 1].isWhite() && lastMove == 1 * 1000 + (column + 1) * 100 + 3 * 10 + (column + 1))
                    this.moves[2][column + 1] = true;
                if (column - 1 >= 0 && board[3][column - 1] instanceof Pawn && board[3][column - 1].isWhite() && lastMove == 1 * 1000 + (column - 1) * 100 + 3 * 10 + (column - 1))
                    this.moves[2][column - 1] = true;
            }
        }

        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                if(this.moves[i][j]){
                    boolean up=false;
                    Piece temp=board[i][j];
                    board[i][j]=board[row][column];
                    board[row][column]=null;
                    if (column != j && board[i][j] == null) {
                            temp=board[i][column];
                            board[i][column] = null;
                            up=true;
                        }
                    if(this.isWhite) {
                        if (Utils.canBeTaken(board, wkl / 10, wkl % 10, true))
                            this.moves[i][j] = false;
                    }
                    else if(Utils.canBeTaken(board,bkl/10,bkl%10,false))
                        this.moves[i][j]=false;
                    if (up) {
                        board[row][column]=board[i][j];
                        board[i][column] = temp;
                        board[i][j] = null;
                    }else {
                        board[row][column] = board[i][j];
                        board[i][j] = temp;
                    }
                }
    }
    public String toString(){
        if(this.isWhite)
            return "Pw";
        return "Pb";
    }
}
