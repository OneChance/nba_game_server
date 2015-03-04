package nba.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "trade_log")
public class TradeLog {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long trade_id;

	private String trade_date;
	private Long trade_player_id;
	private String trade_type;
	private Integer trade_money;
	private Long user_id;
	
	public TradeLog(){
		
	}
	
	public TradeLog(String trade_date,Long trade_player_id,String trade_type,Integer trade_money,Long user_id){
		this.trade_date = trade_date;
		this.trade_player_id = trade_player_id;
		this.trade_type = trade_type;
		this.trade_money = trade_money;
		this.user_id = user_id;
	}

	public Long getUser_id() {
		return user_id;
	}
	public void setUser_id(Long user_id) {
		this.user_id = user_id;
	}
	public Long getTrade_id() {
		return trade_id;
	}
	public void setTrade_id(Long trade_id) {
		this.trade_id = trade_id;
	}
	public String getTrade_date() {
		return trade_date;
	}
	public void setTrade_date(String trade_date) {
		this.trade_date = trade_date;
	}
	public String getTrade_type() {
		return trade_type;
	}
	public void setTrade_type(String trade_type) {
		this.trade_type = trade_type;
	}
	public Long getTrade_player_id() {
		return trade_player_id;
	}
	public void setTrade_player_id(Long trade_player_id) {
		this.trade_player_id = trade_player_id;
	}

	public Integer getTrade_money() {
		return trade_money;
	}

	public void setTrade_money(Integer trade_money) {
		this.trade_money = trade_money;
	}
	
	
}
