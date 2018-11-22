package doanpttkgt;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

public class BaiTau {
	private List<Column> baiTau=null;
	private int height;
	private int columns;
	private int containers;
	
	
	public BaiTau() {
		this.baiTau = new ArrayList<Column>();
	}
	
	public void setHeight(int height) {
		this.height = height;
	}
	
	public void setColumns(int columns) {
		this.columns = columns;
	}
	
	public void setContainers(int containers) {
		this.containers = containers;
	}
	
	public void themContainer(){
		BufferedReader brRead = null;

        try {   
        	brRead = new BufferedReader(new FileReader("test.txt"));       
            String num;
            String kitu[];

            while( (num = brRead.readLine()) != null)
            {   
            	kitu = num.split("\t");
            	for(int i=0;i<kitu.length;i++)
            	{
            		System.out.print(kitu[i]+"\t");
            	}
            	System.out.println();
                
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
            	brRead.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
	}

	public int getHeight() {
		return this.height;
	}

	public int getColumns() {
		return this.columns;
	}
	
	public List<Column> getBaiTau() {
		return baiTau;
	}

	public int getContainers() {
		return containers;
	}
}
