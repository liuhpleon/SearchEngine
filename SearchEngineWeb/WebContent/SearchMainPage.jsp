  <%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head><title>Search engine</title>
<style>
    html{
        height: 100%;
    }
    body{  
        background-repeat: no-repeat;
        background-attachment: fixed;
        background-position: 50% 102% ; 
    }
</style>
</head>
<body background="C:/Users/Jessie/Desktop/searchEngineInterface/UCR.png">
    <center>
        <img src="C:/Users/Jessie/Desktop/searchEngineInterface/edu.png" alt="edu" width="350" height="200">
    </center>
<form method="GET" action='search'>
<center>
<table>
<tr></tr>
  <tr>
   	<font colspan="4"><input type="textbox" name="query" style="width:400px; height:23px;" /></font>
    <font><input type="submit" name="search" value="Search"  style="height:30px; width:60px" />&nbsp; </font>
  </tr>
  <tr>
  <p>
    <font color="black"><strong>Please Select Maximum Return Result Amount</strong></font>
    <select>
      <option value="100">100</option>
    </select>
    </p>
  </tr> 
  <tr>
    <td><input type="radio" name="type" value="lucene"/></td>
    <td><font color = "black"><strong>Lucene</strong></font></td>
    <td><input type="radio" name="type" value="hadoop"/></td>
    <td><font color = "black"><strong>Hadoop</strong></font></td>
  </tr>  
</table>
</center>
</form>
</body>
</html>