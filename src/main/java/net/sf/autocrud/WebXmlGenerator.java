// $Id: WebXmlGenerator.java,v 1.2 2007/07/15 21:02:02 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/WebXmlGenerator.java,v $
package net.sf.autocrud;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.ui.velocity.VelocityEngineUtils;

/**
 * Generate a minimal web.xml file with a reference to the appropriate
 * Spring DispatcherServlet.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class WebXmlGenerator {

  private Log logger = LogFactory.getLog(ActiveRecordGenerator.class);
  
  private VelocityEngine velocityEngine;
  private String templateName;
  private DatabaseMetadata dbMetadataDao;
  private String webAppName;
  
  public void setDbMetadataDao(DatabaseMetadata dbMetadataDao) {
    this.dbMetadataDao = dbMetadataDao;
  }
  
  public void setTemplateName(String templateName) {
    this.templateName = templateName;
  }
  
  public void setVelocityEngine(VelocityEngine velocityEngine) {
    this.velocityEngine = velocityEngine;
  }
  
  public void setWebAppName(String webAppName) {
    this.webAppName = webAppName;
  }

  public void generate() throws Exception {
    Map<String,Object> model = new HashMap<String,Object>();
    model.put("webAppName", webAppName);
    String output = VelocityEngineUtils.mergeTemplateIntoString(
      velocityEngine, templateName, model);
    FileUtils.writeStringToFile(getTargetFile(), output, "UTF-8");
  }
  
  private File getTargetFile() throws Exception {
    File targetFile = new File("src/main/webapp/WEB-INF/web.xml");
    File targetDir = targetFile.getParentFile();
    if (!(targetDir.exists())) {
      FileUtils.forceMkdir(targetDir);
    }
    return targetFile;
  }
}
