import java.awt.BorderLayout;
import java.util.ArrayList;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JPanel;

import section.Section;

public class PanelManager {
	
	private JFrame mainFrame;
	private ChooseTemplatePanel chooseTemplatePanel;
	private EditTemplatePanel editTemplatePanel;
	private JPanel currentPanel;
	private static final PanelManager instance = new PanelManager(MainWindow.getMainFrame());
	
	public PanelManager(JFrame mainFrame){
		this.mainFrame = mainFrame;
		currentPanel = null;
	}
	
	public void onClickNew(){
		chooseTemplatePanel = new ChooseTemplatePanel();
		setCurrentPanel(chooseTemplatePanel);
	}
	
	public void onClickOpen(ArrayList<Section> sectionList){
		editTemplatePanel = new EditTemplatePanel("customCV", sectionList);
		setCurrentPanel(editTemplatePanel);
	}
	
	public void selectTemplate(String templateName){
		editTemplatePanel = new EditTemplatePanel(templateName, null);
		setCurrentPanel(editTemplatePanel);
	}
	
	public void onClickSaveButton(){
		
	}
	
	public void onClickMergeButton(){
		
	}
	
	public void setCurrentPanel(JPanel newPanel){
		
		mainFrame = MainWindow.getMainFrame();
		if(currentPanel != null){
			mainFrame.remove(currentPanel);
		}
		
		currentPanel = newPanel;
		mainFrame.add(newPanel, BorderLayout.CENTER);
		mainFrame.revalidate();
		mainFrame.repaint();
	}

	public void setFrameSize(int width, int height){
		mainFrame.setSize(width, height);
	}
	
	public static synchronized PanelManager getInstance(){
		return instance;
	}
	
	public EditTemplatePanel getEditTemplatePanel(){
		return(editTemplatePanel);
	}
}
