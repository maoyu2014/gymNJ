package models.course;

//这个课程是共享的，仅仅表示一个课程。
//真正可以唯一定位一个课程的是TeamExerciseSchedule
public class TeamExercise {

	public int id;
	public String name;
	public String image;
	public double usedtime;	//时常
	public int strength;
	public String parts;
	public String introduce;
	public String notice;
	
	//展示使用
	public int one=0;
	public int two=0;
	public int three=0;
	public int four=0;
	public int five=0;
	
	public TeamExercise(int id, String name, String image, Double usedtime,
			int strength, String parts, String introduce, String notice) {
		this.id = id;
		this.name = name;
		this.image = image;
		this.usedtime = usedtime;
		this.strength = strength;
		this.parts = parts;
		this.introduce = introduce;
		this.notice = notice;
	}
	
	public TeamExercise(String name, String image, Double usedtime,
			int strength, String parts, String introduce, String notice) {
		this.name = name;
		this.image = image;
		this.usedtime = usedtime;
		this.strength = strength;
		this.parts = parts;
		this.introduce = introduce;
		this.notice = notice;
	}
	
	
}
