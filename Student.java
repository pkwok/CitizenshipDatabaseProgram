// Student Class
/** This class stores a student number, student first and last name, grad status
 *  total points awarded and a participation object which stores the list of clubs 
 *  student participates in and how many points they are awarded in the club
 */
import java.util.ArrayList;

public class Student {
        int studentNumber;
        String lastName;
        String firstName;
        boolean isGraduating;
        int totalPoints;
        
        ArrayList<Participation> listOfClubsParticipated = new ArrayList<Participation>();
        Participation par;
        
        // Constructors of the student object
        Student(int number, String last, String first, boolean grad) {
                studentNumber = number;
                lastName = last;
                firstName = first;
                isGraduating = grad;
                totalPoints = 0;
        }
        // below methods sets and gets variables in this class
        public void changeGrad (){
                isGraduating = true;
        }

        // adds new club and points to the current student
        public void addParticipation (Participation participation, int points)
        {
                listOfClubsParticipated.add (participation);
                totalPoints+= points;
        }
        public int returnNumber() {
                return studentNumber;
        }

        public String returnLastName() {
                return lastName;
        }

        public String returnFirstName() {
                return firstName;
        }
        
        public int returnPoints () {
                return totalPoints;
        }
        public void changePoints(int points){
                totalPoints = points;
        }
        public boolean graduating() {
                return isGraduating;
        }
        
}
