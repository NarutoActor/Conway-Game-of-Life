import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.*;
import javax.swing.border.TitledBorder;


public class GoL extends JFrame{
JPanel p1 = new JPanel(new BorderLayout());
JPanel p2 = new JPanel(new GridLayout(32,32));//Table Panel
//JPanel p3 = new JPanel();//Tool Panel
JPanel p4 = new JPanel();//Signiture
JPanel p5 = new JPanel(new GridLayout(2,1));//PreMade Set
JPanel p6 = new JPanel(new GridLayout(2,1));
JPanel p7 = new JPanel();//front
JPanel p8 = new JPanel();//back
JPanel p9 = new JPanel(new BorderLayout());//Padding
JLabel l1 = new JLabel((char)169 +"Lucas Rivera & Hans Kiessler 2014 for Rutgers University-HACK RU");
JLabel l2 = new JLabel("PreMade Sets");
JLabel l3 = new JLabel("Stop At N=:");
JButton b1 = new JButton("Play");
JButton b2 = new JButton("Stop");
JButton b3 = new JButton("About");
JTextField t1 = new JTextField("N=0");
JTextField t2 = new JTextField("-1");
Cell[][] ppl = new Cell[32][32];
String s[] = {"Space Ship","Rotator"};
JComboBox c1 = new JComboBox(s);
JScrollBar sb = new JScrollBar();
JSplitPane p3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,p5,p6);

	public GoL(){
		p9.add(p3,BorderLayout.CENTER);
		p9.add(p4, BorderLayout.SOUTH);
		
		p2.setMaximumSize(new Dimension(800,200));
		p2.setSize(800, 200);
		
		t1.setEditable(false);
		p3.setMinimumSize(new Dimension(100,50));
		p7.add(t1);
		p7.add(b1);
		p7.add(b2);
		p7.add(b3);
		p8.add(l3);
		p8.add(t2);
		p6.add(p7);
		p6.add(p8);
		p5.add(l2);
		p5.add(c1);
		//p3.add(p5);
		TitledBorder title = BorderFactory.createTitledBorder("CUSTOM");
		p6.setBorder(title);
		for(int i=0;i<ppl.length;i++){
			for(int j=0;j<ppl[0].length;j++){
				ppl[i][j] = new Cell();
				ppl[i][j].addActionListener(new userClick());
				p2.add(ppl[i][j]);
			}
		}
		p4.add(l1);
		p1.add(p2, BorderLayout.CENTER);
		p1.add(p9, BorderLayout.SOUTH);//used to be p3
		//p1.add(p4, BorderLayout.SOUTH);
		add(p1);
		
		
	}
	public static void main(String[] args) {
		GoL r1 = new GoL();
		r1.setSize(800, 780);
		r1.setMinimumSize(new Dimension(600, 400));
		r1.setVisible(true);
		r1.setTitle("Conway's - The Game of Life");
		r1.setLocationRelativeTo(null);
		r1.setDefaultCloseOperation(EXIT_ON_CLOSE);

	}
	public class Cell extends JButton{
		//JButton b;
		boolean alive;
		Cell(){
			//b = new JButton();
		}
	}
	public class userClick implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			Cell p = (Cell)e.getSource();
			p.setBackground(Color.black);
			p.setOpaque(true);
			System.out.println("Did I get here?");
			if(p.alive==false){
				System.out.println("Did I get here?2");
				p.setBackground(Color.BLACK);
				p.setOpaque(true);
				p.setText("X");
				p.revalidate();
				p.alive = true;
				
			}
			else{
				p.setBackground(b1.getBackground());
				p.setText("");
				p.alive = false;
			}
		}
	}

}
