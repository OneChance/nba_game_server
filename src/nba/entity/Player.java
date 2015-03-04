package nba.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Transient;

@Entity
@Table(name = "player_data")
public class Player {

	@Id
	private Long player_id;

	private String player_name;
	private String pos;// 位置
	private int sal;// 当前工资

	private String img_src;

	@Transient
	private int sal_cur; // 签约工资
	@Transient
	private String shoot;
	@Transient
	private String free_throw;
	@Transient
	private String rebound;
	@Transient
	private String assist;
	@Transient
	private String steal;
	@Transient
	private String block;
	@Transient
	private String fault;
	@Transient
	private String foul;
	@Transient
	private String point;
	@Transient
	private Integer ev;

	public int getSal_cur() {
		return sal_cur;
	}

	public void setSal_cur(int sal_cur) {
		this.sal_cur = sal_cur;
	}

	public Integer getEv() {
		return ev;
	}

	public void setEv(Integer ev) {
		if (ev == null)
			ev = 0;
		this.ev = ev;
	}

	public String getImg_src() {
		return img_src;
	}

	public void setImg_src(String img_src) {
		this.img_src = img_src;
	}

	public Long getPlayer_id() {
		return player_id;
	}

	public void setPlayer_id(Long player_id) {
		this.player_id = player_id;
	}

	public String getPlayer_name() {
		return player_name;
	}

	public void setPlayer_name(String player_name) {
		this.player_name = player_name;
	}

	public String getPos() {
		return pos;
	}

	public void setPos(String pos) {
		this.pos = pos;
	}

	public int getSal() {
		return sal;
	}

	public void setSal(int sal) {
		if (sal < 500)
			sal = 500;
		this.sal = sal;
	}

	public String getShoot() {
		return shoot;
	}

	public void setShoot(String shoot) {
		this.shoot = shoot;
	}

	public String getFree_throw() {
		return free_throw;
	}

	public void setFree_throw(String free_throw) {
		this.free_throw = free_throw;
	}

	public String getRebound() {
		return rebound;
	}

	public void setRebound(String rebound) {
		this.rebound = rebound;
	}

	public String getAssist() {
		return assist;
	}

	public void setAssist(String assist) {
		this.assist = assist;
	}

	public String getSteal() {
		return steal;
	}

	public void setSteal(String steal) {
		this.steal = steal;
	}

	public String getBlock() {
		return block;
	}

	public void setBlock(String block) {
		this.block = block;
	}

	public String getFault() {
		return fault;
	}

	public void setFault(String fault) {
		this.fault = fault;
	}

	public String getFoul() {
		return foul;
	}

	public void setFoul(String foul) {
		this.foul = foul;
	}

	public String getPoint() {
		return point;
	}

	public void setPoint(String point) {
		this.point = point;
	}

}
