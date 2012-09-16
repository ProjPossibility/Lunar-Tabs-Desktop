package gui.dep;


import java.awt.Component;
import java.awt.FileDialog;
import java.awt.Frame;

import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

public class AccessibilityAPI {

	/**
	 * Helper for adding accessible labels to gui elements
	 * @param c
	 * @param label
	 */
	public static void setAccessibleLabel(JComponent c, String label) {
		c.getAccessibleContext().setAccessibleName(label);
		c.getAccessibleContext().setAccessibleDescription(label);
		c.setToolTipText(label);
	}

	@SuppressWarnings("deprecation")
	public static void showAccessibleErrorDialog(String title, String message) {
		JOptionPane pane=new JOptionPane(message,JOptionPane.ERROR_MESSAGE);
		JDialog dialog=pane.createDialog(null,title);
		Component[] comps=pane.getComponents();
		for(int i=0;i<comps.length;i++){
		    if(comps[i] instanceof JPanel){
		        Component[] children=((JPanel)comps[i]).getComponents();
		        for(int j=0;j<children.length;j++){
		            if(children[j] instanceof JButton){
		                JButton button=(JButton)children[j];
		                button.getAccessibleContext().setAccessibleName(message+" "
		                     +button.getText());
		            }
		        }
		    }
		}
		dialog.show();
	}
		
    /**
     * Show a open file dialog box
     * @return
     */
    public static String showAccessibleFileDialog(Frame f)
    {
        FileDialog file = new FileDialog (f, "File Chooser: Choose Tab file to load", FileDialog.LOAD);
	    FileNameExtensionFilter filter = new FileNameExtensionFilter("Guitar Pro and Power Tab Files", "gp1", "gp2", "gp3", "gp4", "gp5", "ptb");
	    file.setFilenameFilter(filter);
        file.show(); // Blocks
        return file.getDirectory() + file.getFile();
    }
    
}
