<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="com.example.models.Usuario" %>
<%@ page import="com.example.models.Telefone" %>

<%
    Usuario usuarioLogado = (Usuario) session.getAttribute("usuarioLogado");
    Telefone telefone = (Telefone) request.getAttribute("telefoneDoUsuario");

    String nomeCompleto = (usuarioLogado != null) ? usuarioLogado.getNome() + " " + usuarioLogado.getSobrenome() : "Professor";
    String email = (usuarioLogado != null) ? usuarioLogado.getEmail() : "";
    String tel = (telefone != null) ? telefone.getTelefone() : "";
    String sobreMim = (usuarioLogado != null && usuarioLogado.getSobreMim() != null) ? usuarioLogado.getSobreMim() : "";
    String foto = (usuarioLogado != null && usuarioLogado.getFoto() != null) ? usuarioLogado.getFoto() : "";

    String sucesso = (String) request.getAttribute("sucesso");
    String erro = (String) request.getAttribute("erro");
%>
<!DOCTYPE html>
<html lang="pt-BR">
<head>
    <meta charset="UTF-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1.0" />
    <link rel="shortcut icon" href="${pageContext.request.contextPath}/assets/imgs/Logo.png" type="image/x-icon" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/globalApp.css" />
    <link rel="stylesheet" href="${pageContext.request.contextPath}/assets/styles/perfil.css" />
    <title>Perfil - Monsters University</title>
    <style>
        #foto-perfil-wrapper {
            position: relative;
            display: inline-block;
        }

        #btn-trocar-foto {
            position: absolute;
            bottom: 4px;
            right: 4px;
            background-color: #0d47a1;
            border-radius: 50%;
            width: 2rem;
            height: 2rem;
            display: flex;
            align-items: center;
            justify-content: center;
            cursor: pointer;
            box-shadow: 0 2px 6px rgba(0, 0, 0, 0.35);
            transition: background-color 0.2s;
        }

        #btn-trocar-foto:hover {
            background-color: #1565c0;
        }

        #btn-trocar-foto img {
            width: 0.9rem;
            height: 0.9rem;
            filter: brightness(0) invert(1);
        }
    </style>
</head>
<body>

<aside>
    <div id="logo">
        <img src="${pageContext.request.contextPath}/assets/imgs/Logo.png" alt="" />
    </div>
    <nav>
        <a href="${pageContext.request.contextPath}/turma-read">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-diciplinas.png" alt="" />
            Disciplina
        </a>
        <a href="${pageContext.request.contextPath}/dashboard">
            <img src="${pageContext.request.contextPath}/assets/imgs/icone-boletim.png" alt="" />
            Dashboards
        </a>
    </nav>

    <div id="info-usuario">
        <div id="avatar">
            <% if (!foto.isEmpty()) { %>
            <img id="avatar-img"
                 src="<%= request.getContextPath() + "/assets/imgs/perfil/" + foto %>"
                 alt=""
                 style="filter: none; width: 100%; height: 100%; object-fit: cover; border-radius: 50%;" />
            <% } else { %>
            <img id="avatar-img"
                 src="${pageContext.request.contextPath}/assets/imgs/icone-usuario.png"
                 alt="" />
            <% } %>
        </div>
        <span>
            <strong><%= (usuarioLogado != null) ? usuarioLogado.getNome() : "Professor" %></strong>
            Professor
        </span>
    </div>
</aside>

<main>
    <header>Meu Perfil</header>

    <div id="conteudo">
        <div id="perfil-container">

            <% if (erro != null) { %>
            <script>
                window.addEventListener("load", function() {
                    const alertBox = document.createElement("div");
                    alertBox.innerText = "<%= erro %>";
                    alertBox.style.position = "fixed";
                    alertBox.style.top = "20px";
                    alertBox.style.left = "50%";
                    alertBox.style.transform = "translateX(-50%)";
                    alertBox.style.backgroundColor = "#FDE8E8";
                    alertBox.style.color = "#7c1a1a";
                    alertBox.style.padding = "15px 25px";
                    alertBox.style.borderRadius = "8px";
                    alertBox.style.boxShadow = "0 4px 10px rgba(0,0,0,0.15)";
                    alertBox.style.fontFamily = "Inter";
                    alertBox.style.fontSize = "14px";
                    alertBox.style.zIndex = "9999";
                    alertBox.style.opacity = "0";
                    alertBox.style.transition = "opacity 0.4s ease";
                    document.body.appendChild(alertBox);
                    setTimeout(() => { alertBox.style.opacity = "1"; }, 100);
                    setTimeout(() => {
                        alertBox.style.opacity = "0";
                        setTimeout(() => alertBox.remove(), 400);
                    }, 4000);
                });
            </script>
            <% } %>
            <% if (sucesso != null) { %>
            <script>
                window.addEventListener("load", function() {
                    const alertBox = document.createElement("div");
                    alertBox.innerText = "Alterações salvas com sucesso!";
                    alertBox.style.position = "fixed";
                    alertBox.style.top = "20px";
                    alertBox.style.left = "50%";
                    alertBox.style.transform = "translateX(-50%)";
                    alertBox.style.backgroundColor = "#E8F0FE";
                    alertBox.style.color = "#1a3c7c";
                    alertBox.style.padding = "15px 25px";
                    alertBox.style.borderRadius = "8px";
                    alertBox.style.boxShadow = "0 4px 10px rgba(0,0,0,0.15)";
                    alertBox.style.fontFamily = "Inter";
                    alertBox.style.fontSize = "14px";
                    alertBox.style.zIndex = "9999";
                    alertBox.style.opacity = "0";
                    alertBox.style.transition = "opacity 0.4s ease";
                    document.body.appendChild(alertBox);
                    setTimeout(() => { alertBox.style.opacity = "1"; }, 100);
                    setTimeout(() => {
                        alertBox.style.opacity = "0";
                        setTimeout(() => alertBox.remove(), 400);
                    }, 4000);
                });
            </script>
            <% } %>

            <div id="foto-perfil-wrapper">
                <div id="foto-perfil">
                    <img
                            id="preview-foto"
                            src="<%= !foto.isEmpty()
                                ? request.getContextPath() + "/assets/imgs/perfil/" + foto
                                : request.getContextPath() + "/assets/imgs/mike-perfil.png" %>"
                            alt="Foto de perfil"
                    />
                </div>

                <label for="fotoInput" id="btn-trocar-foto" title="Trocar foto">
                    <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar foto" />
                </label>

                <input
                        type="file"
                        id="fotoInput"
                        name="foto"
                        form="perfil-form"
                        accept="image/*"
                        style="display: none;"
                        onchange="previewFoto(this)"
                />
            </div>

            <h1><%= nomeCompleto %></h1>

            <form id="perfil-form" action="${pageContext.request.contextPath}/perfil-update" method="post" enctype="multipart/form-data">
                <div id="campos-grid">
                    <div class="campo-perfil">
                        <label for="email">Email</label>
                        <div class="input-editavel">
                            <input type="email" id="email" name="email" value="<%= email %>" />
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar" class="icone-editar-campo" />
                        </div>
                    </div>

                    <div class="campo-perfil">
                        <label for="telefone">Telefone</label>
                        <div class="input-editavel">
                            <input type="tel" id="telefone" name="telefone" value="<%= tel %>" maxlength="15" />
                            <img src="${pageContext.request.contextPath}/assets/imgs/icone-editar.png" alt="Editar" class="icone-editar-campo" />
                        </div>
                    </div>
                </div>

                <div class="campo-perfil campo-descricao">
                    <label for="sobre">Sobre mim</label>
                    <textarea id="sobre" name="sobreMim" rows="6"><%= sobreMim %></textarea>
                </div>

                <button type="submit" id="btn-salvar" style="background-color: #0d47a1; color: white; padding: 12px; border-radius: 8px; width: 100%; font-weight: bold; border: none; cursor: pointer; margin-bottom: 15px;">SALVAR ALTERAÇÕES</button>
            </form>

            <form action="${pageContext.request.contextPath}/logout" method="get" style="width: 100%;">
                <button type="submit" id="btn-sair">SAIR DA CONTA</button>
            </form>
        </div>
    </div>
</main>

<script>
    function previewFoto(input) {
        if (input.files && input.files[0]) {
            const reader = new FileReader();
            reader.onload = function (e) {
                document.getElementById('preview-foto').src = e.target.result;
                const avatarImg = document.getElementById('avatar-img');
                avatarImg.src = e.target.result;
                avatarImg.style.filter = 'none';
                avatarImg.style.width = '100%';
                avatarImg.style.height = '100%';
                avatarImg.style.objectFit = 'cover';
                avatarImg.style.borderRadius = '50%';
            };
            reader.readAsDataURL(input.files[0]);
        }
    }
</script>

<script src="${pageContext.request.contextPath}/assets/scripts/loading.js"></script>
</body>
</html>
