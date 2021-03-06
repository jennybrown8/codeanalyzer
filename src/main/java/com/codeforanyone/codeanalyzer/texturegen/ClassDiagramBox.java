package com.codeforanyone.codeanalyzer.texturegen;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Rectangle;
import java.awt.RenderingHints;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

final class ClassDiagramBox extends Canvas {
    private static final long serialVersionUID = 1L;

    private static final double scaleFactor = 1.0;

    // Not too big here - this determines texture size and ultimately GPU performance in 3D view.
    // Size 12 and 8 is readable but fuzzy.  18 and 12 is more usable.
    private static final Font TITLE_FONT = new Font("Anonymous Pro", Font.BOLD, (int) (12 * scaleFactor));
    private static final Font SMALL_FONT = new Font("Anonymous Pro", Font.PLAIN, (int) (9 * scaleFactor));

    private static final double classNameVerticalPadding = 8 * scaleFactor;
    private static final double methodNameVerticalPadding = 2 * scaleFactor;
    private static final double sidePadding = 10 * scaleFactor;
    private static final double footerPadding = 10 * scaleFactor;

    // shortcut math for efficient vertical raster size planning
    private static int ESTIMATED_PIXELS_PER_ROW = (int)( 32 * scaleFactor); 

    protected String jclassName = "";
    protected String jpackageName = "";
    protected List<String> methods = new ArrayList<String>();

    public ClassDiagramBox(String packageName, String jclassName) {
	this.jpackageName = packageName;
	this.jclassName = jclassName;
    }

    public void add(String method) {
	methods.add(method);
    }

    public String getSuggestedPngFilename() {
	return getSuggestedPrefix() + ".png";
    }
    public String getSuggestedMetadataFilename() {
	return getSuggestedPrefix() + ".txt";
    }
    
    public String getSuggestedSubdirName() {
	String cleanPackage = jpackageName.replace(".", "-").replace("/", "-");
	String[] pieces = cleanPackage.split("-");
	if (pieces.length >= 3) {
	    return pieces[0] + "-" + pieces[1] + "-" + pieces[2];
	} else {
	    return cleanPackage;
	}
    }

    private String getSuggestedPrefix() {
	return jpackageName.replace(".", "-").replace("/", "-") + "_" + jclassName;
    }

    /**
     * Experimental - hash a string (like package name) into a consistent color
     * value.
     * 
     * @return A pale (pastel/white-ish) background color
     */
    private Color colorFromString(String text) {
	// We want a fairly pale color, so black text looks good.
	// So, the minimum possible value for each channel is 191 (light gray).
	// That leaves us 64 levels of variation for each channel (191 + 64 =
	// 255).
	// Which means needing 2^6 * 2^6 * 2^6 = 2^18 in total for all channels.
	int size18bit = 262144;

	// hash the text, and mod to the size of value range we need.
	int num = text.hashCode() % size18bit;

	// pick off 6 bits for each color channel via bitmask, and then adjust
	// its scale.
	int red = (num & 258048) >>> 12; // 0b111111000000000000
	int green = (num & 4032) >>> 6; // 0b000000111111000000
	int blue = (num & 63); // 0b000000000000111111

	// now each of (red, green, blue) should be safely within 0-64
	int baseBrightness = 175; // can be set to anything 0-191; above that clipping will occur in highlights.
	return new Color(baseBrightness + red, baseBrightness + green, baseBrightness + blue);
    }

    /**
     * This is the brains of the operation; draws the class name title and then
     * the list of methods beneath it. And then the class name title again at the bottom.
     */
    public void paint(Graphics g) {
	Rectangle r = getBounds();
	g.setColor(colorFromString(this.jpackageName));
	g.fillRect(0, 0, r.width, r.height);

	g.setColor(Color.BLACK);
	g.setFont(TITLE_FONT);
	double titleHeight = getSize(this.jclassName, g, TITLE_FONT).getHeight();
	g.drawString(this.jclassName, (int) (sidePadding), (int) (titleHeight));

	g.setFont(SMALL_FONT);
	double yloc = titleHeight + classNameVerticalPadding;

	for (String text : this.methods) {
	    Dimension methodDim = getSize(text, g, SMALL_FONT);
	    double height = methodDim.getHeight() + methodNameVerticalPadding;
	    yloc += height;
	    g.drawString(text, (int) (sidePadding), (int) yloc);
	}
	yloc += titleHeight + classNameVerticalPadding;
	g.setFont(TITLE_FONT);
	g.drawString(this.jclassName, (int) (sidePadding), (int) yloc);

    }

    private void autosize(Graphics2D graphics) {
	Dimension classNameDim = getSize(jclassName, graphics, TITLE_FONT);
	double maxWidth = classNameDim.getWidth();
	double totalHeight = (3.0 * classNameDim.getHeight()) + classNameVerticalPadding;
	for (String text : this.methods) {
	    Dimension methodDim = getSize(text, graphics, SMALL_FONT);
	    maxWidth = Math.max(maxWidth, methodDim.getWidth());
	    totalHeight += methodDim.getHeight() + methodNameVerticalPadding;
	}
	setBounds(0, 0, (int) (maxWidth + 2 * sidePadding), (int) (totalHeight + footerPadding));
    }

    private Dimension getSize(String text, Graphics graphics, Font font) {
	FontMetrics metrics = graphics.getFontMetrics(font);
	int hgt = metrics.getHeight();
	int adv = metrics.stringWidth(text);
	Dimension size = new Dimension(adv + 2, hgt + 2);
	return size;
    }

    public void saveMetadata(String dir) {
	File packagedir = new File(dir + "/" + getSuggestedSubdirName());
	packagedir.mkdirs();
	PrintWriter pw;
	try {
	    pw = new PrintWriter(new FileWriter(new File(dir + "/" + getSuggestedSubdirName() + "/" + getSuggestedMetadataFilename())));
	} catch (IOException e) {
	    throw new RuntimeException(e);
	}
	pw.println("package\t" + jpackageName);
	pw.println("class\t" + jclassName);
	pw.println("xsize\t" + this.getWidth());
	pw.println("ysize\t" + this.getHeight());
	for (String method : methods) {
	    pw.println("method\t" + method);
	}
	pw.close();
    }
    
    public void saveCanvas(String dir) {
	File packagedir = new File(dir + "/" + getSuggestedSubdirName());
	packagedir.mkdirs();

	
	// TODO: Is there a way to get valid Graphics2D without a buffered
	// image? Pre-sizing is inefficient.
	BufferedImage image = new BufferedImage((int) (2000 * scaleFactor),
		(int) (ESTIMATED_PIXELS_PER_ROW * (methods.size() + 2)), BufferedImage.TYPE_INT_RGB);
	Graphics2D g2 = (Graphics2D) image.getGraphics();
	g2.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_GASP);
	autosize(g2); // needs a graphics to count font size
	// now get right size image
	BufferedImage subimage = image.getSubimage(0, 0, this.getWidth(), this.getHeight());
	paint(g2); // and paint on it
	try {
	    ImageIO.write(subimage, "png", new File(dir + "/" + getSuggestedSubdirName() + "/" + getSuggestedPngFilename()));
	} catch (Exception e) {
	    e.printStackTrace();
	}
    }

}