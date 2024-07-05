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

public class Information extends JFrame implements ActionListener{
	private JLabel titleL;
	private JLabel l1, l2, l3, l4, l5;
	private JLabel t1 ,t2 ,t3 ,t4, t5;
	private JButton closeBtn;
	static String name, region, part, number, address;
	static Db db = new Db(0);
	
	public void setStr(String name, String region, String part, String number, String address) {
		this.name=name;
		this.region=region;
		this.part=part;
		this.number=number;
		this.address=address;
	}
	
	//�̰� �ּ� �� �� ����
	public Information(int a) {
	      if(a==1) {
	          titleL = new JLabel("���� ����");
	          titleL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 25));
	          titleL.setBounds(10, 10, 250, 50);

	          l1 = new JLabel("�̸� : ");
	          l1.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          l2 = new JLabel("��ȭ��ȣ : ");
	          l2.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          l3 = new JLabel("���� : ");
	          l3.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          l4 = new JLabel("���ּ� : ");
	          l4.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          l5 = new JLabel("�뵵���� : ");
	          l5.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));

	          t1 = new JLabel(name);
	          t1.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          t2 = new JLabel(number);
	          t2.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          t3 = new JLabel(region);
	          t3.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          t4 = new JLabel(address);
	          t4.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          t5 = new JLabel(part);
	          t5.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
	          
	          
	          closeBtn = new JButton("�ݱ�");
	           closeBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));     
	           closeBtn.addActionListener(new ActionListener() {
	                @Override
	                public void actionPerformed(ActionEvent e) {   // ��� ��ư ������ ��
	                   setVisible(false);
	                }
	           });
	    
	          Container c = this.getContentPane();
	          c.add(titleL);
	           c.add(closeBtn).setBounds(200, 300, 100, 40);
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
	    
	          // �⺻ ������ ����.
	          setLayout(null);
	          setBounds(980, 10, 500, 400);
	          setVisible(true);
	          setResizable(false);   
	       }
	}
	

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
}

