// $Id: JavaTypeMap.java,v 1.2 2007/07/15 21:02:02 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/JavaTypeMap.java,v $
package net.sf.autocrud;

import java.util.Properties;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.Resource;

/**
 * Describes mappings between java.sql.Types returned from the database
 * and the appropriate Java type. The default set is defined in the
 * resource file types.properties. If specific overrides are desired,
 * then the database vendor name and SQL type is used as the key.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class JavaTypeMap {

  private Log logger = LogFactory.getLog(JavaTypeMap.class);
  
  private Properties props;
  
  public void setTypesProperties(Resource typesProperties) {
    props = new Properties();
    try {
      props.load(typesProperties.getInputStream());
    } catch (Exception e) {
      logger.error("Could not load properties from resource file types.properties", e);
      throw new RuntimeException(e);
    }
  }
  
  public String getJavaType(String sqlType) {
    String javaType = props.getProperty(sqlType);
    if (javaType == null) {
      return sqlType;
    }
    return javaType;
  }
}
