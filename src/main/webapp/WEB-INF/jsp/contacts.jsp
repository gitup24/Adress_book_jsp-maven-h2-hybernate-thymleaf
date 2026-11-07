<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Contacts (JSP)</title>
  <style>
    body { font-family: sans-serif; max-width: 900px; margin: 2rem auto; }
    table { border-collapse: collapse; width: 100%; }
    th, td { border: 1px solid #ccc; padding: .5rem; text-align: left; }
    th { background: #f0f0f0; }
  </style>
</head>
<body>
  <h1>Contacts (vue JSP)</h1>
  <p><a href="${pageContext.request.contextPath}/">Accueil</a> ·
     <a href="${pageContext.request.contextPath}/contacts">Gérer (Thymeleaf)</a></p>
  <table>
    <tr><th>Nom</th><th>Email</th><th>Téléphone</th><th>Adresse</th></tr>
    <c:forEach var="c" items="${contacts}">
      <tr>
        <td><c:out value="${c.lastName} ${c.firstName}"/></td>
        <td><c:out value="${c.email}"/></td>
        <td><c:out value="${c.phone}"/></td>
        <td><c:out value="${c.address}"/></td>
      </tr>
    </c:forEach>
    <c:if test="${empty contacts}">
      <tr><td colspan="4">Aucun contact.</td></tr>
    </c:if>
  </table>
</body>
</html>
