package models;

public class TeamExerciseShow {

	public int id;
	public String name;
	public String image;
	public double usedtime;	//时常
	public int strength;
	public String parts;
	public String introduce;
	public String notice;
	
	public int one=0;
	public int two=0;
	public int three=0;
	public int four=0;
	public int five=0;
	
	public TeamExerciseShow(TeamExercise gg) {
		this.id = gg.id;
		this.name = gg.name;
		this.image = gg.image;
		this.usedtime = gg.usedtime;
		this.strength = gg.strength;
		this.parts = gg.parts;
		this.introduce = gg.introduce;
		this.notice = gg.notice;
	}
	
}
