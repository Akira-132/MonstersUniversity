<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="shortcut icon" href="../../assets/imgs/Logo.png" type="image/x-icon">
    <link rel="stylesheet" href="../../assets/styles/globaLogin.css">
    <link rel="stylesheet" href="../../assets/styles/matricula.css">
    <title>Monsters University</title>
</head>
<body>
    <header>
        <img src="../../assets/imgs/Logo.png" alt="LOGO">
    </header>

    <div id="fundo">

        
        <div id="login-box">
            <h1>Matrícula</h1>
            <div>
                <form action="" method="post">
                    <div class="form-grid">
                        <input type="text" id="nome" name="nome" placeholder="Nome:" required>
                        <input type="email" id="email" name="email" placeholder="Email:" required>
                        <input type="text" id="matricula" name="matricula" placeholder="Matrícula:" required>
                        <input type="password" id="senha" name="senha" placeholder="Senha" required>
                        <input type="text" id="cpf" name="cpf" placeholder="CPF:" required>
                        <input type="submit" value="Enviar" id="btn-enviar">
                    </div>
                </form>
            </div>
        </div>

        <img src="../imgs/montros_escuro.png" alt="" id="montros_escuro" width="360">
    </div>
    
</body>
</html>