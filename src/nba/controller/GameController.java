package nba.controller;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.entity.Player;
import nba.entity.Team;
import nba.entity.User;
import nba.service.AccountService;
import nba.service.GameService;
import nba.tool.Code;
import nba.tool.JsonTool;
import nba.tool.Message;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/game")
public class GameController {

	@RequestMapping("/myteam/")
	public String myteam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = accountService.getLoginUser(request);

		if (user != null) {

			Team team = gameService.getTeamByUser(user);

			request.getSession().setAttribute("team", team);

			if (team != null) {
				if (team.getPlayers() != null && !team.getPlayers().equals("")) {
					List<Player> playerList = gameService
							.getPlayersByTeam(team);
					gameService.setImgSrc(playerList);
					request.setAttribute("team_players", playerList);
					// 如果是手机访问 返回JSON对象封装
				}
			}
		}
		
		request.setAttribute("tradeAble", gameService.timeTradeAble());

		return "myteam";
	}

	@RequestMapping("/create_team/")
	public void CreateTeam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = accountService.getLoginUser(request);
		String team_name = request.getParameter("team_name");

		Team team = new Team(user.getId(), team_name);

		String res = gameService.CreateTeam(team);

		JsonTool jt = JsonTool.getJson(res);

		if (!res.equals(Code.CREATETEAMOK)) {
			jt.setMessage(Message.getMessage(request, res));
		}

		jt.write(response);

	}

	@RequestMapping("/market/")
	public String market(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String pos_select = request.getParameter("pos_select");

		String condition = "";

		if (pos_select != null && !pos_select.equals("")) {
			condition = " and pos like '%" + pos_select + "%'";
			request.setAttribute("pos_select", pos_select);
		}

		// 球队已有球员过滤
		User user = accountService.getLoginUser(request);
		Team team = gameService.getTeamByUser(user);
		String team_player_ids = gameService.getIdsFromTeam(team);

		if (!team_player_ids.equals("")) {
			condition = condition + " and player_id not in (" + team_player_ids
					+ ")";
		}

		List<Player> playerList = gameService.getPlayerByCondition(condition);

		gameService.setImgSrc(playerList);

		request.setAttribute("playerList", playerList);
		request.setAttribute("tradeAble", gameService.timeTradeAble());

		return "market";
	}

	@RequestMapping("/sign_player/")
	public void SignPlayer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String player_id = request.getParameter("player_id");

		Player player = gameService.GetPlayer(Long.parseLong(player_id));

		User user = accountService.getLoginUser(request);

		String res = "";

		if (user != null) {
			Team team = gameService.getTeamByUser(user);
			if (team != null) {
				res = gameService.SignPlayer(player, team);
			}
		} else {
			res = Code.NEEDLOGIN;
		}

		JsonTool jt = JsonTool.getJson(res);

		if (!res.equals(Code.SIGNOK)) {
			jt.setMessage(Message.getMessage(request, res));
		}

		jt.write(response);

	}
	
	@RequestMapping("/unsign_player/")
	public void UnsignPlayer(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String player_id = request.getParameter("player_id");

		Player player = gameService.GetPlayer(Long.parseLong(player_id));

		User user = accountService.getLoginUser(request);

		String res = "";

		if (user != null) {
			Team team = gameService.getTeamByUser(user);
			if (team != null) {
				res = gameService.UnsignPlayer(player, team);
			}
		} else {
			res = Code.NEEDLOGIN;
		}

		JsonTool jt = JsonTool.getJson(res);

		if (!res.equals(Code.UNSIGNOK)) {
			jt.setMessage(Message.getMessage(request, res));
		}

		jt.write(response);

	}

	@Resource
	private AccountService accountService;
	@Resource
	private GameService gameService;
}