<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
    
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>task:list</title>
</head>
<body>
  <h1>task:list</h1>
  <table cellspacing="0" cellpadding="0" border="1" width="100%">
    <tr>
      <th>projectId</th>
      <th>seqId</th>
      <th>name</th>
      <th>estHrs</th>
      <th>View</th>
    </tr>
    <c:forEach items="${records}" var="record">
    <tr>
      <td>${record.projectId}</td>
      <td>${record.seqId}</td>
      <td>${record.name}</td>
      <td>${record.estHrs}</td>
      <td><a href="/prozac/task.do?action=show&id=${record.id}">View</a></td>
    </tr>
    </c:forEach>
  </table>
  <br/>
  <a href="/prozac/task.do?action=add">Add</a>&nbsp;&nbsp;
  <a href="/prozac">Index</a>
</body>
</html>
