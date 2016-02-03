// $Id: ActiveController.java,v 1.2 2007/07/15 21:02:01 spal Exp $
// $Source: /cvsroot-fuse/autocrud/autocrud/src/main/java/net/sf/autocrud/ActiveController.java,v $
package net.sf.autocrud;

import java.util.List;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

/**
 * Standard superclass controller from which all generated ActiveRecord
 * controllers must extend.
 * @author Sujit Pal (spal@users.sourceforge.net)
 * @version $Revision: 1.2 $
 */
public class ActiveController extends MultiActionController {

  private ActiveRecord activeRecord;
  private String tableName;
  
  public void setActiveRecord(ActiveRecord activeRecord) {
    this.activeRecord = activeRecord;
  }
  
  public void setTableName(String tableName) {
    this.tableName = tableName;
  }
  
  public ModelAndView list(HttpServletRequest request, HttpServletResponse response) throws Exception {
    List<ActiveRecord> records = activeRecord.findAll();
    ModelAndView mav = new ModelAndView();
    mav.addObject("records", records);
    mav.addObject("fields", activeRecord.getFields());
    mav.setViewName(StringUtils.join(new String[] {tableName, "list"}, '/'));
    return mav;
  }
  
  public ModelAndView add(HttpServletRequest request, HttpServletResponse response) throws Exception {
    ActiveRecord record = activeRecord.newInstance(activeRecord.getClass().getName());
    ModelAndView mav = new ModelAndView();
    mav.addObject("record", record);
    mav.setViewName(StringUtils.join(new String[] {tableName, "edit"}, '/'));
    return mav;
  }
  
  public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception {
    long id = ServletRequestUtils.getRequiredLongParameter(request, "id");
    ActiveRecord record = activeRecord.findById(id);
    ModelAndView mav = new ModelAndView();
    mav.addObject("record", record);
    mav.setViewName(StringUtils.join(new String[] {tableName, "edit"}, '/'));
    return mav;
  }
  
  public ModelAndView save(HttpServletRequest request, HttpServletResponse response) throws Exception {
    Set<String> fieldNames = activeRecord.getFields();
    ActiveRecord record = activeRecord.newInstance(activeRecord.getClass().getName());
    for (String fieldName : fieldNames) {
      String value = ServletRequestUtils.getStringParameter(request, fieldName);
      record.setValue(fieldName, value);
    }
    record.save();
    return list(request, response);
  }
  
  public ModelAndView remove(HttpServletRequest request, HttpServletResponse response) throws Exception {
    long id = ServletRequestUtils.getRequiredLongParameter(request, "id");
    ActiveRecord record = activeRecord.findById(id);
    record.delete();
    return list(request, response);
  }
  
  public ModelAndView show(HttpServletRequest request, HttpServletResponse response) throws Exception {
    long id = ServletRequestUtils.getRequiredLongParameter(request, "id");
    ActiveRecord record = activeRecord.findById(id);
    ModelAndView mav = new ModelAndView();
    mav.addObject("record", record);
    mav.addObject("fields", activeRecord.getFields());
    mav.setViewName(StringUtils.join(new String[] {tableName, "show"}, '/'));
    return mav;
  }
  
  public ModelAndView search(HttpServletRequest request, HttpServletResponse response) throws Exception {
    StringBuilder conditionBuilder = new StringBuilder();
    Set<String> fieldNames = activeRecord.getFields();
    for (String fieldName : fieldNames) {
      String value = ServletRequestUtils.getStringParameter(request, fieldName);
      if (StringUtils.isBlank(value)) {
        continue;
      }
      conditionBuilder.append(fieldName + "='" + value + "'");
    }
    List<ActiveRecord> records = activeRecord.find(conditionBuilder.toString());
    ModelAndView mav = new ModelAndView();
    mav.addObject("records", records);
    mav.addObject("fields", activeRecord.getFields());
    mav.setViewName(StringUtils.join(new String[] {tableName, "list"}, '/'));
    return mav;
  }
  
}
