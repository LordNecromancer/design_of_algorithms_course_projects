import java.util.Scanner;

public class Q2 {

    //gap opening penalty
    static double o=0.0;
    //gap extension penalty
    static double e=0.0;

    static double[][] blosum62=null;
    static double[][] states;
    static double[][] match;
    static double[][] spaceAfter1;
    static double[][] spaceAfter2;


    public static void main(String[] args) {

        Scanner scanner=new Scanner(System.in);
        String str1=scanner.nextLine();
        String str2=scanner.nextLine();

        //gap opening penalty
        o=scanner.nextDouble();
        //gap extension penalty
        e=scanner.nextDouble();

        blosum62=getScoringMatrix();
        double[][] result=findBestMatch(str1,str2);
        String[] backtrack=backTrack(result,str1,str2);
        int score=0;
        int prev=0;
//        for (int i = 0; i <backtrack[0].length() ; i++) {
//            if(i==0){
//                if(backtrack[0].charAt(i)=='-'){
//                    score-=o;
//
//                }
//                else if(backtrack[1].charAt(i)=='-'){
//                    score-=o;
//
//                }else{
//                    score+=blosum62[backtrack[0].charAt(i)-'A'][backtrack[1].charAt(i)-'A'];
//
//                }
//            }else {
//                if(backtrack[0].charAt(i)=='-'){
//                    if(backtrack[0].charAt(i-1)=='-'){
//                        score-=e;
//                    }else{
//                        score-=o;
//
//                    }
//                } else if(backtrack[1].charAt(i)=='-'){
//                    if(backtrack[1].charAt(i-1)=='-'){
//                        score-=e;
//                    }else{
//                        score-=o;
//
//                    }
//                } else {
//                    score+=blosum62[backtrack[0].charAt(i)-'A'][backtrack[1].charAt(i)-'A'];
//                }
//            }
//
//
//        }
        StringBuilder s1=new StringBuilder();
        StringBuilder s2=new StringBuilder();
        s1.append(backtrack[0]).reverse();
        s2.append(backtrack[1]).reverse();

//        for (int i = backtrack[0].length()-1; i >=0 ; i--) {
//            s1=s1+backtrack[0].charAt(i);
//        }
//        for (int i = backtrack[0].length()-1; i >=0 ; i--) {
//            s2=s2+backtrack[1].charAt(i);
//        }
        System.out.println((int)result[str2.length()][str1.length()]);
        //System.out.println(score);

        System.out.println(s1);
        System.out.println(s2);





    }

    private static String[] backTrack(double[][] states,String str1,String str2) {
        String s1="";
        String s2="";
        int indI=str1.length();
        int indJ=str2.length();
        int c=0;
        String prev="";
        String sign1="-";
        String sign2="-";
        String sign3="-";
        String sign4="-";




        while(indI>=1 || indJ>=1){
//            System.out.println(s1);
//            System.out.println(s2);
//            System.out.println(" ");


            if((indI>0 )&& (  (prev=="left"&& spaceAfter2[indJ][indI]==spaceAfter2[indJ][indI-1]-e ) ) ){

                s1=s1+str1.charAt(indI-1);
                s2=s2+sign1;
                indI--;


                if((indI>0 )&& (  (prev=="left"&& spaceAfter2[indJ][indI]==states[indJ][indI-1]-o))){
                    s1=s1+str1.charAt(indI-1);
                    s2=s2+sign2;
                    indI--;
                    prev="";
                }

            }else if((indJ>0) && ( (prev=="up"&& spaceAfter1[indJ][indI]==spaceAfter1[indJ-1][indI]-e) )){


                s2 = s2 + str2.charAt(indJ - 1);
                s1 = s1 + sign1;
                indJ--;

                if((indJ>0) && ( (prev=="up"&& spaceAfter1[indJ][indI]==states[indJ-1][indI]-o) )){
                    s2 = s2 + str2.charAt(indJ - 1);
                    s1 = s1 + sign2;
                    indJ--;
                    prev="";
                }
            }else if(states[indJ][indI]==spaceAfter2[indJ][indI]){
                if(spaceAfter2[indJ][indI]==states[indJ][indI-1]-o){
                    s1=s1+str1.charAt(indI-1);
                    s2=s2+sign3;
                    indI--;
                }else {
                    s1 = s1 + str1.charAt(indI - 1);
                    s2 = s2 + sign3;
                    indI--;
                    prev = "left";
                }
                if((indI>0 )&& (  (prev=="left"&& spaceAfter2[indJ][indI]==states[indJ][indI-1]-o))){
                    s1=s1+str1.charAt(indI-1);
                    s2=s2+sign4;
                    indI--;
                    prev="";
                }
            }else if(states[indJ][indI]==spaceAfter1[indJ][indI]  ){
                if(spaceAfter1[indJ][indI]==states[indJ-1][indI]-o){
                    s2 = s2 + str2.charAt(indJ - 1);
                    s1 = s1 + sign3;
                    indJ--;
                }else {
                    s2 = s2 + str2.charAt(indJ - 1);
                    s1 = s1 + sign3;
                    indJ--;

                    prev = "up";
                }
                if((indJ>0) && ( (prev=="up"&& spaceAfter1[indJ][indI]==states[indJ-1][indI]-o ) )){
                    s2 = s2 + str2.charAt(indJ - 1);
                    s1 = s1 + sign4;
                    indJ--;
                    prev="";
                }
            }else{
                prev="D";
                s1=s1+str1.charAt(indI-1);
                s2=s2+str2.charAt(indJ-1);
                indI--;
                indJ--;
            }
//            if((indI>0 )&& ( (prev=="left" && spaceAfter2[indJ][indI]==spaceAfter2[indJ][indI-1]-e)  ) ){
//                prev="left";
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+"-";
//                indI--;
//            }else if((indJ>0) && (prev=="up" && spaceAfter1[indJ][indI]==spaceAfter1[indJ-1][indI]-e) ) {
//                prev="up";
//                s2 = s2 + str2.charAt(indJ - 1);
//                s1 = s1 + "-";
//                indJ--;
//            }else if(states[indJ][indI][0]==spaceAfter2[indJ][indI] &&states[indJ][indI][0]!=spaceAfter1[indJ][indI] &&states[indJ][indI][0]!=match[indJ][indI]){
//                prev="left";
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+"*";
//                indI--;
//
//            }else if(states[indJ][indI][0]==spaceAfter1[indJ][indI] &&states[indJ][indI][0]!=spaceAfter2[indJ][indI] &&states[indJ][indI][0]!=match[indJ][indI]){
//                prev="up";
//                s2 = s2 + str2.charAt(indJ - 1);
//                s1 = s1 + "*";
//                indJ--;
//
//            }else if(indI>=1 && indJ>=1){
//                prev="D";
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+str2.charAt(indJ-1);
//                indI--;
//                indJ--;
//            }else {
//                break;
//            }

//        while(indI>=1 || indJ>=1){
//            if((indI>0 )&& (((states[indJ][indI][0]==spaceAfter2[indJ][indI-1]-e) || (prev=="left" && spaceAfter2[indJ][indI]==spaceAfter2[indJ][indI-1]-e) ) ||(prev=="left" && states[indJ][indI][0]==states[indJ][indI-1][0]-o)) ){
//                prev="left";
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+"-";
//                indI--;
//            }else if((indJ>0) &&((states[indJ][indI][0]==spaceAfter1[indJ-1][indI]-e) ||  (prev=="up" && spaceAfter1[indJ][indI]==spaceAfter1[indJ-1][indI]-e) ||  (prev=="up" && states[indJ][indI][0]==states[indJ-1][indI][0]-o))) {
//                prev="up";
//                s2 = s2 + str2.charAt(indJ - 1);
//                s1 = s1 + "-";
//                indJ--;
//            }else if(indI>=1 && indJ>=1){
//                prev="D";
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+str2.charAt(indJ-1);
//                indI--;
//                indJ--;
//            }else {
//                break;
//            }
            c++;
            // System.out.println(indI+"  "+indJ);

//            double cur=states[indJ][indI][0];
//            if(states[indJ][indI][1]==0){
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+"-";
//                indI--;
//
//            }else if(states[indJ][indI][1]==2){
//                s2=s2+str2.charAt(indJ-1);
//                s1=s1+"-";
//                indJ--;
//            }else if(states[indJ][indI][1]==1){
//                s1=s1+str1.charAt(indI-1);
//                s2=s2+str2.charAt(indJ-1);
//                indI--;
//                indJ--;
//
//            }
            //System.out.println(s1.charAt(s1.length()-1)+" "+s2.charAt(s2.length()-1)+" "+cur);
        }
        return new String[]{s1,s2};
    }

    private static double[][] findBestMatch(String str1, String str2) {
        int inf=-10000000;

        states=new double[str2.length()+1][str1.length()+1];
        match=new double[str2.length()+1][str1.length()+1];
        spaceAfter1=new double[str2.length()+1][str1.length()+1];
        spaceAfter2=new double[str2.length()+1][str1.length()+1];

        states[0][0]=0;
        states[0][1]=-o;
        states[1][0]=-o;

        match[0][0]=inf;
        match[0][1]=inf;
        match[1][0]=inf;

        spaceAfter1[0][0]=0;
        spaceAfter1[0][1]=inf;
        spaceAfter1[1][0]=-o;

        spaceAfter2[0][0]=0;
        spaceAfter2[0][1]=-o;
        spaceAfter2[1][0]=inf;

        for (int i = 2; i <str1.length()+1 ; i++) {

            states[0][i]= states[0][i-1]-e;
            match[0][i]= inf;
            spaceAfter1[0][i]= inf;
            spaceAfter2[0][i]= spaceAfter2[0][i-1]-e;




        }
        for (int i = 2; i <str2.length()+1 ; i++) {

            states[i][0]= states[i-1][0]-e;
            match[i][0]= inf;
            spaceAfter1[i][0]= spaceAfter1[i-1][0]-e;
            spaceAfter2[i][0]= inf;

        }
        for (int i = 1; i <str1.length()+1 ; i++) {

            for (int j = 1; j <str2.length()+1 ; j++) {
                match[j][i]=states[j-1][i-1]+blosum62[str2.charAt(j-1)-'A'][str1.charAt(i-1)-'A'];
                spaceAfter1[j][i]=spaceAfter1[j-1][i]-e>=states[j-1][i]-o?spaceAfter1[j-1][i]-e:states[j-1][i]-o;
                spaceAfter2[j][i]=spaceAfter2[j][i-1]-e>=states[j][i-1]-o?spaceAfter2[j][i-1]-e:states[j][i-1]-o;
//                if(i==17 && j==19){
//                    System.out.println(+spaceAfter1[j-1][i]+" "+states[j-1][i][0]);
//                    System.out.println("dddddddd"+match[j][i]+" "+spaceAfter1[j][i]+" "+spaceAfter2[j][i]);
//                }
                //System.out.println("i , j = "+i+" "+j+" "+match[j][i]+" "+spaceAfter1[j][i]+" "+spaceAfter2[j][i]);


                double scoreTop=spaceAfter1[j][i];
                double scoreLeft=spaceAfter2[j][i];
                double scoreTopLeft=match[j][i];
                states[j][i]=Math.max(Math.max(scoreLeft,scoreTop),scoreTopLeft);



//                if(scoreLeft>=scoreTop && scoreLeft>=scoreTopLeft ){
//                    states[j][i]= new double[]{scoreLeft, 0};
//
//
//                }else if(scoreTop>=scoreTopLeft && scoreTop>=scoreLeft){
//                    states[j][i]= new double[]{scoreTop, 2};
//
//
//                }else {
//                    states[j][i]= new double[]{scoreTopLeft, 1};
//
//
//                }



//                double[]top=states[j-1][i];
//                double[]left=states[j][i-1];
//                double[] topLeft=states[j-1][i-1];
//
//                double scoreTop=top[1]==2 ? top[0]-e :  top[0]-o;
//                double scoreLeft=left[1]==0 ? left[0]-e :  left[0]-o;
//                double scoreTopLeft=topLeft[0]+blosum62[str2.charAt(j-1)-'A'][str1.charAt(i-1)-'A'];
//                int ind1,ind2;
//                if(scoreTop>=scoreLeft && scoreTop>=scoreTopLeft){
//                    ind1=scoreTop;
//                    ind2=2;
//                }else if()



//                if(scoreTop>=scoreTopLeft && scoreTop>=scoreLeft){
//                    states[j][i]= new double[]{scoreTop, 2};
//
//
//                }else if(scoreLeft>=scoreTop && scoreLeft>=scoreTopLeft ){
//                    states[j][i]= new double[]{scoreLeft, 0};
//
//
//                }else {
//                    states[j][i]= new double[]{scoreTopLeft, 1};
//
//
//                }



            }

        }
//
//        for (int j = 0; j <str2.length()+1 ; j++) {
//            String s="";
//
//            for (int i = 0; i <str1.length()+1 ; i++) {
//                s=s+" "+states[j][i];
//
//            }
//            System.out.println(s);
//        }
//        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
//        for (int j = 0; j <str2.length()+1 ; j++) {
//            String s="";
//
//            for (int i = 0; i <str1.length()+1 ; i++) {
//                s=s+" "+spaceAfter1[j][i];
//
//            }
//            System.out.println(s);
//        }
//        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
//        for (int j = 0; j <str2.length()+1 ; j++) {
//            String s="";
//
//            for (int i = 0; i <str1.length()+1 ; i++) {
//                s=s+" "+spaceAfter2[j][i];
//
//            }
//            System.out.println(s);
//        }
//        System.out.println("IIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIIII");
//        for (int j = 0; j <str2.length()+1 ; j++) {
//            String s="";
//
//            for (int i = 0; i <str1.length()+1 ; i++) {
//                s=s+" "+match[j][i];
//
//            }
//            System.out.println(s);
//        }




        return states;
    }

    private static double[][] getScoringMatrix() {

        return new double[][]{

                  { 4,-2, 0,-2,-1,-2, 0,-2,-1,-4,-1,-1,-1,-2,-4,-1,-1,-1, 1, 0, 0, 0,-3, 0,-2,-1},
                  {-2, 4,-3, 4, 1,-3,-1, 0,-3,-4, 0,-4,-3, 3,-4,-2, 0,-1, 0,-1,-3,-3,-4,-1,-3, 1},
                  { 0,-3, 9,-3,-4,-2,-3,-3,-1,-4,-3,-1,-1,-3,-4,-3,-3,-3,-1,-1, 9,-1,-2,-2,-2,-3},
                  {-2, 4,-3, 6, 2,-3,-1,-1,-3,-4,-1,-4,-3, 1,-4,-1, 0,-2, 0,-1,-3,-3,-4,-1,-3, 1},
                  {-1, 1,-4, 2, 5,-3,-2, 0,-3,-4, 1,-3,-2, 0,-4,-1, 2, 0, 0,-1,-4,-2,-3,-1,-2, 4},
                  {-2,-3,-2,-3,-3, 6,-3,-1, 0,-4,-3, 0, 0,-3,-4,-4,-3,-3,-2,-2,-2,-1, 1,-1, 3,-3},
                  { 0,-1,-3,-1,-2,-3, 6,-2,-4,-4,-2,-4,-3, 0,-4,-2,-2,-2, 0,-2,-3,-3,-2,-1,-3,-2},
                  {-2, 0,-3,-1, 0,-1,-2, 8,-3,-4,-1,-3,-2, 1,-4,-2, 0, 0,-1,-2,-3,-3,-2,-1, 2, 0},
                  {-1,-3,-1,-3,-3, 0,-4,-3, 4,-4,-3, 2, 1,-3,-4,-3,-3,-3,-2,-1,-1, 3,-3,-1,-1,-3},
                  {-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
                  {-1, 0,-3,-1, 1,-3,-2,-1,-3,-4, 5,-2,-1, 0,-4,-1, 1, 2, 0,-1,-3,-2,-3,-1,-2, 1},
                  {-1,-4,-1,-4,-3, 0,-4,-3, 2,-4,-2, 4, 2,-3,-4,-3,-2,-2,-2,-1,-1, 1,-2,-1,-1,-3},
                  {-1,-3,-1,-3,-2, 0,-3,-2, 1,-4,-1, 2, 5,-2,-4,-2, 0,-1,-1,-1,-1, 1,-1,-1,-1,-1},
                  {-2, 3,-3, 1, 0,-3, 0, 1,-3,-4, 0,-3,-2, 6,-4,-2, 0, 0, 1, 0,-3,-3,-4,-1,-2, 0},
                  {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4, 1,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
                  {-1,-2,-3,-1,-1,-4,-2,-2,-3,-4,-1,-3,-2,-2,-4, 7,-1,-2,-1,-1,-3,-2,-4,-2,-3,-1},
                  {-1, 0,-3, 0, 2,-3,-2, 0,-3,-4, 1,-2, 0, 0,-4,-1, 5, 1, 0,-1,-3,-2,-2,-1,-1, 3},
                  {-1,-1,-3,-2, 0,-3,-2, 0,-3,-4, 2,-2,-1, 0,-4,-2, 1, 5,-1,-1,-3,-3,-3,-1,-2, 0},
                  { 1, 0,-1, 0, 0,-2, 0,-1,-2,-4, 0,-2,-1, 1,-4,-1, 0,-1, 4, 1,-1,-2,-3, 0,-2, 0},
                  { 0,-1,-1,-1,-1,-2,-2,-2,-1,-4,-1,-1,-1, 0,-4,-1,-1,-1, 1, 5,-1, 0,-2, 0,-2,-1},
                  { 0,-3, 9,-3,-4,-2,-3,-3,-1,-4,-3,-1,-1,-3,-4,-3,-3,-3,-1,-1, 9,-1,-2,-2,-2,-3},
                  { 0,-3,-1,-3,-2,-1,-3,-3, 3,-4,-2, 1, 1,-3,-4,-2,-2,-3,-2, 0,-1, 4,-3,-1,-1,-2},
                  {-3,-4,-2,-4,-3, 1,-2,-2,-3,-4,-3,-2,-1,-4,-4,-4,-2,-3,-3,-2,-2,-3,11,-2, 2,-3},
                  { 0,-1,-2,-1,-1,-1,-1,-1,-1,-4,-1,-1,-1,-1,-4,-2,-1,-1, 0, 0,-2,-1,-2,-1,-1,-1},
                  {-2,-3,-2,-3,-2, 3,-3, 2,-1,-4,-2,-1,-1,-2,-4,-3,-1,-2,-2,-2,-2,-1, 2,-1, 7,-2},
                  {-1, 1,-3, 1, 4,-3,-2, 0,-3,-4, 1,-3,-1, 0,-4,-1, 3, 0, 0,-1,-3,-2,-3,-1,-2, 4},
                  {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},
                  {-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4,-4},



        };


    }
}
