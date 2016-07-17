package models;

//会员,已经不用了
public class MemberShow {

	public int id;
	public String openid;			//微信唯一标识
	public String name;
	public String wechatname;
	public String wechatnumber;
	public int sex;		//1男 2女
	public double height;
	public String birthday;	//生日
	public String phone;
	public int fingerprint;	//指纹状态，1已录入 2未录入
	public int comeinpassword;				//入场密码表，注意这个字段不在构造函数中，因为字段是变化的
	public int cityid;
	public int cardtype;	//会员卡种类，进来默认是0非会员  1月卡，2季卡，3半年卡，4年卡
	public String deaddate;	//到期时间
	
	public int exercisetime; 	//时间   1早上 2下午 3晚上
	public int exercisegoal; 	//目标   1减脂 2塑形 3增肌
	public int exercisehz;		//频率，1难的  2一周一次   3一周三次   4每天
	public int distance; 	//距离  1：一公里以内  2：三公里以内  3：三公里以外
	
	public String fitnesstest;	//体能测试
	public String memberstatus;	//会员状态
	
	//体脂信息
	public String bmi;			//BMI
	public String muscle;		//肌肉率
	public String fat;			//脂肪
	public String innerfat;		//内脏脂肪
	public String water;		//水分
	public String protein;		//蛋白质
	public int basicrate;	//基础代谢率
	public int bodyage;		//身体年龄
	
	public String recommendedphone;	//推介人手机号
	
	//展示信息
	public String sexvalue;
	public String fingerprinttype;
	public String cityname;
	public String offenstorename;
	public String cardtypename;
	public String exercisetimevalue;
	public String exercisegoalvalue;
	public String exercisehzvalue;
	public String distancevalue;
	
	public MemberShow() {
	}
	
	public MemberShow(Member gg) {
		this.id = gg.id;
		this.openid = gg.openid;
		this.name = gg.name;
		this.wechatname = gg.wechatname;
		this.sex = gg.sex;
		this.height = gg.height;
		this.birthday = gg.birthday;
		this.phone = gg.phone;
		this.fingerprint = gg.fingerprint;
		this.cityid = gg.cityid;
		this.cardtype = gg.cardtype;
		this.deaddate = gg.deaddate;
		this.exercisetime = gg.exercisetime;
		this.exercisegoal = gg.exercisegoal;
		this.exercisehz = gg.exercisehz;
		this.distance = gg.distance;
		this.bmi = gg.bmi;
		this.muscle = gg.muscle;
		this.fat = gg.fat;
		this.water = gg.water;
		this.protein = gg.protein;
		this.basicrate = gg.basicrate;
		this.bodyage = gg.bodyage;
	}
	
	
	
	
	
}
