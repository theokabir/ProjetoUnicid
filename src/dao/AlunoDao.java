package dao;

import model.Aluno;
import utils.ConnectionFactory;
import utils.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

/**
 * classe com as operações de banco da tabela aluno
 */
public class AlunoDao {

    /**
     * Função para cadastrar o caluno no banco de dados
     * @param aluno Objeto com os dados a serem incluidos no banco na tabela alunos
     */
    public void cadastrar(Aluno aluno){

        String sql = """
                INSERT INTO aluno
                (rgm, nome, nascimento, cpf, email, endereco, municipio, uf, celular)
                VALUES (?,?,?,?,?,?,?,?,?)
                """;

        try{
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, aluno.getRgm());
            ps.setString(2, aluno.getNome());
            ps.setString(3, aluno.getNascimento());
            ps.setString(4, aluno.getCpf());
            ps.setString(5, aluno.getEmail());
            ps.setString(6, aluno.getEndereco());
            ps.setString(7, aluno.getMunicipio());
            ps.setString(8, aluno.getUf());
            ps.setString(9, aluno.getCelular());

            ps.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps);

            Messages.popup("Usuário adicionado");
        }catch(Exception e){
            Messages.error("Erro ao Salvar usuário");
            System.out.println("ERRO:\n"+e.getMessage());
        }
    }

    /**
     * Função para buscar registro de aluno no banco de dados
     * @param rgm rgm do aluno a ser buscado
     * @return reotrna objeto do tipo {@link Aluno} vindo do banco
     */
    public Aluno checarAluno(int rgm){
        String sql = """
                SELECT *
                FROM aluno
                WHERE rgm = ?
                """;
        try{
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, rgm);

            ResultSet rs = ps.executeQuery();

            Aluno aluno = null;

            if(rs.next())
                aluno = new Aluno(
                        rs.getInt("rgm"),
                        rs.getString("nome"),
                        rs.getString("nascimento"),
                        rs.getString("cpf"),
                        rs.getString("email"),
                        rs.getString("endereco"),
                        rs.getString("municipio"),
                        rs.getString("uf"),
                        rs.getString("celular"),
                        rs.getString("curso"),
                        rs.getString("campus"),
                        rs.getString("periodo")
                );

            ConnectionFactory.closeConnection(conn, ps, rs);

            return aluno;
        }catch (Exception e){
            Messages.error("Erro a encontrar aluno");
            System.out.println("ERRO:\n" + e.getMessage());
            return null;
        }
    }

    /**
     * Função para alterar o aluno no banco de dados
     * @param aluno aluno a ser alterado, o rgm deve se referir ao aluno a ser alterado no banco
     */
    public void alterar(Aluno aluno){
        String sql = """
                UPDATE aluno
                SET
                    nome=?,
                    nascimento=?,
                    cpf=?,
                    email=?,
                    endereco=?,
                    municipio=?,
                    uf=?,
                    celular=?
                WHERE
                    rgm=?
                """;

        try{
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, aluno.getNome());
            ps.setString(2, aluno.getNascimento());
            ps.setString(3, aluno.getCpf());
            ps.setString(4, aluno.getEmail());
            ps.setString(5, aluno.getEndereco());
            ps.setString(6, aluno.getMunicipio());
            ps.setString(7, aluno.getUf());
            ps.setString(8, aluno.getCelular());
            ps.setInt(9, aluno.getRgm());

            ps.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps);

            Messages.popup("Usuário Modificado");
        }catch(Exception e){
            Messages.error("Erro ao exlcuir usuário");
            System.out.println("ERRO:\n"+e.getMessage());
        }
    }

    /**
     * Função para excluir aluno junto de suas notas do banco de dados
     * @param rgm rgm do aluno a ser excluido
     */
    public void excluir(int rgm){
        String sql_aluno = "DELETE FROM aluno WHERE rgm=?";
        String sql_notas = "DELETE FROM nota WHERE rgm_aluno=?";

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps_aluno = conn.prepareStatement(sql_aluno);
            PreparedStatement ps_notas = conn.prepareStatement(sql_notas);

            ps_notas.setInt(1, rgm);
            ps_aluno.setInt(1, rgm);

            ps_notas.executeUpdate();
            ps_aluno.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps_notas);
            ps_aluno.close();

            Messages.popup("Aluno Excluido");
        }catch(Exception e){
            Messages.error("erro ao excluir aluno");
            System.out.println("ERRO:\n" + e.getMessage());
        }
    }

    /**
     * Eltera os campos de campus, surso e periodo que o aluno estuda
     * @param aluno aluno a ser alterado, com os campos ja trocados e seu rgm referente ao aluno correto
     */
    public void trocarCurso(Aluno aluno){
        String sql = "UPDATE aluno SET curso=?, campus=?, periodo=? WHERE rgm =?";
        try{
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setString(1, aluno.getCurso());
            ps.setString(2, aluno.getCampus());
            ps.setString(3, aluno.getPeriodo());
            ps.setInt(4, aluno.getRgm());

            ps.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps);

            Messages.popup("curso configurado");
        }catch(Exception e){
            Messages.error("Erro ao configurar curso");
            System.out.println("ERRO:\n"+e.getMessage());
        }
    }

    /**
     * remove os campos de curso periodo e campus do usuário
     * @param rgm registro do aluno a ter o curso removido
     */
    public void excluirCurso(int rgm){
        String sql = "UPDATE aluno SET curso=null, periodo=null, campus=null WHERE rgm=?";
        try{
            Connection conn = ConnectionFactory.getConnection();

            PreparedStatement ps = conn.prepareStatement(sql);
            ps.setInt(1, rgm);

            ps.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps);

            Messages.popup("aluno removido do curso");
        }catch(Exception e){
            Messages.error("Erro ao excluir curso");
            System.out.println("ERRO:\n"+e.getMessage());
        }
    }

}
