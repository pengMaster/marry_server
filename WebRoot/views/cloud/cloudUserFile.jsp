<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.mtm.party.mobile.model.CloudUserBean" %>
<%
    String path = request.getContextPath();
    String pathTomcat = request.getSession().getServletContext().getRealPath("/");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    List<CloudUserBean> list = (List<CloudUserBean>) request.getAttribute("listFile");

%>
<!DOCTYPE html>
<html>
<head lang="en">
    <meta name="viewport"
          content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta charset="UTF-8">
    <title>cloudUser</title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/views/cloud/css/cloud.css"/>

</head>
<body style="text-align: center">
    <div class="item_layout">
        <% for (int i = 0; i < list.size(); i++) {%>
            <%if(list.get(i).getType().equals("image")){%>
                <div class="file_layout">
                    <img width="300" height="150"  src="http://47.104.198.222<%=list.get(i).getChildPath()%>"/>
                    <div><%=list.get(i).getName()%></div>
                </div>
            <% }else{%>
            <div class="file_layout">
                <video  width="300" height="150" src="http://47.104.198.222<%=list.get(i).getChildPath()%>"></video>
                <div><%=list.get(i).getName()%></div>
            </div>
        <% }%>
        <% }%>
    </div>
</body>

<script type="text/javascript">

    function onClickItem(path, grade) {

    }
</script>
</html>