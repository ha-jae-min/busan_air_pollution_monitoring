package busan;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.border.Border;
import javax.swing.border.CompoundBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.border.MatteBorder;

import java.awt.*;
import javax.swing.event.*;
import javax.swing.table.DefaultTableModel;
import javax.xml.crypto.Data;

import busan.Login.bgPanel;

import java.awt.event.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.sql.*;
import java.util.*;
import java.util.List;
import java.time.LocalDate;

public class Main extends JFrame implements ActionListener, MouseListener{
	private JLabel idL, mainlecture, manager, good, normal, bad, vbad;
	//�޺��ڽ�
	private static String[] region= {"���嵿", "�帲��", "���嵿", "��õ��", "���굿", "�뿬��", "û�浿", "������"
			, "������", "������", "������", "�ΰ", "���ȵ�", "������", "��굿", "�����", "�µ�", "������"
			, "��ŵ�", "������", "���ݵ�", "�縮��", "û�е�", "��۵�", "ȭ��", "������", "ȸ����"
			, "��ȣ��", "��õ��", "�ʷ���", "�����"};
	
	//��ư
	private JButton placeBtn, dangerBtn, refreshBtn, triggerBtn,changeBtn, seroBtn, userBtn;
	
	//�� ������ ��
	private static String name;
	private static String ad, address;
	private static Object date;
	
	//���̺�
	private static JTable table;
	private static HistogramPanel panel, panel2;
	private static String header[]= {"������¥", "����׸�", "������", "�����ܰ�"};
	private static double val[] = new double[4];
	private static DefaultTableModel model=new DefaultTableModel(header,0);	//header�߰�, ���� 0�� ����;
	private JScrollPane scrollPane;
	private JScrollPane scrolledTable;
	static Db db = new Db(0);
	static Login log = new Login(0);
	static Trigger tr = new Trigger(0);
	static int ok=0, er=0, manage=0;
	
	public void setName(String name) {
		this.name = name;
	}
	public void setAddress(String ad) {
		this.ad = ad;
		address=ad;
	}
	public void setTable(Object data[]) {
		model.addRow(data);
	}
	public void setval(int i, double value) {
		val[i] = value;
	}
	public void setManage(int manage) {
		this.manage = manage;
	}
	
	public static void main(String arg[]) {
		Login login = new Login(1);	
	}
	
	public Main(int a) {
		if(a==1) {
		      mainlecture = new JLabel();
		      mainlecture.setFont(new Font("���� ���� M", Font.BOLD, 20));
		      manager = new JLabel("������");
		      manager.setFont(new Font("���� ���� M", Font.BOLD, 20));
			  idL = new JLabel(name+"�� ȯ���մϴ�");
		      idL.setFont(new Font("���� ���� M", Font.BOLD, 20));
		      good = new JLabel("����");
		      good.setFont(new Font("���� ���� M", Font.BOLD, 15));
		      good.setForeground(Color.blue);
		      normal = new JLabel("����");
		      normal.setFont(new Font("���� ���� M", Font.BOLD, 15));
		      normal.setForeground(Color.green);
		      bad = new JLabel("����");
		      bad.setFont(new Font("���� ���� M", Font.BOLD, 15));
		      bad.setForeground(Color.orange);
		      vbad = new JLabel("�ſ쳪��");
		      vbad.setFont(new Font("���� ���� M", Font.BOLD, 15));
		      vbad.setForeground(Color.red);
		      
			  JComboBox combo = new JComboBox(region);
		      	
		      //��ġ   setBounds(int x, int y, int width, int height)
		      idL.setBounds(480, 10, 500, 20);
		      mainlecture.setBounds(700, 10, 400, 20);
			  mainlecture.setText("������ : "+ ad);
	
			  //������ ���̺�
			  table=new JTable(model);
			  table.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
			  table.addMouseListener(this); //���̺� Ŭ���� �� �ҷ�����
			  scrolledTable=new JScrollPane(table);	//��ũ�� �� �� �ֵ��� JScrollPane ����
			  scrolledTable.setBounds(480, 70, 500, 488);		  	
			 
		      triggerBtn = new JButton("������");
		      triggerBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
		      triggerBtn.addActionListener(new ActionListener() {
		    	  public void actionPerformed(ActionEvent e) {	
		    		 Trigger tg = new Trigger(1); 
					}
		      });
		      //��ư
		      dangerBtn = new JButton("��������");
		      dangerBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));
		      dangerBtn.addActionListener(new ActionListener() {
		    	  public void actionPerformed(ActionEvent e) {	
		    		 danger dg = new danger(1);  
					}
		      });
		      
		      placeBtn = new JButton("������");
		      placeBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20));     
		      placeBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {	// ������ ��ư ������ ��
						log.setSib(11);
						try {
							log.login();
						} catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
						}
						MeasurePlace mp = new MeasurePlace(1);
					}
				});
		      
		      refreshBtn = new JButton("��ü����");
		      refreshBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 20)); 
		      refreshBtn.addActionListener(new ActionListener() {
					@Override
					public void actionPerformed(ActionEvent e) {	// ��ü���� ��ư ������ ��
						model.setNumRows(0); 
						if(ad.equals(address)) {
							log.setSib(3);
							db.setdbOk(1);
							er=1;
							try {
								log.login();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
						else {
							log.setSib(2);
							db.setdbOk2(1);
							er=1;
							try {
								log.login();
							} catch (SQLException e1) {
								// TODO Auto-generated catch block
								e1.printStackTrace();
							}
						}
					}
				});
		      
		      seroBtn = new JButton("���ΰ�ħ");
		      seroBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 10));
		      seroBtn.addActionListener(new ActionListener() {
		    	  public void actionPerformed(ActionEvent e) {	
		    		 setVisible(false);
		    		 model.setNumRows(0); 
		    		 db.setRegion(address);
		    		 log.setSib(3);
		        	  try {
						log.login();
		        	  } catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
		        	  }		
		        	  Main main = new Main(1);
					}
		      });
		      
		      changeBtn = new JButton("����");
		      changeBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 10));
		      changeBtn.addActionListener(new ActionListener() {
		    	  public void actionPerformed(ActionEvent e) {	
		    		  Member m = new Member(1); 
					}
		      });
		      
		      userBtn = new JButton("ȸ��");
		      userBtn.setFont(new Font("���� �ٰռ��� B", Font.BOLD, 10));
		      userBtn.addActionListener(new ActionListener() {
		    	  public void actionPerformed(ActionEvent e) {	
		    		  log.setSib(8);
		    		  try {
							log.login();
			          } catch (SQLException e1) {
							// TODO Auto-generated catch block
							e1.printStackTrace();
			          }	
		    		  UserList u = new UserList(1); 
					}
		      });
		      
		      panel = new HistogramPanel();	
		      panel2 = new HistogramPanel();
		      setRepaint();
		      panel.layoutHistogram();
		      panel2.layoutHistogram();
		      
		      //�߰�
			  Container c = this.getContentPane();			  
		      c.add(idL);
		      c.add(mainlecture);
		      c.add(good).setBounds(60, 590, 50, 30);
		      c.add(normal).setBounds(180, 590, 50, 30);
		      c.add(bad).setBounds(280, 590, 50, 30);
		      c.add(vbad).setBounds(380, 590, 100, 30);
		      c.add(scrolledTable);
		      c.add(panel).setBounds(50, 40, 400, 280);
		      c.add(panel2).setBounds(50, 310, 400, 280); //����, �ϻ�ȭź�� ������ ������׷��ϳ��� �߰�(��ǥ)
		      c.add(dangerBtn).setBounds(730, 570, 118, 40);
		      c.add(placeBtn).setBounds(855, 570, 118, 40);
		      c.add(refreshBtn).setBounds(480, 570, 118, 40);
		      c.add(triggerBtn).setBounds(605, 570, 118, 40);
		      c.add(changeBtn).setBounds(855, 10, 60, 20);
		      c.add(seroBtn).setBounds(480, 35, 90, 35);
		      if(manage==1) {
		    	  c.add(manager).setBounds(380, 10, 500, 20);
		      	  c.add(userBtn).setBounds(300, 10, 60, 20);
		      }
      	      
		      //���� �����ϴ� �޺��ڽ�
		      c.add(combo).setBounds(889, 35, 90, 30);
		      combo.addActionListener(new ActionListener() {
		          public void actionPerformed(ActionEvent e) {		
		        	  date=null;
		        	  setVisible(false); 
		        	  address=combo.getSelectedItem().toString();
		        	  for(int i=0; i<region.length; i++) { //31���� �� �߿� �޺��ڽ����� ������ ���� �� ó�� ���� �ٲ�
		        		  if(region[i].equals(address)) {
		        			  region[i]=region[0];
		        			  region[0]=address;
		        		  }
		        	  }
		        	  model.setNumRows(0); //������ ���̺� �� �� �����	        	  
		        	  db.setRegion(address);
		        	  db.setAddress(address);
		        	  tr.setAddress(address); //�޺��ڽ��� �����ٲۻ��¿��� Ʈ���������� ���� �Ѱ���
		        	  log.setSib(2);
		        	  try {
						log.login();
		        	  } catch (SQLException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
		        	  }		        	  
		        	  ok=1;
		        	  Main main = new Main(1);
		          }
		      });
			 		  
			  if(ok==1) {		//���� ��¥�� ��Ÿ���� ���ؼ�
				  if(date==null) {
					  LocalDate now = LocalDate.now();
					  String test = String.valueOf(now);
					  JLabel te = new JLabel(test);
					  te.setFont(new Font("���� ���� M", Font.BOLD, 20));
					  c.add(te).setBounds(80, 5, 300, 30);
				  }
				  else {
					  String test = String.valueOf(date);
					  JLabel te = new JLabel(test);
					  te.setFont(new Font("���� ���� M", Font.BOLD, 20));
					  c.add(te).setBounds(80, 5, 300, 30);
				  }

				  JLabel pl = new JLabel(address);
				  pl.setFont(new Font("���� ���� M", Font.BOLD, 20));
				  c.add(pl).setBounds(200, 5, 300, 30);
			  }
			  else {
				  LocalDate now = LocalDate.now();
				  String test = String.valueOf(now);
				  JLabel te = new JLabel(test);
				  te.setFont(new Font("���� ���� M", Font.BOLD, 20));
				  c.add(te).setBounds(80, 5, 300, 30);
				  JLabel pl = new JLabel(address);
				  pl.setFont(new Font("���� ���� M", Font.BOLD, 20));
				  c.add(pl).setBounds(200, 5, 300, 30);
			  }		  
		         
			   //�α����� �Ǹ� �⺻ ������ ����.
			  setLayout(null);
			  setBounds(545, 110, 1000, 660);
			  setVisible(true);
			  setResizable(false);			  
		}

	}	
	
	//���̺� Ŭ���� �ش� ���̺� ���� �޾ƿ�
	public void mouseClicked(MouseEvent e) {
		int row = table.getSelectedRow();
		date=table.getModel().getValueAt(row, 0);
		double v0 = (double) table.getModel().getValueAt(row, 2);
		double v1 = (double) table.getModel().getValueAt(row+1, 2);
		double v2 = (double) table.getModel().getValueAt(row+2, 2);
		double v3 = (double) table.getModel().getValueAt(row+3, 2);
		String s0 = (String) table.getModel().getValueAt(row, 1);
		String s1 = (String) table.getModel().getValueAt(row+1, 1);
		String s2 = (String) table.getModel().getValueAt(row+2, 1);
		String s3 = (String) table.getModel().getValueAt(row+3, 1);
		
		//����׸��� ������� �� ���־ �׷����� ���缭 ����
		//�ش��ϴ� ��¥ ù ��° ���� ������ �� ������� 2022-11-01�� 4���� ������ �� �� �� ���� �ִ� ��
		if(s0.equals("�ʹ̼�����"))
			val[0]=v0;
		else if(s1.equals("�ʹ̼�����"))
			val[0]=v1;
		else if(s2.equals("�ʹ̼�����"))
			val[0]=v2;
		else if(s3.equals("�ʹ̼�����"))
			val[0]=v3;
		
		if(s0.equals("�̼�����"))
			val[1]=v0;
		else if(s1.equals("�̼�����"))
			val[1]=v1;
		else if(s2.equals("�̼�����"))
			val[1]=v2;
		else if(s3.equals("�̼�����"))
			val[1]=v3;
		
		if(s0.equals("����"))
			val[2]=v0;
		else if(s1.equals("����"))
			val[2]=v1;
		else if(s2.equals("����"))
			val[2]=v2;
		else if(s3.equals("����"))
			val[2]=v3;
		
		if(s0.equals("�ϻ�ȭź��"))
			val[3]=v0;
		else if(s1.equals("�ϻ�ȭź��"))
			val[3]=v1;
		else if(s2.equals("�ϻ�ȭź��"))
			val[3]=v2;
		else if(s3.equals("�ϻ�ȭź��"))
			val[3]=v3;

		//setRepaint(); �̰� �ȵǳ� ��������������
		setVisible(false);
		ok=1;
		Main main = new Main(1);
	}
	
	public void setRepaint() {
		  //����׷��� �����ܰ迡 ���� ���� �ٸ���      
		  if(val[0]>=0 && val[0]<=15) 
	    	  panel.addHistogramColumn("�ʹ̼�����", val[0], Color.BLUE);		      
	      else if(val[0]>=16 && val[0]<=35) 
	    	  panel.addHistogramColumn("�ʹ̼�����", val[0], Color.GREEN);		      
	      else if(val[0]>=36 && val[0]<=75) 
	    	  panel.addHistogramColumn("�ʹ̼�����", val[0], Color.ORANGE);	      
	      else 
	    	  panel.addHistogramColumn("�ʹ̼�����", val[0], Color.RED);
	    	  	      
	      if(val[1]>=0 && val[1]<=30)
	    	  panel.addHistogramColumn("�̼�����", val[1], Color.BLUE);
	      else if(val[1]>=31 && val[1]<=80)
	    	  panel.addHistogramColumn("�̼�����", val[1], Color.GREEN);
	      else if(val[1]>=81 && val[1]<=150)
	    	  panel.addHistogramColumn("�̼�����", val[1], Color.ORANGE);
	      else
	    	  panel.addHistogramColumn("�̼�����", val[1], Color.RED);
	      
	      if(val[2]>=0 && val[2]<=0.03)
	    	  panel2.addHistogramColumn("����", val[2], Color.BLUE);
	      else if(val[2]>=0.031 && val[2]<=0.09)
	    	  panel2.addHistogramColumn("����", val[2], Color.GREEN);
	      else if(val[2]>=0.091 && val[2]<=0.15)
	    	  panel2.addHistogramColumn("����", val[2], Color.ORANGE);
	      else
	    	  panel2.addHistogramColumn("����", val[2], Color.RED);
	      
	      if(val[3]>=0 && val[3]<=2)
	    	  panel2.addHistogramColumn("�ϻ�ȭź��", val[3], Color.BLUE);
	      else if(val[3]>=2.01 && val[3]<=9)
	    	  panel2.addHistogramColumn("�ϻ�ȭź��", val[3], Color.GREEN);
	      else if(val[3]>=9.01 && val[3]<=15)
	    	  panel2.addHistogramColumn("�ϻ�ȭź��", val[3], Color.ORANGE);
	      else
	    	  panel2.addHistogramColumn("�ϻ�ȭź��", val[3], Color.RED);
	  
	}
	
	//����׷��� Ŭ����
	class HistogramPanel extends JPanel {
		//����׷���
		private int histogramHeight = 200;
	    private int barWidth = 50;
	    private int barGap = 10;
	    private JPanel barPanel;
	    private JPanel labelPanel;
	    private List<Bar> bars = new ArrayList<Bar>();
	    
	    public HistogramPanel()
	    {
	        setBorder( new EmptyBorder(10, 10, 10, 10) );
	        setLayout( new BorderLayout() );

	        barPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
	        Border outer = new MatteBorder(1, 1, 1, 1, Color.BLACK);
	        Border inner = new EmptyBorder(10, 10, 0, 10);
	        Border compound = new CompoundBorder(outer, inner);
	        barPanel.setBorder( compound );

	        labelPanel = new JPanel( new GridLayout(1, 0, barGap, 0) );
	        labelPanel.setBorder( new EmptyBorder(5, 10, 0, 10) );

	        add(barPanel, BorderLayout.CENTER);
	        add(labelPanel, BorderLayout.PAGE_END);
	    }
	    
	    public void addHistogramColumn(String label, double value, Color color)
	    {
	        Bar bar = new Bar(label, value, color);
	        bars.add( bar );
	    }

	    public void layoutHistogram()
	    {
	        barPanel.removeAll();
	        labelPanel.removeAll();

	        double maxValue = 0;

	        for (Bar bar: bars)
	            maxValue = Math.max(maxValue, bar.getValue());

	        for (Bar bar: bars)
	        {
	            JLabel label = new JLabel(bar.getValue() + "");
	            label.setHorizontalTextPosition(JLabel.CENTER);
	            label.setHorizontalAlignment(JLabel.CENTER);
	            label.setVerticalTextPosition(JLabel.TOP);
	            label.setVerticalAlignment(JLabel.BOTTOM);
	            double barHeight = (bar.getValue() * histogramHeight) / maxValue;
	            Icon icon = new ColorIcon(bar.getColor(), barWidth, barHeight);
	            label.setIcon( icon );
	            barPanel.add( label );

	            JLabel barLabel = new JLabel( bar.getLabel() );
	            barLabel.setHorizontalAlignment(JLabel.CENTER);
	            labelPanel.add( barLabel );
	        }
	    }

	    private class Bar
	    {
	        private String label;
	        private double value;
	        private Color color;

	        public Bar(String label, double value, Color color)
	        {
	            this.label = label;
	            this.value = value;
	            this.color = color;
	        }

	        public String getLabel()
	        {
	            return label;
	        }

	        public double getValue()
	        {
	            return value;
	        }

	        public Color getColor()
	        {
	            return color;
	        }
	    }

	    private class ColorIcon implements Icon
	    {
	        private int shadow = 3;

	        private Color color;
	        private int width;
	        private double height;

	        public ColorIcon(Color color, int width, double barHeight)
	        {
	            this.color = color;
	            this.width = width;
	            this.height = barHeight;
	        }

	        public int getIconWidth()
	        {
	            return width;
	        }

	        public int getIconHeight()
	        {
	            return (int) height;
	        }

	        public void paintIcon(Component c, Graphics g, int x, int y)
	        {
	            g.setColor(color);
	            g.fillRect(x, y, width - shadow, (int) height);
	            g.setColor(Color.GRAY);
	            g.fillRect(x + width - shadow, y + shadow, shadow, (int) (height - shadow));
	        }
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
