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
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;


public class Member extends JFrame implements ActionListener{
	private JLabel titleL;
	private JLabel l1, l2, l3, l4, l5;
	private JTextField t1 ,t2 ,t3 ,t4, t5;
	private JButton closeBtn, updateBtn, checkBtn;
	static String id, pw, name, region, phone;
	static Db db = new Db(0);
	static Login log = new Login(0);
	static Trigger tr = new Trigger(0);
	private static String[] place= {"명장동", "장림동", "학장동", "덕천동", "연산동", "대연동", "청룡동", "전포동"
			, "태종대", "기장읍", "대저동", "부곡동", "광안동", "광복동", "녹산동", "용수리", "좌동", "수정동"
			, "대신동", "덕포동", "개금동", "당리동", "청학동", "재송동", "화명동", "명지동", "회동동"
			, "용호동", "온천동", "초량동", "삼락동"};
	
	public void setStr(String id, String pw, String name, String region, String phone) {
		this.id=id;
		this.pw=pw;
		this.name=name;
		this.region=region;
		this.phone=phone;
	}

	public Member(int a) {
	      if(a==1) {
	          titleL = new JLabel("회원 정보");
	          titleL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 25));
	          titleL.setBounds(10, 10, 250, 50);

	          l1 = new JLabel("아이디 : ");
	          l1.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          l2 = new JLabel("비밀번호 : ");
	          l2.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          l3 = new JLabel("이름 : ");
	          l3.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          l4 = new JLabel("거주지 : ");
	          l4.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          l5 = new JLabel("전화번호 : ");
	          l5.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));

	          t1 = new JTextField();
	          t1.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          t1.setDocument((new JTextFieldLimit(20)));
	          t1.setText(id);         
	          t2 = new JTextField();
	          t2.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));      
	          t2.setDocument((new JTextFieldLimit(20)));
	          t2.setText(pw);
	          t3 = new JTextField();
	          t3.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          t3.setDocument((new JTextFieldLimit(20)));
	          t3.setText(name);
	          t4 = new JTextField();
	          t4.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          t4.setDocument((new JTextFieldLimit(20)));
	          t4.setText(region);
	          t5 = new JTextField();
	          t5.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));
	          t5.setDocument((new JTextFieldLimit(20)));
	          t5.setText(phone);
	          
	          updateBtn = new JButton("확인");
	          updateBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));     
	          updateBtn.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {   // 확인
	                	if(id.equals(t1.getText())) {
		                	if(t1.getText().length()==0 || t2.getText().length()==0 || t3.getText().length()==0 || t4.getText().length()==0 || t5.getText().length()==0) 
			      	        	  JOptionPane.showMessageDialog(null, "빈칸이 있습니다");
			  				else {
			      	          	db.setMember(t1.getText(), t2.getText(), t3.getText(), t4.getText(), t5.getText());
				      	        log.setSib(5);
				      	        tr.setAddress(t4.getText());
				      	        db.setAddress(t4.getText());		      	
								try {
										log.login();
									} catch (SQLException e1) {
										// TODO Auto-generated catch block
										e1.printStackTrace();
									}
				               }
		                }
	                	else
	        				JOptionPane.showMessageDialog(null, "아이디 중복확인 하세요");
	                }
	          });
	         	          	       
	          closeBtn = new JButton("닫기");
	          closeBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));     
	          closeBtn.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {   // 취소 버튼 눌렀을 떄
	                   setVisible(false);
	                }
	          });
	          
	          checkBtn = new JButton("체크");
				checkBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 10)); 
				checkBtn.addActionListener(new ActionListener() { 
					@Override
					public void actionPerformed(ActionEvent e) {
							if(t1.getText().length()==0)
								JOptionPane.showMessageDialog(null, "빈칸입니다");
							else {
								db.setJoinCheck(t1.getText());
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
	    
	          Container c = this.getContentPane();
	          c.add(titleL);
	          c.add(closeBtn).setBounds(250, 300, 100, 40);
	          c.add(updateBtn).setBounds(100, 300, 100, 40);
	          c.add(checkBtn).setBounds(380, 93, 60, 25);
	          c.add(l1).setBounds(10, 80, 250, 50);
	          c.add(l2).setBounds(10, 120, 250, 50);
	          c.add(l3).setBounds(10, 160, 250, 50);
	          c.add(l4).setBounds(10, 200, 250, 50);
	          c.add(l5).setBounds(10, 240, 250, 50);
	          c.add(t1).setBounds(120, 93, 250, 25);
	          c.add(t2).setBounds(120, 133, 250, 25);
	          c.add(t3).setBounds(120, 173, 250, 25);
	          c.add(t4).setBounds(120, 213, 250, 25);
	          c.add(t5).setBounds(120, 253, 250, 25);	    
	    
	          JComboBox combo = new JComboBox(place);
				c.add(combo).setBounds(380, 210, 90, 30);
			      combo.addActionListener(new ActionListener() {
			          public void actionPerformed(ActionEvent e) {		
			        	  String choice=combo.getSelectedItem().toString();
			        	  t4.setText(choice);
			          }
			      });
			      
	          // 기본 프레임 생성.
	          setLayout(null);
	          setBounds(530, 30, 500, 400);
	          setVisible(true);
	          setResizable(false);   
	       }
	}
	
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
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

