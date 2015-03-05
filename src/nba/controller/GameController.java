package nba.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.entity.Arena;
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

	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void DealData(List<Player> playerList, HttpServletRequest request)
			throws Exception, NoSuchMethodException {

		Class pc = Player.class;
		Field[] fields = pc.getDeclaredFields();

		for (Player player : playerList) {

			for (Field f : fields) {
				String m_name = f.getName().substring(0, 1).toUpperCase()
						+ f.getName().substring(1, f.getName().length());
				Method getM = pc.getMethod("get" + m_name);
				Object value = getM.invoke(player);
				if (value == null) {
					Method setM = pc.getMethod("set" + m_name, String.class);
					setM.invoke(player, Message.getMessage(request, "no_data"));
				}

			}
		}
	}

	@RequestMapping("/myteam/")
	public String myteam(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		User user = accountService.getLoginUser(request);

		if (user != null) {

			Team team = gameService.getTeamByUser(user);

			request.getSession().setAttribute("team", team);

			if (team != null) {

				gameService.getTodayInLogByTeam(team);

				if (team.getPlayers() != null && !team.getPlayers().equals("")) {
					List<Player> playerList = gameService
							.getPlayersByTeam(team);
					gameService.setImgSrc(playerList);

					DealData(playerList, request);

					request.setAttribute("team_players", playerList);

					if (playerList.size() != 5) {
						request.setAttribute("not_enough_player", true);
					}
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

		team.setEv(100);
		team.setTeam_money(20000);

		// 创建球馆
		Arena arena = new Arena();
		arena.setArena_name(user.getUser_name()
				+ Message.getMessage(request, "default_arena_name"));
		arena.setEq_level(1);
		arena.setCap_level(1);

		team.setArena(arena);
		arena.setTeam(team);

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