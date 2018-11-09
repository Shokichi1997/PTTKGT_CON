package doanpttkgt;

public class Container {
	private String soContainer;
	private int column;
	private int row;
	
	public Container(String soContainer, int column, int row) {
		this.soContainer = soContainer;
		this.column = column;
		this.row = row;
	}

	public int getColums() {
		return column;
	}

	public void setColums(int column) {
		this.column = column;
	}

	public int getRow() {
		return row;
	}

	public void setRow(int row) {
		this.row = row;
	}

	public String getSoContainer() {
		return soContainer;
	}

	public void setSoContainer(String soContainer) {
		this.soContainer = soContainer;
	}

}
