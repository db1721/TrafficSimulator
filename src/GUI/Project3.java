package GUI;

import java.awt.EventQueue;

/***************************************************************
 * File: Project 3 GUI
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Main of the project that runs the GUI
 ****************************************************************/
public class Project3
{
	public static void main(String[] args) 
	{	
		EventQueue.invokeLater(new Runnable() 
		{
			public void run() 
			{ 
				try 
				{
					@SuppressWarnings("unused")
					Project3 window = new Project3();
		 		} 
				catch (Exception e) 
				{
					e.printStackTrace();
				}
			}
		});
	}

	/***************************************************************
	 * Calls the GUI
	 ****************************************************************/
	private Project3() 
	{
		new GUI();
	}
}