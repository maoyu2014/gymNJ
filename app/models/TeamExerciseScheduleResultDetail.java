package models;

public class TeamExerciseScheduleResultDetail {
	public int id;
	public int teamexercisescheduleid;
	public int memberid;
	public String shijian1;
	public String shijian2;
	public String shijian3;
	public String shijian4;
	public String shijian5;
	public String shijian6;
	public String shijian7;
	public String shijian8;
	public TeamExerciseScheduleResultDetail(int teamexercisescheduleid,
			int memberid, String shijian1, String shijian2, String shijian3,
			String shijian4, String shijian5, String shijian6, String shijian7,
			String shijian8) {
		this.teamexercisescheduleid = teamexercisescheduleid;
		this.memberid = memberid;
		this.shijian1 = shijian1;
		this.shijian2 = shijian2;
		this.shijian3 = shijian3;
		this.shijian4 = shijian4;
		this.shijian5 = shijian5;
		this.shijian6 = shijian6;
		this.shijian7 = shijian7;
		this.shijian8 = shijian8;
	}

	
	
	
}
