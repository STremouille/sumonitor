package database;

/**
 * Enumeration of different link type supported for the connection
 * @author S.Trémouille
 *
 */
public enum LINK_TYPE {
	/**
	 * ODBC Link
	 */
	ODBC,
	/**
	 * TCP/IP Link
	 */
	TCPIP,
	/**
	 * Windows Authentication
	 */
	WINDOWS_AUTH
}
