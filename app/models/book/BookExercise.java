package models.book;

//预约课程
public class BookExercise {

	public int id;
	public int memberid;
	public int type;		//0 团操 1 特训营
	public int exerciseid;	//课程id，切记，id对应teamexerciseschedule表
	public String booktime;
	
	public String membername;
	public String typename;
	public String storename;
	public String exercisename;
	
	public String begintime;
	
	public BookExercise(int id, int memberid, int type, int exerciseid,	String booktime) {
		this.id = id;
		this.memberid = memberid;
		this.type = type;
		this.exerciseid = exerciseid;
		this.booktime = booktime;
	}
	
	public BookExercise(int id, String membername, int type, String exercisename, String booktime) {
		this.id = id;
		this.membername = membername;
		this.type = type;
		this.exercisename = exercisename;
		this.booktime = booktime;
	}
	
	
}
