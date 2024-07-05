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
	//���̺�
	private static JTable table;
	private static String header[]= {"�����̸�","����","ȸ����"};
	private static DefaultTableModel model=new DefaultTableModel(header,0);	//header�߰�, ���� 0�� ����;
	private JScrollPane scrollPane;
	private JScrollPane scrolledTable;

	static Db db = new Db(0);
	static Login log = new Login(0);
	
	public void setTable(Object data[]) {
		model.addRow(data);
	}
	public void setReset() {
		model.setNumRows(0); //������ ���̺� �� �� �����
	}

	//����ȭ�� ���İ� ����
	public MeasurePlace(int a) {
		if(a==1) {
			titleL = new JLabel("������");
			titleL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 25));
			titleL.setBounds(280, 10, 250, 50);
			
			// ���̺�
			table=new JTable(model);
			table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			table.addMouseListener(this); //���̺� Ŭ���� �� �ҷ�����
			scrolledTable=new JScrollPane(table);	//��ũ�� �� �� �ֵ��� JScrollPane ����
			scrolledTable.setBounds(100, 70, 450, 350);
			
			closeBtn = new JButton("�ݱ�");
		    closeBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));     
		    closeBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {	// ������ ��ư ������ ��
						setVisible(false);
					}
		    });
	
			Container c = this.getContentPane();
			c.add(titleL);
			c.add(scrolledTable);
		    c.add(closeBtn).setBounds(270, 440, 90, 40);
	
	
	
			setLayout(null);
	
			// �⺻ ������ ����.
			setLayout(null);
			setBounds(480, 10, 650, 530);
			setVisible(true);
			setResizable(false);	
		}
	}
	
	
	//���̺� Ŭ���� �ش� ���̺� ���� �޾ƿ�
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
