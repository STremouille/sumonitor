package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.swing.JOptionPane;

import net.coobird.thumbnailator.Thumbnails;
import conf.GeneralConfig;
import conf.Utils;

/**
 * 
 * @author S.Trémouille
 * Class modeling and representing the project title block
 */

public class ProjectTitleBlock extends JComponent {
    	private static final long serialVersionUID = -7455002082884984494L;
	private TreeMap<String, String> cartouche;
	private ArrayList<TreeMap<String, String>> revisions;
	private boolean rightAligned;
	private int widthCartouche;
	private int heightCartouche;
	private int xCartouche;
	private int yCartouche;
	private int xRevision;
	private int widthRevision;
	private int heightRevision;

	/**
	 * New Project Title Block
	 */
	public ProjectTitleBlock() {
		this.cartouche = new TreeMap<String, String>();
		this.revisions = new ArrayList<TreeMap<String, String>>();
		this.setRightAligned(false);
		this.initCartouche();
	}
	
	public void initCartouche(){
		this.cartouche.put("companyLogo","");
		this.cartouche.put("projectLogo","");
		this.cartouche.put("contractorLogo","");
		this.cartouche.put("contractNumber","");
		this.cartouche.put("contractorName","");
		this.cartouche.put("customerField","");
		this.cartouche.put("documentTitle","");
		this.cartouche.put("studieType","");
		this.cartouche.put("facilityName","");
		this.cartouche.put("designation","");
		this.cartouche.put("drawingType","");
		this.cartouche.put("docType","");
		this.cartouche.put("systSSsyst","");
		this.cartouche.put("discipline","");
		this.cartouche.put("electronicFilename","");
		this.cartouche.put("companyRef","");
		this.cartouche.put("contractorRef","");
		this.cartouche.put("revision","");
		this.cartouche.put("scale","");
		this.cartouche.put("folio","");
		this.cartouche.put("misc", "This document is the property of TOTAL. It shall not be stored, reproduced or disclosed to others without written authorisation from the company.");
	}

	/**
	 * @return cartouche
	 */
	public TreeMap<String, String> getCartouche() {
		return cartouche;
	}

	/**
	 * @param cartouche
	 */
	public void setCartouche(TreeMap<String, String> cartouche) {
		this.cartouche = cartouche;
	}

	/**
	 * @return revisions
	 */
	public ArrayList<TreeMap<String, String>> getRevisions() {
		return revisions;
	}

	/**
	 * @param revisions
	 */
	public void setRevisions(ArrayList<TreeMap<String, String>> revisions) {
		this.revisions = revisions;
	}

	@Override
	public void paint(Graphics g) {
		if (GeneralConfig.titleBlockEnable) {
			if (GeneralConfig.titleBlockRightAligned) {
				setRightAligned(true);
			} else {
				setRightAligned(false);
			}
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//
			//
			// TITLE BLOCK
			//
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			// drawing the cartouche borders
			widthCartouche = (int) (GeneralConfig.milestoneWidth * 3.2);
			heightCartouche = (int) (GeneralConfig.milestoneHeight * 2.2);
			if (!isRightAligned()) {
				xCartouche = 0;
			} else {
				xCartouche = GeneralConfig.pageWidth - widthCartouche;
			}
			yCartouche = (int) (GeneralConfig.pageHeight - heightCartouche);

			g.setColor(Color.white);
			g.fillRect(xCartouche, yCartouche, widthCartouche, heightCartouche);
			g.setColor(Color.black);
			g.drawRect(xCartouche, yCartouche, widthCartouche, heightCartouche);

			// logos
			// Company
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.04))));
			BufferedImage companyLogo;
			try {
				if (getCartouche().get("companyLogo") != null && !getCartouche().get("companyLogo").equals("") && GeneralConfig.companyLogo!=null && !getCartouche().get("companyLogo").equals("null")) {
					//companyLogo = ImageIO.read(new File(getCartouche().get("companyLogo")));
				    	System.out.println("#"+getCartouche().get("companyLogo")+"#");
					companyLogo = ImageIO.read(new ByteArrayInputStream(GeneralConfig.companyLogo));
					BufferedImage fcompanyLogo = Thumbnails
							.of(companyLogo)
							.size((int) (Math.round(widthCartouche * 0.325)),
									(int) (Math.round(heightCartouche * 0.247)))
							.asBufferedImage();
					g.drawImage(
							fcompanyLogo,
							(int) Math.round(xCartouche
									+ (widthCartouche * 0.325) / 2.0
									- fcompanyLogo.getWidth() / 2),
							(int) Math.round(yCartouche
									+ (heightCartouche * 0.247) / 2.0
									- fcompanyLogo.getHeight() / 2), null);
				} else {
					Utils.printSimpleStringCentered(g, "Company Name & Logo",
							(int) (Math.round(widthCartouche * 0.325)),
							xCartouche, yCartouche
									+ g.getFontMetrics().getHeight() / 2);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
			g.drawRect(xCartouche, yCartouche,
					(int) (Math.round(widthCartouche * 0.325)),
					(int) (Math.round(heightCartouche * 0.247)));

			// Project

			BufferedImage projectLogo;
			try {
				if (getCartouche().get("projectLogo")!=null && !getCartouche().get("projectLogo").equals("") && GeneralConfig.projectLogo!=null && !getCartouche().get("projectLogo").equals("null")) {
					projectLogo = /*ImageIO.read(new File(getCartouche().get(
							"projectLogo")));*/ImageIO.read(new ByteArrayInputStream(GeneralConfig.projectLogo));
					BufferedImage fprojectLogo = Thumbnails
							.of(projectLogo)
							.size((int) (Math.round(widthCartouche
									* (0.669 - 0.325))),
									(int) (Math.round(heightCartouche * 0.247)))
							.asBufferedImage();
					g.drawImage(
							fprojectLogo,
							(int) Math.round(xCartouche + widthCartouche
									* 0.325
									+ (widthCartouche * (0.669 - 0.325)) / 2.0
									- fprojectLogo.getWidth() / 2),
							(int) Math.round(yCartouche
									+ (heightCartouche * 0.247) / 2.0
									- fprojectLogo.getHeight() / 2), null);
				} else {
					Utils.printSimpleStringCentered(
							g,
							"Project Name & Logo",
							(int) (Math.round(widthCartouche * (0.669 - 0.325))),
							(int) (Math.round(xCartouche + widthCartouche
									* 0.325)), yCartouche
									+ g.getFontMetrics().getHeight() / 2);
				}
			} catch (IOException e) {
				JOptionPane.showMessageDialog(null, e.getMessage());
				e.printStackTrace();
			}
			g.drawRect((int) (Math.round(xCartouche + widthCartouche * 0.325)),
					yCartouche,
					(int) (Math.round(widthCartouche * (0.669 - 0.325))),
					(int) (Math.round(heightCartouche * 0.247)));

			// Contractor

			BufferedImage contractorLogo;
			try {
				if (getCartouche().get("contractorLogo")!=null && !getCartouche().get("contractorLogo").equals("") && GeneralConfig.contractorLogo!=null && !getCartouche().get("contractorLogo").equals("null")) {
					//System.out.println("Cartouche : ->"+getCartouche().get("contractorLogo").getClass()+"<-");
					contractorLogo = ImageIO.read(new ByteArrayInputStream(GeneralConfig.contractorLogo));
					BufferedImage fcontractorLogo = Thumbnails.of(contractorLogo).size((int) (Math.round(widthCartouche* (1.0 - 0.669))),(int) Math.round(heightCartouche * 0.247)).asBufferedImage();
					g.drawImage(
							fcontractorLogo,
							(int) Math.round(xCartouche + widthCartouche
									* 0.669 + (widthCartouche * (1.0 - 0.669))
									/ 2.0 - fcontractorLogo.getWidth() / 2),
							(int) Math.round(yCartouche
									+ (heightCartouche * 0.247) / 2.0
									- fcontractorLogo.getHeight() / 2), null);
				} else {
					Utils.printSimpleStringCentered(
							g,
							"Contractor Name & Logo",
							(int) (Math.round(widthCartouche * (1.0 - 0.669))),
							(int) (Math.round(xCartouche + widthCartouche
									* 0.669)), yCartouche
									+ g.getFontMetrics().getHeight() / 2);
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
			g.drawRect((int) (Math.round(xCartouche + widthCartouche * 0.669)),
					yCartouche,
					(int) (Math.round(widthCartouche * (1.0 - 0.669))),
					(int) Math.round(heightCartouche * 0.247));

			// rules
			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.247),
					widthCartouche,
					(int) Math.round(heightCartouche * (0.284 - 0.247)));
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.025))));
			if(this.cartouche.get("misc")!=null)
			    Utils.printSimpleStringCentered(g,this.cartouche.get("misc"),widthCartouche, xCartouche,(int) Math.round(yCartouche + heightCartouche * 0.247)+ g.getFontMetrics().getHeight() / 2);

			// n° contrat + contractor name
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math.round(heightCartouche * 0.03))));
			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.284),
					(int) Math.round(widthCartouche * 0.411),
					(int) Math.round(heightCartouche * (0.383 - 0.284)));
			Utils.printSimpleStringLeftAligned(g, "N contracts/RFS:"
					+ getCartouche().get("contractNumber"),
					(int) Math.round(widthCartouche * 0.411), xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.284)
							+ g.getFontMetrics().getHeight() / 2);
			g.drawRect((int) Math.round(xCartouche + widthCartouche * 0.411),
					(int) Math.round(yCartouche + heightCartouche * 0.284),
					(int) Math.round(widthCartouche * (1.00 - 0.411)),
					(int) Math.round(heightCartouche * (0.383 - 0.284)));
			Utils.printSimpleStringLeftAligned(
					g,
					"Contractor name:" + getCartouche().get("contractorName"),
					(int) Math.round(widthCartouche * (1.0 - 0.412)),
					(int) Math.round(xCartouche + widthCartouche * 0.411),
					(int) Math.round(yCartouche + heightCartouche * 0.284
							+ g.getFontMetrics().getHeight() / 2));

			// customer field
			g.setFont(new Font("Arial Unicode MS Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.04))));
			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.383),
					widthCartouche,
					(int) Math.round(heightCartouche * (0.481 - 0.384)));
			if (getCartouche().get("customerField") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("customerField"),
						widthCartouche,
						xCartouche,
						(int) Math.round(yCartouche + heightCartouche * 0.383
								+ g.getFontMetrics().getHeight() / 2));
			}

			// Document title + Type of study
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.03))));
			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.481),
					(int) Math.round(widthCartouche * (0.804)),
					(int) Math.round(heightCartouche * (0.556 - 0.481)));
			if (getCartouche().get("documentTitle") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("documentTitle"),
						(int) Math.round(widthCartouche * (0.804)),
						xCartouche,
						(int) Math.round(yCartouche + heightCartouche * 0.481
								+ g.getFontMetrics().getHeight() / 2));
			}
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.804)),
					(int) Math.round(yCartouche + heightCartouche * 0.481),
					(int) Math.round(widthCartouche * (1.0 - 0.804)),
					(int) Math.round(heightCartouche * (0.556 - 0.481)));
			if (getCartouche().get("studieType") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("studieType"),
						(int) Math.round(widthCartouche * (1.0 - 0.804)),
						(int) Math.round(xCartouche + widthCartouche * (0.804)),
						(int) Math.round(yCartouche + heightCartouche * 0.481
								+ g.getFontMetrics().getHeight() / 2));
			}

			// name of facilities + designation + type of drawing
			// g.drawRect(xCartouche, (int)
			// Math.round(yCartouche+heightCartouche*0.556),widthCartouche,(int)Math.round(heightCartouche*(0.778-0.556)));
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.05))));
			if (getCartouche().get("facilityName") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("facilityName"),
						widthCartouche,
						xCartouche,
						(int) Math.round(yCartouche + heightCartouche
								* (0.556 + (0.778 - 0.556) / 4.0)));
			}
			g.setFont(new Font("Arial Unicode MS Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.04))));
			if (getCartouche().get("designation") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("designation"),
						widthCartouche,
						xCartouche,
						(int) Math.round(yCartouche + heightCartouche
								* (0.556 + 2.0 * (0.778 - 0.556) / 4.0)));
			}
			if (getCartouche().get("drawingType") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("drawingType"),
						widthCartouche,
						xCartouche,
						(int) Math.round(yCartouche + heightCartouche
								* (0.556 + 3.0 * (0.778 - 0.556) / 4.0)));
			}

			// doc type + Syst + Discipline + Electronic Filename
			g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math
					.round(heightCartouche * 0.03))));
			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.778),
					(int) Math.round(widthCartouche * (0.159)),
					(int) Math.round(heightCartouche * (0.852 - 0.778)));
			Utils.printSimpleStringLeftAligned(g, "Doc type:"
					+ getCartouche().get("docType"),
					(int) Math.round(widthCartouche * 0.159), xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.778)
							+ g.getFontMetrics().getHeight() / 2);
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.159)),
					(int) Math.round(yCartouche + heightCartouche * 0.778),
					(int) Math.round(widthCartouche * (0.337 - 0.159)),
					(int) Math.round(heightCartouche * (0.852 - 0.778)));
			Utils.printSimpleStringLeftAligned(
					g,
					"Syst./S-Syst.:" + getCartouche().get("systSSsyst"),
					(int) Math.round(widthCartouche * (0.337 - 0.159)),
					(int) Math.round(xCartouche + widthCartouche * (0.159)),
					(int) Math.round(yCartouche + heightCartouche * 0.778
							+ g.getFontMetrics().getHeight() / 2));
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.337)),
					(int) Math.round(yCartouche + heightCartouche * 0.778),
					(int) Math.round(widthCartouche * (0.528 - 0.337)),
					(int) Math.round(heightCartouche * (0.852 - 0.778)));
			Utils.printSimpleStringLeftAligned(
					g,
					"Discipline:" + getCartouche().get("discipline"),
					(int) Math.round(widthCartouche * (0.528 - 0.337)),
					(int) Math.round(xCartouche + widthCartouche * (0.337)),
					(int) Math.round(yCartouche + heightCartouche * 0.778
							+ g.getFontMetrics().getHeight() / 2));
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.528)),
					(int) Math.round(yCartouche + heightCartouche * 0.778),
					(int) Math.round(widthCartouche * (1.000 - 0.528)),
					(int) Math.round(heightCartouche * (0.852 - 0.778)));
			Utils.printSimpleStringLeftAligned(
					g,
					"Electronic Filename:"
							+ getCartouche().get("electronicFilename"),
					(int) Math.round(widthCartouche * (1.000 - 0.528)),
					(int) Math.round(xCartouche + widthCartouche * (0.528)),
					(int) Math.round(yCartouche + heightCartouche * 0.778
							+ g.getFontMetrics().getHeight() / 2));

			// Company ref + Contractor ref + R�vision + Scale + Folio
			// Company
			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.852),
					(int) Math.round(widthCartouche * (0.123)),
					(int) Math.round(heightCartouche * (0.926 - 0.853)));
			Utils.printSimpleStringCentered(
					g,
					"Company Ref:",
					(int) Math.round(widthCartouche * (0.123)),
					xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.852
							+ g.getFontMetrics().getHeight() / 2));
			if (getCartouche().get("companyRef") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("companyRef"),
						(int) Math.round(widthCartouche * (0.712 - 0.123)),
						(int) Math.round(xCartouche + widthCartouche * (0.123)),
						(int) Math.round(yCartouche + heightCartouche * 0.852
								+ g.getFontMetrics().getHeight() / 2));
			}
			// Contractor
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.123)),
					(int) Math.round(yCartouche + heightCartouche * 0.852),
					(int) Math.round(widthCartouche * (0.712 - 0.123)),
					(int) Math.round(heightCartouche * (0.926 - 0.853)));
			if (getCartouche().get("contractorRef") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("contractorRef"),
						(int) Math.round(widthCartouche * (0.712 - 0.123)),
						(int) Math.round(xCartouche + widthCartouche * (0.123)),
						(int) Math.round(yCartouche + heightCartouche * 0.926
								+ g.getFontMetrics().getHeight() / 2));
			}

			g.drawRect(xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.926),
					(int) Math.round(widthCartouche * (0.123)),
					(int) Math.round(heightCartouche * (1.0 - 0.926)));
			Utils.printSimpleStringCentered(
					g,
					"Contractor Ref:",
					(int) Math.round(widthCartouche * (0.123)),
					xCartouche,
					(int) Math.round(yCartouche + heightCartouche * 0.926
							+ g.getFontMetrics().getHeight() / 2));
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.123)),
					(int) Math.round(yCartouche + heightCartouche * 0.926),
					(int) Math.round(widthCartouche * (0.712 - 0.123)),
					(int) Math.round(heightCartouche * (1.0 - 0.926)));

			// revision
			Utils.printSimpleStringCentered(
					g,
					"Revision:",
					(int) Math.round(widthCartouche * (0.804 - 0.712)),
					(int) Math.round(xCartouche + widthCartouche * (0.712)),
					(int) Math.round(yCartouche + heightCartouche * 0.852
							+ g.getFontMetrics().getHeight() / 2));
			if (getCartouche().get("revision") != null) {
				Utils.printSimpleStringCentered(
						g,
						getCartouche().get("revision"),
						(int) Math.round(widthCartouche * (0.804 - 0.712)),
						(int) Math.round(xCartouche + widthCartouche * (0.712)),
						(int) Math.round(yCartouche + heightCartouche * 0.926
								+ g.getFontMetrics().getHeight() / 2));
			}
			// scale
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.804)),
					(int) Math.round(yCartouche + heightCartouche * 0.852),
					(int) Math.round(widthCartouche * (1.000 - 0.804)),
					(int) Math.round(heightCartouche * (0.926 - 0.853)));
			Utils.printSimpleStringLeftAligned(
					g,
					"Scale:" + getCartouche().get("scale"),
					(int) Math.round(widthCartouche * (1.000 - 0.804)),
					(int) Math.round(xCartouche + widthCartouche * (0.804)),
					(int) Math.round(yCartouche + heightCartouche * 0.852
							+ g.getFontMetrics().getHeight() / 2));
			Utils.printSimpleStringLeftAligned(
					g,
					"Folio:" + getCartouche().get("folio"),
					(int) Math.round(widthCartouche * (1.000 - 0.804)),
					(int) Math.round(xCartouche + widthCartouche * (0.804)),
					(int) Math.round(yCartouche + heightCartouche * 0.926
							+ g.getFontMetrics().getHeight() / 2));
			// folio
			g.drawRect((int) Math.round(xCartouche + widthCartouche * (0.804)),
					(int) Math.round(yCartouche + heightCartouche * 0.926),
					(int) Math.round(widthCartouche * (1.000 - 0.804)),
					(int) Math.round(heightCartouche * (1.0 - 0.926)));

			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			//
			//
			// REVISION
			//
			//
			// ////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
			g.setFont(new Font("Arial Unicode MS", Font.BOLD, (int) (Math.round(heightCartouche * 0.03))));
			widthRevision = widthCartouche;
			heightRevision = (int) Math.round(heightCartouche
					* (1.000 - 0.926));
			int decalCoeffRevision = (int)Math.round(widthRevision/10);
			if (rightAligned) {
				xRevision = (int) Math.round(GeneralConfig.pageWidth - 2*widthCartouche - decalCoeffRevision);
			} else {
				xRevision = (int) Math.round(widthCartouche + decalCoeffRevision);
			}
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					widthRevision, heightRevision);
			// footer
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					(int) Math.round(widthRevision * 0.074), heightRevision);
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					(int) Math.round(widthRevision * 0.202), heightRevision);
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					(int) Math.round(widthRevision * 0.294), heightRevision);
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					(int) Math.round(widthRevision * 0.638), heightRevision);
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					(int) Math.round(widthRevision * 0.742), heightRevision);
			g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision,
					(int) Math.round(widthRevision * 0.865), heightRevision);
			Utils.printSimpleStringCentered(
					g,
					"Rev",
					(int) Math.round(widthRevision * 0.074),
					xRevision,
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));
			Utils.printSimpleStringCentered(
					g,
					"Date",
					(int) Math.round(widthRevision * (0.202 - 0.074)),
					(int) Math.round(xRevision + widthRevision * 0.074),
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));
			Utils.printSimpleStringCentered(
					g,
					"Status",
					(int) Math.round(widthRevision * (0.294 - 0.202)),
					(int) Math.round(xRevision + widthRevision * 0.202),
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));
			Utils.printSimpleStringCentered(
					g,
					"Revision Memo",
					(int) Math.round(widthRevision * (0.638 - 0.294)),
					(int) Math.round(xRevision + widthRevision * 0.294),
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));
			Utils.printSimpleStringCentered(
					g,
					"Issued By",
					(int) Math.round(widthRevision * (0.742 - 0.638)),
					(int) Math.round(xRevision + widthRevision * 0.638),
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));
			Utils.printSimpleStringCentered(
					g,
					"Checked By",
					(int) Math.round(widthRevision * (0.865 - 0.742)),
					(int) Math.round(xRevision + widthRevision * 0.742),
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));
			Utils.printSimpleStringCentered(
					g,
					"Approved By",
					(int) Math.round(widthRevision * (1.0 - 0.865)),
					(int) Math.round(xRevision + widthRevision * 0.865),
					(int) Math.round(GeneralConfig.pageHeight - heightRevision
							/ 2.0 - g.getFontMetrics().getHeight() / 2.0));

			// content
			Iterator<TreeMap<String, String>> itRevisions = getRevisions()
					.iterator();
			if (getRevisions().size() > 5) {
				List<TreeMap<String, String>> tmp = new ArrayList<TreeMap<String, String>>(
						getRevisions().subList(getRevisions().size() - 5,
								getRevisions().size()));
				itRevisions = tmp.iterator();
			}
			int heightAcc = heightRevision;// start with one step 'cause of the
											// footer
			while (itRevisions.hasNext()) {
				g.setFont(new Font("Arial Unicode MS", Font.PLAIN, (int) (Math
						.round(heightCartouche * 0.03))));
				TreeMap<String, String> rev = new TreeMap<String, String>(
						itRevisions.next());
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, widthRevision, heightRevision);
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, (int) Math.round(widthRevision * 0.074),
						heightRevision);
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, (int) Math.round(widthRevision * 0.202),
						heightRevision);
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, (int) Math.round(widthRevision * 0.294),
						heightRevision);
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, (int) Math.round(widthRevision * 0.638),
						heightRevision);
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, (int) Math.round(widthRevision * 0.742),
						heightRevision);
				g.drawRect(xRevision, GeneralConfig.pageHeight - heightRevision
						- heightAcc, (int) Math.round(widthRevision * 0.865),
						heightRevision);
				if(rev.get("rev")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("rev"),
						(int) Math.round(widthRevision * 0.074),
						xRevision,
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				if(rev.get("date")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("date"),
						(int) Math.round(widthRevision * (0.202 - 0.074)),
						(int) Math.round(xRevision + widthRevision * 0.074),
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				if(rev.get("status")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("status"),
						(int) Math.round(widthRevision * (0.294 - 0.202)),
						(int) Math.round(xRevision + widthRevision * 0.202),
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				if(rev.get("revisionMemo")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("revisionMemo"),
						(int) Math.round(widthRevision * (0.638 - 0.294)),
						(int) Math.round(xRevision + widthRevision * 0.294),
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				if(rev.get("issued")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("issued"),
						(int) Math.round(widthRevision * (0.742 - 0.638)),
						(int) Math.round(xRevision + widthRevision * 0.638),
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				if(rev.get("checked")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("checked"),
						(int) Math.round(widthRevision * (0.865 - 0.742)),
						(int) Math.round(xRevision + widthRevision * 0.742),
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				if(rev.get("approved")!=null)
				Utils.printSimpleStringCentered(
						g,
						"" + rev.get("approved"),
						(int) Math.round(widthRevision * (1.0 - 0.865)),
						(int) Math.round(xRevision + widthRevision * 0.865),
						(int) Math.round(GeneralConfig.pageHeight
								- heightRevision / 2.0
								- g.getFontMetrics().getHeight() / 2.0)
								- heightAcc);
				heightAcc = heightAcc + heightRevision;
			}
		}
	}

	@Override
	public Rectangle getBounds() {
		return new Rectangle(xCartouche,yCartouche,widthCartouche,heightCartouche);
	}
	
	/**
	 * @param p
	 * @return boolean
	 */
	public String belongsTo(Point p){
		Rectangle c = new Rectangle(xCartouche,yCartouche,widthCartouche,heightCartouche);
		Rectangle r = new Rectangle(xRevision,GeneralConfig.pageHeight-(heightRevision*(revisions.size()+1)),widthRevision,heightRevision*(revisions.size()+1));
		if (c.contains(p)){
			return "titleBlock";
		} else if(r.contains(p)){
			return "revision";
		}
		return "";
		
	}

	/**
	 * @return boolean
	 */
	public boolean isRightAligned() {
		return rightAligned;
	}

	/**
	 * @param rightAligned
	 */
	public void setRightAligned(boolean rightAligned) {
		this.rightAligned = rightAligned;
	}
}
