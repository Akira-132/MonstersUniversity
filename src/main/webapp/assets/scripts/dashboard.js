require('dotenv').config();
const express = require("express");
const { Pool } = require("pg");

const app = express();
const path = require("path");

app.use(express.static(path.join(__dirname, "../../")));

const pool = new Pool({
    user: process.env.user,
    host: process.env.host,
    database: process.env.database,
    password: process.env.password,
    port: process.env.PORT,
    ssl: {
        rejectUnauthorized: false
    }
});

app.get("/desempenho", async (req, res) => {
    try {

        const { id_aluno, id_disciplina } = req.query;

        let query = `
            select 
                u.nome,
                round(avg(n.nota::numeric), 2) as media
            from nota n
            join aluno a on a.id_aluno = n.id_aluno
            join usuario u on u.id_usuario = a.id_usuario
            join disciplina d on d.id_disciplina = n.id_disciplina
            where 1=1
        `;

        const valores = [];

        if (id_aluno) {
            valores.push(id_aluno);
            query += ` and a.id_aluno = $${valores.length}`;
        }

        if (id_disciplina) {
            valores.push(id_disciplina);
            query += ` and d.id_disciplina = $${valores.length}`;
        }

        query += `
            group by u.nome
            order by media desc
        `;

        const resultado = await pool.query(query, valores);

        res.json(resultado.rows);

    } catch (err) {
        console.error(err);
        res.status(500).json({ erro: "Erro ao buscar dados" });
    }
});
app.get("/alunos", async (req, res) => {
    try {
      const resultado = await pool.query(`
        select a.id_aluno, u.nome
        from aluno a
        join usuario u ON u.id_usuario = a.id_usuario
        order by u.nome
      `);
  
      res.json(resultado.rows);
    } catch (erro) {
      console.error(erro);
      res.status(500).json({ erro: "Erro ao buscar alunos" });
    }
  });
  app.get("/disciplinas", async (req, res) => {
    try {
      const resultado = await pool.query(`
        select id_disciplina, nome
        from disciplina
        order by nome
      `);
  
      res.json(resultado.rows);
    } catch (erro) {
      console.error(erro);
      res.status(500).json({ erro: "Erro ao buscar disciplinas" });
    }
  });
app.listen(3000, () => {
    console.log("Servidor rodando na porta 3000");
});