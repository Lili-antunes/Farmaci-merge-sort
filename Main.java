import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Medicamento> estoque = new ArrayList<>();
    private static final Scanner sc = new Scanner(System.in);

    public static void main(String[] args) {
        int opcao;
        do {
            System.out.println("\n CADASTRO DE MEDICAMENTOS ");
            System.out.println("1 - Cadastrar medicamento");
            System.out.println("2 - Listar medicamentos");
            System.out.println("3 - Ordenar por nome (A-Z)");
            System.out.println("4 - Ordenar por preco (menor -> maior)");
            System.out.println("5 - Carregar exemplos");
            System.out.println("0 - Sair");
            System.out.print("Opcao: ");
            opcao = lerInt();

            switch (opcao) {
                case 1 -> cadastrar();
                case 2 -> listar();
                case 3 -> ordenar("nome", Comparator.comparing(m -> m.getNome().toLowerCase()));
                case 4 -> ordenar("preco", Comparator.comparingDouble(Medicamento::getPreco));
                case 5 -> carregarExemplos();
                case 0 -> System.out.println("Encerrando...");
                default -> System.out.println("Opcao invalida.");
            }
        } while (opcao != 0);
    }

    private static void cadastrar() {
        System.out.print("Nome: ");
        String nome = sc.nextLine().trim();
        System.out.print("Fabricante: ");
        String fabricante = sc.nextLine().trim();
        System.out.print("Preco (ex: 12,90): ");
        double preco = lerDouble();

        estoque.add(new Medicamento(nome, fabricante, preco));
        System.out.println("Medicamento cadastrado! Total: " + estoque.size());
    }

    private static void listar() {
        if (estoque.isEmpty()) {
            System.out.println("Nenhum medicamento cadastrado.");
            return;
        }
        System.out.println();
        for (int i = 0; i < estoque.size(); i++) {
            System.out.printf("%2d) %s%n", i + 1, estoque.get(i));
        }
    }

    private static void ordenar(String campo, Comparator<Medicamento> criterio) {
        if (estoque.isEmpty()) {
            System.out.println("Nenhum medicamento cadastrado.");
            return;
        }
        Mergesort.ordenar(estoque, criterio);
        System.out.println("Ordenado por " + campo + " usando Merge Sort:");
        listar();
    }

     private static void carregarExemplos() {
        estoque.add(new Medicamento("Dipirona 500mg", "EMS", 8.90));
        estoque.add(new Medicamento("Amoxicilina 500mg", "Eurofarma", 24.50));
        estoque.add(new Medicamento("Paracetamol 750mg", "Medley", 11.20));
        estoque.add(new Medicamento("Ibuprofeno 400mg", "Neo Quimica", 15.75));
        estoque.add(new Medicamento("Omeprazol 20mg", "EMS", 18.30));
        estoque.add(new Medicamento("Losartana 50mg", "Biolab", 13.40));
        estoque.add(new Medicamento("Dorflex", "Sanofi", 14.90));
        estoque.add(new Medicamento("Loratadina 10mg", "Germed", 9.60));
        System.out.println("8 medicamentos de exemplo carregados.");
    }

    private static int lerInt() {
        while (true) {
            try {
                return Integer.parseInt(sc.nextLine().trim());
            } catch (NumberFormatException e) {
                System.out.print("Valor invalido, digite um numero inteiro: ");
            }
        }
    }

    private static double lerDouble() {
        while (true) {
            try {
                return Double.parseDouble(sc.nextLine().trim().replace(",", "."));
            } catch (NumberFormatException e) {
                System.out.print("Valor invalido, digite um numero (ex: 12,90): ");
            }
        }
    }
}