package nba.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.entity.Team;
import nba.entity.User;
import nba.service.AccountService;
import nba.service.GameService;
import nba.tool.Code;
import nba.tool.JsonTool;
import nba.tool.Message;
import nba.tool.WebUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/account")
public class AccountController {

    @RequestMapping("/login/")
    public void login(HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	JsonTool jt = null;
	String device = request.getParameter("device");

	try {
	    String user_name = request.getParameter("user_name");
	    String password = request.getParameter("password");

	    User user = new User(user_name, password);

	    String res = accountService.checkLogin(user);

	    jt = JsonTool.getJson(user);

	    if (!res.equals(Code.LOGINOK)) {
		jt.setMessage(Message.getMessage(request, res));
	    } else {

		if (device == null) {
		    WebUtil.setCookies(response, "loginuid", user.getId()
			    .toString());
		}

		request.getSession().setAttribute("loginu", user);
		Team team = gameService.getTeamByUser(user);
		request.getSession().setAttribute("team", team);
	    }
	} catch (Exception e) {
	    e.printStackTrace();
	    WebUtil.setCookies(response, "loginuid", "");
	    request.getSession().setAttribute("loginu", null);
	    jt.setMessage(Message.getMessage(request, "login_error"));
	}

	jt.write(response);
    }

    @RequestMapping("/register/")
    public void register(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {

	String user_name = request.getParameter("user_name");
	String password = request.getParameter("password");

	User user = new User();
	user.setUser_name(user_name);
	user.setPassword(password);

	String res = accountService.checkAccount(user);

	JsonTool jt = JsonTool.getJson(res);

	if (!res.equals(Code.REGOK)) {
	    jt.setMessage(Message.getMessage(request, res));
	}

	jt.write(response);
    }

    @RequestMapping("/reg/")
    public String reg(HttpServletRequest request, HttpServletResponse response)
	    throws Exception {

	return "reg";
    }

    @Resource
    private AccountService accountService;
    @Resource
    private GameService gameService;
}