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
	private JButton joinBtn, checkBtn; //������ư
	static int cl=0;
	static Db db = new Db(0);
	static Login log = new Login(0);
	private static String[] region= {"���嵿", "�帲��", "���嵿", "��õ��", "���굿", "�뿬��", "û�浿", "������"
			, "������", "������", "������", "�ΰ", "���ȵ�", "������", "��굿", "�����", "�µ�", "������"
			, "��ŵ�", "������", "���ݵ�", "�縮��", "û�е�", "��۵�", "ȭ��", "������", "ȸ����"
			, "��ȣ��", "��õ��", "�ʷ���", "�����"};
	
	public void setClose(int cl) {
		this.cl = cl;
	}
	
	public Join(int a) {
		if(a==1)
		{
			//���߰�
			titleL = new JLabel("ȸ������");
			titleL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 25));
			titleL.setBounds(10, 10, 250, 50);
			idL = new JLabel("���̵�:");
			idL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
			pwL = new JLabel("��й�ȣ:");
			pwL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
			nameL = new JLabel("�̸�:");
			nameL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
			regionL = new JLabel("������(��):");
			regionL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
			numL = new JLabel("��ȭ��ȣ:");
			numL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
			
			idT = new JTextField();
			idT.setFont(new Font("���� ���� M", Font.BOLD, 20));
			idT.setDocument((new JTextFieldLimit(20)));
			pwT = new JTextField();
			pwT.setFont(new Font("���� ���� M", Font.BOLD, 20));
			pwT.setDocument((new JTextFieldLimit(20)));
			nameT = new JTextField();
			nameT.setFont(new Font("���� ���� M", Font.BOLD, 20));
			nameT.setDocument((new JTextFieldLimit(20)));
			regionT = new JTextField();
			regionT.setFont(new Font("���� ���� M", Font.BOLD, 20));
			regionT.setDocument((new JTextFieldLimit(20)));
			numT = new JTextField();
			numT.setFont(new Font("���� ���� M", Font.BOLD, 20));
			numT.setDocument((new JTextFieldLimit(20)));
			
			checkBtn = new JButton("üũ");
			checkBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 10)); 
			checkBtn.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) {
						if(idT.getText().length()==0)
							JOptionPane.showMessageDialog(null, "��ĭ�Դϴ�");
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
			
			joinBtn = new JButton("����");
			joinBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20)); 
			joinBtn.addActionListener(new ActionListener() { 
				@Override
				public void actionPerformed(ActionEvent e) {	
					if(idT.getText().length()==0 || pwT.getText().length()==0 || nameT.getText().length()==0 || regionT.getText().length()==0 || numT.getText().length()==0) 
	      	        	  JOptionPane.showMessageDialog(null, "��ĭ�� �ֽ��ϴ�");
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
			
			// �⺻ ������ ����.
			setLayout(null);
			setBounds(580, 100, 500, 400);
			setVisible(true);
			setResizable(false);
		}
	}
	
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	//���ڼ� ����
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


