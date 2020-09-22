<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Admin page</title>
</head>
<body>
<hr/>
${user},Hello!
<hr/>
<form name="AdminForm" method="post" action="controller">
    <input type="hidden" name="command" value="admin"/>
    <input type="submit" value="Show all users">
<a href="controller?command=Logout">Logout</a>
</form>
</body>
</html>
