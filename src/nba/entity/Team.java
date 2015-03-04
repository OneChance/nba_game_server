package nba.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "team")
public class Team {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String team_name;
	private String players;
	private Long user_id;
	private Integer team_money;
	private Integer ev;

	@OneToOne(optional = true, cascade = CascadeType.ALL, mappedBy = "team")
	private Arena arena;

	@Transient
	private String fans_change_state;

	public Arena getArena() {
		return arena;
	}

	public void setArena(Arena arena) {
		this.arena = arena;
	}

	public String getFans_change_state() {
		return fans_change_state;
	}

	public void setFans_change_state(String fans_change_state) {
		this.fans_change_state = fans_change_state;
	}

	public Integer getTeam_money() {
		return team_money;
	}

	public void setTeam_money(Integer team_money) {
		if (team_money == null)
			team_money = 0;
		this.team_money = team_money;
	}

	public Integer getEv() {
		return ev;
	}

	public void setEv(Integer ev) {
		if (ev == null || ev < 0)
			ev = 0;
		this.ev = ev;
	}

	public Team() {

	}

	public Team(Long user_id, String team_name) {
		this.user_id = user_id;
		this.team_name = team_name;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getTeam_name() {
		return team_name;
	}

	public void setTeam_name(String team_name) {
		this.team_name = team_name;
	}

	public String getPlayers() {
		return players;
	}

	public void setPlayers(String players) {
		this.players = players;
	}

	public Long getUser_id() {
		return user_id;
	}

	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}

}
