// $Id: BeanDataHolder.java,v 1.2 2007/07/15 21:02:01 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/BeanDataHolder.java,v $
package net.sf.autocrud;

/**
 * Data Holder for the beans velocity view bean.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class BeanDataHolder {

  private String name;
  private String uppername;
  private String tableName;
  
  public BeanDataHolder() {
    super();
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getTableName() {
    return tableName;
  }

  public void setTableName(String tableName) {
    this.tableName = tableName;
  }

  public String getUppername() {
    return uppername;
  }

  public void setUppername(String uppername) {
    this.uppername = uppername;
  }

}
