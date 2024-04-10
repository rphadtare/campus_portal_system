<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="content-type" content="text/html; charset=utf-8" />
<title>Campus Portal System</title>
<meta name="keywords" content="" />
<meta name="description" content="" />
<link href="default.css" rel="stylesheet" type="text/css" />

<script type="text/javascript">
    function valid()
    {
        document.form2.submit();
    }
</script>

</head>
<body>
<!-- start header -->
<div id="header">
	<div id="menu">
		<ul>
			<li><a href="/login">Login</a></li>
		</ul>
	</div>
</div>
<!-- end header -->
<!-- start page -->
<div id="page">
<!-- 	start content -->
	<div id="content">
		<div class="post">
			<h1 class="title">Welcome to Campus Portal</h1>
		</div>
	</div>
</div>
<!-- end page -->
<!-- ======================================================================================================== -->

    <h3>Registration for Head Of Department</h3>
        <div style="height: 339px; margin-left: 4px">
            <form action="/department_register" method="post" name="form2">
            &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
    	    <table align="center" style="width: 75%; height: 232px;">
    				<tr>
    				    <tr>
                           <td>institute_id</td>
                           <td>institute name</td>
                           <td>institute university</td>
                           <td>institute email id</td>
                           <td>institute.address</td>
                        </tr>
    				    <c:forEach items="${institutes}" var="institute">
                           <tr>
                               <td>${institute.institute_id}</td>
                               <td>${institute.name}</td>
                               <td>${institute.university}</td>
                               <td>${institute.email_id}</td>
                               <td>${institute.address}</td>
                           </tr>
                        </c:forEach>


    					<td class="auto-style7"><input id="Button1" type="button"
    						value="Submit" onclick="valid()" /></td>
    					<td class="auto-style9"><input id="Reset1" type="reset"
    						value="Reset" /></td>
    				</tr>
    			</table>
    		</form>
        </div>


    <div id="footer">
	    <p id="legal">&copy;2023 Compaus Portal. All Rights Reserved. | Designed by Rutu</p>
    </div>
</body>
</html>