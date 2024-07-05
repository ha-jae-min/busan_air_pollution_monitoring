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

public class MeasurePlace extends JFrame implements ActionListener, MouseListener{
	private JLabel titleL;
	private JButton closeBtn;
	//테이블
	private static JTable table;
	private static String header[]= {"직원이름","지역","회원수"};
	private static DefaultTableModel model=new DefaultTableModel(header,0);	//header추가, 행은 0개 지정;
	private JScrollPane scrollPane;
	private JScrollPane scrolledTable;

	static Db db = new Db(0);
	static Login log = new Login(0);
	
	public void setTable(Object data[]) {
		model.addRow(data);
	}
	public void setReset() {
		model.setNumRows(0); //기존의 테이블 값 다 지우기
	}

	//메인화면 형식과 동일
	public MeasurePlace(int a) {
		if(a==1) {
			titleL = new JLabel("측정소");
			titleL.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 25));
			titleL.setBounds(280, 10, 250, 50);
			
			// 테이블
			table=new JTable(model);
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			table.addMouseListener(this); //테이블 클릭시 값 불러오기
			scrolledTable=new JScrollPane(table);	//스크롤 될 수 있도록 JScrollPane 적용
			scrolledTable.setBounds(100, 70, 450, 350);
			
			closeBtn = new JButton("닫기");
		    closeBtn.setFont(new Font("한컴 바겐세일 B", Font.BOLD, 20));     
		    closeBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {	// 측정소 버튼 눌렀을 떄
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
			setBounds(480, 10, 650, 530);
			setVisible(true);
			setResizable(false);	
		}
	}
	
	
	//테이블 클릭시 해당 테이블 값을 받아옴
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		String region=(String) table.getModel().getValueAt(row, 1 );
		db.setRegion(region);
		log.setSib(1);
		try {
			log.login();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mousePressed(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseReleased(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseEntered(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void mouseExited(MouseEvent e) {
		// TODO Auto-generated method stub
		
	}
	
	
	
	
}
