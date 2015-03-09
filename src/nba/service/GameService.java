package nba.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.hibernate.Session;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import nba.chart.ChartData;
import nba.chart.Data;
import nba.chart.Path;
import nba.entity.Arena;
import nba.entity.DayInLog;
import nba.entity.GameData;
import nba.entity.Player;
import nba.entity.Team;
import nba.entity.TradeLog;
import nba.entity.User;
import nba.tool.Code;

@Service
public class GameService extends DatabaseService {

	public static int SAL_RATIO = 1;

	public Team getTeamByUser(User user) throws Exception {

		List<Team> tList = this.getsByHibernate(Team.class,
				"from Team where user_id=?", new Long[] { user.getId() });

		if (tList != null && tList.size() > 0) {
			return tList.get(0);
		}

		return null;
	}

	public String getIdsFromTeam(Team team) {
		String players = team.getPlayers();
		String team_player_ids = "";
		if (players != null && !players.equals("")) {
			String[] playerArray = players.split("@");
			for (String p : playerArray) {
				team_player_ids = team_player_ids + "," + p.split("-")[0];
			}
			team_player_ids = team_player_ids.substring(1);
			return team_player_ids;
		}
		return "";
	}

	public Map<String, Integer> getPlayerSalByTeam(Team team) {

		Map<String, Integer> player_sign_sale = new HashMap<String, Integer>();

		String players = team.getPlayers();
		if (players != null && !players.equals("")) {
			String[] playerArray = players.split("@");
			for (String p : playerArray) {
				player_sign_sale.put(p.split("-")[0],
						Integer.parseInt(p.split("-")[2]));
			}
		}
		return player_sign_sale;
	}

	public List<Player> getPlayersByTeam(Team team) throws Exception {

		String team_player_ids = getIdsFromTeam(team);

		if (team_player_ids.equals("")) {
			team_player_ids = "-1";
		}

		return this
				.gets(Player.class,
						"select a.*,shoot,free_throw,rebound,assist,steal,block,fault,foul,point,ev from player_data a left join (select * from game_data where game_date = curdate()) b "
								+ "on a.player_id = b.player_id where a.player_id in ("
								+ team_player_ids + ") order by sal desc ",
						null);
	}

	public String CreateTeam(Team team) {
		try {

			Team team_db = this.get(Team.class,
					"select * from team where user_id=?",
					new Long[] { team.getUser_id() });

			if (team_db != null) {
				return Code.CREATETEAMERROR;
			}

			this.save(team);

			return Code.CREATETEAMOK;
		} catch (Exception e) {
			e.printStackTrace();
			return Code.CREATETEAMERROR;
		}
	}

	public Player GetPlayer(Long player_id) throws Exception {
		return this.get(Player.class, player_id);
	}

	public List<Player> getPlayerByCondition(String condition) {
		return this.gets(Player.class, "select * from player_data where 1=1 "
				+ condition + " order by sal desc ", null);
	}

	public String SignPlayer(Player player, Team team) throws Exception {

		try {
			if (player.getSal() > team.getTeam_money()) {
				return Code.NOTENOUGHMONEY;
			}

			String players = player.getPlayer_id() + "-" + player.getPos()
					+ "-" + player.getSal();

			if (team.getPlayers() != null && !team.getPlayers().equals("")) {

				players = team.getPlayers() + "@" + player.getPlayer_id() + "-"
						+ player.getPos() + "-" + player.getSal();

				String res = checkPos(players);

				if (!res.equals("ok")) {
					return res;
				}
			}

			team.setPlayers(players);
			team.setTeam_money(team.getTeam_money() - player.getSal());

			TradeLog tl = new TradeLog(getNowDate(), player.getPlayer_id(),
					"in", player.getSal(), team.getUser_id());

			Session session = this.getSessionFactory().openSession();
			session.beginTransaction();
			session.merge(team);
			session.merge(tl);
			session.getTransaction().commit();

		} catch (Exception e) {
			return Code.OPERFIAL;
		}

		return Code.SIGNOK;
	}

	public String UnsignPlayer(Player player, Team team) throws Exception {

		String players = team.getPlayers();

		String new_team_array = "";

		for (String p : players.split("@")) {
			if (!p.split("-")[0].equals(player.getPlayer_id().toString())) {
				new_team_array = new_team_array + "@" + p;
			}
		}

		if (new_team_array.startsWith("@")) {
			new_team_array = new_team_array.substring(1);
		}

		team.setPlayers(new_team_array);
		team.setTeam_money(team.getTeam_money() + player.getSal());

		TradeLog tl = new TradeLog(getNowDate(), player.getPlayer_id(), "out",
				player.getSal(), team.getUser_id());

		Session session = this.getSessionFactory().openSession();
		session.beginTransaction();
		session.merge(team);
		session.merge(tl);
		session.getTransaction().commit();

		return Code.UNSIGNOK;
	}

	private String checkPos(String players) {

		String[] playerArray = players.split("@");

		if (playerArray.length > 5) {
			return Code.TEAMFULL;
		} else {
			if (!isValiTeam(playerArray)) {
				return Code.TEAMLISTERROR;
			}
		}
		return "ok";
	}

	private boolean isValiTeam(String[] playerArray) {

		Map<Integer, int[][]> proper = new HashMap<Integer, int[][]>();

		for (int i = 0; i < playerArray.length; i++) {
			if (playerArray[i].split("-")[1].equals("中锋/前锋")
					|| playerArray[i].split("-")[1].equals("前锋/中锋")) {
				proper.put(i, new int[][] { { 1, 0, 0 }, { 0, 1, 0 } });
			} else if (playerArray[i].split("-")[1].equals("后卫")) {
				proper.put(i, new int[][] { { 0, 0, 1 } });
			} else if (playerArray[i].split("-")[1].equals("前锋")) {
				proper.put(i, new int[][] { { 0, 1, 0 } });
			} else if (playerArray[i].split("-")[1].equals("中锋")) {
				proper.put(i, new int[][] { { 1, 0, 0 } });
			} else if (playerArray[i].split("-")[1].equals("前锋/后卫")
					|| playerArray[i].split("-")[1].equals("后卫/前锋")) {
				proper.put(i, new int[][] { { 0, 1, 0 }, { 0, 0, 1 } });
			}
		}

		int[][] p1 = proper.get(0);
		int[][] p2 = proper.get(1);
		int[][] p3 = proper.get(2);
		int[][] p4 = proper.get(3);
		int[][] p5 = proper.get(4);

		if (p3 == null) {
			p3 = new int[][] { { 0, 0, 0 } };
		}
		if (p4 == null) {
			p4 = new int[][] { { 0, 0, 0 } };
		}
		if (p5 == null) {
			p5 = new int[][] { { 0, 0, 0 } };
		}

		for (int i1 = 0; i1 < p1.length; i1++) {
			for (int i2 = 0; i2 < p2.length; i2++) {
				for (int i3 = 0; i3 < p3.length; i3++) {
					for (int i4 = 0; i4 < p4.length; i4++) {
						for (int i5 = 0; i5 < p5.length; i5++) {
							if (p1[i1][0] + p2[i2][0] + p3[i3][0] + p4[i4][0]
									+ p5[i5][0] <= 1
									&& p1[i1][1] + p2[i2][1] + p3[i3][1]
											+ p4[i4][1] + p5[i5][1] <= 2
									&& p1[i1][2] + p2[i2][2] + p3[i3][2]
											+ p4[i4][2] + p5[i5][2] <= 2) {

								return true;
							}
						}
					}
				}
			}
		}

		return false;
	}

	public void setImgSrc(List<Player> playerList) {

	}

	public boolean timeTradeAble() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date());
		if (Integer.parseInt(hour) >= 15) {
			return true;
		}
		return false;
	}

	public List<GameData> GetGamedataByCondition(String condition) {
		return this.gets(GameData.class, "select * from game_data where 1=1 "
				+ condition, null);
	}
	
	public List<GameData> GetGamedataInNum(int num,String condition) {
		return this.gets(GameData.class, "select * from game_data where 1=1 "
				+ condition + " LIMIT "+num , null);
	}

	/*
	 * 1. 当天ev结算2. 根据当前人气值，计算当天比赛球票价格3. 根据ev计算人气变化4. 根据球票价格，当天观众计算球馆收益5. 支付球员工资
	 * 6. 记录当天收益
	 */
	public void EvCal() throws Exception {

		List<Team> teams = this.getsByHibernate(Team.class, "from Team ", null);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<GameData> gamedatas = GetGamedataByCondition(" and game_date = '"
				+ sdf.format(new Date()) + "'");
		List<Player> players = this.getPlayerByCondition("");

		Map<String, Integer> player_ev = new HashMap<String, Integer>();
		Map<String, Player> playerMap = new HashMap<String, Player>();

		for (Player player : players) {
			playerMap.put(player.getPlayer_id().toString(), player);
		}

		for (GameData gamedata : gamedatas) {
			player_ev.put(gamedata.getPlayer_id(), gamedata.getEv());
		}

		List<Team> updateTeam = new ArrayList<Team>();
		List<DayInLog> logList = new ArrayList<DayInLog>();

		for (Team team : teams) {
			String player_ids = this.getIdsFromTeam(team);
			Map<String, Integer> player_sign_sale = getPlayerSalByTeam(team);

			if (!player_ids.equals("")) {

				String[] player_id_array = player_ids.split(",");

				if (player_id_array.length == 5) { // 球队有5人 才结算工资，获得比赛收益， 变动人气

					int fans_change = 0;
					int pay_sum = 0;
					int today_ingame_sal_sum = 0;// 今天球队有比赛的球员当前市场工资总额
					int current_sal_sum = 0;
					int ev_sum = 0;

					for (String player_id : player_id_array) {

						Player player = playerMap.get(player_id);

						if (player_ev.get(player_id) != null) { // 该球员今天有比赛

							// 根据ev计算人气变化
							fans_change += calFanNumByEv(player_ev
									.get(player_id));

							// 只要球队有比赛，就要支付工资(签约时工资)
							pay_sum += player_sign_sale.get(player_id) == null ? 0
									: player_sign_sale.get(player_id);

							today_ingame_sal_sum += player.getSal();

							ev_sum += player_ev.get(player_id);

						}

						current_sal_sum += player.getSal();

					}

					team.setTeam_money(team.getTeam_money() - pay_sum);

					int today_in = calTodayIn(team,
							(double) today_ingame_sal_sum
									/ (double) current_sal_sum);

					team.setEv(team.getEv() + fans_change);

					if (fans_change > 0) {
						team.setFans_change_state("up");
					} else {
						team.setFans_change_state("down");
					}

					updateTeam.add(team);

					// 记录当天收支情况
					DayInLog dil = new DayInLog(ev_sum, fans_change, today_in,
							pay_sum, (today_in - pay_sum),
							sdf.format(new Date()), team.getId());

					logList.add(dil);
				}

			}
		}

		Session session = this.getSessionFactory().openSession();
		session.beginTransaction();
		if (updateTeam.size() > 0) {
			for (Team team : updateTeam) {
				session.merge(team);
			}
		}
		if (logList.size() > 0) {
			for (DayInLog dil : logList) {
				session.merge(dil);
			}
		}
		session.getTransaction().commit();

	}

	public int calTicketsPrice(int fans) {
		// 球迷总数的平方根
		return (int) Math.round(Math.sqrt(fans));
	}

	public int calFanNumByEv(int ev) {

		return ev;
	}

	public int calTodayIn(Team team, double ev_contribute_ratio) {

		// 根据当前人气值，计算当天比赛球票价格
		int tickets_price = calTicketsPrice(team.getEv());

		Arena arena = team.getArena();

		// 到场观众数
		int fans_in = (int) (arena.getCap() * arena.getAttendance());

		// 今日有比赛球员
		return (int) (tickets_price * fans_in * ev_contribute_ratio);

	}

	public void GameDataGet() throws Exception {
		Document doc = Jsoup
				.connect("http://nba.sports.sina.com.cn/match_result.php?dpc=1")
				.timeout(0).get();
		Elements trs = doc.select("#table980middle tr");

		Calendar calendar = Calendar.getInstance();
		calendar.add(Calendar.DAY_OF_YEAR, -1);

		String today = new SimpleDateFormat("yyyyMMdd").format(calendar
				.getTime());
		String game_date = new SimpleDateFormat("yyyy-MM-dd")
				.format(new Date());

		List<GameData> gdList = new ArrayList<GameData>();
		List<Player> updatePlayerList = new ArrayList<Player>();
		List<Player> pList = this.getPlayerByCondition("");

		Map<String, Player> playerMap = new HashMap<String, Player>();

		for (Player p : pList) {
			playerMap.put(p.getPlayer_id().toString(), p);
		}

		for (Element tr : trs) {
			Elements tds = tr.select("td");

			String href = tds.get(8).select("a").attr("href");

			String id = getIdFromUrl(href);

			if (id.contains(today)) {

				Document one_game = Jsoup
						.connect("http://nba.sports.sina.com.cn/" + href)
						.timeout(0).get();
				Elements all_tr = one_game.select("#main tr");

				for (Element one_player : all_tr) {

					if (one_player.select("td").size() == 14) {

						Elements game_datas = one_player.select("td");

						String player_id = getIdFromUrl(game_datas.get(0)
								.select("a").attr("href"));
						String player_name = game_datas.get(0).select("a")
								.html();
						String game_time = game_datas.get(1).html();
						String shoot = game_datas.get(2).html();
						String point3 = game_datas.get(3).html();
						String free_throw = game_datas.get(4).html();
						String rebound_front = game_datas.get(5).html();
						String rebound_back = game_datas.get(6).html();
						String rebound = game_datas.get(7).html();
						String assist = game_datas.get(8).html();
						String steal = game_datas.get(9).html();
						String block = game_datas.get(10).html();
						String fault = game_datas.get(11).html();
						String foul = game_datas.get(12).html();
						String point = game_datas.get(13).html();

						if (!player_id.equals("")) {
							GameData gd = new GameData();
							gd.setPlayer_id(player_id);
							gd.setPlayer_name(player_name);
							gd.setGame_time(game_time);
							gd.setShoot(shoot);
							gd.setPoint3(point3);
							gd.setFree_throw(free_throw);
							gd.setRebound_front(rebound_front);
							gd.setRebound_back(rebound_back);
							gd.setRebound(rebound);
							gd.setAssist(assist);
							gd.setSteal(steal);
							gd.setBlock(block);
							gd.setFault(fault);
							gd.setFoul(foul);
							gd.setPoint(point);
							gd.setGame_date(game_date);

							Player player = playerMap.get(player_id);
							setEV(gd, player);

							if (player != null) {
								updatePlayerList.add(player);
							}

							gdList.add(gd);
						}
					} else if (one_player.select("td").size() == 2) {
						// 没有上场的球员
						Elements game_datas = one_player.select("td");

						String player_id = getIdFromUrl(game_datas.get(0)
								.select("a").attr("href"));
						String player_name = game_datas.get(0).select("a")
								.html();

						if (!player_id.equals("")) {
							GameData gd = new GameData();
							gd.setPlayer_id(player_id);
							gd.setPlayer_name(player_name);
							gd.setGame_date(game_date);
							gd.setEv(-5);

							Player player = playerMap.get(player_id);

							if (player != null) {
								int sal = new BigDecimal(player.getSal()).add(
										new BigDecimal(gd.getEv())).intValue();
								player.setSal(sal);
								updatePlayerList.add(player);
							}

							gdList.add(gd);
						}
					}
				}
			}
		}

		if (gdList.size() > 0)
			this.merge(gdList);
		if (updatePlayerList.size() > 0)
			this.merge(updatePlayerList);
	}

	private void setEV(GameData gd, Player player) {

		BigDecimal point = new BigDecimal(gd.getPoint());
		BigDecimal rebound = new BigDecimal(gd.getRebound());
		BigDecimal assist = new BigDecimal(gd.getAssist());
		BigDecimal steal = new BigDecimal(gd.getSteal());
		BigDecimal block = new BigDecimal(gd.getBlock());

		BigDecimal shoot_out = new BigDecimal(gd.getShoot().split("-")[1]);
		BigDecimal shoot_in = new BigDecimal(gd.getShoot().split("-")[0]);
		BigDecimal throw_out = new BigDecimal(gd.getFree_throw().split("-")[1]);
		BigDecimal throw_in = new BigDecimal(gd.getFree_throw().split("-")[0]);

		BigDecimal fault = new BigDecimal(gd.getFault());

		int ev_d = point.add(rebound).add(assist).add(steal).add(block)
				.subtract(shoot_out).add(shoot_in).subtract(throw_out)
				.add(throw_in).subtract(fault).intValue();

		ev_d = new BigDecimal(ev_d).multiply(new BigDecimal(SAL_RATIO))
				.intValue();

		gd.setEv(ev_d);

		if (player == null) {
			System.out.println(gd.getPlayer_name() + "[" + gd.getPlayer_id()
					+ "] no player info");
		} else {

			int sal = new BigDecimal(player.getSal()).add(new BigDecimal(ev_d))
					.intValue();

			player.setSal(sal);
		}

	}

	public void getTodayInLogByTeam(Team team) throws Exception {
		DayInLog dil = this.get(DayInLog.class,
				"select * from day_in_log where team_id=? and day_in_date=?",
				new Object[] { team.getId(), getNowDate() });
		if (dil == null) {
			dil = new DayInLog();
		}
		team.setDil(dil);
	}

	public List<DayInLog> getDayInLogs(String category) {

		List<DayInLog> dilList = new ArrayList<DayInLog>();

		String sql = "";
		if (category.equals("week")) {

			List<String> dates = dateToWeek();

			String date_condition = "";

			for (String date : dates) {
				date_condition = date_condition + ",'" + date + "'";
			}

			sql = "select * from day_in_log where day_in_date in ("
					+ date_condition.substring(1) + ")";
		}

		dilList = this.gets(DayInLog.class, sql, null);

		return dilList;
	}

	public List<String> dateToWeek() {
		List<String> dates = new ArrayList<String>();
		Calendar cal = Calendar.getInstance();

		int now_index = (cal.get(Calendar.DAY_OF_WEEK) - 1) == 0 ? 7 : (cal
				.get(Calendar.DAY_OF_WEEK) - 1);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		Long today = new Date().getTime();

		for (int i = 1; i < now_index; i++) {
			Long temp = today - 24 * 60 * 60 * 1000 * i;
			dates.add(sdf.format(new Date(temp)));
		}
		for (int i = 1; i <= (7 - now_index); i++) {
			Long temp = today + 24 * 60 * 60 * 1000 * i;
			dates.add(sdf.format(new Date(temp)));
		}

		dates.add(getNowDate());

		return dates;
	}

	public static void main(String[] args) {
		GameService g = new GameService();
		g.dateToWeek();
	}

	private String getIdFromUrl(String url) {
		int start = url.indexOf("=") + 1;

		String id = url.substring(start, url.length());

		return id;
	}

	public String getNowDate() {
		return new SimpleDateFormat("yyyy-MM-dd").format(new Date());
	}

	public List<ChartData> getTeamMoneyChart(Team team, String category,
			List<ChartData> cdList) {

		ChartData cd = new ChartData();
		List<Path> pathList = new ArrayList<Path>();

		List<DayInLog> dilList = this.getDayInLogs(category);

		Path path_in = new Path();
		Path path_pay = new Path();
		Path path_profit = new Path();

		path_in.setClassName(".main.l1");
		path_pay.setClassName(".main.l2");
		path_profit.setClassName(".main.l3");

		List<Data> dataList_in = new ArrayList<Data>();
		List<Data> dataList_pay = new ArrayList<Data>();
		List<Data> dataList_profit = new ArrayList<Data>();

		if (dilList != null) {
			for (DayInLog dil : dilList) {
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
		}

		return cdList;
	}

	public List<ChartData> getPlayerEvChart(String player_id,
			List<ChartData> cdList) {

		ChartData cd = new ChartData();
		List<Path> pathList = new ArrayList<Path>();

		List<GameData> gdList = this.GetGamedataInNum(7," and player_id="
				+ player_id + " order by game_date desc");

		Path path = new Path();

		path.setClassName(".main.l2");

		List<Data> dataList = new ArrayList<Data>();

		if (gdList != null) {
			for (GameData gd : gdList) {
				Data data = new Data();
				
				data.setX(gd.getGame_date());
				data.setY(gd.getEv());

				dataList.add(data);
			}

			path.setData(dataList);

			pathList.add(path);

			cd.setMain(pathList);

			cdList.add(cd);
		}

		return cdList;
	}
}
