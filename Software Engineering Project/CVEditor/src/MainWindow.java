import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.KeyStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.FlowLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.BoxLayout;


public class MainWindow {

	private static JFrame frame;
	private static int screenWidth, screenHeight;
	private static JMenuItem saveMenuItem;
	private static JMenuItem saveAsMenuItem;
	private static MenuActionListener menuActionListener;
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					MainWindow window = new MainWindow();
					window.frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the application.
	 */
	public MainWindow() {
		initialize();
	}
	
	public void initScreenSize(){
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = (int)screenSize.getWidth();
		screenHeight = (int)screenSize.getHeight();
	}
	

	/**
	 * Initialize the contents of the frame.
	 */
	private void initialize() {
		initScreenSize();
		frame = new JFrame();
		frame.setBounds(100, 100, 1024, 520);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setResizable(false);
		menuActionListener = new MenuActionListener();
		frame.setJMenuBar(createMenu());		
	}
	
	private JMenuBar createMenu(){
		JMenuBar menuBar;
		JMenu menu;
		JMenuItem menuItem;
		
		menuBar = new JMenuBar();
		
		menu = new JMenu("File");
		menuBar.add(menu);

		menuItem = new JMenuItem("New", resizeImage("newFile.png", 25, 25));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_N, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(menuActionListener);
		menu.add(menuItem);
		
		menuItem = new JMenuItem("Open", resizeImage("open.png", 25, 25));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_O, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(menuActionListener);
		menu.add(menuItem);
		
		saveMenuItem = new JMenuItem("Save", resizeImage("save.png", 25, 25));
		saveMenuItem.setEnabled(false);
		saveMenuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_S, ActionEvent.CTRL_MASK));
		saveMenuItem.addActionListener(menuActionListener);
		menu.add(saveMenuItem);
		
		saveAsMenuItem = new JMenuItem("Save As", resizeImage("saveAs.png", 25, 25));
		saveAsMenuItem.setEnabled(false);
		saveAsMenuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_A, ActionEvent.CTRL_MASK));
		saveAsMenuItem.addActionListener(menuActionListener);
		menu.add(saveAsMenuItem);
		
		menuItem = new JMenuItem("Exit", resizeImage("exit.png", 25, 25));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_E, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(menuActionListener);
		menu.add(menuItem);
		
		menu = new JMenu("Edit");
		menuBar.add(menu);
		
		menuItem = new JMenuItem("Merge", resizeImage("compare.png", 25, 25));
		menuItem.setAccelerator(KeyStroke.getKeyStroke(
		        KeyEvent.VK_M, ActionEvent.CTRL_MASK));
		menuItem.addActionListener(menuActionListener);
		menu.add(menuItem);
		
		return menuBar;
	}
	
	public static ImageIcon resizeImage(String imageName, int width, int height){
		Image img = null;
			
		img = ResourceLoader.getImage(imageName);
		Image dimg = img.getScaledInstance(width, height,Image.SCALE_SMOOTH);
		
		return new ImageIcon(dimg);
	}
	
	public static int getScreenWidth(){
		return screenWidth;
	}
	
	public static int getScreenHeight(){
		return screenHeight;
	}
	
	public static JFrame getMainFrame(){
		return frame;
	}
	
	public static MenuActionListener getMenuActionListener(){
		return menuActionListener;
	}
	
	public static void setSaveEnabled(boolean enabled){
		saveMenuItem.setEnabled(enabled);
	}
	
	public static void setSaveAsEnabled(boolean enabled){
		saveAsMenuItem.setEnabled(enabled);
	}
	
	public static JMenuItem getSaveAsMenuItem(){
		return saveAsMenuItem;
	}
}
