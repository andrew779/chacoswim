package model;

public class WaitingListModel2 {
	String id="",status="",firstName="",lastName="",term="",location="",level="",day="",time="";

	public WaitingListModel2(){
		
	}
	public WaitingListModel2(String id, String status, String firstName, String lastName, String term, String location,
			String level, String day, String time) {
		super();
		this.id = id;
		this.status = status;
		this.firstName = firstName;
		this.lastName = lastName;
		this.term = term;
		this.location = location;
		this.level = level;
		this.day = day;
		this.time = time;
	}

	
	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getTerm() {
		return term;
	}

	public void setTerm(String term) {
		this.term = term;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public String getDay() {
		return day;
	}

	public void setDay(String day) {
		this.day = day;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getId() {
		return id;
	}
	
	public void setId(String id){
		this.id = id;
	}
	
	@Override
	public String toString() {
		return "WaitingListModel2 [id=" + id + ", status=" + status + ", firstName=" + firstName + ", lastName="
				+ lastName + ", term=" + term + ", location=" + location + ", level=" + level + ", day=" + day
				+ ", time=" + time + "]";
	}
	
}
