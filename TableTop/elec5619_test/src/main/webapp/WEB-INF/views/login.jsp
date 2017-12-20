<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Login</title>
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
		<h2 align="center">Login</h2>
		<p align="center" style="color:red">${errorMessage}</p>
		<form align="center" class="form-signin" action="login" method="post">
			<table align="center">
				<tr>
					<td><input type="text" id="username" name="username" class="css-input" placeholder="Username" required autofocus></td>
				</tr>
				<tr>
					<td><input type="password" id="password" name="password" class="css-input" placeholder="Password" required></td>
				</tr>
				<tr>
					<td><button class="myButton">Sign In</button></td>
				</tr>
			</table>
		</form>
	</div>
</body>
</html>