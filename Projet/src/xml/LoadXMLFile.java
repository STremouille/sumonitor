package xml;

import java.awt.Color;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Scanner;
import java.util.TreeMap;

import javax.swing.text.StyledEditorKit.BoldAction;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import model.Comment;
import model.Milestone;
import model.ProjectTitleBlock;
import model.SequenceBar;
import model.StartUpSequence;
import model.StartUpStep;
import model.TitleBar;
import model.TitleBar.Style;

import org.w3c.dom.DOMException;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

import conf.GeneralConfig;
import conf.Utils;
import database.Database;
import database.LINK_TYPE;
/**
 * 
 * class use in order to load the project from a xml file
 * @author S.Trémouille
 * 
 */
public class LoadXMLFile {
	private double zoomValue;
	private int milestoneGridSize;
	private boolean graphicQuality;
	private float pageWidthCoeff, pageHeightCoeff;
	public ArrayList<String> tmpStepField;
	public TreeMap<Integer,Boolean> tmpStepFieldVisible;
	private Integer stepCharacterLimit;
	
	/**
	 * @param path
	 * @return StartUpSequence model
	 * @throws ParserConfigurationException
	 * @throws SAXException
	 * @throws IOException
	 * @throws DOMException
	 * @throws ParseException
	 */
	public StartUpSequence doIt(String path) throws ParserConfigurationException, SAXException, IOException, DOMException, ParseException{
		GeneralConfig.loadMode=true;
		//pending connection because it only can be surely loaded when all milestones have been loaded
		TreeMap<Integer, ArrayList<Integer>> pendingConnectionFromMilestoneToMilestone = new TreeMap<Integer, ArrayList<Integer>>();
		TreeMap<Integer, ArrayList<Integer>> pendingConnectionFromMilestoneToSUT = new TreeMap<Integer, ArrayList<Integer>>();
		TreeMap<Integer, ArrayList<Integer>> pendingConnectionFromSUTtoSUT = new TreeMap<Integer, ArrayList<Integer>>();
		TreeMap<Integer, ArrayList<Integer>> pendingConnectionFromSUTtoMilestone = new TreeMap<Integer, ArrayList<Integer>>();
		
		
		File fXmlFile = new File(path);
		DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
		DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
		Document doc = dBuilder.parse(fXmlFile);
		
		//normalization
		doc.getDocumentElement().normalize();
		
		this.zoomValue=Double.valueOf(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("z"));
		
		
		this.graphicQuality=Boolean.valueOf(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("graphicQuality"));
		
		this.milestoneGridSize=(int)Integer.valueOf(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("milestoneGridSize"));
		
		if(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("stepCharacterLimit")!=""){
			this.stepCharacterLimit=Integer.valueOf(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("stepCharacterLimit"));
		}
		
		if((((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("pageHeightCoeff")!="")&&(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("pageWidthCoeff")!="")){
			this.pageHeightCoeff=Float.valueOf(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("pageHeightCoeff"));
			this.pageWidthCoeff=Float.valueOf(((Element) doc.getElementsByTagName("start-up-sequence").item(0)).getAttribute("pageWidthCoeff"));
		}
		
		
		NodeList NListStepFields = doc.getElementsByTagName("stepField");
		tmpStepField = new ArrayList<String>();
		tmpStepFieldVisible = new TreeMap<Integer, Boolean>();
		for(int i=0;i<NListStepFields.getLength();i++){
			Node nNode = NListStepFields.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				tmpStepField.add(Integer.valueOf(eElement.getAttribute("index")), eElement.getAttribute("content"));
				tmpStepFieldVisible.put(Integer.valueOf(eElement.getAttribute("index")),Boolean.valueOf(eElement.getAttribute("visible")));
			}
		}
		
		
		//last update dates
		SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yyyy");
		if((((Element) doc.getElementsByTagName("start-up-sequence").item(0))).getAttribute("lastDocUpdateDate")!="")
			GeneralConfig.lastDocUpdateDate=sdf.parse((((Element) doc.getElementsByTagName("start-up-sequence").item(0))).getAttribute("lastDocUpdateDate"));
		else
			GeneralConfig.lastDocUpdateDate=new Date(0);
		
		StartUpSequence loadedModel = new StartUpSequence("",true);
		
		//Title Bar
		TitleBar tbar = new TitleBar("", (int)(GeneralConfig.pageWidth*0.333)/2, 0, (int)(GeneralConfig.pageWidth*0.666), (int)(GeneralConfig.pageHeight*0.05));
		if(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("style").equals("CLASSIC")){
		    tbar.setStyle(Style.CLASSIC);
		} else if(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("style").equals("LARGE")){
		    tbar.setStyle(Style.LARGE);
		} else {
		    tbar.setStyle(Style.NO_STYLE);
		}
		GeneralConfig.titleBarDisplayDates=Boolean.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("displayPrintDates"));
		GeneralConfig.titleEnable=Boolean.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("enable"));
		GeneralConfig.centeredTitleBar=Boolean.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("center"));
		GeneralConfig.leftAlignedTitleBar=Boolean.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("left"));
		tbar.setX(Double.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("x")));
		tbar.setName(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("name"));
		tbar.setWidth(Double.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("width")));
		tbar.setHeight(Double.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("height")));
		Color color = new Color(Integer.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("r")), Integer.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("g")), Integer.valueOf(((Element) doc.getElementsByTagName("titleBar").item(0)).getAttribute("b")));
		
		tbar.setColor(color);
		loadedModel.setTitleBar(tbar);
		
		//Conf
		GeneralConfig.SUDocPath=doc.getElementsByTagName("doc").item(0).getTextContent();
		//Cr�ation du log file avec le contenue de la sauvegarde
		//String log = doc.getElementsByTagName("logDoc").item(0).getTextContent();
		//Creation of log file
		/*File logFile = new File("logSUDoc.log");
		FileOutputStream fos = new FileOutputStream(logFile);
		fos.write(log.getBytes());
		fos.flush();
		fos.close();*/
		
		//Load Title Block
		//Project Title Block
		GeneralConfig.titleBlockEnable=Boolean.valueOf(((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getAttribute("enable"));
		GeneralConfig.titleBlockRightAligned=Boolean.valueOf(((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getAttribute("rightAligned"));

		ProjectTitleBlock ptb = new ProjectTitleBlock();
		ptb.getCartouche().put("companyLogo", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("companyLogo").item(0).getTextContent());
		ptb.getCartouche().put("projectLogo", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("projectLogo").item(0).getTextContent());
		ptb.getCartouche().put("contractorLogo", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("contractorLogo").item(0).getTextContent());
		ptb.getCartouche().put("contractNumber", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("contractNumber").item(0).getTextContent());
		ptb.getCartouche().put("contractorName", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("contractorName").item(0).getTextContent());
		ptb.getCartouche().put("customerField", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("customerField").item(0).getTextContent());
		ptb.getCartouche().put("documentTitle", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("documentTitle").item(0).getTextContent());
		ptb.getCartouche().put("studieType", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("studieType").item(0).getTextContent());
		ptb.getCartouche().put("facilityName", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("facilityName").item(0).getTextContent());
		ptb.getCartouche().put("designation", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("designation").item(0).getTextContent());
		ptb.getCartouche().put("drawingType", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("drawingType").item(0).getTextContent());
		ptb.getCartouche().put("docType", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("docType").item(0).getTextContent());
		ptb.getCartouche().put("systSSsyst", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("systSSsyst").item(0).getTextContent());
		ptb.getCartouche().put("discipline", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("discipline").item(0).getTextContent());
		ptb.getCartouche().put("electronicFilename", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("electronicFilename").item(0).getTextContent());
		ptb.getCartouche().put("companyRef", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("companyRef").item(0).getTextContent());
		ptb.getCartouche().put("contractorRef", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("contractorRef").item(0).getTextContent());
		ptb.getCartouche().put("revision", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("revision").item(0).getTextContent());
		ptb.getCartouche().put("scale", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("scale").item(0).getTextContent());
		ptb.getCartouche().put("folio", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("folio").item(0).getTextContent());
		ptb.getCartouche().put("misc", ((Element) doc.getElementsByTagName("projectTitleBlock").item(0)).getElementsByTagName("misc").item(0).getTextContent());
		
		//R�vision
		ArrayList<TreeMap<String, String>> revisions=new ArrayList<TreeMap<String,String>>();
		NodeList nList = ((Element) doc.getElementsByTagName("revisions").item(0)).getElementsByTagName("revision");
		for(int i=0;i<nList.getLength();i++){
			Node nNode = nList.item(i);
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				TreeMap<String, String> rev = new TreeMap<String, String>();
				rev.put("rev", eElement.getAttribute("rev"));
				rev.put("date", eElement.getAttribute("date"));
				rev.put("status", eElement.getAttribute("status"));
				rev.put("revisionMemo", eElement.getAttribute("revisionMemo"));
				rev.put("issued", eElement.getAttribute("issued"));
				rev.put("checked", eElement.getAttribute("checked"));
				rev.put("approved", eElement.getAttribute("approved"));
				revisions.add(rev);
			}
		}	
		
		loadedModel.setRevisions(revisions);
		loadedModel.setCartouche(ptb.getCartouche());
		
		
		//Création du log file avec le contenue de la sauvegarde
		GeneralConfig.subSysOperated= doc.getElementsByTagName("subsystemsOperated").item(0).getTextContent();
		
		
		//Model
		//milestone load
		NodeList nListt = doc.getElementsByTagName("milestone");
		for(int i=0;i<nListt.getLength();i++){
			Node nNode = nListt.item(i);
			Integer id=-1;
			Milestone m = new Milestone("");
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				id = Integer.valueOf(eElement.getAttribute("id"));
				m.setIndeterminated(Boolean.valueOf(eElement.getAttribute("indeterminated")));
				m.setInTADAILYREPORT(Boolean.valueOf(eElement.getAttribute("commCalculable")));
				m.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
				m.setX(Double.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent()));
				m.setY(Double.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent()));
				m.setDescription(eElement.getElementsByTagName("description").item(0).getTextContent());
				m.setPrecommProgress(Double.valueOf(eElement.getElementsByTagName("precommProgress").item(0).getTextContent()));
				m.setCommProgress(Double.valueOf(eElement.getElementsByTagName("commProgress").item(0).getTextContent()));
				m.setDocumentationProgress(Double.valueOf(eElement.getElementsByTagName("docProgress").item(0).getTextContent()));
				m.setPunchOpened(Integer.valueOf(eElement.getElementsByTagName("PLOpened").item(0).getTextContent()));
				m.setPunchAOpened(Integer.valueOf(eElement.getElementsByTagName("PLAOpened").item(0).getTextContent()));
				m.setPunchBOpened(Integer.valueOf(eElement.getElementsByTagName("PLBOpened").item(0).getTextContent()));
				m.setPunchCOpened(Integer.valueOf(eElement.getElementsByTagName("PLCOpened").item(0).getTextContent()));
				m.setAOCProgress(Double.valueOf(eElement.getElementsByTagName("AOCProgress").item(0).getTextContent()));
				m.setSsRFCProgress(Double.valueOf(eElement.getElementsByTagName("RFCProgress").item(0).getTextContent()));
				if((eElement.getElementsByTagName("stepProgress").item(0)!=null)&&(!eElement.getElementsByTagName("stepProgress").item(0).getTextContent().equals("null"))){	
					m.setStepProgress(Double.valueOf(eElement.getElementsByTagName("stepProgress").item(0).getTextContent()));
				}
				SimpleDateFormat formatter = new SimpleDateFormat("yyyy/MM/dd");
				m.setTargetDate(formatter.parse(eElement.getElementsByTagName("targetDate").item(0).getTextContent()));
				if(eElement.getElementsByTagName("scopeDoneDate").item(0).getTextContent()!="")
					m.setScopeDoneDate(formatter.parse(eElement.getElementsByTagName("scopeDoneDate").item(0).getTextContent()));
				m.setOperated(Boolean.valueOf(eElement.getElementsByTagName("operated").item(0).getTextContent()));
				m.setSsNumber(Integer.valueOf(eElement.getElementsByTagName("subsysNumber").item(0).getTextContent()));
				m.setSsOperatedProgress(Double.valueOf(eElement.getElementsByTagName("subsysOperatedProgress").item(0).getTextContent()));
				
				String dest = eElement.getElementsByTagName("dest").item(0).getTextContent();
				Scanner sc = new Scanner(dest);
				sc.useDelimiter(" con ");
				ArrayList<Integer> destinators = new ArrayList<Integer>();
				while(sc.hasNext()){
					destinators.add(Integer.valueOf(sc.next()));
				}
				pendingConnectionFromMilestoneToMilestone.put(id, destinators);
				sc.close();
				
				String destSUT = eElement.getElementsByTagName("destSUT").item(0).getTextContent();
				sc = new Scanner(destSUT);
				sc.useDelimiter(" con ");
				ArrayList<Integer> destinatorsSUT = new ArrayList<Integer>();
				while(sc.hasNext()){
					destinatorsSUT.add(Integer.valueOf(sc.next()));
				}
				pendingConnectionFromMilestoneToSUT.put(id, destinatorsSUT);
				sc.close();
			}
			loadedModel.getMilestones().put(id,m);
		}
		
		////////////////////////////////////////////////////
		////START UP TASK LOADING
		////////////////////////////////////////////////////
		NodeList nListStartUpTask = doc.getElementsByTagName("startUpTask");
		for(int i=0;i<nListStartUpTask.getLength();i++){
			Node nNode = nListStartUpTask.item(i);
			StartUpStep sut = new StartUpStep("",0,0,0,0);
			int id=-1;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				id = Integer.valueOf(eElement.getAttribute("id"));
				sut.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
				
				if(eElement.getElementsByTagName("secondLine").item(0)!=null){
					sut.setSecondLine(eElement.getElementsByTagName("secondLine").item(0).getTextContent());
				}
				
				sut.setX(Double.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent()));
				sut.setY(Double.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent()));
				sut.setWidth(Double.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()));
				sut.setHeight(Double.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent()));
				sut.setOperated(Boolean.valueOf(eElement.getElementsByTagName("operated").item(0).getTextContent()));
				sut.setRelatedToThisMilestone(String.valueOf(eElement.getElementsByTagName("milestoneRelated").item(0).getTextContent()));
				sut.setShapeName(String.valueOf(eElement.getElementsByTagName("shape").item(0).getTextContent()));
				Element bgColor = (Element) eElement.getElementsByTagName("backgroundColor").item(0);
				sut.setColor(new Color(Integer.valueOf(bgColor.getAttribute("r")), Integer.valueOf(bgColor.getAttribute("g")), Integer.valueOf(bgColor.getAttribute("b"))));
				
				Element attr = (Element) eElement.getElementsByTagName("attr").item(0);
				if(tmpStepField.size()>0){
					Iterator<String> it = tmpStepField.iterator();
					while(it.hasNext()){
						String key = it.next();
						if(attr.getAttribute(key)!="")
							sut.getAttr().put(key, attr.getAttribute(key));
					}
				}
				
				sut.setLocalAttrRule(Boolean.valueOf(eElement.getElementsByTagName("localRules").item(0).getTextContent()));
				
				
				Element localAttr = (Element) eElement.getElementsByTagName("localAttr").item(0);
				if(tmpStepField.size()>0){
					Iterator<String> it = tmpStepField.iterator();
					while(it.hasNext()){
						String key = it.next();
						if(attr.getAttribute(key)!="")
							sut.getLocalAttrToDisplay().put(key, Boolean.valueOf(localAttr.getAttribute(key)));
					}
				}
				
				String dest = eElement.getElementsByTagName("dest").item(0).getTextContent();
				Scanner sc = new Scanner(dest);
				sc.useDelimiter(" con ");
				ArrayList<Integer> destinators = new ArrayList<Integer>();
				while(sc.hasNext()){
					destinators.add(Integer.valueOf(sc.next()));
				}
				pendingConnectionFromSUTtoMilestone.put(id, destinators);
				sc.close();
				
				String destSUT = eElement.getElementsByTagName("destSUT").item(0).getTextContent();
				sc = new Scanner(destSUT);
				sc.useDelimiter(" con ");
				ArrayList<Integer> destinatorsSUT = new ArrayList<Integer>();
				while(sc.hasNext()){
					destinatorsSUT.add(Integer.valueOf(sc.next()));
				}
				pendingConnectionFromSUTtoSUT.put(id, destinatorsSUT);
				sc.close();
				
								
			}
			loadedModel.getStartUpTasks().put(id, sut);
		}
		
		
		//CONNECTIONS : MILESTONE -> MILESTONE
		for(int id : pendingConnectionFromMilestoneToMilestone.keySet()){
			Milestone m = loadedModel.getMilestone(id);
			Iterator<Integer> it = pendingConnectionFromMilestoneToMilestone.get(id).iterator();
			while(it.hasNext()){
				m.addDest(loadedModel.getMilestone(it.next()));
			}
		}
		
		//CONNECTIONS : MILESTONE -> START UP TASK
		for(int id : pendingConnectionFromMilestoneToSUT.keySet()){
			Milestone m = loadedModel.getMilestone(id);
			Iterator<Integer> it = pendingConnectionFromMilestoneToSUT.get(id).iterator();
			while(it.hasNext()){
				m.addDestSUT(loadedModel.getStartUpTask(it.next()));
			}
		}
		
		//CONNECTIONS : START UP TASK -> MILESTONE
		for(int id : pendingConnectionFromSUTtoSUT.keySet()){
			StartUpStep sut = loadedModel.getStartUpTask(id);
			Iterator<Integer> it = pendingConnectionFromSUTtoSUT.get(id).iterator();
			while(it.hasNext()){
				sut.addDestSUT(loadedModel.getStartUpTask(it.next()));
			}
		}
		
		//CONNECTIONS : START UP TASK -> START UP TASK
		for(int id : pendingConnectionFromSUTtoMilestone.keySet()){
			StartUpStep sut = loadedModel.getStartUpTask(id);
			Iterator<Integer> it = pendingConnectionFromSUTtoMilestone.get(id).iterator();
			while(it.hasNext()){
				sut.addDest(loadedModel.getMilestone(it.next()));
			}
		}
		
		//sequence load
		NodeList nListSequenceBar = doc.getElementsByTagName("sequenceBar");
		for(int i=0;i<nListSequenceBar.getLength();i++){
			Node nNode = nListSequenceBar.item(i);
			SequenceBar sb = new SequenceBar("",0,0,0,0,0);
			int id=-1;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				id = Integer.valueOf(eElement.getAttribute("id"));
				boolean c = Boolean.valueOf(eElement.getAttribute("cAligned"));
				boolean l = Boolean.valueOf(eElement.getAttribute("lAligned"));
				if(l){
					sb.setLeftAligned();
				} else if(c) {
					sb.setCenterAligned();
				} else if(!c&&!l){
					sb.setRightAligned();
				}
				sb.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
				sb.setX(Double.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent()));
				sb.setY(Double.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent()));
				sb.setWidth(Double.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()));
				sb.setHeight(Double.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent()));
				sb.setExtendedHeight(Double.valueOf(eElement.getElementsByTagName("extendedHeight").item(0).getTextContent()));
				
				Element bgColor = (Element) eElement.getElementsByTagName("backgroundColor").item(0);
				sb.setColor(new Color(Integer.valueOf(bgColor.getAttribute("r")), Integer.valueOf(bgColor.getAttribute("g")), Integer.valueOf(bgColor.getAttribute("b"))));
				
				Element dlColor = (Element) eElement.getElementsByTagName("dashedLineColor").item(0);
				sb.setDottedLineColor(new Color(Integer.valueOf(dlColor.getAttribute("r")), Integer.valueOf(dlColor.getAttribute("g")), Integer.valueOf(dlColor.getAttribute("b"))));
								
			}
			loadedModel.getSequences().put(id, sb);
		}
		
		//Comment load
		NodeList nListComment = doc.getElementsByTagName("comment");
		for(int i=0;i<nListComment.getLength();i++){
			Node nNode = nListComment.item(i);
			Comment c = new Comment("", 0, 0, 0, 0);
			int id=-1;
			if (nNode.getNodeType() == Node.ELEMENT_NODE) {
				Element eElement = (Element) nNode;
				id = Integer.valueOf(eElement.getAttribute("id"));
				c.setName(eElement.getElementsByTagName("name").item(0).getTextContent());
				c.setX(Double.valueOf(eElement.getElementsByTagName("x").item(0).getTextContent()));
				c.setY(Double.valueOf(eElement.getElementsByTagName("y").item(0).getTextContent()));
				c.setWidth(Double.valueOf(eElement.getElementsByTagName("width").item(0).getTextContent()));
				c.setHeight(Double.valueOf(eElement.getElementsByTagName("height").item(0).getTextContent()));
				
				Element bgColor = (Element) eElement.getElementsByTagName("backgroundColor").item(0);
				c.setColor(new Color(Integer.valueOf(bgColor.getAttribute("r")), Integer.valueOf(bgColor.getAttribute("g")), Integer.valueOf(bgColor.getAttribute("b"))));
				
			}
		}
		
		//ICAPS
		
		//Configuration loading
		NodeList nListIcaps = (NodeList) doc.getElementsByTagName("icaps").item(0);
		if (((Node) nListIcaps).getNodeType() == Node.ELEMENT_NODE) {
			Element eElement = (Element) nListIcaps;
						
			//DATABASES LOADING
			GeneralConfig.databases = new ArrayList<Database>();
			NodeList nListDB = doc.getElementsByTagName("database");
			for(int i=0;i<nListDB.getLength();i++){
				Node nNode = nListDB.item(i);
				Database dbTmp = new Database();
				if ( nNode.getNodeType() == Node.ELEMENT_NODE) {
					Element eElement1 = (Element) nNode;
					if(eElement1.getAttribute("connexionType").equals("ODBC")){
						dbTmp.setConnexionType(LINK_TYPE.ODBC);
					} else if(eElement1.getAttribute("connexionType").equals("TCPIP")){
						dbTmp.setConnexionType(LINK_TYPE.TCPIP);
					} else if(eElement1.getAttribute("connexionType").equals("WINDOWS_AUTH")){
						dbTmp.setConnexionType(LINK_TYPE.WINDOWS_AUTH);
					}
					dbTmp.setAlias(eElement1.getAttribute("alias"));
					dbTmp.setActivated(Boolean.valueOf(eElement1.getAttribute("activated")));
					dbTmp.setOdbcLinkName(eElement1.getAttribute("odbcLinkName"));
					dbTmp.setLogin(eElement1.getAttribute("login"));
					dbTmp.setPassword(Utils.rot13(eElement1.getAttribute("password")));
					dbTmp.setMilestoneColumn(eElement1.getAttribute("milestoneColumn"));
					dbTmp.setServerURL(eElement1.getAttribute("serverName"));
					dbTmp.setDatabaseName(eElement1.getAttribute("dbName"));
					dbTmp.setInstanceName(eElement1.getAttribute("instanceName"));
					if(eElement1.getAttribute("lastDbUpdate")!="")
					    dbTmp.setLastUpdateDate(new Date(Long.valueOf(eElement1.getAttribute("lastDbUpdate"))));
					GeneralConfig.databases.add(dbTmp);
				}
			}
		}
		
		//Element displayed on milestone
		GeneralConfig.plDisplayed=Boolean.valueOf(((Element) doc.getElementsByTagName("ElementDisplayedOnMilestone").item(0)).getAttribute("PL"));
		GeneralConfig.ssAOCdisplayed=Boolean.valueOf(((Element) doc.getElementsByTagName("ElementDisplayedOnMilestone").item(0)).getAttribute("AOC"));
		GeneralConfig.commProgressDisplayed=Boolean.valueOf(((Element) doc.getElementsByTagName("ElementDisplayedOnMilestone").item(0)).getAttribute("Comm"));
		GeneralConfig.docProgressDisplayed=Boolean.valueOf(((Element) doc.getElementsByTagName("ElementDisplayedOnMilestone").item(0)).getAttribute("Doc"));
		if(((Element) doc.getElementsByTagName("ElementDisplayedOnMilestone").item(0)).getAttribute("Step")!="")
			GeneralConfig.stepProgress=Boolean.valueOf(((Element) doc.getElementsByTagName("ElementDisplayedOnMilestone").item(0)).getAttribute("Step"));
		
		GeneralConfig.targetAccuracy=Integer.valueOf(doc.getElementsByTagName("target").item(0).getTextContent());
		
		if(doc.getElementsByTagName("labelForDoc").item(0)!=null)
		    GeneralConfig.labelForDoc=String.valueOf(doc.getElementsByTagName("labelForDoc").item(0).getTextContent());
		
		//Expert Mode
		GeneralConfig.expertMode=Boolean.valueOf(doc.getElementsByTagName("expertMode").item(0).getTextContent());
		GeneralConfig.excelFileCell=doc.getElementsByTagName("cellReference").item(0).getTextContent();
		
		//Contractor Logo
		byte[] toParseCLArray = Base64.decode(doc.getElementsByTagName("contractorLogoParse").item(0).getTextContent());
		GeneralConfig.contractorLogo = new byte[toParseCLArray.length];
		GeneralConfig.contractorLogo=toParseCLArray;
//		
//		//Company Logo
		byte[] toParseCOArray = Base64.decode(doc.getElementsByTagName("companyLogoParse").item(0).getTextContent());
		GeneralConfig.companyLogo = new byte[toParseCOArray.length];
		GeneralConfig.companyLogo=toParseCOArray;
//		
//		//Project Logo
		byte[] toParsePArray = Base64.decode(doc.getElementsByTagName("projectLogoParse").item(0).getTextContent());
		GeneralConfig.projectLogo = new byte[toParsePArray.length];
		GeneralConfig.projectLogo=toParsePArray;
		
		//Log Doc 
		GeneralConfig.logSUDoc=doc.getElementsByTagName("logSUActivitiesProgress").item(0).getTextContent();
		
		//End Loading
		GeneralConfig.loadMode=false;
		return loadedModel;
	}
	
	/**
	 * @return zoom value of SUS to load
	 */
	public double getZoomValue(){
		return zoomValue;
	}
	
	/**
	 * @return milestone grid size value of SUS to load
	 */
	public int getMilestoneGridSize(){
	    return milestoneGridSize;
	}
	
	/**
	 * @return graphicQuality
	 */
	public boolean getGraphicQuality(){
	    return graphicQuality;
	}
	
	public float getPageWidthCoeff(){
		if(pageWidthCoeff>0)
			return pageWidthCoeff;
		else
			return (float) 1.0;
	}
	
	public int getStepCharacterLimit(){
		if(stepCharacterLimit!=null){
			return this.stepCharacterLimit;
		} else {
			return 12;
		}
		
	}
	
	public float getPageHeightCoeff(){
		if(pageHeightCoeff>0)
			return pageHeightCoeff;
		else
			return (float) 1.0;
	}
	
	public ArrayList<String> getStepFields(){
		return tmpStepField;
	}
	
	public TreeMap<Integer, Boolean> getStepsFieldVisible(){
		return tmpStepFieldVisible;
	}
	
}
