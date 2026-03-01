<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globaLogin.css">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/style.css">
    <title>Login</title>
</head>
<body>
<header>
    <a href="${pageContext.request.contextPath}/login?tipo=admin">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="LOGO">
    </a>
</header>

<div id="fundo">
    <img src="${pageContext.request.contextPath}/assets/imgs/jake_login.png" alt="Sulley" id="jake">

    <div id="login-box">
        <h1>Login</h1>
        <div>
            <form action="${pageContext.request.contextPath}/login?tipo=normal" method="post">
                <input type="text" name="username" placeholder="Usuário" required>
                <input type="password" name="password" placeholder="Senha" required>

                <div id="links_principais">
                    <a href="${pageContext.request.contextPath}/WEB-INF/views/redefinirSenhaVeri.jsp">Esqueceu a Senha?</a>
                    <a href="${pageContext.request.contextPath}/ativar-matricula">Não fez a matrícula?</a>
                </div>

                <input type="submit" value="Entrar" id="btn-login">
            </form>
        </div>
    </div>

    <img src="assets/imgs/Mical_login.png" alt="Mike" id="mical">
</div>
</body>
</html>