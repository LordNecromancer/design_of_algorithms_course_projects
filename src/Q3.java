import java.util.*;

class Graph{
    final int m,n;
    ArrayList<ArrayList<Integer>> adjL=new ArrayList<>();
   // ArrayList<ArrayList<Integer>> adjR=new ArrayList<>();
    int edgeCount=0;


    public Graph(int m , int n,int k){
        this.edgeCount=k;
        this.m=m;
        this.n=n;
        for (int i = 0; i < m+1; i++) {
            adjL.add(new ArrayList<Integer>());
        }
//        for (int i = 0; i < n; i++) {
//            adjR.add(new ArrayList<Integer>());
//        }

    }

     void addEdge(int m ,int n){

        adjL.get(m).add(n);
      //  adjR.get(n).add(m);

    }

    void removeEdge(int m,int n){
        int ind=adjL.get(m).indexOf(n);
        adjL.get(m).remove(ind);
    }

    public void changeEdgeCount(int c){
        this.edgeCount+=c;
    }

        }



public class Q3 {
    static int[] pairL,pairR,dist;
    static int dummy=0;
    static final int INF=Integer.MAX_VALUE;
   // static HashMap<Integer,Integer> count=new HashMap<>();


    public static void main(String[] args) {
        Scanner scanner=new Scanner(System.in);
        int m=scanner.nextInt();
        int n=scanner.nextInt();
        int k=scanner.nextInt();
        Graph graph=new Graph(m,n,k);

        for (int i = 0; i <k ; i++) {
            int l=scanner.nextInt();
//            if(count.containsKey(l)){
//                count.put(l,count.get(l)+1);
//            }else{
//                count.put(l,1);
//            }
            int r=scanner.nextInt();
            graph.addEdge(l,r);

        }
        sendPackets(graph);
    }

    private static void sendPackets(Graph graph) {
        int t=0;
        int r=1;
        ArrayList<int[]> resultsL=new ArrayList<>();
        ArrayList<Integer> resultsMatches=new ArrayList<>();

        // ArrayList<int[]> resultsR=new ArrayList<>();

        while (r>0){
            pairL=new int[graph.m+1];
            pairR=new int[graph.n+1];
            dist=new int[graph.m+1];

            Arrays.fill(pairL,dummy);
            Arrays.fill(pairR,dummy);
// After calling findMaximumMatching , pairL and pairR will be filled with the new matching
            r=findMaximumMatching(graph);
            resultsL.add(pairL);
            resultsMatches.add(r);
           // resultsR.add(pairR);
            removeEdges(graph,pairL);
            if(r>0) {
                t++;
            }
        }
        System.out.println(t);
        for (int i = 0; i < resultsL.size()-1; i++) {
            int[] curr=resultsL.get(i);
            System.out.println(resultsMatches.get(i));
            for (int j = 1; j <curr.length ; j++) {
                if (curr[j] != dummy) {
                    System.out.println ( j + " " + curr[j]);

                }
            }



        }

    }

    private static void removeEdges(Graph graph, int[] pairL) {
        for (int i = 0; i <pairL.length ; i++) {
            if(pairL[i]!=dummy){
                graph.removeEdge(i,pairL[i]);
            }

        }
    }

    private static int findMaximumMatching(Graph graph) {
        int result=0;



        while (bfs(graph)){

            for (int i=1;i<graph.m+1;i++){

                if(pairL[i]==dummy && dfs(i,graph)){
                    result++;


                }
            }
        }

       return result;


    }

    private static boolean dfs(int i, Graph graph) {

        if(i==dummy){
            return true;
        }

        for (int v : graph.adjL.get(i)){
            if(dist[pairR[v]]==dist[i]+1){
                if(dfs(pairR[v],graph)){
                    pairR[v]=i;
                    pairL[i]=v;
                    return true;
                }

            }
        }
        dist[i]=INF;
        return false;
    }

    private static boolean bfs(Graph graph) {
        LinkedList<Integer> queue=new LinkedList<>();
        for (int i = 1; i <graph.m+1 ; i++) {
            if(pairL[i]==dummy){
                dist[i]=0;
                queue.add(i);
            }else{
                dist[i]=INF;
            }

        }
        dist[dummy]=INF;

        while (!queue.isEmpty()){

            int l=queue.poll();
            if(dist[l]<dist[dummy]){
                for(int i:graph.adjL.get(l)){
                    if (dist[pairR[i]]==INF){
                        dist[pairR[i]]=dist[l]+1;
                        queue.add(pairR[i]);
                    }

                }
            }
        }



        return (dist[dummy] != INF);

    }


}
