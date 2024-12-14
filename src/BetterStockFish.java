public class BetterStockFish {
   public static  int boardValue(Board b,boolean isWhite){
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
    public static int botBestMove(Board b,boolean isWhite,int n){
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
                               int v;
                               if(isWhite&&!Utils.isThereMove(board,false)){
                                   if(Utils.canBeTaken(board.board,board.bkl/10,board.bkl%10,false))
                                       v=1000;
                                   else
                                       v=0;
                               }else if(!Utils.isThereMove(board,true)){
                                   if(Utils.canBeTaken(board.board,board.wkl/10,board.wkl%10,true))
                                       v=-1000;
                                   else
                                       v=0;
                               }else {
                                   if (n > 1) {
                                       int ms = botBestMove(board, !isWhite, n - 1);
                                       board.move(ms / 1000, ms % 1000 / 100, ms % 100 / 10, ms % 10);
                                   }
                               }
                               v=boardValue(board,isWhite);
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
       return maxM;
    }
    public static void botMove(Board b,boolean isWhite){
       int m=botBestMove(b,isWhite,3);
       b.move(m/1000,m%1000/100,m%100/10,m%10);
    }
}
