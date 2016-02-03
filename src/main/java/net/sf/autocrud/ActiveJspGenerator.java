// $Id: ActiveJspGenerator.java,v 1.2 2007/07/15 21:02:01 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/ActiveJspGenerator.java,v $
package net.sf.autocrud;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Generates the two JSP files needed for each table.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ActiveJspGenerator {

  private VelocityEngine velocityEngine;
  private DatabaseMetadata dbMetadataDao;
  private String webAppName;
  
  public void setDbMetadataDao(DatabaseMetadata dbMetadataDao) {
    this.dbMetadataDao = dbMetadataDao;
  }
  
  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }

  public void setWebAppName(String webAppName) {
    this.webAppName = webAppName;
  }
  
  public void generate(String tableName, String viewName) throws Exception {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("webAppName", webAppName);
    model.put("tableName", tableName);
    model.put("viewName", viewName);
    List<ColumnDataHolder> columnDataHolders = getColumnDataHolders(tableName);
    model.put("columnDataHolders", columnDataHolders);
    String output = VelocityEngineUtils.mergeTemplateIntoString(
      velocityEngine, viewName + "Jsp.vm", model);
    FileUtils.writeStringToFile(getTargetFile(tableName, viewName), output, "UTF-8");
  }
  
  private List<ColumnDataHolder> getColumnDataHolders(String tableName) throws Exception {
    List<ColumnDataHolder> columnDataHolders = new ArrayList<ColumnDataHolder>();
    List<NameValuePair> columnInfos = dbMetadataDao.getColumns(tableName);
    for (NameValuePair columnInfo : columnInfos) {
      String columnInfoName = columnInfo.getName();
      if (columnInfoName.equals("id")) {
        continue;
      }
      ColumnDataHolder columnDataHolder = new ColumnDataHolder();
      columnDataHolder.setColumnName(columnInfoName);
      columnDataHolder.setJavaName(NamingUtils.toCamelCase(columnInfoName, false));
      columnDataHolder.setJstlGetExpr("${record." + columnDataHolder.getJavaName() + "}");
      columnDataHolder.setJavaType(columnInfo.getValue());
      columnDataHolders.add(columnDataHolder);
    }
    return columnDataHolders;
  }

  private File getTargetFile(String tableName, String viewName) throws Exception {
    File targetFile = new File(StringUtils.join(new String[] {
        "src/main/webapp/", 
        tableName,
        viewName + ".jsp"
    }, File.separatorChar));
    File targetDir = targetFile.getParentFile();
    if (! targetDir.exists()) {
      FileUtils.forceMkdir(targetDir);
    }
    return targetFile;
  }
}
