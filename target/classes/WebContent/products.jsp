<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Insert title here</title>
</head>
<body>
<table>
<c:forEach var="product" items="${products}">
					<c:set var="classProducts" value="" />
					<c:if test="${id == priduct.id}">
						<c:set var="classSucess" value="info" />
					</c:if>
					<tr class="${classProducts}">
						<td>${product.id}</td>
						<td>${product.name}</td>
						<td>${product.price}</td>
						<td>${product.amount}&nbsp;${product.units}</td>
						<td></td>
						</tr>
						</c:forEach>
						</table>
</body>
</html>