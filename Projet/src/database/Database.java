package database;

import java.util.Date;

/**
 * @author S.Trémouille
 * A database model fetching all informations needed to settle a connection
 *
 */
public class Database {
	private LINK_TYPE connexionType;
	private String odbcLinkName;
	private String login;
	private String password;
	private String milestoneColumn;
	private String serverURL;
	private String databaseName;
	private String instanceName;
	private String alias;
	private Date lastUpdateDate;
	private boolean activated;
	
	/**
	 * Constructor
	 * @param alias
	 * @param connexionType
	 * @param odbcLinkName
	 * @param login
	 * @param password
	 * @param milestoneColumn
	 * @param serverName
	 * @param databaseName
	 * @param instanceName
	 * @param lastUpDateDate
	 */
	public Database(String alias,LINK_TYPE connexionType, String odbcLinkName, String login,
			String password, String milestoneColumn, String serverName,
			String databaseName, String instanceName, Date lastUpDateDate) {
		super();
		this.alias=alias;
		this.connexionType = connexionType;
		this.odbcLinkName = odbcLinkName;
		this.login = login;
		this.password = password;
		this.milestoneColumn = milestoneColumn;
		this.serverURL = serverName;
		this.databaseName = databaseName;
		this.instanceName = instanceName;
		this.activated=true;
	}
	
	public Database(String alias,LINK_TYPE connexionType, String odbcLinkName, String login,
		String password, String milestoneColumn, String serverName,
		String databaseName, String instanceName, Date lastUpDateDate, boolean activated) {
	super();
	this.alias=alias;
	this.connexionType = connexionType;
	this.odbcLinkName = odbcLinkName;
	this.login = login;
	this.password = password;
	this.milestoneColumn = milestoneColumn;
	this.serverURL = serverName;
	this.databaseName = databaseName;
	this.instanceName = instanceName;
	this.activated=activated;
}
	
	/**
	 * Constructor
	 * @param alias
	 * @param connexionType
	 * @param odbcLinkName
	 * @param login
	 * @param password
	 * @param milestoneColumn
	 * @param serverName
	 * @param databaseName
	 * @param instanceName
	 */
	public Database(String alias,LINK_TYPE connexionType, String odbcLinkName, String login,
		String password, String milestoneColumn, String serverName,
		String databaseName, String instanceName) {
	    this(alias,connexionType,odbcLinkName,login,password,milestoneColumn,serverName,databaseName,instanceName,null);
	}
	
	/**
	 * Constructor
	 */
	public Database() {
		this("",LINK_TYPE.ODBC,"","","","","","","",null);
		this.setActivated(true);
	}
	/**
	 * @return LINK_TYPE
	 */
	public LINK_TYPE getConnexionType() {
		return connexionType;
	}
	/**
	 * @param connexionType
	 */
	public void setConnexionType(LINK_TYPE connexionType) {
		this.connexionType = connexionType;
	}
	/**
	 * @return ODBC Link Name
	 */
	public String getOdbcLinkName() {
		return odbcLinkName;
	}
	/**
	 * @param odbcLinkName
	 */
	public void setOdbcLinkName(String odbcLinkName) {
		this.odbcLinkName = odbcLinkName;
	}
	/**
	 * @return login
	 */
	public String getLogin() {
		return login;
	}
	/**
	 * @param login
	 */
	public void setLogin(String login) {
		this.login = login;
	}
	/**
	 * @return password (no encryption)
	 */
	public String getPassword() {
		return password;
	}
	/**
	 * @param password (no encryption)
	 */
	public void setPassword(String password) {
		this.password = password;
	}
	/**
	 * @return Milestone Column Name in Icaps Subsystem Table
	 */
	public String getMilestoneColumn() {
		return milestoneColumn;
	}
	/**
	 * @param milestoneColumnNameInICAPSSubsystemTable
	 */
	public void setMilestoneColumn(String milestoneColumnNameInICAPSSubsystemTable) {
		this.milestoneColumn = milestoneColumnNameInICAPSSubsystemTable;
	}
	/**
	 * @return Server URL
	 */
	public String getServerURL() {
		return serverURL;
	}
	/**
	 * @param serverURL
	 */
	public void setServerURL(String serverURL) {
		this.serverURL = serverURL;
	}
	/**
	 * @return DB Name
	 */
	public String getDatabaseName() {
		return databaseName;
	}
	/**
	 * @param databaseName
	 */
	public void setDatabaseName(String databaseName) {
		this.databaseName = databaseName;
	}
	/**
	 * @return instance of the database
	 */
	public String getInstanceName() {
		return instanceName;
	}
	/**
	 * @param instanceName
	 */
	public void setInstanceName(String instanceName) {
		this.instanceName = instanceName;
	}
	/**
	 * @return Alias of the database
	 */
	public String getAlias() {
		return alias;
	}
	/**
	 * @param alias
	 */
	public void setAlias(String alias) {
		this.alias=alias;
	}
	/**
	 * @return last Update Date of the db
	 */
	public Date getLastUpdateDate() {
	    return lastUpdateDate;
	}
	/**
	 * @param lastUpdateDate
	 */
	public void setLastUpdateDate(Date lastUpdateDate) {
	    this.lastUpdateDate = lastUpdateDate;
	}


	public boolean isActivated() {
	    return activated;
	}

	public void setActivated(boolean activated) {
	    this.activated = activated;
	}
	
	public void copy(Database db){
	    this.alias=db.getAlias();
	    this.connexionType=db.getConnexionType();
	    this.odbcLinkName=db.getOdbcLinkName();
	    this.login=db.getLogin();
	    this.password=db.getPassword();
	    this.milestoneColumn=db.getMilestoneColumn();
	    this.serverURL=db.getServerURL();
	    this.databaseName=db.getDatabaseName();
	    this.instanceName=db.getInstanceName();
	    this.lastUpdateDate=db.getLastUpdateDate();
	    this.activated=db.isActivated();
	}
	
	public String toString(){
	    return alias+" - "+activated;
	}
}

