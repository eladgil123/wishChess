public class Rook extends Piece {

    public Rook(boolean isWhite){
        super(isWhite);
        v=5;
    }
    public Rook copy(){
        Rook p=new Rook(isWhite);
        p.moves=new boolean[8][8];
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                p.moves[i][j]=this.moves[i][j];
        p.didMove=this.didMove;
        return p;
    }
    public void reCheckMoves(Piece[][] board,int row,int column,int wkl,int bkl,int lastMove) {
        this.moves=new boolean[8][8];
        for(int i=column+1;i<8;i++){
            if(board[row][i]!=null){
                if(this.isWhite==board[row][i].isWhite())
                    break;
                else{
                    this.moves[row][i]=true;
                    break;
                }
            }
            else
                this.moves[row][i]=true;
        }
        for(int i=column-1;i>=0;i--){
            if(board[row][i]!=null){
                if(this.isWhite==board[row][i].isWhite())
                    break;
                else{
                    this.moves[row][i]=true;
                    break;
                }
            }
            else
                this.moves[row][i]=true;
        }
        for(int i=row+1;i<8;i++){
            if(board[i][column]!=null){
                if(this.isWhite==board[i][column].isWhite())
                    break;
                else{
                    this.moves[i][column]=true;
                    break;
                }
            }
            else
                this.moves[i][column]=true;
        }
        for(int i=row-1;i>=0;i--){
            if(board[i][column]!=null){
                if(this.isWhite==board[i][column].isWhite())
                    break;
                else{
                    this.moves[i][column]=true;
                    break;
                }
            }
            else
                this.moves[i][column]=true;
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
                    else  if(Utils.canBeTaken(board,bkl/10,bkl%10,false))
                        this.moves[i][j]=false;
                    board[row][column]=board[i][j];
                    board[i][j]=temp;
                }
    }
    public String toString(){

        if(this.isWhite)
            return "Rw";
        return "Rb";
    }
}
