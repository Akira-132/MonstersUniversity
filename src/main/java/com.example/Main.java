package com.example;

import com.example.dao.*;
import com.example.models.*;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.LinkedList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
//        TESTE AdminDAO
////
////        AdminDAO adminDAO = new AdminDAO();
////
////        try {
////
////            System.out.println("========== TESTE CREATE ==========");
////
////            int usuarioIdExistente = 6;
////
////            Admin novoAdmin = new Admin(200, usuarioIdExistente);
////
////            boolean criado = adminDAO.create(novoAdmin);
////
////            if (criado) {
////                System.out.println("Admin criado com sucesso!");
////            } else {
////                System.out.println("Falha ao criar admin.");
////            }
////
////
////            System.out.println("\n========== TESTE READ (LISTAR TODOS) ==========");
////
////            List<Admin> lista = adminDAO.read();
////
////
////            for (Admin a : lista) {
////                System.out.println("ID Admin: " + a.getId());
////                System.out.println("ID Usuario: " + a.getFkUsuarioId());
////                System.out.println("Nome: " + a.getUsuario().getNome());
////                System.out.println("Email: " + a.getUsuario().getEmail());
////                System.out.println("--------------------------");
////            }
////
////
////            System.out.println("\n========== TESTE READ BY ID ==========");
////
////            if (!lista.isEmpty()) {
////                int idTeste = lista.get(lista.size() - 1).getId();
////
////                Admin adminById = adminDAO.readById(idTeste);
////
////                if (adminById != null) {
////                    System.out.println("Admin encontrado:");
////                    System.out.println("ID: " + adminById.getId());
////                    System.out.println("Nome: " + adminById.getUsuario().getNome());
////                } else {
////                    System.out.println("Admin não encontrado.");
////                }
////
////
////                System.out.println("\n========== TESTE READ BY USUARIO ID ==========");
////
////                Admin adminByUsuario = adminDAO.readByUsuarioId(usuarioIdExistente);
////
////                if (adminByUsuario != null) {
////                    System.out.println("Admin encontrado pelo id_usuario:");
////                    System.out.println("ID Admin: " + adminByUsuario.getId());
////                } else {
////                    System.out.println("Nenhum admin com esse usuario.");
////                }
////
////
////                System.out.println("\n========== TESTE UPDATE ==========");
////
////                // Atualizando para o mesmo usuário apenas como teste
////                adminById.setFkUsuarioId(usuarioIdExistente);
////
////                int linhasAfetadas = adminDAO.update(adminById);
////
////                System.out.println("Linhas afetadas: " + linhasAfetadas);
////
////
////                System.out.println("\n========== TESTE DELETE ==========");
////
////                int deletado = adminDAO.deleteById(idTeste);
////
////                System.out.println("Linhas deletadas: " + deletado);
////            }
//
//        } catch (SQLException e) {
//            System.out.println("Erro no banco:");
//            e.printStackTrace();
//        }
//
//        System.out.println("\n========== FIM DOS TESTES ==========");










////        TESTE AlunoDAO
//
//
//        AlunoDAO alunoDAO = new AlunoDAO();
//
//        try {
//
//            System.out.println("========== TESTE CREATE VÁLIDO ==========");
//
//            int usuarioValido = 5;
//
//            Aluno alunoValido = new Aluno("12345678901", usuarioValido);
//
////            boolean criado = alunoDAO.create(alunoValido);
////
////            System.out.println("Aluno criado: " + criado);
////
////
////            System.out.println("\n========== TESTE CREATE FK INVÁLIDA ==========");
////
////            try {
////                Aluno alunoFkInvalida = new Aluno("98765432100", 999999);
////                alunoDAO.create(alunoFkInvalida);
////            } catch (SQLException e) {
////                System.out.println("Erro esperado (FK inválida): " + e.getMessage());
////            }
////
////
////            System.out.println("\n========== TESTE CPF INVÁLIDO ==========");
////
////            try {
////                Aluno cpfInvalido = new Aluno("123", usuarioValido);
////            } catch (IllegalArgumentException e) {
////                System.out.println("Erro esperado (CPF inválido): " + e.getMessage());
////            }
//
//
//            System.out.println("\n========== TESTE READ (LISTAR TODOS) ==========");
//
//            List<Aluno> lista = alunoDAO.read();
//
//            for (Aluno a : lista) {
//                System.out.println("ID: " + a.getId());
//                System.out.println("CPF: " + a.getCpf());
//                System.out.println("Matrícula: " + a.getMatricula());
//                System.out.println("Usuário: " + a.getUsuario().getNome());
//                System.out.println("-------------------------");
//            }
//
//
//            if (!lista.isEmpty()) {
//
//                Aluno ultimo = lista.get(lista.size() - 1);
//
////                System.out.println("\n========== TESTE READ BY ID ==========");
////
////                Aluno porId = alunoDAO.readById(ultimo.getId());
////
////                if (porId != null) {
////                    System.out.println("Encontrado por ID: " + porId.getMatricula());
////                }
////
////
////                System.out.println("\n========== TESTE READ BY MATRICULA ==========");
////
////                Aluno porMatricula = alunoDAO.readByMatricula(ultimo.getMatricula());
////
////                if (porMatricula != null) {
////                    System.out.println("Encontrado por matrícula: " + porMatricula.getCpf());
////                }
//
//
//                System.out.println("\n========== TESTE UPDATE ==========");
//
//                ultimo.setCpf("15922233344");
//
//                int linhasUpdate = alunoDAO.update(ultimo);
//
//                System.out.println("Linhas afetadas no update: " + linhasUpdate);
//
//
//                System.out.println("\n========== TESTE UPDATE COM ID INVÁLIDO ==========");
//
//                try {
//                    ultimo.setId(-1);
//                } catch (IllegalArgumentException e) {
//                    System.out.println("Erro esperado (ID inválido): " + e.getMessage());
//                }
//
//
//                System.out.println("\n========== TESTE DELETE BY ID ==========");
//
//                int deletado = alunoDAO.deleteById(ultimo.getId());
//
//                System.out.println("Linhas deletadas: " + deletado);
//
//
//                System.out.println("\n========== TESTE DELETE BY MATRICULA (NÃO EXISTENTE) ==========");
//
//                int deletadoMatricula = alunoDAO.deleteByMatricula("MAT_INEXISTENTE");
//
//                System.out.println("Delete matrícula inexistente (esperado 0): " + deletadoMatricula);
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Erro no banco:");
//            e.printStackTrace();
//        }
//
//        System.out.println("\n========== FIM DOS TESTES ==========");

















//        TESTE DisciplinaDAO
//        DisciplinaDAO disciplinaDAO = new DisciplinaDAO();
//
//        try {
//
//            System.out.println("========== TESTE CREATE VÁLIDO ==========");
//
//            int professorValido = 1;
//
//            Disciplina disciplinaValida = new Disciplina("Matemática Avançada", professorValido);
//
//            boolean criada = disciplinaDAO.create(disciplinaValida);
//
//            System.out.println("Disciplina criada: " + criada);
//
//
//            System.out.println("\n========== TESTE CREATE FK INVÁLIDA ==========");
//
//            try {
//                Disciplina fkInvalida =
//                        new Disciplina("Física Quântica", 999999);
//                disciplinaDAO.create(fkInvalida);
//            } catch (SQLException e) {
//                System.out.println("Erro esperado (FK inválida): " + e.getMessage());
//            }
//
//
//            System.out.println("\n========== TESTE CREATE NOME INVÁLIDO ==========");
//
//            try {
//                Disciplina nomeInvalido =
//                        new Disciplina("", professorValido);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Erro esperado (Nome inválido): " + e.getMessage());
//            }
//
//
//            System.out.println("\n========== TESTE READ (LISTAR TODOS) ==========");
//
//            try {
//                List<Disciplina> lista = disciplinaDAO.read();
//
//                for (Disciplina d : lista) {
//                    System.out.println("ID: " + d.getId());
//                    System.out.println("Nome: " + d.getNome());
//                    System.out.println("Professor ID: " + d.getProfessor().getId());
//                    System.out.println("Professor Nome: " +
//                            d.getProfessor().getUsuario().getNome());
//                    System.out.println("-------------------------");
//                }
//
//                if (!lista.isEmpty()) {
//
//                    Disciplina ultima = lista.get(lista.size() - 1);
//
//                    System.out.println("\n========== TESTE READ BY ID ==========");
//
//                    Disciplina porId = disciplinaDAO.readById(ultima.getId());
//
//                    if (porId != null) {
//                        System.out.println("Encontrado por ID: " + porId.getNome());
//                    }
//
//
//                    System.out.println("\n========== TESTE READ BY ID INEXISTENTE ==========");
//
//                    Disciplina inexistente = disciplinaDAO.readById(999999);
//                    System.out.println("Resultado esperado (null): " + inexistente);
//
//
//                    System.out.println("\n========== TESTE READ BY NOME ==========");
//
//                    Disciplina porNome =
//                            disciplinaDAO.readByNome(ultima.getNome());
//
//                    if (porNome != null) {
//                        System.out.println("Encontrado por nome: " + porNome.getId());
//                    }
//
//
//                    System.out.println("\n========== TESTE READ BY NOME INEXISTENTE ==========");
//
//                    Disciplina nomeNaoExiste =
//                            disciplinaDAO.readByNome("DISCIPLINA_XYZ");
//
//                    System.out.println("Resultado esperado (null): " + nomeNaoExiste);
//
//
//                    System.out.println("\n========== TESTE UPDATE ==========");
//
//                    ultima.setNome("Matemática Atualizada");
//
//                    int linhasUpdate = disciplinaDAO.update(ultima);
//
//                    System.out.println("Linhas afetadas: " + linhasUpdate);
//
//
//                    System.out.println("\n========== TESTE UPDATE ID INEXISTENTE ==========");
//
//                    ultima.setId(999999);
//                    int updateInvalido = disciplinaDAO.update(ultima);
//
//                    System.out.println("Update ID inexistente (esperado 0): " + updateInvalido);
//
//
//                    System.out.println("\n========== TESTE UPDATE FK INVÁLIDA ==========");
//
//                    try {
//                        ultima.setFkProfessorId(999999);
//                        disciplinaDAO.update(ultima);
//                    } catch (SQLException e) {
//                        System.out.println("Erro esperado (FK inválida): " + e.getMessage());
//                    }
//
//
//                    System.out.println("\n========== TESTE DELETE BY ID ==========");
//
//                    int deletado = disciplinaDAO.deleteById(1);
//                    System.out.println("Linhas deletadas: " + deletado);
//
//
//                    System.out.println("\n========== TESTE DELETE ID INEXISTENTE ==========");
//
//                    int deleteInexistente =
//                            disciplinaDAO.deleteById(999999);
//
//                    System.out.println("Delete inexistente (esperado 0): " + deleteInexistente);
//
//
//                    System.out.println("\n========== TESTE DELETE BY NOME ==========");
//
//                    int deleteNome =
//                            disciplinaDAO.deleteByNome("Matemática Atualizada");
//
//                    System.out.println("Delete por nome: " + deleteNome);
//
//
//                    System.out.println("\n========== TESTE DELETE NOME INEXISTENTE ==========");
//
//                    int deleteNomeInexistente =
//                            disciplinaDAO.deleteByNome("NOME_QUE_NAO_EXISTE");
//
//                    System.out.println("Delete nome inexistente (esperado 0): " + deleteNomeInexistente);
//                }
//
//            } catch (SQLException e) {
//                System.out.println("Erro durante READ:");
//                e.printStackTrace();
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Erro geral no banco:");
//            e.printStackTrace();
//        }
//
//        System.out.println("\n========== FIM DOS TESTES ==========");














//        TESTE NotaDAO
//        NotaDAO notaDAO = new NotaDAO();
//
//        try {
//
//            System.out.println("========== TESTE CREATE VÁLIDO ==========");
//
//            int alunoValido = 1;
//            int disciplinaValida = 3;
//
//            Nota notaValida = new Nota(
//                    "N1",
//                    1,
//                    2025,
//                    8.5,
//                    alunoValido,
//                    disciplinaValida
//            );
//
//            boolean criada = notaDAO.create(notaValida);
//            System.out.println("Nota criada: " + criada);
//
//
//            System.out.println("\n========== TESTE CREATE FK ALUNO INVÁLIDA ==========");
//
//            try {
//                Nota fkAlunoInvalida = new Nota(
//                        "N2", 1, 2025, 7.0,
//                        999999, disciplinaValida
//                );
//                notaDAO.create(fkAlunoInvalida);
//            } catch (SQLException e) {
//                System.out.println("Erro esperado FK aluno: " + e.getMessage());
//            }
//
//
//            System.out.println("\n========== TESTE CREATE FK DISCIPLINA INVÁLIDA ==========");
//
//            try {
//                Nota fkDisciplinaInvalida = new Nota(
//                        "N1", 1, 2025, 6.0,
//                        alunoValido, 999999
//                );
//                notaDAO.create(fkDisciplinaInvalida);
//            } catch (SQLException e) {
//                System.out.println("Erro esperado FK disciplina: " + e.getMessage());
//            }
//
//
//            System.out.println("\n========== TESTE READ ==========");
//
//            List<Nota> lista = notaDAO.read();
//
//            for (Nota n : lista) {
//                System.out.println("ID: " + n.getId());
//                System.out.println("Tipo: " + n.getTipo());
//                System.out.println("Aluno: " + n.getAluno().getUsuario().getNome());
//                System.out.println("Disciplina: " + n.getDisciplina().getNome());
//                System.out.println("Professor: " +
//                        n.getDisciplina().getProfessor().getUsuario().getNome());
//                System.out.println("---------------------------");
//            }
//
//
//            if (!lista.isEmpty()) {
//
//                Nota ultima = lista.get(lista.size() - 1);
//
//                System.out.println("\n========== TESTE READ BY ID ==========");
//
//                Nota porId = notaDAO.readById(ultima.getId());
//                System.out.println("Encontrado: " + porId.getTipo());
//
//
//                System.out.println("\n========== TESTE READ BY ID INEXISTENTE ==========");
//
//                Nota inexistente = notaDAO.readById(999999);
//                System.out.println("Resultado esperado (null): " + inexistente);
//
//
//                System.out.println("\n========== TESTE READ BY ALUNO ==========");
//
//                List<Nota> notasAluno =
//                        notaDAO.readByAlunoId(alunoValido);
//
//                System.out.println("Qtd notas aluno: " + notasAluno.size());
//
//
//                System.out.println("\n========== TESTE READ BY ALUNO INEXISTENTE ==========");
//
//                List<Nota> notasAlunoInexistente =
//                        notaDAO.readByAlunoId(999999);
//
//                System.out.println("Qtd esperada 0: " + notasAlunoInexistente.size());
//
//
//                System.out.println("\n========== TESTE READ BY DISCIPLINA ==========");
//
//                List<Nota> notasDisciplina =
//                        notaDAO.readByDisciplinaId(disciplinaValida);
//
//                System.out.println("Qtd notas disciplina: " + notasDisciplina.size());
//
//
//                System.out.println("\n========== TESTE UPDATE ==========");
//
//                ultima.setNota(9.5);
//
//                int linhas = notaDAO.update(ultima);
//                System.out.println("Linhas afetadas: " + linhas);
//
//
//                System.out.println("\n========== TESTE UPDATE ID INEXISTENTE ==========");
//
//                ultima.setId(999999);
//                int updateInvalido = notaDAO.update(ultima);
//                System.out.println("Esperado 0: " + updateInvalido);
//
//
//                System.out.println("\n========== TESTE DELETE BY ID ==========");
//
//                int deletado = notaDAO.deleteById(3);
//                System.out.println("Linhas deletadas: " + deletado);
//
//
//                System.out.println("\n========== TESTE DELETE ID INEXISTENTE ==========");
//
//                int deleteInexistente =
//                        notaDAO.deleteById(999999);
//
//                System.out.println("Esperado 0: " + deleteInexistente);
//
//
//                System.out.println("\n========== TESTE DELETE BY ALUNO INEXISTENTE ==========");
//
//                int deleteAluno =
//                        notaDAO.deleteByAlunoId(135);
//
//                System.out.println("Esperado 0: " + deleteAluno);
//
//
//                System.out.println("\n========== TESTE DELETE BY DISCIPLINA INEXISTENTE ==========");
//
//                int deleteDisciplina =
//                        notaDAO.deleteByDisciplinaId(999999);
//
//                System.out.println("Esperado 0: " + deleteDisciplina);
//            }
//
//        } catch (SQLException e) {
//            System.out.println("Erro geral:");
//            e.printStackTrace();
//        }
//
//        System.out.println("\n========== FIM DOS TESTES ==========");











//        TESTE ObservacaoDAO

//        ObservacaoDAO dao = new ObservacaoDAO();
//
//        int ID_PROFESSOR_EXISTENTE = 1;
//        int ID_ALUNO_EXISTENTE = 1;
//
//        try {
//
//            System.out.println("============== TESTE CREATE ==============");
//
//            Observacao nova = new Observacao(
//                    26,
//                    "Observação de teste",
//                    LocalDateTime.now(),
//                    ID_PROFESSOR_EXISTENTE,
//                    ID_ALUNO_EXISTENTE
//            );
//
//            boolean criada = dao.create(nova);
//            System.out.println("Criada com sucesso? " + criada);
//
//
//            System.out.println("\n============== TESTE READ (TODOS) ==============");
//
//            List<Observacao> lista = dao.read();
//
//            if (lista.isEmpty()) {
//                System.out.println("Lista vazia.");
//            } else {
//                for (Observacao o : lista) {
//                    System.out.println("ID: " + o.getId());
//                    System.out.println("comentario: " + o.getComentario());
//                    System.out.println("Professor ID: " + o.getFkProfessorId());
//                    System.out.println("Aluno ID: " + o.getFkAlunoId());
//                    System.out.println("---------------------------");
//                }
//            }
//
//
//            System.out.println("\n============== TESTE READ BY ID (EXISTENTE) ==============");
//
//            if (!lista.isEmpty()) {
//                int idExistente = lista.get(0).getId();
//
//                Observacao encontrada = dao.readById(idExistente);
//
//                if (encontrada != null) {
//                    System.out.println("Encontrada: " + encontrada.getComentario());
//                } else {
//                    System.out.println("Não encontrada.");
//                }
//            }
//
//
//            System.out.println("\n============== TESTE READ BY ID (INEXISTENTE) ==============");
//
//            Observacao naoExiste = dao.readById(999999);
//            System.out.println("Resultado para ID inexistente: " + naoExiste);
//
//
//            System.out.println("\n============== TESTE READ BY ALUNO ID ==============");
//
//            List<Observacao> porAluno = dao.readByAlunoId(ID_ALUNO_EXISTENTE);
//            System.out.println("Quantidade encontrada: " + porAluno.size());
//
//
//            System.out.println("\n============== TESTE READ BY PROFESSOR ID ==============");
//
//            List<Observacao> porProfessor = dao.readByProfessorId(ID_PROFESSOR_EXISTENTE);
//            System.out.println("Quantidade encontrada: " + porProfessor.size());
//
//
//            System.out.println("\n============== TESTE UPDATE ==============");
//
//            if (!lista.isEmpty()) {
//
//                Observacao atualizar = lista.get(0);
//                atualizar.setComentario("comentario atualizado");
//                atualizar.setDataEnvio(LocalDateTime.now());
//
//                int linhas = dao.update(atualizar);
//                System.out.println("Linhas afetadas: " + linhas);
//
//                Observacao verificada = dao.readById(atualizar.getId());
//                System.out.println("comentario após update: " + verificada.getComentario());
//            }
//
//
//            System.out.println("\n============== TESTE DELETE BY ID ==============");
//
//            if (!lista.isEmpty()) {
//
//                int idParaExcluir = lista.get(lista.size() - 1).getId();
//                int linhas = dao.deleteById(idParaExcluir);
//
//                System.out.println("Linhas deletadas: " + linhas);
//
//                Observacao verificada = dao.readById(idParaExcluir);
//                System.out.println("Após delete (deve ser null): " + verificada);
//            }
//
//
//            System.out.println("\n============== TESTE DELETE BY ALUNO ID (INEXISTENTE) ==============");
//
//            int deletadosAluno = dao.deleteByAlunoId(999999);
//            System.out.println("Registros deletados: " + deletadosAluno);
//
//
//            System.out.println("\n============== TESTE DELETE BY PROFESSOR ID (INEXISTENTE) ==============");
//
//            int deletadosProfessor = dao.deleteByProfessorId(999999);
//            System.out.println("Registros deletados: " + deletadosProfessor);
//
//
//        } catch (Exception e) {
//            System.out.println("Erro durante os testes:");
//            e.printStackTrace();
//        }
//
//        System.out.println("\n============== TODOS OS TESTES FINALIZADOS ==============");












//    TESTE ProfessorDAO
//        ProfessorDAO dao = new ProfessorDAO();
//
//        int ID_USUARIO_EXISTENTE = 1;
//
//        try {
//
//            System.out.println("============== TESTE CREATE ==============");
//
//            Professor novo = new Professor(22, ID_USUARIO_EXISTENTE);
//
//            boolean criado = dao.create(novo);
//            System.out.println("Criado com sucesso? " + criado);
//
//
//            System.out.println("\n============== TESTE READ (TODOS) ==============");
//
//            List<Professor> lista = dao.read();
//
//            if (lista.isEmpty()) {
//                System.out.println("Lista vazia.");
//            } else {
//                for (Professor p : lista) {
//                    System.out.println("ID Professor: " + p.getId());
//                    System.out.println("ID Usuario FK: " + p.getFkUsuarioId());
//                    System.out.println("Nome: " + p.getUsuario().getNome());
//                    System.out.println("Email: " + p.getUsuario().getEmail());
//                    System.out.println("---------------------------");
//                }
//            }
//
//
//            System.out.println("\n============== TESTE READ BY ID (EXISTENTE) ==============");
//
//            if (!lista.isEmpty()) {
//
//                int idExistente = lista.get(0).getId();
//
//                Professor encontrado = dao.readById(idExistente);
//
//                if (encontrado != null) {
//                    System.out.println("Encontrado: " + encontrado.getUsuario().getNome());
//                } else {
//                    System.out.println("Não encontrado.");
//                }
//            }
//
//
//            System.out.println("\n============== TESTE READ BY ID (INEXISTENTE) ==============");
//
//            Professor naoExiste = dao.readById(999999);
//            System.out.println("Resultado para ID inexistente: " + naoExiste);
//
//
//            System.out.println("\n============== TESTE READ BY USUARIO ID (EXISTENTE) ==============");
//
//            Professor porUsuario = dao.readByUsuarioId(ID_USUARIO_EXISTENTE);
//
//            if (porUsuario != null) {
//                System.out.println("Professor encontrado pelo usuarioId: " + porUsuario.getId());
//            } else {
//                System.out.println("Nenhum professor encontrado para esse usuarioId.");
//            }
//
//
//            System.out.println("\n============== TESTE READ BY USUARIO ID (INEXISTENTE) ==============");
//
//            Professor porUsuarioInexistente = dao.readByUsuarioId(999999);
//            System.out.println("Resultado: " + porUsuarioInexistente);
//
//
//            System.out.println("\n============== TESTE UPDATE ==============");
//
//            if (!lista.isEmpty()) {
//
//                Professor atualizar = lista.get(0);
//
//                atualizar.setFkUsuarioId(7);
//
//                int linhas = dao.update(atualizar);
//                System.out.println("Linhas afetadas: " + linhas);
//
//                Professor verificado = dao.readById(atualizar.getId());
//                System.out.println("Usuario FK após update: " + verificado.getFkUsuarioId());
//            }
//
//
//            System.out.println("\n============== TESTE DELETE BY ID ==============");
//
//            if (!lista.isEmpty()) {
//
//                int idParaExcluir = lista.get(lista.size() - 1).getId();
//
//                int linhas = dao.deleteById(idParaExcluir);
//                System.out.println("Linhas deletadas: " + linhas);
//
//                Professor verificado = dao.readById(idParaExcluir);
//                System.out.println("Após delete (deve ser null): " + verificado);
//            }
//
//
//            System.out.println("\n============== TESTE DELETE BY USUARIO ID (INEXISTENTE) ==============");
//
//            int deletadosUsuario = dao.deleteByUsuarioId(999999);
//            System.out.println("Registros deletados: " + deletadosUsuario);
//
//        } catch (Exception e) {
//            System.out.println("Erro durante os testes:");
//            e.printStackTrace();
//        }
//        System.out.println("\n============== TODOS OS TESTES FINALIZADOS ==============");







//        TESTE TelefoneDAO
//
//        TelefoneDAO dao = new TelefoneDAO();
//
//        int ID_USUARIO_EXISTENTE = 1; // ALTERE SE NECESSÁRIO
//        String NUMERO_TESTE = "11999999999";
//
//        try {
//
//            System.out.println("============== TESTE CREATE ==============");
//
//            Telefone novo = new Telefone(
//                    20,
//                    NUMERO_TESTE,
//                    ID_USUARIO_EXISTENTE
//            );
//
//            boolean criado = dao.create(novo);
//            System.out.println("Criado com sucesso? " + criado);
//
//
//            System.out.println("\n============== TESTE READ (TODOS) ==============");
//
//            List<Telefone> lista = dao.read();
//
//            if (lista.isEmpty()) {
//                System.out.println("Lista vazia.");
//            } else {
//                for (Telefone t : lista) {
//                    System.out.println("ID Telefone: " + t.getId());
//                    System.out.println("Numero: " + t.getTelefone());
//                    System.out.println("Usuario ID: " + t.getFkUsuarioId());
//                    System.out.println("Nome Usuario: " + t.getUsuario().getNome());
//                    System.out.println("---------------------------");
//                }
//            }
//
//
//            System.out.println("\n============== TESTE READ BY ID (EXISTENTE) ==============");
//
//            if (!lista.isEmpty()) {
//
//                int idExistente = lista.get(0).getId();
//
//                Telefone encontrado = dao.readById(idExistente);
//
//                if (encontrado != null) {
//                    System.out.println("Encontrado: " + encontrado.getTelefone());
//                } else {
//                    System.out.println("Não encontrado.");
//                }
//            }
//
//
//            System.out.println("\n============== TESTE READ BY ID (INEXISTENTE) ==============");
//
//            Telefone naoExiste = dao.readById(999999);
//            System.out.println("Resultado para ID inexistente: " + naoExiste);
//
//
//            System.out.println("\n============== TESTE READ BY TELEFONE (EXISTENTE) ==============");
//
//            Telefone porNumero = dao.readByTelefone(NUMERO_TESTE);
//
//            if (porNumero != null) {
//                System.out.println("Encontrado pelo numero: " + porNumero.getId());
//            } else {
//                System.out.println("Nenhum telefone encontrado.");
//            }
//
//
//            System.out.println("\n============== TESTE READ BY TELEFONE (INEXISTENTE) ==============");
//
//            Telefone porNumeroInexistente = dao.readByTelefone("00000000000");
//            System.out.println("Resultado: " + porNumeroInexistente);
//
//
//            System.out.println("\n============== TESTE UPDATE ==============");
//
//            if (!lista.isEmpty()) {
//
//                Telefone atualizar = lista.get(0);
//
//                atualizar.setTelefone("11888888888");
//
//                int linhas = dao.update(atualizar);
//                System.out.println("Linhas afetadas: " + linhas);
//
//                Telefone verificado = dao.readById(atualizar.getId());
//                System.out.println("Numero após update: " + verificado.getTelefone());
//            }
//
//
//            System.out.println("\n============== TESTE DELETE BY TELEFONE ==============");
//
//            int deletadosNumero = dao.deleteByTelefone("11888888888");
//            System.out.println("Registros deletados: " + deletadosNumero);
//
//
//            System.out.println("\n============== TESTE DELETE BY ID (INEXISTENTE) ==============");
//
//            int deletadosId = dao.deleteById(999999);
//            System.out.println("Registros deletados: " + deletadosId);
//
//
//        } catch (Exception e) {
//            System.out.println("Erro durante os testes:");
//            e.printStackTrace();
//        }
//
//        System.out.println("\n============== TODOS OS TESTES FINALIZADOS ==============");











//        TESTE TurmaDAO

//        TurmaDAO dao = new TurmaDAO();
//
//        try {
//
//            System.out.println("========== TESTE CREATE ==========");

            // Criando lista de alunos (ids precisam existir no banco)
//            List<Aluno> alunos = new LinkedList<>();
//            alunos.add(new Aluno(1, "12345678901", "MAT001", 1));
//            alunos.add(new Aluno(2, "98765432100", "MAT002", 2));
//
//            Turma turma = new Turma("Manhã", "A1", 3);
//            turma.setAlunos(alunos);
//
//            boolean criada = dao.create(turma);
//            System.out.println("Turma criada: " + criada);


//            System.out.println("\n========== TESTE READ ==========");
//
//            List<Turma> turmas = dao.read();
//            for (Turma t : turmas) {
//                System.out.println("ID: " + t.getId() +
//                        " | Sala: " + t.getSala() +
//                        " | Período: " + t.getPeriodo() +
//                        " | Disciplina: " + t.getDisciplina().getNome() +
//                        " | Qtd Alunos: " + (t.getAlunos() != null ? t.getAlunos().size() : 0));
//            }
//
//
//            System.out.println("\n========== TESTE READ BY ID ==========");
//
//            Turma porId = dao.readById(1);
//            if (porId != null) {
//                System.out.println("Encontrada turma ID 1: " + porId.getSala());
//            } else {
//                System.out.println("Turma ID 1 não encontrada.");
//            }
//
//
//            System.out.println("\n========== TESTE READ BY SALA ==========");
//
//            Turma porSala = dao.readBySala("A1");
//            if (porSala != null) {
//                System.out.println("Encontrada turma sala A1: " + porSala.getPeriodo());
//            } else {
//                System.out.println("Turma sala A1 não encontrada.");
//            }
//
//
//            System.out.println("\n========== TESTE UPDATE ==========");
//
//            Turma turmaUpdate = dao.readBySala("A1");
//
//            if (turmaUpdate != null) {
//
//                turmaUpdate.setPeriodo("Tarde");
//                turmaUpdate.setSala("B2");
//
//                List<Aluno> novosAlunos = new LinkedList<>();
//                novosAlunos.add(new Aluno(1, "12345678901", "MAT001", 1));
//                turmaUpdate.setAlunos(novosAlunos);
//
//                int linhas = dao.update(turmaUpdate);
//                System.out.println("Linhas afetadas no update: " + linhas);
//            }
//
//
//            System.out.println("\n========== TESTE DELETE BY SALA ==========");
//
//            int deletadasSala = dao.deleteBySala("B2");
//            System.out.println("Linhas deletadas por sala: " + deletadasSala);
//
//
//            System.out.println("\n========== TESTE CREATE SEM ALUNOS ==========");
//
//            Turma turmaSemAlunos = new Turma("Noite", "C3", 1);
//            boolean criadaSemAlunos = dao.create(turmaSemAlunos);
//            System.out.println("Turma criada sem alunos: " + criadaSemAlunos);
//
//
//            System.out.println("\n========== TESTE DELETE BY ID ==========");
//
//            Turma turmaParaExcluir = dao.readBySala("C3");
//            if (turmaParaExcluir != null) {
//                int deletadasId = dao.deleteById(turmaParaExcluir.getId());
//                System.out.println("Linhas deletadas por ID: " + deletadasId);
//            }
//
//
//            System.out.println("\n========== TESTE READ INEXISTENTE ==========");
//
//            Turma inexistente = dao.readById(9999);
//            System.out.println("Busca ID inexistente retornou: " + inexistente);
//
//
//            System.out.println("\n========== TESTE ERROS DA MODEL ==========");
//
//            try {
//                Turma erro1 = new Turma("Madrugada", "D1", 1);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Erro período inválido capturado ✔");
//            }
//
//            try {
//                Turma erro2 = new Turma("Manhã", "ABC", 1);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Erro sala > 2 caracteres capturado ✔");
//            }
//
//            try {
//                Turma erro3 = new Turma("Manhã", "D1", -1);
//            } catch (IllegalArgumentException e) {
//                System.out.println("Erro disciplina inválida capturado ✔");
//            }
//
//
//            System.out.println("\n========== TESTE FINAL READ ==========");
//
//            List<Turma> finalTurmas = dao.read();
//            System.out.println("Total de turmas no banco: " + finalTurmas.size());
//
//
//        } catch (SQLException e) {
//            System.err.println("Erro SQL detectado:");
//            e.printStackTrace();
//        }












//        TESTE UsuarioDAO

//        UsuarioDAO dao = new UsuarioDAO();
//
//        try {

//            System.out.println("===== TESTE CREATE =====");
//
//            Usuario usuario = new Usuario(
//                    "Matheus",
//                    "Orestes",
//                    "matheus@email.com",
//                    "Senha123!"
//            );
//
//            boolean criado = dao.create(usuario);
//            System.out.println("Usuário criado: " + criado);


//            System.out.println("\n===== TESTE READ =====");
//
//            List<Usuario> lista = dao.read();
//
//            for (Usuario u : lista) {
//                System.out.println(
//                        u.getId() + " - " +
//                                u.getNome() + " " +
//                                u.getSobrenome() + " - " +
//                                u.getEmail()
//                );
//            }
//
//
//            System.out.println("\n===== TESTE READ BY EMAIL =====");
//
//            Usuario porEmail = dao.readByEmail("matheus@email.com");
//
//            if (porEmail != null) {
//                System.out.println("Encontrado: " + porEmail.getNome());
//            } else {
//                System.out.println("Usuário não encontrado.");
//            }
//
//
//            System.out.println("\n===== TESTE LOGIN =====");
//
//            Usuario login = dao.login("matheus@email.com", "Senha123!");
//
//            if (login != null) {
//                System.out.println("Login realizado com sucesso: " + login.getNome());
//            } else {
//                System.out.println("Falha no login.");
//            }
//
//
//            System.out.println("\n===== TESTE UPDATE =====");
//
//            if (porEmail != null) {
//
//                porEmail.setNome("Matheus Atualizado");
//                porEmail.setSobrenome("Silva");
//                porEmail.setSenha("NovaSenha123!");
//
//                int atualizado = dao.update(porEmail);
//                System.out.println("Linhas atualizadas: " + atualizado);
//            }
//
//
//            System.out.println("\n===== TESTE READ BY ID =====");
//
//            if (porEmail != null) {
//
//                Usuario porId = dao.readById(porEmail.getId());
//
//                if (porId != null) {
//                    System.out.println("Usuário atualizado: " + porId.getNome());
//                }
//            }
//
//
//            System.out.println("\n===== TESTE DELETE =====");
//
//            if (porEmail != null) {
//
//                int deletado = dao.deleteById(porEmail.getId());
//                System.out.println("Linhas deletadas: " + deletado);
//            }
//
//
//        } catch (SQLException e) {
//            System.out.println("Erro no teste: " + e.getMessage());
//        }
    }
}