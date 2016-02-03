<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>project:show</title>
</head>
<body>
  <h1>project:show</h1>
  <table cellspacing="0" cellpadding="0" border="0" width="100%">
    <tr>
      <td><b>name</b></td>
      <td>${record.name}</td>
    </tr>
    <tr>
      <td><b>description</b></td>
      <td>${record.description}</td>
    </tr>
    <tr>
      <td><b>startDt</b></td>
      <td>${record.startDt}</td>
    </tr>
    <tr>
      <td><b>endDt</b></td>
      <td>${record.endDt}</td>
    </tr>
  </table>
  <input type="button" name="edit" value="Edit" onclick="javascript:window.location='/prozac/project.do?action=edit&id=${record.id}'"/>&nbsp;&nbsp;
  <input type="button" name="delete" value="Delete" onclick="javascript:window.location='/prozac/project.do?action=remove&id=${record.id}'"/>&nbsp;&nbsp;
  <input type="button" name="cancel" value="Cancel" onclick="javascript:window.location='/prozac/project.do?action=list'"/>
</body>
</html>
