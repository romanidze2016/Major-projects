<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
    <%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
        <html>
        <head>
        	<link href="<c:url value="/resources/css/main.css" />" rel="stylesheet">
            <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
            <title>Registration</title>
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
	        	<h2 align="center">Register</h2>
	            <form:form align="center" id="regForm" modelAttribute="user" action="addUser" method="post">
	                <table align="center">
	                    <tr>
	                        <td>
	                            <input path="username" name="username" id="username" class="css-input" placeholder="Username" required/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>
	                            <input type="password" minlength="6" path="password" name="password" id="password" class="css-input" placeholder="Password" required/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>
	                            <input path="first" name="first" id="first" class="css-input" placeholder="First name" required/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>
	                            <input path="last" name="last" id="last" class="css-input" placeholder="Last name" required/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td>
	                            <input path="email" name="email" id="email" class="css-input" placeholder="Email" required/>
	                        </td>
	                    </tr>
	                    <tr>
	                        <td align="center"><input class="myButton" type="submit" value="Create account"></td>
	                    </tr>
	                </table>
	            </form:form>
            </div>
        </body>
        </html>