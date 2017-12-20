<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>TableTop</title>
</head>
<body>
	<div id="header">
    	<h1 id="title"><a class="titleLink" href="index">TableTop</a></h1>
    		
    	<ul id="navigation">
  			<li class="navItem"><a class="navLink" href="index">Home</a></li>
  			<li class="navItem"><a class="navLink" href="join">Join</a></li>
  			<li class="navItem"><a class="navLink" href="create">Create</a></li>
  			<li class="navItem"><a class="navLink" href="friends">Friends</a></li>
  			<li class="navItem"><a class="navLink" href="account">Account</a></li>
  			<c:choose>
  				<c:when test = "${empty loggedInUser.username}">
  					<li class="navItem"><a class="navLink" href="register">Register</a></li>
  					<li class="navItem"><a class="navLink" href="login">Log in</a></li>
  				</c:when>
  				<c:otherwise>
  					<li class="navItem"><a class="navLink" href="logout">Log out</a></li>
  				</c:otherwise>
  			</c:choose>
		</ul>
    </div>
    		
    <div id="content">
    	<ul class="tabs">
    		<li class="unselected"><a class="unselectedLink" href="friends">Find</a></li>
    		<li class="unselected"><a class="unselectedLink" href="friendlist">Friend List</a></li>
    		<li class="selected"><a class="selectedLink" href="friendrequests">Friend Requests</a></li>
    	</ul>
    	
	    <h2>Friend requests</h2>
		
		<form class="friendRequest" action="" method="post" name="mainForm">
			<table class="resultTable">
				<c:choose>
	  				<c:when test = "${empty listOfUsers}">
	  				</c:when>
	  				<c:otherwise>
	  					<tr class="resultTr">
			      			<td style="font-weight:bold;">Username</td>
			      			<td style="font-weight:bold;">First name</td>
			      			<td style="font-weight:bold;">Last name</td>
			      			<td style="font-weight:bold;">Email</td>
			      			<td></td>
		    			</tr>
	  				</c:otherwise>
  				</c:choose>
			
				<c:forEach items="${listOfUsers}" var="user">
	    			<tr class="resultTr">
	      			<td><c:out value="${user.username}" /></td>
	      			<td><c:out value="${user.first}" /></td>
	      			<td><c:out value="${user.last}" /></td>
	      			<td><c:out value="${user.email}" /></td>
	      			<td><button onclick="calA();" class="myButton" id="smallButton" name="friend" value="${user.username}">Accept</button></td>
	    			<td><button onclick="calB();" class="myButton" id="smallButton" name="friend" value="${user.username}">Reject</button></td>
	    			</tr>
	    		</c:forEach>
			</table>
		</form>
		<script>
			function calA()
			{
			 document.mainForm.action ="acceptFriend";
			}
			function calB()
			{
			document.mainForm.action = "rejectRequest";
			}
		</script>
    </div>
</body>
</html>