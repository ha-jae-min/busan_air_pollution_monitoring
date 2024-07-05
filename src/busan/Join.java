package busan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import busan.Login.bgPanel;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.time.LocalDate;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

public class Join extends JFrame implements ActionListener{

	private JLabel titleL, idL, pwL, nameL, regionL, numL;
	private JTextField idT, pwT, nameT, regionT, numT;
	private JButton joinBtn, checkBtn; //생성버튼
	static int cl=0;
	static Db db = new Db(0);
	static Login log = new Login(0);
	private static String[] region= {"명장동", "장림동", "학장동", "덕천동", "연산동", "대연동", "청룡동", "전포동"
			, "태종대", "기장읍", "대저동", "부곡동", "광안동", "광복동", "녹산동", "용수리", "좌동", "수정동"
			, "대신동", "덕포동", "개금동", "당리동", "청학동", "재송동", "화명동", "명지동", "회동동"
			, "용호동", "온천동", "초량동", "삼락동"};
	
	public void setClose(int cl) {
		this.cl = cl;
	}
	
	public Join(int a) {
		if(a==1)
		{
			//라벨추가
			titleL = new JLabel("회원가입");
			titleL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 25));
			titleL.setBounds(10, 10, 250, 50);
			idL = new JLabel("아이디:");
			idL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
			pwL = new JLabel("비밀번호:");
			pwL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
			nameL = new JLabel("이름:");
			nameL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
			regionL = new JLabel("거주지(동):");
			regionL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
			numL = new JLabel("전화번호:");
			numL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
			
			idT = new JTextField();
			idT.setFont(new Font("한컴 백제 M", Font.BOLD, 20));
			idT.setDocument((new JTextFieldLimit(20)));
			pwT = new JTextField();
			pwT.setFont(new Font("한컴 백제 M", Font.BOLD, 20));
			pwT.setDocument((new JTextFieldLimit(20)));
			nameT = new JTextField();
			nameT.setFont(new Font("한컴 백제 M", Font.BOLD, 20));
			nameT.setDocument((new JTextFieldLimit(20)));
			regionT = new JTextField();
			regionT.setFont(new Font("한컴 백제 M", Font.BOLD, 20));
			regionT.setDocument((new JTextFieldLimit(20)));
			numT = new JTextField();
			numT.setFont(new Font("한컴 백제 M", Font.BOLD, 20));
			numT.setDocument((new JTextFieldLimit(20)));
			
			checkBtn = new JButton("체크");
			checkBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 10)); 
			checkBtn.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) {
						if(idT.getText().length()==0)
							JOptionPane.showMessageDialog(null, "빈칸입니다");
						else {
							db.setJoinCheck(idT.getText());
							log.setSib(10); 
							try {
								log.login();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}  							
						}			  				
				}
			});
			
			joinBtn = new JButton("생성");
			joinBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20)); 
			joinBtn.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) {	
					if(idT.getText().length()==0 || pwT.getText().length()==0 || nameT.getText().length()==0 || regionT.getText().length()==0 || numT.getText().length()==0) 
	      	        	  JOptionPane.showMessageDialog(null, "빈칸이 있습니다");
					else {
						String ID = idT.getText();
						String PW = pwT.getText();
						String name = nameT.getText();
						String region = regionT.getText();
						String num = numT.getText();
						db.setUser(ID, PW, name, region, num);
						log.setSib(6); 
						try {
							log.login();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						if(cl==1)
							setVisible(false);
						cl=0;
	  				}		  				
				}
			});
			
			
			
			Container c = this.getContentPane();
			c.add(titleL);
			c.add(joinBtn).setBounds(155, 300, 100, 40);
			c.add(checkBtn).setBounds(380, 93, 60, 25);
			c.add(idL).setBounds(10, 80, 250, 50);
			c.add(idT).setBounds(120, 93, 250, 25);
			c.add(pwL).setBounds(10, 120, 250, 50);
			c.add(pwT).setBounds(120, 133, 250, 25);
			c.add(nameL).setBounds(10, 160, 250, 50);
			c.add(nameT).setBounds(120, 173, 250, 25);
			c.add(regionL).setBounds(10, 200, 250, 50);
			c.add(regionT).setBounds(120, 213, 250, 25);
			c.add(numL).setBounds(10, 240, 250, 50);
			c.add(numT).setBounds(120, 253, 250, 25);	
			setLayout(null);
			
			JComboBox combo = new JComboBox(region);
			c.add(combo).setBounds(380, 210, 90, 30);
		      combo.addActionListener(new ActionListener() {
		          public void actionPerformed(ActionEvent e) {		
		        	  String choice=combo.getSelectedItem().toString();
		        	  regionT.setText(choice);
		          }
		      });
			
			// 기본 프레임 생성.
			setLayout(null);
			setBounds(580, 100, 500, 400);
			setVisible(true);
			setResizable(false);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//글자수 제한
	class JTextFieldLimit extends PlainDocument {
		   private int limit;
		   private boolean toUppercase = false;

		    JTextFieldLimit(int limit) {
		      super();
		      this.limit = limit;
		   }

		    JTextFieldLimit(int limit, boolean upper) {
		      super();
		      this.limit = limit;
		      this.toUppercase = upper;
		   }

		 

		   public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
		      if (str == null) {
		         return;
		      }

		      if ( (getLength() + str.length()) <= limit) {
		         if (toUppercase) {
		            str = str.toUpperCase();
		         }
		         super.insertString(offset, str, attr);
		      }
		   }
		}
}


