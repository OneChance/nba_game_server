package nba.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.entity.User;
import nba.service.AccountService;
import nba.tool.Code;
import nba.tool.JsonTool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/account")
public class AccountController {

	@RequestMapping("/login/")
	public void login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JsonTool.getJson(Code.LOGINOK).write(response);
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
		
		JsonTool.getJson(res).write(response);
	}
	
	@RequestMapping("/reg/")
	public String reg(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "reg";
	}
	
	@Resource
	private AccountService accountService;
}