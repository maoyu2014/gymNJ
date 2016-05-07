package models;

public class PrivateExerciseShow {

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
	public String exerciseweeknum;				//训练时间是礼拜几
	public String exercisebegintime;	//训练时间是几时几分
	public String exerciseendtime;
	public String signbegintime;		//报名时间
	public String signendtime;
	
	public String courseintroduce;	//课程介绍
	public String courseplan;		//课程安排，中间用等于号分割
	public String notice;			//注意事项
	public String fitstep;			//健身步骤，中间用等于号分割
	
	public String storename;
	public String classroomname;
	public String employeename;
	public String status;
	
	public PrivateExerciseShow(PrivateExercise gg) {
		this.id = gg.id;
		this.name = gg.name;
		this.image = gg.image;
		this.weeks = gg.weeks;
		this.period = gg.period;
		this.num = gg.num;
		this.oknum = gg.oknum;
		this.price = gg.price;
		this.storeid = gg.storeid;
		this.classroomid = gg.classroomid;
		this.employeeid = gg.employeeid;
		this.classbegintime = gg.classbegintime;
		this.classendtime = gg.classendtime;
		this.exerciseweeknum = gg.exerciseweeknum;
		this.exercisebegintime = gg.exercisebegintime;
		this.exerciseendtime = gg.exerciseendtime;
		this.signbegintime = gg.signbegintime;
		this.signendtime = gg.signendtime;
		this.courseintroduce = gg.courseintroduce;
		this.courseplan = gg.courseplan;
		this.notice = gg.notice;
		this.fitstep = gg.fitstep;
	}
	
	
	
}
