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

public class Trigger extends JFrame implements ActionListener{
	private JLabel titleL, region, date, chomise, mise, ozon, ilsan, employeeID;
	private JTextField chomiseT, miseT, ozonT, ilsanT, employeeIDT;
	private JButton updateBtn, okBtn;
	static Db db = new Db(0);
	static Login log = new Login(0);
	static Main main = new Main(0);
	private static String address, eID;
	static int ok=0;
	
	public void setAddress(String address) {
		this.address=address;
	}
	public void seteID(String eID) {
		this.eID = eID;
	}
	public void setOk(int ok) {
		this.ok = ok;
	}
	public Trigger(int a) {
		if(a==1)
		{
			LocalDate now = LocalDate.now();
			titleL = new JLabel("측정값 변경");
			titleL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 25));
			titleL.setBounds(10, 10, 250, 50);
			
			region = new JLabel(address);
			region.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			date = new JLabel(String.valueOf(now));
			date.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			
			
			chomise = new JLabel("초미세먼지");
			chomise.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			mise = new JLabel("미세먼지");
			mise.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			ozon = new JLabel("오존");
			ozon.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			ilsan = new JLabel("일산화탄소");
			ilsan.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			employeeID = new JLabel("직원ID");
			employeeID.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			
			chomiseT = new JTextField();
			chomiseT.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			chomiseT.setDocument((new JTextFieldLimit(20)));
			miseT = new JTextField();
			miseT.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			miseT.setDocument((new JTextFieldLimit(20)));
			ozonT = new JTextField();
			ozonT.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			ozonT.setDocument((new JTextFieldLimit(20)));
			ilsanT = new JTextField();
			ilsanT.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			ilsanT.setDocument((new JTextFieldLimit(20)));
			employeeIDT = new JTextField();
			employeeIDT.setFont(new Font("한컴 백제 M", Font.BOLD, 18));
			employeeIDT.setDocument((new JTextFieldLimit(20)));
			
			if(ok==1)
				employeeIDT.setText("관리자");
			
			ilsanT.addActionListener(new ActionListener() {
	  			@Override
	  			public void actionPerformed(ActionEvent e) {	// 마지막 값 입력 후 엔터를 눌렀을때 발생
	  				double a1=Double.parseDouble(chomiseT.getText());
					double a2=Double.parseDouble(miseT.getText());
					double a3=Double.parseDouble(ozonT.getText());
					double a4=Double.parseDouble(ilsanT.getText());
					db.setValue(a1, a2, a3, a4);
					log.setSib(4);
					try {
						log.login();
					} catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}					
	  			}
			});	
			updateBtn = new JButton("확인");
			updateBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 15)); 
			updateBtn.addActionListener(new ActionListener() { //확인버튼 눌렀을때
					@Override
					public void actionPerformed(ActionEvent e) {	
						if(ok==1) {
							if(employeeIDT.getText().length()==0 || chomiseT.getText().length()==0 || miseT.getText().length()==0 || ozonT.getText().length()==0 || ilsanT.getText().length()==0) 
			      	        	  JOptionPane.showMessageDialog(null, "빈칸이 있습니다");
			  				else {
								double a1=Double.parseDouble(chomiseT.getText());
								double a2=Double.parseDouble(miseT.getText());
								double a3=Double.parseDouble(ozonT.getText());
								double a4=Double.parseDouble(ilsanT.getText());
								db.setValue(a1, a2, a3, a4);
								log.setSib(4); //측정값 수정		      	        
								try {
									log.login();
								} catch (SQLException e1) {
									// TODO Auto-generated catch block
									e1.printStackTrace();
								}	
								JOptionPane.showMessageDialog(null, "업데이트 완료");
			  				}
						}
						else
							JOptionPane.showMessageDialog(null, "관리자 ID 체크하세요");
					}
		    });		
			okBtn = new JButton("체크");
			okBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 10)); 
			okBtn.addActionListener(new ActionListener() { //확인버튼 눌렀을때
					@Override
					public void actionPerformed(ActionEvent e) {	
						String eID = employeeIDT.getText(); //직원아이디 입력받은거 eID에 저장
						db.setManage(eID);
			    		log.setSib(7); //관리자 ID비교					
						try {
							log.login();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}						
					}
		    });			
		    
		    Container c = this.getContentPane();
			c.add(titleL);
		    c.add(updateBtn).setBounds(200, 320, 70, 30);
		    c.add(okBtn).setBounds(380, 113, 60, 20);
	        c.add(region).setBounds(10, 60, 250, 50);
	        c.add(date).setBounds(100, 60, 250, 50);
	        c.add(chomise).setBounds(10, 140, 250, 50);
	        c.add(mise).setBounds(10, 180, 250, 50);
	        c.add(ozon).setBounds(10, 220, 250, 50);
	        c.add(ilsan).setBounds(10, 260, 250, 50);
	        c.add(chomiseT).setBounds(120, 153, 250, 25);
	        c.add(miseT).setBounds(120, 193, 250, 25);
	        c.add(ozonT).setBounds(120, 233, 250, 25);
	        c.add(ilsanT).setBounds(120, 273, 250, 25);	 
		    c.add(employeeID).setBounds(10, 100, 250, 50);
		    c.add(employeeIDT).setBounds(120, 113, 250, 25);
		    setLayout(null);
			
			// 기본 프레임 생성.
		    setLayout(null);
			setBounds(600, 40, 500, 400);
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

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
