import java.io.*;
import java.util.*;

import javax.swing.JOptionPane;

/**
 * @author Paulinee Kwok, Jerry Cheng
 *
 */
/*
 Description
 This citizenship program reads in files and obtain student information (student number, first name,
 last name, graduation status), and outputs the errors in other files. It reads in club information
 for all the years and stores club name and number of points each student received. New students can be added.
 Finally, student and club information can be displayed and written to output files
 (with restrictions).
 */

public class Main {

	static Scanner input = new Scanner (System.in);
	static Student stu;
	static ArrayList <Student> students = new ArrayList<Student>();
	static ArrayList <Integer> graduates = new ArrayList<Integer>();
	static ArrayList <Club> clubs = new ArrayList<Club>();


	public static void main(String[] args) {
		GUI.Run (); // initializes gui
	}

	public static int StudentInformation (String file, boolean graduating) {
		int studentNum;
		String lastName, firstName;
		boolean existing = false;
		boolean opened = false; // check if studentErrors.txt has been created
		boolean flag = false;
		PrintStream outputFile = null;

		try{
			if (!opened){
				outputFile = new PrintStream(new FileOutputStream("errors/StudentErrors.txt", true));
				opened = true;
			}
			while (!flag){
				try {
					Scanner sc = new Scanner(new File(file));
					flag = true;

					// inputs the student number, first and last name
					while (sc.hasNext()){
						studentNum = sc.nextInt();
						lastName = sc.next();
						firstName = sc.next();
						existing = false;  // initializes existing to false
						// checks to see if student is existing from previous text files
						// loops for all existing student instances
						int s = 0;
						while(s < students.size() && existing == false)
						{
							// checks for the student number of each student
							if ((students.get(s).returnNumber()== studentNum)){
								// if student exists, and its a graduation text file their graduation status is changed to true
								if (graduating){
									students.get(s).changeGrad();
									graduates.add (studentNum); // add student number into grad list for future use
								}
								else{
									Output.StudentError (outputFile, firstName, lastName, studentNum);
								}

								existing = true;
							}
							s++;
						}
						// if current student input doesn't exist
						if (!existing){
							// Creates a new instance of student with these variables
							stu = new Student(studentNum, lastName, firstName, graduating);
							// Adds new instance into the students array list
							students.add(stu);
							if (graduating)
								graduates.add (studentNum);
							// add student number into grad list for future use
						}
					}

				}
				catch (FileNotFoundException e) {
					return -2; // if file isn't found, asks until file is found
				}
			}
		}
		catch(IOException e){
			return -1;
		}
		return 0;
	}

	public static int addStudentManual (String number, String last, String first, boolean grad)
	{
		if (number.equals("")|| last.equals("")|| first.equals("")){// if the name is blank
			return -1; // incomplete error
		}

		if (!IntCheck(number))
			return -2;	// not integer error

		for (int i = 0; i < students.size(); i++)
		{
			if (students.get(i).returnNumber()==Integer.parseInt(number)){
				return -3; // duplicate student number error
			}
		}
		if (number.length () != 6)
			return -4; // wrong length
		stu = new Student (Integer.parseInt(number), last, first, grad);
		students.add(stu);

		return 0; // no error
	}


	public static void ClubInputMenu(String folder) throws FileNotFoundException{

		String clubname;
		int stuNum;
		int points;
		boolean flag = false; // checks if the student number is existing or not
		Club club;
		Participation participation;
		int i = 0;

			// Loops for all the files in this folder
				try{
					File f = new File(folder);
					File[] files = f.listFiles();
					for (File file : files)
					{
						// name is the directory + the file name to be used for the scanner
						String name = folder + "/" + file.getName();
						PrintStream outputFile = new PrintStream
						(new FileOutputStream("errors/" + file.getName(), true));
						// opens a new scanner for the current text file
						Scanner sc = new Scanner(new File(name));
						// get the club name
						clubname = sc.nextLine();

						flag = false;
						// adds the club name to the participation array list
						while (flag == false && i < clubs.size ())
						{
							if (clubname.equals (clubs.get (i).getClubName ()))
							{
								flag = true; // club already exists
								i--; // negates the i++ line to keep track of club address
							}
							i++;

						}
                        if (flag)
                        {
                                String message = clubname + " already exists. \n Are you sure you want to reset club information?";
                                int n = JOptionPane.showConfirmDialog(GUI.frame[4],message, "ERROR", JOptionPane.YES_NO_OPTION);

                                if (n == JOptionPane.YES_OPTION)
                                {
                                        Main.clubs.remove (i);
                                        flag = false;
                                        JOptionPane.showMessageDialog(GUI.frame[4],clubname + "'s information changed", "INFO CHANGED", JOptionPane.INFORMATION_MESSAGE);
                                        outputFile.println (clubname + "'s information changed");
                                        outputFile.println("          ---------                       ----------    ");
                                }
                                else if (n == JOptionPane.NO_OPTION)
                                {
                                        JOptionPane.showMessageDialog(GUI.frame[4],clubname + " already exists in list", "ERROR", JOptionPane.ERROR_MESSAGE);
                                        outputFile.println(clubname + " already exists in list");
                                        outputFile.println("          ---------                       ----------    ");
                                }
                        }

						if (!flag)
						{
							club = new Club (clubname);
							Main.clubs.add (club);
							// Loops till the end of the text file
							while (sc.hasNext())
							{
								flag = false;
								stuNum = sc.nextInt();
								points = sc.nextInt();
								// loops for all the students in the students array
								for (int s = 0; s < Main.students.size(); s++){
									// checks for the student number of each student
									if (Main.students.get(s).returnNumber()== stuNum){
										// if the student is in the club, then club and points is
										// added to the participation arraylist in the student object
										participation = new Participation (clubname, points);
										Main.students.get(s).addParticipation(participation, points);
										Main.clubs.get(Main.clubs.size()-1).addStudent (Main.students.get(s));
										Main.clubs.get(Main.clubs.size()-1).addParticipation(participation);
										flag = true;
									}
								}
								if (!flag){
									Output.ClubError (outputFile, clubname, stuNum);
								}
							}
						}
					}
					//System.out.println ("Errors (if any) for each club are stored in errors folder");
				}

				// if file isn't found, asks until file is found
				catch (FileNotFoundException e) {
					String message = folder + " not found. Please re-enter folder directory: ";
					JOptionPane.showMessageDialog(GUI.frame[4], message, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				catch(NullPointerException e){
					String message = folder + " not found. Please re-enter folder directory: ";
					JOptionPane.showMessageDialog(GUI.frame[4], message, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
				catch(InputMismatchException e)
				{
					String message = folder + " is invalid. Please use another folder";
					JOptionPane.showMessageDialog(GUI.frame[4], message, "ERROR", JOptionPane.ERROR_MESSAGE);
				}
			}


	// add 1 club at a time
	public static int addClubManual (String name)
	{
		boolean check3 = true;

		// checks for existing clubs
		for (int l = 0; l < clubs.size(); l++)
		{
			if (clubs.get(l).getClubName ().equals(name))
				check3 = false;
		}

		if (check3){
			Club temp = new Club (name);
			clubs.add (temp);
			return 0;
		}
		else
			return -1;
	}

	// checks for positive integer
	public static boolean IntCheck (String str)
	{
		int blah;
		try
		{
			blah = Integer.parseInt(str);
			if (blah >= 0)
				return true;
			else
				return false;
		}
		catch (NumberFormatException blah2)
		{
			return false;
		}
	}
}
