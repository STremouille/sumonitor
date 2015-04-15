package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Iterator;

import conf.GeneralConfig;
import conf.Utils;
import database.Database;

/**
 * 
 * @author S.Trémouille
 * 
 */

public class TitleBar extends InteractiveBar {
    private static final long serialVersionUID = -5450340763800698159L;
    private Style style = Style.LARGE;

    /**
     * @param title
     * @param x
     * @param y
     * @param width
     * @param height
     */
    public TitleBar(String title, int x, int y, int width, int height) {
	super(title, new Rectangle(x, y, width, height));
	this.setColor(Color.lightGray);
    }

    /**
     * @param title
     * @param x
     * @param y
     * @param width
     * @param height
     * @param c
     */
    public TitleBar(String title, int x, int y, int width, int height, Color c) {
	super(title, new Rectangle(x, y, width, height));
	this.setColor(c);
    }

    @Override
    public void paint(Graphics g) {
	if (GeneralConfig.titleEnable) {
	    updateBoundsForAlignement();
	    if (style.equals(Style.CLASSIC)) {
		super.paint(g);
	    } else if (style.equals(Style.LARGE)) {
		Font fonte = new Font("Arial Unicode MS", Font.BOLD,
			(int) (GeneralConfig.pageHeightRatio / 20.0));
		g.setFont(fonte);
		g.setColor(getColor());
		g.fillRect(0, getBounds().y, GeneralConfig.pageWidth,
			getBounds().height);
		g.setColor(Color.black);
		Utils.printSimpleStringCentered(g, getName(), getWidth(),
			getX(), (int) (getHeight() * 0.66) / 2 + getY());
		g.drawLine(0, getBounds().height, GeneralConfig.pageWidth,
			getBounds().height);
	    } else {
		Utils.printSimpleStringCentered(g,
			"(Unknow style)" + getName(), getWidth(), getX(),
			(int) (getHeight() * 0.66) / 2 + getY());
	    }

	    /////////////////////////////////////////////////////////////////
	    /////			PRINT DATES			/////
	    /////////////////////////////////////////////////////////////////
	    if(GeneralConfig.titleBarDisplayDates){
        	    g.setFont(new Font("Arial Unicode MS", Font.TRUETYPE_FONT, (int) Math.round((GeneralConfig.pageHeightRatio/50.0))));
        	    SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yyyy");
        	    int printYCoord = (int)Math.round(this.getHeight() - g.getFontMetrics().getHeight()*0.6);
        	    int printXCoord=0;
        	    if (this.getStyle() == Style.LARGE) {
        		printXCoord = GeneralConfig.pageWidth / 20;
        	    } else if (this.getStyle().equals(Style.CLASSIC)) {
        		printXCoord = (int) (this.getX() + g.getFontMetrics()
        			.getStringBounds(" Print date :  ", g).getWidth());
        	    }
        	    int printAcc = 0;
        	    int printAccRatio = GeneralConfig.pageWidth / 8;
        	    // Doc print
        	    Utils.printSimpleStringLeftAligned(g, "Print date : "+sdf.format(Calendar.getInstance().getTime()), 0, printXCoord, printYCoord);
        	    printAcc = printAcc + printAccRatio;
        	    // Doc
        	    if (GeneralConfig.lastDocUpdateDate != null)
        		Utils.printSimpleStringLeftAligned(g,"Last doc. update : "+sdf.format(GeneralConfig.lastDocUpdateDate),0, printXCoord + printAcc,printYCoord);
        	    else
        		Utils.printSimpleStringLeftAligned(g, "Last doc. update : --/--/----",0, printXCoord + printAcc,printYCoord);
        	    printAcc = printAcc + printAccRatio;
        	    // DB
        	    Utils.printSimpleStringLeftAligned(g, "Last db update :",0, printXCoord + printAcc, printYCoord);
        	    // Request r = new Request();
        	    Iterator<Database> it = GeneralConfig.databases.iterator();
        	    int acc = (int) Math.round(g.getFontMetrics().getStringBounds("Last db update : ", g).getWidth());
        	    while (it.hasNext()) {
        		Database db = it.next();
        		java.util.Date d = db.getLastUpdateDate();
        		if (d != null) {
        		    Utils.printSimpleStringLeftAligned(g, db.getAlias()+ " : " + sdf.format(d),0 ,printXCoord + printAcc + acc, printYCoord);
        		} else {
        		    Utils.printSimpleStringLeftAligned(g, db.getAlias()+ " : --/--/----", 0,printXCoord + printAcc + acc, printYCoord);
        		}
        		acc = acc + GeneralConfig.pageWidth / 15;
        	    }
	    	}
	}
    }

    private void updateBoundsForAlignement() {
	if (GeneralConfig.leftAlignedTitleBar)
	    this.setBounds(new Rectangle(0, this.getY(), this.getWidth(), this
		    .getHeight()));
	else if (GeneralConfig.centeredTitleBar)
	    this.setBounds(new Rectangle((int) Math
		    .round(GeneralConfig.pageWidth / 2.0 - this.getWidth()
			    / 2.0), this.getY(), this.getWidth(), this
		    .getHeight()));
	else if (!GeneralConfig.leftAlignedTitleBar
		&& !GeneralConfig.centeredTitleBar)
	    this.setBounds(new Rectangle(GeneralConfig.pageWidth
		    - this.getWidth(), this.getY(), this.getWidth(), this
		    .getHeight()));
    }

    @Override
    public ResizeDirection getResizeDirection(Point p) {
	ResizeDirection rz = super.getResizeDirection(p);
	if (GeneralConfig.leftAlignedTitleBar
		&& !GeneralConfig.centeredTitleBar
		&& (rz == ResizeDirection.WEST || rz == ResizeDirection.SOUTHWEST)) {
	    return null;
	} else if (!GeneralConfig.leftAlignedTitleBar
		&& !GeneralConfig.centeredTitleBar
		&& (rz == ResizeDirection.EAST || rz == ResizeDirection.SOUTHEAST)) {
	    return null;
	} else {
	    return rz;
	}
    }

    @Override
    public void setBounds(Rectangle r) {
	if (GeneralConfig.printMode
		|| (!(r.getWidth() < GeneralConfig.cellWidth * 5))
		&& (!(r.getHeight() < GeneralConfig.cellHeight))) {
	    super.setBounds(r);
	} else {
	    System.out.println("FAUX!");
	}
    }

    /**
     * @return Style
     */
    public Style getStyle() {
	return style;
    }

    /**
     * @param style
     */
    public void setStyle(Style style) {
	this.style = style;
    }
    
    /**
     * @author S.Trémouille
     *	Enumeration of title bar different style
     */
    public enum Style{
	/**
	 * CLASSIC
	 */
	CLASSIC,
	/**
	 * LARGE
	 */
	LARGE,
	/**
	 * NO_STYLE
	 */
	NO_STYLE
    }
}
