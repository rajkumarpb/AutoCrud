<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>project:edit</title>
</head>
<body>
  <h1>project:edit</h1>
  <form name="edit" action="/prozac/project.do" method="post">
    <input type="hidden" name="action" value="save"/>
    <input type="hidden" name="id" value="${record.id}"/>
    <table cellspacing="0" cellpadding="0" border="0" width="100%">
      <tr>
        <td><b>name</b></td>
        <td><input type="text" name="name" value="${record.name}"/></td>
      </tr>
      <tr>
        <td><b>description</b></td>
        <td><input type="text" name="description" value="${record.description}"/></td>
      </tr>
      <tr>
        <td><b>startDt</b></td>
        <td><input type="text" name="start_dt" value="${record.startDt}"/></td>
      </tr>
      <tr>
        <td><b>endDt</b></td>
        <td><input type="text" name="end_dt" value="${record.endDt}"/></td>
      </tr>
    </table>
    <input type="submit" name="submit" value="Submit"/>&nbsp;&nbsp;
    <input type="button" name="cancel" value="Cancel" onclick="javascript:window.location='/prozac/project.do?action=list'"/>
  </form>
</body>
</html>
