package models;

public class FitnessPlan {
	
	public int id;
	public int style;	//1表示热身健身，2表示器械健身，3表示团操健身
	public String name;
	public int fitsex; //1表示适合男，2表示适合女
	public String parts; //锻炼部位
	public String number;
	
	public FitnessPlan(int id, int style, String name, int fitsex,
			String parts, String number) {
		this.id = id;
		this.style = style;
		this.name = name;
		this.fitsex = fitsex;
		this.parts = parts;
		this.number = number;
	}
	
	public FitnessPlan(int style, String name, int fitsex,
			String parts, String number) {
		this.style = style;
		this.name = name;
		this.fitsex = fitsex;
		this.parts = parts;
		this.number = number;
	}
	
}
