<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.mtm.party.user.model.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>

<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
  <head>
  
	<%
	List list = (List)request.getAttribute("list");
	%>

    <base href="<%=basePath%>">
    
    <title>Marry后台配置</title>
    
	<meta http-equiv="pragma" content="no-cache">
	<meta http-equiv="cache-control" content="no-cache">
	<meta http-equiv="expires" content="0">    
	<meta http-equiv="keywords" content="keyword1,keyword2,keyword3">
	<meta http-equiv="description" content="This is my page">
	<!--
	<link rel="stylesheet" type="text/css" href="styles.css">
	-->
	
	<style type="text/css">   
    .float{    
        float:left;    
        width : 200px;    
        height: 200px;    
        overflow: hidden;    
        border: 1px solid #CCCCCC;    
        border-radius: 10px;    
        padding: 5px;    
        margin: 5px;    
    }        
	</style>  

	<script src="script/jquery.js" type="text/javascript" ></script>
	 
	<script type="text/javascript">
	 

     
 
	</script>
  </head>
  
  <body>
          
    <div><%=list%></div>
    
    <div>
    <%for(int i=0;i<list.size();i++){%>
       
        <%
        Object[] user = (Object[])list.get(i);
         %>
         <img alt="a" src="<%=user[2]%>">
         <u><%=user[4]%></u>
        <%}%>
    </div>
    
  </body>
</html>
