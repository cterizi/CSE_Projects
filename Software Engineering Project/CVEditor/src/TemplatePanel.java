import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.MouseEvent;
import java.awt.event.MouseAdapter;


import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class TemplatePanel extends JPanel{
	
	private int panelWidth, panelHeight;
	private JButton selectButton;
	private String templateName;
	
	TemplatePanel(String templateName){
		
		this.templateName = templateName;
		
		panelWidth = (int)(0.2*MainWindow.getScreenWidth());
		panelHeight = (int)(0.5*MainWindow.getScreenHeight());
		
		setSize(new Dimension(panelWidth, panelHeight));
		selectButton = new JButton();
		selectButton.setBorder(null);
		selectButton.setIcon(MainWindow.resizeImage(templateName, panelWidth, panelHeight));
		selectButton.setHorizontalTextPosition(JButton.CENTER);
		selectButton.setVerticalTextPosition(JButton.TOP);
		selectButton.setForeground(Color.RED);
		
		add(selectButton);
		setOpaque(false);
		
		selectButton.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseEntered(MouseEvent arg0) {
				Border border = new LineBorder(new Color(40, 250, 144), 5);
				selectButton.setBorder(border);
				
				selectButton.setText(getTemplateName(templateName));
				invalidate();
				repaint();
			}
			
			@Override
			public void mouseExited(MouseEvent arg0) {
				selectButton.setBorder(null);
				selectButton.setText("");
				invalidate();
				repaint();
			}
			
			@Override
			public void mouseClicked(MouseEvent e) {
				PanelManager.getInstance().selectTemplate(templateName);
			}
		
		});
		
	}
	
	public String getTemplateName(String templateName){
		return templateName.split("\\.")[0].toUpperCase();
	}


}
