package nba.chart;

import java.util.ArrayList;
import java.util.List;

public class ChartData {
	private String xScale;
	private String yScale;
	private String type;
	private List<Comp> comp;
	private List<Path> main;
	
	public ChartData(){
		this.setxScale("ordinal");
		this.setyScale("linear");
		this.setType("line-dotted");
		this.comp = new ArrayList<Comp>();
	}
	
	public String getyScale() {
		return yScale;
	}


	public void setyScale(String yScale) {
		this.yScale = yScale;
	}


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getxScale() {
		return xScale;
	}

	public void setxScale(String xScale) {
		this.xScale = xScale;
	}

	public List<Path> getMain() {
		return main;
	}

	public void setMain(List<Path> main) {
		this.main = main;
	}

	public List<Comp> getComp() {
		return comp;
	}

	public void setComp(List<Comp> comp) {
		this.comp = comp;
	}

	
}
