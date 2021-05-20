package Traffic;

import java.awt.*;
import java.util.ArrayList;

/***************************************************************
 * File: Project 3 XRoads
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Extends road to be able to draw the horizontal roads
 * on the traffic panel
 ****************************************************************/
public class XRoads extends Road
{
	/***************************************************************
	 * Global Variables
	 ****************************************************************/
    final int roadMedianY, panelWidth, roadWidth;
    final int leftSideY, rightSideY;

    /***************************************************************
	 * Constructor that builds off of the parameter median and panel 
	 * width
	 ****************************************************************/
    public XRoads(int roadMedianY, int panelWidth)
    {
        this.roadMedianY = roadMedianY;
        this.panelWidth = panelWidth;
        this.leftSideY = roadMedianY-5;
        this.rightSideY = roadMedianY+5; 
        this.length = panelWidth;
        this.roadWidth = 25;
        this.carsArray = new ArrayList<>();
        this.trafficLights = new ArrayList<>();
    }

    /***************************************************************
	 * Draws the roads and cars in the car array
	 ****************************************************************/
    public void draw(Graphics graphics) 
    {
        graphics.setColor(Color.GRAY);
        //graphics.fillRect(x, y, width, height);
        graphics.fillRect(0, (roadMedianY-7), panelWidth, roadWidth);

        for (Car car : carsArray) 
        {
            car.draw(graphics);
        }
    }
}
