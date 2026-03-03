//package com.example.servlet.ServletObservacao;
//
//import com.example.dao.AlunoDAO;
//import com.example.dao.ObservacaoDAO;
//import com.example.dao.ProfessorDAO;
//import com.example.models.Observacao;
//import jakarta.servlet.ServletException;
//import jakarta.servlet.annotation.WebServlet;
//import jakarta.servlet.http.HttpServlet;
//import jakarta.servlet.http.HttpServletRequest;
//import jakarta.servlet.http.HttpServletResponse;
//
//import java.io.IOException;
//import java.time.LocalDateTime;
//
//@WebServlet("/observacao-update")
//public class UpdateObservacao extends HttpServlet {
//
//    @Override
//    protected void doPost(HttpServletRequest request, HttpServletResponse response)
//            throws ServletException, IOException {
//
//        request.setCharacterEncoding("UTF-8");
//
//        String idStr = request.getParameter("id");
//        String texto = request.getParameter("texto");
//        String idProfessorStr = request.getParameter("fkProfessorId");
//        String idAlunoStr = request.getParameter("fkAlunoId");
//
//        ObservacaoDAO observacaoDAO = new ObservacaoDAO();
//        AlunoDAO alunoDAO = new AlunoDAO();
//        ProfessorDAO professorDAO = new ProfessorDAO();
//        String erro = null;
//
//        try {
//            int id = Integer.parseInt(idStr);
//            int fkProfessorId = Integer.parseInt(idProfessorStr);
//            int fkAlunoId = Integer.parseInt(idAlunoStr);
//
//            Observacao observacao = observacaoDAO.readById(id);
//            if (observacao == null) throw new Exception("Observação não encontrada.");
//
//            observacao.setTexto(texto);
//            observacao.setFkProfessorId(fkProfessorId);
//            observacao.setFkAlunoId(fkAlunoId);
//            observacao.setDataEnvio(LocalDateTime.now());
//
//            if (observacaoDAO.update(observacao) > 0) {
//                response.sendRedirect(request.getContextPath() + "/observacao-read");
//                return;
//            } else {
//                erro = "Erro ao atualizar observação no banco.";
//            }
//
//        } catch (NumberFormatException e) {
//            erro = "Dados inválidos.";
//        } catch (IllegalArgumentException e) {
//            erro = "Validação: " + e.getMessage();
//        } catch (Exception e) {
//            e.printStackTrace();
//            erro = "Erro: " + e.getMessage();
//        }
//
//        request.setAttribute("erro", erro);
//        request.setAttribute("modalAtivo", "update");
//
//        try {
//            request.setAttribute("listaObservacoes", observacaoDAO.read());
//            request.setAttribute("listaAlunos", alunoDAO.read());
//            request.setAttribute("listaProfessores", professorDAO.read());
//
//            if (idStr != null) {
//                request.setAttribute("observacaoModal", observacaoDAO.readById(Integer.parseInt(idStr)));
//            }
//
//        } catch (Exception e) {
//            e.printStackTrace();
//            if (request.getAttribute("erro") == null) {
//                request.setAttribute("erro", "Erro ao recarregar as listas.");
//            }
//        }
//
//        request.getRequestDispatcher("/WEB-INF/pages/observacoes.jsp").forward(request, response);
//    }
//}