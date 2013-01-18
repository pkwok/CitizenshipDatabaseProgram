// The club class 
// Contains club information of club name, number of students in club
// as well as list of students in club (points and student number in the student club) 
// and list of points  awarded with club name (in particpation object)
import java.util.ArrayList;

public class Club {
        String clubName;
        int numberOfStudents;
        ArrayList<Student> listOfStudentsInClub = new ArrayList<Student>();
        ArrayList<Participation> listOfPointsAwarded = new ArrayList<Participation>();
     	// constructor
        public Club(String name) {
                clubName = name;
        }
        // below methods to set and get value of variables
        public String getClubName() {
                return clubName;
        }
        public void setClubName(String clubName) {
                this.clubName = clubName;
        }
        public int getNumberOfStudents() {
                return numberOfStudents;
        }
        public void setNumberOfStudents(int numberOfStudents) {
                this.numberOfStudents = numberOfStudents;
        }
        public ArrayList<Student> getListOfStudentsInClub() {
                return listOfStudentsInClub;
        }
        public void setListOfStudentsInClub(ArrayList<Student> listOfStudentsInClub) {
                this.listOfStudentsInClub = listOfStudentsInClub;
        }
        public ArrayList<Participation> getListOfPointsAwarded() {
                return listOfPointsAwarded;
        }
        public void setListOfPointsAwarded(ArrayList<Participation> listOfPointsAwarded) {
                this.listOfPointsAwarded = listOfPointsAwarded;
        }
        // create another instance of student
        public void addStudent (Student student)
        {
                listOfStudentsInClub.add (student);
                numberOfStudents ++;
        }
     // create another instance of participation
        public void addParticipation (Participation participation)
        {
                listOfPointsAwarded.add (participation);
        }
}
