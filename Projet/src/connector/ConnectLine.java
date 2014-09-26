package connector;

import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Rectangle;
import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.JComponent;

import conf.GeneralConfig;



/**
 * @author Stanislav Lapitsky
 * @author Samuel Trémouille
 *
 */
public class ConnectLine extends JComponent{
    	private static final long serialVersionUID = -8654434196930784910L;
	/**
	 * LINE_TYPE_SIMPLE
	 */
	public static final int LINE_TYPE_SIMPLE = 0;
	/**
	 * LINE_TYPE_RECT_1BREAK
	 */
	public static final int LINE_TYPE_RECT_1BREAK = 1;
	/**
	 * LINE_TYPE_RECT_2BREAK
	 */
	public static final int LINE_TYPE_RECT_2BREAK = 2;

	/**
	 * LINE_START_HORIZONTAL
	 */
	public static final int LINE_START_HORIZONTAL = 0;
	/**
	 * LINE_START_VERTICAL
	 */
	public static final int LINE_START_VERTICAL = 1;

	/**
	 * LINE_ARROW_NONE
	 */
	public static final int LINE_ARROW_NONE = 0;
	/**
	 * LINE_ARROW_SOURCE
	 */
	public static final int LINE_ARROW_SOURCE = 1;
	/**
	 * LINE_ARROW_DEST
	 */
	public static final int LINE_ARROW_DEST = 2;
	/**
	 * LINE_ARROW_BOTH
	 */
	public static final int LINE_ARROW_BOTH = 3;
	
	//Use to calculate the bounds of a line
	private ArrayList<Rectangle> shapes;

	/**
	 * Source line point
	 */
	Point p1;
	/**
	 * Destination line point
	 */
	Point p2;

	/**
	 * Line type can be one of LINE_TYPE_SIMPLE, LINE_TYPE_RECT_1BREAK,
	 * LINE_TYPE_RECT_2BREAK
	 */
	int lineType = LINE_TYPE_SIMPLE;
	/**
	 * for the LINE_TYPE_RECT_2BREAK type the param defines how line should be
	 * rendered
	 */
	int lineStart = LINE_START_HORIZONTAL;
	/**
	 * arrow can be one of following LINE_ARROW_NONE - no arrow
	 * LINE_ARROW_SOURCE - arrow beside source point LINE_ARROW_DEST - arrow
	 * beside dest point LINE_ARROW_BOTH - both source and dest has arrows
	 */
	int lineArrow = LINE_ARROW_NONE;
	/**
	 * Constructs default line
	 */
	private int lineWidth;
	private int lineArrowWidth;

	/**
	 * @param p1
	 * @param p2
	 */
	public ConnectLine(Point p1, Point p2) {
		this(p1, p2, LINE_TYPE_SIMPLE, LINE_START_HORIZONTAL, LINE_ARROW_NONE);
	}

	/**
	 * Constructs line with specified params
	 * 
	 * @param p1
	 *            Point start
	 * @param p2
	 *            Point end
	 * @param lineType
	 *            int type of line (LINE_TYPE_SIMPLE, LINE_TYPE_RECT_1BREAK,
	 *            LINE_TYPE_RECT_2BREAK)
	 * @param lineStart
	 *            int for the LINE_TYPE_RECT_2BREAK type the param defines how
	 *            line should be rendered
	 * @param lineArrow
	 *            int defines line arrow type
	 */
	public ConnectLine(Point p1, Point p2, int lineType, int lineStart, int lineArrow) {
		this.p1 = p1;
		this.p2 = p2;
		this.lineType = lineType;
		this.lineStart = lineStart;
		this.lineArrow = lineArrow;
		this.lineWidth = GeneralConfig.milestoneHeight / 35;
		this.lineArrowWidth = lineWidth * 6;
		this.shapes = new ArrayList<Rectangle>();
	}

	/**
	 * Paints the line with specified params
	 * 
	 * @param g2d
	 *            Graphics2D
	 */
	public void paint(Graphics2D g2d) {
		switch (lineType) {
			case LINE_TYPE_SIMPLE:
				paintSimple(g2d);
				break;
			case LINE_TYPE_RECT_1BREAK:
				paint1Break(g2d);
				break;
			case LINE_TYPE_RECT_2BREAK:
				paint2Breaks(g2d);
				break;
        		default:
        		    	paintSimple(g2d);
        			break;
		}
	}

	protected void paintSimple(Graphics2D g2d) {
		g2d.drawLine(p1.x, p1.y, p2.x, p2.y);
		switch (lineArrow) {
			case LINE_ARROW_DEST:
				paintArrow(g2d, p1, p2);
				break;
			case LINE_ARROW_SOURCE:
				paintArrow(g2d, p2, p1);
				break;
			case LINE_ARROW_BOTH:
				paintArrow(g2d, p1, p2);
				paintArrow(g2d, p2, p1);
				break;
			default:
				break;
		}
	}

	protected void paintArrow(Graphics2D g2d, Point p1, Point p2) {
		paintArrow(g2d, p1, p2, getRestrictedArrowWidth(p1, p2));
	}

	protected void paintArrow(Graphics2D g2d, Point p1, Point p2, int width) {
		Point2D.Float pp1 = new Point2D.Float(p1.x, p1.y);
		Point2D.Float pp2 = new Point2D.Float(p2.x, p2.y);
		Point2D.Float left = getLeftArrowPoint(pp1, pp2, width);
		Point2D.Float right = getRightArrowPoint(pp1, pp2, width);
		Point mid = p2;

		//		System.out.println(left.x + " : " + left.y);
		//		System.out.println(mid.x + " : " + mid.y);
		//		System.out.println(right.x + " : " + right.y);
		int[] x = new int[3];
		int[] y = new int[3];
		x[0] = Math.round(left.x);
		x[1] = Math.round(right.x);
		x[2] = mid.x;
		y[0] = Math.round(left.y);
		y[1] = Math.round(right.y);
		y[2] = mid.y;
		g2d.fillPolygon(x, y, 3);

	}

	protected void paint1Break(Graphics2D g2d) {
		if (lineStart == LINE_START_HORIZONTAL) {
			//1 --> 2
			if (p1.x < p2.x) {
				//Not use
				if (p1.y > p2.y) {
					//--
					//  |
					//  v
					g2d.fillRect(p1.x, p1.y - lineWidth / 2, p2.x - p1.x + lineWidth / 2, lineWidth);
					g2d.fillRect(p2.x - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y - 10 * lineWidth);
					
					this.shapes.add(new Rectangle(p1.x, p1.y - lineWidth / 2, p2.x - p1.x + lineWidth / 2, lineWidth));
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y - 10 * lineWidth));
				} else {
					//  ^
					//  |
					//--
					g2d.fillRect(p1.x, p1.y - lineWidth / 2, p2.x - p1.x + lineWidth / 2, lineWidth);
					g2d.fillRect(p2.x - lineWidth / 2, p1.y + 2 * lineWidth, lineWidth, p2.y - p1.y - lineWidth);
					
					this.shapes.add(new Rectangle(p1.x, p1.y - lineWidth / 2, p2.x - p1.x + lineWidth / 2, lineWidth));
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, p1.y + 2 * lineWidth, lineWidth, p2.y - p1.y - lineWidth));
				}
			} else if (p1.x > p2.x) {
				if (p1.y > p2.y) {
					//  --
					//  |
					//  v
					g2d.fillRect(p2.x, p1.y - lineWidth / 2, p1.x - p2.x, lineWidth);
					g2d.fillRect(p2.x - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y - lineWidth);
					
					this.shapes.add(new Rectangle(p2.x, p1.y - lineWidth / 2, p1.x - p2.x, lineWidth));
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y - lineWidth));
				} else {
					//  ^
					//  |
					//  --
					g2d.fillRect(p2.x, p1.y - lineWidth / 2, p1.x - p2.x, lineWidth);
					g2d.fillRect(p2.x - lineWidth / 2, p1.y + 2 * lineWidth, lineWidth, p2.y - p1.y - lineWidth);
					
					this.shapes.add(new Rectangle(p2.x, p1.y - lineWidth / 2, p1.x - p2.x, lineWidth));
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, p1.y + 2 * lineWidth, lineWidth, p2.y - p1.y - lineWidth));
				}
			}
			switch (lineArrow) {
				case LINE_ARROW_DEST:
					paintArrow(g2d, new Point(p2.x, p1.y), p2);
					break;
				case LINE_ARROW_SOURCE:
					paintArrow(g2d, new Point(p2.x, p1.y), p1);
					break;
				case LINE_ARROW_BOTH:
					paintArrow(g2d, new Point(p2.x, p1.y), p2);
					paintArrow(g2d, new Point(p2.x, p1.y), p1);
					break;
				default:
					break;
			}
		}
	}

	protected void paint2Breaks(Graphics2D g2d) {
		if (lineStart == LINE_START_HORIZONTAL) {
			if (p1.x < p2.x) {
				if (p1.y < p2.y) {
					//--   
					//  |
					//   -->
					g2d.fillRect(p1.x, p1.y - lineWidth / 2, (p1.x + p2.x) / 2 - p1.x, lineWidth);
					g2d.fillRect((p1.x + p2.x) / 2 - lineWidth / 2, p1.y, lineWidth, p2.y - p1.y);
					g2d.fillRect((p1.x + p2.x) / 2, p2.y - lineWidth / 2, p2.x - (p1.x + p2.x) / 2 - lineWidth, lineWidth);
					
					this.shapes.add(new Rectangle(p1.x, p1.y - lineWidth / 2, (p1.x + p2.x) / 2 - p1.x, lineWidth));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2 - lineWidth / 2, p1.y, lineWidth, p2.y - p1.y));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2, p2.y - lineWidth / 2, p2.x - (p1.x + p2.x) / 2 - lineWidth, lineWidth));
				} else {
					//   -->
					//  |
					//--
					g2d.fillRect(p1.x, p1.y - lineWidth / 2, (p1.x + p2.x) / 2 - p1.x, lineWidth);
					g2d.fillRect((p1.x + p2.x) / 2 - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y);
					g2d.fillRect((p1.x + p2.x) / 2, p2.y - lineWidth / 2, p2.x - (p1.x + p2.x) / 2 - lineWidth, lineWidth);
					
					this.shapes.add(new Rectangle(p1.x, p1.y - lineWidth / 2, (p1.x + p2.x) / 2 - p1.x, lineWidth));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2 - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2, p2.y - lineWidth / 2, p2.x - (p1.x + p2.x) / 2 - lineWidth, lineWidth));
				}
			} else {
				if (p1.y < p2.y) {
					//    --
					//   |
					//<--
					g2d.fillRect(p2.x + 2 * lineWidth, p2.y - lineWidth / 2, (p1.x + p2.x) / 2 - p2.x - 2 * lineWidth, lineWidth);
					g2d.fillRect((p1.x + p2.x) / 2 - lineWidth / 2, p1.y, lineWidth, p2.y - p1.y);
					g2d.fillRect((p1.x + p2.x) / 2, p1.y - lineWidth / 2, p1.x - (p1.x + p2.x) / 2, lineWidth);
					
					this.shapes.add(new Rectangle(p2.x + 2 * lineWidth, p2.y - lineWidth / 2, (p1.x + p2.x) / 2 - p2.x - 2 * lineWidth, lineWidth));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2 - lineWidth / 2, p1.y, lineWidth, p2.y - p1.y));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2, p1.y - lineWidth / 2, p1.x - (p1.x + p2.x) / 2, lineWidth));
				} else {
					//<--
					//   |
					//	  --
					g2d.fillRect(p2.x + 2 * lineWidth, p2.y - lineWidth / 2, (p1.x + p2.x) / 2 - p2.x - 2 * lineWidth, lineWidth);
					g2d.fillRect((p1.x + p2.x) / 2 - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y);
					g2d.fillRect((p1.x + p2.x) / 2, p1.y - lineWidth / 2, p1.x - (p1.x + p2.x) / 2, lineWidth);
					
					this.shapes.add(new Rectangle(p2.x + 2 * lineWidth, p2.y - lineWidth / 2, (p1.x + p2.x) / 2 - p2.x - 2 * lineWidth, lineWidth));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2 - lineWidth / 2, p2.y, lineWidth, p1.y - p2.y));
					this.shapes.add(new Rectangle((p1.x + p2.x) / 2, p1.y - lineWidth / 2, p1.x - (p1.x + p2.x) / 2, lineWidth));
				}
			}
			switch (lineArrow) {
				case LINE_ARROW_DEST:
					paintArrow(g2d, new Point(p1.x + (p2.x - p1.x) / 2, p2.y), p2);
					break;
				case LINE_ARROW_SOURCE:
					paintArrow(g2d, new Point(p1.x + (p2.x - p1.x) / 2, p1.y), p1);
					break;
				case LINE_ARROW_BOTH:
					paintArrow(g2d, new Point(p1.x + (p2.x - p1.x) / 2, p2.y), p2);
					paintArrow(g2d, new Point(p1.x + (p2.x - p1.x) / 2, p1.y), p1);
					break;
				default:
					break;
			}
		} else if (lineStart == LINE_START_VERTICAL) {
			if (p1.x < p2.x) {
				if (p1.y < p2.y) {
					//|
					// --
					//   |
					//   v
					g2d.fillRect(p1.x - lineWidth / 2, p1.y, lineWidth, ((p2.y + p1.y) / 2) - p1.y);
					g2d.fillRect(p1.x, (p1.y + p2.y) / 2 - lineWidth / 2, p2.x - p1.x, lineWidth);
					g2d.fillRect(p2.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p2.y - (p1.y + p2.y) / 2-lineWidth);
					
					this.shapes.add(new Rectangle(p1.x - lineWidth / 2, p1.y, lineWidth, ((p2.y + p1.y) / 2) - p1.y));
					this.shapes.add(new Rectangle(p1.x, (p1.y + p2.y) / 2 - lineWidth / 2, p2.x - p1.x, lineWidth));
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p2.y - (p1.y + p2.y) / 2-lineWidth));
				} else {
					//   ^
					//   |
					// --
					//|  
					g2d.fillRect(p2.x - lineWidth / 2, p2.y+2*lineWidth, lineWidth, ((p2.y + p1.y) / 2) - p2.y-2*lineWidth);
					g2d.fillRect(p1.x, (p1.y + p2.y) / 2 - lineWidth / 2, p2.x - p1.x, lineWidth);
					g2d.fillRect(p1.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p1.y - (p1.y + p2.y) / 2);
					
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, p2.y+2*lineWidth, lineWidth, ((p2.y + p1.y) / 2) - p2.y-2*lineWidth));
					this.shapes.add(new Rectangle(p1.x, (p1.y + p2.y) / 2 - lineWidth / 2, p2.x - p1.x, lineWidth));
					this.shapes.add(new Rectangle(p1.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p1.y - (p1.y + p2.y) / 2));
				}
			} else {
				if (p1.y < p2.y) {
					//   |
					// --
					//|
					//v
					g2d.fillRect(p1.x - lineWidth / 2, p1.y, lineWidth, ((p2.y + p1.y) / 2) - p1.y);
					g2d.fillRect(p2.x, (p2.y + p1.y) / 2 - lineWidth / 2, p1.x - p2.x, lineWidth);
					g2d.fillRect(p2.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p2.y - (p1.y + p2.y) / 2-lineWidth);
					
					this.shapes.add(new Rectangle(p1.x - lineWidth / 2, p1.y, lineWidth, ((p2.y + p1.y) / 2) - p1.y));
					this.shapes.add(new Rectangle(p2.x, (p2.y + p1.y) / 2 - lineWidth / 2, p1.x - p2.x, lineWidth));
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p2.y - (p1.y + p2.y) / 2-lineWidth));
				} else {
					//^
					//|
					// --
					//   |
					g2d.fillRect(p2.x - lineWidth / 2, p2.y+2*lineWidth, lineWidth, ((p2.y + p1.y) / 2) - p2.y-2*lineWidth);
					g2d.fillRect(p2.x, (p2.y + p1.y) / 2 - lineWidth / 2, p1.x - p2.x, lineWidth);
					g2d.fillRect(p1.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p1.y - (p1.y + p2.y) / 2);
					
					this.shapes.add(new Rectangle(p2.x - lineWidth / 2, p2.y+2*lineWidth, lineWidth, ((p2.y + p1.y) / 2) - p2.y-2*lineWidth));
					this.shapes.add(new Rectangle(p2.x, (p2.y + p1.y) / 2 - lineWidth / 2, p1.x - p2.x, lineWidth));
					this.shapes.add(new Rectangle(p1.x - lineWidth / 2, (p1.y + p2.y) / 2, lineWidth, p1.y - (p1.y + p2.y) / 2));
				}
			}
			switch (lineArrow) {
				case LINE_ARROW_DEST:
					paintArrow(g2d, new Point(p2.x, p1.y + (p2.y - p1.y) / 2), p2);
					break;
				case LINE_ARROW_SOURCE:
					paintArrow(g2d, new Point(p1.x, p1.y + (p2.y - p1.y) / 2), p1);
					break;
				case LINE_ARROW_BOTH:
					paintArrow(g2d, new Point(p2.x, p1.y + (p2.y - p1.y) / 2), p2);
					paintArrow(g2d, new Point(p1.x, p1.y + (p2.y - p1.y) / 2), p1);
					break;
				default:
				    	break;
			}
		}
	}

	/**
	 * 
	 * @return LineType
	 */
	public int getLineType()
	{
		return lineType;
	}

	/**
	 * @param lineType
	 */
	public void setLineType(int lineType) {
		this.lineType = lineType;
	}

	/**
	 * @return LineStart
	 */
	public int getLineStart() {
		return lineStart;
	}

	/**
	 * @param start
	 */
	public void setLineStart(int start) {
		lineStart = start;
	}

	/**
	 * @return LineArrow
	 */
	public int getLineArrow() {
		return lineArrow;
	}

	/**
	 * @param arrow
	 */
	public void setLineArrow(int arrow) {
		lineType = lineArrow;
	}

	/**
	 * @return P1
	 */
	public Point getP1() {
		return p1;
	}

	/**
	 * @param p
	 */
	public void setP1(Point p) {
		p1 = p;
	}

	/**
	 * @return P2
	 */
	public Point getP2() {
		return p2;
	}

	/**
	 * @param p
	 */
	public void setP2(Point p) {
		p2 = p;
	}

	// return the middle point of the arrow
	protected Point2D.Float getMidArrowPoint(Point2D.Float p1, Point2D.Float p2, float w) {
		Point2D.Float res = new Point2D.Float();
		//distance between p1 & p2
		float d = Math.round(Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)));

		if (p1.x < p2.x) {
			res.x = p2.x - w * Math.abs(p1.x - p2.x) / d;
		} else {
			res.x = p2.x + w * Math.abs(p1.x - p2.x) / d;
		}

		if (p1.y < p2.y) {
			res.y = p2.y - w * Math.abs(p1.y - p2.y) / d;
		} else {
			res.y = p2.y + w * Math.abs(p1.y - p2.y) / d;
		}

		return res;
	}

	protected Point2D.Float getLeftArrowPoint(Point2D.Float p1, Point2D.Float p2) {
		return getLeftArrowPoint(p1, p2, lineArrowWidth);
	}

	protected Point2D.Float getLeftArrowPoint(Point2D.Float p1, Point2D.Float p2, float w) {
		Point2D.Float res = new Point2D.Float();
		double alpha = Math.PI / 2;
		if (p2.x != p1.x) {
			alpha = Math.atan((p2.y - p1.y) / (p2.x - p1.x));
		}
		alpha += Math.PI / 10;
		float xShift = Math.abs(Math.round(Math.cos(alpha) * w));
		float yShift = Math.abs(Math.round(Math.sin(alpha) * w));
		if (p1.x <= p2.x) {
			res.x = p2.x - xShift;
		} else {
			res.x = p2.x + xShift;
		}
		if (p1.y < p2.y) {
			res.y = p2.y - yShift;
		} else {
			res.y = p2.y + yShift;
		}
		return res;
	}

	protected Point2D.Float getRightArrowPoint(Point2D.Float p1, Point2D.Float p2) {
		return getRightArrowPoint(p1, p2, lineArrowWidth);
	}

	protected Point2D.Float getRightArrowPoint(Point2D.Float p1, Point2D.Float p2, float w) {
		Point2D.Float res = new Point2D.Float();
		double alpha = Math.PI / 2;
		if (p2.x != p1.x) {
			alpha = Math.atan((p2.y - p1.y) / (p2.x - p1.x));
		}
		alpha -= Math.PI / 10;
		float xShift = Math.abs(Math.round(Math.cos(alpha) * w));
		float yShift = Math.abs(Math.round(Math.sin(alpha) * w));
		if (p1.x < p2.x) {
			res.x = p2.x - xShift;
		} else {
			res.x = p2.x + xShift;
		}
		if (p1.y <= p2.y) {
			res.y = p2.y - yShift;
		} else {
			res.y = p2.y + yShift;
		}
		return res;
	}

	protected int getRestrictedArrowWidth(Point p1, Point p2) {
		return Math.min(lineArrowWidth, (int) Math.sqrt((p1.x - p2.x) * (p1.x - p2.x) + (p1.y - p2.y) * (p1.y - p2.y)));
	}
	
	/**
	 * @param p
	 * @return Boolean
	 */
	public boolean belongsTo(Point p){
		Iterator<Rectangle> it = shapes.iterator();
		while(it.hasNext()){
			if(it.next().contains(p))
				return true;
		}
		return false;
	}

	@Override
	public Rectangle getBounds(Rectangle arg0) {
		Rectangle res = new Rectangle();
		Iterator<Rectangle> it = shapes.iterator();
		while(it.hasNext()){
			res.union(it.next());
		}
		return res;
	}
	
	
}
