public class Aluno
{
    private int codigo;
    private String nome;
    private String dataNascimento;
    private String email;
    private String senha;

    // Curso do aluno
    private Curso cursoMatriculado;

    // Notas
    private double[] notas = new double[3];
    private boolean[] lancada = new boolean[3];

    // Mensalidades
    private Mensalidade[] mensalidades;
    private int numParcelas;

    public Aluno(int codigo, String nome, String dataNascimento,
                 String email, String senha)
    {
        this.codigo = codigo;
        this.nome = nome;
        this.dataNascimento = dataNascimento;
        this.email = email;
        this.senha = senha;

        numParcelas = 0;
    }

    public int getCodigo()
    {
        return codigo;
    }

    public void setCodigo(int codigo)
    {
        this.codigo = codigo;
    }

    public String getNome()
    {
        return nome;
    }

    public void setNome(String nome)
    {
        this.nome = nome;
    }

    public String getDataNascimento()
    {
        return dataNascimento;
    }

    public void setDataNascimento(String dataNascimento)
    {
        this.dataNascimento = dataNascimento;
    }

    public String getEmail()
    {
        return email;
    }

    public void setEmail(String email)
    {
        this.email = email;
    }

    public String getSenha()
    {
        return senha;
    }

    public void setSenha(String senha)
    {
        this.senha = senha;
    }

    // Curso

    public Curso getCursoMatriculado()
    {
        return cursoMatriculado;
    }

    public void setCursoMatriculado(Curso cursoMatriculado)
    {
        this.cursoMatriculado = cursoMatriculado;
    }

    public void exibeDados()
    {
        System.out.println("Codigo: " + codigo);
        System.out.println("Nome: " + nome);
        System.out.println("Data de nascimento: " + dataNascimento);
        System.out.println("Email: " + email);
        System.out.println("Senha: " + senha);

        if (cursoMatriculado != null)
        {
            System.out.println(
                "Curso: " + cursoMatriculado.getNome()
            );
        }
        else
        {
            System.out.println("Curso: Nenhum curso matriculado");
        }
    }

    // =========================
    // NOTAS
    // =========================

    public void lancarNotas(double nota1, double nota2, double nota3)
    {
        notas[0] = nota1;
        notas[1] = nota2;
        notas[2] = nota3;

        lancada[0] = true;
        lancada[1] = true;
        lancada[2] = true;
    }

    public double calcularMedia()
    {
        return (notas[0] + notas[1] + notas[2]) / 3;
    }

    public void exibirNotas()
    {
        System.out.println("Nota 1: " + notas[0]);
        System.out.println("Nota 2: " + notas[1]);
        System.out.println("Nota 3: " + notas[2]);
        System.out.println("Media: " + calcularMedia());
    }

    // =========================
    // MENSALIDADES
    // =========================

    public void adicionarMensalidades(double[] valores)
    {
        numParcelas = valores.length;

        mensalidades = new Mensalidade[numParcelas];

        for (int i = 0; i < numParcelas; i++)
        {
            mensalidades[i] = new Mensalidade(valores[i]);
        }
    }

    public void exibirMensalidades()
    {
        if (mensalidades == null)
        {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }

        for (int i = 0; i < numParcelas; i++)
        {
            String status;

            if (mensalidades[i].isPago())
            {
                status = "PAGA";
            }
            else
            {
                status = "NAO PAGA";
            }

            System.out.println(
                "Parcela " + (i + 1) +
                " - R$ " + mensalidades[i].getValor() +
                " - " + status
            );
        }
    }

    public void pagarMensalidade(int indice)
    {
        if (mensalidades == null)
        {
            System.out.println("Nenhuma mensalidade cadastrada.");
            return;
        }

        if (indice < 0 || indice >= numParcelas)
        {
            System.out.println("Parcela invalida.");
            return;
        }

        mensalidades[indice].darBaixa();

        System.out.println(
            "Parcela " + (indice + 1) +
            " paga com sucesso!"
        );
    }
}