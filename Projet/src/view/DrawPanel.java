package view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import javax.swing.JPanel;

import conf.GeneralConfig;
/**
 * 
 * @author S.Trémouille
 *
 */
public class DrawPanel extends JPanel{
	private static final long serialVersionUID = 6785172435664617770L;
	private String policy = "Arial Unicode MS";
	View view;
	private int xmovingMilestone;
	private int ymovingMilestone;
	private boolean movingMilestone;
	
	private int xmovingSequenceBar,ymovingSequenceBar,widthmovingSequenceBar,heightmovingSequenceBar;
	private int xmovingStartUpTask,ymovingStartUpTask,widthmovingStartUpTask,heightmovingStartUpTask;
	private boolean movingSequenceBar,movingStartUpTask;
	private boolean movingComment;
	private int xmovingComment;
	private int ymovingComment;
	private int widthmovingComment;
	private int heightmovingComment;
	
	
	private Graphics2D g;

	/**
	 * @param view
	 */
	public DrawPanel(View view) {
		this.view = view;
		movingMilestone=false;
		movingSequenceBar = false;
		this.setSize(GeneralConfig.pageWidthRatio, GeneralConfig.pageHeightRatio);
		this.setFocusable(true);
	}
	
	/**
	 * @param x
	 * @param y
	 */
	public void drawMovingMilestone(int x,int y){
		movingMilestone=true;
		xmovingMilestone=x;
		ymovingMilestone=y;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawMovingSequenceBar(int x, int y,int width,int height) {
		movingSequenceBar=true;
		xmovingSequenceBar=x;
		ymovingSequenceBar=y;
		widthmovingSequenceBar=width;
		heightmovingSequenceBar=height;
	}
	
	/**
	 * @param x
	 * @param y
	 * @param width
	 * @param height
	 */
	public void drawMovingComment(int x, int y, int width, int height) {
		movingComment = true;
		xmovingComment = x;
		ymovingComment = y;
		widthmovingComment = width;
		heightmovingComment = height;
	}
	
	public void drawMovingStartUpTask(int x, int y, int width, int height) {
		movingStartUpTask = true;
		xmovingStartUpTask = x;
		ymovingStartUpTask = y;
		widthmovingStartUpTask = width;
		heightmovingStartUpTask = height;
	}

	@Override
	public void paint(Graphics gg) {
		this.g=(Graphics2D)gg;
		if(view.getModel().getMilestones().size()<1){
			view.setWarningMessage("You can put Milestone on the drawing (Drag & Drop from the toolbar OR Right Click -> New Milestone)",Color.red);
		} else if(GeneralConfig.SUDocPath.equals("")){
			view.setWarningMessage("There is no Excel file associated (Settings -> Preferences -> Start Up Documentation File Location)",Color.red);
		} else if(GeneralConfig.databases.size()==0){
			view.setWarningMessage("There is no ICAPS database connected (Settings -> Preferences -> Databases -> New)",Color.red);
		} else if(GeneralConfig.zoom>100){
			view.setWarningMessage("Disposition of element on the draw may be not reflect disposition in zommed position (only zoomed disposition is taken into account during printing)",Color.orange);
		}
		
		
		if(GeneralConfig.graphicQuality||GeneralConfig.printMode){
        		g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        		g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        		g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        		g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_ON);
		} else {
		    	g.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_OFF);
		    	g.setRenderingHint(RenderingHints.KEY_TEXT_ANTIALIASING, RenderingHints.VALUE_TEXT_ANTIALIAS_OFF);
    			g.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_SPEED);
    			g.setRenderingHint(RenderingHints.KEY_FRACTIONALMETRICS, RenderingHints.VALUE_FRACTIONALMETRICS_OFF);
		}
		
		super.paint(g);
		//Draw the alignement grid
		g.setColor(Color.LIGHT_GRAY);
		if(!GeneralConfig.printMode){
		for(int grid=0;grid<100;grid++){
			for(int i = 0;i<GeneralConfig.pageHeight;i++){
				g.drawLine((int)Math.round(grid*GeneralConfig.cellWidth),i , (int)Math.round(grid*GeneralConfig.cellWidth),i+1);
				i=i+9;
			}
			for(int i = 0;i<GeneralConfig.pageWidth;i++){
				g.drawLine(i ,(int)Math.round(grid*GeneralConfig.cellHeight),i+1, (int)Math.round(grid*GeneralConfig.cellHeight));
				i=i+9;
			}
		}
		}
		
		//Draw the title
		view.model.getTitleBar().paint(g);
		//Draw paper border
		g.setColor(Color.black);
		g.drawRect(0, 0, GeneralConfig.pageWidth, GeneralConfig.pageHeight);
		
		g.setFont(new Font("Arial Unicode MS", Font.TRUETYPE_FONT, (int) (GeneralConfig.pageHeightRatio/40.0)));
		
		
		Font fonte = new Font(policy, Font.TRUETYPE_FONT, (int) (GeneralConfig.pageHeightRatio/60.0));
		g.setFont(fonte);
		// Sequence drawing
		for (Integer s : view.getModel().getSequences().descendingKeySet()) {
			view.getModel().getSequence(s).paint(g);
			
		}
		
		//Milestones Drawing
		for (Integer s : view.getModel().getMilestones().descendingKeySet()) {
			view.getModel().getMilestone(s).paint(g);
		}
		
		//Comments drawing
		for(Integer commentId : view.getModel().getComments().descendingKeySet()){
			view.getModel().getComment(commentId).paint(g);
		}
		
		//Start Up Task drawing
		for(Integer startUpTaskId : view.getModel().getStartUpTasks().descendingKeySet()){
			view.getModel().getStartUpTask(startUpTaskId).paint(g);
		}
		
		if(movingMilestone){
			Graphics2D g2d = (Graphics2D) g.create();
			g2d.setColor(Color.black);
			g2d.drawRoundRect(xmovingMilestone, ymovingMilestone, GeneralConfig.milestoneWidth, GeneralConfig.milestoneHeight,GeneralConfig.roundCoeff,GeneralConfig.roundCoeff);
		}
		
		if(movingSequenceBar){
			g.setColor(Color.black);
			g.drawRect(xmovingSequenceBar, ymovingSequenceBar, widthmovingSequenceBar, heightmovingSequenceBar);
		}
		
		if(movingComment){
			g.setColor(Color.black);
			g.drawRect(xmovingComment, ymovingComment, widthmovingComment, heightmovingComment);
		}
		
		if(movingStartUpTask){
			g.setColor(Color.black);
			g.drawRect(xmovingStartUpTask, ymovingStartUpTask, widthmovingStartUpTask, heightmovingStartUpTask);
		}
		
		//title block painting
		view.getModel().getProjectTitleBlock().paint(g);
	}

	/**
	 * To invoke when dragging on main screen is stop
	 */
	public void notAnyMoreMoving() {
		movingMilestone=false;
		movingSequenceBar=false;
		movingComment=false;
		movingStartUpTask=false;
	}

	
	@Override
	public Graphics getGraphics(){
		return this.g;
	}

	
}
