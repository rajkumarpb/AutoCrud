// $Id: ActiveRecord.java,v 1.2 2007/07/15 21:02:01 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/ActiveRecord.java,v $
package net.sf.autocrud;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.jdbc.core.support.JdbcDaoSupport;

/**
 * Base class that all generated beans will extend. This is a support
 * class, providing generic implementations of persistence and retrieval 
 * methods. Generated subclasses simply declare getters and setters that
 * can be used by the client code.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ActiveRecord extends JdbcDaoSupport {

  private Log logger = LogFactory.getLog(ActiveRecord.class);
  
  private Map<String,Object> fields = new HashMap<String,Object>();
  private String tableName;

  /**
   * Called from within the generated setter methods in subclasses.
   * @param fieldName the database field name.
   * @param value the value.
   */
  public void setValue(String fieldName, Object value) {
    fields.put(fieldName, value);
  }

  /**
   * Called from within the generated getter methods in subclasses.
   * @param fieldName the database field name.
   * @return the value.
   */
  public Object getValue(String fieldName) {
    return fields.get(fieldName);
  }

  /**
   * Set the database column names. This is set by the generated subclasses
   * in their constructor.
   * @param fieldNames the database column names.
   */
  public void setFields(String[] fieldNames) {
    for (String fieldName : fieldNames) {
      if (fields.containsKey(fieldName)) {
        continue;
      }
      fields.put(fieldName, null);
    }
  }
  
  public Set<String> getFields() {
    return fields.keySet();
  }
  
  /**
   * Set the database table name. This is set by the generated subclasses
   * in their constructor.
   * @param tableName the database table name.
   */
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public String getTableName() {
    return tableName;
  }
  
  public List<ActiveRecord> find(String condition) throws Exception {
    if (StringUtils.isBlank(condition)) {
      return findAll();
    }
    Set<String> colNames = fields.keySet();
    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("select ").
      append(StringUtils.join(colNames.iterator(), ',')).
      append(" from ").
      append(getTableName()).
      append(" where ").append(condition);
    return getActiveRecords(queryBuilder.toString());
  }

  public List<ActiveRecord> findAll() throws Exception {
    Set<String> colNames = fields.keySet();
    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("select ").
      append(StringUtils.join(colNames.iterator(), ',')).
      append(" from ").
      append(getTableName());
    return getActiveRecords(queryBuilder.toString());
  }

  public ActiveRecord findById(long id) throws Exception {
    List<ActiveRecord> records = find("id=" + id);
    if (records.size() == 0) {
      return null;
    }
    if (records.size() > 1) {
      throw new Exception(getTableName() + ".id must be a primary key");
    }
    return records.get(0);
  }

  public long getId() {
    Object oid = fields.get("id");
    if (oid == null) {
      return 0L;
    }
    return new Long(oid.toString());
  }

  public long delete() throws Exception {
    StringBuilder queryBuilder = new StringBuilder();
    queryBuilder.append("delete from ").
      append(getTableName()).
      append(" where id=").
      append(getId());
    getJdbcTemplate().update(queryBuilder.toString());
    return getId();
  }

  public long save() throws Exception {
    ActiveRecord dbRecord = findById(getId());
    StringBuilder queryBuilder = new StringBuilder();
    if (this.equals(dbRecord)) {
      // record exists, do update
      queryBuilder.append("update ").
        append(getTableName()).
        append(" set ");
      Set<String> colNames = fields.keySet();
      Object[] params = new Object[colNames.size() - 1];
      int i = 0;
      for (String colName : colNames) {
        if (colName.equals("id")) {
          continue;
        }
        if (i > 0) {
          queryBuilder.append(",");
        }
        queryBuilder.append(colName).append("=?");
        params[i] = fields.get(colName);
        i++;
      }
      queryBuilder.append(" where id=").append(getId());
      getJdbcTemplate().update(queryBuilder.toString(), params);
    } else {
      // record is new, do insert
      Set<String> colNames = fields.keySet();
      Object[] params = new Object[colNames.size()];
      queryBuilder.append("insert into ").append(getTableName()).append("(");
      StringBuilder columnListBuilder = new StringBuilder();
      StringBuilder placeHolderBuilder = new StringBuilder();
      int i = 0;
      for (String colName : colNames) {
        if (i > 0) {
          columnListBuilder.append(",");
          placeHolderBuilder.append(",");
        }
        columnListBuilder.append(colName);
        placeHolderBuilder.append("?");
        params[i] = fields.get(colName);
        i++;
      }
      queryBuilder.append(columnListBuilder.toString()).
        append(")values(").
        append(placeHolderBuilder.toString()).
        append(")");
      getJdbcTemplate().update(queryBuilder.toString(), params);
      long id = getJdbcTemplate().queryForLong("select max(id) from " + getTableName());
      setValue("id", id);
    }
    return getId();
  }

  @Override
  public int hashCode() {
    return (int) getId();
  }
  
  @Override
  public boolean equals(Object obj) {
    if (!(obj instanceof ActiveRecord)) {
      return false;
    }
    ActiveRecord that = (ActiveRecord) obj;
    return (this.getId() == that.getId());
  }

  protected ActiveRecord newInstance(String className) throws Exception {
    Object obj = Class.forName(className).newInstance();
    if (obj instanceof ActiveRecord) {
      ActiveRecord activeRecord = (ActiveRecord) obj;
      activeRecord.setDataSource(getDataSource());
      activeRecord.setValue("id", new Long(0L));
      return activeRecord;
    } else {
      throw new Exception("Class:" + className + " must extend ActiveRecord");
    }
  }

  @SuppressWarnings("unchecked")
  private List<ActiveRecord> getActiveRecords(String query) throws Exception {
    List<Map<String,Object>> rows = getJdbcTemplate().queryForList(query);
    List<ActiveRecord> records = new ArrayList<ActiveRecord>();
    Set<String> colNames = fields.keySet();
    for (Map<String,Object> row : rows) {
      ActiveRecord record = newInstance(this.getClass().getName());
      for (String colName : colNames) {
        record.setValue(colName, row.get(colName));
      }
      records.add(record);
    }
    return records;
  }
}
