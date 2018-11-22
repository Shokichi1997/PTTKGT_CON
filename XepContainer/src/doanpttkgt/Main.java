package doanpttkgt;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;
import java.util.concurrent.TimeUnit;

import javax.swing.plaf.SliderUI;

public class Main {
	private static BaiChinh baiChinh;
	private static BaiTam baiTam;
	private static BaiTau baiTau;
	static List<String> step;
	static String time;
	static StringBuffer sbRead = new StringBuffer();
	
	public static void docFile(String fileName) throws IOException {
		try {
			FileReader fr = new FileReader(fileName);
			BufferedReader br = new BufferedReader(fr);

			String line = br.readLine();
			
			int flag = -1;
			while (line != null) {
				// System.out.println("flag = "+flag);
				// Xử lý dữ liệu đọc vào
				if (line.contains("ship")) {
					flag = 1; // 1 : bãi tàu
				}
				if (line.contains("yard")) {
					flag = 3; // 3: bãi chính
				}
				if (line.contains("containers") && flag == 3) {
					flag = 0; // 0: container
				}

				if (flag == 1) {
					// System.out.println("Setup bãi tàu");
					if (line.contains("height")) {
						String s[] = line.split("//");
						baiTau.setHeight(Integer.parseInt(s[0]));
					}
					if (line.contains("columns")) {
						String s[] = line.split("//");
						baiTau.setColumns(Integer.parseInt(s[0]));
					}
					if (line.contains("containers")) {
						String s[] = line.split("//");
						baiTau.setContainers(Integer.parseInt(s[0]));
					}
				}
				if (flag == 3) {
					// System.out.println("Setup bãi chính");
					if (line.contains("height")) {
						String s[] = line.split("//");
						baiChinh.setHeight(Integer.parseInt(s[0]));
						System.out.println(" Chiều cao của bãi chính là :" + baiChinh.getHeight());
					}
				}

				if (flag == 0) {
					System.out.println("Đọc container");
					// Khởi tạo bãi tàu
					for (int i = 0; i < baiTau.getColumns(); i++) {
						baiTau.getBaiTau().add(new Column());
					}
					// Tiến hành đọc vào dữ liệu container
					br.readLine(); // Đọc dòng kế tiếp (dòng có chỉ số của cột)
					while (line != null) {
						line = br.readLine();

						String dong[] = null;
						if (line != null && !line.equals("")) {
							dong = line.split("\t");

							// Bỏ qua cột 0 vì nó là chỉ số cột
							int row = 0;
							for (int j = 1; j < dong.length; j++) {
								String soContainers = dong[j];
								int soContainer;
								if (soContainers.equals("x")) {
									soContainer = -1;
								} else {
									soContainer = Integer.parseInt(dong[j]);
								}
								int column = j;
								++row;
								baiTau.getBaiTau().get(j - 1).getColumn().push(new Container(soContainer, column, row));
							}
						}

					}
				}
				if(line!=null) sbRead.append(line+"\n");
				line = br.readLine();
			}
			daoNguocDuLieu();
			br.close();
			fr.close();

			if (baiChinh.getColumn() == 0) {
				int sodu = baiTau.getContainers() % baiChinh.getHeight();
				int numberColumn = baiTau.getContainers() / baiChinh.getHeight();
				if (sodu != 0) {
					numberColumn++;
				}
				baiChinh.setColumns(numberColumn);
			}
			for (int i = 0; i < baiChinh.getColumn(); i++) {
				Column column = new Column();
				baiChinh.getBaiChinh().add(column);
			}

		} catch (FileNotFoundException e) {
			System.out.println("Không tìm thấy file " + fileName);
			System.exit(0);
		}

	}

	public static void daoNguocDuLieu() {
		for (int i = 0; i < baiTau.getColumns(); i++) {
			List<Container> listCon = new ArrayList<>();
			for (int j = 0; j < baiTau.getHeight(); j++) {
				Container con = baiTau.getBaiTau().get(i).getColumn().pop();
				listCon.add(con);
				con.setRow(j);
				con.setColums(i);

			}

			// Đổ lại
			for (int k = 0; k < baiTau.getHeight(); k++) {
				baiTau.getBaiTau().get(i).getColumn().push(listCon.get(k));
			}

		}
	}

	public static List<Container> layContainerBC() {
		baiTau.getHeight();
		List<Container> listSS = new ArrayList<>();

		for (int i = 0; i < baiTau.getColumns(); i++) {
			if (!baiTau.getBaiTau().get(i).getColumn().isEmpty()) {
				Container con = baiTau.getBaiTau().get(i).getColumn().peek();// lay object container
				int soContainer = con.getSoContainer();// lay gia tri cua container
				if (soContainer == -1) {
					// Neu container la x thi bo no ra khoi bai
					baiTau.getBaiTau().get(i).getColumn().pop();
				}
				while (soContainer == -1 && !baiTau.getBaiTau().get(i).getColumn().isEmpty()) {
					if (!baiTau.getBaiTau().get(i).getColumn().isEmpty()) {
						// neu la x thi lay container o hang tiep theo cua cot
						con = baiTau.getBaiTau().get(i).getColumn().peek();
						soContainer = con.getSoContainer();
						if (soContainer == -1) {
							// Neu container la x thi bo no ra khoi bai
							baiTau.getBaiTau().get(i).getColumn().pop();
						}
					}
				}
				if (soContainer != -1) {
					listSS.add(con);
				}
			}
		}
		return listSS;
	}

	public static List<Container> sapXepListCon() {
		List<Container> list = layContainerBC();
		for (int i = 0; i < list.size() - 1; i++) {
			for (int j = i + 1; j < list.size(); j++) {
				if (list.get(i).getSoContainer() < list.get(j).getSoContainer()) {
					Container temp = list.get(i);
					list.set(i, list.get(j));
					list.set(j, temp);
				}
			}
		}
		return list;
	}

	public static void bocContainer() {
		step.add("//Schedule of Moving");
		List<Column> baiConChinh = baiChinh.getBaiChinh();
		int heightYard = baiChinh.getHeight();
		List<Column> baiConTau = baiTau.getBaiTau();
		int time = 0;

		int soConDu = 0;
		int k = 20;
		//Khi tren bai tau con container thi lam viec
		while (baiTau.getContainers() > 0) {
			List<Container> listCon = sapXepListCon();
			//Duyet day container phia tren cung cua bai tau
			System.out.print("\nDanh sách list con: ");
			for (Container container : listCon) {
				System.out.print(container.getSoContainer() + "  ");
			}

			int soLanLap = listCon.size() / heightYard;
			// So container du de sap xep vua chieu cao bai chinh
			int soConVuaDu = listCon.size() - listCon.size() % heightYard;
			//So con du la so container con lai khong du de xep vua hang bai chinh
			soConDu = listCon.size() % heightYard;
			
			
			int chiSoCotBaiChinh = 0;
			//Neu so container vua du van con
			while (soConVuaDu > 0) {
				//j: chi so cua tung cot container (column)
				int j = 0;
				while (j < heightYard) {
					System.out.println("chiso="+chiSoCotBaiChinh);
					Stack<Container> columnOfYard = baiConChinh.get(chiSoCotBaiChinh).getColumn();
					//Neu cot hien tai co kich thuoc nho hon chieu cao bai chinh
					if ((columnOfYard.size() < heightYard) && listCon.size()>0) {
						//TH1: khi cot co so container nho hon chieu cao cua bai chinh - 1 thi them container co
						//chi so lon nhat vao
						if (columnOfYard.size() < heightYard - 1) {
							System.out.println(listCon.size());
							Container con = listCon.get(0);
							int x = con.getRow();
							int y = con.getColums();
							baiConTau.get(y).getColumn().pop();
							step.add("Time "+ (++time)+": "+con.getSoContainer()+"\t->\t"+(chiSoCotBaiChinh+1));
							baiTau.setContainers(baiTau.getContainers() - 1);
							System.out.println("Thêm "+con.getSoContainer());
							baiConChinh.get(chiSoCotBaiChinh).getColumn().add(con);
							listCon.remove(0);
							soConVuaDu--;
						} else { 
							//TH2: khi cot da co 3 container thi xep container co so nho nhat vao 
							Container con = listCon.get(listCon.size() - 1);
							int x = con.getRow();
							int y = con.getColums();
							baiConTau.get(y).getColumn().pop();
							step.add("Time "+ (++time)+": "+con.getSoContainer()+"\t->\t"+(chiSoCotBaiChinh+1));
							baiTau.setContainers(baiTau.getContainers() - 1);
							System.out.println("Thêm "+con.getSoContainer());
							baiConChinh.get(chiSoCotBaiChinh).getColumn().add(con);
							listCon.remove(listCon.size() - 1);
							soConVuaDu--;
						}

					} else {
						chiSoCotBaiChinh++;
						break;
					}
					j++;

				}

				// }
				soLanLap--;

			}

			if (soConVuaDu == 0 && soConDu > 0) {
				// Xu ly so container con lai (neu con))
				List<Container> listConDu = sapXepListCon();
				for (int i = 0; i < baiConChinh.size(); i++) {
					while ((baiConChinh.get(i).getColumn().size() < 4) && listConDu.size() > 0) {
						Container con = listConDu.get(0);
						if(baiConChinh.get(i).getColumn().size()>0) {
							if (baiConChinh.get(i).getColumn().peek().getSoContainer() < con.getSoContainer()) {
								Column newColumn = new Column();
								baiChinh.setColumns(baiChinh.getColumn()+1);
								baiConChinh.add(newColumn);
								break;
							}
						}
						
						int x = con.getRow();
						int y = con.getColums();
						System.out.println("số con = " + con.getSoContainer());
						System.out.println("Số cột = " + y);
						baiConTau.get(y).getColumn().pop();
						step.add("Time "+ (++time)+": "+con.getSoContainer()+"\t->\t"+(i+1));
						baiTau.setContainers(baiTau.getContainers() - 1);
						System.out.println("So container: "+baiTau.getContainers());
						baiConChinh.get(i).getColumn().add(con);
						listConDu.remove(0);
					}
				}
			}

		}

	}

	public static void main(String[] args) {
		long startTime = System.currentTimeMillis();


		
		baiChinh = new BaiChinh();
		baiTam = new BaiTam();
		baiTau = new BaiTau();
		step = new ArrayList<>();
		
		String fileName = "test";
		try {
			docFile(fileName+".txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}

		bocContainer();

		long endTime = System.currentTimeMillis();
		long temp = endTime - startTime;
		DecimalFormat num = new DecimalFormat("#.###");
		time = num.format(temp/1000.0);
		System.out.println("That took " + time + " milliseconds");
		
		ghiFile(fileName+"HAsolved.txt");

	}

	private static void ghiFile(String fileName) {
		BufferedWriter br = null;
		FileWriter fr = null;
		try {
			fr = new FileWriter(fileName);
			br = new BufferedWriter(fr);
			
			//Ghi file
			br.write(sbRead.toString());
			
			//
			br.write("\n//OUT PUT\n");
			br.write(time+"//Total Cost of moving\n");
			br.write("0//Cost of moving to Temporary Yard\n");
			br.write("//Containers Position in Main Yard\n");
			for(int i=0;i<=baiChinh.getColumn();i++) {
				if(i==0) {
					br.write("\t");
				}
				else {
					br.write("c"+i+"\t");
				}
			}
			
			//Lấp đầy để in ra
			for(int i = 0;i<baiChinh.getColumn();i++) {
				Stack<Container> curentColumn = baiChinh.getBaiChinh().get(i).getColumn();
				if(curentColumn.size()<baiChinh.getHeight()) {
					while(curentColumn.size()<baiChinh.getHeight()) {
						curentColumn.push(new Container(-1, i, 0));
					}
				}
			}
			
			for(int k = 0;k<baiChinh.getHeight();k++) {
				br.write("\nr"+(k+1)+"\t");
				for(int i = 0; i < baiChinh.getColumn();i++) {
					Stack<Container> column = null;
					column = baiChinh.getBaiChinh().get(i).getColumn();
					if (!column.isEmpty()) {
						Container con = column.pop();
						if(con.getSoContainer()==-1) {
							br.write(" "+"\t");
						}
						else
							br.write(con.getSoContainer()+"\t");
					}
				}
				br.write("\n");
			}
			
			br.write("\n//Containers Temporary Yard\n");
			
			for(int i = 0;i<step.size();i++) {
				br.write("\n"+step.get(i));
			}
			
			br.close();
			fr.close();
			
			
		}catch (Exception e) {
			e.printStackTrace();
		}
	}
}
