import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class BetterStockFish {
    public static double boardValue(Board b, boolean isWhite) {
        double sum = 0;
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++)
                if (b.board[i][j] != null) {
                    sum += b.board[i][j].v;
                }
        //System.out.println("1: "+sum);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (b.board[i][j] == null)
                    continue;
                if (!isWhite) {
                    if (!b.board[i][j].isWhite)
                        continue;
                    int e = Utils.WCanBeTaken(b.board, i, j, true);
                    if (e != -1) {
                        if (Utils.canBeTaken(b.board, i, j, false)) {
                            if (b.board[i][j].v > -b.board[e / 10][e % 10].v)
                                sum -= (b.board[i][j].v - b.board[e / 10][e % 10].v);
                        } else
                            sum -= b.board[i][j].v;
                    }
                } else {
                    if (b.board[i][j].isWhite)
                        continue;
                    int e = Utils.WCanBeTaken(b.board, i, j, false);
                    if (e != -1) {
                        if (Utils.canBeTaken(b.board, i, j, true)) {
                            if (b.board[i][j].v < -b.board[e / 10][e % 10].v)
                                sum -= (b.board[i][j].v -b.board[e / 10][e % 10].v);
                        } else
                            sum -= b.board[i][j].v;
                    }

                }
            }
        //System.out.println("2: "+sum);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if (b.board[i][j] == null)
                    continue;
                b.board[i][j].reCheckMoves(b.board, i, j, b.wkl, b.bkl, b.lastMove);
                boolean[][] m = b.board[i][j].getMoves();
                if (b.board[i][j].isWhite)
                    for (int s = 0; s < 8; s++)
                        for (int k = 0; k < 8; k++) {
                            if (m[s][k])
                                sum += 0.02;
                        }
                else
                    for (int s = 0; s < 8; s++)
                        for (int k = 0; k < 8; k++)
                            if (m[s][k])
                                sum -= 0.02;
            }
        //System.out.println("3: "+sum);
        for (int i = 0; i < 8; i++)
            for (int j = 0; j < 8; j++) {
                if(b.board[i][j] instanceof Pawn) {
                    if (b.board[i][j].isWhite)
                        sum += (i - 1) * 0.05;
                    else
                        sum -= (6-i) * 0.05;
                }
            }
        //System.out.println("4: "+sum);
        return sum;

    }
    public static double minimax(Board b,boolean isWhite,int n,double alpha,double beta){

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
        double maxV=isWhite? -10000:10000;
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

                            double v = minimax(board,!isWhite,n-1,alpha,beta);
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
       int bestM=0;
       double bestV=isWhite? -10000:10000;
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
                            tasks.add(() -> new Result(mm,  minimax(board,!isWhite,3,-10000,10000)));
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
                System.out.println(result.getInput()+": "+result.output);
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
        private final double output;

        public Result(int input, double output) {
            this.input = input;
            this.output = output;
        }

        public int getInput() {
            return input;
        }

        public double getOutput() {
            return output;
        }
    }
}
