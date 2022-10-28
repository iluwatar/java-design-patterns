package General;

import java.util.*;
import java.lang.*;
import java.io.*;

public class Sudoku_Solver {
    static class FastReader{
        BufferedReader br;
        StringTokenizer st;
        public FastReader(){
            br=new BufferedReader(new InputStreamReader(System.in));
        }
        String next(){
            while(st==null || !st.hasMoreTokens()){
                try {
                    st=new StringTokenizer(br.readLine());
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            return st.nextToken();
        }
        int nextInt(){
            return Integer.parseInt(next());
        }
        long nextLong(){
            return Long.parseLong(next());
        }
        double nextDouble(){
            return Double.parseDouble(next());
        }
        String nextLine(){
            String str="";
            try {
                str=br.readLine().trim();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return str;
        }
    }
    static class FastWriter {
        private final BufferedWriter bw;
        public FastWriter() {
            this.bw = new BufferedWriter(new OutputStreamWriter(System.out));
        }
        public void print(Object object) throws IOException {
            bw.append("").append(String.valueOf(object));
        }

        public void println(Object object) throws IOException {
            print(object);
            bw.append("\n");
        }
        public void close() throws IOException {
            bw.close();
        }
    }


    static int N = 9;
    static int[] row = new int[N], col = new int[N],
            box = new int[N];
    static Boolean seted = false;

    static int getBox(int i, int j)
    {
        return i / 3 * 3 + j / 3;
    }

    static Boolean isSafe(int i, int j, int number)
    {
        return ((row[i] >> number) & 1) == 0
                && ((col[j] >> number) & 1) == 0
                && ((box[getBox(i, j)] >> number) & 1) == 0;
    }

    static void setInitialValues(int[][] grid)
    {
        for (int i = 0; i < grid.length; i++) {
            for (int j = 0; j < grid.length; j++) {
                row[i] |= 1 << grid[i][j];
                col[j] |= 1 << grid[i][j];
                box[getBox(i, j)] |= 1 << grid[i][j];
            }
        }
    }

    static Boolean SolveSudoku(int[][] grid, int i, int j)
    {
        if (!seted) {
            seted = true;
            setInitialValues(grid);
        }

        if (i == N - 1 && j == N)
            return true;
        if (j == N) {
            j = 0;
            i++;
        }

        if (grid[i][j] > 0)
            return SolveSudoku(grid, i, j + 1);

        for (int nr = 1; nr <= N; nr++) {
            if (isSafe(i, j, nr)) {
                grid[i][j] = nr;
                row[i] |= 1 << nr;
                col[j] |= 1 << nr;
                box[getBox(i, j)] |= 1 << nr;

                if (SolveSudoku(grid, i, j + 1))
                    return true;
                row[i] &= ~(1 << nr);
                col[j] &= ~(1 << nr);
                box[getBox(i, j)] &= ~(1 << nr);
            }

            grid[i][j] = 0;
        }

        return false;
    }

    static void print(int[][] grid)
    {
        for (int[] ints : grid) {
            for (int j = 0; j < grid[0].length; j++) {
                if (ints[j]==0){
                    System.out.print("| . ");
                }
                else {
                    System.out.printf("| %d ", ints[j]);
                }
            }
            System.out.println("|");
        }
    }


    public static void main(String[] args) throws Exception{
        try {
            FastReader in=new FastReader();
            FastWriter out = new FastWriter();
                System.out.println("Welcome to Grid Game");
                System.out.println("There Will be a 9 by 9 sudoku grid ,it will be solved here-- ");
                System.out.println("Enter your Choice Number :\n1. Use Default Game\n2. Input your own game to solve");
                int t = in.nextInt();
                if(t==2){
                    System.out.println("\n\nGame Rules :\nThere Will be a 9/9 Grid Sudoku Game you will have to input proper value for each cell:");
                    System.out.println(" The cell value are from 1-9 \n Empty Cell denoted by .\n");
                    int[][] grid = new int[9][9];
                    for (int i = 0; i < 9; i++) {
                        for (int j = 0; j < 9; j++) {
                            System.out.println("Enter value for position : "+i+" , "+j);
                            String x = in.next();
                            if (x.charAt(0)=='.'){
                                grid[i][j]=0;
                            }
                            else{
                                grid[i][j]=(int)x.charAt(0);
                            }
                        }
                    }
                    System.out.println("The final Input Grid is:");
                    print(grid);
                    if (SolveSudoku(grid, 0, 0)) {
                        print(grid);
                    }
                    else {
                        System.out.println("No Solution exists");
                    }
                }
                else {
                    System.out.println("\nThe Default game is :\n");
                    int[][] grid = { { 3, 0, 6, 5, 0, 8, 4, 0, 0 },
                            { 5, 2, 0, 0, 0, 0, 0, 0, 0 },
                            { 0, 8, 7, 0, 0, 0, 0, 3, 1 },
                            { 0, 0, 3, 0, 1, 0, 0, 8, 0 },
                            { 9, 0, 0, 8, 6, 3, 0, 0, 5 },
                            { 0, 5, 0, 0, 9, 0, 6, 0, 0 },
                            { 1, 3, 0, 0, 0, 0, 2, 5, 0 },
                            { 0, 0, 0, 0, 0, 0, 0, 7, 4 },
                            { 0, 0, 5, 2, 0, 6, 3, 0, 0 } };
                    print(grid);
                    if (SolveSudoku(grid, 0, 0)) {
                        System.out.println("\n\nThe Solution is:");
                        print(grid);
                    }
                    else {
                        System.out.println("No Solution exists");
                    }
                }
            out.close();
        } catch (Exception e) {
            return;
        }
    }

}
