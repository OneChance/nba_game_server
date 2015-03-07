package nba.controller;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nba.chart.ChartData;
import nba.chart.Data;
import nba.chart.Path;
import nba.entity.Arena;
import nba.entity.DayInLog;
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
	public void DealData(List<Player> playerList, HttpServletRequest request,
			Team team) throws Exception, NoSuchMethodException {

		Class pc = Player.class;
		Field[] fields = pc.getDeclaredFields();
		Map<String, Integer> sign_sal = gameService.getPlayerSalByTeam(team);

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

			// 填充签约金额
			player.setSign_sal(sign_sal.get(player.getPlayer_id().toString()));

			team.getArena().setTicket_price(
					gameService.calTicketsPrice(team.getEv()));

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

					DealData(playerList, request, team);

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

	@RequestMapping("/update_cap/")
	public void UpdateCap(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String res = "";

		User user = accountService.getLoginUser(request);
		Team team = gameService.getTeamByUser(user);
		Arena arena = team.getArena();

		int update_money = arena.getCap_level() * arena.getCap_level() * 5000;

		if (team.getTeam_money() <= update_money) {
			res = Code.NOTENOUGHMONEY;
		} else {
			arena.setCap_level(arena.getCap_level() + 1);
			team.setTeam_money(team.getTeam_money() - update_money);
			gameService.merge(team);
			res = Code.UPDATEOK;
		}

		JsonTool jt = JsonTool.getJson(res);

		if (!res.equals(Code.UPDATEOK)) {
			jt.setMessage(Message.getMessage(request, res));
		}

		jt.write(response);
	}

	@RequestMapping("/update_eq/")
	public void UpdateEq(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String res = "";

		User user = accountService.getLoginUser(request);
		Team team = gameService.getTeamByUser(user);
		Arena arena = team.getArena();

		int update_money = arena.getEq_level() * arena.getEq_level() * 5000;

		if (team.getTeam_money() <= update_money) {
			res = Code.NOTENOUGHMONEY;
		} else {
			arena.setEq_level(arena.getEq_level() + 1);
			team.setTeam_money(team.getTeam_money() - update_money);
			gameService.merge(team);
			res = Code.UPDATEOK;
		}

		JsonTool jt = JsonTool.getJson(res);

		if (!res.equals(Code.UPDATEOK)) {
			jt.setMessage(Message.getMessage(request, res));
		}

		jt.write(response);
	}

	@RequestMapping("/chart/")
	public String Chart(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		return "chart";
	}

	@RequestMapping("/get_chart/")
	public void GetChart(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String type = request.getParameter("type");
		String category = request.getParameter("category");
		List<ChartData> cdList = new ArrayList<ChartData>();

		if (type.equals("team_money")) {
			User user = accountService.getLoginUser(request);

			if (user != null) {

				Team team = gameService.getTeamByUser(user);

				request.getSession().setAttribute("team", team);

				if (team != null) {

					this.getTeamMoneyChart(team, category, cdList);

				}
			}
		}

		JsonTool.getJson(cdList).write(response);
	}

	public List<ChartData> getTeamMoneyChart(Team team, String category,
			List<ChartData> cdList) {

		ChartData cd = new ChartData();
		List<Path> pathList = new ArrayList<Path>();

		List<DayInLog> dilList = gameService.getDayInLogs(category);

		Path path_in = new Path();
		Path path_pay = new Path();
		Path path_profit = new Path();

		path_in.setClassName(".main.l1");
		path_pay.setClassName(".main.l2");
		path_profit.setClassName(".main.l3");

		List<Data> dataList_in = new ArrayList<Data>();
		List<Data> dataList_pay = new ArrayList<Data>();
		List<Data> dataList_profit = new ArrayList<Data>();
		
		for(DayInLog dil:dilList){
			Data data_in = new Data();
			data_in.setX(dil.getDay_in_date());
			data_in.setY(dil.getArena_in());
			
			Data data_pay = new Data();
			data_pay.setX(dil.getDay_in_date());
			data_pay.setY(dil.getPay());
			
			Data data_profit = new Data();
			data_profit.setX(dil.getDay_in_date());
			data_profit.setY(dil.getProfit());
			
			dataList_in.add(data_in);
			dataList_pay.add(data_pay);
			dataList_profit.add(data_profit);
		}

		path_in.setData(dataList_in);
		path_pay.setData(dataList_pay);
		path_profit.setData(dataList_profit);
		
		pathList.add(path_in);
		pathList.add(path_pay);
		pathList.add(path_profit);
		
		cd.setMain(pathList);

		cdList.add(cd);

		return cdList;
	}

	@Resource
	private AccountService accountService;
	@Resource
	private GameService gameService;
}