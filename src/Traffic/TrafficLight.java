package Traffic;

import javax.swing.*;
import java.awt.*;

/***************************************************************
 * File: Project 3 TrafficLight
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Builds the traffic lights
 ****************************************************************/
public class TrafficLight extends SwingWorker<Void, Void> 
{
	/***************************************************************
	 * Global Variables
	 ****************************************************************/
    private Timer timer;
    YRoads nsRoad;
    XRoads ewRoad;
    int ewLightX, nsLightX, nsLightY,ewLightY, id;
    Color ewLightColorStatus = Color.GREEN; 
    Color nsLightColorStatus = Color.RED;
    /***************************************************************
	 * Individuals Lights Colors
	 ****************************************************************/
    private Color ewGreenLight;
    private Color ewYellowLight;
    private Color ewRedLight;
    private Color nsGreenLight;
    private Color nsYellowLight;
    private Color nsRedLight;
    /***************************************************************
	 * Individuals Lights Coordinates
	 ****************************************************************/
    private int ewGreenLightX;
    private int ewYellowLightX;
    private int ewRedLightX;
    private int nsGreenLightY;
    private int nsYellowLightY;
    private int nsRedLightY;
    static int counter = 1;
    private TrafficLightColor trafficLightState; // holds the current traffic light color 
    TrafficLightColor ewStatus = TrafficLightColor.GREEN;
    TrafficLightColor nsStatus = TrafficLightColor.RED;

    /***************************************************************
	 * Constructor for the traffic light
	 ****************************************************************/
   public TrafficLight(YRoads nsRoad, XRoads ewRoad)
   {
       this.nsRoad = nsRoad;
       this.ewRoad = ewRoad;
       this.ewLightY = ewRoad.rightSideY-40;
       this.ewLightX = nsRoad.leftSideX-10;
       this.ewGreenLightX = nsRoad.leftSideX-13;
       this.ewYellowLightX = nsRoad.leftSideX + 2;
       this.ewRedLightX = nsRoad.leftSideX + 17;
       this.nsLightX = nsRoad.rightSideX + 23;
       this.nsLightY = ewRoad.rightSideY;
       this.nsGreenLightY = ewRoad.rightSideY-23;
       this.nsYellowLightY = ewRoad.rightSideY - 8;
       this.nsRedLightY = ewRoad.rightSideY + 7;
       trafficLightState = TrafficLightColor.GREEN;
   }

   /***************************************************************
	 * Runs both traffic lights at each intersection
	 ****************************************************************/
    @Override
    protected Void doInBackground() throws Exception 
    {
        while (!timer.isStop()) 
        {
            if (!timer.isPause()) 
            {
            	try 
            	{ 
            		if(nsLightColorStatus == Color.RED) 
            		{
            			switch(trafficLightState) 
            			{ 
            			case GREEN: 
            				ewLightColorStatus = Color.GREEN;
            				nsLightColorStatus = Color.RED;
            				ewStatus = TrafficLightColor.GREEN;
            			    nsStatus = TrafficLightColor.RED;
            			    ewGreenLight = Color.GREEN;
            	 	 	    ewYellowLight = Color.BLACK;
            	 	 	    ewRedLight = Color.BLACK;
            	 	 	    nsGreenLight = Color.BLACK;
            	 	 	    nsYellowLight = Color.BLACK;
            	 	 	    nsRedLight  = Color.RED;
            				Thread.sleep(6000); // green for 6 seconds 
            				break; 
            			case YELLOW: 
            				ewLightColorStatus = Color.YELLOW;
            				nsLightColorStatus = Color.RED;
            				ewStatus = TrafficLightColor.YELLOW;
            			    nsStatus = TrafficLightColor.RED;
            			    ewGreenLight = Color.BLACK;
            	 	 	    ewYellowLight = Color.YELLOW;
            	 	 	    ewRedLight = Color.BLACK;
            	 	 	    nsGreenLight = Color.BLACK;
            	 	 	    nsYellowLight = Color.BLACK;
            	 	 	    nsRedLight  = Color.RED;
            				Thread.sleep(2000);  // yellow for 2 seconds 
            				break; 
            			case RED: 
            				ewLightColorStatus = Color.RED;
            				nsLightColorStatus = Color.GREEN;
            				ewStatus = TrafficLightColor.RED;
            			    nsStatus = TrafficLightColor.GREEN;
            			    ewGreenLight = Color.BLACK;
            	 	 	    ewYellowLight = Color.BLACK;
            	 	 	    ewRedLight = Color.RED;
            	 	 	    nsGreenLight = Color.GREEN;
            	 	 	    nsYellowLight = Color.BLACK;
            	 	 	    nsRedLight  = Color.BLACK;
            				Thread.sleep(4000); // red for 4 seconds 
            				break; 
            			}
            		}
            		else if(ewLightColorStatus == Color.RED)
            		{
            			switch(trafficLightState) 
            			{ 
            			case GREEN: 
            				ewLightColorStatus = Color.RED;
            				nsLightColorStatus = Color.GREEN;
            				ewStatus = TrafficLightColor.RED;
            			    nsStatus = TrafficLightColor.GREEN;
            			    ewGreenLight = Color.BLACK;
            	 	 	    ewYellowLight = Color.BLACK;
            	 	 	    ewRedLight = Color.RED;
            	 	 	    nsGreenLight = Color.GREEN;
            	 	 	    nsYellowLight = Color.BLACK;
            	 	 	    nsRedLight  = Color.BLACK;
            				Thread.sleep(6000); // green for 6 seconds 
            				break; 
            			case YELLOW: 
            				ewLightColorStatus = Color.RED;
            				nsLightColorStatus = Color.YELLOW;
            				ewStatus = TrafficLightColor.RED;
            			    nsStatus = TrafficLightColor.YELLOW;
            			    ewGreenLight = Color.BLACK;
            	 	 	    ewYellowLight = Color.BLACK;
            	 	 	    ewRedLight = Color.RED;
            	 	 	    nsGreenLight = Color.BLACK;
            	 	 	    nsYellowLight = Color.YELLOW;
            	 	 	    nsRedLight  = Color.BLACK;
            				Thread.sleep(2000);  // yellow for 2 seconds 
            				break; 
            			case RED: 
            				ewLightColorStatus = Color.GREEN;
            				nsLightColorStatus = Color.RED;
            				ewStatus = TrafficLightColor.GREEN;
            			    nsStatus = TrafficLightColor.RED;
            			    ewGreenLight = Color.GREEN;
            	 	 	    ewYellowLight = Color.BLACK;
            	 	 	    ewRedLight = Color.BLACK;
            	 	 	    nsGreenLight = Color.BLACK;
            	 	 	    nsYellowLight = Color.BLACK;
            	 	 	    nsRedLight  = Color.RED;
            				Thread.sleep(4000); // red for 4 seconds 
            				break; 
            			}
            		}
            	} 
    			catch(InterruptedException exc) 
    			{ 
    				JOptionPane.showMessageDialog(null, "Error in traffic light!"); 
    			} 
    			changeColor(); 
            }
        }
        return null;
    }
    
    /***************************************************************
	 * Changes the color of the drawn light
	 ****************************************************************/
 	synchronized void changeColor() 
 	{ 
 		switch(trafficLightState) 
 		{ 
 		case RED: 
 			trafficLightState = TrafficLightColor.GREEN; 
 			break; 
 		case YELLOW: 
 			trafficLightState = TrafficLightColor.RED; 
 			break; 
 		case GREEN: 
 			trafficLightState = TrafficLightColor.YELLOW; 
 		} 

 		notify(); // signal that the light has changed 
 	} 

 	/***************************************************************
	 * Draws ns light and ew light
	 ****************************************************************/
 	public void draw(Graphics g)
 	{
 	    //north=south light
 		//red
 		g.setColor(nsGreenLight);
 		g.fillOval(ewGreenLightX, ewLightY,15, 15);
 		//yellow
 		g.setColor(nsYellowLight);
 		g.fillOval(ewYellowLightX, ewLightY,15, 15);
 		//green
 		g.setColor(nsRedLight);
 		g.fillOval(ewRedLightX, ewLightY,15, 15);

 		//east west light
 		//red
 		g.setColor(ewRedLight);
 		g.fillOval(nsLightX, nsRedLightY,15, 15);
 		//yellow
 		g.setColor(ewYellowLight);
 		g.fillOval(nsLightX, nsYellowLightY,15, 15);
 		//green
 		g.setColor(ewGreenLight);
 		g.fillOval(nsLightX, nsGreenLightY,15, 15);
 	}

    /***************************************************************
	 * Getters/Setters to call items from this class
	 ****************************************************************/
    public void playStopPause(Timer timer)
    { 
    	this.timer = timer; 
    }
    
  	public synchronized String getColor() 
  	{ 
  		return trafficLightState.name(); 
  	}
}
