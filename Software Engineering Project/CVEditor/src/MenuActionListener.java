import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.filechooser.FileNameExtensionFilter;

import compare.CVCompare;
import cv.CV;
import cv.CustomCV;
import input.FileLoader;
import input.LATEXLoader;
import input.TXTLoader;
import output.FileExporter;
import output.LATEXExporter;
import output.TXTExporter;

public class MenuActionListener implements ActionListener {
	private PanelManager panelManager = PanelManager.getInstance();
	private TXTExporter txtExporter = new TXTExporter();
	private LATEXExporter latexExporter = new LATEXExporter();
	private TXTLoader txtLoader = new TXTLoader();
	private LATEXLoader latexLoader = new LATEXLoader();
	private FileExporter saveExporter;
	private FileLoader fileLoader;
	private String savePath = "";
	private CVCompare cvCompare = new CVCompare();
	 
	public void actionPerformed(ActionEvent e) {
		if(e.getActionCommand().equals("New")){
			panelManager.setFrameSize(1024, 520);
			panelManager.onClickNew();
			MainWindow.setSaveEnabled(false);
			MainWindow.setSaveAsEnabled(false);
		}
		else if(e.getActionCommand().equals("Open")){
			selectFile("Open", true);
		}
		else if(e.getActionCommand().equals("Save")){
			if (saveExporter == null){
				ActionEvent event = new ActionEvent(MainWindow.getSaveAsMenuItem(), 0, "Save As");
				actionPerformed(event);
				return;
			}
			PanelManager.getInstance().getEditTemplatePanel().updateSections();
			saveExporter.writeFile(PanelManager.getInstance().getEditTemplatePanel().getCV(), savePath);
		}
		else if(e.getActionCommand().equals("Save As")){
			PanelManager.getInstance().getEditTemplatePanel().updateSections();
			JFileChooser fileChooser = new JFileChooser();
			fileChooser.setDialogTitle("Save as");  
			
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".txt", "txt"));
			fileChooser.addChoosableFileFilter(new FileNameExtensionFilter(".ltx", "ltx"));
			
			int userSelection = fileChooser.showSaveDialog(MainWindow.getMainFrame());
			if (userSelection == JFileChooser.APPROVE_OPTION) {
			    File fileToSave = fileChooser.getSelectedFile();
			    String absolutePath = fileToSave.getAbsolutePath();
			    String description = fileChooser.getFileFilter().getDescription();
			    if(description.equals(".txt")){
			    	txtExporter.writeFile(PanelManager.getInstance().getEditTemplatePanel().getCV(), absolutePath + description);
			    	saveExporter = txtExporter; 
			    }
			    else{
			    	latexExporter.writeFile(PanelManager.getInstance().getEditTemplatePanel().getCV(), absolutePath + description);
			    	saveExporter = latexExporter;
			    }
			    savePath = absolutePath + description;
			}
		}
		else if(e.getActionCommand().equals("Exit")){
			exit();
		}
		else if(e.getActionCommand().equals("Merge")){
			boolean correctSelection;
			OpenFileModified openFileModified1 = selectFile("Select file 1", false);
			if (!openFileModified1.getCorrectOpen()) return;
			CV firstCV = new CustomCV(fileLoader.getSectionList());
			OpenFileModified openFileModified2 = selectFile("Select file 2", false);
			if (!openFileModified2.getCorrectOpen()) return;
			CV secondCV = new CustomCV(fileLoader.getSectionList());
			if(!cvCompare.compareCVs(firstCV, secondCV, openFileModified1.getLastModifiedDate(), openFileModified2.getLastModifiedDate())){
				JOptionPane.showMessageDialog(MainWindow.getMainFrame(),
            		    "Different type of CV templates",
            		    "Merge Failed",
            		    JOptionPane.ERROR_MESSAGE);
			}
		}
	}
	
	private void exit(){
		System.exit(0);
	}
	
	private OpenFileModified selectFile(String dialogTitle, boolean pathToBeChanged){
		JFileChooser fileChooser = new JFileChooser();
		fileChooser.setDialogTitle(dialogTitle);
		int returnVal = fileChooser.showOpenDialog(MainWindow.getMainFrame());
		File file = null;
		OpenFileModified openFileModified = new OpenFileModified();
		
		if (returnVal == JFileChooser.APPROVE_OPTION) {
            file = fileChooser.getSelectedFile();
            String fileName = file.getName();
            String extension = fileName.split("\\.")[1];
            boolean load = false;
            
            if(!(extension.equals("txt") || extension.equals("ltx"))){
            	JOptionPane.showMessageDialog(MainWindow.getMainFrame(),
            		    "Only TXT or LATEX files are supported",
            		    "Wrong format",
            		    JOptionPane.ERROR_MESSAGE);
            	openFileModified.setCorrectOpen(false);
            	return(openFileModified);
            }
            else{
            	if(extension.equals("txt")){
            		load = txtLoader.loadFile(txtLoader, file.getAbsolutePath());
            		fileLoader = txtLoader;
            	}
            	else{
            		load = latexLoader.loadFile(latexLoader, file.getAbsolutePath());
            		fileLoader = latexLoader;
            	}
            	if (pathToBeChanged) {
            		savePath = file.getAbsolutePath();
            	}
            	
            	if(!load){
    				JOptionPane.showMessageDialog(MainWindow.getMainFrame(),
                		    "Wrong file format",
                		    "Load Failed",
                		    JOptionPane.ERROR_MESSAGE);
    				openFileModified.setCorrectOpen(false);
                	return(openFileModified);
    			}
    			else if (pathToBeChanged){
    				PanelManager.getInstance().onClickOpen(fileLoader.getSectionList());
    			}
            }
        }
		else if(returnVal == JFileChooser.CANCEL_OPTION){
			openFileModified.setCorrectOpen(false);
        	return(openFileModified);
		}
		openFileModified.setCorrectOpen(true);
		openFileModified.setLastModifiedDate(file);
    	return(openFileModified);
	}
}