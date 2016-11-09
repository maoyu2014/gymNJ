package models.course;

public class TeamExerciseScheduleResultInfo {
	public int id;
	public int teamexercisescheduleid;
	public String yundong1;
	public String yundong2;
	public String yundong3;
	public String yundong4;
	public String yundongliang;
	public String one2three;
	public String four2six;
	public String seven212;
	public TeamExerciseScheduleResultInfo(int teamexercisescheduleid,
			String yundong1, String yundong2, String yundong3, String yundong4,
			String yundongliang, String one2three, String four2six,
			String seven212) {
		this.teamexercisescheduleid = teamexercisescheduleid;
		this.yundong1 = yundong1;
		this.yundong2 = yundong2;
		this.yundong3 = yundong3;
		this.yundong4 = yundong4;
		this.yundongliang = yundongliang;
		this.one2three = one2three;
		this.four2six = four2six;
		this.seven212 = seven212;
	}
	
	
}
