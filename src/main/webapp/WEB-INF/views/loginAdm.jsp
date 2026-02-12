<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../../assets/styles/globaLogin.css">
    <link rel="stylesheet" href="../../assets/styles/loginAdm.css">
    <title>Login</title>
</head>
<body>
    <header>
        <img src="../../assets/imgs/Logo.png" alt="LOGO">
    </header>

    <div id="fundo">
        
        <div id="login-box">
            <h1>Admin</h1>
            <div>
                <form action="" method="post">
                    <input type="text" id="username" name="username" placeholder="Usuário" required>
                    <input type="password" id="password" name="password" placeholder="Senha" required>
                    
                    <div id="links_principais">
                        <a href="redefinirSenhaVeri.jsp">Esqueceu a Senha?</a>
                    </div>
                    
                    <input type="submit" value="Entrar" id="btn-login">
                </form>
            </div>
        </div>

        <img src="../../assets/imgs/surpresa.png" alt="Mike" id="img_admin">
    </div>
</body>
</html>