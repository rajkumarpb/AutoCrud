<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>task:edit</title>
</head>
<body>
  <h1>task:edit</h1>
  <form name="edit" action="/prozac/task.do" method="post">
    <input type="hidden" name="action" value="save"/>
    <input type="hidden" name="id" value="${record.id}"/>
    <table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tr>
        <td><b>projectId</b></td>
        <td><input type="text" name="project_id" value="${record.projectId}"/></td>
      </tr>
      <tr>
        <td><b>seqId</b></td>
        <td><input type="text" name="seq_id" value="${record.seqId}"/></td>
      </tr>
      <tr>
        <td><b>name</b></td>
        <td><input type="text" name="name" value="${record.name}"/></td>
      </tr>
      <tr>
        <td><b>estHrs</b></td>
        <td><input type="text" name="est_hrs" value="${record.estHrs}"/></td>
      </tr>
    </table>
    <input type="submit" name="submit" value="Submit"/>&nbsp;&nbsp;
    <input type="button" name="cancel" value="Cancel" onclick="javascript:window.location='/prozac/task.do?action=list'"/>
  </form>
</body>
</html>
