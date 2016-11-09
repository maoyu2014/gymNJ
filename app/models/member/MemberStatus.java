package models.member;

public class MemberStatus {

	public int id;
	public int memberid;
	public String memberstatus;
	public String edittime;
	public String reason;
	
	public MemberStatus(int id, int memberid, String memberstatus,
			String edittime, String reason) {
		this.id = id;
		this.memberid = memberid;
		this.memberstatus = memberstatus;
		this.edittime = edittime;
		this.reason = reason;
	}
	
	public MemberStatus(int memberid, String memberstatus,
			String edittime, String reason) {
		this.memberid = memberid;
		this.memberstatus = memberstatus;
		this.edittime = edittime;
		this.reason = reason;
	}
	
}
