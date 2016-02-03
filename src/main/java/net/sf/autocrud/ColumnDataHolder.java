// $Id: ColumnDataHolder.java,v 1.2 2007/07/15 21:02:01 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/ColumnDataHolder.java,v $
package net.sf.autocrud;

/**
 * A holder bean for Column data. Used b ActiveRecord generator to store
 * column properties.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ColumnDataHolder {

  private String columnName;
  private String javaName;
  private String javaType;
  private String jstlGetExpr;
  
  public ColumnDataHolder() {
    super();
  }

  public String getColumnName() {
    return columnName;
  }

  public void setColumnName(String columnName) {
    this.columnName = columnName;
  }

  public String getJavaName() {
    return javaName;
  }

  public void setJavaName(String javaName) {
    this.javaName = javaName;
  }

  public String getJavaType() {
    return javaType;
  }

  public void setJavaType(String javaType) {
    this.javaType = javaType;
  }

  public String getJstlGetExpr() {
    return jstlGetExpr;
  }

  public void setJstlGetExpr(String jstlGetExpr) {
    this.jstlGetExpr = jstlGetExpr;
  }
}
