package models;

public class FitnessPlanShow {
	
	public int id;
	public int style;	//1表示热身健身，2表示器械健身，3表示团操健身
	public String name;		//如果style是3，这里存teamexerciseschedule的id
	public int fitsex; //1表示适合男，2表示适合女
	public String parts; //锻炼部位
	public String number;
	
	public String teamexercisename;
	
	public int one=0;
	public int two=0;
	public int three=0;
	public int four=0;
	public int five=0;
	public double geshu1=0;
	public double geshu2=0;
	public double geshu3=0;
	public double geshu4=0;
	public int geshu5=0;
	public double fenzhong1=0;
	public double fenzhong2=0;
	public double fenzhong3=0;
	public double fenzhong4=0;
	public int fenzhong5=0;
	public double keshu1=0;
	public double keshu2=0;
	public double keshu3=0;
	public double keshu4=0;
	public int keshu5=0;
	
	
	
	public FitnessPlanShow(int id, int style, String name, int fitsex,
			String parts, String number) {
		this.id = id;
		this.style = style;
		this.name = name;
		this.fitsex = fitsex;
		this.parts = parts;
		this.number = number;
	}
	
	public FitnessPlanShow(int style, String name, int fitsex,
			String parts, String number) {
		this.style = style;
		this.name = name;
		this.fitsex = fitsex;
		this.parts = parts;
		this.number = number;
	}
	
	public FitnessPlanShow(FitnessPlan gg) {
		this.id = gg.id;
		this.style = gg.style;
		this.name = gg.name;
		this.fitsex = gg.fitsex;
		this.parts = gg.parts;
		this.number = gg.number;
	}
	
}
