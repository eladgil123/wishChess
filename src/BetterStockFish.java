public class BetterStockFish {
   public static  int boardValue(Board b){
       int sum=0;
       for(int i=0;i<8;i++)
           for(int j=0;j<8;j++)
               if(b.board[i][j]!=null&&!(b.board[i][j] instanceof King)){
                   if(b.board[i][j].isWhite)
                       sum+=b.board[i][j].v;
                   else
                       sum-=b.board[i][j].getValue();
               }
       return sum;
    }
    public static void botMove(Board b,boolean isWhite){
       int maxM=0,maxV=isWhite? -10000:10000;

       for(int i=0;i<8;i++)
           for(int j=0;j<8;j++) {
               if (b.board[i][j] != null&&b.board[i][j].isWhite()==isWhite) {
                   b.board[i][j].reCheckMoves(b.board, i, j, b.wkl, b.bkl, b.lastMove);
                   boolean[][] m = b.board[i][j].getMoves();
                   for (int k = 0; k < 8; k++)
                       for (int s = 0; s < 8; s++)
                           if (m[k][s]) {
                               Board board = new Board(b);
                               board.move(i, j, k, s);
                               int v = boardValue(board);
                               if (isWhite && v > maxV) {
                                   maxV = v;
                                   maxM = i * 1000 + j * 100 + k * 10 + s;
                               } else if (!isWhite && maxV > v) {
                                   maxV = v;
                                   maxM = i * 1000 + j * 100 + k * 10 + s;
                               }
                           }
               }
           }
       b.move(maxM/1000,maxM%1000/100,maxM%100/10,maxM%10);
    }
}
