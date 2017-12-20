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
    	<h2>Room lobby</h2>
      	<% response.setIntHeader("Refresh", 5); %>
      	
      	<h3>Room creator</h3>
      	<form action="" method="post">
	      <table class="resultTable">
	      	<tr class="resultTr">
	      		<td style="font-weight:bold;">Username</td>
	      		<td style="font-weight:bold;">First name</td>
	           	<td style="font-weight:bold;">Last name</td>
	       	</tr>
	            <tr class="resultTr">
	            <td><c:out value="${roomCreator.username}" /></td>
	            <td><c:out value="${roomCreator.first}" /></td>
	       		<td><c:out value="${roomCreator.last}" /></td>
	      	</tr>
	      </table>
      	</form> 
      	
      	<h3>Invited players</h3>
      	<form action="" method="post">
	      <table class="resultTable">
	      	<tr class="resultTr">
	      		<td style="font-weight:bold;">Username</td>
	      		<td style="font-weight:bold;">First name</td>
	           	<td style="font-weight:bold;">Last name</td>
	           	<td style="font-weight:bold;">Status</td>
	        </tr>
	        
	        <c:forEach items="${players}" var="player">
	            <tr class="resultTr">
	              <td><c:out value="${player.username}" /></td>
	              <td><c:out value="${player.first}" /></td>
	              <td><c:out value="${player.last}" /></td>
	              <td><c:out value="${player.string}" /></td>
	            </tr>
	      	</c:forEach>
	      </table>
      </form> 
      
		<c:choose>
  			<c:when test = "${empty player}">
  				<a class="roomButton" href="cancel" style="bottom: 50px;">Cancel</a>
  				<a class="roomButton" href="room" style="bottom: 50px; right:50px;">Start game</a> 
  			</c:when>
  			<c:otherwise>
  				<a class="roomButton" href="exit" style="bottom: 50px;">Exit</a> 
  			</c:otherwise>
  		</c:choose>
	</div>


</body>
</html>