package player;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.BoxLayout;
import javax.swing.JRadioButton;
import javax.swing.JSplitPane;
import java.awt.Button;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;

public class gui extends JFrame{

	private static final long serialVersionUID = 1L;
	
	public static void main(String[] args){
		gui gui = new gui();
		gui.setDefaultCloseOperation(3);
		gui.setVisible(true);
	}
	
	public gui(){
		setLocationByPlatform(true);
	    setSize(487, 364);
	    setTitle("IEDCS Player");
	    getContentPane().setLayout(new GridLayout(1, 2, 0, 0));
	    
	    Button button_1 = new Button("New button");
	    button_1.addActionListener(new ActionListener() {
	    	public void actionPerformed(ActionEvent e) {
	    	}
	    });
	    getContentPane().add(button_1);
	    
	    Button button = new Button("New button");
	    getContentPane().add(button);
	    
	    JPanel background = new JPanel();
	    background.setLayout(new GridLayout(3, 3));
	}
	
	protected void alert(String message){
		JOptionPane.showMessageDialog(getContentPane(), message);
	}
	

}