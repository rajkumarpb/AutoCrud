// $Id: ActiveRecordGeneratorTest.java,v 1.2 2007/07/15 21:02:07 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/test/java/net/sf/autocrud/ActiveRecordGeneratorTest.java,v $
package net.sf.autocrud;

import net.sf.autocrud.ActiveRecordGenerator;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO: Class level Javadocs
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ActiveRecordGeneratorTest {

  private Log logger = LogFactory.getLog(ActiveRecordGeneratorTest.class);
  
  private static ApplicationContext context;
  private ActiveRecordGenerator activeRecordGenerator;
  
  @BeforeClass
  public static void setUpBeforeClass() throws Exception {
    context = new ClassPathXmlApplicationContext("classpath:applicationContext-autocrud.xml");
  }
  
  @Before
  public void setUp() throws Exception {
    activeRecordGenerator = (ActiveRecordGenerator) context.getBean("activeRecordGenerator");
  }
  
  @Test
  public void testGeneration() throws Exception {
    activeRecordGenerator.generate("project");
    activeRecordGenerator.generate("person");
    activeRecordGenerator.generate("task");
    activeRecordGenerator.generate("hour");
    activeRecordGenerator.generate("project_person");
  }
}
