package nba.service;

import javax.servlet.http.HttpServletRequest;

import nba.entity.User;
import nba.tool.Code;

import org.springframework.stereotype.Service;

@Service
public class AccountService extends DatabaseService {

    public String checkAccount(User user) throws Exception {

	if (user.getUser_name() == null || user.getUser_name().equals("")) {
	    return Code.NAMENULL;
	}
	if (user.getPassword() == null || user.getPassword().equals("")) {
	    return Code.PASSWORDNULL;
	}

	User user_db = this.get(User.class,
		"select * from user where user_name=?",
		new String[] { user.getUser_name() });

	if (user_db != null) {
	    return Code.ACCOUNTEXIST;
	} else {
	    this.save(user);
	}

	return Code.REGOK;
    }

    public String checkLogin(User user) throws Exception {

	if (user.getUser_name() == null || user.getUser_name().equals("")) {
	    return Code.NAMENULL;
	}
	if (user.getPassword() == null || user.getPassword().equals("")) {
	    return Code.PASSWORDNULL;
	}

	User user_db = this.get(User.class,
		"select * from user where user_name=? and password=?",
		new String[] { user.getUser_name(), user.getPassword() });

	if (user_db == null) {
	    return Code.LOGINERROR;
	} else {
	    user.setId(user_db.getId());
	}

	return Code.LOGINOK;
    }

    public User getUser(String userid) throws NumberFormatException, Exception {
	User user = this.get(User.class, Long.parseLong(userid));
	return user;
    }

    public User getLoginUser(HttpServletRequest request)
	    throws NumberFormatException, Exception {
	User user = (User) request.getSession().getAttribute("loginu");
	return user;
    }
}
