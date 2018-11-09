package doanpttkgt;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

public class Main {
	private static BaiChinh baiChinh;
	private static BaiTam baiTam;
	private static BaiTau baiTau;

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

				line = br.readLine();
			}
			daoNguocDuLieu();
			br.close();
			fr.close();
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
				Container con = baiTau.getBaiTau().get(i).getColumn().pop();// lay object container
				int soContainer = con.getSoContainer();// lay gia tri cua container

				while (soContainer == -1 && !baiTau.getBaiTau().get(i).getColumn().isEmpty()) {
					if (!baiTau.getBaiTau().get(i).getColumn().isEmpty()) {
						// neu la x thi lay container o hang tiep theo cua cot
						con = baiTau.getBaiTau().get(i).getColumn().pop();
						soContainer = con.getSoContainer();
					}
				}
				if (!(soContainer == -1)) {
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
	

	public static void main(String[] args) {
		baiChinh = new BaiChinh();
		baiTam = new BaiTam();
		baiTau = new BaiTau();

		try {
			docFile("test.txt");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (int i = 0; i < baiTau.getHeight(); i++) {
			List<Container> list = sapXepListCon();
			if (list.size()!=0) {
				System.out.print("\nso lon nhat dong :" + i);
				for (Container container : list) {
					System.out.print(container.getSoContainer() + " ");
				}
			}
		}
	}
}
