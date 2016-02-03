package net.sf.autocrud;


import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Generates the ApplicationContext file for the application.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ApplicationContextGenerator {

  private Log logger = LogFactory.getLog(ActiveRecordGenerator.class);
  
  private VelocityEngine velocityEngine;
  private String templateName;
  private DatabaseMetadata dbMetadataDao;
  private String projectPackage;
  private String webAppName;
  
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
  
  public void setWebAppName(String webAppName) {
    this.webAppName = webAppName;
  }
  
  public void generate() throws Exception {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("packageName", projectPackage);
    List<String> tableNames = dbMetadataDao.getTables();
    List<BeanDataHolder> beanDataHolders = new ArrayList<BeanDataHolder>();
    for (String tableName : tableNames) {
      BeanDataHolder beanDataHolder = new BeanDataHolder();
      beanDataHolder.setTableName(tableName);
      String beanName = getBeanName(tableName);
      beanDataHolder.setName(StringUtils.uncapitalize(beanName));
      beanDataHolder.setUppername(beanName);
      beanDataHolders.add(beanDataHolder);
    }
    model.put("beans", beanDataHolders);
    String output = VelocityEngineUtils.mergeTemplateIntoString(
      velocityEngine, templateName, model);
    FileUtils.writeStringToFile(getTargetFile(), output, "UTF-8");
  }
  
  private String getBeanName(String tableName) {
    return NamingUtils.toCamelCase(tableName, true);
  }
  
  private File getTargetFile() throws Exception {
    File targetFile = new File("src/main/webapp/WEB-INF/" + webAppName + "-servlet.xml");
    File targetDir = targetFile.getParentFile();
    if (!(targetDir.exists())) {
      FileUtils.forceMkdir(targetDir);
    }
    return targetFile;
  }
}
