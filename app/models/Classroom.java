package models;

public class Classroom {

	public int id;
	public String name;
	public int holdnumber;
	public int usage;	//1表示团操，2表示私教
	public int storeid;
	
	public Classroom(int id, String name, int holdnumber, int usage, int storeid) {
		this.id = id;
		this.name = name;
		this.holdnumber = holdnumber;
		this.usage = usage;
		this.storeid = storeid;
	}
	
	public Classroom(String name, int holdnumber, int usage, int storeid) {
		this.name = name;
		this.holdnumber = holdnumber;
		this.usage = usage;
		this.storeid = storeid;
	}
	
	
}
