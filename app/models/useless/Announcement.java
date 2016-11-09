package models.useless;

public class Announcement {

	public int id;
	public String name;
	
	public int storeid;
	
	public String starttime;
	public String endtime;
	
	public String content;
	
	public int employeeid;
	
	
	public Announcement(int id, String name, int storeid, 
			String starttime, String endtime, String content, int employeeid) {
		this.id = id;
		this.name = name;
		this.storeid = storeid;
		this.starttime = starttime;
		this.endtime = endtime;
		this.content = content;
		this.employeeid = employeeid;
	}
	
	public Announcement(String name, int storeid,
			String starttime, String endtime, String content, int employeeid) {
		this.name = name;
		this.storeid = storeid;
		this.starttime = starttime;
		this.endtime = endtime;
		this.content = content;
		this.employeeid = employeeid;
	}
	
}
