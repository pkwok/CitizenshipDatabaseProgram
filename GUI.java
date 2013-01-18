import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;
import javax.swing.*;


@SuppressWarnings("serial")
public class GUI extends JFrame implements ActionListener {

	static Student stu; 
	static String menuOption = "Add Student";
	
	static JFrame [] frame = new JFrame [7];
	
	private JTextField numField, lastNameField, firstNameField, clubNameField, studentTextField, clubTextField; 
	private JLabel errorMsg, text, text2;
	private JRadioButton gradT, gradF, def, custom;
	private ButtonGroup option;
	
	static Scanner input = new Scanner (System.in);
	

	int sort = 1;
	boolean grad;
	// fonts to be used
	
	static Font font = new Font("Cambria", Font.BOLD, 20);
	static Font font1 = new Font("Cambria", Font.BOLD, 35);
	static Font font2 = new Font("Cambria",Font.BOLD, 16);
	static Font font3 = new Font("Arial", Font.BOLD, 15);
	static Font font4 = new Font("Arial", Font.PLAIN, 14);
	static Font font5 = new Font("Cambria",Font.BOLD, 14);
	static Font font6 = new Font(Font.MONOSPACED, Font.PLAIN,14);
	
	protected JMenuBar createMenuBar() {
		JMenuBar menuBar;
		JMenu menu, submenu;
		JMenuItem menuItem;
        JRadioButtonMenuItem rbMenuItem;
        
		//Create the menu bar.
		menuBar = new JMenuBar();
		//Build the first menu.
		menu = new JMenu("Input Menu");
		menuBar.add(menu);
		//a subMenu for student information auto input
		menuItem = new JMenuItem("Input Student Info Automatically");
		menuItem.addActionListener (this);

		menu.add(menuItem);
		//club information auto input
		menuItem = new JMenuItem("Input Clubs Info Automatically");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		// menu separator
		menu.addSeparator();
		menuItem = new JMenuItem("Add Student Info Manually");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		menuItem = new JMenuItem("Add Club");
		menuItem.addActionListener(this);
		menu.add(menuItem);

		//Build the second menu. OUTPUT MENU
		menu = new JMenu("Output Menu");
		menuBar.add(menu);
		// submenu for outputing student info
		
		submenu = new JMenu ("Output Student Information");
		menuItem = new JMenuItem("Output all student");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menuItem = new JMenuItem("Output all students over 40 points");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menuItem = new JMenuItem("Output all graduate students");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		menuItem = new JMenuItem("Output all graduate students over 40 points");
		menuItem.addActionListener(this);
		submenu.add(menuItem);
		   //a group of radio button menu items
        submenu.addSeparator();
        ButtonGroup group = new ButtonGroup();

        rbMenuItem = new JRadioButtonMenuItem("Sort Outputs by Last Name");
        rbMenuItem.setSelected(true);
        rbMenuItem.setMnemonic(KeyEvent.VK_R);
        group.add(rbMenuItem);
        rbMenuItem.addActionListener(this);
        submenu.add(rbMenuItem);
        rbMenuItem = new JRadioButtonMenuItem("Sort Outputs by Student Number");
        rbMenuItem.setMnemonic(KeyEvent.VK_O);
        group.add(rbMenuItem);
        rbMenuItem.addActionListener(this);
        submenu.add(rbMenuItem);
        menu.add(submenu);
        
		
		menuItem = new JMenuItem("Output all clubs");
		menuItem.addActionListener(this);
		menu.add(menuItem);
		
		return menuBar;
	}
	
	protected void Menu (JFrame frame)
	{
		frame.setTitle("Menu");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (500, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (createMenuBar ());
		frame.setVisible (false);
		
		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setVisible (true);
		panel.setBackground(Color.white);
		
		text = new JLabel();
		text.setFont(font1);
		text.setForeground(Color.magenta);
		text.setText ("Citizenship Program");
		
		text2 = new JLabel ();
		text2.setFont(font);
		text2.setForeground(Color.red);
		text2.setText ("Please use the drop down menu to access options");
		ImageIcon image = createImageIcon("pic.png");
		JLabel pic = new JLabel(image);
		addElement(panel, pic, 180, 180, 130,120);
		//------
		addElement (panel, text, 80, 10, 400, 50);
		addElement (panel, text2, 10, 100, 700, 50);
		////------
	}
	protected static ImageIcon createImageIcon(String path) {
		java.net.URL imgURL = GUI.class.getResource(path);
		if (imgURL != null) {
			return new ImageIcon(imgURL);
		} else {
			System.err.println("Couldn't find file: " + path);
			return null;
		}
	}
	protected void addStudent(JFrame frame){
		// set the title
		frame.setTitle("CITIZENSHIP PROGRAM");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (500, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (createMenuBar ());
		frame.setVisible (false);
		
		// sets up panel
		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setBackground(Color.white);
		//Create the label panels and sets the positions
		
		text = new JLabel();
		text.setFont(font2);
		text.setForeground(Color.BLUE);
		text.setText ("Enter Student Information:");
		addElement(panel, text, 20, 20,300, 25);
		
		JLabel nameLab = new JLabel("Student Number:");
		nameLab.setFont(font2);
		nameLab.setForeground(Color.GRAY);
		addElement(panel, nameLab, 50, 50,200, 25);
		
		JLabel lastLab = new JLabel("Last Name:");
		lastLab.setFont(font2);
		lastLab.setForeground(Color.GRAY);
		addElement(panel, lastLab, 50, 90,200, 25);
		
		JLabel FirstLab = new JLabel("First Name:");
		FirstLab.setFont(font2);
		FirstLab.setForeground(Color.GRAY);
		addElement(panel,FirstLab, 50, 130,200, 25);

		// adds the error msg but does not display it
		errorMsg = new JLabel("Error. Fill in ALL blanks.");
		addElement(panel, errorMsg, 10, 218,300, 20);
		errorMsg.setForeground(Color.RED);
		errorMsg.setVisible (false);

		// student number, and first + last name textfields
		numField = new JTextField();
		numField.setText (null);
		numField.setForeground(Color.red);
		numField.setFont(font3);
		numField.addActionListener (this);
		addElement (panel, numField,200 , 50, 150, 25);
		
		// last name text field
		lastNameField = new JTextField();
		lastNameField.setText (null);
		lastNameField.setForeground(Color.blue);
		lastNameField.setFont(font4);
		lastNameField.addActionListener (this);
		addElement (panel, lastNameField,200 , 90, 150, 25);
		
		// first name text field
		firstNameField = new JTextField();
		firstNameField.setText (null);
		firstNameField.setForeground(Color.blue);
		firstNameField.setFont(font4);
		firstNameField.addActionListener (this);
		addElement (panel, firstNameField,200 , 130, 150, 25);
		
		// add radio buttons COUNT
		gradT = new JRadioButton("Is Graduating");
		gradT.setFont(font4);
		gradT.setBackground(Color.white);
		gradT.setForeground(Color.pink);
		gradT.setMnemonic(KeyEvent.VK_H);
		addElement(panel, gradT, 100,170,110,30);
		
		// add radio button EXCLUDE
		gradF = new JRadioButton("Not Graduating");
		gradF.setMnemonic(KeyEvent.VK_E);
		gradF.setFont(font4);
		gradF.setBackground(Color.white);
		gradF.setForeground(Color.pink);
		addElement(panel, gradF, 210,170,200,30);
		
		// adds action listener to the radio buttons
		gradF.addActionListener(this);
		gradT.addActionListener(this);
		gradF.setSelected(true);
		
		// adds the two buttons (count and exclude) to the button group
		option = new ButtonGroup();
		option.add(gradF);
		option.add(gradT);
		
		// the submit button
		JButton submit = new JButton("Submit");
		submit.addActionListener (this);
		submit.setActionCommand ("add student");
		submit.setMnemonic (KeyEvent.VK_ENTER);
		addElement (panel, submit, 200, 215, 90, 30);
		
	}
	
	
	protected void addClub(JFrame frame){
		// output screen
		frame.setTitle("Club");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (500, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (createMenuBar ());
		frame.setVisible (false);
		
		//--- sets up the panel ---//
		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setVisible(true);
		panel.setBackground(Color.yellow);
		//Create the label panels and sets the positions
		
		
	
		// labels
		JLabel title1 = new JLabel("Club Information:");
		title1.setFont(font2);
		title1.setForeground(Color.gray);
		addElement(panel, title1, 20, 20,200, 20);

		// first name text field
		clubNameField = new JTextField();
		clubNameField.setText (null);
		clubNameField.setFont(font4);
		clubNameField.setForeground(Color.red);
		clubNameField.addActionListener (this);
		addElement (panel, clubNameField,135 , 60, 180, 25);

		JLabel options1 = new JLabel("Club Name: ");
		options1.setFont(font3);
		options1.setForeground(Color.magenta);
		addElement(panel, options1, 35, 60,100, 20);

		//	submit button
		JButton ok = new JButton("Submit");
		ok.setFont(font4);
		ok.setForeground(Color.green);
		ok.setBackground(Color.blue);
		ok.addActionListener(this);
		addElement(panel, ok,  200, 165, 90, 30);
		ok.setActionCommand("add club");
	}
	

	
	protected void newFile(JFrame frame, boolean grad){
		// output screen
	
		frame.setTitle("Get File Name");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (500, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (createMenuBar ());
		frame.setVisible (false);
		
		//--- sets up the panel ---//
		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setVisible(true);
		panel.setBackground(Color.pink);
		
		// labels
		JLabel title1 = new JLabel("Student Information File Name:");
		title1.setFont(font2);
		title1.setForeground(Color.gray);
		addElement(panel, title1, 20, 20,250, 20);
		// first name text field
		studentTextField = new JTextField();
		studentTextField.setText ("input/inputfile.txt");
		studentTextField.setFont(font3);
		studentTextField.setForeground(Color.blue);
		studentTextField.addActionListener (this);
		addElement (panel, studentTextField,90 , 60, 280, 25);

		// add radio buttons COUNT
		gradT = new JRadioButton("Is Graduating");
		gradT.setFont(font4);
		gradT.setForeground(Color.gray);
		gradT.setBackground(Color.pink);
		gradT.setMnemonic(KeyEvent.VK_H);
		addElement(panel, gradT, 100,100,110,30);
		
		// add radio button EXCLUDE
		gradF = new JRadioButton("Not Graduating");
		gradF.setFont(font4);
		gradF.setForeground(Color.gray);
		gradF.setBackground(Color.pink);
		addElement(panel, gradF, 210,100,200,30);
		
		// adds action listener to the radio buttons
		gradF.addActionListener(this);
		gradT.addActionListener(this);
		gradF.setSelected(true);
		gradT.setActionCommand ("grad");
		gradF.setActionCommand ("nograd");
		
		// adds the two buttons (count and exclude) to the button group
		option = new ButtonGroup();
		option.add(gradF);
		option.add(gradT);
		
		// add radio button EXCLUDE
		def = new JRadioButton("Default");
		def.setFont(font4);
		def.setForeground(Color.gray);
		def.setBackground(Color.pink);
		addElement(panel, def, 100,140,100,20);
		
		// add radio button EXCLUDE
		custom = new JRadioButton("Custom");
		custom.setFont(font4);
		custom.setForeground(Color.gray);
		custom.setBackground(Color.pink);
		addElement(panel, custom, 210,140,200,20);
		
		// adds action listener to the radio buttons
		def.addActionListener(this);
		custom.addActionListener(this);
		def.setSelected(true);
		def.setActionCommand ("default");
		custom.setActionCommand ("custom");
		
		// adds the two buttons (count and exclude) to the button group
		option = new ButtonGroup();
		option.add(def);
		option.add(custom);
		
		text = new JLabel ("Default file are inputfile.txt for normal students");
		text.setForeground (Color.blue);
		addElement (panel, text, 100, 220, 300, 20);

		text2 = new JLabel ("graduateStudents.txt for graduate students");
		text2.setForeground (Color.blue);
		addElement (panel, text2, 100, 240, 300, 20);

		
		//	submit button
		JButton ok = new JButton("Submit");
		ok.addActionListener(this);
		addElement(panel, ok,  200, 165, 90, 30);
		ok.setActionCommand("student file");
		

	}
	
	
	protected void newFolder(JFrame frame){
		frame.setTitle("Get Folder Name");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (500, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (createMenuBar ());
		frame.setVisible (false);
		
		//--- sets up the panel ---//
		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setVisible(true);
		panel.setBackground(Color.orange);
		// labels
		JLabel title1 = new JLabel("Club Folder Name:");
		title1.setFont(font2);
		title1.setForeground(Color.gray);
		addElement(panel, title1, 20, 20,250, 20);

		// first name text field
		clubTextField = new JTextField();
		clubTextField.setText ("datafiles/");
		clubTextField.setFont(font3);
		clubTextField.setForeground(Color.blue);
		clubTextField.addActionListener (this);
		addElement (panel, clubTextField,120 , 60, 180, 25);

		def = new JRadioButton("Default");
		def.setFont(font4);
		def.setForeground(Color.blue);
		def.setBackground(Color.orange);
		addElement(panel, def, 100,140,100,20);
		
		// add radio button EXCLUDE
		custom = new JRadioButton("Custom");
		custom.setFont(font4);
		custom.setForeground(Color.blue);
		custom.setBackground(Color.orange);
		addElement(panel, custom, 210,140,200,20);
		
		// adds action listener to the radio buttons
		def.addActionListener(this);
		custom.addActionListener(this);
		def.setSelected(true);
		def.setActionCommand ("defaultc");
		custom.setActionCommand ("customc");
		
		option = new ButtonGroup();
		option.add(def);
		option.add(custom);
		
		//	submit button
		JButton ok = new JButton("Submit");
		ok.addActionListener(this);
		addElement(panel, ok,  200, 180, 90, 30);
		ok.setActionCommand("club folder");

	}
	//The standard main method.
	
	
	protected static void createAndShowGUI() {
		//Create and set up the window.
		GUI inputDemo = new GUI ();
		Output outputDemo = new Output ();
		
		for (int i = 0; i < 7; i++)
			frame [i] = new JFrame ();
		
		// create all 7 frames at the start
		inputDemo.Menu (frame[0]); // create menu frame
		inputDemo.addStudent (frame[1]);
		inputDemo.addClub (frame[2]);
		inputDemo.newFile (frame[3], false);
		inputDemo.newFolder (frame[4]);
		outputDemo.StudentOutput (frame[5]);
		outputDemo.ClubOutput(frame[6]);

		frame[0].setVisible (true); // set to menu
	}
	
	public static void Run() {
		//Schedule a job for the event dispatch thread:
		//creating and showing this application's GUI.
		SwingUtilities.invokeLater(new Runnable() {
			public void run() {
				createAndShowGUI();
			}
		});
	}

	
	public void actionPerformed(ActionEvent e) {
		int error; // various error triggers
		
		try{
			CheckMenuOptions (e); // check if drop down menu options are chosen
		}
		
		// submit or okay buttons
		catch(java.lang.ClassCastException u)
		{
			SwitchInputFile (e); // automatically changes textfield in file input frame
			SwitchInputFolder (e); // automatically changes textfield in folder input frame
			
			if (e.getActionCommand().equals("add student"))
			{ // add student manually
				error = Main.addStudentManual (numField.getText(), lastNameField.getText(), firstNameField.getText(),gradT.isSelected ());
				if (error == 0)
				{ // none
					JOptionPane.showMessageDialog(frame[1], "New Student added.", "INFO", JOptionPane.INFORMATION_MESSAGE);
					numField.setText ("");
					lastNameField.setText ("");
					firstNameField.setText ("");
					// clears field
				}
				else if (error == -1)
					JOptionPane.showMessageDialog(frame[1], "Error. Not all fields are filled!", "ERROR", JOptionPane.ERROR_MESSAGE);  // display error
				else if (error == -2){
					JOptionPane.showMessageDialog(frame[1], "Error. Please enter only positive integers for student number!", "ERROR", JOptionPane.ERROR_MESSAGE);
				numField.setText ("");
				}
				else if (error == -3){
					JOptionPane.showMessageDialog(frame[1], "Error. Student Number Already In Records!", "ERROR", JOptionPane.ERROR_MESSAGE);
				numField.setText ("");}
				else if (error == -4){
					JOptionPane.showMessageDialog(frame[1], "Error. Student number must be 6 digits!", "ERROR", JOptionPane.ERROR_MESSAGE);
				numField.setText ("");
				}
			}
			
			else if (e.getActionCommand().equals("add club"))
			{ // add club manually
			//	System.out.println ("added at " + clubNameField.getText ());
				error = Main.addClubManual (clubNameField.getText());
				if (error == 0)
				{
					JOptionPane.showMessageDialog(frame[2], "Club Added", "INFO", JOptionPane.INFORMATION_MESSAGE);
					clubNameField.setText ("");
				}
				else if (error == -1)
					JOptionPane.showMessageDialog(frame[2], "Error. Club Already Exists", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			
			else if(e.getActionCommand().equals("student file"))
			{ // add student from file
				// see if student is graduate or not
				if (gradT.isSelected()){
					grad = true;
				}
				else{
					grad = false;
				}
				error = Main.StudentInformation(studentTextField.getText(), grad);    
				// outputs errors, if any
				if (error == 0)
				{
					JOptionPane.showMessageDialog(frame[1],"Finished inputing student information.", "FINISHED", JOptionPane.INFORMATION_MESSAGE);
					studentTextField.setText("");
				}
				else if (error == -1)
					JOptionPane.showMessageDialog(frame[1], "StudentErrors.txt couldn't be created", "ERROR", JOptionPane.ERROR_MESSAGE);
				else if (error == -2)
					JOptionPane.showMessageDialog(frame[1],"File not found. Re-enter File.", "ERROR", JOptionPane.ERROR_MESSAGE);
			}
			else if(e.getActionCommand().equals("club folder"))
			{
				try {
					Main.ClubInputMenu(clubTextField.getText());
				} catch (FileNotFoundException e1) {
				}
			}
		}
	}
	
	private void CheckMenuOptions (ActionEvent e)
	{
		JMenuItem source = (JMenuItem)(e.getSource());
		menuOption = source.getText();
		if (menuOption.equals("Add Student Info Manually"))
			selectFrame (1); // chooses add student frame
		
		else if (menuOption.equals("Add Club"))
			selectFrame (2); // chooses add club frame
		
		else if (menuOption.equals ("Input Student Info Automatically"))
			selectFrame (3); // basic student - auto
		
		else if (menuOption.equals("Input Clubs Info Automatically"))
			selectFrame (4); // club - auto
		
		else if (menuOption.equals("Output all student")){
			selectFrame (5);
			Output.OutputStudent (Main.students, 1, sort);
		}
		else if (menuOption.equals("Output all students over 40 points")){
			selectFrame (5);
			Output.OutputStudent (Main.students, 2, sort);
		}
		else if (menuOption.equals("Output all graduate students")){
			selectFrame (5);
			Output.OutputStudent (Main.students, 3, sort);
		}
		else if (menuOption.equals("Output all graduate students over 40 points")){
			selectFrame (5);
			Output.OutputStudent (Main.students, 4, sort);
		}
		else if (menuOption.equals("Sort Outputs by Last Name"))
			sort = 1;
		else if (menuOption.equals("Sort Outputs by Student Number"))
			sort = 2;
		
		else if (menuOption.equals("Output all clubs")){
			selectFrame (6);
			Output.OutputClub(Main.clubs);
		}
		
	}
	
	private void SwitchInputFile (ActionEvent e)
	{
		// no default file
		if (e.getActionCommand () == "custom")
			studentTextField.setText("");
		else if (e.getActionCommand() == "default")
		{
			if (gradT.isSelected () == true)
				studentTextField.setText("input/graduateStudents.txt");
			else
				studentTextField.setText("input/inputfile.txt");
		}
		else if (e.getActionCommand() == "grad" && custom.isSelected() == false)
			studentTextField.setText("input/graduateStudents.txt");
		else if (e.getActionCommand() == "nograd" && custom.isSelected() == false)
			studentTextField.setText("input/inputfile.txt");
	}
	
	private void SwitchInputFolder (ActionEvent e)
	{
		if (e.getActionCommand () == "customc")
		{		
			clubTextField.setText("");
		}
		else if (e.getActionCommand() == "defaultc")
			clubTextField.setText("datafiles/");
	}
	
	public static void addElement (Container c, Component e, int x, int y, int w, int h){
		// this adds a component onto the screen
		e.setBounds (x, y, w, h);
		c.add (e);
	}

	public void selectFrame (int n)
	{
		for (int i = 0; i < 7; i++)
			frame[i].setVisible(false);
		frame[n].setVisible (true);
	}
}