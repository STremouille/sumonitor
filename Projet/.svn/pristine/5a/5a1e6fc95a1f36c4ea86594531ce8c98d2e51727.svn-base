package model;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Point;
import java.awt.Rectangle;

import javax.swing.JComponent;
import javax.swing.JLabel;

import conf.GeneralConfig;
import conf.Utils;

/**
 * 
 * @author S.Trémouille
 * Abstract class of comment, sequence bar, and title bar.
 */

public abstract class InteractiveBar extends JComponent{
	
    	private static final long serialVersionUID = -4966114642567515396L;
	private String name;
	private boolean selected;
	private Rectangle[] borders;
	private Color color;
	private Font fonte;
	private double X,Y,width,height;
	
	/**
	 * @param title
	 * Title of the element
	 */
	public InteractiveBar(String title){
		this(title,new Rectangle());
	}
	
	/**
	 * @param title
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public InteractiveBar(String title,int x, int y,int width, int height){
		this(title,new Rectangle(x,y,width, height));
	}
	/**
	 * @param title
	 * @param rectangle
	 */
	public InteractiveBar(String title,Rectangle rectangle){
		setName(title);
		this.width=rectangle.width;
		this.height=rectangle.height;
		this.X=rectangle.x;
		this.Y=rectangle.y;
		this.color=Color.LIGHT_GRAY;
		initBorders();
	}
	
	/**
	 * @return Rectangle[]
	 * Return a 4-array of Rectangle, which represents borders of the shape
	 */
	public Rectangle[] getBorders(){
		return borders;
	}
	
	@Override
	public String getName(){
		return name;
	}
	
	@Override
	public void setName(String n){
		this.name=n;
	}
	
	@Override
	public void setBounds(Rectangle r){
		super.setBounds(r);
		this.X=r.x;
		this.Y=r.y;
		this.width=r.width;
		this.height=r.height;
		initBorders();
	}
	
	@Override
	public Rectangle getBounds(){
		return new Rectangle((int)Math.round(X),(int)Math.round(Y),(int)Math.round(width),(int)Math.round(height));
	}
	
	@Override
	public int getWidth(){
		return (int)Math.round(width);
	}
	
	@Override
	public int getHeight(){
		return (int)Math.round(height);
	}
	
	
	
	/**
	 * @return Double
	 * Return a more accurate value of the X Coord
	 */
	public Double getDoubleX(){
		return X;
	}
	
	/**
	 * @return Double
	 * Return a more accurate value of the Y Coord
	 */
	public Double getDoubleY(){
		return Y;
	}
	
	/**
	 * @return Double
	 * Return a more accurate value of the Width
	 */
	public Double getDoubleWidth(){
		return width;
	}
	
	/**
	 * @return Double
	 * Return a more accurate value of the Height
	 */
	public Double getDoubleHeight(){
		return height;
	}
	
	@Override
	public int getX() {
		return (int)Math.round(X);
	}
	
	@Override
	public int getY() {
		return (int)Math.round(Y);
	}
	
	/**
	 * @param x
	 * Set X Coordinate of the component
	 */
	public void setX(double x){
		this.X=x;
	}
	
	/**
	 * @param y
	 * Set Y Coordinate of the component
	 */
	public void setY(double y){
		this.Y=y;
	}
	
	/**
	 * @param w
	 * @return Boolean
	 *  Try to set a width
	 * 	Return True if it's enable else False
	 */
	public boolean setWidth(double w){
		if(w>=GeneralConfig.milestoneWidth/4.0||GeneralConfig.printMode){
			this.width=w;
			return true;
		} else {
			this.width=GeneralConfig.milestoneWidth/4.0;
		}
		return false;
	}
	
	/**
	 * @param h
	 * @return Boolean
	 * Try to set a height
	 * 	Return True if it's enable else False
	 */
	public boolean setHeight(double h){
		//System.out.println("Milestone Height :"+h+">="+(GeneralConfig.cellHeight-1));
		if(h>=GeneralConfig.cellHeight/2-1||GeneralConfig.printMode||GeneralConfig.loadMode){
			this.height=h;
			return true;
		}/* else {
			this.height=GeneralConfig.milestoneHeight/5.0;
		}*/
		return false;
	}
	
	/**
	 * @return Color
	 * return the background color
	 */
	public Color getColor(){
		return color;
	}
	
	/**
	 * @param color
	 * Set a background color
	 */
	public void setColor(Color color){
		this.color=color;
	}
	
	@Override
	public void paint(Graphics g){
		fonte = new Font("Arial", Font.BOLD, (int) (GeneralConfig.pageHeightRatio/20.0));
		g.setFont(fonte);
		g.setColor(getColor());
		g.fillRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		g.setColor(Color.black);
		Utils.printSimpleStringCentered(g, getName(),(int)Math.round(getDoubleWidth()),(int)Math.round(getDoubleX()), (int)Math.round((height*0.66)/2+getDoubleY()));
		g.drawRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		if(selected){
			g.setColor(Color.blue);
			g.drawRect((int)Math.round(getDoubleX()), (int)Math.round(getDoubleY()), (int)Math.round(getDoubleWidth()), (int)Math.round(getDoubleHeight()));
		}
	}
	
	@Override
	public String toString(){
		return this.getClass().getName()+" : "+this.getName();
	}
	
	
	/**
	 * @param point
	 * @return ResizeDirection
	 */
	public ResizeDirection getResizeDirection(Point point){
		int res = 0;
		int[] coeff = new int[]{1,2,4,7};
		for(int i=0;i<4;i++){
			if(borders[i].contains(point)){
				res=res+coeff[i];
			}
		}
		ResizeDirection direction=null;
		switch (res){
			case 1 : 
				direction=ResizeDirection.NORTH;
				break;
			case 2 : 
				direction=ResizeDirection.EAST;
				break;
			case 3 : 
				direction=ResizeDirection.NORTHEAST;
				break;
			case 4 : 
				direction=ResizeDirection.SOUTH;
				break;
			case 6 : 
				direction=ResizeDirection.SOUTHEAST;
				break;
			case 7 : 
				direction=ResizeDirection.WEST;
				break;
			case 8 : 
				direction=ResizeDirection.NORTHWEST;
				break;
			case 11 : 
				direction=ResizeDirection.SOUTHWEST;
				break;
			default:
			    	break;
		}
		return direction;
	}

	/**
	 * @author S.Trémouille
	 * Different type of Resize Directions
	 *
	 */
	public enum ResizeDirection{
		/**
		 * Up of the box
		 */
		NORTH,
		/**
		 * Left of the box
		 */
		WEST,
		/**
		 * Down of the box
		 */
		SOUTH,
		/**
		 * Right of the box
		 */
		EAST,
		/**
		 * Up & Right of the box
		 */
		NORTHEAST,
		/**
		 * Down & Right of the box
		 */
		SOUTHEAST,
		/**
		 * Down & Left of the box
		 */
		SOUTHWEST,
		/**
		 * Up & Left of the box
		 */
		NORTHWEST,
		/**
		 * Second down direction used to resize dot line's sequence bar
		 */
		EXTENDEDSOUTH
	}
	
	/**
	 * Initialization of the four rectangle used to resize a shape
	 */
	public void initBorders(){
		this.borders= new Rectangle[4];
		this.borders[0]=new Rectangle((int)Math.round(X), (int)Math.round(Y), (int)Math.round(width), 20);//NORTH
		this.borders[1]=new Rectangle((int)Math.round(X+width-20), (int)Math.round(Y), 20, (int)Math.round(height));//EAST
		this.borders[2]=new Rectangle((int)Math.round(X), (int)Math.round(Y+height-20), (int)Math.round(width), 20);//SOUTH
		this.borders[3]=new Rectangle((int)Math.round(X),(int)Math.round(Y),20,(int)Math.round(height));//WEST
	}
	
	
	public void setSelected(boolean b) {
		this.selected=b;
	}
	
	public boolean isSelected(){
		return selected;
	}

	@Override
	public boolean equals(Object obj) {
		System.out.println(this.getClass());
		System.out.println(obj.getClass());
		if(this.getClass()==obj.getClass()&&this.getX()==((InteractiveBar) obj).getX()&&this.getY()==((InteractiveBar) obj).getY()&&this.getWidth()==((InteractiveBar) obj).getWidth()&&this.getHeight()==((InteractiveBar) obj).getHeight()&&this.getName().equals(((InteractiveBar) obj).getName())){
			return true;
		} else {
			return false;
		}
	}
	
	
}	
