package nba.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.tool.JsonTool;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;




@Controller
@RequestMapping("/account")
public class AccountController {

	@RequestMapping("/login/")
	public void login(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JsonTool.getJson("login").write(response);
	}
	
	@RequestMapping("/register/")
	public void register(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		JsonTool.getJson("register").write(response);
	}
}