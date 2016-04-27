package models;

public class InOutInfo {

	public int id;
	public String deviceid;
	public int memberid;
	public int inOutType;		//0:进 1:出
	public String inOutTm;
	
	public InOutInfo(int id, String deviceid, int memberid, int inOutType, String inOutTm) {
		this.id = id;
		this.deviceid = deviceid;
		this.memberid = memberid;
		this.inOutType = inOutType;
		this.inOutTm = inOutTm;
	}
	
	public InOutInfo(String deviceid, int memberid, int inOutType, String inOutTm) {
		this.deviceid = deviceid;
		this.memberid = memberid;
		this.inOutType = inOutType;
		this.inOutTm = inOutTm;
	}
	
}
