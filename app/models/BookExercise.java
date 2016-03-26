package models;

//预约团操
public class BookExercise {

	public int id;
	public int memberid;
	public int type;
	public int exerciseid;
	public String booktime;
	
	public BookExercise(int id, int memberid, int type, int exerciseid,
			String booktime) {
		this.id = id;
		this.memberid = memberid;
		this.type = type;
		this.exerciseid = exerciseid;
		this.booktime = booktime;
	}
	
	
}
