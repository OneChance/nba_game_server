package nba.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "day_in_log")
public class DayInLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long day_in_id;

	private Integer ev_change;
	private Integer fans_change;
	private Integer arena_in;
	private Integer pay;
	private Integer profit;
	private String day_in_date;
	private Long team_id;

	public DayInLog() {

	}

	public DayInLog(Integer ev_change, Integer fans_change, Integer arena_in,
			Integer pay, Integer profit, String day_in_date, Long team_id) {
		this.ev_change = ev_change;
		this.fans_change = fans_change;
		this.arena_in = arena_in;
		this.pay = pay;
		this.profit = profit;
		this.day_in_date = day_in_date;
		this.team_id = team_id;
	}

	public Long getTeam_id() {
		return team_id;
	}

	public void setTeam_id(Long team_id) {
		this.team_id = team_id;
	}

	public Long getDay_in_id() {
		return day_in_id;
	}

	public void setDay_in_id(Long day_in_id) {
		this.day_in_id = day_in_id;
	}

	public Integer getEv_change() {
		return ev_change;
	}

	public void setEv_change(Integer ev_change) {
		this.ev_change = ev_change;
	}

	public Integer getFans_change() {
		return fans_change;
	}

	public void setFans_change(Integer fans_change) {
		this.fans_change = fans_change;
	}

	public Integer getArena_in() {
		if (arena_in == null) {
			arena_in = 0;
		}
		return arena_in;
	}

	public void setArena_in(Integer arena_in) {
		this.arena_in = arena_in;
	}

	public Integer getPay() {
		if (pay == null) {
			pay = 0;
		}
		return pay;
	}

	public void setPay(Integer pay) {
		this.pay = pay;
	}

	public Integer getProfit() {
		if (profit == null) {
			profit = 0;
		}
		return profit;
	}

	public void setProfit(Integer profit) {
		this.profit = profit;
	}

	public String getDay_in_date() {
		return day_in_date;
	}

	public void setDay_in_date(String day_in_date) {
		this.day_in_date = day_in_date;
	}
}
