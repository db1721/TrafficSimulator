package Traffic;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
/***************************************************************
 * File: Project 3 Car
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Runs the car simulation threads
 ****************************************************************/
public class Car extends SwingWorker<Void, Void>
{
	/***************************************************************
	 * Global Variables
	 ****************************************************************/
	private Road road;
	private Timer timer;
	private String y1;
	private String y2;
	private String y3;
	private String x1;
	private String x2;
	private String x3;
	private String car1Speed;
	private String car2Speed;
	private String car3Speed;
	private String travelDirection;
	private String carShape;
	private String colorName;
	private JTextField tfx1;
	private JTextField tfy1;
	private JTextField tfx2;
	private JTextField tfy2;
	private JTextField tfx3;
	private JTextField tfy3;
	private JTextField tfSpeed1;
	private JTextField tfSpeed2;
	private JTextField tfSpeed3;
	private Color color = setColor();
	private static int counter = 1;
	private int id;
	private int ewLightCount;
	private int nsLightCount;
	private final int panel;
	public int x;                                                           
	public int y;
	private int speed;
	private final Random random;
	private Map<Color, String> colorMap = new HashMap<Color, String>();//for color map
	
	/***************************************************************
	 * Car Constructor
	 * Builds each car thread
	 * passes road
	 * passes traffic light count for both road types
	 * passes car shape
	 * passes JTextFields for 3 car's speed, x, and y
	 ****************************************************************/
	public Car(Road road, String carShape, JTextField tfx1, JTextField tfy1, JTextField tfx2, JTextField tfy2,
			JTextField tfx3, JTextField tfy3, JTextField tfSpeed1, JTextField tfSpeed2, JTextField tfSpeed3,
			int ewLightCount, int nsLightCount)
	{
		this.tfx1 = tfx1;
		this.tfy1 = tfy1;
		this.tfx2 = tfx2;
		this.tfy2 = tfy2;
		this.tfx3 = tfx3;
		this.tfy3 = tfy3;
		this.tfSpeed1 = tfSpeed1;
		this.tfSpeed2 = tfSpeed2;
		this.tfSpeed3 = tfSpeed3;
		this.carShape = carShape;
		this.ewLightCount = ewLightCount;
		this.nsLightCount = nsLightCount;
		this.road = road;
		this.id = counter++;
		this.random = new Random();
		this.speed = random.nextInt(15) + 1;
		this.panel = 758;

		if (road instanceof YRoads)
		{
			this.x = ((YRoads) road).leftSideX;
			this.y = road.length-10;

		}
		if (road instanceof XRoads)
		{
			this.x = 0;
			this.y = ((XRoads) road).leftSideY;
		}
		
		//assign colors
		buildColorMap();
	}

	/***************************************************************
	 * Uses random number to select a color
	 ****************************************************************/
	private Color setColor() 
	{
		Random rand = new Random();
		int intColor = rand.nextInt(10);
		if(intColor == 0) 
		{
			color = Color.BLACK;
		}
		else if(intColor == 1) 
		{
			color = Color.YELLOW;
		}
		else if(intColor == 2) 
		{
			color = Color.GREEN;
		}
		else if(intColor == 3) 
		{
			color = Color.ORANGE;
		}
		else if(intColor == 4) 
		{
			color = Color.BLUE;
		}
		else if(intColor == 5) 
		{
			color = Color.PINK;
		}
		else if(intColor == 6) 
		{
			color = Color.CYAN;
		}
		else if(intColor == 7) 
		{
			color = Color.WHITE;
		}
		else if(intColor == 8) 
		{
			color = Color.MAGENTA;
		}
		else if(intColor == 9) 
		{
			color = Color.RED;
		}
		
		return color;
	}
	
	/***************************************************************
	 * Builds the color map
	 ****************************************************************/
	private void buildColorMap() 
	{
		colorMap.put(Color.BLACK, "Black");
		colorMap.put(Color.YELLOW, "Yellow");
		colorMap.put(Color.GREEN, "Green");
		colorMap.put(Color.ORANGE, "Orange");
		colorMap.put(Color.BLUE, "Blue");
		colorMap.put(Color.PINK, "Pink");
		colorMap.put(Color.CYAN, "Cyan");
		colorMap.put(Color.WHITE, "White");
		colorMap.put(Color.MAGENTA, "Magenta");
		colorMap.put(Color.RED, "Red");
		this.colorName = colorMap.get(this.color);
	}

	/***************************************************************
	 * Background process that continuously runs instances of the roads
	 ****************************************************************/
	@Override
	protected Void doInBackground() throws Exception 
	{
		while (!timer.isStop()) 
		{
			if (!timer.isPause()) 
			{
				if (this.road instanceof YRoads)
				{
					travelingNorth();
					this.travelDirection = "NS";
				}
				else 
				{
					travelingEast();
					this.travelDirection = "EW";
				}
			}
			Thread.sleep(1000);
		}
		return null;
	}

	/***************************************************************
	 * North roads checking for traffic light actions and other cars
	 * Sends y coordinates for the first 3 vehicles
	 * Sends car speed on cars traveling north
	 ****************************************************************/
	public void travelingNorth() 
	{
		ArrayList<Car> cars = road.getCars();
		ArrayList<Car> carCrashes = new ArrayList<>();
		ArrayList<TrafficLight> lights = road.getTrafficLights();;
		ArrayList<TrafficLight> stopAtLights = new ArrayList<>();
		int drivingControl = this.y - speed;

		if(this.id == 1) 
		{
			if(travelDirection == "NS") 
			{
				int tempSpeed = convertSpeed(this.speed, this.nsLightCount);
				this.car1Speed = String.format("%d mph", tempSpeed);
				this.tfSpeed1.setText(car1Speed);
			}
			this.y1 = String.format("%d", this.y);
			this.tfy1.setText(y1);
		}
		else if(this.id == 2) 
		{
			if(travelDirection == "NS") 
			{
				int tempSpeed = convertSpeed(this.speed, this.nsLightCount);
				this.car2Speed = String.format("%d mph", tempSpeed);
				this.tfSpeed2.setText(car2Speed);
			}
			this.y2 = String.format("%d", this.y);
			this.tfy2.setText(y2);
		}
		else if(this.id == 3) 
		{
			if(travelDirection == "NS") 
			{
				int tempSpeed = convertSpeed(this.speed, this.nsLightCount);
				this.car3Speed = String.format("%d mph", tempSpeed);
				this.tfSpeed3.setText(car3Speed);
			}
			this.y3 = String.format("%d", this.y);
			this.tfy3.setText(y3);
		}
		
		//Distance to travel
		cars.forEach(car -> 
		{
			if (car.y >= drivingControl && car.y < this.y)
			{
				carCrashes.add(car);
			}
		});
		if (carCrashes.size() > 0)
		{
			slowForSlowCars(carCrashes);
		}
		//Intersections
		lights.forEach(trafficLight -> 
		{
			if (trafficLight.nsLightY >= drivingControl && trafficLight.nsLightY < this.y)
			{
				stopAtLights.add(trafficLight);
			}
		});
		//Follow the laws of the traffic light
		if (stopAtLights.size() > 0)
		{
			TrafficLight light = stopAtLights.get(0);
			if (light.nsStatus == TrafficLightColor.RED) 
			{
				//set spot behind light
				int stopCount = carCrashes.size() > 0 ? carCrashes.size() : 1;
				driveNorth(this.y - light.nsLightY - (10 * stopCount));
			} 
			else if(light.nsStatus == TrafficLightColor.GREEN || light.nsStatus == TrafficLightColor.YELLOW)
			{
				if(random.nextBoolean())
				{
					driveNorth(this.speed);
				}
			}
		}
		else
		{
			driveNorth(this.speed);
		}
	}

	/***************************************************************
	 * East roads checking for traffic light actions and other cars
	 * Sends x coordinates for the first 3 vehicles
	 * Sends car speed on cars traveling east
	 ****************************************************************/
	private void travelingEast()
	{
		ArrayList<Car> cars = road.getCars();
		ArrayList<TrafficLight> lights = road.getTrafficLights();
		ArrayList<Car> carCrashes = new ArrayList<>();
		ArrayList<TrafficLight> stopAtLights = new ArrayList<>();
		int drivingControl = this.x + speed;
		if(this.id == 1) 
		{
			if(travelDirection == "EW") 
			{
				int tempSpeed = convertSpeed(this.speed, this.ewLightCount);
				this.car1Speed = String.format("%d mph", tempSpeed);
				this.tfSpeed1.setText(car1Speed);
			}
			this.x1 = String.format("%d", this.x);
			this.tfx1.setText(x1);
		}
		else if(this.id == 2) 
		{
			if(travelDirection == "EW") 
			{
				int tempSpeed = convertSpeed(this.speed, this.ewLightCount);
				this.car2Speed = String.format("%d mph", tempSpeed);
				this.tfSpeed2.setText(car2Speed);
			}
			this.x2 = String.format("%d", this.x);
			this.tfx2.setText(x2);
		}
		else if(this.id == 3) 
		{
			if(travelDirection == "EW") 
			{
				int tempSpeed = convertSpeed(this.speed, this.ewLightCount);
				this.car3Speed = String.format("%d mph", tempSpeed);
				this.tfSpeed3.setText(car3Speed);
			}
			this.x3 = String.format("%d", this.x);
			this.tfx3.setText(x3);
		}

		//Distance to travel
		cars.forEach(car -> 
		{
			if (car.x <= drivingControl && car.x > this.x)
			{
				carCrashes.add(car);
			}
		});
		if (carCrashes.size() > 0)
		{
			slowForSlowCars(carCrashes);
		}
		//Intersection
		lights.forEach(trafficLight -> 
		{
			if (trafficLight.ewLightX <= drivingControl && trafficLight.ewLightX > this.x)
			{
				stopAtLights.add(trafficLight);
			}
		});

		//Follow the laws of the traffic light
		if (stopAtLights.size() > 0)
		{
			TrafficLight light = stopAtLights.get(0);
			if (light.ewStatus == TrafficLightColor.RED) 
			{
				//set spot at light
				int stopCount = carCrashes.size() > 0 ? carCrashes.size() : 1;
				driveEast(light.ewLightX - this.x - (10 * stopCount) ); //
			} 
			else if(light.ewStatus == TrafficLightColor.GREEN || light.ewStatus == TrafficLightColor.YELLOW)
			{
				if(random.nextBoolean())
				{
					driveEast(this.speed);
				}
			}
		}
		else
		{
			driveEast(this.speed);
		}
	}

	/***************************************************************
	 * checks for cars ahead and will not let them pass
	 * slows the speed down of the car approaching another car
	 ****************************************************************/
	private void slowForSlowCars(ArrayList<Car> carCrashes) 
	{
		final int[] slowestCar = 
			{
					this.speed
			};
		carCrashes.forEach(car -> 
		{
			if (car.speed < slowestCar[0]) 
			{
				slowestCar[0] = car.speed - 1;
			}
		});
		this.speed = slowestCar[0];
	}

	/***************************************************************
	 * Drives the car east
	 ****************************************************************/
	private void driveEast(int distance)
	{
		this.x += distance;
		if (this.x >= this.road.length)
		{
			this.x = 0;
		}
	}

	/***************************************************************
	 * Drives the car north
	 ****************************************************************/
	private void driveNorth(int distance)
	{
		this.y -= distance;
		if(this.y <= 0)
		{
			this.y = this.road.length;
		}
	}

	/***************************************************************
	 * Draws the cars based on the shape selected
	 ****************************************************************/
	public void draw(Graphics graphics)
	{
		if(this.carShape == "Circle") 
		{
			graphics.setColor(this.color);
			//g.drawOval(x, y, width, height);
			graphics.fillOval(this.x-1, this.y, 20, 20);
		}
		else 
		{
			graphics.setColor(this.color);
			//graphics.fillRect(x, y, width, height);
			graphics.fillRect(this.x, this.y, 20, 20);
		}
	}
	
	/***************************************************************
	 * Converts the car's speed to MPH
	 ****************************************************************/
    public int convertSpeed(int speedToCalc, int lightCount) 
    {
    	//speed = distance ÷ time
    	double divPanel = this.panel / (lightCount + 1);
    	double sp = divPanel / speedToCalc;
    	double distInMeters = (1000/sp);
    	
    	//convert meters to mph - multiply the speed value by 2.23694
    	double mphSpeed  = distInMeters * 2.23694;
    	return (int)mphSpeed;
    }

    /***************************************************************
	 * Sends timer to the car thread
	 ****************************************************************/
	public void passTimer(Timer timer)
	{
		this.timer = timer;
	}
	
	/***************************************************************
	 * Getters/Setters to call items from this class
	 ****************************************************************/
	public int getSpeed() 
	{
		return this.speed;
	}
	public String getColor() 
	{
		return this.colorName;
	}
}


