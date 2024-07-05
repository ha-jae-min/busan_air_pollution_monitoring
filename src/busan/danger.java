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


public class danger extends JFrame implements ActionListener{
	private JLabel titleL;
	private JButton closeBtn;
	
	//테이블
	private static JTable table;
	private static String header[]= {"날짜", "대기항목", "위험지역", "오염단계"};
	private static DefaultTableModel model=new DefaultTableModel(header,0);	//header추가, 행은 0개 지정;
	private JScrollPane scrollPane;
	private JScrollPane scrolledTable;
	
	static Db db = new Db(0);
	static Login log = new Login(0);
	
	public void setTable(Object data[]) {
		model.addRow(data);
	}
	
	public danger(int a) {
		if(a==1)
		{
			titleL = new JLabel("위험지역");
			titleL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 25));
			titleL.setBounds(260, 10, 250, 50);
			
			// 테이블
			table=new JTable(model);
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			scrolledTable=new JScrollPane(table);	//스크롤 될 수 있도록 JScrollPane 적용
			scrolledTable.setBounds(100, 70, 450, 350);
			
			closeBtn = new JButton("닫기");
		    closeBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));     
		    closeBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {	
						setVisible(false);
					}
		    });
		    
		    Container c = this.getContentPane();
			c.add(titleL);
			c.add(scrolledTable);
		    c.add(closeBtn).setBounds(270, 440, 90, 40);
		    
		    setLayout(null);
			
			// 기본 프레임 생성.
			setLayout(null);
			setBounds(380, 10, 650, 530);
			setVisible(true);
			setResizable(false);
		}
	}

	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}

}
