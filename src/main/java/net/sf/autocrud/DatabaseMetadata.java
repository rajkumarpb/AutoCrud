// $Id: DatabaseMetadata.java,v 1.2 2007/07/15 21:02:02 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/DatabaseMetadata.java,v $
package net.sf.autocrud;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Provides methods to interact with the database table metadata.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class DatabaseMetadata extends JdbcDaoSupport {

  private Log logger = LogFactory.getLog(DatabaseMetadata.class);
  
  private JavaTypeMap javaTypeMap;
  
  public void setJavaTypeMap(JavaTypeMap javaTypeMap) {
    this.javaTypeMap = javaTypeMap;
  }
  
  /**
   * Return a List of table names from the specified database. The database
   * is the one corresponding to the data source set in this bean.
   * @return a List of table names.
   * @throws SQLException if one is thrown.
   */
  public List<String> getTables() throws SQLException {
    ResultSet rs = getDatabaseMetaData().getTables(null, null, null, null);
    List<String> tableNames = new ArrayList<String>();
    while (rs.next()) {
      tableNames.add(rs.getString("TABLE_NAME"));
    }
    rs.close();
    return tableNames;
  }
  
  /**
   * Returns a List of NameValuePair objects. Each NameValuePair object 
   * represents a column in the specified table. The name part of each
   * NameValuePair is the column name and the value part is the internal
   * data type of the column. This is the same value as returned by 
   * java.sql.Types. We use types.properties (which is largely same across
   * databases, but there are some important differences, so this needs to 
   * be database specific) to map the internal type to a corresponding
   * Java type.
   * @param table the name of the table.
   * @return the List of pairs of (columnName, columnType).
   * @throws SQLException if one is thrown.
   */
  public List<NameValuePair> getColumns(String table) throws SQLException {
    ResultSet rs = getDatabaseMetaData().getColumns(null, null, table, null);
    List<NameValuePair> columns = new ArrayList<NameValuePair>();
    while (rs.next()) {
      columns.add(new NameValuePair(rs.getString("COLUMN_NAME"), 
        javaTypeMap.getJavaType(rs.getString("DATA_TYPE"))));
    }
    rs.close();
    return columns;
  }
  
  /**
   * Get the list of tables for this tableName. The bean corresponding to this
   * tableName will treat these tables as straight object references.
   * @param tableName the table name to look up.
   * @return a List of tables (or corresponding entities) which can linked from
   * this table (or entity).
   * @throws SQLException if one is thrown.
   */
  public List<String> getReferenceTables(String tableName) throws SQLException {
    List<String> referenceTables = new ArrayList<String>();
    if (tableName == null || tableName.contains("_")) {
      return referenceTables;
    }
    List<String> tables = getTables();
    for (String table : tables) {
      if (table.contains(tableName) && (!(table.equals(tableName)))) {
        // find the other part of the table name
        String referenceTable = table.replace(tableName, "").replace("_", "");
        referenceTables.add(referenceTable);
      }
    }
    return referenceTables;
  }
  
  /**
   * Given a table name, returns a list of all tables that refer to it as
   * a foreign key. The method uses the rule that all references to a parent
   * table should be done using a column named ${entityName}_id.
   * @param tableName the table name to check.
   * @return the List of table names that reference this table.
   * @throws SQLException if one is thrown.
   */
  public List<String> getReferencingTables(String tableName) throws SQLException {
    List<String> referencingTables = new ArrayList<String>();
    if (tableName == null || tableName.contains("_")) {
      return referencingTables;
    }
    List<String> tables = getTables();
    for (String table : tables) {
      if (table.equals(tableName) || table.contains("_")) {
        continue;
      }
      List<NameValuePair> columns = getColumns(table);
      for (NameValuePair column : columns) {
        String colName = column.getName();
        if (colName.endsWith("_id")) {
          String referencedTable = colName.replace("_id", "");
          if (referencedTable.equals(tableName)) {
            referencingTables.add(table);
          }
        }
      }
    }
    return referencingTables;
  }
  
  private DatabaseMetaData getDatabaseMetaData() throws SQLException {
    return getDataSource().getConnection().getMetaData();
  }
}
