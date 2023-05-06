package pojo;

import java.util.Arrays;

/**
 * enum criado para armazenar os cursos e disciplinas disponíveis na aplicação
 */
public enum Curso {
    NULO("insira um curso", null),
    CIENCIA_DA_COMPUTAÇÂO("Ciência da computação", new String[]{
            "POO",
            "Matemática discreta",
            "Cálculo"
    }),
    ENGENHARIA_DE_SOFTWARE("Engenharia de software", new String[]{
            "POO",
            "Docker",
            "Sistemas operacionais"
    }),

    DESIGN("Design", new String[]{
            "desenho",
            "ergonomia",
            "photoshop"
    });

    public final String nome;
    public final String[] disciplinas;

    Curso(String nome, String[] disciplinas) {
        this.nome = nome;
        this.disciplinas = disciplinas;
    }

    public static Curso get(String name){
        for(Curso curso: Curso.values())
            if(curso.nome.equals(name))
                return curso;

        return null;
    }

    public boolean checarDisciplina(String disciplina){
        return  Arrays.stream(this.disciplinas).anyMatch(d -> d.equals(disciplina));
    }

    @Override
    public String toString(){
        return this.nome;
    }
}
