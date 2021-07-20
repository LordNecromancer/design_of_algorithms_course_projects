
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.Scanner;

class ResidualGraph{
    final int v,e,s,t;

    //ArrayList<ArrayList<Integer>> adjs=new ArrayList<>();
    int[][] adjs;


    public ResidualGraph(int v , int e,int ns,int nt){
        this.v=v;
        this.e=e;
        this.s=0;
        this.t=2*v+1;
        this.adjs=new int[2*v+2][2*v+2];
        for (int i = 0; i <2*v+2 ; i++) {
            Arrays.fill(this.adjs[i],0);

        }

    }

    public void addEdge(int m ,int n,int w){
        this.adjs[m][n]=w;

    }

    public void incrementEdge(int m,int n,int w){
        this.adjs[m][n]=adjs[m][n]+w;
        this.adjs[n][m]=adjs[n][m]-w;



    }
}




public class Q4 {

    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        final int INF=Integer.MAX_VALUE;

        int v=scanner.nextInt();
        int e=scanner.nextInt();
        int ns=scanner.nextInt();
        int nt=scanner.nextInt();
        int middle=v-nt-ns;
        ArrayList<Integer> sources=new ArrayList<>();
        ArrayList<Integer> targets=new ArrayList<>();

        ResidualGraph rg=new ResidualGraph(v,e,ns,nt);
        ArrayList<Integer>middles=new ArrayList<>();
        for (int i = 1; i <=v ; i++) {
            middles.add(i);

        }




            for (int i = 0; i < ns; i++) {
                int s = scanner.nextInt();
                sources.add(s);
                middles.remove(new Integer(s));
                rg.addEdge(0, 2*s, INF);
                rg.addEdge(2*s-1, 2*s, 1);

                //  rg.addEdge(s, 0, 0);


            }
            for (int i = 0; i < nt; i++) {
                int t = scanner.nextInt();
                targets.add(t);
                middles.remove(new Integer(t));
                rg.addEdge(2*t-1, rg.t, INF);
                rg.addEdge(2*t-1, 2*t, 1);

                //  rg.addEdge(rg.t, ns + 2 * middle + targets.indexOf(t) + 1, 0);

            }


            for (int i = 0; i < e; i++) {
                int m = scanner.nextInt();
                int n = scanner.nextInt();
                int newM, newN;
//
//                if (sources.contains(m)) {
//                    newM = m;
//                } else {
//                    newM = ns + 2 * (middles.indexOf(m)+1) - 1;
//                }
//
//                if (targets.contains(n)) {
//                    newN = ns + 2 * middle + targets.indexOf(n) + 1;
//                } else {
//                    newN = ns + 2 * (middles.indexOf(n)+1) - 1;
//                }
//
//
//
//                if (false) {
//
//
//                        rg.addEdge(newM, newN, 1);
//
//                 //   rg.addEdge(newN, newM, 0);
//                } else {



                rg.addEdge(2*m-1, 2*m, 1);
                       // rg.addEdge(newM + 1, newM, 0);

                rg.addEdge(2*m, 2*n-1, 1);
                        //rg.addEdge(newN, newM + 1, 0);

               // }



            }
//        for (int i = 0; i <rg.adjs[0].length ; i++) {
//            String s="";
//            for (int j = 0; j <rg.adjs[0].length ; j++) {
//
//                s+=rg.adjs[i][j]+" ";
//
//            }
//            System.out.println(s);
//
//        }

            System.out.println(findMaxFlow(rg));



    }

    private static int findMaxFlow(ResidualGraph rg){
        int flow=0;


        int[] path=bfs(rg.s,rg.t,rg);

        while(path!=null){
           // System.out.println("kkkkkkkkkkkkkkkkkkkkkkkk");
            int minPathFlow=Integer.MAX_VALUE;

            int parent=path[rg.t];
            //System.out.println(parent);

            while (path[parent]!=-1){
                minPathFlow=minPathFlow<rg.adjs[path[parent]][parent] ? minPathFlow : rg.adjs[path[parent]][parent];
                parent=path[parent];
               // System.out.println(parent);

            }

            parent=path[rg.t];
            while (path[parent]!=-1){

//                for (int i = 0; i < rg.t+1; i++) {
//                    if(path[parent]!=0) {
//                        if (rg.adjs[parent][i] == 1) {
//                            //  System.out.println(parent+"  "+i);
//                            rg.incrementEdge(parent, i, -1);
//                        }
//                    }else{
//                        break;
//                    }
//                }
                if (path[parent]!=0) {
                   // System.out.println(path[parent]+" "+ parent);
                    rg.incrementEdge(path[parent], parent, -minPathFlow);
                }

                parent=path[parent];
            }
            path=bfs(rg.s,rg.t,rg);
         for (int i = 0; i <rg.adjs[0].length ; i++) {
            String s="";
            for (int j = 0; j <rg.adjs[0].length ; j++) {

                s+=rg.adjs[i][j]+" ";

            }
            System.out.println(s);

        }


            flow+=minPathFlow;

        }





        return flow;
    }

    private static int[] bfs(int s, int t, ResidualGraph rg) {



        int[] path=new int[t+1];
        boolean[] visited=new boolean[t+1];
        Arrays.fill(visited,false);
        LinkedList<Integer> q=new LinkedList<>();
        visited[0]=true;
        path[0]=-1;
        for (int i = 0; i < t+1; i++) {
            if(rg.adjs[s][i]!=0){
                q.add(i);
                visited[i]=true;
                path[i]=0;
            }
        }


        while (q.size()>0){
            int current=q.poll();
            for (int i = 0; i < t+1; i++) {
                if(rg.adjs[current][i]!=0 && !visited[i]){
                    q.add(i);
                    visited[i]=true;
                    path[i]=current;
                    if(i==t){
                        return path;
                    }
                }
            }

        }
        return null;
    }
}
