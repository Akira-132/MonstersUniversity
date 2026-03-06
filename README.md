# 🎓 Projeto Monsters University

Sistema de gerenciamento escolar temático baseado no universo de **Monstros S.A.**, desenvolvido como um projeto escolar. A aplicação permite o gerenciamento completo de alunos, professores, turmas, disciplinas, notas e observações, com fluxos distintos para cada tipo de usuário.

---

## 📋 Sumário

- [Tecnologias](#-tecnologias)
- [Funcionalidades](#-funcionalidades)
- [Estrutura do Projeto](#-estrutura-do-projeto)
- [Pré-requisitos](#-pré-requisitos)
- [Configuração do Banco de Dados](#-configuração-do-banco-de-dados)
- [Configuração do Ambiente](#-configuração-do-ambiente)
- [Como Executar](#-como-executar)
- [Perfis de Usuário](#-perfis-de-usuário)
- [Fluxo de Cadastro de Aluno](#-fluxo-de-cadastro-de-aluno)

---

## 🛠 Tecnologias

- **Java 17+**
- **Jakarta Servlet 6.x** (Jakarta EE)
- **JSP** (JavaServer Pages)
- **PostgreSQL**
- **Apache Tomcat 10+**
- **JDBC** (conexão direta, sem ORM)
- **JavaMail** (envio de e-mail para recuperação de senha e ativação)
- **HTML e CSS3**

---

## ✅ Funcionalidades

### Administrador
- Login dedicado
- CRUD completo de alunos, professores, turmas, disciplinas e notas
- Gerenciamento de matrículas de alunos em turmas
- Visualização do histórico de observações de qualquer aluno
- Lançamento e edição de notas

### Professor
- Login pelo portal principal
- Visualização da sua turma e lista de alunos
- Lançamento e edição de notas da sua disciplina
- Registro e edição de observações sobre alunos
- Visualização do histórico de observações por aluno

### Aluno
- Fluxo de cadastro com verificação de CPF e código de ativação por e-mail
- Login pelo portal principal
- Visualização das suas disciplinas
- Visualização do seu boletim com médias e situação
- Edição de perfil (e-mail e telefone)

---

## 📁 Estrutura do Projeto

```
projeto/
├── java/
│   └── com.example/
│       ├── controller/
│       │   └── Conexao.java               # Gerenciamento de conexão com o banco
│       ├── models/                        # Entidades do sistema
│       │   ├── Usuario.java
│       │   ├── Aluno.java
│       │   ├── Professor.java
│       │   ├── Admin.java
│       │   ├── Turma.java
│       │   ├── TurmaAluno.java
│       │   ├── Disciplina.java
│       │   ├── Nota.java
│       │   ├── Boletim.java
│       │   ├── Observacao.java
│       │   └── Telefone.java
│       ├── dao/                           # Acesso ao banco de dados
│       │   ├── UsuarioDAO.java
│       │   ├── AlunoDAO.java
│       │   ├── ProfessorDAO.java
│       │   ├── AdminDAO.java
│       │   ├── TurmaDAO.java
│       │   ├── DisciplinaDAO.java
│       │   ├── NotaDAO.java
│       │   ├── BoletimDAO.java
│       │   ├── ObservacaoDAO.java
│       │   └── TelefoneDAO.java
│       ├── servlet/                       # Servlets organizados por entidade
│       │   ├── ServletAuth/               # Login, logout, matrícula, recuperação de senha
│       │   ├── ServletAdmin/
│       │   ├── ServletAluno/
│       │   ├── ServletProfessor/
│       │   ├── ServletTurma/
│       │   ├── ServletTurmaAluno/
│       │   ├── ServletDisciplina/
│       │   ├── ServletNota/
│       │   ├── ServletBoletim/
│       │   ├── ServletObservacao/
│       │   ├── ServletPerfil/
│       │   └── ServletUsuario/
│       └── filters/
│           ├── AuthFilter.java            # Controle de acesso por sessão
│           └── MapsAdicionar.java         # Redirecionamento da página de adição
└── webapp/
    ├── index.jsp                          # Página de login principal
    ├── assets/
    │   ├── imgs/
    │   └── styles/
    └── WEB-INF/
        └── views/                         # Páginas JSP da aplicação
```

---

## 📦 Pré-requisitos

- JDK 17 ou superior
- Apache Tomcat 10.x
- PostgreSQL 13 ou superior
- IDE com suporte a Jakarta EE (IntelliJ IDEA recomendado)

---

## 🗄 Configuração do Banco de Dados

1. Crie um banco de dados PostgreSQL:
```sql
CREATE DATABASE monsters_university;
```

2. Execute o script SQL de criação das tabelas e views (arquivo `schema.sql` na raiz do repositório).

> ⚠️ O sistema depende das views `Media_Final` e `Media_Por_P1_P2` para o funcionamento do boletim. Certifique-se de que o script as cria corretamente.

---

## ⚙️ Configuração do Ambiente

A conexão com o banco é configurada via variáveis de ambiente ou arquivo `.env` na raiz do projeto:

```env
DB_URL=jdbc:postgresql://localhost:5432/monsters_university
DB_USER=seu_usuario
DB_PASSWORD=sua_senha
```

Se o arquivo `.env` não for encontrado, a aplicação tentará ler as variáveis diretamente do ambiente do sistema operacional.

---

## ▶️ Como Executar

1. Clone o repositório
2. Configure as variáveis de ambiente conforme descrito acima
3. Importe o projeto na sua IDE como projeto Jakarta EE
4. Configure o Tomcat 10+ como servidor de aplicação
5. Execute o script SQL para criar o banco
6. Inicie o servidor e acesse

---

## 👤 Perfis de Usuário

| Perfil | Acesso | Rota de login |
|---|---|---|
| Administrador | Gerenciamento completo do sistema | `/login-admin` |
| Professor | Turma, notas e observações da sua disciplina | `/` |
| Aluno | Disciplinas, boletim e perfil | `/` |

---

## 🎓 Fluxo de Cadastro de Aluno

O cadastro de novos alunos segue um fluxo especial temático:

1. **Teste do Grito** — O candidato precisa provar que é um monstro
2. **Verificação de CPF** — Confirma se o CPF está pré-cadastrado pelo admin
3. **Ativação da Conta** — Um código de 5 dígitos é enviado por e-mail
4. **Definição de Senha** — O aluno define e-mail e senha para acesso

> O pré-cadastro do aluno (nome, sobrenome, CPF) deve ser feito previamente pelo administrador.

---

## 👥 Integrantes

<table>
  <tr>
    <td align="center">
      <a href="https://github.com/FelipeAugustoSan">
        <img src="https://avatars.githubusercontent.com/u/211806301?v=4" width="100px;"/><br>
        <sub>
          <b>Felipe Augusto</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/GabrielAndozia">
        <img src="https://avatars.githubusercontent.com/u/209763165?v=4" width="100px;"/><br>
        <sub>
          <b>Gabriel Andozia</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Akira-132">
        <img src="https://avatars.githubusercontent.com/u/210878589?v=4" width="100px;"/><br>
        <sub>
          <b>Henrique Akira</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/matheus-orestes">
        <img src="https://avatars.githubusercontent.com/u/100971661?v=4" width="100px;"/><br>
        <sub>
          <b>Matheus Orestes</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/rafasepol">
        <img src="https://avatars.githubusercontent.com/u/162934130?v=4" width="100px;"/><br>
        <sub>
          <b>Rafael Lopes</b>
        </sub>
      </a>
    </td>
    <td align="center">
      <a href="https://github.com/Shhiaa">
        <img src="https://avatars.githubusercontent.com/u/199670872?v=4" width="100px;"/><br>
        <sub>
          <b>Sophia Castro</b>
        </sub>
      </a>
    </td>
  </tr>
</table>