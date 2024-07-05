package busan;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;

import oracle.net.aso.j;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;

public class Login extends JFrame implements ActionListener {
	private JLabel titleL, pwL, idL;
	private JTextField idT;
	private JPasswordField pwT;
	private JButton loginBtn;
	private JButton joinBtn; //ȸ������ ��ư
	private BufferedImage img = null;
	private String name;
	private String address;
	private static int ok=0, mok=0, jok=0;
	static Db db = new Db(2);
	static Main main = new Main(0); 
	static Join join = new Join(0);
	static UserList u = new UserList(0);
	static Trigger tg = new Trigger(0); 
	static MeasurePlace mp = new MeasurePlace(0);
	static int sib=0;
	
	public void setSib(int sib) {
		this.sib=sib;
	}
	public void setOk(int ok) {
		this.ok = ok;
	}
	public void setMangerOk(int mok) {
		this.mok = mok;
	}
	public void setJoinOk(int jok) {
		this.jok = jok;
	}
	
	public Login(int a) {
		// ���̵� �Է�â
		titleL = new JLabel("�λ�����Ȳ�ý���");
		titleL.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 30));

		idL = new JLabel("���̵�");
		idL.setFont(new Font("���� ���� M", Font.BOLD, 18));

		idT = new JTextField();
		idT.setFont(new Font("���� ���� M", Font.BOLD, 18));

		// ��й�ȣ �Է�â
		pwL = new JLabel("��й�ȣ");
		pwL.setFont(new Font("���� ���� M", Font.BOLD, 18));

		pwT = new JPasswordField();
		pwT.setFont(new Font("���� ���� M", Font.BOLD, 18));
		pwT.setEchoChar('��');

		// �α��� ��ư
		loginBtn = new JButton("�α���");
		loginBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	// �α��� ��ư�� �������� �߻� 
				try {
					login();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		idT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	// ID �Է� �� ���͸� �������� �߻�
				
				try {
					login();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		pwT.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {	// pw �Է� �� ���͸� �������� �߻�		
				try {
					login();
				} catch (SQLException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		});
		
		joinBtn = new JButton("ȸ������"); //ȸ������ â���� �������� ��ư
		joinBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 12));
		joinBtn.addActionListener(new ActionListener() {
	    	  public void actionPerformed(ActionEvent e) {	
	    		 Join j = new Join(1);
				}
	    });
		
		// ��ġ
		titleL.setBounds(190, 140, 300, 50);
		idL.setBounds(330, 230, 80, 30);
		idT.setBounds(420, 230, 150, 30);
		pwL.setBounds(330, 290, 80, 30);
		pwT.setBounds(420, 290, 150, 30);
		loginBtn.setBounds(460, 340, 132, 50);
		joinBtn.setBounds(460, 410, 132, 50);
		
		// add ~ * ~
		Container c = this.getContentPane();
		c.add(titleL);
		c.add(idL);
		c.add(idT);

		c.add(pwL);
		c.add(pwT);

		c.add(loginBtn);
		c.add(joinBtn); //UI�� ��ư �߰�
		
		setLayout(null);
		
		// ��� Panel
		JLayeredPane layeredPane = new JLayeredPane();
		layeredPane.setSize(650, 530);
		layeredPane.setLayout(null);

		try {
			img = ImageIO.read(new File("img/���ȴ뱳.jpg"));
		} catch (IOException e) {
			JOptionPane.showMessageDialog(null, "�̹��� �ҷ����� ����");
			System.exit(0);
		}
		// �̹��� ����
		bgPanel panel = new bgPanel();
		panel.setSize(650, 530);
		layeredPane.add(panel);
		add(layeredPane);

		// �⺻ ������ ����.
		if(a==1) {
		setLayout(null);
		setBounds(800, 200, 650, 530);
		setVisible(true);
		setResizable(false);	
		}
	}
	
	class bgPanel extends JPanel {
		public void paint(Graphics g) {
			g.drawImage(img, 0, 0, null);
		}
	}


	@Override
	public void actionPerformed(ActionEvent e) {
		
	}

	//�α��� �ϸ� �ߴ� â
	public void login() throws SQLException {
		// ������
		if(sib==0) {
			String id = idT.getText();
			String pw = pwT.getText();
			db.setId(id);
			db.setPw(pw);
			db.loginDb();
			
			if(ok==1) {
				setVisible(false);
				db.tableDb();
				db.placeDb();
				db.dangerDb();
				db.memberDb();
				Main main = new Main(1);
			}
		}
		else if(sib==1){		
			//���� ���� â ���
			db.inforDb();
			Information in = new Information(1);
		}
		else if(sib==2){
	      	db.tableDb2();      	
      	    //Main main = new Main(1);
		}
		else if(sib==3){
			db.tableDb();			 		
			//Main main = new Main(1);
		}
		else if(sib==4){
			db.triggerDb(); //������ ����
		}
		else if(sib==5){
			db.memberUpdateDb();
			db.memberDb();				
		}
		else if(sib==6) {
			if(jok==1) {
				db.joinDb();  //db�� ������ ȸ�� ����
				JOptionPane.showMessageDialog(null, "ȸ������ �Ϸ�");
				sib=0;
				join.setClose(1);
			}
			else
				JOptionPane.showMessageDialog(null, "���̵� �ߺ�Ȯ�� �ϼ���");
		}
		else if(sib==7) {
			db.managerDb();  //������ ID ���Ϸ���
			if(mok==1) {
				main.setManage(1);
				tg.setOk(1);
			}
		}
		else if(sib==8) {
			u.setReset();
			db.userDb();
		}
		else if(sib==9) {
			db.userDeleteDb();
			db.userDb();
		}
		else if(sib==10) {
			db.joinCheckDb();
		}
		else if(sib==11) {
			mp.setReset();
			db.placeDb();
		}
		
	}
	
	
}