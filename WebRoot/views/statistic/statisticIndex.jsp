<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>
<%@ page import="com.mtm.party.user.model.User"%>
<%
String path = request.getContextPath();
String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
%>
<!DOCTYPE html>
<html>
<head lang="en">
	<%
	List list = (List)request.getAttribute("list");
	%>
    <meta name="viewport" content="width=device-width, initial-scale=1.0, minimum-scale=1.0, maximum-scale=1.0, user-scalable=no">
    <meta charset="UTF-8">
    <title></title>
    <link rel="stylesheet" type="text/css" href="<%=basePath %>/views/statistic/css/BigDataShow.css" />

</head>
<body style="background-color: #313131;;text-align: center">
        <div id="datashow-main" class="datashow-main">
            <div id="datashow-title">
                KingMarry综合大数据平台
            </div>
            <div id="datashow-top-nav">
                <table border="0" align="center">
                    <tr>
                        <td><div id="datashow-nav-car" class="datashow-top-nav">
                            累计登陆次数<br>
                            123891
                        </div></td>
                        <td style="width: 50%" rowspan="2">
                            <div class="wrap" style="width: 450px;">
                                <div class="items" id="Item0">
                                    <div class="itemCon">
                                        <div id="Map"  ></div>
                                    </div>
                                </div>
                            </div>
                        </td>
                        <td><div id="datashow-nav-people" class="datashow-top-nav">
                            人员总量<br>
                            2341
                        </div></td>
                    </tr>
                    <tr>
                        <td><div id="datashow-nav-ytpeople" class="datashow-top-nav">
                            今日登陆次数<br>
                            350
                        </div></td>
                        <td><div id="datashow-nav-operation" class="datashow-top-nav">
                            今日新增用户<br>
                            28
                        </div></td>
                    </tr>
                </table>
            </div>
            <div class="datashow-charts" >
                <div class="datashow-chart" id="chart-nowcar"></div>
                <div class="datashow-chart" id="chart-7daycar"></div>
                <div class="datashow-chart" id="chart-6monthcar"></div>
                <div class="datashow-chart" id="chart-maxcar"></div>
            </div>
            <div class="datashow-charts">
                <div class="datashow-chart" id="chart-yearpeople"></div>
                <div class="datashow-chart" id="chart-peoplechange"></div>
                <div class="datashow-chart" id="chart-sexpeople"></div>
                <div class="datashow-chart" id="chart-agepeople"></div>
            </div>
            <div class="datashow-charts">
                <div class="datashow-chart" id="chart-countrypeople"></div>
                <div class="datashow-chart" id="chart-citypeople"></div>
                <div class="datashow-chart" id="chart-culturepeople"></div>
                <div class="datashow-chart" id="chart-jobpeople"></div>
            </div>
            <div class="datashow-charts">
                <div class="datashow-chart" id="chart-yearperson"></div>
                <div class="datashow-chart" id="chart-changeperson"></div>
                <div class="datashow-chart" id="chart-sexperson"></div>
                <div class="datashow-chart" id="chart-ageperson"></div>
            </div>
            <div class="datashow-charts">
                <div class="datashow-chart" id="chart-dirverperson"></div>
                <div class="datashow-chart" id="chart-foreignperson"></div>
                <div class="datashow-chart" id="chart-prisonperson"></div>
                <div class="datashow-chart" id="chart-eventperson"></div>
            </div>
        </div>
        
</body>
<script src="<%=basePath %>/views/statistic/js/jquery-1.7.2.min.js"></script>
<script src="<%=basePath %>/views/statistic/js/highcharts.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-nowcar.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-7daycar.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-6monthcar.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-maxcar.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-yearpeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-peoplechange.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-sexpeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-sexperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-dirverperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-agepeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-ageperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-countrypeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-citypeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-culturepeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-jobpeople.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-yearperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-changeperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-prisonperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-foreignperson.js"></script>
<script src="<%=basePath %>/views/statistic/js/chart-eventperson.js"></script>


<script type="text/javascript" src="<%=basePath %>/views/statistic/js/lib/raphael-min.js"></script>
<script type="text/javascript" src="<%=basePath %>/views/statistic/js/map.js"></script>
<script type="text/javascript" src="<%=basePath %>/views/statistic/data/c3706.js"></script>
<script type="text/javascript" src="<%=basePath %>/views/statistic/data/china.js"></script>
<script type="text/javascript">
    $(function(){
        // 默认
        $('#Map').SVGMap({
            mapName: 'china',
            mapWidth: 400,
            mapHeight: 300,
            showName: true
        });
    });
</script>
</html>