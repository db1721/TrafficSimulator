package Traffic;

import GUI.TrafficPanel;
import javax.swing.*;
import java.util.List;

/***************************************************************
 * File: Project 3 Time
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Runs the main panel of the code
 ****************************************************************/
public class Timer extends SwingWorker<Void, Integer> 
{
	/***************************************************************
	 * Global Variables
	 ****************************************************************/
    private boolean stop, pause;
    private final JTextField textField;
    private final TrafficPanel trafficPanel;

    /***************************************************************
	 * Constructor of the timer
	 * Populates the timer textfield
	 * repaints the panel
	 * Allows to pause and stop the simulation
	 ****************************************************************/
    public Timer(JTextField textField, TrafficPanel trafficPanel)
    {
        this.textField = textField;
        this.trafficPanel = trafficPanel;
        this.stop = false;
        this.pause = false;
    }
    
    /***************************************************************
	 * Counts up in seconds as the program continues
	 ****************************************************************/
    @Override
    protected Void doInBackground() throws Exception 
    {
        int seconds = 0;
        while (!stop)
        {
            if (!pause)
            {
                seconds++;
                publish(seconds);
            }
            Thread.sleep(1000);
        }
        return null;
    }

    /***************************************************************
	 * Repaints the timer as it continues
	 ****************************************************************/
    @Override
    protected void process(List<Integer> stopWatch) 
    {
        Integer second = stopWatch.get(stopWatch.size()-1);
        String time = String.format("%02d:%02d", second / 60, second % 60);
        textField.setText(time);
        trafficPanel.repaint();
    }

    /***************************************************************
	 * Getters/Setters to call items from this class
	 ****************************************************************/
    public void pause() 
    { 
    	this.pause = true; 
    }
    public void play() 
    { 
    	this.pause = false; 
    }
    public void stop() 
    { 
    	this.stop = true; 
    }
    public boolean isStop() 
    { 
    	return stop; 
    }
    public boolean isPause() 
    { 
    	return pause; 
    }
}
