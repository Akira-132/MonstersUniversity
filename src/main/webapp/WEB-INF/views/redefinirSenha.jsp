<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../../assets/styles/globaLogin.css">
    <link rel="stylesheet" href="../../assets/styles/redefinirSenha.css">
    <title>Monsters University</title>
</head>
<body>
    <header>
        <img src="../../assets/imgs/Logo.png" alt="LOGO">
    </header>

    <div id="fundo">
     
        <div id="container">
            <img src="../../assets/imgs/crianca_veri.png" alt="Criança" id="" width="250px" id="crianca_veri">
            <div id="login-box">
                <h1>Verificação Aluno</h1>
                <div>
                    <form action="verificaçao.jsp" method="post">
                        <input type="text" id="username" name="username" placeholder="Digite o seu email" required>
                        <input type="text" id="username" name="username" placeholder="Digite o seu email" required>
                        
                        
                        <div id="buttons">
                            <a href="verificaçao.jsp" id="btn-voltar">Voltar</a>
                            <input type="submit" value="Entrar" id="btn-login">
                        </div>
                    </form>
                </div>
            </div>
            <img src="../../assets/imgs/mike_pequeno.png" alt="Criança" id="mike_pequeno" width="250">
        </div>

    </div>
</body>
</html>