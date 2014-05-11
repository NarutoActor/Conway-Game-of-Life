import java.awt.*;
import java.awt.Dialog.ModalityType;
import java.awt.event.*;
import java.io.*;
import javax.swing.*;
import javax.swing.border.TitledBorder;


public class GoL extends JFrame{
JPanel p1 = new JPanel(new BorderLayout());
JPanel p2 = new JPanel(new GridLayout(32,32));//Table Panel
//JPanel p3 = new JPanel();//Tool Panel
JPanel p4 = new JPanel();//Signiture
JPanel p5 = new JPanel(new GridLayout(4,1));//PreMade Set
JPanel p6 = new JPanel(new GridLayout(2,1));
JPanel p7 = new JPanel();//front
JPanel p8 = new JPanel();//back
JPanel p9 = new JPanel(new BorderLayout());//Padding
JLabel l1 = new JLabel((char)169 +"Lucas Rivera & Hans Kiessler 2014 for Rutgers University-HACK RU");
JLabel l2 = new JLabel("PreMade Sets");
JLabel l3 = new JLabel("Stop At N=:");
JLabel l4 = new JLabel("\tTime Delay:");
JLabel l5 = new JLabel("ms");
JButton b1 = new JButton("Play");
JButton b2 = new JButton("Stop");
JButton b3 = new JButton("About");
JButton b4 = new JButton("Expand Set");
JButton b5 = new JButton("Save");
JButton b6 = new JButton("Clear");
JButton b7 = new JButton("Reset");
JButton b8 = new JButton("Load");
JTextField t1 = new JTextField("N=0000");
JTextField t2 = new JTextField("-1");
JTextField t3 = new JTextField("200");
JTextField ti2;
JTextField ti1;
JRadioButton rb1;
JRadioButton rb2;
JRadioButton rb3;
JRadioButton rb4;
JDialog di;
Cell[][] ppl = new Cell[32][32];
//Point[] compress = new Point[1024]; <-- will eventually use for optimization at some point(pun was not inteded)
String s[] = {"--Select--"};
JComboBox c1 = new JComboBox(s);
JScrollBar sb = new JScrollBar();
JSplitPane p3 = new JSplitPane(JSplitPane.HORIZONTAL_SPLIT,p5,p6);
int count = 0;
byte[] ini = new byte[128];
boolean isOn = false;
boolean begin = true;
long sleep = 200;
String[] manyS;
File dir = new File("design");
	public GoL(){
		try {
			DataInputStream in = new DataInputStream(new FileInputStream("design/log.gol"));
			in.close();
			findFiles();
			
		} catch (FileNotFoundException e1) {
			try{
				//File dir = new File("design");
				dir.mkdir();
				DataOutputStream out = new DataOutputStream(new FileOutputStream("design/log.gol"));
				out.writeUTF("Hay");
				out.close();
			}
			catch(FileNotFoundException e2) {
				e1.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		b1.addActionListener(new playButton());
		b2.addActionListener(new stopButton());
		b3.addActionListener(new aboutButton());
		b6.addActionListener(new clearButton());
		b5.addActionListener(new saveButton());
		b7.addActionListener(new resetButton());
		t3.addActionListener(new timeDelay());
		b8.addActionListener(new loadButton());
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
		p8.add(l4);
		p8.add(t3);
		p8.add(l5);
		p6.add(p7);
		p6.add(p8);
		p5.add(l2);
		p5.add(c1);
		p5.add(b8);
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
		
		(new saveButton()).actionPerformed(new ActionEvent(new Object(),0,""));
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
	public void reloadList(){
		c1 = new JComboBox(s);
		//c1.validate();
		//this.validate();

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
				if((Long.parseLong(t1.getText().substring(2)))==(Long.parseLong(t2.getText()))){
					isOn = false;
					break;
				}
				try {
					Thread.sleep(sleep);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
	}
	public class playButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			sleep = Long.parseLong(t3.getText());
			if(!isOn){
			p2.setEnabled(false);
			isOn = true;
			if(begin){
				begin = false;
				downloadBored();
			}
			(new Thread(new AlgoPlay())).start();
			//algoritm();
			}
			
			
		}
		
	}

	
	public void downloadBored(){
		///* Save Feture on the frits
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
		manyS = bigS.split("(?<=\\G........)");
		for(int i=0;i<manyS.length;i++){
			ini[i] = (byte)(Integer.parseInt(manyS[i],2));
		}
		/*
		for(int i=0;i<ini.length;i++){
			System.out.println(manyS[i]);
		}*/
		//need to spit out to file
	}
	public void findFiles(){
		File[] matches = dir.listFiles(new FilenameFilter()
		{
		  public boolean accept(File dir, String name)
		  {
		     return name.endsWith(".gol");
		  }
		});
		
		if(matches.length>0){
			s = new String[matches.length+1];
		}
		else{
			s = new String[1];
		}
		s[0] = "--Select--";
		for(int i=1;i<s.length;i++){
			s[i] = matches[i-1].getName().replaceAll(".gol","");
		}
		for(int k=0;k<s.length;k++){
			System.out.println(s[k]);
		}
		c1 = new JComboBox(s);
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
					if(ppl[ppl.length-1][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[0][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[1][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[ppl.length-1][0].alive==true){
						count = count + 1;
					}
					if(ppl[1][0].alive==true){
						count = count + 1;
					}
					if(ppl[ppl.length-1][1].alive==true){
						count = count + 1;
					}
					if(ppl[0][1].alive==true){
						count = count + 1;
					}
					if(ppl[1][1].alive==true){
						count = count + 1;
					}
				}
				else if(i>0 && j==0 && i<ppl.length-1){//Top-Mid
					if(ppl[i-1][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[i][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][ppl[0].length-1].alive==true){
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
				}
				else if(i==ppl.length-1 && j==0){//Top-Left
					if(ppl[i-1][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[i][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[0][ppl[0].length-1].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][0].alive==true){
						count = count + 1;
					}
					if(ppl[0][0].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][1].alive==true){
						count = count + 1;
					}
					if(ppl[i][1].alive==true){
						count = count + 1;
					}
					if(ppl[0][1].alive==true){
						count = count + 1;
					}
				}
				else if(i==0&& j>0 && j < ppl[0].length-1){//Middle-Left
					if(ppl[ppl.length-1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[ppl.length-1][j].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][j].alive==true){
						count = count + 1;
					}
					if(ppl[ppl.length-1][j+1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j+1].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][j+1].alive==true){
						count = count + 1;
					}
				}
				else if(i==(ppl.length-1)&& j>0 && j<ppl.length-1){//Middle-Right
					if(ppl[i-1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[0][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][j].alive==true){
						count = count + 1;
					}
					if(ppl[0][j].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][j+1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j+1].alive==true){
						count = count + 1;
					}
					if(ppl[0][j+1].alive==true){
						count = count + 1;
					}
				}
				else if(i==0 && j==(ppl[0].length-1)){//Bottom-Left
					if(ppl[ppl.length-1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[0][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[ppl.length-1][j].alive==true){
						count = count + 1;
					}
					if(ppl[1][j].alive==true){
						count = count + 1;
					}
					if(ppl[ppl.length-1][0].alive==true){
						count = count + 1;
					}
					if(ppl[0][0].alive==true){
						count = count + 1;
					}
					if(ppl[1][0].alive==true){
						count = count + 1;
					}
				}
				else if(j==(ppl[0].length-1) && i>0 && i<ppl.length-1){//Bottom-Mid
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
					if(ppl[i-1][0].alive==true){
						count = count + 1;
					}
					if(ppl[i][0].alive==true){
						count = count + 1;
					}
					if(ppl[i+1][0].alive==true){
						count = count + 1;
					}
				}
				else if(i==(ppl.length-1)&&j==(ppl[0].length-1)){//Bottom-Right
					if(ppl[i-1][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[0][j-1].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][j].alive==true){
						count = count + 1;
					}
					if(ppl[0][j].alive==true){
						count = count + 1;
					}
					if(ppl[i-1][0].alive==true){
						count = count + 1;
					}
					if(ppl[i][0].alive==true){
						count = count + 1;
					}
					if(ppl[0][0].alive==true){
						count = count + 1;
					}
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
		
		t1.setText("N="+((Long.parseLong(t1.getText().substring(2)))+1));
		
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
			if(begin){
				downloadBored();
			}
			else{
			di = new JDialog ();
    		di.setModal (true);
    		di.setAlwaysOnTop (true);
    		di.setModalityType (ModalityType.APPLICATION_MODAL);
    		JPanel pi = new JPanel(new BorderLayout());
    		JPanel pi2 = new JPanel(new GridLayout(2,1));
    		JPanel pi3 = new JPanel(new GridLayout(5,1));
    		JPanel pi4 = new JPanel();
    		JPanel pi5 = new JPanel(new GridLayout(1,1));
    		JPanel pi6 = new JPanel(new GridLayout(1,1));
    		JLabel li1 = new JLabel("File Name:");
    		JLabel li2 = new JLabel("Broswer Path:");
    		JTextArea li3 = new JTextArea(""
    				+ "Chose to save the current state that your bored is in,\n"
    				+ " or save the intial state that, that yeild such wonderful\n"
    				+ "results!\n"
    				+ "\n"
    				+ "It is recamended to use the default path locations,\n"
    				+ " which is the realitive path to where the program\n"
    				+ "(Game of Life) is located\n"
    				+ "However, If you wish you can save files else where\n"
    				+ "You just have to import them using the import\n"
    				+ "feture\n"
    				+ "\n"
    				+ "Enjoy.");
    		ti1 = new JTextField("Example.gol");
    		ti2 = new JTextField("C:/PC/Example.gol");
    		JButton bi1 = new JButton("Browse");
    		JButton bi2 = new JButton("Save");
    		rb1 = new JRadioButton("Initial Bored",true);
    		rb2 = new JRadioButton("Current Bored");
    		rb3 = new JRadioButton("Realitive Path",true);
    		rb4 = new JRadioButton("Absolute Path");
    		radioChoice ActionList = new radioChoice();
    		rb3.addActionListener(ActionList);
    		rb4.addActionListener(ActionList);
    		bi1.addActionListener(new browserButton());
    		bi2.addActionListener(new saveWriter());
    		ButtonGroup g1 = new ButtonGroup();
    		ButtonGroup g2 = new ButtonGroup();
    		g1.add(rb1);
    		g1.add(rb2);
    		g2.add(rb3);
    		g2.add(rb4);
    		pi5.add(rb1);
    		pi5.add(rb2);
    		pi6.add(rb3);
    		pi6.add(rb4);
    		pi2.add(pi5);
    		pi2.add(pi6);
    		TitledBorder tit = BorderFactory.createTitledBorder("Save Options");
    		pi2.setBorder(tit);
    		TitledBorder tit2 = BorderFactory.createTitledBorder("Discription");
    		pi2.setBorder(tit2);
    		li3.setEditable(false);
    		li3.setBorder(tit2);
    		ti2.setEnabled(false);
    		li3.setBackground(b1.getBackground());
    		pi3.add(li1);
    		pi3.add(ti1);
    		pi3.add(li2);
    		pi3.add(ti2);
    		pi4.add(bi1);
    		pi4.add(bi2);
    		pi3.add(pi4);
    		pi.add(pi2,BorderLayout.NORTH);
    		pi.add(pi3,BorderLayout.CENTER);
    		pi.add(li3, BorderLayout.SOUTH);
    		di.add(pi);
    		di.setEnabled(true);
    		di.setLocationRelativeTo(null);
    		di.setSize(352,476);//340
    		di.setMinimumSize(new Dimension(352,476));
    		di.setVisible(true);
			
			}
		}	
	}
	public void writeBored(){
		try {
			if(rb1.isSelected() || rb2.isSelected()){
				FileOutputStream s = new FileOutputStream("design/"+ti1.getText());
				s.write(ini);
				s.close();
			}
			else{
				FileOutputStream s = new FileOutputStream(ti2.getText());
				s.write(ini);
				s.close();
			}
		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	public class radioChoice implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(rb3.isSelected()){
				ti2.setEnabled(false);
			}
			else if(rb4.isSelected()){
				ti2.setEnabled(true);
			}
			
		}
	}
	public class browserButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(ti2.isEnabled()){

	    		di.setAlwaysOnTop (false);
				JFileChooser j = new JFileChooser();
				j.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				j.setDialogTitle("Select target directory");
				int returnVal = j.showOpenDialog(null);

		        if (returnVal == JFileChooser.APPROVE_OPTION) {
		            File file = j.getSelectedFile();
		            ti2.setText(file.getAbsolutePath()+"/"+ti1.getText());
		        } else {
		           System.out.print("Open command cancelled by user.");
		        }

	    		di.setAlwaysOnTop (true);
			}
			
		}	
	}
	public class saveWriter implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(rb1.isSelected()&&!ti2.isEnabled()){//Ini, rel
				writeBored();
			}
			else if(rb1.isSelected()&&rb4.isSelected()){//Ini,abso
				writeBored();
			}
			else if(rb2.isSelected()&&rb3.isSelected()){//Cur, rel
				downloadBored();
				writeBored();
			}
			else if(rb2.isSelected()&&rb4.isSelected()){//cur,absol
				downloadBored();
				writeBored();
			}
			findFiles();
			reloadList();
			di.dispose();
			
			
		}
	}
	public class timeDelay implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			sleep = Long.parseLong(t3.getText());
			
		}
		
	}
	public class loadButton implements ActionListener{
		public void actionPerformed(ActionEvent e) {
			if(c1.getSelectedIndex()!=0){
				isOn = false;
			System.out.println(c1.getSelectedItem().toString());
			try {
				FileInputStream in = new FileInputStream("design/"+c1.getSelectedItem().toString()+".gol");
				in.read(ini);
				in.close();
				//convert byte->string binary
				for(int i=0;i<ini.length;i++){
					manyS[i] = String.format("%8s", Integer.toBinaryString(ini[i] & 0xFF)).replace(' ', '0');
					//System.out.println(manyS[i]);
				}
				int m = 0, p=0, q=0;
				for(int i=0;i<manyS.length;i++){
					for(int j=0;j<manyS[i].length();j++){
						m= i*8+j;
						p = m/32;
						q = m%32;
						ppl[p][q].alive=true;
						ppl[p][q].setText("X");
						if(Integer.parseInt(""+manyS[i].charAt(j))==1){//1
							ppl[p][q].alive=true;
							ppl[p][q].setText("X");
						}
						else{//0
							ppl[p][q].alive=false;
							ppl[p][q].setText("");
						}
					}
				}
			} catch (FileNotFoundException e1) {
				// TODO Auto-generated catch block
				//You deleated a file you BAD BOY!
				e1.printStackTrace();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			
		}
		}
		
	}

}
