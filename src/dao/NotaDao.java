package dao;

import model.Nota;
import pojo.Disciplina;
import utils.ConnectionFactory;
import utils.Messages;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * classe com as operações de banco da tabela nota
 */
public class NotaDao {

    /**
     * Função para adicionar a nota no banco
     * @param nota objeto {@link Nota} que será inserido no banco
     */
    public void adicionar(Nota nota){

        String sql = "INSERT INTO nota VALUES (default,?,?,?,?,?)";

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, nota.getRgm_aluno());
            ps.setString(2, nota.getDisciplina());
            ps.setString(3, nota.getSemestre());
            ps.setFloat(4, nota.getNota());
            ps.setInt(5, nota.getFalta());

            ps.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps);

            Messages.popup("Nota salva");

        }catch (Exception e){
            System.out.println(e.getMessage());
            Messages.error("Erro ao adicionar nota");
        }

    }

    /**
     * função ara consultar nota no banco de dados
     * @param id id da nota a ser retornada
     * @return retorna o nota vinda do banco ded ados
     */
    public Nota consultar(int id){

        String sql = "SELECT * FROM nota WHERE id=?";
        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);

            return getNota(conn, ps);

        }catch (Exception e){

            Messages.error("Erro ao consultar nota");
            System.out.println("ERRO:\n" + e.getMessage());
            return null;

        }

    }

    /**
     * função para retornar a nota em que o id seja congruente com o rgm do aluno informado
     * @param id identificador da nota
     * @param rgm rgm do aluno ligado a nota
     * @return retorna o objeto {@link Nota} proveniente do banco de dados
     */
    public Nota consultar(int id, int rgm){

        String sql = "SELECT * FROM nota WHERE id=? AND rgm_aluno=?";
        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.setInt(2, rgm);

            return getNota(conn, ps);

        }catch (Exception e){

            Messages.error("Erro ao consultar nota");
            System.out.println("ERRO:\n" + e.getMessage());
            return null;

        }

    }

    /**
     * Método criado para simplificação dos dois métodos de consulta
     * esse método é responsável por transferir o registro do banco em um objeto {@link Nota}
     * @param conn objeto de conexão
     * @param ps preparedStatement criado com a query correta para a busca da nota
     * @return Retorna a nota vinda do banco
     * @throws Exception em caso de erros com a consulta ao banco
     */
    private Nota getNota(Connection conn, PreparedStatement ps) throws Exception{
        ResultSet rs = ps.executeQuery();

        Nota nota = null;

        if(rs.next()){
            nota = new Nota();
            nota.setNota(rs.getFloat("nota"));
            nota.setDisciplina(rs.getString("disciplina"));
            nota.setId(rs.getInt("id"));
            nota.setSemestre(rs.getString("semestre"));
            nota.setRgm_aluno(rs.getInt("rgm_aluno"));
            nota.setFalta(rs.getInt("faltas"));
        }

        ConnectionFactory.closeConnection(conn, ps, rs);

        return nota;
    }

    /**
     * Função para alterar a nota de algum aluno
     * @param nota objeto que deve ser alterado, com o id referente ao objeto correto no banco
     */
    public void alterar(Nota nota){

        Nota toUpdate = consultar(nota.getId(), nota.getRgm_aluno());

        if(toUpdate==null){
            Messages.popup("Nota não encontrada\n" +
                    "O id deve se referir ao aluno do rgm, não é possivel alterar o dono da nota");
            return;
        }


        String sql = "UPDATE nota SET nota=?, faltas=? , semestre=?, disciplina=? WHERE id=? AND rgm_aluno=?";

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setFloat(1, nota.getNota());
            ps.setInt(2, nota.getFalta());
            ps.setString(3, nota.getSemestre());
            ps.setString(4, nota.getDisciplina());
            ps.setInt(5, nota.getId());
            ps.setInt(6, nota.getRgm_aluno());

            ps.executeUpdate();

            ConnectionFactory.closeConnection(conn, ps);

            Messages.popup("Nota alterada");

        }catch (Exception e){

            Messages.error("Erro ao alterar nota");
            System.out.println("ERRO:\n" + e.getMessage());

        }

    }

    /**
     * Função para excluir registro de nota no banco
     * @param id identificador da nota
     */
    public void excluir(int id){
        String sql = "DELETE FROM nota WHERE id=?";
        Nota nota = this.consultar(id);

        if(nota == null){
            Messages.popup("Nota inexistente");
            return;
        }

        try{
            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, id);
            ps.executeUpdate();

            Messages.popup("Nota excluida");

            ConnectionFactory.closeConnection(conn, ps);
        }catch (Exception e){
            Messages.error("Erro ao consultar nota");
            System.out.println("ERRO:\n" + e.getMessage());
        }
    }

    /**
     * Função que retorna todas as notas de um aluno referente a disciplina selecionada e o semestre
     * @param rgm cadastro do aluno
     * @param semestre semestre a ser consultado
     * @param disciplina disciplina a ser consultado
     * @return ele devolte um vetor de objetos do tipo {@link Nota}
     */
    public Nota[] getNotasBoletim(int rgm, String semestre, String disciplina){
        String sql = """
                SELECT * 
                FROM nota
                WHERE rgm_aluno=? AND semestre=? AND disciplina=?
                """;

        try{

            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setInt(1, rgm);
            ps.setString(2, semestre);
            ps.setString(3, disciplina);

            ResultSet rs = ps.executeQuery();

            List<Nota> notas = new ArrayList<>();

            while(rs.next()){
                notas.add(new Nota(
                        rs.getInt("id"),
                        rs.getInt("rgm_aluno"),
                        rs.getString("disciplina"),
                        rs.getString("semestre"),
                        rs.getFloat("nota"),
                        rs.getInt("faltas")
                ));
            }

            ConnectionFactory.closeConnection(conn, ps, rs);
            return notas.toArray(new Nota[0]);

        }catch (Exception e){
            Messages.error("Erro ao encontrar notas");
            return new Nota[0];
        }
    }

    /**
     * Função que gera os objetos do tipo {@link Disciplina} com as informações a serem dispostas no boletim baseado baseado no rgm do aluno e no semestre
     * @param rgm registro do aluno
     * @param semestre semestre a ser consultado
     * @return ele retorna um Array do tipo {@link Disciplina} com as disciplinas, sua média de notas e somatória de faltas
     */
    public Disciplina[] gerarBoletimBase(int rgm, String semestre){
        String sql = """
            SELECT disciplina, AVG(nota) as media, SUM(faltas) as faltas
            FROM nota
            WHERE semestre=? AND rgm_aluno=?
            GROUP BY disciplina;
            """;

        try{

            Connection conn = ConnectionFactory.getConnection();
            PreparedStatement ps = conn.prepareStatement(sql);

            ps.setString(1, semestre);
            ps.setInt(2, rgm);

            ResultSet rs = ps.executeQuery();
            List<Disciplina> disciplinas = new ArrayList<>();

            while(rs.next())
                disciplinas.add(new Disciplina(
                        rs.getString("disciplina"),
                        rs.getFloat("media"),
                        rs.getInt("faltas")
                ));

            ConnectionFactory.closeConnection(conn, ps, rs);

            return disciplinas.toArray(new Disciplina[0]);
        }catch (Exception e){
            Messages.popup("Erro ao carregar notas do boletim");
            return null;
        }
    }

}
