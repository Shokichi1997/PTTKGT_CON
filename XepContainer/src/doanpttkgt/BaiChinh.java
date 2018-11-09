package doanpttkgt;

import java.util.ArrayList;
import java.util.List;

public class BaiChinh {
	private List<Column> baiChinh = null;
	private int height;
	private int columns;
	
	public BaiChinh() {
		baiChinh = new ArrayList<Column>();
	}

	public void setHeight(int height) {
		this.height = height;
		
	}
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public List<Column> getBaiChinh() {
		return baiChinh;
	}

	public int getHeight() {
		return height;
	}

	
}
