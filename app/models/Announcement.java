package models;

public class Announcement {

	public int id;
	public String name;
	
	public int storeid;
	public String storename;
	
	public String starttime;
	public String endtime;
	
	public String content;
	
	public int employeeid;
	public String employeename;
	
	public Announcement(int id, String name, int storeid, String storename,
			String starttime, String endtime, String content, int employeeid,
			String employeename) {
		this.id = id;
		this.name = name;
		this.storeid = storeid;
		this.storename = storename;
		this.starttime = starttime;
		this.endtime = endtime;
		this.content = content;
		this.employeeid = employeeid;
		this.employeename = employeename;
	}
	
	public Announcement(String name, int storeid, String storename,
			String starttime, String endtime, String content, int employeeid,
			String employeename) {
		this.name = name;
		this.storeid = storeid;
		this.storename = storename;
		this.starttime = starttime;
		this.endtime = endtime;
		this.content = content;
		this.employeeid = employeeid;
		this.employeename = employeename;
	}
	
}
