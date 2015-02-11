package nba.service;

import org.springframework.stereotype.Service;

import nba.entity.User;
import nba.tool.Code;


@Service
public class AccountService extends DatabaseService {

	//验证账号有效性
	public String checkAccount(User user) throws Exception{
		
		//规则验证
		if(user.getUser_name()==null||user.getUser_name().equals("")){
			return Code.NAMENULL;
		}
		if(user.getPassword()==null||user.getPassword().equals("")){
			return Code.PASSWORDNULL;
		}
	
		//存在验证
		User user_db = this.get(User.class, "select * from user where user_name=?", new String[]{user.getUser_name()});
		
		if(user_db!=null){
			return Code.ACCOUNTEXIST;
		}else{
			this.save(user);
		}
		
		return Code.REGOK;
	}
}
