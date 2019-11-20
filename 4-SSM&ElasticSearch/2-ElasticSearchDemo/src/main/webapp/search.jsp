<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>搜索测试</title>
</head>
<body>
    <form action="search.do" method="POST">
        关键字：<input type="text" name="keyword"><br/>
        地点：<input type="text" name="jobaddr"><br/>
        薪资（10-15）：<input type="text" name="salary"><br/>
        <input type="submit" value="搜索"><br/>
    </form>
</body>
</html>