public class Knight extends Piece{

    public Knight(boolean isWhite){
        super(isWhite);
        v=3;
    }
    public Knight copy(){
        Knight p=new Knight(isWhite);
        p.didMove=this.didMove;
        return p;
    }
     @Override
    public void reCheckMoves(Piece[][] board, int row, int column,int wkl,int bkl,int lastMove  ) {
        this.moves=new boolean[8][8];
        int r=row+2,c=column-1;
        if(r<8&&c>=0) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                    this.moves[r][c] = true;
        }
        c=column+1;
        if(r<8&&c<8) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
        r=row+1;
        c=column+2;
        if(r<8&&c<8) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
        r=row-1;
        if(r>=0&&c<8) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
        r=row-2;
        c=column+1;
        if(r>=0&&c<8) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
        c=column-1;
        if(r>=0&&c>=0) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
        r=row-1;
        c=column-2;
        if(r>=0&&c>=0) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
        r=row+1;
        if(r<8&&c>=0) {
            if (board[r][c] != null) {
                if (board[r][c].isWhite() != this.isWhite)
                    this.moves[r][c] = true;
            }
            else
                this.moves[r][c] = true;
        }
         for(int i=0;i<8;i++)
             for(int j=0;j<8;j++)
                 if(this.moves[i][j]){
                     Piece temp=board[i][j];
                     board[i][j]=board[row][column];
                     board[row][column]=null;
                     if(this.isWhite) {
                         if (Utils.canBeTaken(board, wkl / 10, wkl % 10, true))
                             this.moves[i][j] = false;
                     }
                     else if(Utils.canBeTaken(board,bkl/10,bkl%10,false))
                             this.moves[i][j]=false;
                     board[row][column]=board[i][j];
                     board[i][j]=temp;
                 }
    }
    public String toString(){

        if(this.isWhite)
            return "Nw";
        return "Nb";
    }
}
