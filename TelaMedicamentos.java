import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class TelaMedicamentos extends JFrame {

    private final List<Medicamento> estoque = new ArrayList<>();
    private boolean exemplosCarregados = false;

    private final JTextField campoNome = new JTextField(14);
    private final JTextField campoFabricante = new JTextField(12);
    private final JTextField campoPreco = new JTextField(7);

    private final DefaultTableModel modelo = new DefaultTableModel(
            new String[]{"#", "Nome", "Fabricante", "Preco"}, 0) {
        @Override
        public boolean isCellEditable(int row, int col) {
            return false; // tabela somente leitura
        }
    };
    private final JLabel status = new JLabel("Nenhum medicamento cadastrado.");

    public TelaMedicamentos() {
        super("Cadastro de Medicamentos");
        setDefaultCloseOperation(EXIT_ON_CLOSE);
        setLayout(new BorderLayout(8, 8));

        JPanel form = new JPanel(new FlowLayout(FlowLayout.LEFT));
        form.add(new JLabel("Nome:"));
        form.add(campoNome);
        form.add(new JLabel("Fabricante:"));
        form.add(campoFabricante);
        form.add(new JLabel("Preco (ex: 12,90):"));
        form.add(campoPreco);
        JButton btnCadastrar = new JButton("Cadastrar");
        btnCadastrar.addActionListener(e -> cadastrar());
        form.add(btnCadastrar);
        add(form, BorderLayout.NORTH);

        JTable tabela = new JTable(modelo);
        tabela.getColumnModel().getColumn(0).setMaxWidth(40);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        JPanel rodape = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton btnNome = new JButton("Ordenar por nome (A-Z)");
        btnNome.addActionListener(e ->
                ordenar("nome", Comparator.comparing(m -> m.getNome().toLowerCase())));
        JButton btnPreco = new JButton("Ordenar por preco");
        btnPreco.addActionListener(e ->
                ordenar("preco", Comparator.comparingDouble(Medicamento::getPreco)));
        JButton btnExemplos = new JButton("Carregar exemplos");
        btnExemplos.addActionListener(e -> carregarExemplos());
        rodape.add(btnNome);
        rodape.add(btnPreco);
        rodape.add(btnExemplos);
        rodape.add(status);
        add(rodape, BorderLayout.SOUTH);

        setSize(780, 420);
        setLocationRelativeTo(null);
    }

    private void cadastrar() {
        String nome = campoNome.getText().trim();
        String fabricante = campoFabricante.getText().trim();
        if (nome.isEmpty() || fabricante.isEmpty()) {
            aviso("Preencha o nome e o fabricante.");
            return;
        }
        double preco;
        try {
            preco = Double.parseDouble(campoPreco.getText().trim().replace(",", "."));
        } catch (NumberFormatException ex) {
            aviso("Preco invalido. Use um numero, por exemplo 12,90.");
            return;
        }
        estoque.add(new Medicamento(nome, fabricante, preco));
        campoNome.setText("");
        campoFabricante.setText("");
        campoPreco.setText("");
        campoNome.requestFocus();
        atualizarTabela("Medicamento cadastrado! Total: " + estoque.size());
    }

    private void ordenar(String campo, Comparator<Medicamento> criterio) {
        if (estoque.isEmpty()) {
            aviso("Nenhum medicamento cadastrado.");
            return;
        }
        Mergesort.ordenar(estoque, criterio); // Merge Sort do grupo
        atualizarTabela("Ordenado por " + campo + " usando Merge Sort.");
    }

    private void carregarExemplos() {
        if (exemplosCarregados) {
            aviso("Os exemplos ja foram carregados.");
            return;
        }
        estoque.add(new Medicamento("Dipirona 500mg", "EMS", 8.90));
        estoque.add(new Medicamento("Amoxicilina 500mg", "Eurofarma", 24.50));
        estoque.add(new Medicamento("Paracetamol 750mg", "Medley", 11.20));
        estoque.add(new Medicamento("Ibuprofeno 400mg", "Neo Quimica", 15.75));
        estoque.add(new Medicamento("Omeprazol 20mg", "EMS", 18.30));
        estoque.add(new Medicamento("Losartana 50mg", "Biolab", 13.40));
        estoque.add(new Medicamento("Dorflex", "Sanofi", 14.90));
        estoque.add(new Medicamento("Loratadina 10mg", "Germed", 9.60));
        exemplosCarregados = true;
        atualizarTabela("8 medicamentos de exemplo carregados.");
    }

    private void atualizarTabela(String mensagem) {
        modelo.setRowCount(0);
        for (int i = 0; i < estoque.size(); i++) {
            Medicamento m = estoque.get(i);
            modelo.addRow(new Object[]{
                    i + 1, m.getNome(), m.getFabricante(),
                    String.format("R$ %.2f", m.getPreco())});
        }
        status.setText(mensagem);
    }

    private void aviso(String texto) {
        JOptionPane.showMessageDialog(this, texto, "Aviso", JOptionPane.WARNING_MESSAGE);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
            } catch (Exception ignored) { }
            new TelaMedicamentos().setVisible(true);
        });
    }
}