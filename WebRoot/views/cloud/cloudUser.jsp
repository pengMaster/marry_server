<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" %>
<%@ page import="com.mtm.party.mobile.model.CloudUserBean" %>
<%
    String path = request.getContextPath();
    String pathTomcat = request.getSession().getServletContext().getRealPath("/");
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + path + "/";
    List<CloudUserBean> list = (List<CloudUserBean>) request.getAttribute("listDirs");

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

        <div class="item_dir" onclick="onClickItem('<%=list.get(i).getPath()%>','<%=list.get(i).getGrade()%>')">
            <%=list.get(i).getName()%>
        </div>

        <% }%>
    </div>

</body>

<script type="text/javascript">

    function onClickItem(path, grade) {
        if ("1" === grade) {
            window.self.location = "<%=basePath %>/cloud/dirItem?path=" + path;
        } else {
            window.self.location = "<%=basePath %>/cloud/file?path=" + path;
        }
    }
</script>
</html>