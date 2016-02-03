package net.sf.autocrud;

import net.sf.autocrud.ActiveJspGenerator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;


/**
 * TODO: Class level Javadocs
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ActiveJspGeneratorTest {

  private ApplicationContext context;
  private ActiveJspGenerator generator;
  
  @Before
  public void setUp() throws Exception {
    context = new ClassPathXmlApplicationContext("classpath:applicationContext-autocrud.xml");
    generator = (ActiveJspGenerator) context.getBean("activeJspGenerator");
  }
  
  @Test
  public void testGeneration() throws Exception {
    String[] tableNames = {"project", "person", "project_person", "task", "hours"};
    String[] viewNames = {"list", "edit", "show"};
    for (String tableName : tableNames) {
      for (String viewName : viewNames) {
        generator.generate(tableName, viewName);
      }
    }
  }
}
