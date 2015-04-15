package view;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.Toolkit;
import java.awt.event.ActionListener;
import java.util.TreeMap;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.WindowConstants;
import javax.swing.border.TitledBorder;

import model.ProjectTitleBlock;

import org.eclipse.wb.swing.FocusTraversalOnArray;

import com.jgoodies.forms.factories.FormFactory;
import com.jgoodies.forms.layout.ColumnSpec;
import com.jgoodies.forms.layout.FormLayout;
import com.jgoodies.forms.layout.RowSpec;

import conf.GeneralConfig;

import javax.swing.JTextArea;
import javax.swing.JScrollPane;

/**
 * Project Title Block View
 * @author S.Trémouille
 *
 */

public class ProjectTitleBlockView extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = -2268650583586518726L;
	private JPanel contentPane;
	private JTextField companyLogo;
	private JTextField projectLogo;
	private JTextField contractorLogo;
	private JTextField contract;
	private JTextField contractorName;
	private JTextField customerField;
	private JTextField documentTitle;
	private JTextField studieType;
	private JTextField facilitiesName;
	private JTextField designation;
	private JTextField drawingType;
	private JTextField docType;
	private JTextField systSSsyst;
	private JTextField discipline;
	private JTextField electronicFilename;
	private JTextField companyRef;
	private JTextField contractorRef;
	private JTextField revision;
	private JTextField scale;
	private JTextField folio;
	private JLabel contractorLogoLabel,companyLogoLabel,projectLogoLabel;
	
	private JButton btnValidate;
	private JButton btnCancel;
	private JButton companyLogoBrowse;
	private JButton projectLogoBrowse;
	private JButton contractorLogoBrowse;
	private JLabel lblMisc;
	private JScrollPane scrollPane;
	private JTextArea textArea;


	

	/**
	 * Create the frame.
	 * @param model 
	 */
	public ProjectTitleBlockView(ProjectTitleBlock model) {
		setIconImage(Toolkit.getDefaultToolkit().getImage(MilestoneEditorFrame.class.getResource("/img/icone.png")));
		setForeground(Color.BLACK);
		setTitle("Title Block Edition");
		setFont(new Font("Arial", Font.PLAIN, 12));
		setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);
		contentPane = new JPanel();
		contentPane.setBorder(new TitledBorder(null, "Cartbridge Configuration", TitledBorder.LEADING, TitledBorder.TOP, null, null));
		setContentPane(contentPane);
		contentPane.setLayout(new FormLayout(new ColumnSpec[] {
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("max(70dlu;default):grow"),
				FormFactory.RELATED_GAP_COLSPEC,
				FormFactory.DEFAULT_COLSPEC,
				FormFactory.RELATED_GAP_COLSPEC,
				ColumnSpec.decode("default:grow"),},
			new RowSpec[] {
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),
				FormFactory.RELATED_GAP_ROWSPEC,
				FormFactory.DEFAULT_ROWSPEC,
				FormFactory.RELATED_GAP_ROWSPEC,
				RowSpec.decode("default:grow"),}));
		
		companyLogoLabel = new JLabel("Company Logo :");
		contentPane.add(companyLogoLabel, "2, 2, right, default");
		
		JPanel panel = new JPanel();
		contentPane.add(panel, "4, 2, fill, fill");
		panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		companyLogo = new JTextField(model.getCartouche().get("companyLogo"));
		panel.add(companyLogo);
		companyLogo.setColumns(10);
		
		companyLogoBrowse = new JButton("Browse");
		panel.add(companyLogoBrowse);
		
		projectLogoLabel = new JLabel("Project Logo :");
		contentPane.add(projectLogoLabel, "2, 4, right, default");
		
		JPanel panel_1 = new JPanel();
		contentPane.add(panel_1, "4, 4, fill, fill");
		panel_1.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		projectLogo = new JTextField(model.getCartouche().get("projectLogo"));
		projectLogo.setColumns(10);
		panel_1.add(projectLogo);
		
		projectLogoBrowse = new JButton("Browse");
		panel_1.add(projectLogoBrowse);
		
		contractorLogoLabel = new JLabel("Contrator Logo :");
		contentPane.add(contractorLogoLabel, "2, 6, right, default");
		
		JPanel panel_2 = new JPanel();
		contentPane.add(panel_2, "4, 6, fill, fill");
		panel_2.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
		
		contractorLogo = new JTextField(model.getCartouche().get("contractorLogo"));
		contractorLogo.setColumns(10);
		panel_2.add(contractorLogo);
		
		contractorLogoBrowse = new JButton("Browse");
		panel_2.add(contractorLogoBrowse);
		
		JLabel lblNContractsrfs = new JLabel("N\u00B0 Contracts/RFS :");
		contentPane.add(lblNContractsrfs, "2, 8, right, default");
		
		contract = new JTextField(model.getCartouche().get("contractNumber"));
		contentPane.add(contract, "4, 8, fill, default");
		contract.setColumns(10);
		
		JLabel lblSystssysyt = new JLabel("Syst./S.Sysyt. :");
		contentPane.add(lblSystssysyt, "6, 8, right, default");
		
		systSSsyst = new JTextField(model.getCartouche().get("systSSsyst"));
		systSSsyst.setColumns(10);
		contentPane.add(systSSsyst, "8, 8, fill, default");
		
		JLabel lblContractorName = new JLabel("Contractor Name :");
		contentPane.add(lblContractorName, "2, 10, right, default");
		
		contractorName = new JTextField(model.getCartouche().get("contractorName"));
		contractorName.setColumns(10);
		contentPane.add(contractorName, "4, 10, fill, default");
		
		JLabel lblDiscipline = new JLabel("Discipline :");
		contentPane.add(lblDiscipline, "6, 10, right, default");
		
		discipline = new JTextField(model.getCartouche().get("discipline"));
		discipline.setColumns(10);
		contentPane.add(discipline, "8, 10, fill, default");
		
		JLabel lblCustomerField = new JLabel("Customer Field :");
		contentPane.add(lblCustomerField, "2, 12, right, default");
		
		customerField = new JTextField(model.getCartouche().get("customerField"));
		customerField.setColumns(10);
		contentPane.add(customerField, "4, 12, fill, default");
		
		JLabel lblElectonicFilename = new JLabel("Electonic Filename :");
		contentPane.add(lblElectonicFilename, "6, 12, right, default");
		
		electronicFilename = new JTextField(model.getCartouche().get("electronicFilename"));
		electronicFilename.setColumns(10);
		contentPane.add(electronicFilename, "8, 12, fill, default");
		
		JLabel lblDocumentTitle = new JLabel("Document Title :");
		contentPane.add(lblDocumentTitle, "2, 14, right, default");
		
		documentTitle = new JTextField(model.getCartouche().get("documentTitle"));
		documentTitle.setColumns(10);
		contentPane.add(documentTitle, "4, 14, fill, default");
		
		JLabel lblCompanyRef = new JLabel("Company Ref :");
		contentPane.add(lblCompanyRef, "6, 14, right, default");
		
		companyRef = new JTextField(model.getCartouche().get("companyRef"));
		companyRef.setColumns(10);
		contentPane.add(companyRef, "8, 14, fill, default");
		
		JLabel lblTypeOfStudy = new JLabel("Type of study :");
		contentPane.add(lblTypeOfStudy, "2, 16, right, default");
		
		studieType = new JTextField(model.getCartouche().get("studieType"));
		studieType.setColumns(10);
		contentPane.add(studieType, "4, 16, fill, default");
		
		JLabel lblContractorRef = new JLabel("Contractor Ref :");
		contentPane.add(lblContractorRef, "6, 16, right, default");
		
		contractorRef = new JTextField(model.getCartouche().get("contractorRef"));
		contractorRef.setColumns(10);
		contentPane.add(contractorRef, "8, 16, fill, default");
		
		JLabel lblNameOfFacilities = new JLabel("Name of facilities :");
		contentPane.add(lblNameOfFacilities, "2, 18, right, default");
		
		facilitiesName = new JTextField(model.getCartouche().get("facilityName"));
		facilitiesName.setColumns(10);
		contentPane.add(facilitiesName, "4, 18, fill, default");
		
		JLabel lblRevision = new JLabel("Revision :");
		contentPane.add(lblRevision, "6, 18, right, default");
		
		revision = new JTextField(model.getCartouche().get("revision"));
		revision.setColumns(10);
		contentPane.add(revision, "8, 18, fill, default");
		
		JLabel lblDesignation = new JLabel("Designation :");
		contentPane.add(lblDesignation, "2, 20, right, default");
		
		designation = new JTextField(model.getCartouche().get("designation"));
		designation.setColumns(10);
		contentPane.add(designation, "4, 20, fill, default");
		
		JLabel lblScale = new JLabel("Scale :");
		contentPane.add(lblScale, "6, 20, right, default");
		
		scale = new JTextField(model.getCartouche().get("scale"));
		scale.setColumns(10);
		contentPane.add(scale, "8, 20, fill, default");
		
		JLabel lblTypeOfDrawing = new JLabel("Type of drawing :");
		contentPane.add(lblTypeOfDrawing, "2, 22, right, default");
		
		drawingType = new JTextField(model.getCartouche().get("drawingType"));
		drawingType.setColumns(10);
		contentPane.add(drawingType, "4, 22, fill, default");
		
		JLabel lblFolio = new JLabel("Folio :");
		contentPane.add(lblFolio, "6, 22, right, default");
		
		folio = new JTextField(model.getCartouche().get("folio"));
		folio.setColumns(10);
		contentPane.add(folio, "8, 22, fill, default");
		
		JLabel lblDocType = new JLabel("Doc Type :");
		contentPane.add(lblDocType, "2, 24, right, default");
		
		docType = new JTextField(model.getCartouche().get("docType"));
		docType.setColumns(10);
		contentPane.add(docType, "4, 24, fill, default");
		
		lblMisc = new JLabel("Misc :");
		contentPane.add(lblMisc, "2, 26, right, top");
		
		scrollPane = new JScrollPane();
		contentPane.add(scrollPane, "4, 26, fill, fill");
		
		textArea = new JTextArea(model.getCartouche().get("misc"));
		textArea.setLineWrap(true);
		textArea.setRows(5);
		scrollPane.setViewportView(textArea);
		
		btnValidate = new JButton("Validate");
		contentPane.add(btnValidate, "6, 28");
		
		btnCancel = new JButton("Cancel");
		contentPane.add(btnCancel, "8, 28");
		setFocusTraversalPolicy(new FocusTraversalOnArray(new Component[]{companyLogoBrowse, projectLogoBrowse, contractorLogoBrowse, contract, contractorName, customerField, documentTitle, studieType, facilitiesName, designation, drawingType, docType, systSSsyst, discipline, electronicFilename, companyRef, contractorRef, revision, scale, folio, btnValidate, btnCancel}));
		this.pack();
	}
	
	/**
	 * @return TreeMap containing project title block
	 */
	public TreeMap<String, String> getOutingTitleBlock(){
		TreeMap<String, String> res = new TreeMap<String, String>();
		res.put("companyLogo", companyLogo.getText());
		res.put("projectLogo", projectLogo.getText());
		res.put("contractorLogo", contractorLogo.getText());
		res.put("contractNumber", contract.getText());
		res.put("contractorName", contractorName.getText());
		res.put("customerField", customerField.getText());
		res.put("documentTitle", documentTitle.getText());
		res.put("studieType", studieType.getText());
		res.put("facilityName", facilitiesName.getText());
		res.put("designation", designation.getText());
		res.put("drawingType", drawingType.getText());
		res.put("docType", docType.getText());
		res.put("systSSsyst", systSSsyst.getText());
		res.put("discipline", discipline.getText());
		res.put("electronicFilename", electronicFilename.getText());
		res.put("companyRef", companyRef.getText());
		res.put("contractorRef", contractorRef.getText());
		res.put("revision", revision.getText());
		res.put("scale", scale.getText());
		res.put("folio", folio.getText());
		res.put("misc", textArea.getText());
		
		return res;
	}

	/**
	 * @param validateListener
	 */
	public void addValidateListener(ActionListener validateListener) {
		btnValidate.addActionListener(validateListener);
	}

	/**
	 * @param cancelListener
	 */
	public void addCancelListener(ActionListener cancelListener) {
		btnCancel.addActionListener(cancelListener);
	}
	
	/**
	 * @param al
	 */
	public void addBrowseCompanyLogoListener(ActionListener al){
		companyLogoBrowse.addActionListener(al);
	}
	
	/**
	 * @param al
	 */
	public void addBrowseProjectLogoListener(ActionListener al){
		projectLogoBrowse.addActionListener(al);
	}

	/**
	 * @param al
	 */
	public void addBrowseContractorLogoListener(ActionListener al){
		contractorLogoBrowse.addActionListener(al);
	}

	/**
	 * @return tContractorLogoTextField
	 */
	public JTextField getContractorLogoTextField(){
		return contractorLogo;
	}
	
	/**
	 * @return CompanyLogoTextField
	 */
	public JTextField getCompanyLogoTextField(){
		return companyLogo;
	}
	
	/**
	 * @return ProjectLogoTextField
	 */
	public JTextField getProjectLogoTextField(){
		return projectLogo;
	}

	/**
	 * @return CompanyLogoLabel
	 */
	public JLabel getCompanyLogoLabel() {
		return companyLogoLabel;
	}

	/**
	 * @return ProjectLogoLabel
	 */
	public JLabel getProjectLogoLabel() {
		return projectLogoLabel;
	}

	/**
	 * @return ContractorLogoLabel
	 */
	public JLabel getContractorLogoLabel() {
		return contractorLogoLabel;
	}
}
