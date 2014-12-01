/**
 * Author: Joel Valenzuela
 * Date: September 30th, 2014
 * Version: 1.0
 *
 * Resources:
 *  https://github.com/brianshaler/Circle-Packing-in-Processing/blob/master/circle_packing/circle_packing.pde
 */

package bubbles;

import java.awt.*;
import java.awt.geom.Area;
import java.util.ArrayList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import java.util.Collections;
import java.util.Enumeration;
import java.util.Map;


public class Bubble{
    
    public String name="";
    public int diameter=0;
    public Point position=new Point();
    public Point confines=new Point();
    public Point localCenter=new Point();
    public Point globalCenter=new Point();
    public Bubble parent;
    public ArrayList<Bubble> bubbles=new ArrayList<Bubble>();
    public int radius=0;
    public Boolean computed=false;
    
    public Bubble()
    {
        
    }

    /***
     * Reads input text formated as RADIUS,NAME (ex. 10,BubbleButt)
     * @param txt
     */
    public Bubble(String txt)
    {
        readInput(txt);
    }

    /***
     * Reads input string and pulls radius and name.
     * @param text
     */
    public void readInput(String text)
    {
          String radiusStr=text.substring(0, text.indexOf(","));
          radius=Integer.parseInt(radiusStr);
          diameter=  radius* 2;
          name= text.substring(text.indexOf(",") +1, text.length());
          localCenter.x=(diameter/2);
          localCenter.y=(diameter/2);
    }

    /***
     * Retrieve the diameter of the circle
     * @return Diameter of the circle
     */
    public int getSize()
    {
        return diameter;
    }

    /***
     * Get the area of the circle
     * @return
     */
    public Rectangle getArea()
    {
        Rectangle r= new Rectangle();
        r.x=position.x;
        r.y=position.y;
        r.height=diameter;
        r.width=diameter;

        return r;
    }

    /***
     * Get the children of bubble
     * @return ArrayList of lesser sized bubbles (possible children)
     */
    public ArrayList<Bubble> children()
    {
        //TODO: Analyze children
        return bubbles;
    }

    /***
     * If Bubble does not have children it's a leaf
     * @return True if bubble does not have children.
     */
    public boolean isLeaf()
    {
        return bubbles.isEmpty();
    }

    /***
     * Gets the bubble's immediate parent
     * @return Bubble of next greatest size
     */
    public Bubble getParent() {
        //TODO: Better qualify parent
        return parent;
    }

    /***
     * Given a point, find the distance of the current bubble from that point
     * @param p2 Point of other bubble
     * @return The distance between the two
     */
    public double getDistance(Point p2)
    {
        Point p1=this.localCenter;
        double a=(p1.x-p2.x);
        double b=(p1.y-p2.y);
        return Math.sqrt(a*a + b*b);
    }

    /***
     * Compare the distance from one circle's relationship to the center
     * to another circle's distance relationship to the center.
     * @param b2 Bubble to compare
     * @return 1: Parameter bubble is further, -1: Parameter bubble is closer,
     * 0: They're the same distance from eachother
     */
    public int compare(Bubble b2)
    {
        Bubble b1=this;
        double d1 = getDistance(b1.globalCenter);

            double d2 = getDistance(b2.globalCenter);
            if (d1 < d2)
                return 1;
            else if (d1 > d2)
                return -1;
            else return 0;
    }

    /***
     * Compare the size of two circles
     * @param b2 Circle to compare to current circle
     * @return 1:Param is larger, -1: Param is smaller, 0: Same size
     */
    public int compareRadius(Bubble b2)
    {
        Bubble b1=this;
        double d1 = b1.diameter;

            double d2 = b2.diameter;
            if (d1 < d2)
                return 1;
            else if (d1 > d2)
                return -1;
            else return 0;
    }

    /***
     * Overriding toString method
     * @return Bubble String
     */
    @Override
    public String toString()
    {
        return "Bubble: "+this.name+"\t Radius: "+this.diameter/2+" X:"+this.position.x+" Y: "+this.position.y;
    }


    /***
     * Adapted Brian Shaler's algorithm to work with current implementation
     * @resource: https://github.com/brianshaler/Circle-Packing-in-Processing/blob/master/circle_packing/circle_packing.pde
     * @param b BubbleList (Singleton Bubble Manager holds items list)
     */
    void computePosition(ArrayList<Bubble> b)
    {
        int i, j;
        int ang;
        Point pnt;
        boolean collision;
        Point[] openPoints = new Point[0];        

        if (computed) 
            return;
        
        for (i = 0; i < b.size(); i++)
        {
            if (b.get(i).computed)
            {
                ang = 0;
                for (ang = 0; ang < 360; ang += 1)
                {
                    collision = false;
                    pnt = new Point();

                    //Variant of the parametric equation
                    pnt.x = b.get(i).position.x + (int)((Math.cos(ang * Math.PI / 180) * (radius + b.get(i).radius + 1)));
                    pnt.y = b.get(i).position.y + (int)((Math.sin(ang * Math.PI / 180) * (radius + b.get(i).radius + 1)));

                    for (j=0; j < b.size(); j++)
                    {
                        if (b.get(j).computed && !collision)
                        {
                            if ((dist(b.get(j).localCenter.x, b.get(j).localCenter.y,pnt.x+radius, pnt.y+radius) < (radius + b.get(j).radius))
                                    ||(pnt.x+radius>globalCenter.x*2||pnt.y+radius>globalCenter.y*2))
                                collision = true;                            
                        }
                    }
                    if (!collision)
                    {
                        openPoints = (Point[]) expand(openPoints, openPoints.length + 1);
                        openPoints[openPoints.length - 1] = pnt;
                    }
                }
            }
        }

        float min_dist = -1;
        int best_point = 0;
        
        for (i = 0; i < openPoints.length; i++)
        {
            if (min_dist == -1 || dist(globalCenter.x, globalCenter.y, openPoints[i].x, openPoints[i].y) < min_dist)
            {
                best_point = i;
                min_dist = dist(globalCenter.x, globalCenter.y, openPoints[i].x, openPoints[i].y);
            }
        }
        
        if (openPoints.length == 0)
            System.out.println("no points?");
         else
        {
            System.out.println(openPoints.length + " points");
            position.x = openPoints[best_point].x;
            position.y = openPoints[best_point].y;
            localCenter.x= position.x+radius;
            localCenter.y= position.y+radius;
        }

        computed = true;
    }

    /***
     * Calculates the distance between two points.
     * @param x1
     * @param y1
     * @param x2
     * @param y2
     * @return The distance betwee the two.
     */
    private float dist(int x1, int y1, int x2, int y2)
    {
        return (float)Math.sqrt(((x2-x1)*(x2-x1))+((y2-y1)*(y2-y1)));        
    }

    /***
     * Stupid method.
     * Expand the Point Array
     * @param p Point Array
     * @param newSize The size of the new array --Not really necessary
     * @return The same array one element larger
     */
    private Point[] expand(Point[] p, int newSize)
    {
        Point[] newArray= new Point[newSize];
        for(int i=0; i<p.length; i++)
            newArray[i]=p[i];
        return newArray;
    }

}

