public class Queen extends Piece{

    public Queen(boolean isWhite){
        super(isWhite);
        v=9;
    }
    public Queen copy() {
        Queen p = new Queen(isWhite);
        p.didMove=this.didMove;
        return p;
    }
    @Override
    public void reCheckMoves(Piece[][] board, int row, int column,int wkl,int bkl,int lastMove) {
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
        for(int i=row+1,j=column+1;i<8&&j<8;i++,j++){
            if(board[i][j]!=null){
                if(this.isWhite==board[i][j].isWhite())
                    break;
                else{
                    this.moves[i][j]=true;
                    break;
                }
            }
            else
                this.moves[i][j]=true;
        }
        for(int i=row+-1,j=column+1;i>=0&&j<8;i--,j++){
            if(board[i][j]!=null){
                if(this.isWhite==board[i][j].isWhite())
                    break;
                else{
                    this.moves[i][j]=true;
                    break;
                }
            }
            else
                this.moves[i][j]=true;
        }
        for(int i=row+1,j=column-1;i<8&&j>=0;i++,j--){
            if(board[i][j]!=null){
                if(this.isWhite==board[i][j].isWhite())
                    break;
                else{
                    this.moves[i][j]=true;
                    break;
                }
            }
            else
                this.moves[i][j]=true;
        }
        for(int i=row-1,j=column-1;i>=0&&j>=0;i--,j--){
            if(board[i][j]!=null){
                if(this.isWhite==board[i][j].isWhite())
                    break;
                else{
                    this.moves[i][j]=true;
                    break;
                }
            }
            else
                this.moves[i][j]=true;
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
            return "Qw";
        return "Qb";
    }
}
