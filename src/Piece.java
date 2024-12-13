public abstract class Piece {
    protected boolean[][] moves;
    protected boolean isWhite,didMove;
    public abstract void reCheckMoves(Piece[][] board,int row,int column,int wkl,int bkl,int lastMove);
    public boolean isWhite() {
        return isWhite;
    }
    public Piece(boolean isWhite){
        this.isWhite=isWhite;
        this.didMove=false;
    }
    public abstract Piece copy();
    public boolean[][] getMoves(){
        boolean[][] m=new boolean[8][8];
        for(int i=0;i<8;i++)
            for(int j=0;j<8;j++)
                m[i][j]=this.moves[i][j];
        return m;
    }
    public abstract String toString();
    public boolean isDidMove(){return this.didMove;}
    public void setDidMove(boolean didMove){
        this.didMove=didMove;
    }
    public void movesPrinter(){
        for(int i=8;i>=1;i--) {
            System.out.print(i + " ");
            for(int j=0;j<8;j++) {
                if (this.moves[i-1][j])
                    System.out.print("t   ");
                else
                    System.out.print("f  ");
            }
            System.out.println();
        }
        System.out.print("  ");
        for(int i=1;i<=8;i++)
            System.out.print(+i + "  ");
    }
}
