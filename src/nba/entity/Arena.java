package nba.entity;

import javax.persistence.Entity;
import javax.persistence.GenerationType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "arena")
public class Arena {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String arena_name;
	private Integer cap;
	private Double attendance;
	private String arena_img;

	@OneToOne(optional = false)
	@JoinColumn(name = "team_id")
	private Team team;

	private Integer today_in;
	
	@Transient
	private Integer ticket_price;

	public Integer getTicket_price() {
		return ticket_price;
	}

	public void setTicket_price(Integer ticket_price) {
		this.ticket_price = ticket_price;
	}

	public Team getTeam() {
		return team;
	}

	public void setTeam(Team team) {
		this.team = team;
	}

	public Integer getToday_in() {
		if (today_in == null)
			today_in = 0;
		return today_in;
	}

	public void setToday_in(Integer today_in) {
		this.today_in = today_in;
	}

	// 球馆设施级别(提升上座率)
	private Integer eq_level;
	// 球馆容量级别(提升容量)
	private Integer cap_level;

	public Integer getCap_level() {
		return cap_level;
	}

	public void setCap_level(Integer cap_level) {

		this.arena_img = cap_level + ".jpg";
		this.cap = cap_level * cap_level * 500;

		this.cap_level = cap_level;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getArena_name() {
		return arena_name;
	}

	public void setArena_name(String arena_name) {
		this.arena_name = arena_name;
	}

	public Integer getCap() {
		return cap;
	}

	public void setCap(Integer cap) {
		this.cap = cap;
	}

	public Double getAttendance() {
		return attendance;
	}

	public void setAttendance(Double attendance) {
		this.attendance = attendance;
	}

	public Integer getEq_level() {
		return eq_level;
	}

	public void setEq_level(Integer eq_level) {
		this.attendance = eq_level * 0.1;
		this.eq_level = eq_level;
	}

	public String getArena_img() {
		return arena_img;
	}

	public void setArena_img(String arena_img) {
		this.arena_img = arena_img;
	}

}
