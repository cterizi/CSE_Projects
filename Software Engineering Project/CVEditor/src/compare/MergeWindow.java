package compare;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import input.Field;
import section.Section;

public class MergeWindow extends JFrame{
	private JFrame mergeWindow;
	private JPanel panel = new JPanel();
	private JPanel mergePanel = new JPanel();
	private ArrayList<MergeSectionPanel> mergeSectionPanels = new ArrayList<MergeSectionPanel>();

	public MergeWindow(){
		mergeWindow = new JFrame("Merge");
		mergeWindow.setBounds(250, 10, 750, 700);
		mergeWindow.setResizable(false);
		JButton mergeButton = new JButton("Merge");
		mergeButton.setBackground(new Color(255,105,180));
		mergeButton.addActionListener(new ActionListener()
		{
		  public void actionPerformed(ActionEvent e)
		  {
		    merge();
		  }
		});
		panel.setLayout(new BorderLayout());
		mergePanel.setLayout(new BoxLayout(mergePanel, BoxLayout.PAGE_AXIS));
		panel.add(mergePanel, BorderLayout.CENTER);
		panel.add(mergeButton, BorderLayout.SOUTH);
		mergeWindow.add(panel);
	}
	
	public void addMergeSection(Section section, ArrayList<Field> fields){
		mergeSectionPanels.add(new MergeSectionPanel(section, fields));
		mergePanel.add(mergeSectionPanels.get(mergeSectionPanels.size()-1));
	}
	
	public void setVisible(boolean visible){
		mergeWindow.revalidate();
		mergeWindow.repaint();
		mergeWindow.setVisible(visible);
	}
	
	public void merge(){
		for (MergeSectionPanel mergeSectionPanel : mergeSectionPanels){
			mergeSectionPanel.getSection().updateFromFields(mergeSectionPanel.getFields());
		}
		mergeWindow.dispose();
	}
}