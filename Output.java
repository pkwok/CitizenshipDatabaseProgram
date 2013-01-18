import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import javax.swing.*;

@SuppressWarnings("serial")
public class Output extends JPanel{

	JLabel header;
	static JTextArea studentField;
	static JTextArea clubField;
	JScrollPane pane;
	static GUI inputDemo = new GUI ();

	protected void StudentOutput (JFrame frame)
	{
		//student output frame
		frame.setTitle("Output Student Information");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (550, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (inputDemo.createMenuBar ());
		frame.setVisible (false);

		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setBackground(Color.RED);

		header = new JLabel();
		header.setText(Indent("Student#", 12) + Indent("Last Name", 20) + Indent("First Name", 20) + "Points");
		JScrollPane scrollPane = null;


		studentField = new JTextArea();
		studentField.setEditable(false);
		studentField.setFont(GUI.font6);
		studentField.setBackground(Color.black);
		studentField.setForeground(Color.white);

		scrollPane = new JScrollPane(studentField);
		header.setForeground(Color.blue);
		// header.setFont(GUI.font5);

		header.setFont(GUI.font6);
		GUI.addElement(panel,header, 25, 10,500, 25);
		GUI.addElement(panel,scrollPane, 25, 40,500, 280);

	}


	public static void setStudentOutput(ArrayList<Student> list)
	{
		studentField.setText("");

		String text = studentField.getText();
		studentField.append(text + "\n");

		for (int i=0; i < list.size(); i++)
		{
			String msg = Indent (NumberIndent (list.get(i).returnNumber()), 12) +
			Indent (list.get(i).returnLastName(), 20)+ Indent (list.get(i).returnFirstName(), 20)+
			list.get(i).returnPoints() + "\n";

			studentField.append(msg);
		}   
	}

	public static void OutputStudent (ArrayList <Student> oldlist, int fn, int sort)
	{// output mechanism
		ArrayList <Student> students = new ArrayList <Student> ();
		students = oldlist; // copy list

		if (students.size () != 0 && fn != 1)
			students = TrimList (students, fn); // set restrictions

		if (students.size () == 0)
		{       // list is empty after trimming
			String message = "No student in list meet requirements";
			JOptionPane.showMessageDialog(GUI.frame[5], message, "ERROR", JOptionPane.ERROR_MESSAGE);
		}
		else
		{
			PrintStream outputFile = null;
			String filename = "";

			// 4 different output files depending on output specifications
			if (fn == 1)
				filename = "output/TotalForAllStudents.txt";
			else if (fn == 2)
				filename = "output/TotalAllStudentsOver40.txt";
			else if (fn == 3)
				filename = "output/TotalForGrad.txt";
			else
				filename = "output/TotalForGradOver40.txt";

			try {
				outputFile = new PrintStream(new FileOutputStream(filename));

				students = MergeSort (students, 0, students.size () - 1, sort); // sorts (student number or name)

				setStudentOutput (students); // GUI output

				for (int i = 0; i < students.size (); i ++)
				{ // file output
					outputFile.print (Indent (NumberIndent (students.get (i).returnNumber ()), 12));
					outputFile.print (Indent (students.get (i).returnLastName (), 20));
					outputFile.print (Indent (students.get (i).returnFirstName (), 20));
					outputFile.println (Indent (Integer.toString(students.get(i).returnPoints ()), 4));
				}

				String message = "Information stored in " + filename;
				JOptionPane.showMessageDialog(GUI.frame[5], message, "INFO STORED", JOptionPane.INFORMATION_MESSAGE);

			} catch (FileNotFoundException e) {
				String message = "output file " + filename + " not found";
				JOptionPane.showMessageDialog(GUI.frame[5], message, "ERROR", JOptionPane.ERROR_MESSAGE);
			}
		}
	}

	protected void ClubOutput (JFrame frame)
	{
		// club output frame
		frame.setTitle("Output Club Information");
		frame.setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE);
		frame.setSize (550, 400);
		frame.setLocation (new Point (300, 100));
		frame.setJMenuBar (inputDemo.createMenuBar ());
		frame.setVisible (false);

		JPanel panel = new JPanel();
		frame.setContentPane (panel);
		panel.setLayout (null);
		panel.setBackground(Color.DARK_GRAY);

		header = new JLabel();
		header.setText(Indent("Club name", 40) + "# of members");
		header.setForeground(Color.white);
		header.setFont(GUI.font6);
		clubField = new JTextArea();
		clubField.setFont(GUI.font6);
		clubField.setEditable(false);
		clubField.setForeground(Color.red);
		clubField.setBackground(Color.lightGray);

		JScrollPane scrollPane = new JScrollPane(clubField);

		GUI.addElement(panel,header, 20,10,500, 25);
		GUI.addElement(panel,scrollPane, 20, 40,500, 280);
	}

	protected static void setClubOutput (ArrayList<Club> list)
	{// set output for club text field
		clubField.setText("");

		String text = clubField.getText();
		clubField.append(text + "\n");

		for (int i=0; i < list.size(); i++)
		{
			clubField.append (Indent (list.get(i).getClubName(), 40));
			clubField.append (Indent ("" + list.get(i).getNumberOfStudents(), 5) + "\n");
			for (int j = 0; j < list.get(i).getNumberOfStudents(); j++){
				clubField.append (Indent ("" + NumberIndent (list.get(i).getListOfStudentsInClub ().get(j).returnNumber()), 12));
				clubField.append (Indent (list.get(i).getListOfStudentsInClub ().get(j).returnLastName(), 20));
				clubField.append (Indent (list.get(i).getListOfStudentsInClub ().get(j).returnFirstName(), 20));
				clubField.append (list.get(i).getListOfStudentsInClub ().get(j).returnPoints() + "\n");
			}
			clubField.append ("\n");
		}

	}

	// outputs all clubs -> we specified file -> user specified directory, with info such as total students as well as
	// the number of points each student gets. The students in this list does not get sorted.
	public static void OutputClub (ArrayList <Club> clubs)
	{
		if (clubs.size () > 0)
		{

			PrintStream outputFile;
			try {   
				outputFile = new PrintStream(new FileOutputStream("output/clubs.txt"));

				setClubOutput (clubs); // GUI output

				for (int i = 0; i < clubs.size (); i++)
				{
					outputFile.println(clubs.get(i).getClubName());
					outputFile.println(clubs.get(i).getNumberOfStudents() + " student(s)"); 

					for (int j = 0; j < clubs.get(i).getNumberOfStudents(); j++){
						outputFile.print(Indent (NumberIndent (clubs.get(i).getListOfStudentsInClub ().get(j).returnNumber()), 12));
						outputFile.print(Indent (clubs.get(i).getListOfStudentsInClub ().get(j).returnFirstName(),20));
						outputFile.print(Indent (clubs.get(i).getListOfStudentsInClub ().get(j).returnLastName(),20));
						outputFile.println(Indent (Integer.toString(clubs.get(i).getListOfPointsAwarded().get(j).clubPoints()),4));
						// this is for file
					}
					outputFile.println ("");
				}

				String message = "club information stored in output/clubs.txt";
				JOptionPane.showMessageDialog(GUI.frame[6], message, "INFO STORED", JOptionPane.INFORMATION_MESSAGE);
			}
			catch (IOException e)
			{
				String message = "output file clubs.txt cannot be opened";
				JOptionPane.showMessageDialog(GUI.frame[6], message, "INFO STORED", JOptionPane.INFORMATION_MESSAGE);
			}
		}
		else
		{
			String message = "There are no clubs stored";
			JOptionPane.showMessageDialog(GUI.frame[6], message, "INFO STORED", JOptionPane.INFORMATION_MESSAGE);
		}
	}


	public static void StudentError (PrintStream outputFile, String firstName, String lastName, int studentNum)
	{
		String message = firstName + " " + lastName + ", student number " + studentNum + " is already in data base."; 
		JOptionPane.showMessageDialog(GUI.frame[3], message, "ERROR", JOptionPane.ERROR_MESSAGE);
		outputFile.print(Indent(firstName, 15));
		outputFile.println(Indent(lastName,20));
		outputFile.print(Indent (NumberIndent(studentNum), 12));
		outputFile.println(" is already in data base.");
		outputFile.println("          ---------                       ----------    ");
	}

	public static void ClubError (PrintStream outputFile, String clubname, int stuNum)
	{
		String message = clubname + "'s student with student number " + stuNum + " does not exist in database";
		JOptionPane.showMessageDialog(GUI.frame[4], message, "ERROR", JOptionPane.ERROR_MESSAGE);
		outputFile.println(clubname + "'s student with student number " + stuNum + " does not exist in database");
		outputFile.println("          ---------                       ----------    ");
	}

	public static ArrayList<Student> MergeSort (ArrayList<Student> students, int lower, int upper, int c)
	{
		if (lower < upper)
		{ // more than 1 element in array              
			int mid = (int)(upper + lower) / 2;     // splits original array into 2 (equal in size)

			ArrayList<Student> left;                // left array
			ArrayList<Student> right;               // right array
			ArrayList<Student> tot;                 // left and right recombined

			left = MergeSort (students, lower, mid, c);             // recursive with left array
			right = MergeSort (students, mid+1, upper, c);  // recursive with right
			tot =  Merge (left, right, c);                                  // recombine with order
			return tot;
		}
		else
		{       // only 1 element - can't split again
			ArrayList <Student> blah = new ArrayList <Student> (); // creates an arraylist of 1 element
			blah.add (students.get (lower));
			return blah; // return array of 1 element
		}

	}

	public static ArrayList<Student> Merge (ArrayList<Student> left, ArrayList<Student> right, int c)
	{
		ArrayList <Student> students = new ArrayList <Student> ();

		while ((!left.isEmpty()) || (!right.isEmpty())) // continue until merged
		{
			if ((!left.isEmpty ()) && (!right.isEmpty ())) // both lists are still non-empty
			{
				if (c == 2) // sort by number
				{
					if ((left.get (0).returnNumber ()) <= (right.get (0).returnNumber ()))  
					{       // add the smaller element onto students and remove (<= in case of error)
						students.add (left.get (0));
						left.remove (0);
					}
					else
					{
						students.add (right.get (0));
						right.remove (0);
					}
				}
				else // sort by name
				{
					if ((left.get (0).returnLastName ()).compareTo(right.get (0).returnLastName ()) < 0)    
					{ // the "alphabetically smaller" word is added onto students
						students.add (left.get (0));
						left.remove (0);
					}
					else if ((left.get (0).returnLastName ()).compareTo(right.get (0).returnLastName ()) > 0)
					{
						students.add (right.get (0));
						right.remove (0);
					}
					else // same last name. Compare first name instead
					{
						if ((left.get (0).returnFirstName ()).compareTo(right.get (0).returnFirstName ()) < 0)
						{
							students.add (left.get (0));
							left.remove (0);
						}
						else if ((left.get (0).returnFirstName ()).compareTo(right.get (0).returnFirstName ()) > 0)
						{
							students.add (right.get (0));
							right.remove (0);
						}
						// if two students have the same full name, then student number will have to be used
						// since it will never duplicate
						else 
						{
							if (left.get(0).returnNumber () <= right.get(0).returnNumber ())
							{
								students.add (left.get (0));
								left.remove (0);
							}
							else
							{
								students.add (right.get(0));
								right.remove(0);
							}

						}
					}
				}
			}
			else if (right.isEmpty ()) // append left onto students
			{
				students.addAll (left);
				left.clear();           // removes all elements in left array to trigger loop exit statement
			}

			else if (left.isEmpty ()) // append right
			{
				students.addAll (right);
				right.clear();
			}

		}
		return students;
	}

	public static ArrayList <Student> TrimList (ArrayList <Student> students, int c)
	{
		ArrayList <Student> newList = new ArrayList <Student> (); 
		// starts out as empty and increases with each student that fit criteria

		if (c == 2)     // only students with over 40 points
		{
			for (int i = 0; i < students.size (); i ++)
			{
				if (students.get (i).returnPoints () >= 40)
					newList.add (students.get (i));
			}
		}
		else if (c == 3)        // only include grad students
		{
			for (int i = 0; i < students.size (); i ++)
			{
				if (students.get (i).graduating ())
					newList.add (students.get (i));
			}
		}
		else if (c == 4)        // only grad student with over 40 points
		{
			for (int i = 0; i < students.size (); i ++)
			{
				if ((students.get (i).returnPoints () >= 40) && (students.get (i).graduating ()))
					newList.add(students.get (i));
			}
		}

		return newList;
	}

	private static String Indent (String str, int tot)
	{
		if (str.length () < tot)
		{
			int j = tot - str.length ();
			for (int i =0; i < j;i++)
				str += " ";
		}
		return str;
	}

	public static String NumberIndent (int number)
	{
		if (number > 99999)
			return Integer.toString(number);
		else
		{
			String output = Integer.toString(number);
			int duration = 6 - output.length ();

			for (int i = 0; i < duration; i++)

				output = "0" + output;

			return output;
		}

	}

}
