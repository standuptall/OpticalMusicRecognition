package it.unipv.computervision.albez;

import javax.swing.*;
import java.awt.Image;

@SuppressWarnings("serial")
public class ImageFrame extends JFrame {

	private final JLabel jlabel;
	
	public ImageFrame(String imageName) {
		jlabel = new JLabel(new ImageIcon(imageName));
		initFrame(imageName);
	}

	public ImageFrame(Image img, String title) {
		jlabel = new JLabel(new ImageIcon(img));
		initFrame(title);

	}

	private void initFrame(String title) {
		setTitle(title);
		getContentPane().add(jlabel);
		pack();
		setDefaultCloseOperation(defaultCloseOperation);
		setVisible(true);
	}
	
	public void setImage(Image img) {
		jlabel.setIcon(new ImageIcon(img));
	}
	
	public void setImage(String imageName) {
		jlabel.setIcon(new ImageIcon(imageName));
		pack();
		setTitle(imageName);
	}
	
	public Image getImage() {
		return ((ImageIcon) jlabel.getIcon()).getImage();
	}

	static int defaultCloseOperation = EXIT_ON_CLOSE;

	public static void main(String[] imageFileNames) {
		for (int i = 0; i < imageFileNames.length; i++) {
			new ImageFrame(imageFileNames[i]);
		}
	}

}
