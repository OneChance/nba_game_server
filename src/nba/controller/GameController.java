package nba.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;


@Controller
@RequestMapping("/game")
public class GameController {

	@RequestMapping("/myteam/")
	public String myteam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "myteam";
	}
	
	@RequestMapping("/market/")
	public String market(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		
		return "market";
	}


}