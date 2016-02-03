<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>hour:edit</title>
</head>
<body>
  <h1>hour:edit</h1>
  <form name="edit" action="/prozac/hour.do" method="post">
    <input type="hidden" name="action" value="save"/>
    <input type="hidden" name="id" value="${record.id}"/>
    <table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tr>
        <td><b>taskId</b></td>
        <td><input type="text" name="task_id" value="${record.taskId}"/></td>
      </tr>
      <tr>
        <td><b>logDate</b></td>
        <td><input type="text" name="log_date" value="${record.logDate}"/></td>
      </tr>
      <tr>
        <td><b>actHrs</b></td>
        <td><input type="text" name="act_hrs" value="${record.actHrs}"/></td>
      </tr>
    </table>
    <input type="submit" name="submit" value="Submit"/>&nbsp;&nbsp;
    <input type="button" name="cancel" value="Cancel" onclick="javascript:window.location='/prozac/hour.do?action=list'"/>
  </form>
</body>
</html>
