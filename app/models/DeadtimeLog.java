package models;

public class DeadtimeLog {
	
	public int id;
	public int memberid;
	public String updatetime;
	public int cardtype;
	public String deadtime;
	public String employeename;
	
	public DeadtimeLog(int id, int memberid, String updatetime,	int cardtype, String deadtime, String employeename) {
		this.id = id;
		this.memberid = memberid;
		this.updatetime = updatetime;
		this.cardtype = cardtype;
		this.deadtime = deadtime;
		this.employeename = employeename;
	}
	
	public DeadtimeLog(int memberid, String updatetime,	int cardtype, String deadtime, String employeename) {
		this.memberid = memberid;
		this.updatetime = updatetime;
		this.cardtype = cardtype;
		this.deadtime = deadtime;
		this.employeename = employeename;
	}
	
	
}
