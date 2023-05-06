package pojo;

/**
 * Objeto criado para transferir registros do banco de dados para um objeto java
 * Registros retornados pela consulta da funlçao {@link dao.NotaDao#gerarBoletimBase(int, String)}
 */
public class Disciplina {

    private String disciplina;
    private float media;
    private int faltas;

    public Disciplina(String disciplina, float media, int faltas) {
        this.disciplina = disciplina;
        this.media = media;
        this.faltas = faltas;
    }

    @Override
    public String toString(){
        return String.format("%s - Media: %.2f - Faltas: %d", disciplina, media, faltas);
    }

    public Disciplina() {
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public float getMedia() {
        return media;
    }

    public void setMedia(float media) {
        this.media = media;
    }

    public int getFaltas() {
        return faltas;
    }

    public void setFaltas(int faltas) {
        this.faltas = faltas;
    }
}
