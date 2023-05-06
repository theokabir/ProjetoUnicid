package model;

/**
 * modelo de objeto utilizado para transformar os registros da tabela notas em um objeto java
 */
public class Nota {

    private int id;
    private int rgm_aluno;
    private String disciplina;
    private String semestre;
    private float nota;
    private int falta;

    public Nota(int id, int rgm_aluno, String disciplina, String semestre, float nota, int falta) {
        this.id = id;
        this.rgm_aluno = rgm_aluno;
        this.disciplina = disciplina;
        this.semestre = semestre;
        this.nota = nota;
        this.falta = falta;
    }

    @Override
    public String toString(){
        return String.format("id: %d - nota: %.2f - falta: %d", id, nota, falta);
    }

    public Nota() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRgm_aluno() {
        return rgm_aluno;
    }

    public void setRgm_aluno(int rgm_aluno) {
        this.rgm_aluno = rgm_aluno;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getSemestre() {
        return semestre;
    }

    public void setSemestre(String semestre) {
        this.semestre = semestre;
    }

    public float getNota() {
        return nota;
    }

    public void setNota(float nota) {
        this.nota = nota;
    }

    public int getFalta() {
        return falta;
    }

    public void setFalta(int falta) {
        this.falta = falta;
    }
}
