<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../../assets/styles/globaLogin.css">
    <link rel="stylesheet" href="../../assets/styles/verificacao.css">
    <title>Monsters University</title>
</head>
<body>
    <header>
        <img src="../../assets/imgs/Logo.png" alt="LOGO">
    </header>

    <div id="fundo">
     
        <div id="container">
           
            <div id="login-box">
                <div id="componentes">
                    <h1>Verificação</h1>
                    <p>Insira o código de 5 dígitos enviado para exemplo@email.com:</p>
                </div>
                <div>
                    <form action="matricula.jsp" method="post">
                        <div id="inputs">
                            <input type="text" maxlength="1" />
                            <input type="text" maxlength="1" />
                            <input type="text" maxlength="1" />
                            <input type="text" maxlength="1" />
                            <input type="text" maxlength="1" />
                        </div>
                        <p>Não recebeu um código? <a href="#">Enviar Novamente</a></p>
                        <div id="buttons">
                            <a href="redefinirSenhaVeri.jsp" id="btn-voltar">Voltar</a>
                            <input type="submit" value="Entrar" id="btn-login">
                        </div>
                    </form>
                </div>
            </div>
        </div>
        <img src="../../assets/imgs/jake_fita.png" alt="Criança" id="jake_fita" width="280">
    </div>
</body>
</html>