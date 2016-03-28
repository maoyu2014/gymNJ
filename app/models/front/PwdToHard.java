package models.front;

import java.util.ArrayList;
import java.util.List;

/*
 * 传递给硬件的密码json串
 */
public class PwdToHard {

	public String datatype;
	public String state;
	public String lastUpdateTime;
	public String josnSize;
	public List<InnerPwd> pAList = null;;
}

