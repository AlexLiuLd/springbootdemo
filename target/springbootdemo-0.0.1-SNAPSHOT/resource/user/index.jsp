<%@ page contentType="text/html; charset=UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort()
            + path + "/";
%>
<!DOCTYPE html>
<html>
<body>
<div> 当前环境：${environment}</div>
<div> haha：${haha}</div>
<div>
    <c:forEach items="${userList }" var="item">
        <label value="${item.id }">${item.userName }</label>
    </c:forEach>
</div>
</body>
</html>