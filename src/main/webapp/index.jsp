<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head>
  <meta charset="UTF-8">
  <title>Carnet d'adresses</title>
  <style>
    body { font-family: sans-serif; max-width: 600px; margin: 3rem auto; }
    a { display: block; margin: .6rem 0; }
  </style>
</head>
<body>
  <h1>Carnet d'adresses</h1>
  <p>Java · JSP · Thymeleaf · Hibernate · H2 · Maven (sans Spring Boot)</p>
  <a href="${pageContext.request.contextPath}/contacts">Gérer les contacts (Thymeleaf)</a>
  <a href="${pageContext.request.contextPath}/jsp/contacts">Voir la liste (JSP)</a>
</body>
</html>
