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
JPanel p5 = new JPanel(new GridLayout(3,1));//PreMade Set
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
JButton b4 = new JButton("Expand Set");
JButton b5 = new JButton("Save");
JButton b6 = new JButton("Clear");
JButton b7 = new JButton("Reset");
JTextField t1 = new JTextField("N=0000");
JTextField t2 = new JTextField("-1");
Cell[][] ppl = new Cell[32][32];
//Point[] compress = new Point[1024]; <-- will eventually use for optimization at some point
String s[] = {"--Select--", "Space Ship","Rotator"};
JComboBox c1 = new JComboBox(s);
JScrollBar sb = new JScrollBar();
JSplitPane p3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,p5,p6);
int count = 0;
byte[] ini = new byte[128];
boolean isOn = false;
boolean begin = true;
	public GoL(){
		//Timer ourTimer = new Timer("get");
		b1.addActionListener(new playButton());
		b2.addActionListener(new stopButton());
		b3.addActionListener(new aboutButton());
		b6.addActionListener(new clearButton());
		b5.addActionListener(new saveButton());
		b7.addActionListener(new resetButton());
		p9.add(p3,BorderLayout.CENTER);
		p9.add(p4, BorderLayout.SOUTH);
		
		p2.setMaximumSize(new Dimension(800,200));
		p2.setSize(800, 200);
		
		t1.setEditable(false);
		p3.setMinimumSize(new Dimension(100,50));
		p7.add(t1);
		p7.add(b1);
		p7.add(b2);
		p7.add(b6);
		p7.add(b5);
		p7.add(b7);
		p7.add(b3);
		
		p8.add(l3);
		p8.add(t2);
		p6.add(p7);
		p6.add(p8);
		p5.add(l2);
		p5.add(c1);
		p5.add(b4);
		
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
		boolean after;
		Cell(){
			//b = new JButton();
		}
	}
	public class userClick implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(isOn==false){
			Cell p = (Cell)e.getSource();
			
			if(p.alive==false){
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
	public class aboutButton implements ActionListener{

		@Override
		public void actionPerformed(ActionEvent e) {
			JOptionPane.showMessageDialog(null,
					"Conway's Game of Life\n\n"
					+ "It is a 'game' solely determined by its initial state\n"
					+ "It follow some basic rules.\n"
					+ "A cell has eight neighbors.\n"
					+ "A cell is either alive or dead.\n"
					+ "Any live cell with either 2 or 3 neighbors lives on.\n"
					+ "Any live cell with < 2 neighbors dies.\n"
					+ "Any live cell with > 3 neighbors dies.\n"
					+ "Any dead cell with exactly 3 neighbors comes to life.\n"
					+ "\nImagine a System in which"
					+ "\nThe Play button starts the clycle and the algoritm and the stop button"
					+ "\npauses the algoritm at which point the play button"
					+ "Stop at N: Option allows the user to specify a specific "
					+ "\nGeneration(N) at which the program will stop"
					
							
					
					
					
					);/*"Conway's Game of Life:\n"
					+ "\nImageine a System in which\nThe Play button starts the clycle and the algoritm and the stop button"
					+ "\npauses the algoritm at which point the play button"
					+ "Stop at N: Option allows the user to specify a specific "
					+ "\nGeneration(N) at which the program will stop"
					+ "\n");*/
			
		}
		
	}
	public class AlgoPlay implements Runnable{

		public void run() {
			
			while(isOn){
				
				algoritm();
				try {
					Thread.sleep(200);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
		
	}
	public class playButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			p2.setEnabled(false);
			isOn = true;
			/* Save Feture on the frits
			String bigS = "";
			for(int i=0;i<ppl.length;i++){
				for(int j=0;j<ppl[0].length;j++){
					if(ppl[i][j].alive==true){
						bigS = bigS + "1";
					}
					else{
						bigS = bigS + "0";
					}
				}
			}
			String[] manyS = bigS.split("(?<=\\G........)");
			for(int i=0;i<manyS.length;i++){
				ini[i] = Byte.parseByte(manyS[i],2);
			}
			*/
			(new Thread(new AlgoPlay())).start();
			//algoritm();
			
			
			
		}
		
	}
	public void algoritm(){
		for(int i=0;i<ppl.length;i++){
			for(int j=0;j<ppl[0].length;j++){
				if((i>0&&j>0)&&(i<(ppl.length-1)&&j<(ppl[0].length-1))){
					if(ppl[i-1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][j].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][j].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][j+1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j+1].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][j+1].alive==true){
						count = count + 1;
					}
					
					
				}//first case - Without Wrapping
				else if(i==0 && j==0){//Top-Right
					
				}
				else if(i>0 && j==0 && i<ppl.length-1){//Top-Mid
					
				}
				else if(i==ppl.length-1 && j==0){//Top-Left
					
				}
				else if(i==0&& j>0 && j < ppl[0].length){//Middle-Left
					
				}
				else if(i==(ppl.length-1)&& j>0 && j<ppl.length-1){//Middle-Right
					
				}
				else if(i==0 && j==(ppl[0].length-1)){//Bottom-Left
					
				}
				else if(j==(ppl[0].length-1) && i>0 && i<ppl.length-1){//Bottom-Mid
					
				}
				else if(i==(ppl.length-1)&&j==(ppl[0].length-1)){//Bottom-Right
					
				}
				if(ppl[i][j].alive==true){
					if(count<2 || count>3){
						ppl[i][j].setText("");
						ppl[i][j].after = false;
					}
					else{
						ppl[i][j].after = true;
					}
					
				}
				else{
					if(count==3){
						ppl[i][j].after= true;
						ppl[i][j].setText("X");
					}
				}
				count = 0;
				
			}//end of inner for-loop
		}//end of nested for-loop
		for(int i=0;i<ppl.length;i++){
			for(int j=0;j<ppl[0].length;j++){
				if(ppl[i][j].after){
					ppl[i][j].alive=true;
					ppl[i][j].after = false;
				}
				else{
					ppl[i][j].alive=false;
					ppl[i][j].after = false;
				}
			}
		}
		t1.setText("N="+((Integer.parseInt(t1.getText().substring(2)))+1));
	}
	public class stopButton implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			isOn = false;
			
		}
		
	}
	public class clearButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			isOn = false;
			for(int i=0;i<ppl.length;i++){
				for(int j=0;j<ppl[0].length;j++){
					ppl[i][j].setText("");//TODO
					ppl[i][j].alive = false;
					ppl[i][j].after = false;
				}
			}
		}
		
	}
	public class resetButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			(new clearButton()).actionPerformed(e);
			t1.setText("N=0000");
			begin = true;
		}
		
	}
	public class saveButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			
		}	
	}

}
