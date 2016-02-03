// $Id: WebXmlGeneratorTest.java,v 1.2 2007/07/15 21:02:08 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/test/java/net/sf/autocrud/WebXmlGeneratorTest.java,v $
package net.sf.autocrud;

import net.sf.autocrud.WebXmlGenerator;

import org.junit.Before;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * TODO: Class level Javadocs
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class WebXmlGeneratorTest {

  private ApplicationContext context;
  private WebXmlGenerator generator;
  
  @Before
  public void setUp() throws Exception {
    context = new ClassPathXmlApplicationContext("classpath:applicationContext-autocrud.xml");
    generator = (WebXmlGenerator) context.getBean("webXmlGenerator");
  }
  
  @Test
  public void testGenerate() throws Exception {
    generator.generate();
  }
}
