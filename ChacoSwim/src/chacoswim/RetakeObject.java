package chacoswim;

public class RetakeObject {
	private int levelID;
	private int sid;
	private int termID;
	public RetakeObject(int sid,int termID,int levelID){
		this.sid = sid;
		this.termID = termID;
		this.levelID = levelID;
	}
	public int getLevelID() {
		return levelID;
	}
	public void setLevelID(int levelID) {
		this.levelID = levelID;
	}
	public int getSid() {
		return sid;
	}
	public void setSid(int sid) {
		this.sid = sid;
	}
	public int getTermID() {
		return termID;
	}
	public void setTermID(int termID) {
		this.termID = termID;
	}
	
	
}
