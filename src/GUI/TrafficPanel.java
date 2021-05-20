package GUI;

import Traffic.*;
import Traffic.Timer;
import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/***************************************************************
 * File: Project 3 Traffic Panel
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Runs the traffic panel of the code
 ****************************************************************/
public class TrafficPanel extends JPanel
{
	/***************************************************************
	 * Global Variables
	 ****************************************************************/
	private static final long serialVersionUID = 1L;
	private final int horizontalRoads;
	private final int verticalRoads;
	private final int carCount;
	private final int width, height;
	private String carShape;
	private String carColor;
	private int carSpeed;
	private Car car;
	private JTextField x1;
	private JTextField y1;
	private JTextField x2;
	private JTextField y2;
	private JTextField x3;
	private JTextField y3;
	private JTextField speed1;
	private JTextField speed2;
	private JTextField speed3;
	ArrayList<Road> roads = new ArrayList<>();
	ArrayList<TrafficLight> trafficLights = new ArrayList<>();
	ArrayList<YRoads> nsRoads = new ArrayList<>();
	ArrayList<XRoads> ewRoads = new ArrayList<>();
	ArrayList<Car> cars = new ArrayList<>();
	Timer timer;
	GUI gui;
	ExecutorService executorService;

	/***************************************************************
	 * Traffic Panel Constructor
	 * Builds the traffic panel
	 * passes horizontal and vertical road count
	 * passes car count
	 * passes car shape
	 * passes JTextFields for 3 car's speed, x, and y
	 ****************************************************************/
	TrafficPanel(int horizontalRoads, int verticalRoads, int carCount, String carShape, 
			JTextField x1, JTextField y1, JTextField x2, JTextField y2,
			JTextField x3, JTextField y3, JTextField speed1, JTextField speed2, 
			JTextField speed3) 
	{
		this.carShape = carShape;
		this.horizontalRoads = horizontalRoads;
		this.verticalRoads = verticalRoads;
		this.carCount = carCount;
		this.x1 = x1;
		this.y1 = y1;
		this.x2 = x2;
		this.y2 = y2;
		this.x3 = x3;
		this.y3 = y3;
		this.speed1 = speed1;
		this.speed2 = speed2;
		this.speed3 = speed3;
		int carBuffer = 50;
		this.executorService = Executors.newFixedThreadPool((horizontalRoads * verticalRoads) + carCount + carBuffer + 1);

		/***************************************************************
		 * Set size to fit in main panel
		 * Builds secondary panel
		 ****************************************************************/
		this.width = 758;
		this.height = 758;
		this.setPreferredSize(new Dimension(this.width, this.height));
		this.setBackground(Color.DARK_GRAY);
		initialize();
	}

	/***************************************************************
	 * Paints the roads and trafficlights
	 ****************************************************************/
	@Override
	protected void paintComponent(Graphics g) 
	{
		super.paintComponent(g);
		roads.forEach(road -> road.draw(g));
		trafficLights.forEach(trafficLight -> trafficLight.draw(g));
	}

	/***************************************************************
	 * Calls to build each car based on the user amount entered
	 ****************************************************************/
	public void populateCars()
	{
		for (int i = 0; i < carCount ; i++) 
		{
			addRandomCar();
		}
	}

	/***************************************************************
	 * Builds a car thread when called
	 ****************************************************************/
	public Car addRandomCar()
	{
		Random random = new Random();
		Road road = roads.get(random.nextInt(roads.size()));
		car = new Car(road, this.carShape, this.x1, this.y1, this.x2, this.y2, 
				this.x3, this.y3, this.speed1, this.speed2, this.speed3, 
				this.horizontalRoads, this.verticalRoads);
		road.addCar(car);
		this.carColor = car.getColor();
		this.carSpeed = car.getSpeed();
		this.revalidate();
		return car; 
	}

	/***************************************************************
	 * Uses the timer to be able to play, stop and pause the threads
	 ****************************************************************/
	public void playStopPause(Timer timer)
	{
		this.timer = timer;
		trafficLights.forEach(trafficLight ->
		trafficLight.playStopPause(timer)
				);
		roads.forEach(road -> 
		{
			cars = road.getCars();
			cars.forEach( car -> car.passTimer(timer));
		});
	}

	/***************************************************************
	 * Calculates where the roads where be placed based on how many 
	 * intersections the user defined
	 ****************************************************************/
	public void initialize() 
	{
		int firstX = this.width / (verticalRoads+1);
		int firstY = this.height / (horizontalRoads+1);

		for (int i = 1; i <= this.verticalRoads; i++) 
		{
			int currentRoadX = firstX * i;
			YRoads road = new YRoads(currentRoadX, height);
			roads.add(road);
			nsRoads.add(road);
		}

		for (int i = 1; i <= this.horizontalRoads; i++) 
		{
			int currentRoadY = firstY * i;
			XRoads road = new XRoads(currentRoadY, width);
			roads.add(road);
			ewRoads.add(road);
		}

		nsRoads.forEach(nsRoad -> ewRoads.forEach(ewRoad -> 
		{
			TrafficLight trafficLight = new TrafficLight(nsRoad, ewRoad);
			nsRoad.addTrafficLight(trafficLight);
			ewRoad.addTrafficLight(trafficLight);
			trafficLights.add(trafficLight);
		}));

		populateCars();
	}

	/***************************************************************
	 * Sends each road and trafficlight into the thread pool
	 ****************************************************************/
	public void executeWorkers()
	{
		roads.forEach(road -> 
		{
			cars = road.getCars();
			cars.forEach(car -> executorService.submit(car));
		});
		trafficLights.forEach(light -> 
		executorService.submit(light)
		);
	}

	/***************************************************************
	 * Getters/Setters to call items from this class
	 ****************************************************************/
	public int getRowCount()
	{ 
		return this.horizontalRoads; 
	}
	public int getColumnCount() 
	{ 
		return this.verticalRoads; 
	}
	public int getCarCount() 
	{ 
		return this.carCount; 
	}
	public String getSuccess() 
	{ 
		return "Street Map Drawn"; 
	}
	public String getCurrentCarColor() 
	{ 
		return carColor; 
	}
	public int getCurrentCarSpeed() 
	{
		return carSpeed;
	}
}
