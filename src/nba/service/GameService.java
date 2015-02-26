package nba.service;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;
import nba.entity.GameData;
import nba.entity.Player;
import nba.entity.Team;
import nba.entity.User;
import nba.tool.Code;

@Service
public class GameService extends DatabaseService {

	public static int SAL_RATIO = 1;

	public Team getTeamByUser(User user) throws Exception {
		return this.get(Team.class, "select * from team where user_id=?",
				new Long[] { user.getId() });
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

		if (player.getSal() > team.getTeam_money()) {
			return Code.NOTENOUGHMONEY;
		}

		String players = "";

		if (team.getPlayers() == null || team.getPlayers().equals("")) {
			players = player.getPlayer_id() + "-" + player.getPos();
		} else {

			players = team.getPlayers();

			players = players + "@" + player.getPlayer_id() + "-"
					+ player.getPos();

			String res = checkPos(players);

			if (!res.equals("ok")) {
				return res;
			}
		}

		team.setPlayers(players);
		team.setTeam_money(team.getTeam_money() - player.getSal());

		this.merge(team);

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

		this.merge(team);

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
		/*for (Player player : playerList) {
			if (player.getImg_src() != null && !player.getImg_src().equals("")) {
				String[] srcpaths = player.getImg_src().split("\\\\");
				player.setImg_src(srcpaths[srcpaths.length - 1]);
			}
		}*/
	}

	public static void main(String[] args) {

	}

	public boolean timeTradeAble() {

		SimpleDateFormat sdf = new SimpleDateFormat("HH");
		String hour = sdf.format(new Date());
		if (Integer.parseInt(hour) >= 13) {
			return true;
		}
		return false;
	}

	public List<GameData> GetGamedataByCondition(String condition) {
		return this.gets(GameData.class, "select * from game_data where 1=1 "
				+ condition, null);
	}

	public void EvCal() throws Exception {
		List<Team> teams = this.gets(Team.class);

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

		List<GameData> gamedatas = GetGamedataByCondition(" and game_date = '"
				+ sdf.format(new Date()) + "'");

		Map<String, Integer> player_ev = new HashMap<String, Integer>();
		for (GameData gamedata : gamedatas) {
			player_ev.put(gamedata.getPlayer_id(), gamedata.getEv());
		}

		List<Team> updateTeam = new ArrayList<Team>();

		for (Team team : teams) {
			String player_ids = this.getIdsFromTeam(team);
			if (!player_ids.equals("")) {
				int ev_sum = 0;
				String[] player_id_array = player_ids.split(",");
				for (String player_id : player_id_array) {
					if (player_ev.get(player_id) != null) {
						ev_sum += player_ev.get(player_id);
					}
				}

				team.setEv(team.getEv() + ev_sum);
				updateTeam.add(team);
			}
		}

		if (updateTeam.size() > 0) {
			this.merge(updateTeam);
		}
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

	private String getIdFromUrl(String url) {
		int start = url.indexOf("=") + 1;

		String id = url.substring(start, url.length());

		return id;
	}
}
