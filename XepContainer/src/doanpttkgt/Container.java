package doanpttkgt;

public class Container {
	private int soContainer;
	private int column;
	private int row;
	
	public Container(int soContainer, int column, int row) {
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

	public int getSoContainer() {
		return soContainer;
	}

	public void setSoContainer(int soContainer) {
		this.soContainer = soContainer;
	}

}
