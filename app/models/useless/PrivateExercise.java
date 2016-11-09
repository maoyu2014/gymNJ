package models.useless;

public class PrivateExercise {

	public int id;
	
	public String name;
	public String coursestyle;	//免费课程，基础课程，提高课程，强化课程
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
	public String exerciseweeknum;				//训练时间是礼拜几几几
	public String exercisebegintime;	//训练时间是几时几分
	public String exerciseendtime;
	public String signbegintime;		//报名时间
	public String signendtime;
	
	public String summary;			//内容简介
	public String courseintroduce;	//课程介绍
	public String courseplan;		//课程安排，中间用逗号空格(, )号分割,目前不用了
	public String notice;			//注意事项
	public String fitstep;			//健身步骤，中间用逗号空格(, )号分割
	
	public String storename;
	public String classroomname;
	public String employeename;
	public String status;
	
	public PrivateExercise() {}
	
	public PrivateExercise(int id, String name, String coursestyle, int weeks,
			int period, int num, int oknum, double price, String storename, String employeename, 
			String classbegintime, String classendtime, String exerciseweeknum, String exercisebegintime,
			String exerciseendtime, String signbegintime, String signendtime) {
		this.id = id;
		this.name = name;
		this.coursestyle = coursestyle;
		this.weeks = weeks;
		this.period = period;
		this.num = num;
		this.oknum = oknum;
		this.price = price;
		this.classbegintime = classbegintime;
		this.classendtime = classendtime;
		this.exerciseweeknum = exerciseweeknum;
		this.exercisebegintime = exercisebegintime;
		this.exerciseendtime = exerciseendtime;
		this.signbegintime = signbegintime;
		this.signendtime = signendtime;
		this.storename = storename;
		this.employeename = employeename;
	}
	
	public PrivateExercise(int id, String name, String coursestyle, String image, int weeks,
			int period, int num, int oknum, double price, int storeid,
			int classroomid, int employeeid, String classbegintime,
			String classendtime, String exerciseweeknum, String exercisebegintime,
			String exerciseendtime, String signbegintime, String signendtime,
			String summary, String courseintroduce, String courseplan, String notice,
			String fitstep) {
		this.id = id;
		this.name = name;
		this.coursestyle = coursestyle;
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
		this.summary = summary;
		this.courseintroduce = courseintroduce;
		this.courseplan = courseplan;
		this.notice = notice;
		this.fitstep = fitstep;
	}
	
	public PrivateExercise(String name, String coursestyle, String image, int weeks,
			int period, int num, int oknum, double price, int storeid,
			int classroomid, int employeeid, String classbegintime,
			String classendtime, String exerciseweeknum, String exercisebegintime,
			String exerciseendtime, String signbegintime, String signendtime,
			String summary, String courseintroduce, String courseplan, String notice,
			String fitstep) {
		this.name = name;
		this.coursestyle = coursestyle;
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
		this.summary = summary;
		this.courseintroduce = courseintroduce;
		this.courseplan = courseplan;
		this.notice = notice;
		this.fitstep = fitstep;
	}
	
}
