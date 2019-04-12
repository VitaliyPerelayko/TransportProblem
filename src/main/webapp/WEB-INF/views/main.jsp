<%@ page contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>main</title>
</head>
<body>
<div>
    <ul>
        <li>
            ${message}
        </li>
    </ul>
</div>
<div>
    <p>modelAttribute:</p>
    <form method="GET" action="/modelAttribute">
        <input type="text" name="input"><input type="submit" value="add">
    </form>
</div>
<div>
    <p>viewName:</p>
    <form method="GET" action="/viewName">
        <input type="text" name="value"> <input type="submit" value="add">
    </form>
</div>
</body>
</html>