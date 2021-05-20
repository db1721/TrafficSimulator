package Traffic;

import java.awt.*;
import java.util.ArrayList;

/***************************************************************
 * File: Project 3 Road
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: X and YRoads extend from this class
 ****************************************************************/
public abstract class Road  
{
    ArrayList<TrafficLight> trafficLights;
    ArrayList<Car> carsArray;
    int length;

    public void addCar(Car car) 
    { 
    	carsArray.add(car); 
    }
    public ArrayList<Car> getCars()
    { 
    	return this.carsArray; 
    }
    public void addTrafficLight(TrafficLight light)
    { 
    	trafficLights.add(light); 
    }
    public ArrayList<TrafficLight> getTrafficLights() 
    { 
    	return trafficLights; 
    }
    public abstract void draw(Graphics g);
}
