<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Join</title>
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
      <h2>Create Room</h2>

        
      <div style="float: left; margin-top: 30px;">
      <h2 style="margin-top: 10px;">Friends</h2>
      <form action="inviteFriend" method="post">
	      <table class="resultTable">
	      	<tr class="resultTr">
	      		<td style="font-weight:bold;">Username</td>
	      		<td style="font-weight:bold;">First name</td>
	           	<td style="font-weight:bold;">Last name</td>
	            <td style="font-weight:bold;">Add</td>
	            <td></td>
	        </tr>
	        
	        <c:forEach items="${listOfFriends}" var="user">
	            <tr class="resultTr">
	              <td><c:out value="${user.username}" /></td>
	              <td><c:out value="${user.first}" /></td>
	              <td><c:out value="${user.last}" /></td>
	              <td><button class="myButton" id="smallButton" name="invite" value="${user.username}">+</button></td>
	            </tr>
	          </c:forEach>
	      </table>
      </form>
    </div>

    <div style="float: right; margin-top: 30px; margin-right: 30px">
      <h2 style="margin-top: 10px;">Invited</h2>
      <form action="removeMember" method="post">
	      <table class="resultTable">
	      	<tr class="resultTr">
	            <td style="font-weight:bold;">Username</td>
	            <td style="font-weight:bold;">First name</td>
	            <td style="font-weight:bold;">Last name</td>
	  	    	<td style="font-weight:bold;">Remove</td>
         		<td></td>
	        </tr>
	        
	        <c:forEach items="${listOfMembers}" var="member">
	            <tr class="resultTr">
	              <td><c:out value="${member.username}" /></td>
	              <td><c:out value="${member.first}" /></td>
	              <td><c:out value="${member.last}" /></td>
	              <td><button class="myButton" id="smallButton" name="remove" value="${member.username}">-</button></td>
	            </tr>
	          </c:forEach>
	      </table>
    	</form>
    
    </div>
      
    <a class="roomButton" href="removeRoom" style="position:fixed; left:30px; bottom:50px;">Remove room</a>
    <a class="roomButton" href="lobby" style="position: fixed; bottom: 50px; right:50px;">Create</a>

  	</div>
</body>
</html>