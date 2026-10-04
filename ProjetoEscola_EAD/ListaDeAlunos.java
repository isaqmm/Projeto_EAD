public class ListaDeAlunos
{
    private Aluno[] alunos;
    private int totalAlunos;

    public ListaDeAlunos(int capacidade)
    {
        alunos = new Aluno[capacidade];
        totalAlunos = 0;
    }

    public boolean adicionarAluno(Aluno a)
    {
        if (totalAlunos >= alunos.length)
        {
            return false;
        }

        for (int i = 0; i < totalAlunos; i++)
        {
            if (alunos[i].getCodigo() == a.getCodigo())
            {
                return false;
            }
        }

        alunos[totalAlunos] = a;
        totalAlunos++;

        return true;
    }

    public void exibirLista()
    {
        if (totalAlunos == 0)
        {
            System.out.println("Nenhum aluno cadastrado.");
            return;
        }

        for (int i = 0; i < totalAlunos; i++)
        {
            alunos[i].exibeDados();

            System.out.println("-------------------------");
        }
    }

    public Aluno buscarAluno(int codigo)
    {
        for (int i = 0; i < totalAlunos; i++)
        {
            if (alunos[i].getCodigo() == codigo)
            {
                return alunos[i];
            }
        }

        return null;
    }

    public void exibirAlunosDoCurso(Curso curso)
    {
        boolean encontrou = false;

        for (int i = 0; i < totalAlunos; i++)
        {
            if (alunos[i].getCursoMatriculado() == curso)
            {
                System.out.println(
                    "- " + alunos[i].getNome() +
                    " (codigo " + alunos[i].getCodigo() + ")"
                );

                encontrou = true;
            }
        }

        if (!encontrou)
        {
            System.out.println("- Nenhum aluno matriculado");
        }
    }
}