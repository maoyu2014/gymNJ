package models;

public class ComeInPassword {

	public int id;
	public int memberid;
	public String cdate;
	public int[] arr = new int[24];
	public String updateTime;
	
	public ComeInPassword(int id, int memberid, String cdate, int[] arr,String updateTime) {
		this.id = id;
		this.memberid = memberid;
		this.cdate = cdate;
		this.arr = arr;
		this.updateTime = updateTime;
	}
	
	
}
