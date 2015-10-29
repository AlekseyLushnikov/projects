<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
<body>

<jsp:useBean id="userBean" class="com.itis.UserBean" />
<%! com.itis.UserBean userBean1 = new com.itis.UserBean(); %>
<%userBean1.addUsers(); %>
<table>
<c:forEach items="${userBean.getUserlist()}" var="user">
                <tr>
                       <td>${user.getFirstname()}</td>
                       <td>${user.getLastname()}</td>
                       <td>${user.getAge()}</td>
                </tr>
</c:forEach>

</table>
</body>
</head>
</html>
