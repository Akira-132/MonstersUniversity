<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../styles/globaLogin.css">
    <link rel="stylesheet" href="../styles/verifiçaoUsuario.css">
    <title>Monsters University</title>
</head>
<body>
    <header>
        <img src="../imgs/Logo.png" alt="LOGO">
    </header>

    <div id="fundo">
     
        <div id="container">
            <img src="../imgs/crianca.png" alt="Criança" id="criança" width="230px">
            <div id="login-box">
                <h1>Verificação Aluno</h1>
                <div>
                    <form action="matricula.html" method="post">
                        <input type="text" id="username" name="username" placeholder="Digite o seu CPF
                        " required>
                        
                        
                        <div id="buttons">
                            <a href="../../index.htm" id="btn-voltar">Voltar</a>
                            <input type="submit" value="Entrar" id="btn-login">
                        </div>
                    </form>
                </div>
            </div>
        </div>

    </div>
</body>
</html>