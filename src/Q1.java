import java.util.*;


class Point{

    long x,y;
    Point next,prev;
    public Point(long x,long y){
        this.x=x;
        this.y=y;
        this.next=null;
        this.prev=null;
    }
}
public class Q1 {



    public static void main(String[] args) {


            final ArrayList<Point> sortedX = getInput();
            ArrayList<Point> convexHull;
            final Set<Point> duplicates = new HashSet<>();
            Collections.sort(sortedX, new Comparator<Point>() {
                @Override
                public int compare(Point p1, Point p2) {


                    if (p1.x < p2.x || (p1.x == p2.x && p1.y > p2.y)) {
                        return -1;
                    } else if (p1.x > p2.x || (p1.x == p2.x && p1.y < p2.y)) {
                        return 1;

                }else{
                    if(p1.y==p2.y && !duplicates.contains(p1)){
                        duplicates.add(p2);
                    }
                    return 0;
                }
                    }

            });

        for(Point p:duplicates){
            sortedX.remove(p);
        }



        convexHull = findConvexHullB(sortedX);

        ArrayList<Point> diameter=findDiameter(convexHull);
ArrayList<Point> e=new ArrayList<>();

        Collections.sort(diameter, new Comparator<Point>() {
            @Override
            public int compare(Point p1, Point p2) {


                if (p1.x < p2.x || (p1.x == p2.x && p1.y < p2.y)) {
                    return -1;
                } else if (p1.x > p2.x || (p1.x == p2.x && p1.y > p2.y)) {
                    return 1;

                }else{

                    return 0;
                }
            }

        });




        for (Point p :diameter) {
                System.out.println((long)p.x + " " +(long) p.y);


            }
        }

public static Double distFromLine(Point a, Point b, Point target) {   
   long p = b.y - a.y;    
   long q = a.x - b.x;     
   long r =  b.x * a.y - a.x * b.y;    

   return Math.abs(p * target.x + q * target.y + r) /      
                       Math.sqrt(Math.pow(p, 2) + Math.pow(q, 2)); 
   }
    private static ArrayList<Point> findDiameter(ArrayList<Point> convexHull) {

        ArrayList<Point> diameter=new ArrayList<>();
        Point cur=convexHull.get(0);
        Point next=cur.next;
        Point index=cur.next.next;
        double dist=0;

        for (int i = 0; i <convexHull.size() ; i++) {
            double tempDist=0;
double td=0;
            Point tempFirst=cur;
            Point tempSecond=index;
int j=0;

            while(distFromLine(cur,next,index)<=distFromLine(cur,next,index.prev) || distFromLine(cur,next,index)<distFromLine(cur,next,index.next)){
j++;



                index=index.next;

                double d=distFromLine(cur,next,index);
                if(d>=tempDist ){
double m1=getDist(cur,next,index);
double m2=getDist(next,next,index);
if(m1>=m2 && m1>td){
   tempDist=d;
td=m1;
                    tempFirst=cur;
                    tempSecond=index;
}else{
   tempDist=d;
if(m2>td){
td=m2;
                    tempFirst=next;
                    tempSecond=index;
}
}
                 
                }


            }
if(getDist(cur,next,next)>td){
td=getDist(cur,next,next);
tempFirst=cur;
tempSecond=next;
}
            if(td>dist){
                dist=td;
                diameter.clear();
                diameter.add(tempFirst);
                diameter.add(tempSecond);
                index=index.prev;
            }
            cur=cur.next;
            next=next.next;

        }




        return diameter;
    }


    private static double getDist(Point cur,Point next,Point index){

        return  Math.sqrt(Math.pow(cur.x - index.x, 2) + Math.pow(cur.y - index.y, 2));
    }
//        System.out.println("      ");
//        convexHull = findConvexHullA(sortedX);
//        for (Point p:convexHull) {
//            System.out.println(p.x+" "+p.y);
//
//
//
//        }





    private static ArrayList<Point> findConvexHullB(ArrayList<Point>xSorted) {
            ArrayList<Point> convexHull = new ArrayList<>();
      //  try {

            if (xSorted.size() < 10) {
                return findConvexHullA(xSorted);
            }


            int mid = (Integer) ((xSorted.size() - 1) / 2);
            while (mid < xSorted.size() - 1 && xSorted.get(mid).x == xSorted.get(mid + 1).x) {
                mid++;
            }


            ArrayList<Point> fx = new ArrayList<>(xSorted.subList(0, mid + 1));
            ArrayList<Point> sx = new ArrayList<>(xSorted.subList(mid + 1, xSorted.size()));


//        for(Point p:xSorted){
//            if(p.x<=mid){
//                fx.add(p);
//            }else{
//                sx.add(p);
//            }
//        }

            if (fx.size() == 0) {

                return findConvexHullA(sx);
            } else if (sx.size() == 0) {

                return findConvexHullA(fx);
            }


            ArrayList<Point> fConvexHull = findConvexHullB(fx);
            ArrayList<Point> sConvexHull = findConvexHullB(sx);
           // System.out.println(sx.size()+" "+sConvexHull.size());



                return mergeConvexHulls(fConvexHull, sConvexHull, fx, sx);


       // }catch (Error e){
//            return convexHull;
//
//        }










    }

    private static ArrayList<Point> mergeConvexHulls(ArrayList<Point> fConvexHull, ArrayList<Point> sConvexHull,ArrayList<Point>sortedF,ArrayList<Point>sortedS) {



            //System.out.println("ooooooooo");

            Point fTop = sortedF.get(sortedF.size() - 1);
            Point sTop = sConvexHull.get(0);

            Point fBottom = sortedF.get(sortedF.size() - 1);
            Point sBottom = sConvexHull.get(0);

            int fStartInd = fConvexHull.indexOf(fTop);

//                for (Point p:fConvexHull){
//                    System.out.println("fuck     "+p.x+" "+p.y);
//                }

          //  System.out.println(fTop.x+" "+fTop.y+" "+fStartInd);
//            if(fStartInd==-1 && (fConvexHull.size()<=10 || sConvexHull.size()<=10)  ){
//                return fConvexHull;
//            }

            int fTopInd = fStartInd;
            int sTopInd = 0;
            int fBottomInd = fStartInd;
            int sBottomInd = 0;

//        for (Point p:fConvexHull){
//            System.out.println("pppp    "+p.x+"    "+p.y);
//        }
//
//        for (Point p:sConvexHull){
//            //System.out.println("qqqq    "+p.x+"    "+p.y);
//        }



            while (passThrough(fTop, sTop, fConvexHull, sConvexHull, true, true) || passThrough(fTop, sTop, fConvexHull, sConvexHull, true, false)) {


                while (passThrough(fTop, sTop, fConvexHull, sConvexHull, true, false)) {

                    sTop = sTop.next;
                    sTopInd++;


                }
                while (passThrough(fTop, sTop, fConvexHull, sConvexHull, true, true)) {

                    fTop = fTop.prev;
                    fTopInd--;


                }
            }


            while (passThrough(fBottom, sBottom, fConvexHull, sConvexHull, false, true) || passThrough(fBottom, sBottom, fConvexHull, sConvexHull, false, false)) {


                while (passThrough(fBottom, sBottom, fConvexHull, sConvexHull, false, false)) {


                    sBottom = sBottom.prev;
                    sBottomInd--;


                }
                while (passThrough(fBottom, sBottom, fConvexHull, sConvexHull, false, true)) {


                    fBottom = fBottom.next;
                    fBottomInd++;


                }
            }
         //   try{
            fTop.next = sTop;
            sTop.prev = fTop;
            fBottom.prev = sBottom;
            sBottom.next = fBottom;

            sTopInd = (sTopInd + sConvexHull.size()) % sConvexHull.size();

            sBottomInd = (sBottomInd + sConvexHull.size()) % sConvexHull.size();
            fBottomInd = (fBottomInd + fConvexHull.size()) % fConvexHull.size();
            fTopInd = (fTopInd + fConvexHull.size()) % fConvexHull.size();

//                sTopInd=sConvexHull.indexOf(sTop);
//                sBottomInd=sConvexHull.indexOf(sBottom);
//                fTopInd=fConvexHull.indexOf(fTop);
//                fBottomInd=fConvexHull.indexOf(fBottom);



                return createNewConvexHull(fBottomInd, sBottomInd, fTopInd, sTopInd, fConvexHull, sConvexHull);
//        }catch (Exception e){
//                return fConvexHull;
//        }



    }

    private static ArrayList<Point> createNewConvexHull(int fBottomInd, int sBottomInd, int fTopInd, int sTopInd, ArrayList<Point> fConvexHull, ArrayList<Point> sConvexHull) {

        ArrayList<Point> result =new ArrayList<>();

       // System.out.println("   "+sBottomInd+"   "+fBottomInd+"   "+sTopInd+"   "+fTopInd);



       // System.out.println("size    "   +fConvexHull.size()+"   "+sConvexHull.size());

     //   try {


            result.addAll(fConvexHull.subList(0, fTopInd + 1));
            //Todo
            if (sBottomInd != 0) {
                result.addAll(sConvexHull.subList(sTopInd, sBottomInd + 1));
            } else {
                result.addAll(sConvexHull.subList(sTopInd, sConvexHull.size()));
                result.add(sConvexHull.get(0));

            }
            if (fBottomInd > fTopInd) {
                result.addAll(fConvexHull.subList(fBottomInd, fConvexHull.size()));
            }

//                System.out.println("fTop:  "+fTop.x+"   "+fTop.y+"   next   "+fTop.next.x+"   "+fTop.next.y);
//        System.out.println("sTop:  "+sTop.x+"   "+sTop.y+"   next   "+sTop.next.x+"   "+sTop.next.y);
//        System.out.println("sbottom:  "+sBottom.x+"   "+sBottom.y+"   next   "+sBottom.next.x+"   "+sBottom.next.y);
//
//        System.out.println("fbottom:  "+fBottom.x+"   "+fBottom.y+"   next   "+fBottom.next.x+"   "+fBottom.next.y);
//        System.out.println("indexes   :"+sConvexHull.indexOf(sTop)+"   "+sConvexHull.indexOf(sBottom));
//        for(Point p:sConvexHull){
//            System.out.println(p.x+"   "+p.y);
//        }
//        System.out.println("      ");
//        result.addAll(fConvexHull.subList(0,fConvexHull.indexOf(fTop)+1));
//        result.addAll(sConvexHull.subList(sConvexHull.indexOf(sTop),sConvexHull.indexOf(sBottom)+1));
//        result.addAll(fConvexHull.subList(fConvexHull.indexOf(fBottom),fConvexHull.size()));
//        Point current=fConvexHull.get(0);
//        result.add(current);
            //current=current.next;
//        System.out.println("current:  "+fConvexHull.get(0).x+"   "+fConvexHull.get(0).y +"   next   "+current.next.x+"   "+current.next.y);
//
//        System.out.println("fTop:  "+fTop.x+"   "+fTop.y+"   next   "+fTop.next.x+"   "+fTop.next.y);
//        System.out.println("sTop:  "+sTop.x+"   "+sTop.y+"   next   "+sTop.next.x+"   "+sTop.next.y);
//        System.out.println("sbottom:  "+sBottom.x+"   "+sBottom.y+"   next   "+sBottom.next.x+"   "+sBottom.next.y);
//
//        System.out.println("fbottom:  "+fBottom.x+"   "+fBottom.y+"   next   "+fBottom.next.x+"   "+fBottom.next.y);


//        Point current=fConvexHull.get(0);
//        result.add(current);
//        current=current.next;
//        while (current!=fConvexHull.get(0)){
//            //System.out.println(current.x+"  "+current.y);
//            result.add(current);
//            current=current.next;
//        }

//
//        while (current !=fTop){
//            current=current.next;
//
//            result.add(current);
//
//
//        }
//        current=sTop;
//        result.add(current);
//        while (current !=sBottom){
//            current=current.next;
//            result.add(current);
//
//
//        }
//        current=fBottom;
//        while (current !=fConvexHull.get(0)){
//            result.add(current);
//            current=current.next;
//
//        }
       // }catch (Exception e){

        //}

        return result;
    }

    private static ArrayList<Point> sort(Point[] points, int i) {


        ArrayList<Point> sorted=new ArrayList<>();
        if(points.length==1) {
            sorted.add(points[0]);
            return sorted;
        }else{
            int mid=(Integer)(points.length/2);
            Point[] f= Arrays.copyOfRange(points,0,mid);
            Point[] s=Arrays.copyOfRange(points,mid,points.length);

            ArrayList<Point> sortedF=sort(f,i);
            ArrayList<Point> sortedS=sort(s,i);

            merge(sorted, sortedF, sortedS,i);
            return sorted;

        }
        }

    private static void merge(ArrayList<Point> sorted, ArrayList<Point> sortedF, ArrayList<Point> sortedS,int i) {
        int j=0;
        int k=0;
        while(sorted.size()!=(sortedF.size()+sortedS.size())){

            if(j==sortedF.size()){
                sorted.add(sortedS.get(k));
                k++;

            }else if (k==sortedS.size()){
                sorted.add(sortedF.get(j));
                j++;
            }else{

            if(i==0) {

                if (sortedF.get(j).x <= sortedS.get(k).x) {
                    sorted.add(sortedF.get(j));
                    j++;
                } else {
                    sorted.add(sortedS.get(k));
                    k++;
                }
            }else if(i==1){

                if (sortedF.get(j).y <= sortedS.get(k).y) {
                    sorted.add(sortedF.get(j));
                    j++;
                } else {
                    sorted.add(sortedS.get(k));
                    k++;
                }

            }
            }

        }
    }


    private static ArrayList<Point> findConvexHullA(ArrayList<Point> sortedPoints) {

       // try {
//        System.out.println("    ");
//
//        for(Point p:sortedPoints){
//            System.out.println(p.x+"   "+p.y);
//        }

            ArrayList<Point> points1 = sortedPoints;
           // points1.addAll(sortedPoints);

            if (points1.size() == 0) {
                return new ArrayList<>();
            } else if (points1.size() == 1) {
                return points1;
            }

            Point current = points1.get(0);

            ArrayList<Point> convexHull = new ArrayList<>();
            convexHull.add(current);
//            points1.remove(current);
//            points1.add(current);
            // Point imaginary=new Point(current.x,current.y-1000);
            Point prev = null;
            while (true) {



                double max = -Integer.MAX_VALUE;
                double minDist = Double.MAX_VALUE;
                Point next = null;
                double x1 = 0, y1 = 0;

                if (prev != null) {
                    x1 = current.x - prev.x;
                    y1 = current.y - prev.y;
                }


                for (int i = 0; i < points1.size(); i++) {
                   // System.out.println(i);


                    Point p2 = points1.get(i);
                    if (prev == p2 || current == p2) {
                        continue;
                    }
                    double x2 = p2.x - current.x;
                    double y2 = p2.y - current.y;

                    double angle;

                    if (prev != null) {

                        angle = Math.atan2(x1 * y2 - y1 * x2, x1 * x2 + y1 * y2) - Math.PI;
                    } else {

                        if (y2 >= 0) {

                            angle = Math.PI / 2 + Math.atan2(y2, x2);
                        } else {
                            angle = Math.PI / 2 - Math.atan2(-y2, x2);
                        }
                    }

                    //double angle=Math.acos((x1*x2+y1*y2)/(Math.sqrt(Math.pow(x1,2)+Math.pow(y1,2))*Math.sqrt(Math.pow(x2,2)+Math.pow(y2,2))));
                    angle = (angle >= 0 ? angle : (2 * Math.PI + angle)) * 360 / (2 * Math.PI);
                    //System.out.println(current.x+" "+current.y+"  "+p2.x+" "+p2.y+"  "+angle);


                    double currentDist = Math.sqrt(Math.pow(x2, 2) + Math.pow(y2, 2));

                    if (angle > max ) {
                        max = angle;
                        next = p2;
                      //  minDist = currentDist;
                    }


                }


                if (next == sortedPoints.get(0)) {
                   // System.out.println("ddddddd");
                   convexHull.get(0).prev = convexHull.get(convexHull.size() - 1);
                    convexHull.get(convexHull.size() - 1).next = convexHull.get(0);

//                for(Point p:convexHull){
//                    System.out.println(p.next+" "+p.prev);
//                }

                    return convexHull;
                }


                convexHull.add(next);
                current.next = next;
                next.prev = current;
                prev = current;
                current = next;
               // points1.remove(current);


            }
//        }catch (Exception e){
//            return new ArrayList<>();
//        }
    }

    private static ArrayList<Point> getInput() {
        Scanner scanner=new Scanner(System.in);



        int n=scanner.nextInt();
        ArrayList<Point> points=new ArrayList<>();

        for (int i = 0; i <n ; i++) {

            long x=scanner.nextLong();
            long y=scanner.nextLong();
            Point point=new Point(x,y);
            points.add(point);





        }
        return points;
    }



    private static boolean passThrough(Point fTop, Point sTop, ArrayList<Point> fConvexHull, ArrayList<Point> sConvexHull,boolean isTop,boolean isCL) {










        Point prev,next;

      //  try {


            if (isCL) {
                prev = fTop.prev;
                next = fTop.next;
            } else {
                prev = sTop.prev;
                next = sTop.next;
            }
           // System.out.println(isTop+"  "+isCL+"   "+sTop.x+" "+sTop.y+"   "+next+"   "+prev);


            double m = (double) (sTop.y - fTop.y) / (double) (sTop.x - fTop.x);

            if (isTop) {


                if (next.y > (m * (next.x - fTop.x) + fTop.y) || prev.y > (m * (prev.x - fTop.x) + fTop.y)) {


                    return true;
                }

                return false;
            } else {


                if (next.y < (m * (next.x - fTop.x) + fTop.y) || prev.y < (m * (prev.x - fTop.x) + fTop.y)) {

                    return true;
                }

                return false;
            }
//        }catch (Exception e){
//            return true;
//        }



    }
    }
