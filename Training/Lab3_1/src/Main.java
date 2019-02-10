import javax.sound.sampled.Line;
import java.awt.*;
import java.io.*; // import PrintStream
import java.util.Scanner; // import Scanner
import java.awt.geom.*;

public class Main {

    //check if on edges, construct edges, calculate intersections

    //stack queue stack
    public static void main(String[] args) throws IOException {
        Scanner input = new Scanner(System.in);
        PrintStream output = new PrintStream(System.out);
        int n = input.nextInt();

        //get our point
        Point2D our = new Point2D.Double(input.nextDouble(),input.nextDouble());


//        Point2D p[] = new Point2D.Double[n];
        Point2D p[] = new Point2D.Double[n];
        //not necessary
        Line2D lines[] = new Line2D[n];
        double x = input.nextDouble();
        double y = input.nextDouble();
        int count = 0;


        p[0] = new Point2D.Double(x,y);

        for(int i = 1 ; i < n; i++)
        {
            x = input.nextDouble();
            y = input.nextDouble();
            p[i]= new Point2D.Double(x,y);
            lines[i-1] = new Line2D.Double(p[i-1],p[i]);
        }



        double mxDist = 0;
        int fxPointInd = 0;
        for(int i = 0; i < n; i++)
        {
//            System.out.print(i);
//            System.out.print(":");
//            System.out.println((i+1)%n);
//            System.out.println(Line2D.ptLineDistSq(p[i].getX(), p[i].getY(), p[(i + 1)%(n)].getX(), p[(i + 1)%n].getY(), our.getX(), our.getY()));
            if(mxDist < Line2D.ptLineDist(p[i].getX(), p[i].getY(), p[(i + 1)%n].getX(), p[(i + 1)%n].getY(), our.getX(), our.getY()))
            {
                fxPointInd = i;
                mxDist = Line2D.ptLineDist(p[i].getX(), p[i].getY(), p[(i + 1)%n].getX(), p[(i + 1)%n].getY(), our.getX(), our.getY());
            }

        }
//        System.out.println("asdas");
//        System.out.println(fxPointInd);
//        System.out.println((p[fxPointInd].getX()+p[fxPointInd+1].getX())/2.0);
//        System.out.println(p[fxPointInd].getY()+p[fxPointInd+1].getY()/2.0);

        //check if they are on they are on the segment or on the vertex
        for(int i = 0 ; i < n; i++) {
            if (Line2D.ptLineDistSq(p[i].getX(), p[i].getY(), p[(i + 1)%n].getX(), p[(i + 1)%n].getY(), our.getX(), our.getY()) != 0&& Line2D.linesIntersect(p[i].getX(), p[i].getY(), p[(i + 1)%n].getX(), p[(i + 1)%n].getY(), our.getX(), our.getY(), (p[fxPointInd].getX()+p[(fxPointInd+1)%n].getX())/2.0, (p[fxPointInd].getY()+p[(fxPointInd+1)%n].getY())/2.0)) {
                count++;
//                System.out.print("intersect:");
//                System.out.println(i);
            }
        }

        if(count%2 == 1)
            System.out.println("YES");
        else
            System.out.println("NO");

        input.close();
        output.close();
    }
}
