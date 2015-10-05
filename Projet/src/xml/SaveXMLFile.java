package xml;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.TreeMap;

import javax.imageio.ImageIO;
import javax.swing.JComponent;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import model.Milestone;
import model.StartUpSequence;
import model.StartUpStep;

import org.apache.commons.codec.binary.Base64;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;






import conf.GeneralConfig;
import conf.Utils;
import database.Database;

	/**
	 * Class use in order to save the project in a xml file
	 * @author S.Trémouille
	 */
public class SaveXMLFile {

	/**
	 * @param model
	 * @param path
	 * @return Boolean
	 * @throws ParserConfigurationException
	 * @throws TransformerException
	 * @throws IOException
	 */
	public boolean doIt(StartUpSequence model,String path) throws ParserConfigurationException, TransformerException, IOException{
		DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
		DocumentBuilder db = dbf.newDocumentBuilder();
		
		//root element
		Document doc = db.newDocument();
		Element root = doc.createElement("start-up-sequence");
		doc.appendChild(root);
		
		root.setAttribute("z", GeneralConfig.zoom+"");
		
		root.setAttribute("graphicQuality", String.valueOf(GeneralConfig.graphicQuality));
		
		root.setAttribute("milestoneGridSize", GeneralConfig.milestoneAlignementGridSize+"");
		
		root.setAttribute("pageWidthCoeff", String.valueOf(GeneralConfig.pageWidthCoeff));
		root.setAttribute("pageHeightCoeff", String.valueOf(GeneralConfig.pageHeightCoeff));
		
		root.setAttribute("stepCharacterLimit", String.valueOf(GeneralConfig.stepCharacterLimit));
		
		//Steps Field
		for(int i = 0 ;i< GeneralConfig.stepsField.size();i++){
			Element stepField = doc.createElement("stepField");
			//id
			Attr index = doc.createAttribute("index");
			index.setValue(""+i);
			stepField.setAttributeNode(index);
					
			//content
			Attr content = doc.createAttribute("content");
			content.setValue(GeneralConfig.stepsField.get(i));
			stepField.setAttributeNode(content);
			
			//visible
			Attr visible = doc.createAttribute("visible");
			visible.setValue(String.valueOf(GeneralConfig.stepFieldVisible.get(i)));
			stepField.setAttributeNode(visible);	
					
			root.appendChild(stepField);
		}
		
		//lastUpdateDates
		SimpleDateFormat sdf = new SimpleDateFormat("MMM/dd/yyyy");
		
		Attr lastDocUpdateDate = doc.createAttribute("lastDocUpdateDate");
		if(GeneralConfig.lastDocUpdateDate!=null){
			lastDocUpdateDate.setValue(sdf.format(GeneralConfig.lastDocUpdateDate));
		} else {
			lastDocUpdateDate.setValue("");
		}
		root.setAttributeNode(lastDocUpdateDate);
		
		//title bar
		Element titleBar = doc.createElement("titleBar");
		
		Attr tbstyle = doc.createAttribute("style");
		tbstyle.setValue(""+model.getTitleBar().getStyle());
		titleBar.setAttributeNode(tbstyle);
		
		Attr tbDisplayPrintDates = doc.createAttribute("displayPrintDates");
		tbDisplayPrintDates.setValue(GeneralConfig.titleBarDisplayDates+"");
		titleBar.setAttributeNode(tbDisplayPrintDates);
		
		Attr tbname = doc.createAttribute("name");
		tbname.setValue(""+model.getTitleBar().getName());
		titleBar.setAttributeNode(tbname);
		
		Attr tbenable = doc.createAttribute("enable");
		tbenable.setValue(""+GeneralConfig.titleEnable);
		titleBar.setAttributeNode(tbenable);
		
		Attr tbcenter = doc.createAttribute("center");
		tbcenter.setValue(""+GeneralConfig.centeredTitleBar);
		titleBar.setAttributeNode(tbcenter);
		
		Attr tbleft = doc.createAttribute("left");
		tbleft.setValue(""+GeneralConfig.leftAlignedTitleBar);
		titleBar.setAttributeNode(tbleft);
		
		Attr tbx = doc.createAttribute("x");
		tbx.setValue(""+model.getTitleBar().getX());
		titleBar.setAttributeNode(tbx);
		
		Attr tbwidth = doc.createAttribute("width");
		tbwidth.setValue(""+model.getTitleBar().getWidth());
		titleBar.setAttributeNode(tbwidth);
		
		Attr tbheight = doc.createAttribute("height");
		tbheight.setValue(""+model.getTitleBar().getHeight());
		titleBar.setAttributeNode(tbheight);
		
		titleBar.setAttribute("r", model.getTitleBar().getColor().getRed()+"");
		titleBar.setAttribute("g", model.getTitleBar().getColor().getGreen()+"");
		titleBar.setAttribute("b", model.getTitleBar().getColor().getBlue()+"");
		
		root.appendChild(titleBar);
		
		//milestones
		for(int i : model.getMilestones().keySet()){
			Element milestone = doc.createElement("milestone");
			//id
			Attr id = doc.createAttribute("id");
			id.setValue(""+i);
			milestone.setAttributeNode(id);
			
			//indeterminated
			Attr indeteterminated = doc.createAttribute("indeterminated");
			indeteterminated.setValue(""+model.getMilestone(i).isIndeterminated());
			milestone.setAttributeNode(indeteterminated);
			
			//comm calculable
			Attr commCalculable = doc.createAttribute("commCalculable");
			commCalculable.setValue(""+model.getMilestone(i).isInTADAILYREPORT() );
			milestone.setAttributeNode(commCalculable);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(model.getMilestone(i).getName()));
			milestone.appendChild(name);
			
			//draw attr
			Element x = doc.createElement("x");
			x.appendChild(doc.createTextNode(""+model.getMilestone(i).getX()));
			milestone.appendChild(x);
			
			Element y = doc.createElement("y");
			y.appendChild(doc.createTextNode(""+model.getMilestone(i).getY()));
			milestone.appendChild(y);
			
			Element description = doc.createElement("description");
			description.appendChild(doc.createTextNode(model.getMilestone(i).getDescription()));
			milestone.appendChild(description);
			
			Element precomProgress = doc.createElement("precommProgress");
			precomProgress.appendChild(doc.createTextNode(model.getMilestone(i).getPrecommProgress()+""));
			milestone.appendChild(precomProgress);
			
			Element commProgress = doc.createElement("commProgress");
			commProgress.appendChild(doc.createTextNode(model.getMilestone(i).getCommProgress()+""));
			milestone.appendChild(commProgress);
			
			Element punch = doc.createElement("PLOpened");
			punch.appendChild(doc.createTextNode(model.getMilestone(i).getPunchOpened()+""));
			milestone.appendChild(punch);
			
			Element PLA = doc.createElement("PLAOpened");
			PLA.appendChild(doc.createTextNode(model.getMilestone(i).getPunchAOpened()+""));
			milestone.appendChild(PLA);
			
			Element PLB = doc.createElement("PLBOpened");
			PLB.appendChild(doc.createTextNode(model.getMilestone(i).getPunchBOpened()+""));
			milestone.appendChild(PLB);
			
			Element PLC = doc.createElement("PLCOpened");
			PLC.appendChild(doc.createTextNode(model.getMilestone(i).getPunchCOpened()+""));
			milestone.appendChild(PLC);
			
			Element aocProgress = doc.createElement("AOCProgress");
			aocProgress.appendChild(doc.createTextNode(model.getMilestone(i).getAOCProgress()+""));
			milestone.appendChild(aocProgress);
			
			Element rfcProgress = doc.createElement("RFCProgress");
			rfcProgress.appendChild(doc.createTextNode(model.getMilestone(i).getSsRFCProgress()+""));
			milestone.appendChild(rfcProgress);
			
			
			
			Element docProgress = doc.createElement("docProgress");
			docProgress.appendChild(doc.createTextNode(model.getMilestone(i).getDocumentationProgress()+""));
			milestone.appendChild(docProgress);
			
			Element stepProgress = doc.createElement("stepProgress");
			stepProgress.appendChild(doc.createTextNode(model.getMilestone(i).getStepProgress()+""));
			milestone.appendChild(stepProgress);
			
			Element operated = doc.createElement("operated");
			operated.appendChild(doc.createTextNode(model.getMilestone(i).isOperated()+""));
			milestone.appendChild(operated);
			
			Element subsysOperated = doc.createElement("subsysOperated");
			subsysOperated.appendChild(doc.createTextNode(model.getMilestone(i).isOperated()+""));
			milestone.appendChild(subsysOperated);
			
			Element subsysNumber = doc.createElement("subsysNumber");
			subsysNumber.appendChild(doc.createTextNode(model.getMilestone(i).getSsNumber()+""));
			milestone.appendChild(subsysNumber);
			
			Element subsysOperatedProgress = doc.createElement("subsysOperatedProgress");
			subsysOperatedProgress.appendChild(doc.createTextNode(model.getMilestone(i).getSsOperatedProgress()+""));
			milestone.appendChild(subsysOperatedProgress);
			
			Element targetDate = doc.createElement("targetDate");
			SimpleDateFormat format = new SimpleDateFormat("yyyy/MM/dd");
			targetDate.appendChild(doc.createTextNode(format.format(model.getMilestone(i).getTargetDate())));
			milestone.appendChild(targetDate);
			
			Element scopeDoneDate = doc.createElement("scopeDoneDate");
			if(model.getMilestone(i).getScopeDoneDate()!=null)
				scopeDoneDate.appendChild(doc.createTextNode(format.format(model.getMilestone(i).getScopeDoneDate())));
			milestone.appendChild(scopeDoneDate);
			
			//Miletone dest
			Element dest = doc.createElement("dest");
			Iterator<Milestone> it = model.getMilestone(i).getDestMilestone().iterator();
			while(it.hasNext()){
				JComponent m = it.next();
				for(int j : model.getMilestones().keySet()){
					if(model.getMilestone(j).equal(m)){
						if(it.hasNext())
							dest.appendChild(doc.createTextNode(j+" con "));
						else
							dest.appendChild(doc.createTextNode(j+""));
					}
				}
			}
			milestone.appendChild(dest);
			
			//Start Up Task dest
			Element destSUT = doc.createElement("destSUT");
			Iterator<StartUpStep> iterator = model.getMilestone(i).getDestSUT().iterator();
			while(iterator.hasNext()){
				StartUpStep sut = iterator.next();
				for(int j : model.getStartUpTasks().keySet()){
					if(model.getStartUpTask(j).equals(sut)){
						if(iterator.hasNext())
							destSUT.appendChild(doc.createTextNode(j+" con "));
						else
							destSUT.appendChild(doc.createTextNode(j+""));
					}
				}
			}
			milestone.appendChild(destSUT);
			
			root.appendChild(milestone);
		}
		
		//Start Up Task 
		for(int i : model.getStartUpTasks().keySet()){
			Element startUpTask = doc.createElement("startUpTask");
			//id
			Attr id = doc.createAttribute("id");
			id.setValue(""+i);
			startUpTask.setAttributeNode(id);
			
					
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(model.getStartUpTask(i).getName()));
			startUpTask.appendChild(name);
			
			Element secondLine = doc.createElement("secondLine");
			secondLine.appendChild(doc.createTextNode(model.getStartUpTask(i).getSecondLine()));
			startUpTask.appendChild(secondLine);
			
			//draw attr
			Element x = doc.createElement("x");
			x.appendChild(doc.createTextNode(""+model.getStartUpTask(i).getDoubleX()));
			startUpTask.appendChild(x);
			
			Element y = doc.createElement("y");
			y.appendChild(doc.createTextNode(""+model.getStartUpTask(i).getY()));
			startUpTask.appendChild(y);
			
			Element width = doc.createElement("width");
			width.appendChild(doc.createTextNode(""+model.getStartUpTask(i).getWidth()));
			startUpTask.appendChild(width);
			
			Element height = doc.createElement("height");
			height.appendChild(doc.createTextNode(""+model.getStartUpTask(i).getHeight()));
			startUpTask.appendChild(height);
			
			Element operated = doc.createElement("operated");
			operated.appendChild(doc.createTextNode(""+model.getStartUpTask(i).isOperated()));
			startUpTask.appendChild(operated);
			
			Element shape = doc.createElement("shape");
			shape.appendChild(doc.createTextNode(""+model.getStartUpTask(i).getShapeName()));
			startUpTask.appendChild(shape);
			
			Element milestoneRelated = doc.createElement("milestoneRelated");
			milestoneRelated.appendChild(doc.createTextNode(""+model.getStartUpTask(i).getRelatedToThisMilestone()));
			startUpTask.appendChild(milestoneRelated);
			
			Element backgroundColor = doc.createElement("backgroundColor");
			backgroundColor.setAttribute("r", model.getStartUpTask(i).getColor().getRed()+"");
			backgroundColor.setAttribute("g", model.getStartUpTask(i).getColor().getGreen()+"");
			backgroundColor.setAttribute("b", model.getStartUpTask(i).getColor().getBlue()+"");
			startUpTask.appendChild(backgroundColor);
			
			Element attr = doc.createElement("attr");
			for(String s : model.getStartUpTask(i).getAttr().keySet()){
				/*System.out.println(s);
				System.out.println(model.getStartUpTask(i).getAttr(s));*/
				attr.setAttribute(String.valueOf(s), model.getStartUpTask(i).getAttr(s)+"");
			}
			startUpTask.appendChild(attr);
			
			Element localRules = doc.createElement("localRules");			
			localRules.appendChild(doc.createTextNode(String.valueOf(model.getStartUpTask(i).isLocalAttrRule())));
			startUpTask.appendChild(localRules);
			
			Element localAttr = doc.createElement("localAttr");
			
			for(String s : model.getStartUpTask(i).getLocalAttrToDisplay().keySet()){
				/*System.out.println(s);
				System.out.println(model.getStartUpTask(i).getAttr(s));*/
				localAttr.setAttribute(String.valueOf(s), String.valueOf(model.getStartUpTask(i).getLocalAttrToDisplay().get(s)));
			}
			startUpTask.appendChild(localAttr);
			
			//Miletone dest
			Element dest = doc.createElement("dest");
			Iterator<Milestone> it = model.getStartUpTask(i).getDestMilestone().iterator();
			while(it.hasNext()){
				Milestone m = it.next();
				for(int j : model.getMilestones().keySet()){
					if(model.getMilestone(j).equal(m)){
						if(it.hasNext())
							dest.appendChild(doc.createTextNode(j+" con "));
						else
							dest.appendChild(doc.createTextNode(j+""));
					}
				}
			}
			startUpTask.appendChild(dest);
			
			//Start Up Task dest
			Element destSUT = doc.createElement("destSUT");
			Iterator<StartUpStep> iterator = model.getStartUpTask(i).getDestSUT().iterator();
			while(iterator.hasNext()){
				StartUpStep sut = iterator.next();
				for(int j : model.getStartUpTasks().keySet()){
					if(model.getStartUpTask(j).equals(sut)){
						if(iterator.hasNext())
							destSUT.appendChild(doc.createTextNode(j+" con "));
						else
							destSUT.appendChild(doc.createTextNode(j+""));
					}
				}
			}
			startUpTask.appendChild(destSUT);
			
			root.appendChild(startUpTask);
		}
		
		//Sequence Bar
		for(int i : model.getSequences().keySet()){
			Element sequenceBar = doc.createElement("sequenceBar");
			//id
			Attr id = doc.createAttribute("id");
			id.setValue(""+i);
			sequenceBar.setAttributeNode(id);
			
			//alignement
			Attr lAligned = doc.createAttribute("lAligned");
			lAligned.setValue(""+model.getSequence(i).isLeftAligned());
			sequenceBar.setAttributeNode(lAligned);
			
			Attr cAligned = doc.createAttribute("cAligned");
			cAligned.setValue(""+model.getSequence(i).isCenterAligned());
			sequenceBar.setAttributeNode(cAligned);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(model.getSequence(i).getName()));
			sequenceBar.appendChild(name);
			
			//draw attr
			Element x = doc.createElement("x");
			x.appendChild(doc.createTextNode(""+model.getSequence(i).getX()));
			sequenceBar.appendChild(x);
			
			Element y = doc.createElement("y");
			y.appendChild(doc.createTextNode(""+model.getSequence(i).getY()));
			sequenceBar.appendChild(y);
			
			Element width = doc.createElement("width");
			width.appendChild(doc.createTextNode(""+model.getSequence(i).getWidth()));
			sequenceBar.appendChild(width);
			
			Element height = doc.createElement("height");
			height.appendChild(doc.createTextNode(""+model.getSequence(i).getHeight()));
			sequenceBar.appendChild(height);
			
			Element extendedHeight = doc.createElement("extendedHeight");
			extendedHeight.appendChild(doc.createTextNode(""+model.getSequence(i).getExtendedHeight()));
			sequenceBar.appendChild(extendedHeight);
			
			Element backgroundColor = doc.createElement("backgroundColor");
			backgroundColor.setAttribute("r", model.getSequence(i).getColor().getRed()+"");
			backgroundColor.setAttribute("g", model.getSequence(i).getColor().getGreen()+"");
			backgroundColor.setAttribute("b", model.getSequence(i).getColor().getBlue()+"");
			sequenceBar.appendChild(backgroundColor);
			
			Element dashedLineColor = doc.createElement("dashedLineColor");
			dashedLineColor.setAttribute("r", model.getSequence(i).getDottedLineColor().getRed()+"");
			dashedLineColor.setAttribute("g", model.getSequence(i).getDottedLineColor().getGreen()+"");
			dashedLineColor.setAttribute("b", model.getSequence(i).getDottedLineColor().getBlue()+"");
			sequenceBar.appendChild(dashedLineColor);
			
			root.appendChild(sequenceBar);
		}
		
		//Comments
		for(int i : model.getComments().keySet()){
			Element comment = doc.createElement("comment");
			
			//id
			Attr id = doc.createAttribute("id");
			id.setValue(""+i);
			comment.setAttributeNode(id);
			
			Element name = doc.createElement("name");
			name.appendChild(doc.createTextNode(model.getComment(i).getName()));
			comment.appendChild(name);
			
			//draw attr
			Element x = doc.createElement("x");
			x.appendChild(doc.createTextNode(""+model.getComment(i).getX()));
			comment.appendChild(x);
			
			Element y = doc.createElement("y");
			y.appendChild(doc.createTextNode(""+model.getComment(i).getY()));
			comment.appendChild(y);
			
			Element width = doc.createElement("width");
			width.appendChild(doc.createTextNode(""+model.getComment(i).getWidth()));
			comment.appendChild(width);
			
			Element height = doc.createElement("height");
			height.appendChild(doc.createTextNode(""+model.getComment(i).getHeight()));
			comment.appendChild(height);
			
			Element backgroundColor = doc.createElement("backgroundColor");
			backgroundColor.setAttribute("r", model.getComment(i).getColor().getRed()+"");
			backgroundColor.setAttribute("g", model.getComment(i).getColor().getGreen()+"");
			backgroundColor.setAttribute("b", model.getComment(i).getColor().getBlue()+"");
			comment.appendChild(backgroundColor);
			
			root.appendChild(comment);
		}
		
		//Project configuration
		/*public static boolean isICAPSLinked=true;
		public static String login="sa";
		public static String password="Pvision_2008";
		public static String milestoneColumn="data2";
		public static String serverName="frep-l107796";
		public static String dbName="ICAPS";
		public static String instanceName="SQLEXPRESS";
		
		public static int targetAccuracy=100;*/
		//ICAPS
		Element icaps = doc.createElement("icaps");
		//icaps.setAttribute("linked", GeneralConfig.isICAPSLinked ? "1" : "0");
		Iterator<Database> itDB = GeneralConfig.databases.iterator();
		while(itDB.hasNext()){
			Database tmp = itDB.next();
			Element database = doc.createElement("database");
			database.setAttribute("alias", tmp.getAlias());
			database.setAttribute("activated", String.valueOf(tmp.isActivated()));
			database.setAttribute("connexionType", tmp.getConnexionType().toString());
			database.setAttribute("odbcLinkName", tmp.getOdbcLinkName());
			database.setAttribute("login",tmp.getLogin());
			String pass = Utils.rot13(tmp.getPassword());
			database.setAttribute("password", pass);
			database.setAttribute("milestoneColumn", tmp.getMilestoneColumn());
			database.setAttribute("serverName", tmp.getServerURL());
			database.setAttribute("dbName", tmp.getDatabaseName());
			database.setAttribute("instanceName", tmp.getInstanceName());
			if(tmp.getLastUpdateDate()!=null)
			    database.setAttribute("lastDbUpdate", tmp.getLastUpdateDate().getTime()+"");
			icaps.appendChild(database);
		}
		root.appendChild(icaps);
		
		//miscellaneous
		Element target = doc.createElement("target");
		target.appendChild(doc.createTextNode(GeneralConfig.targetAccuracy+""));
		root.appendChild(target);
		
		//Element displayed on milestone
		Element displayedOnMilestone = doc.createElement("ElementDisplayedOnMilestone");
		displayedOnMilestone.setAttribute("PL", GeneralConfig.plDisplayed+"");
		displayedOnMilestone.setAttribute("AOC", GeneralConfig.ssAOCdisplayed+"");
		displayedOnMilestone.setAttribute("Comm", GeneralConfig.commProgressDisplayed+"");
		displayedOnMilestone.setAttribute("Doc", GeneralConfig.docProgressDisplayed+"");
		displayedOnMilestone.setAttribute("Step", GeneralConfig.stepProgress+"");
		root.appendChild(displayedOnMilestone);
		
		//Start Up Documentation
		Element docu = doc.createElement("doc");
		docu.appendChild(doc.createTextNode(GeneralConfig.SUDocPath));
		root.appendChild(docu);
		
		//Log doc progress
		/*Element logDoc = doc.createElement("logDoc");
		BufferedReader br = new BufferedReader(new FileReader(new File("logSUDoc.log")));
		StringBuffer log= new StringBuffer();
		String line;
		while ((line = br.readLine()) != null) {
		   log.append(line);
		}
		br.close();
		String resultLog = log.toString();
		//System.out.println(resultLog);
		logDoc.appendChild(doc.createTextNode(resultLog));
		root.appendChild(logDoc);*/
		
		//Subsystems operated
		Element sso = doc.createElement("subsystemsOperated");
		sso.appendChild(doc.createTextNode(GeneralConfig.subSysOperated));
		root.appendChild(sso);
		
		//Project Title Block
		Element ptb = doc.createElement("projectTitleBlock");
		ptb.setAttribute("enable", ""+GeneralConfig.titleBlockEnable);
		ptb.setAttribute("rightAligned", GeneralConfig.titleBlockRightAligned+"");
		
		Element companyLogo = doc.createElement("companyLogo");
		companyLogo.appendChild(doc.createTextNode(""+model.getCartouche().get("companyLogo")));
		ptb.appendChild(companyLogo);
		
		Element projectLogo = doc.createElement("projectLogo");
		projectLogo.appendChild(doc.createTextNode(""+model.getCartouche().get("projectLogo")));
		ptb.appendChild(projectLogo);
		
		Element contractorLogo = doc.createElement("contractorLogo");
		contractorLogo.appendChild(doc.createTextNode(""+model.getCartouche().get("contractorLogo")));
		ptb.appendChild(contractorLogo);
		
		Element contractNumber = doc.createElement("contractNumber");
		contractNumber.appendChild(doc.createTextNode(""+model.getCartouche().get("contractNumber")));
		ptb.appendChild(contractNumber);
		
		Element contractorName = doc.createElement("contractorName");
		contractorName.appendChild(doc.createTextNode(""+model.getCartouche().get("contractorName")));
		ptb.appendChild(contractorName);
		
		Element customerField = doc.createElement("customerField");
		customerField.appendChild(doc.createTextNode(""+model.getCartouche().get("customerField")));
		ptb.appendChild(customerField);
		
		Element documentTitle = doc.createElement("documentTitle");
		documentTitle.appendChild(doc.createTextNode(""+model.getCartouche().get("documentTitle")));
		ptb.appendChild(documentTitle);
		
		Element studieType = doc.createElement("studieType");
		studieType.appendChild(doc.createTextNode(""+model.getCartouche().get("studieType")));
		ptb.appendChild(studieType);
		
		Element facilityName = doc.createElement("facilityName");
		facilityName.appendChild(doc.createTextNode(""+model.getCartouche().get("facilityName")));
		ptb.appendChild(facilityName);

		Element designation = doc.createElement("designation");
		designation.appendChild(doc.createTextNode(""+model.getCartouche().get("designation")));
		ptb.appendChild(designation);
		
		Element drawingType = doc.createElement("drawingType");
		drawingType.appendChild(doc.createTextNode(""+model.getCartouche().get("drawingType")));
		ptb.appendChild(drawingType);
		
		Element docType = doc.createElement("docType");
		docType.appendChild(doc.createTextNode(""+model.getCartouche().get("docType")));
		ptb.appendChild(docType);
		
		Element systSSsyst = doc.createElement("systSSsyst");
		systSSsyst.appendChild(doc.createTextNode(""+model.getCartouche().get("systSSsyst")));
		ptb.appendChild(systSSsyst);
		
		Element discipline = doc.createElement("discipline");
		discipline.appendChild(doc.createTextNode(""+model.getCartouche().get("discipline")));
		ptb.appendChild(discipline);
		
		Element electronicFilename = doc.createElement("electronicFilename");
		electronicFilename.appendChild(doc.createTextNode(""+model.getCartouche().get("electronicFilename")));
		ptb.appendChild(electronicFilename);
		
		Element companyRef = doc.createElement("companyRef");
		companyRef.appendChild(doc.createTextNode(""+model.getCartouche().get("companyRef")));
		ptb.appendChild(companyRef);
		
		Element contractorRef = doc.createElement("contractorRef");
		contractorRef.appendChild(doc.createTextNode(""+model.getCartouche().get("contractorRef")));
		ptb.appendChild(contractorRef);
		
		Element revision = doc.createElement("revision");
		revision.appendChild(doc.createTextNode(""+model.getCartouche().get("revision")));
		ptb.appendChild(revision);
		
		Element scale = doc.createElement("scale");
		scale.appendChild(doc.createTextNode(""+model.getCartouche().get("scale")));
		ptb.appendChild(scale);
		
		Element folio = doc.createElement("folio");
		folio.appendChild(doc.createTextNode(""+model.getCartouche().get("folio")));
		ptb.appendChild(folio);
		
		Element misc = doc.createElement("misc");
		misc.appendChild(doc.createTextNode(""+model.getCartouche().get("misc")));
		ptb.appendChild(misc);
		
		//Revisions
		Element revisions = doc.createElement("revisions");
		Iterator<TreeMap<String, String>> it = model.getRevisions().iterator();
		while(it.hasNext()){
			System.out.print("Saving rev ");
			TreeMap<String, String> rev = it.next();
			Element revi = doc.createElement("revision");
			//Rev
			Attr r = doc.createAttribute("rev");
			r.setValue(rev.get("rev"));
			System.out.print(rev.get("rev"));
			revi.setAttributeNode(r);
			//Date
			Attr date = doc.createAttribute("date");
			date.setValue(rev.get("date"));
			System.out.print(rev.get("date"));
			revi.setAttributeNode(date);
			//status
			Attr status = doc.createAttribute("status");
			status.setValue(rev.get("status"));
			System.out.print(rev.get("status"));
			revi.setAttributeNode(status);
			//Revision Memo
			Attr revisionMemo = doc.createAttribute("revisionMemo");
			revisionMemo.setValue(rev.get("revisionMemo"));
			revi.setAttributeNode(revisionMemo);
			//Issued By
			Attr issued = doc.createAttribute("issued");
			issued.setValue(rev.get("issued"));
			revi.setAttributeNode(issued);
			//Checked By
			Attr checked = doc.createAttribute("checked");
			checked.setValue(rev.get("checked"));
			revi.setAttributeNode(checked);
			//Approved by
			Attr approved = doc.createAttribute("approved");
			approved.setValue(rev.get("approved"));
			revi.setAttributeNode(approved);
			
			revisions.appendChild(revi);
		}
		ptb.appendChild(revisions);
		root.appendChild(ptb);
		
		//In order to improve portability logos raw bytes array are stored directly in the xml file
		//Logos Storage
		Element contractorLogoParse = doc.createElement("contractorLogoParse");
		if(GeneralConfig.contractorLogo!=null){
		    contractorLogoParse.appendChild(doc.createTextNode(Base64.encodeBase64String(GeneralConfig.contractorLogo)));
		}
		
		root.appendChild(contractorLogoParse);
		
		Element coompanyLogoParse = doc.createElement("companyLogoParse");
		if(GeneralConfig.companyLogo!=null){
		    coompanyLogoParse.appendChild(doc.createTextNode(Base64.encodeBase64String(GeneralConfig.companyLogo)));
		}
		
		root.appendChild(coompanyLogoParse);
		
		Element projectLogoParse = doc.createElement("projectLogoParse");
		if(GeneralConfig.projectLogo!=null){
		    projectLogoParse.appendChild(doc.createTextNode(Base64.encodeBase64String(GeneralConfig.projectLogo)));
		}
		
		root.appendChild(projectLogoParse);
		
		//Label for doc
		Element labelForDoc = doc.createElement("labelForDoc");
		labelForDoc.appendChild(doc.createTextNode(GeneralConfig.labelForDoc));
		root.appendChild(labelForDoc);
		
		Element expertMode = doc.createElement("expertMode");
		if(GeneralConfig.expertMode){
		    expertMode.appendChild(doc.createTextNode("true"));
		} else {
		    expertMode.appendChild(doc.createTextNode("false"));
		}
		root.appendChild(expertMode);
		
		Element cellReference = doc.createElement("cellReference");
		cellReference.appendChild(doc.createTextNode(GeneralConfig.excelFileCell));
		root.appendChild(cellReference);
		
		
		Element logSUDoc = doc.createElement("logSUActivitiesProgress");
		logSUDoc.appendChild(doc.createTextNode(GeneralConfig.logSUDoc));
		root.appendChild(logSUDoc);
		
		
		// write the content into xml file
		TransformerFactory transformerFactory = TransformerFactory.newInstance();
		Transformer transformer = transformerFactory.newTransformer();
		transformer.setOutputProperty(OutputKeys.INDENT, "yes");
		DOMSource source = new DOMSource(doc);
		StreamResult result = new StreamResult(new File(path));
		 
		// Output to console for testing
//		 StreamResult result = new StreamResult(System.out);
		 
		transformer.transform(source, result);
		 
		//System.out.println("File saved!");
		return true;
	}
	
}
