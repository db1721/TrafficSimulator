package Traffic;

import java.awt.*;
import java.util.ArrayList;

/***************************************************************
 * File: Project 3 YRoads
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Extends road to be able to draw the vertical roads
 * on the traffic panel
 ****************************************************************/
public class YRoads extends Road
{
    final int roadMedianX, panelHeight, roadWidth;
    final int leftSideX, rightSideX;

    /***************************************************************
	 * Constructor that builds off of the parameter median and panel 
	 * width
	 ****************************************************************/
    public YRoads(int roadMedianX, int panelHeight)  
    {
        this.roadMedianX = roadMedianX;
        this.panelHeight = panelHeight;
        this.leftSideX = roadMedianX-5;
        this.rightSideX = roadMedianX+5;
        this.length = panelHeight;
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
        graphics.fillRect((roadMedianX-7), 0, roadWidth, panelHeight);

        for (Car car : this.carsArray) 
        {
            car.draw(graphics);
        }
    }
}
