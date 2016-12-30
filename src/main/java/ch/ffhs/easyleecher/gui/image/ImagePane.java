package ch.ffhs.easyleecher.gui.image;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.JPanel;

/**
 * Diese Klasse wird verwendet um die Banners einzublenden
 * 
 * @author pascal bieri
 */
@SuppressWarnings("serial")
public class ImagePane extends JPanel {
	private BufferedImage image;

	/**
	 * @param path
	 */
	public void loadimage(String path) {
		setOpaque(true);
		setBorder(BorderFactory.createLineBorder(Color.BLACK, 5));
		try {
			image = ImageIO.read(new File(path));
		} catch (IOException ioe) {
			System.out.println("Unable to fetch image.");
			ioe.printStackTrace();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#getPreferredSize()
	 */
	@Override
	public Dimension getPreferredSize() {
		return (new Dimension(image.getWidth(), image.getHeight()));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.JComponent#paintComponent(java.awt.Graphics)
	 */
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, this);
	}
}