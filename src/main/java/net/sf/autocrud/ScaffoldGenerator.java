// $Id: ScaffoldGenerator.java,v 1.2 2007/07/15 21:02:02 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/ScaffoldGenerator.java,v $
package net.sf.autocrud;

import java.util.List;

/**
 * Top level class which delegates to the various generators and generates
 * the scaffold files for the entire application. This generator only has
 * dependencies on other generators.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ScaffoldGenerator {

  private String[] JSP_NAMES = {"list", "show", "edit"};
  
  private DatabaseMetadata dbMetadataDao;
  private ActiveRecordGenerator activeRecordGenerator;
  private ActiveJspGenerator activeJspGenerator;
  private IndexJspGenerator indexJspGenerator;
  private ApplicationContextGenerator applicationContextGenerator;
  private WebXmlGenerator webXmlGenerator;
  
  public void setActiveJspGenerator(ActiveJspGenerator activeJspGenerator) {
    this.activeJspGenerator = activeJspGenerator;
  }
  
  public void setActiveRecordGenerator(ActiveRecordGenerator activeRecordGenerator) {
    this.activeRecordGenerator = activeRecordGenerator;
  }
  
  public void setApplicationContextGenerator(ApplicationContextGenerator applicationContextGenerator) {
    this.applicationContextGenerator = applicationContextGenerator;
  }
  
  public void setDbMetadataDao(DatabaseMetadata dbMetadataDao) {
    this.dbMetadataDao = dbMetadataDao;
  }
  
  public void setIndexJspGenerator(IndexJspGenerator indexJspGenerator) {
    this.indexJspGenerator = indexJspGenerator;
  }

  public void setWebXmlGenerator(WebXmlGenerator webXmlGenerator) {
    this.webXmlGenerator = webXmlGenerator;
  }
  
  public void generate() throws Exception {
    List<String> tableNames = dbMetadataDao.getTables();
    for (String tableName : tableNames) {
      activeRecordGenerator.generate(tableName);
      for (String jspName : JSP_NAMES) {
        activeJspGenerator.generate(tableName, jspName);
      }
    }
    indexJspGenerator.generate();
    applicationContextGenerator.generate();
    webXmlGenerator.generate();
  }
  
}
