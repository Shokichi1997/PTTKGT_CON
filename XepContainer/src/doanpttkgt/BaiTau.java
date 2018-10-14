package doanpttkgt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;

public class BaiTau {
	ArrayList<Integer> danhSach=null;
	public BaiTau() {
		this.danhSach=new ArrayList<Integer>();
	}
	
	public void themContainer(){
		BufferedReader brRead = null;

        try {   
        	brRead = new BufferedReader(new FileReader("C:\\Users\\C01\\Downloads\\test.txt"));       

            System.out.println("�?�?c nội dung file sử dụng phương thức read()");

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
	/*public void inFile(){
		BufferedWriter bw = null;
		FileWriter fw = null;
		try { 
			
			fw = new FileWriter("C:\\Users\\C01\\Downloads\\testResult.txt");
			bw = new BufferedWriter(fw);
			bw.write();
 
			System.out.println("Xong");
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			try {
				if (bw != null)
					bw.close();
				if (fw != null)
					fw.close();
			} catch (Exception ex) {
				ex.printStackTrace();
			}
		}
	}
	}*/
	
}
