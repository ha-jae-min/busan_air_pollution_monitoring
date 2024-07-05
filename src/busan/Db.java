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
	
	//set함수들은 다른 클래스에서 정보를 전달
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
	
	//대부분의 생성자에 if문과 a를 쓴 이유는 실행안시키고 다른 클래스에서 사용하기 위해서
	public Db(int a) {
		if(a==2) {
			try { 
				Class.forName("oracle.jdbc.driver.OracleDriver");
				System.out.println("드라이버 적재 성공");
			} catch (ClassNotFoundException e) { System.out.println("No Driver."); } 
			
			try { 
				con = DriverManager.getConnection(url, id, password);
				System.out.println("DB 연결 성공");
			} catch (SQLException e) { System.out.println("Connection Fail"); }
		}
	}

	//프로시저 호출해서 회원가입
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
	
	//회원가입에서 아이디 중복확인
	public void joinCheckDb() throws SQLException { 
		int ok=1;
		String query = "select 회원ID from 회원";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			while(rs.next()) {
				if(join.equals(rs.getString("회원ID")))
					ok=0;
			}
			log.setJoinOk(ok);
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
		if(ok==0)
			JOptionPane.showMessageDialog(null, "이미 사용 중인 아이디입니다");
		else
			JOptionPane.showMessageDialog(null, "사용 가능합니다");			
	}
	
	//회원 정보 DB상에서 받아와서 로그인
	public void loginDb() throws SQLException {
		// 데이터
		int ok=0;
	    String query = "select 회원ID, PW, 이름, 거주지 from 회원";
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		try {
			while (rs.next()) {
				if(id2.equals(rs.getString("회원ID")) && pw.equals(rs.getString("PW"))) {
					ok=1;
					log.setOk(ok);
					main.setName(rs.getString("이름"));
					main.setAddress(rs.getString("거주지"));
					t.setAddress(rs.getString("거주지"));
					address=rs.getString("거주지");
				}		
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
		//1이면 메인화면으로 넘어감
		if(ok!=1) {JOptionPane.showMessageDialog(null, "다시 입력해주세요");}					
	}
	
	//대기상태 정보 DB상에서 받아옴
	public void tableDb() throws SQLException {
		LocalDate now = LocalDate.now();
		int i=0;
		if(dbOk==0) {		//현재날짜만
			String query = "select 대기항목, 측정값, 측정날짜, 오염단계 from 대기상태 "
					+ "where 측정지역='"+address+"' and 측정날짜='"+Date.valueOf(now)+"'"
							+ " order by 측정날짜 asc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				while (rs.next()) {
					String item=rs.getString("대기항목");
					double value=rs.getDouble("측정값");
					Date Mdate=rs.getDate("측정날짜");
					String pollution = rs.getString("오염단계");
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
		else {			//모든 날짜
		    String query = "select 대기항목, 측정값, 측정날짜, 오염단계 from 대기상태 "
		    		+ "where 측정지역='"+address+"' order by 측정날짜 asc";
			Statement stmt = con.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			try {
				while (rs.next()) {
					String item=rs.getString("대기항목");
					double value=rs.getDouble("측정값");
					Date Mdate=rs.getDate("측정날짜");
					String pollution = rs.getString("오염단계");
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
	
	//콤보박스 동 클릭시 해당 지역 대기상태 정보 DB상에서 받아옴
	public void tableDb2() throws SQLException {
		LocalDate now = LocalDate.now();
		int i=0;
		if(dbOk2==0) {
			String query = "select 대기항목, 측정값, 측정날짜, 오염단계 from 대기상태 "
					+ "where 측정지역='"+region+"' and 측정날짜='"+Date.valueOf(now)+"'"
							+ " order by 측정날짜 asc";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					String item=rs.getString("대기항목");
					double value=rs.getDouble("측정값");
					Date Mdate=rs.getDate("측정날짜");
					String pollution = rs.getString("오염단계");
					Object data[] = {Mdate, item, value, pollution};
					main.setTable(data);
					main.setval(i, value);
					i++;
					if(i==4) {i=0;}
				}
				pstmt.close(); rs.close();
			} catch (SQLException e) { e.printStackTrace();}
		}
		else {			//모든 날짜
		    String query = "select 대기항목, 측정값, 측정날짜, 오염단계 from 대기상태 "
		    		+ "where 측정지역='"+region+"' order by 측정날짜 asc";
			PreparedStatement pstmt = con.prepareStatement(query);
			ResultSet rs = pstmt.executeQuery();
			try {
				while (rs.next()) {
					String item=rs.getString("대기항목");
					double value=rs.getDouble("측정값");
					Date Mdate=rs.getDate("측정날짜");
					String pollution = rs.getString("오염단계");
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
	
	//회원정보 값 받아오기
	public void memberDb() throws SQLException {
		String query ="select 회원ID, PW, 이름, 거주지, 전화번호 from 회원 "
		 		+ "where 회원ID='"+id2+"'";
		 PreparedStatement stmt = con.prepareStatement(query);
		 ResultSet rs = stmt.executeQuery(query);
		 try {
	     while (rs.next()) {
	       String id=rs.getString("회원ID");
	       String pw=rs.getString("PW");
	       String name=rs.getString("이름");
	       String region=rs.getString("거주지");
	       String phone=rs.getString("전화번호");
	       m.setStr(id, pw, name, region, phone);
		 }
		 stmt.close(); rs.close();
		 } catch (SQLException e) { e.printStackTrace();}  
	}
	
	//회원정보 수정한 거 Db에 업데이트	
	public void memberUpdateDb() throws SQLException { //회원정보 수정한 거 Db에 업데이트      
	      
	      CallableStatement cstmt = con.prepareCall("{call update_u(?,?,?,?,?,?)}");
	      cstmt.setString(1, id2);
	      cstmt.setString(2, idM);
	      cstmt.setString(3, pwM);
	      cstmt.setString(4, nameM);
	      cstmt.setString(5, regionM);
	      cstmt.setString(6, phoneM);
	      
	      cstmt.execute();
	      cstmt.close();
	      JOptionPane.showMessageDialog(null, "업데이트 완료");
	      main.setName(nameM);
		  main.setAddress(regionM);
	}
	
	//직원 ID값 받아오기
	public void managerDb() throws SQLException { 
		int ok=0;
		String query = "select 직원ID from 직원";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			while(rs.next()) {
				if(eID.equals(rs.getString("직원ID"))) {
					ok=1;
					log.setMangerOk(ok);
				}
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
		if(ok==0)
			JOptionPane.showMessageDialog(null, "관리자 ID 불일치");
		else {
			JOptionPane.showMessageDialog(null, "일치합니다");
			log.setMangerOk(1);
		}
	}	
	
	//측정값 수정한 거 Db에 업데이트	
	public void triggerDb() throws SQLException { 	
		LocalDate now = LocalDate.now();
		String query1 = "update 대기상태 set 측정값='"+chomise+"' where 측정지역='"+address+"' "
					+ "and 측정날짜='"+Date.valueOf(now)+"' and 대기항목='초미세먼지'";
	
		String query2 = "update 대기상태 set 측정값='"+mise+"' where 측정지역='"+address+"' "
					+ "and 측정날짜='"+Date.valueOf(now)+"' and 대기항목='미세먼지'";
		
		String query3 = "update 대기상태 set 측정값='"+ozon+"' where 측정지역='"+address+"' "
					+ "and 측정날짜='"+Date.valueOf(now)+"' and 대기항목='오존'";
		
		String query4 = "update 대기상태 set 측정값='"+ilsan+"' where 측정지역='"+address+"' "
					+ "and 측정날짜='"+Date.valueOf(now)+"' and 대기항목='일산화탄소'";
		
		Statement stmt = con.createStatement();
		stmt.executeUpdate(query1);
		stmt.executeUpdate(query2);
		stmt.executeUpdate(query3);
		stmt.executeUpdate(query4);
		try {		
			stmt.close();		
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	//회원 정보 DB상에서 받아옴
	public void userDb() throws SQLException {
	    String query = "select 회원ID, 이름, 거주지, 전화번호 from 회원";
		PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		try {
			while (rs.next()) {
				String name=rs.getString("이름");
				String region=rs.getString("거주지");
				String phone=rs.getString("전화번호");
				String id=rs.getString("회원ID");
				Object data[] = {name, region, phone, id};
				u.setTable(data);		
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}		
	}
	
	//회원 정보 삭제
	public void userDeleteDb() throws SQLException {
	    String query = "delete from 회원 where 회원ID='"+v+"'";
	    Statement stmt = con.createStatement();
		stmt.executeUpdate(query);
		try {		
			stmt.close();		
		} catch (SQLException e) { e.printStackTrace();}	
	}
	
	//위험지역 Db상에서 받아옴
	public void dangerDb() throws SQLException { 
		String query = "select 측정지역, 대기항목, 측정날짜, 오염단계 from 대기상태 "
				+ "where 오염단계 = '매우나쁨' or 오염단계 = '나쁨'  order by 측정날짜 asc";
		Statement stmt = con.createStatement();
		ResultSet rs = stmt.executeQuery(query);
		try {
			while(rs.next()) {
				
				String region = rs.getString("측정지역");
				String item = rs.getString("대기항목");
				Date date = rs.getDate("측정날짜");
				String pol = rs.getString("오염단계");
				Object data[] = {date, item, region, pol};
				dg.setTable(data);
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
	}
	
	//측정소 정보 DB상에서 받아옴
	public void placeDb() throws SQLException {
	    String query ="select 이름, 지역, 회원수 from 직원";
	    PreparedStatement stmt = con.prepareStatement(query);
		ResultSet rs = stmt.executeQuery(query);
		try {
			while (rs.next()) {
				String name=rs.getString("이름");
				String region=rs.getString("지역");
				int part=rs.getInt("회원수");
				Object data[] = {name, region, part};
				mp.setTable(data);
			}
			stmt.close(); rs.close();
		} catch (SQLException e) { e.printStackTrace();}
		
	}
	
	//직원 정보 DB상에서 받아옴
	public void inforDb() throws SQLException {
		 String query ="select 직원.이름, 직원.지역, 직원.전화번호, 측정소.상세주소, 측정소.용도구분 "
		       + "from 직원, 측정소 where 측정소.지역='"+region+"' and 직원.지역='"+region+"'";
		 PreparedStatement stmt = con.prepareStatement(query);
		 ResultSet rs = stmt.executeQuery(query);
		 try {
         while (rs.next()) {
	         String name=rs.getString("이름");
	         String region=rs.getString("지역");
	         String part=rs.getString("용도구분");
	         String number=rs.getString("전화번호");
	         String address=rs.getString("상세주소");
	         inf.setStr(name, region, part, number, address);
		 }
		 stmt.close(); rs.close();
		 } catch (SQLException e) { e.printStackTrace();}
		      
	}	
}
