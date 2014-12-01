/*
 * Author: Joel Valenzuela
 * Date: September 30th, 2014
 * Version: 1.0
 *
 * Resources:
 *      http://hcil2.cs.umd.edu/trs/91-06/91-06.html *
*/
package bubbles;

import java.awt.Color;
import java.awt.Dimension;
import java.util.*;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.io.StringReader;

/**
 * Bubble Manager is a singleton who manages all of the bubbles.
 * @author Joel Valenzuela
 */
public class BubbleManager {

    private ArrayList<Bubble> bubbles = new ArrayList<Bubble>();
    private Point[] occupiedPoints;    
    private Point limits;
    private static BubbleManager bubbleMaker= new BubbleManager();

    public static BubbleManager BubbleManager()
    {
        return bubbleMaker;
    }

    public static BubbleManager getBubbleManager()
    {
        return bubbleMaker;
    }

    /**
     * Add a bubble to the Mangager's list.
     * @param b
     */
    public void addBubble(Bubble b)
    {
        bubbles.add(b);
    }

    /***
     * Read Input data from the textbox.
     * @param input Full Input String
     * @param panel Panel Dimensions
     */
    public void readInputData(String input, Dimension panel)
    {        
        StringReader r = new StringReader(input);
        String bubbleData="";
        char nextChar = ' ';
        
        try{
        for(int i=0; i<input.length(); i++)
        {                    
            nextChar = (char) r.read();                        
            if(nextChar=='\n')
            {               
                Bubble newb= new Bubble(bubbleData);
                bubbles.add(newb);
                if(bubbles.size()==1)
                {
                    newb.position.x=panel.width/2;
                    newb.position.y=panel.height/2;
                    newb.computed=true;
                }                
                newb.globalCenter.x=panel.width/2;
                newb.globalCenter.y=panel.height/2;                
                bubbleData="";
            }
            else
                bubbleData= bubbleData + nextChar;            
        }
        //*/
        int index;
        for(Iterator i=bubbles.iterator(); i.hasNext();)
        {
            Bubble b=(Bubble)i.next();
            index=bubbles.indexOf(b);

            for(int i2=index+1; i2< bubbles.size(); i2++)
            {
                Bubble b2=bubbles.get(bubbles.indexOf(b)+1);
                b2.parent=b;
                b.bubbles.add(b2);
            }
        }
        //*/
        r.close();
        }
        catch(Exception e){System.out.println("Bad Input "+e.getMessage());}
    }

    /***
     * Sort bubbles from smallest to largest using mergesort O(nlogn)
     */
    public void sortBubbles()
    {
     bubbles=MergeSort.sort(bubbles);
    }

    /***
     * Display the bubbles on the panel
     * @param g Jpanel
     * @return Graphics created using bubble information
     */
    public Graphics displayBubbles(Graphics g)
    {
     
        try{
            for(Iterator i=bubbles.iterator(); i.hasNext();)
            {
                Bubble b= (Bubble)i.next();
                b.computePosition(bubbles);
                g.setColor(genRandColor());
                g.drawOval(b.position.x, b.position.y, b.diameter, b.diameter);
                g.fillOval(b.position.x, b.position.y, b.diameter, b.diameter);                
                g.setColor(Color.black);
                g.drawChars(b.name.toCharArray(), 0, b.name.length(), b.position.x+(int)(b.diameter/2), b.position.y+(int)(b.diameter/2));
            }
            return g;
        }
        catch(Exception e){ System.out.println(e.getMessage()); return g;}        
    }

    /***
     * Generate random colors so we can beautify
     * this ugly duckling.
     * @return
     */
    public Color genRandColor()
    {
        Random r= new Random();
        return new Color(r.nextFloat(),r.nextFloat(),r.nextFloat());
    }

    /***
     * Print the bubble information to the console.
     */
    public void printBubbles()
    {
        for(Iterator i = bubbles.iterator(); i.hasNext();)
            System.out.println((Bubble)i.next());
        System.out.println("");
    }

    /***
     * Get rid of all the bubbles and start over.
     */
    void clearList() {
        bubbles.removeAll(bubbles);
    }


}
