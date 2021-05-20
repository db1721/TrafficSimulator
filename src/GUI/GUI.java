package GUI;

import java.awt.Color;
import javax.swing.BorderFactory;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSlider;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import Traffic.*;
import javax.swing.SwingConstants;

/***************************************************************
 * File: Project 3 GUI
 * Author: Dan Beck
 * Date: May 11, 2021
 * Purpose: Runs the main panel of the code
 ****************************************************************/
public class GUI extends JFrame
{
	/***************************************************************
	 * Global Variables
	 ****************************************************************/
	private static final long serialVersionUID = 1L;
	private JTextArea txtConsole;
	private String carShape;
	private Timer timer;
	private int carCounter;
	private JTextField timeElapsed;
	private JTextField carCountTxt;
	private TrafficPanel trafficPanel;
	private JButton drawBtn;
	private JButton stopBtn;
	private JButton addBtn;
	private JButton pauseBtn;
	private JButton startBtn;
	private JLabel car3Info;
	private JLabel car1Info;
	public JTextField textFieldCar1Y;
	public JTextField textFieldCar2Y;
	public JTextField textFieldCar3Y;
	public JTextField textFieldCar1X;
	public JTextField textFieldCar2X;
	public JTextField textFieldCar3X;
	public JTextField textFieldCar1Speed;
	public JTextField textFieldCar2Speed;
	public JTextField textFieldCar3Speed;
	private JLabel txtCarSpeed;
	private JLabel txtCarX;
	private JLabel txtCarY;

	/***************************************************************
	 * Java fix for allowing a list of drop down items
	 ****************************************************************/
	String[] carShapes = {"Circle","Square"};
	DefaultComboBoxModel<String> comboModel = new DefaultComboBoxModel<String>(carShapes);
	JComboBox<String> shapeSelector = new JComboBox<String>(comboModel);

	/***************************************************************
	 * Runs the application as public.
	 ****************************************************************/
	public GUI()
	{
		/***************************************************************
		 * GUI basics
		 ****************************************************************/
		super("Traffic Signal Program");
		setBounds(100, 100, 1095, 806);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setLocationRelativeTo(null);
		setTitle("Traffic Signal Program");
		setResizable(false);
		setVisible(true);
		getContentPane().setBackground(Color.WHITE);
		getContentPane().setLayout(null);

		/***************************************************************
		 * Text console
		 ****************************************************************/
		txtConsole = new JTextArea();
		txtConsole.setBounds(0, 387, 314, 265);
		txtConsole.setText("Select variables then press Draw");
		txtConsole.setEditable(false);
		txtConsole.setBorder(BorderFactory.createTitledBorder("Console"));
		txtConsole.setBackground(Color.WHITE);
		getContentPane().add(txtConsole);

		/***************************************************************
		 * Car shape selector
		 ****************************************************************/
		shapeSelector.setEditable(false);
		shapeSelector.setBounds(98, 8, 218, 20);
		shapeSelector.setSelectedIndex(0);
		getContentPane().add(shapeSelector);

		/***************************************************************
		 * Sliders
		 ****************************************************************/
		JSlider rowSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 2);
		rowSlider.setBounds(10, 36, 306, 68);
		rowSlider.setMajorTickSpacing(1);
		rowSlider.setMinorTickSpacing(1);
		rowSlider.setPaintTicks(true);
		rowSlider.setPaintLabels(true);
		rowSlider.setBorder(BorderFactory.createTitledBorder("Horizontal Roads"));
		rowSlider.setBackground(Color.WHITE);
		getContentPane().add(rowSlider);

		JSlider columnSlider = new JSlider(JSlider.HORIZONTAL, 1, 5, 2);
		columnSlider.setBounds(8, 115, 306, 68);
		columnSlider.setMajorTickSpacing(1);
		columnSlider.setMinorTickSpacing(1);
		columnSlider.setPaintTicks(true);
		columnSlider.setPaintLabels(true);
		columnSlider.setBorder(BorderFactory.createTitledBorder("Vertical Roads"));
		columnSlider.setBackground(Color.WHITE);
		getContentPane().add(columnSlider);

		JSlider carSlider = new JSlider(JSlider.HORIZONTAL, 1, 15, 1);
		carSlider.setBounds(8, 194, 306, 68);
		carSlider.setMajorTickSpacing(2);
		carSlider.setMinorTickSpacing(1);
		carSlider.setPaintTicks(true);
		carSlider.setPaintLabels(true);
		carSlider.setBorder(BorderFactory.createTitledBorder("Cars on Streets"));
		carSlider.setBackground(Color.WHITE);
		getContentPane().add(carSlider);

		/***************************************************************
		 * Draw Button
		 ****************************************************************/
		drawBtn = new JButton("Draw");
		drawBtn.setBounds(10, 273, 304, 23);
		//drawBtn.setBounds(x, y, width, height);
		getContentPane().add(drawBtn);
		drawBtn.addActionListener(event ->  
		{
			try 
			{
				String carShape = (String) shapeSelector.getSelectedItem();

				trafficPanel = new TrafficPanel(rowSlider.getValue(),
						columnSlider.getValue(),carSlider.getValue(),carShape,
						textFieldCar1X, textFieldCar1Y,textFieldCar2X, textFieldCar2Y, 
						textFieldCar3X, textFieldCar3Y, textFieldCar1Speed, 
						textFieldCar2Speed, textFieldCar3Speed
						);
				trafficPanel.setBounds(324, 8, 758, 758);
				getContentPane().add(trafficPanel);
				txtConsole.setText(trafficPanel.getSuccess() + "\n" + txtConsole.getText());
				txtConsole.setText("Press START to begin simulation" + "\n" + txtConsole.getText());
				repaint();

				afterDrawPress();
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Error in draw button!");
			}
		}); 

		/***************************************************************
		 * Pause Button
		 ****************************************************************/
		pauseBtn = new JButton("Pause");
		pauseBtn.setBounds(113, 319, 96, 23);
		getContentPane().add(pauseBtn);
		pauseBtn.addActionListener(event ->  
		{
			try 
			{
				timer.pause();
				txtConsole.setText("***PAUSED***" + "\n" + txtConsole.getText());
				whilePaused();
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Error in pause button!");
			}

		}); 

		/***************************************************************
		 * Start Button
		 ****************************************************************/
		startBtn = new JButton("Start");
		startBtn.setBounds(10, 319, 96, 23);
		getContentPane().add(startBtn);
		startBtn.addActionListener(event -> 
		{
			try
			{
				if (timer == null || timer.isStop())
				{
					timer = new Timer(this.timeElapsed, this.trafficPanel);
					this.trafficPanel.executorService.submit(timer);
					this.trafficPanel.playStopPause(timer);
					this.trafficPanel.executeWorkers();

					carCounter = carSlider.getValue();
					carCountTxt.setText(String.valueOf(carCounter));
				} 
				else 
				{
					timer.play();
				}
				afterStartPress();
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Error in start button!");
			}
		});

		/***************************************************************
		 * Stop Button
		 ****************************************************************/
		stopBtn = new JButton("Stop");
		stopBtn.setBounds(218, 319, 96, 23);
		getContentPane().add(stopBtn);
		stopBtn.addActionListener(event -> 
		{
			try
			{
				txtConsole.setText("***STOPPED***" + "\n" + txtConsole.getText());
				timeElapsed.setText("00:00");
				timer.stop();
				trafficPanel = new TrafficPanel(0, 0, 0, carShape, textFieldCar1X, textFieldCar1Y,
						textFieldCar2X, textFieldCar2Y, textFieldCar3X, textFieldCar3Y, 
						textFieldCar1Speed, textFieldCar2Speed, textFieldCar3Speed); 
				trafficPanel.setBounds(324, 8, 758, 758);
				trafficPanel.executorService.shutdown();
				getContentPane().add(trafficPanel);
				repaint();
				startUp();
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Error in stop button!");
			}
		});

		/***************************************************************
		 * Add Car Button
		 ****************************************************************/
		addBtn = new JButton("Add Car");
		addBtn.setBounds(113, 353, 96, 23);
		getContentPane().add(addBtn);
		addBtn.addActionListener(event -> 
		{
			try
			{
				carCounter++;
				carCountTxt.setText(String.valueOf(carCounter));
				Car car = this.trafficPanel.addRandomCar();
				car.passTimer(timer);
				trafficPanel.executorService.submit(car);
				this.trafficPanel.repaint();
				txtConsole.setText("A " + trafficPanel.getCurrentCarColor() + " car was added"
						+ "\n" + txtConsole.getText());
			}
			catch(Exception e1)
			{
				JOptionPane.showMessageDialog(null, "Error in add car button!");
			}
		});

		/***************************************************************
		 * Labels
		 ****************************************************************/
		JLabel txtShapeSelector = new JLabel("Car Shape");
		txtShapeSelector.setBounds(10, 11, 306, 14);
		getContentPane().add(txtShapeSelector);

		timeElapsed = new JTextField(4);
		timeElapsed.setEditable(false);
		timeElapsed.setBounds(218, 353, 96, 23);
		getContentPane().add(timeElapsed);

		carCountTxt = new JTextField(4);
		carCountTxt.setEditable(false);
		carCountTxt.setBounds(10, 353, 96, 23);
		getContentPane().add(carCountTxt);

		JLabel car2Info = new JLabel("Car 2 Data");
		car2Info.setBounds(10, 721, 77, 14);
		getContentPane().add(car2Info);

		car3Info = new JLabel("Car 3 Data");
		car3Info.setBounds(10, 752, 77, 14);
		getContentPane().add(car3Info);

		car1Info = new JLabel("Car 1 Data");
		car1Info.setBounds(10, 685, 77, 14);
		getContentPane().add(car1Info);

		txtCarSpeed = new JLabel("Speed");
		txtCarSpeed.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarSpeed.setBounds(98, 656, 77, 14);
		getContentPane().add(txtCarSpeed);

		txtCarX = new JLabel("X");
		txtCarX.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarX.setBounds(184, 656, 60, 14);
		getContentPane().add(txtCarX);

		txtCarY = new JLabel("Y");
		txtCarY.setHorizontalAlignment(SwingConstants.CENTER);
		txtCarY.setBounds(254, 656, 60, 14);
		getContentPane().add(txtCarY);

		startUp();
	}

	/***************************************************************
	 * Sets start up GUI config
	 ****************************************************************/
	public void startUp()
	{
		trafficPanel = new TrafficPanel(0, 0, 0, carShape, textFieldCar1X, textFieldCar1Y,
				textFieldCar2X, textFieldCar2Y, textFieldCar3X, textFieldCar3Y, 
				textFieldCar1Speed, textFieldCar2Speed, textFieldCar3Speed); 
		trafficPanel.setBounds(324, 8, 758, 758);
		getContentPane().add(trafficPanel);
		carCountTxt.setText("Car Counter");
		drawBtn.setEnabled(true);
		startBtn.setEnabled(false);
		pauseBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		addBtn.setEnabled(false);

		/***************************************************************
		 * car data text fields
		 ****************************************************************/
		textFieldCar1Y = new JTextField(4);
		textFieldCar1Y.setEditable(false);
		textFieldCar1Y.setText("0");
		textFieldCar1Y.setBounds(254, 681, 60, 23);
		getContentPane().add(textFieldCar1Y);

		textFieldCar2Y = new JTextField(4);
		textFieldCar2Y.setEditable(false);
		textFieldCar2Y.setText("0");
		textFieldCar2Y.setBounds(254, 715, 60, 23);
		getContentPane().add(textFieldCar2Y);

		textFieldCar3Y = new JTextField(4);
		textFieldCar3Y.setEditable(false);
		textFieldCar3Y.setText("0");
		textFieldCar3Y.setBounds(254, 748, 60, 23);
		getContentPane().add(textFieldCar3Y);

		textFieldCar1X = new JTextField(4);
		textFieldCar1X.setEditable(false);
		textFieldCar1X.setText("0");
		textFieldCar1X.setBounds(184, 681, 60, 23);
		getContentPane().add(textFieldCar1X);

		textFieldCar2X = new JTextField(4);
		textFieldCar2X.setEditable(false);
		textFieldCar2X.setText("0");
		textFieldCar2X.setBounds(184, 715, 60, 23);
		getContentPane().add(textFieldCar2X);

		textFieldCar3X = new JTextField(4);
		textFieldCar3X.setEditable(false);
		textFieldCar3X.setText("0");
		textFieldCar3X.setBounds(184, 748, 60, 23);
		getContentPane().add(textFieldCar3X);

		textFieldCar1Speed = new JTextField(4);
		textFieldCar1Speed.setEditable(false);
		textFieldCar1Speed.setText("0");
		textFieldCar1Speed.setBounds(98, 681, 77, 23);
		getContentPane().add(textFieldCar1Speed);

		textFieldCar2Speed = new JTextField(4);
		textFieldCar2Speed.setEditable(false);
		textFieldCar2Speed.setText("0");
		textFieldCar2Speed.setBounds(98, 717, 77, 23);
		getContentPane().add(textFieldCar2Speed);

		textFieldCar3Speed = new JTextField(4);
		textFieldCar3Speed.setEditable(false);
		textFieldCar3Speed.setText("0");
		textFieldCar3Speed.setBounds(98, 748, 77, 23);
		getContentPane().add(textFieldCar3Speed);
	}

	/***************************************************************
	 * GIU config after draw is pressed
	 ****************************************************************/
	public void afterDrawPress()
	{
		drawBtn.setEnabled(false);
		startBtn.setEnabled(true);
		pauseBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		addBtn.setEnabled(false);
	}

	/***************************************************************
	 * GUI config while paused
	 ****************************************************************/
	public void whilePaused()
	{
		drawBtn.setEnabled(false);
		startBtn.setEnabled(true);
		pauseBtn.setEnabled(false);
		stopBtn.setEnabled(false);
		addBtn.setEnabled(false);
	}

	/***************************************************************
	 * GUI config after start is pressed
	 ****************************************************************/
	public void afterStartPress()
	{
		drawBtn.setEnabled(false);
		startBtn.setEnabled(true);
		pauseBtn.setEnabled(true);
		stopBtn.setEnabled(true);
		addBtn.setEnabled(true);
	}
}
