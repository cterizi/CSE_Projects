package compare;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

import input.Field;

public class CheckFieldPanel extends JPanel{
	private JCheckBox checkButton1;
	private JCheckBox checkButton2;
	
	public CheckFieldPanel(Field field){
		JLabel label = new JLabel(field.getLabel());
		label.setForeground(new Color(255, 20, 147));
		setLayout(new BoxLayout(this, BoxLayout.LINE_AXIS));
		add(label);
		add(Box.createRigidArea(new Dimension(5,0)));
		if(!field.getPassField()){
			add(new JLabel(field.getField()));
			return;
		}
		
		createFirstCheckBox(field);
		if (!field.getComparedFieldValue().equals(field.getField())){
			createSecondCheckBox(field);
		}
		else{
			checkButton1.addActionListener(new ActionListener(){
				@Override
				public void actionPerformed(ActionEvent arg0) {
					field.setKeepComparedField(!field.getKeepComparedField());
				}
			});
		}
	}
	
	public void createFirstCheckBox(Field field){
		checkButton1 = new JCheckBox(field.getComparedFieldValue());
		field.setKeepComparedField(true);
		checkButton1.setSelected(true);
		add(checkButton1);
	}
	
	public void createSecondCheckBox(Field field){
		checkButton2 = new JCheckBox(field.getField());
		add(Box.createRigidArea(new Dimension(5,0)));
		add(checkButton2);
		checkButton1.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkButton1.isSelected()){
					checkButton2.setSelected(false);
					field.setKeepComparedField(true);
				}
				else{
					checkButton2.setSelected(true);
					field.setKeepComparedField(false);
				}
				
				revalidate();
				repaint();
			}
		});
		checkButton2.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				if(checkButton2.isSelected()){
					checkButton1.setSelected(false);
					field.setKeepComparedField(false);
				}
				else{
					checkButton1.setSelected(true);
					field.setKeepComparedField(true);
				}
				revalidate();
				repaint();
			}
		});
	}
}
