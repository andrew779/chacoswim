package chacoswim;

public class StudentObject {
	
	private String time;
	private String line;
	private String level;
	private String coach;
	private String name;
	
	public String getTime() {
		return time;
	}

	public String getLine() {
		return line;
	}

	public String getLevel() {
		return level;
	}

	public String getCoach() {
		return coach;
	}

	public String getName() {
		return name;
	}
	
	

	
	
	@Override
	public String toString() {
		return "StudentObject [time=" + time + ", line=" + line + ", level=" + level + ", coach=" + coach + ", name="
				+ name + "]\n";
	}

	public StudentObject(String time, String line, String level, String coach, String fname, String lname) {
		super();
		this.time = time;
		this.line = line;
		this.level = level;
		this.coach = coach;
		this.name = fname+" "+lname;
	}
	
	
	
	
	
}
