package models.useless;


public class AnnouncementShow {

	public int id;
	public String name;
	
	public int storeid;
	public String storename;
	
	public String starttime;
	public String endtime;
	
	public String content;
	
	public int employeeid;
	public String employeename;
	
	public String status;
	
	public AnnouncementShow(int id, String name, int storeid, String storename,
			String starttime, String endtime, String content, int employeeid,
			String employeename, String status) {
		this.id = id;
		this.name = name;
		this.storeid = storeid;
		this.storename = storename;
		this.starttime = starttime;
		this.endtime = endtime;
		this.content = content;
		this.employeeid = employeeid;
		this.employeename = employeename;
		this.status = status;
	}
	
	public AnnouncementShow(Announcement aa) {
		this.id = aa.id;
		this.name = aa.name;
		this.storeid = aa.storeid;
		this.starttime = aa.starttime;
		this.endtime = aa.endtime;
		this.content = aa.content;
		this.employeeid = aa.employeeid;
	}
	
}
