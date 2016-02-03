// $Id: DbMetadataDaoTest.java,v 1.2 2007/07/15 21:02:08 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/test/java/net/sf/autocrud/DbMetadataDaoTest.java,v $
package net.sf.autocrud;

import java.util.List;

import junit.framework.Assert;

import net.sf.autocrud.DatabaseMetadata;

import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Test for DbMetaDataDao.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class DbMetadataDaoTest {

  private Log logger = LogFactory.getLog(DbMetadataDaoTest.class);
  
  private DatabaseMetadata metadataDao;
  
  /**
   * @throws java.lang.Exception
   */
  @Before
  public void setUp() throws Exception {
    ApplicationContext context = new ClassPathXmlApplicationContext(new String[] {
      "classpath:applicationContext-autocrud.xml"  
    });
    metadataDao = (DatabaseMetadata) context.getBean("databaseMetadata");
  }

  @Test
  public void testGetAllTables() throws Exception {
    List<String> tables = metadataDao.getTables();
//    logger.debug("tables=" + tables.toString());
    Assert.assertTrue(tables.size() > 0);
    Assert.assertTrue(tables.contains("hour"));
    Assert.assertTrue(tables.contains("project"));
    Assert.assertTrue(tables.contains("project_person"));
    Assert.assertTrue(tables.contains("person"));
    Assert.assertTrue(tables.contains("task"));
  }
  
  @Test
  public void testGetColumns() throws Exception {
    List<NameValuePair> columns = metadataDao.getColumns("project");
    logger.debug("columns=" + columns.toString());
  }
  
  @Test
  public void testGetReferenceTables() throws Exception {
    List<String> tables = metadataDao.getReferenceTables("project");
    logger.debug("R(project)=" + tables.toString());
    tables = metadataDao.getReferenceTables("person");
    logger.debug("R(person)=" + tables.toString());
    tables = metadataDao.getReferenceTables("project_person");
    logger.debug("R(project_person)=" + tables.toString());
  }
  
  @Test 
  public void testGetReferencingTables() throws Exception {
    List<String> tables = metadataDao.getReferencingTables("project");
    logger.debug("r(project)=" + tables.toString());
    tables = metadataDao.getReferencingTables("task");
    logger.debug("r(task)=" + tables.toString());
    tables = metadataDao.getReferencingTables("hour");
    logger.debug("r(hour)=" + tables.toString());
    tables = metadataDao.getReferencingTables("person");
    logger.debug("r(person)=" + tables.toString());
    tables = metadataDao.getReferencingTables("project_person");
    logger.debug("r(project_person)=" + tables.toString());
  }
}
