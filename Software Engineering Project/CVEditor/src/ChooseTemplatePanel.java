import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.Border;
import javax.swing.border.LineBorder;

public class ChooseTemplatePanel extends JPanel{
	
	private BoxLayout boxLayout;
	
	ChooseTemplatePanel(){
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		setBackground(new Color(81, 93, 107));
		
		JLabel tLabel = new JLabel("Choose Template", JLabel.CENTER);
		tLabel.setForeground(Color.white);
        tLabel.setAlignmentX(CENTER_ALIGNMENT);
        tLabel.setMaximumSize(new Dimension(Integer.MAX_VALUE, tLabel.getPreferredSize().height));
        tLabel.setBorder(BorderFactory.createMatteBorder(0, 0, 2, 0, Color.white));
        add(tLabel);
		
        
        JPanel vertical = new JPanel();
        vertical.setLayout(new BoxLayout(vertical, BoxLayout.Y_AXIS));
		vertical.setOpaque(false);
        
		JPanel templates = new JPanel();
		templates.setOpaque(false);
		templates.setLayout(new BoxLayout(templates, BoxLayout.X_AXIS));
        templates.add(new TemplatePanel("chronological.jpg"));
        templates.add(new TemplatePanel("functional.jpg"));
        templates.add(new TemplatePanel("combined.jpg"));
        
        vertical.add(templates);
        add(vertical);
	}
	
	
}
