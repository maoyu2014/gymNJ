package models;

public class TeamExerciseScheduleShow {

	public int id;
	public int storeid;
	public int classroomid;
	public int employeeid;
	public int teamexerciseid;
	
	public int num;
	public int oknum;
	
	public String begintime;
	public String endtime;
	
	public String storename;
	public String classroomname;
	public String employeename;
	public String teamexercisename;


	public TeamExerciseScheduleShow(TeamExerciseSchedule gg) {
		this.id = gg.id;
		this.storeid = gg.storeid;
		this.classroomid = gg.classroomid;
		this.employeeid = gg.employeeid;
		this.teamexerciseid = gg.teamexerciseid;
		this.num = gg.num;
		this.oknum = gg.oknum;
		this.begintime = gg.begintime;
		this.endtime = gg.endtime;
	}
}
