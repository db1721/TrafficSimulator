package Examples;

import Project3.GUI;

// Try This 12-1

// A simulation of a traffic light that uses 
// an enumeration to describe the light's color. 
 
// An enumeration of the colors of a traffic light. 
enum TrafficLightColor 
{  
  RED, GREEN, YELLOW 
} 
 
// A computerized traffic light. 
class TrafficLightSimulator implements Runnable 
{ 
  private TrafficLightColor trafficLightState; // holds the current traffic light color 
  private boolean stop = false; // set to true to stop the simulation 
  private boolean changed = false; // true when the light has changed
 
  TrafficLightSimulator(TrafficLightColor init) 
  {  
    trafficLightState = init; 
  } 
 
  TrafficLightSimulator() 
  {  
    trafficLightState = TrafficLightColor.RED; 
  } 
 
  // Start up the light. 
  public void run() 
  { 
    while(!stop) 
    { 
      try 
      { 
        switch(trafficLightState) 
        { 
          case GREEN: 
            Thread.sleep(10000); // green for 10 seconds 
            break; 
          case YELLOW: 
            Thread.sleep(2000);  // yellow for 2 seconds 
            break; 
          case RED: 
            Thread.sleep(12000); // red for 12 seconds 
            break; 
        } 
      } 
      catch(InterruptedException exc) 
      { 
        System.out.println(exc); 
      } 
      changeColor(); 
    }  
  } 
 
  // Change color. 
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
 
    changed = true;
    notify(); // signal that the light has changed 
  } 
 
  // Wait until a light change occurs. 
  synchronized void waitForChange() 
  { 
    try 
    { 
      while(!changed) 
        wait(); // wait for light to change 
      changed = false;
    } 
    catch(InterruptedException exc) 
    { 
      System.out.println(exc); 
    } 
  } 
 
  // Return current color. 
  synchronized TrafficLightColor getColor() 
  { 
    return trafficLightState; 
  } 
 
  // Stop the traffic light. 
  synchronized void cancel() 
  { 
    stop = true; 
  } 
}  
  
class TrafficLightDemo 
{  
  public static void main(String args[]) 
  {  
    TrafficLightSimulator tl =
      new TrafficLightSimulator(TrafficLightColor.GREEN); 

    Thread thrd = new Thread(tl);
    thrd.start();
 
    for(int i=0; i < 9; i++) 
    { 
      System.out.println(tl.getColor()); 
      tl.waitForChange(); 
    } 
    tl.cancel(); 
  }  
}