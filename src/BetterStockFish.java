import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BetterStockFish {
   public static  int boardValue(Board b,boolean isWhite){
       int sum=0;
       for(int i=0;i<8;i++)
           for(int j=0;j<8;j++)
               if(b.board[i][j]!=null){
                   if(b.board[i][j].isWhite)
                       sum+=b.board[i][j].v;
                   else
                       sum-=b.board[i][j].getValue();
               }
       if(isWhite&&!Utils.isThereMove(b,false)){
           if(Utils.canBeTaken(b.board,b.bkl/10,b.bkl%10,false))
               return 1000;
           else
               return 0;
       }else if(!isWhite&&!Utils.isThereMove(b,true)){
           if(Utils.canBeTaken(b.board,b.wkl/10,b.wkl%10,true))
               return -1000;
           else
               return 0;
       }
       return sum;
    }
    public static int minimax(Board b,boolean isWhite,int n,double alpha,double beta){

        if (b.isOver){
            if (!isWhite && Utils.canBeTaken(b.board,b.bkl/10,b.bkl%10,false)){
                return 10000;
            }
            if (isWhite && Utils.canBeTaken(b.board,b.wkl/10,b.wkl%10,true)){
                return -10000;
            }
            return 0;
        }
        if (n==0){
            return boardValue(b, isWhite);
        }
        int maxV=isWhite? -10000:10000;
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if (b.board[i][j] != null && b.board[i][j].isWhite() == isWhite) {
                    b.board[i][j].reCheckMoves(b.board, i, j, b.wkl, b.bkl, b.lastMove);
                    boolean[][] m = b.board[i][j].getMoves();
                    for (int k = 0; k < 8; k++) {
                        for (int s = 0; s < 8; s++) {
                            if (!m[k][s]) {
                                continue;
                            }
                            Board board = new Board(b);
                            if (board.board[i][j] instanceof Pawn && (k == 0 || k == 7) )
                                board.move(i, j, k, s, 1);
                            else
                                board.move(i, j, k, s);

                            int v = minimax(board,!isWhite,n-1,alpha,beta);
                            if(!isWhite&&v<alpha)
                                return v;
                            if(isWhite&& v>beta)
                                return v;
                            if(isWhite&&v>alpha)
                                alpha=v;
                            if(!isWhite&&beta<v)
                                beta=v;
                            if (isWhite && v > maxV) {
                                maxV = v;
                            } else if (!isWhite && maxV > v) {
                                maxV = v;
                            }
                        }
                    }
                }
            }
        }
        return maxV;
    }
    public static void botMove(Board b,boolean isWhite){
        ExecutorService executorService = Executors.newFixedThreadPool(12); // Create a thread pool with 4 threads
        List<Callable<Result>> tasks = new ArrayList<>();
       int bestM=0,bestV=isWhite? -10000:10000;
        for(int i=0;i<8;i++) {
            for (int j = 0; j < 8; j++) {
                if (b.board[i][j] != null && b.board[i][j].isWhite() == isWhite) {
                    b.board[i][j].reCheckMoves(b.board, i, j, b.wkl, b.bkl, b.lastMove);
                    boolean[][] m = b.board[i][j].getMoves();
                    for (int k = 0; k < 8; k++) {
                        for (int s = 0; s < 8; s++) {
                            if (!m[k][s]) {
                                continue;
                            }
                            Board board = new Board(b);
                            if (board.board[i][j] instanceof Pawn && (k == 0 || k == 7) )
                                board.move(i, j, k, s, 1);
                            else
                                board.move(i, j, k, s);
                            int mm = 1000*i+100*j+10*k+s;
                            tasks.add(() -> new Result(mm,  minimax(board,!isWhite,4,-10000,10000)));
                        }
                    }
                }
            }
        }
        try {
            // Submit tasks and collect futures
            List<Future<Result>> futures = executorService.invokeAll(tasks);

            // Process the results
            Result maxResult = null;
            for (Future<Result> future : futures) {
                Result result = future.get();
                if (maxResult == null || (isWhite && result.getOutput() > maxResult.getOutput()) || (!isWhite && result.getOutput() < maxResult.getOutput())) {
                    maxResult = result;
                }
            }

            if (maxResult != null) {
                System.out.println("Input with max output: " + maxResult.getInput());
                System.out.println("Max output: " + maxResult.getOutput());
                bestM = maxResult.getInput();
                if (b.board[bestM/1000][bestM%1000/100] instanceof Pawn && (bestM%100/10 == 0 || bestM%100/10 == 7) )
                    b.move(bestM/1000,bestM%1000/100,bestM%100/10,bestM%10,1);
                else
                    b.move(bestM/1000,bestM%1000/100,bestM%100/10,bestM%10);
            }
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        } finally {
            executorService.shutdown(); // Shutdown the executor service
        }


    }
    static class Result {
        private final int input;
        private final int output;

        public Result(int input, int output) {
            this.input = input;
            this.output = output;
        }

        public int getInput() {
            return input;
        }

        public int getOutput() {
            return output;
        }
    }
}
