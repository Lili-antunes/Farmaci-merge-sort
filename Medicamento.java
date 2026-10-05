public class Medicamento {

    private String nome;
    private String fabricante;
    private double preco;

    public Medicamento(String nome, String fabricante, double preco) {
        this.nome = nome;
        this.fabricante = fabricante;
        this.preco = preco;
    }

    public String getNome() { return nome; }
    public String getFabricante() { return fabricante; }
    public double getPreco() { return preco; }

    @Override
    public String toString() {
        return String.format("%-22s | %-15s | R$ %8.2f", nome, fabricante, preco);
    }
}