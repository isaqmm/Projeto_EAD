import java.util.Scanner;

public class SistemaEscolaEAD
{
    private Scanner entrada;
    private ListaDeAlunos listaAlunos;

    // Matriz bidimensional obrigatoria
    private Curso[][] matrizCursos;

    private int totalCursos;

    public SistemaEscolaEAD()
    {
        entrada = new Scanner(System.in);

        listaAlunos = new ListaDeAlunos(50);

        // 3 turmas com 5 cursos cada
        matrizCursos = new Curso[3][5];

        totalCursos = 0;
    }

    public void iniciar()
    {
        int opcao;

        do
        {
            exibirMenu();

            opcao = entrada.nextInt();
            entrada.nextLine();

            switch (opcao)
            {
                case 1:
                    listaAlunos.exibirLista();
                    break;

                case 2:
                    adicionarAluno();
                    break;

                case 3:
                    System.out.println("Sistema encerrado.");
                    break;

                case 4:
                    verificarNotas();
                    break;

                case 5:
                    verificarFinanceiro();
                    break;

                case 6:
                    cadastrarCursos();
                    break;

                case 7:
                    matricularAlunoCurso();
                    break;

                case 8:
                    exibirCursos();
                    break;

                default:
                    System.out.println("Opcao invalida.");
            }

        } while (opcao != 3);
    }

    private void exibirMenu()
    {
        System.out.println();
        System.out.println("================================");
        System.out.println("          ESCOLA EAD");
        System.out.println("================================");

        System.out.println("1 - Visualizar Lista de Alunos");
        System.out.println("2 - Adicionar Aluno");
        System.out.println("3 - Sair");
        System.out.println("4 - Verificar Notas do Aluno");
        System.out.println("5 - Verificar Financeiro do Aluno");
        System.out.println("6 - Cadastrar Cursos");
        System.out.println("7 - Matricular Aluno em Curso");
        System.out.println("8 - Visualizar Cursos e Alunos");

        System.out.println("================================");
        System.out.print("Digite uma opcao: ");
    }

    // ==========================================
    // CADASTRAR ALUNO
    // ==========================================

    private void adicionarAluno()
    {
        System.out.println();
        System.out.println("===== CADASTRO DE ALUNO =====");

        System.out.print("Codigo: ");
        int codigo = entrada.nextInt();
        entrada.nextLine();

        System.out.print("Nome: ");
        String nome = entrada.nextLine();

        System.out.print("Data de nascimento: ");
        String dataNascimento = entrada.nextLine();

        System.out.print("Email: ");
        String email = entrada.nextLine();

        System.out.print("Senha: ");
        String senha = entrada.nextLine();

        System.out.print("O aluno e bolsista? (s/n): ");
        String resposta = entrada.nextLine();

        Aluno aluno;

        if (resposta.equalsIgnoreCase("s"))
        {
            System.out.print("Tipo de bolsa: ");
            String tipoBolsa = entrada.nextLine();

            aluno = new AlunoBolsista(
                codigo,
                nome,
                dataNascimento,
                email,
                senha,
                tipoBolsa
            );
        }
        else
        {
            aluno = new Aluno(
                codigo,
                nome,
                dataNascimento,
                email,
                senha
            );
        }

        if (listaAlunos.adicionarAluno(aluno))
        {
            System.out.println();
            System.out.println("Aluno cadastrado com sucesso!");

            System.out.println();
            System.out.print(
                "Deseja matricular o aluno em um curso agora? (s/n): "
            );

            String matricular = entrada.nextLine();

            if (matricular.equalsIgnoreCase("s"))
            {
                matricularAluno(aluno);
            }
        }
        else
        {
            System.out.println();
            System.out.println("Nao foi possivel cadastrar.");
            System.out.println(
                "O codigo ja pode estar cadastrado ou a lista esta cheia."
            );
        }
    }

    // ==========================================
    // CADASTRAR CURSOS
    // ==========================================

    private void cadastrarCursos()
    {
        System.out.println();
        System.out.println("===== CADASTRO DE CURSOS =====");

        System.out.print("Quantos cursos deseja cadastrar? ");
        int quantidade = entrada.nextInt();
        entrada.nextLine();

        for (int i = 0; i < quantidade; i++)
        {
            if (totalCursos >= 15)
            {
                System.out.println("Limite de cursos atingido.");
                break;
            }

            System.out.println();
            System.out.println("===== CURSO " + (i + 1) + " =====");

            System.out.print("Codigo: ");
            int codigo = entrada.nextInt();
            entrada.nextLine();

            System.out.print("Nome: ");
            String nome = entrada.nextLine();

            System.out.print("Duracao em horas: ");
            int duracao = entrada.nextInt();
            entrada.nextLine();

            Curso curso = new Curso(
                codigo,
                nome,
                duracao
            );

            int linha = totalCursos / 5;
            int coluna = totalCursos % 5;

            matrizCursos[linha][coluna] = curso;

            totalCursos++;

            System.out.println("Curso cadastrado com sucesso!");
        }
    }

    // ==========================================
    // BUSCAR CURSO
    // ==========================================

    private Curso buscarCurso(int codigo)
    {
        for (int linha = 0; linha < matrizCursos.length; linha++)
        {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length;
                 coluna++)
            {
                if (matrizCursos[linha][coluna] != null)
                {
                    if (matrizCursos[linha][coluna].getCodigo() == codigo)
                    {
                        return matrizCursos[linha][coluna];
                    }
                }
            }
        }

        return null;
    }

    // ==========================================
    // MATRICULAR ALUNO
    // ==========================================

    private void matricularAlunoCurso()
    {
        System.out.println();
        System.out.println("===== MATRICULA EM CURSO =====");

        System.out.print("Digite o codigo do aluno: ");
        int codigoAluno = entrada.nextInt();

        Aluno aluno = listaAlunos.buscarAluno(codigoAluno);

        if (aluno == null)
        {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        matricularAluno(aluno);
    }

    private void matricularAluno(Aluno aluno)
    {
        if (totalCursos == 0)
        {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        System.out.println();
        System.out.println("Cursos disponiveis:");

        exibirSomenteCursos();

        System.out.println();

        System.out.print("Digite o codigo do curso: ");
        int codigoCurso = entrada.nextInt();
        entrada.nextLine();

        Curso curso = buscarCurso(codigoCurso);

        if (curso == null)
        {
            System.out.println("Curso nao encontrado.");
            return;
        }

        aluno.setCursoMatriculado(curso);

        System.out.println();
        System.out.println("Aluno matriculado com sucesso!");
        System.out.println(
            "Curso: " + curso.getNome()
        );
    }

    // ==========================================
    // EXIBIR CURSOS
    // ==========================================

    private void exibirCursos()
    {
        System.out.println();
        System.out.println("===== CURSOS E ALUNOS =====");

        if (totalCursos == 0)
        {
            System.out.println("Nenhum curso cadastrado.");
            return;
        }

        for (int linha = 0; linha < matrizCursos.length; linha++)
        {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length;
                 coluna++)
            {
                Curso curso = matrizCursos[linha][coluna];

                if (curso != null)
                {
                    System.out.println();
                    curso.exibeDados();

                    System.out.println("Alunos matriculados:");

                    listaAlunos.exibirAlunosDoCurso(curso);

                    System.out.println("-------------------------");
                }
            }
        }
    }

    private void exibirSomenteCursos()
    {
        for (int linha = 0; linha < matrizCursos.length; linha++)
        {
            for (int coluna = 0;
                 coluna < matrizCursos[linha].length;
                 coluna++)
            {
                if (matrizCursos[linha][coluna] != null)
                {
                    System.out.println(
                        "Codigo: " +
                        matrizCursos[linha][coluna].getCodigo() +
                        " - " +
                        matrizCursos[linha][coluna].getNome()
                    );
                }
            }
        }
    }

    // ==========================================
    // NOTAS
    // ==========================================

    private void verificarNotas()
    {
        System.out.println();
        System.out.println("===== NOTAS DO ALUNO =====");

        System.out.print("Digite o codigo do aluno: ");
        int codigo = entrada.nextInt();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        System.out.println();
        aluno.exibeDados();

        System.out.println();

        System.out.print("Digite a nota 1: ");
        double nota1 = entrada.nextDouble();

        System.out.print("Digite a nota 2: ");
        double nota2 = entrada.nextDouble();

        System.out.print("Digite a nota 3: ");
        double nota3 = entrada.nextDouble();

        aluno.lancarNotas(
            nota1,
            nota2,
            nota3
        );

        System.out.println();
        System.out.println("===== RESULTADO =====");

        aluno.exibirNotas();
    }

    // ==========================================
    // FINANCEIRO
    // ==========================================

    private void verificarFinanceiro()
    {
        System.out.println();
        System.out.println("===== FINANCEIRO DO ALUNO =====");

        System.out.print("Digite o codigo do aluno: ");
        int codigo = entrada.nextInt();

        Aluno aluno = listaAlunos.buscarAluno(codigo);

        if (aluno == null)
        {
            System.out.println("Aluno nao encontrado.");
            return;
        }

        System.out.println();
        aluno.exibeDados();

        System.out.println();

        System.out.print(
            "Digite o valor total do curso: R$ "
        );

        double valorTotal = entrada.nextDouble();

        System.out.print(
            "Em quantas parcelas deseja pagar? "
        );

        int quantidade = entrada.nextInt();

        if (quantidade <= 0)
        {
            System.out.println("Quantidade de parcelas invalida.");
            return;
        }

        double valorParcela = valorTotal / quantidade;

        double[] valores = new double[quantidade];

        for (int i = 0; i < quantidade; i++)
        {
            valores[i] = valorParcela;
        }

        aluno.adicionarMensalidades(valores);

        System.out.println();
        System.out.println("===== PLANO DE PAGAMENTO =====");

        System.out.println(
            "Valor total: R$ " + valorTotal
        );

        System.out.println(
            "Quantidade de parcelas: " + quantidade
        );

        System.out.println(
            "Valor de cada parcela: R$ " + valorParcela
        );

        System.out.println();
        System.out.println("===== MENSALIDADES =====");

        aluno.exibirMensalidades();

        System.out.println();

        System.out.print(
            "Deseja pagar uma parcela? (1 = Sim / 0 = Nao): "
        );

        int pagar = entrada.nextInt();

        if (pagar == 1)
        {
            System.out.print(
                "Digite o numero da parcela: "
            );

            int parcela = entrada.nextInt();

            aluno.pagarMensalidade(parcela - 1);

            System.out.println();
            System.out.println("===== FINANCEIRO ATUALIZADO =====");

            aluno.exibirMensalidades();
        }
    }

    // ==========================================
    // MAIN
    // ==========================================

    public static void main(String[] args)
    {
        SistemaEscolaEAD sistema =
            new SistemaEscolaEAD();

        sistema.iniciar();
    }
}