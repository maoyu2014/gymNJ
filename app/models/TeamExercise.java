package models;

//这个团操的课程是共享的，切记。
//真正可以唯一定位一个团操的是TeamExerciseSchedule
public class TeamExercise {

	public int id;
	public String name;
	public String image;
	public double usedtime;	//时常
	public int strength;
	public String parts;
	public String introduce;
	public String notice;
	
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
