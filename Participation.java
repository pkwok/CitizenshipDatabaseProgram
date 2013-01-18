// @author Pauline Kwok, Jerry Cheng
// participation class shows name of club as well as number of points
public class Participation {
	String nameOfClub;
	int numOfPoints;
	
	// constructor
	Participation(String name, int points){
		nameOfClub = name;
		numOfPoints = points;
	}
	// below methods to return variables values
	public String clubName(){
		return nameOfClub;
	}
	public int clubPoints(){
		return numOfPoints;
	}

}
