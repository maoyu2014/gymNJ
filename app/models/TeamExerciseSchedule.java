package models;

//这个才是真正唯一定位一个团操的类
public class TeamExerciseSchedule {

	public int id;
	public int storeid;
	public int classroomid;
	public int employeeid;
	public int teamexerciseid;
	
	public int num;
	public int oknum;
	
	public String begintime;
	public String endtime;
	
	//展示信息
	public String storename;
	public String classroomname;
	public String employeename;
	public String teamexercisename;
	
	public TeamExerciseSchedule() {}
	
	public TeamExerciseSchedule(int id, int storeid, int classroomid,
			int employeeid, int teamexerciseid, int num, int oknum,
			String begintime, String endtime) {
		this.id = id;
		this.storeid = storeid;
		this.classroomid = classroomid;
		this.employeeid = employeeid;
		this.teamexerciseid = teamexerciseid;
		this.num = num;
		this.oknum = oknum;
		this.begintime = begintime;
		this.endtime = endtime;
	}
	
	public TeamExerciseSchedule(int storeid, int classroomid,
			int employeeid, int teamexerciseid, int num, int oknum,
			String begintime, String endtime) {
		this.storeid = storeid;
		this.classroomid = classroomid;
		this.employeeid = employeeid;
		this.teamexerciseid = teamexerciseid;
		this.num = num;
		this.oknum = oknum;
		this.begintime = begintime;
		this.endtime = endtime;
	}

	public TeamExerciseSchedule(int id, String storename,String classroomname, String employeename,
			String teamexercisename, int num, int oknum, String begintime,	String endtime) {
		this.id = id;
		this.storename = storename;
		this.classroomname = classroomname;
		this.employeename = employeename;
		this.teamexercisename = teamexercisename;
		this.num = num;
		this.oknum = oknum;
		this.begintime = begintime;
		this.endtime = endtime;
	}
	
	
}
