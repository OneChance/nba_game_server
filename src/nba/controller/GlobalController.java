package nba.controller;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.service.AccountService;
import nba.service.GlobalService;
import nba.tool.Code;
import nba.tool.JsonTool;
import nba.tool.WebUtil;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/global")
public class GlobalController {

	@RequestMapping
	public String global(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return "index";
	}

	@RequestMapping("/changeLocal/")
	public void changeLocal(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String local = request.getParameter("local");
		WebUtil.setCookies(response, "nbagame_lan", local);

		JsonTool.getJson(Code.CHANGELOCALOK).write(response);
	}

	@Resource
	private GlobalService globalService;
	@Resource
	private AccountService accountService;
	
}