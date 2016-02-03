// $Id: ActiveRecordGenerator.java,v 1.2 2007/07/15 21:02:01 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/ActiveRecordGenerator.java,v $
package net.sf.autocrud;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Writes a bean for a specified database table and the bean velocity
 * template.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ActiveRecordGenerator {

  private Log logger = LogFactory.getLog(ActiveRecordGenerator.class);
  
  private VelocityEngine velocityEngine;
  private String templateName;
  private DatabaseMetadata dbMetadataDao;
  private String projectPackage;
  
  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }
  
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }
  
  public void setDbMetadataDao(DatabaseMetadata dbMetadataDao) {
    this.dbMetadataDao = dbMetadataDao;
  }
  
  public void setProjectPackage(String projectPackage) {
    this.projectPackage = projectPackage;
  }
  
  public void generate(String tableName) throws Exception {
    Map<String,Object> model = new HashMap<String,Object>();
    String beanName = getBeanName(tableName);
    List<ColumnDataHolder> columnDataHolders = getColumnDataHolders(tableName);
    model.put("columnDataHolders", columnDataHolders);
    model.put("packageName", StringUtils.join(new String[] {projectPackage, "beans"}, '.'));
    model.put("tableName", tableName);
    model.put("beanName", beanName);
    String output = VelocityEngineUtils.mergeTemplateIntoString(
      velocityEngine, templateName, model);
    FileUtils.writeStringToFile(getTargetFile(projectPackage, tableName), output, "UTF-8");
  }
  
  private File getTargetFile(String targetPackage, String tableName) throws Exception {
    File targetFile = new File(StringUtils.join(new String[] {
      "src/main/java/" + 
      StringUtils.replace(targetPackage, ".", File.separator),
      "beans",
      getBeanName(tableName) + ".java"
    }, File.separatorChar));
    File targetDir = targetFile.getParentFile();
    if (! targetDir.exists()) {
      FileUtils.forceMkdir(targetDir);
    }
    return targetFile;
  }
  
  private String getBeanName(String tableName) {
    return NamingUtils.toCamelCase(tableName, true);
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
      columnDataHolder.setJavaName(NamingUtils.toCamelCase(columnInfoName, true));
      columnDataHolder.setJavaType(columnInfo.getValue());
      columnDataHolders.add(columnDataHolder);
    }
    return columnDataHolders;
  }
}
