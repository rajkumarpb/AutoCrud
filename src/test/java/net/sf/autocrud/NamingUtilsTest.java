// $Id: NamingUtilsTest.java,v 1.2 2007/07/15 21:02:08 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/test/java/net/sf/autocrud/NamingUtilsTest.java,v $
package net.sf.autocrud;

import net.sf.autocrud.NamingUtils;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;


/**
 * Test for NamingUtils.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class NamingUtilsTest {

  private Log logger = LogFactory.getLog(NamingUtilsTest.class);
  
  @Test
  public void testConversionFromCamelCaseToUnderscore() throws Exception {
    String input = "projectProperties";
    String output = NamingUtils.toUnderscoreSeparated(input);
    logger.debug("input=" + input + ", output=" + output);
  }
  
  @Test
  public void testConversionFromUnderscoreToCamelCase() throws Exception {
    String input = "project_properties";
    String output = NamingUtils.toCamelCase(input, false);
    logger.debug("input=" + input + ", output=" + output);
  }
  
  @Test
  public void testGetTableNameFromFkColName() throws Exception {
    String input = "resource_id";
    String output = NamingUtils.getReferencedTable(input);
    logger.debug("input=" + input + ", output=" + output);
  }
}
