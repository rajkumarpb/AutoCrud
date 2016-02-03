// $Id: NamingUtils.java,v 1.2 2007/07/15 21:02:02 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/NamingUtils.java,v $
package net.sf.autocrud;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.WordUtils;

/**
 * Some standard String manipulation utilities to convert between database
 * underscore separated names to Java camelcased variable names.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class NamingUtils {

  public static String toCamelCase(String underscoreSeparatedString, boolean capitalize) {
    String[] parts = StringUtils.split(underscoreSeparatedString, '_');
    for (int i = 1; i < parts.length; i++) {
      parts[i] = WordUtils.capitalize(parts[i]);
    }
    String camelCasedString = StringUtils.join(parts, ' ').replaceAll("\\s+", ""); 
    if (capitalize) {
      return WordUtils.capitalize(camelCasedString);
    } else {
      return camelCasedString;
    }
  }
  
  public static String toUnderscoreSeparated(String camelCasedString) {
    StringBuilder builder = new StringBuilder();
    StringTokenizer tokenizer = new StringTokenizer(camelCasedString, "ABCDEFGHIJKLMNOPQRSTUVWXYZ", true);
    int i = 0;
    while (tokenizer.hasMoreTokens()) {
      String token = tokenizer.nextToken();
      if (i > 0 && token.length() == 1 && Character.isUpperCase(token.charAt(0))) {
        builder.append("_");
      }
      builder.append(StringUtils.lowerCase(token));
      i++;
    }
    return builder.toString();
  }

  public static String getReferencedTable(String colName) {
    return colName.replaceAll("_id$", "");
  }
}
