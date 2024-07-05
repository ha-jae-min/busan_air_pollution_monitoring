package busan;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;

import javax.swing.JOptionPane;

public class Db {
	Connection con = null;
	String url = "jdbc:oracle:thin:@localhost:1521:XE"; String id = "busan"; String password = "1234";
	private static String id2, pw, eID, v, join;
	private static String region, address;
	private static int dbOk=0, dbOk2=0;
	private static double chomise, mise, ozon, ilsan;
	private static String idM, pwM, nameM, regionM, phoneM;
	private static String idU, pwU, nameU, regionU, numU;
	static Main main = new Main(0);
	static Login log = new Login(0);
	static MeasurePlace mp = new MeasurePlace(0);
	static Information inf = new Information(0);
	static danger dg = new danger(0);
	static Trigger t = new Trigger(0);
	static Member m = new Member(0);
	static UserList u = new UserList(0);
	
	//set�Լ����� �ٸ� Ŭ�������� ������ ����
	public void setId(String id) {
		this.id2 = id;
	}
	public void setPw(String pw) {
		this.pw = pw;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public void setAddress(String address) {
		this.address = address;
	}
	public void setdbOk(int dbOk) {
		this.dbOk = dbOk;
	}
	public void setdbOk2(int dbOk2) {
		this.dbOk2 = dbOk2;
	}
	public void setManage(String eID) {
		this.eID = eID;
	}
	public void setJoinCheck(String join) {
		this.join = join;
	}
	public void setDelete(String v) {
		this.v = v;
	}
	public void setValue(double chomise, double mise, double ozon, double ilsan) {
		this.chomise=chomise;
		this.mise=mise;
		this.ozon=ozon;
		this.ilsan=ilsan;
	}

	public void setMember(String idM, String pwM, String nameM, String regionM, String phoneM) {
		this.idM=idM;
		this.pwM=pwM;
		this.nameM=nameM;
		this.regionM=regionM;
		this.phoneM=phoneM;
	}
	
	public void setUser(String idU, String pwU, String nameU, String regionU, String phoneU) {
		this.idU=idU;
		this.pwU=pwU;
		this.nameU=nameU;
		this.regionU=regionU;
		this.numU=phoneU;
	}
	
	//��κ��� �����ڿ� if���� a�� �� ������ ����Ƚ�Ű�� �ٸ� Ŭ�������� ����ϱ� ���ؼ�
	public Db(int a) {
		if(a==2) {
			try { 
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("����̹� ���� ����");
			} catch (ClassNotFoundException e) { System.out.println("No Driver."); } 
			
			try { 
				con = DriverManager.getConnection(url, id, password);
				System.out.println("DB ���� ����");
			} catch (SQLException e) { System.out.println("Connection Fail"); }
		}
	}

	//���ν��� ȣ���ؼ� ȸ������
	public void joinDb() throws SQLException{
		CallableStatement cstmt = con.prepareCall("{call join_u(?,?,?,?,?)}");
		cstmt.setString(1,  idU);
		cstmt.setString(2,  pwU);
		cstmt.setString(3,  nameU);
		cstmt.setString(4,  regionU);
		cstmt.setString(5,  numU);

		cstmt.execute();
		cstmt.close();
	}
	
	//ȸ�����Կ��� ���̵� �ߺ�Ȯ��
	public void joinCheckDb() throws SQLException { 
		int ok=1;
		String query = "select ȸ��ID from ȸ��";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			while(rs.next()) {
				if(join.equals(rs.getString("ȸ��ID")))
					ok=0;
			}
			log.setJoinOk(ok);
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
		if(ok==0)
			JOptionPane.showMessageDialog(null, "�̹� ��� ���� ���̵��Դϴ�");
		else
			JOptionPane.showMessageDialog(null, "��� �����մϴ�");			
	}
	
	//ȸ�� ���� DB�󿡼� �޾ƿͼ� �α���
	public void loginDb() throws SQLException {
		// ������
		int ok=0;
	    String query = "select ȸ��ID, PW, �̸�, ������ from ȸ��";
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		try {
			while (rs.next()) {
				if(id2.equals(rs.getString("ȸ��ID")) && pw.equals(rs.getString("PW"))) {
					ok=1;
					log.setOk(ok);
					main.setName(rs.getString("�̸�"));
					main.setAddress(rs.getString("������"));
					t.setAddress(rs.getString("������"));
					address=rs.getString("������");
				}		
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
		//1�̸� ����ȭ������ �Ѿ
		if(ok!=1) {JOptionPane.showMessageDialog(null, "�ٽ� �Է����ּ���");}					
	}
	
	//������ ���� DB�󿡼� �޾ƿ�
	public void tableDb() throws SQLException {
		LocalDate now = LocalDate.now();
		int i=0;
		if(dbOk==0) {		//���糯¥��
			String query = "select ����׸�, ������, ������¥, �����ܰ� from ������ "
					+ "where ��������='"+address+"' and ������¥='"+Date.valueOf(now)+"'"
							+ " order by ������¥ asc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				while (rs.next()) {
					String item=rs.getString("����׸�");
					double value=rs.getDouble("������");
					Date Mdate=rs.getDate("������¥");
					String pollution = rs.getString("�����ܰ�");
					Object data[] = {Mdate, item, value, pollution};
					main.setTable(data);
					main.setval(i, value);
					i++;
					if(i==4)
						i=0;
				}
				stmt.close(); rs.close();
			} catch (SQLException e) { e.printStackTrace();}
		}
		else {			//��� ��¥
		    String query = "select ����׸�, ������, ������¥, �����ܰ� from ������ "
		    		+ "where ��������='"+address+"' order by ������¥ asc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				while (rs.next()) {
					String item=rs.getString("����׸�");
					double value=rs.getDouble("������");
					Date Mdate=rs.getDate("������¥");
					String pollution = rs.getString("�����ܰ�");
					Object data[] = {Mdate, item, value, pollution};
					main.setTable(data);
					main.setval(i, value);
					i++;
					if(i==4)
						i=0;
				}
				stmt.close(); rs.close();
			} catch (SQLException e) { e.printStackTrace();}
			dbOk=0;
		}	
	}			
	
	//�޺��ڽ� �� Ŭ���� �ش� ���� ������ ���� DB�󿡼� �޾ƿ�
	public void tableDb2() throws SQLException {
		LocalDate now = LocalDate.now();
		int i=0;
		if(dbOk2==0) {
			String query = "select ����׸�, ������, ������¥, �����ܰ� from ������ "
					+ "where ��������='"+region+"' and ������¥='"+Date.valueOf(now)+"'"
							+ " order by ������¥ asc";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					String item=rs.getString("����׸�");
					double value=rs.getDouble("������");
					Date Mdate=rs.getDate("������¥");
					String pollution = rs.getString("�����ܰ�");
					Object data[] = {Mdate, item, value, pollution};
					main.setTable(data);
					main.setval(i, value);
					i++;
					if(i==4) {i=0;}
				}
				pstmt.close(); rs.close();
			} catch (SQLException e) { e.printStackTrace();}
		}
		else {			//��� ��¥
		    String query = "select ����׸�, ������, ������¥, �����ܰ� from ������ "
		    		+ "where ��������='"+region+"' order by ������¥ asc";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					String item=rs.getString("����׸�");
					double value=rs.getDouble("������");
					Date Mdate=rs.getDate("������¥");
					String pollution = rs.getString("�����ܰ�");
					Object data[] = {Mdate, item, value, pollution};
					main.setTable(data);
					main.setval(i, value);
					i++;
					if(i==4) {i=0;}
				}
				pstmt.close(); rs.close();
			} catch (SQLException e) { e.printStackTrace();}
			dbOk2=0;
		}
	}
	
	//ȸ������ �� �޾ƿ���
	public void memberDb() throws SQLException {
		String query ="select ȸ��ID, PW, �̸�, ������, ��ȭ��ȣ from ȸ�� "
		 		+ "where ȸ��ID='"+id2+"'";
		 PreparedStatement stmt = con.prepareStatement(query);
		 ResultSet rs = stmt.executeQuery(query);
		 try {
	     while (rs.next()) {
	       String id=rs.getString("ȸ��ID");
	       String pw=rs.getString("PW");
	       String name=rs.getString("�̸�");
	       String region=rs.getString("������");
	       String phone=rs.getString("��ȭ��ȣ");
	       m.setStr(id, pw, name, region, phone);
		 }
		 stmt.close(); rs.close();
		 } catch (SQLException e) { e.printStackTrace();}  
	}
	
	//ȸ������ ������ �� Db�� ������Ʈ	
	public void memberUpdateDb() throws SQLException { //ȸ������ ������ �� Db�� ������Ʈ      
	      
	      CallableStatement cstmt = con.prepareCall("{call update_u(?,?,?,?,?,?)}");
	      cstmt.setString(1, id2);
	      cstmt.setString(2, idM);
	      cstmt.setString(3, pwM);
	      cstmt.setString(4, nameM);
	      cstmt.setString(5, regionM);
	      cstmt.setString(6, phoneM);
	      
	      cstmt.execute();
	      cstmt.close();
	      JOptionPane.showMessageDialog(null, "������Ʈ �Ϸ�");
	      main.setName(nameM);
		  main.setAddress(regionM);
	}
	
	//���� ID�� �޾ƿ���
	public void managerDb() throws SQLException { 
		int ok=0;
		String query = "select ����ID from ����";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			while(rs.next()) {
				if(eID.equals(rs.getString("����ID"))) {
					ok=1;
					log.setMangerOk(ok);
				}
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
		if(ok==0)
			JOptionPane.showMessageDialog(null, "������ ID ����ġ");
		else {
			JOptionPane.showMessageDialog(null, "��ġ�մϴ�");
			log.setMangerOk(1);
		}
	}	
	
	//������ ������ �� Db�� ������Ʈ	
	public void triggerDb() throws SQLException { 	
		LocalDate now = LocalDate.now();
		String query1 = "update ������ set ������='"+chomise+"' where ��������='"+address+"' "
					+ "and ������¥='"+Date.valueOf(now)+"' and ����׸�='�ʹ̼�����'";
	
		String query2 = "update ������ set ������='"+mise+"' where ��������='"+address+"' "
					+ "and ������¥='"+Date.valueOf(now)+"' and ����׸�='�̼�����'";
		
		String query3 = "update ������ set ������='"+ozon+"' where ��������='"+address+"' "
					+ "and ������¥='"+Date.valueOf(now)+"' and ����׸�='����'";
		
		String query4 = "update ������ set ������='"+ilsan+"' where ��������='"+address+"' "
					+ "and ������¥='"+Date.valueOf(now)+"' and ����׸�='�ϻ�ȭź��'";
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query1);
		stmt.executeUpdate(query2);
		stmt.executeUpdate(query3);
		stmt.executeUpdate(query4);
		try {		
			stmt.close();		
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	//ȸ�� ���� DB�󿡼� �޾ƿ�
	public void userDb() throws SQLException {
	    String query = "select ȸ��ID, �̸�, ������, ��ȭ��ȣ from ȸ��";
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		try {
			while (rs.next()) {
				String name=rs.getString("�̸�");
				String region=rs.getString("������");
				String phone=rs.getString("��ȭ��ȣ");
				String id=rs.getString("ȸ��ID");
				Object data[] = {name, region, phone, id};
				u.setTable(data);		
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}		
	}
	
	//ȸ�� ���� ����
	public void userDeleteDb() throws SQLException {
	    String query = "delete from ȸ�� where ȸ��ID='"+v+"'";
	    Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
		try {		
			stmt.close();		
		} catch (SQLException e) { e.printStackTrace();}	
	}
	
	//�������� Db�󿡼� �޾ƿ�
	public void dangerDb() throws SQLException { 
		String query = "select ��������, ����׸�, ������¥, �����ܰ� from ������ "
				+ "where �����ܰ� = '�ſ쳪��' or �����ܰ� = '����'  order by ������¥ asc";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			while(rs.next()) {
				
				String region = rs.getString("��������");
				String item = rs.getString("����׸�");
				Date date = rs.getDate("������¥");
				String pol = rs.getString("�����ܰ�");
				Object data[] = {date, item, region, pol};
				dg.setTable(data);
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	//������ ���� DB�󿡼� �޾ƿ�
	public void placeDb() throws SQLException {
	    String query ="select �̸�, ����, ȸ���� from ����";
	    PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		try {
			while (rs.next()) {
				String name=rs.getString("�̸�");
				String region=rs.getString("����");
				int part=rs.getInt("ȸ����");
				Object data[] = {name, region, part};
				mp.setTable(data);
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
	}
	
	//���� ���� DB�󿡼� �޾ƿ�
	public void inforDb() throws SQLException {
		 String query ="select ����.�̸�, ����.����, ����.��ȭ��ȣ, ������.���ּ�, ������.�뵵���� "
		       + "from ����, ������ where ������.����='"+region+"' and ����.����='"+region+"'";
		 PreparedStatement stmt = con.prepareStatement(query);
		 ResultSet rs = stmt.executeQuery(query);
		 try {
         while (rs.next()) {
	         String name=rs.getString("�̸�");
	         String region=rs.getString("����");
	         String part=rs.getString("�뵵����");
	         String number=rs.getString("��ȭ��ȣ");
	         String address=rs.getString("���ּ�");
	         inf.setStr(name, region, part, number, address);
		 }
		 stmt.close(); rs.close();
		 } catch (SQLException e) { e.printStackTrace();}
		      
	}	
}
