package models;

public class PrivateExercise {

	public int id;
	
	public String name;
	public String image;
	public int weeks;
	public int period;
	public int num;
	public int oknum;
	
	public double price;
	public int storeid;
	public int classroomid;
	public int employeeid;
	
	public String classbegintime;		//开课时间
	public String classendtime;
	public int exerciseweeknum;				//训练时间是礼拜几
	public String exercisebegintime;	//训练时间是几时几分
	public String exerciseendtime;
	public String signbegintime;		//报名时间
	public String signendtime;
	
	public String courseintroduce;	//课程介绍
	public String courseplan;		//课程安排，中间用逗号空格(, )号分割
	public String notice;			//注意事项
	public String fitstep;			//健身步骤，中间用逗号空格(, )号分割
	
	
	public PrivateExercise() {}
	
	public PrivateExercise(int id, String name, String image, int weeks,
			int period, int num, int oknum, double price, int storeid,
			int classroomid, int employeeid, String classbegintime,
			String classendtime, int exerciseweeknum, String exercisebegintime,
			String exerciseendtime, String signbegintime, String signendtime,
			String courseintroduce, String courseplan, String notice,
			String fitstep) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.weeks = weeks;
		this.period = period;
		this.num = num;
		this.oknum = oknum;
		this.price = price;
		this.storeid = storeid;
		this.classroomid = classroomid;
		this.employeeid = employeeid;
		this.classbegintime = classbegintime;
		this.classendtime = classendtime;
		this.exerciseweeknum = exerciseweeknum;
		this.exercisebegintime = exercisebegintime;
		this.exerciseendtime = exerciseendtime;
		this.signbegintime = signbegintime;
		this.signendtime = signendtime;
		this.courseintroduce = courseintroduce;
		this.courseplan = courseplan;
		this.notice = notice;
		this.fitstep = fitstep;
	}
	
	
	
	public PrivateExercise(String name, String image, int weeks,
			int period, int num, int oknum, double price, int storeid,
			int classroomid, int employeeid, String classbegintime,
			String classendtime, int exerciseweeknum, String exercisebegintime,
			String exerciseendtime, String signbegintime, String signendtime,
			String courseintroduce, String courseplan, String notice,
			String fitstep) {
		this.name = name;
		this.image = image;
		this.weeks = weeks;
		this.period = period;
		this.num = num;
		this.oknum = oknum;
		this.price = price;
		this.storeid = storeid;
		this.classroomid = classroomid;
		this.employeeid = employeeid;
		this.classbegintime = classbegintime;
		this.classendtime = classendtime;
		this.exerciseweeknum = exerciseweeknum;
		this.exercisebegintime = exercisebegintime;
		this.exerciseendtime = exerciseendtime;
		this.signbegintime = signbegintime;
		this.signendtime = signendtime;
		this.courseintroduce = courseintroduce;
		this.courseplan = courseplan;
		this.notice = notice;
		this.fitstep = fitstep;
	}
	
}
