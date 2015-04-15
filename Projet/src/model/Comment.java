package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Polygon;
import java.util.ArrayList;
import java.util.Scanner;

import conf.GeneralConfig;
import conf.Utils;

/**
 * 
 * @author S.Trémouille
 *
 */

public class Comment extends InteractiveBar implements MovableItem{
    	private static final long serialVersionUID = -3195013411018124053L;
	private ArrayList<String> content;
	private int minWidth;
	
	/**
	 * @param title
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public Comment(String title, int x, int y, int width, int height) {
		super(title, x, y, width, height);
		this.setColor(Color.yellow);
	}
	
	@Override
	public void paint(Graphics g){
		initBorders();
		
		Font f = new Font("Arial Unicode MS", Font.PLAIN, (int) Math.round((GeneralConfig.pageHeightRatio/60.0)));
		g.setFont(f);
		formatCommentContent(g);
		if(getWidth()<minWidth){
			this.setWidth(minWidth);
		}
		//Shadow
		g.setColor(Color.gray);
		g.fillRect(getX()+4, getY()+4,(int) Math.round(getWidth()*0.80), getHeight());
		g.fillRect((int)Math.round(getX()+ getWidth()*0.80)+4, (int)Math.round(getY()+getHeight()*0.2)+4, (int)Math.round(getWidth()*0.2),(int)Math.round( getHeight()*0.8));
		Polygon cornerShadow = new Polygon(		new int[]{
														(int) Math.round(getX()+0.8*getWidth())+4,
														(int) Math.round(getX()+0.8*getWidth())+4,
														getX()+getWidth()+4},
												new int[]{
														getY()+4,
														(int) Math.round(getY()+0.2*getHeight())+4,
														(int) Math.round(getY()+0.2*getHeight())+4}, 
												3);
		g.fillPolygon(cornerShadow);
		
		//Background
		g.setColor(getColor());
		g.fillRect(getX(), getY(),(int) Math.round(getWidth()*0.80), getHeight());
		g.fillRect((int)Math.round(getX()+ getWidth()*0.80), (int)Math.round(getY()+getHeight()*0.2), (int)Math.round(getWidth()*0.2),(int)Math.round( getHeight()*0.8));
		Polygon corner = new Polygon(	new int[]{(int) Math.round(getX()+0.8*getWidth()),(int) Math.round(getX()+0.8*getWidth()),getX()+getWidth()},
										new int[]{getY(),(int) Math.round(getY()+0.2*getHeight()),(int) Math.round(getY()+0.2*getHeight())}, 
										3);
		g.fillPolygon(corner);
		
		
		//Borders
		g.setColor(Color.black);
		g.drawPolyline(	new int[]{
							getX(),
							getX(),
							getX()+getWidth(),
							getX()+getWidth(),
							(int) Math.round(getX()+0.8*getWidth()),
							(int) Math.round(getX()+0.8*getWidth()),
							getX()+getWidth(),
							(int) Math.round(getX()+0.8*getWidth()),
							getX()},
						new int[]{
							getY(),
							getY()+getHeight(),
							getY()+getHeight(),
							(int) Math.round(getY()+0.2*getHeight()),
							getY(),
							(int) Math.round(getY()+0.2*getHeight()),	
							(int) Math.round(getY()+0.2*getHeight()),
							getY(),		
							getY()},
						9);
		
		//Content
//		System.out.println(getY()+" - "+getHeight());
		double step = (getHeight())/(content.size()+1);
//		System.out.println("Step : "+step);
		for(double i=0;i<content.size();i++){
			double yPos = (i+1)*step;
			//System.out.println((int)Math.round(yPos)+" < "+(int)Math.round(getHeight()*0.2));
			Utils.printSimpleStringLeftAligned(g, content.get((int) i), getWidth(), getX(), (int)Math.round(getY()+yPos));
			
		}
		
		if(isSelected()){
			g.setColor(Color.red);
			g.fillRect(getBounds().x-GeneralConfig.milestoneHeight/50, getBounds().y-GeneralConfig.milestoneHeight/50,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect(getBounds().x-GeneralConfig.milestoneHeight/50+getBounds().width, getBounds().y-GeneralConfig.milestoneHeight/50,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect(getBounds().x-GeneralConfig.milestoneHeight/50,getBounds().y-GeneralConfig.milestoneHeight/50+getBounds().height,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
			g.fillRect(getBounds().x-GeneralConfig.milestoneHeight/50+getBounds().width, getBounds().y-GeneralConfig.milestoneHeight/50+getBounds().height,GeneralConfig.milestoneHeight/25 ,GeneralConfig.milestoneHeight/25 );
		}
	}
	
	@Override
	public String toString(){
		return "Comment : "+this.getName();
	}

	
	private void formatCommentContent(Graphics g){
		int minWidth=0;
		Scanner scanner = new Scanner(getName().trim()+" EOFL");
		scanner.useDelimiter("[ \n]");
		content = new ArrayList<String>();
		int line=0;
		int accWith=0;
		boolean newLine=true;
		String s = "";
		
		while(scanner.hasNext()){
			if(!newLine){
				s = scanner.next();
			} else if(newLine){
				content.add("");
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()>minWidth){
				minWidth=(int) g.getFontMetrics().getStringBounds(s, g).getWidth()+1;
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()>=this.getWidth()*0.8){
				s="too long word";
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()<=((this.getWidth()*0.8-accWith))){
				if(!s.equals("EOFL")){
	//				System.out.println(s);
				    if(content.get(line).equals(""))
					content.set(line,s);
				    else
					content.set(line, content.get(line)+" "+s);
					accWith=(int) (accWith+g.getFontMetrics().getStringBounds(s+" ", g).getWidth());
					newLine=false;
				}
			}
			else{
	//			System.out.println("new line");
				accWith=0;
				line++;
				newLine=true;
				}
			}
		scanner.close();
	}

	@Override
	public void move(double x, double y) {
		this.setX(this.getX()+x);
		this.setY(this.getY()+y);
	}

	
	/*public void formatCommentContent(Graphics g){
		minWidth=0;
		String name = getName()+" EOFL";
//		System.out.println(name);
		Scanner scanner = new Scanner(name);
		content = new ArrayList<String>();
		int line=0;
		int accWith=0;
		boolean newLine=true;
		String s = "";
		
		while(scanner.hasNext()){
			if(!newLine){
				s = scanner.next();
			} else if(newLine){
				content.add("");
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()>minWidth){
				minWidth=(int) g.getFontMetrics().getStringBounds(s, g).getWidth()+1;
			}
			if(g.getFontMetrics().getStringBounds(s, g).getWidth()<=((this.getWidth()*0.8-accWith)-this.getWidth()*0.1)){
				if(!s.equals("EOFL")){
//					System.out.println(s);
					content.set(line, content.get(line)+" "+s);
					accWith=(int) (accWith+g.getFontMetrics().getStringBounds(s, g).getWidth());
					newLine=false;
				}
			}
			else{
//				System.out.println("new line");
				accWith=0;
				line++;
				newLine=true;
			}
		}
	}*/
	
}