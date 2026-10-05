import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * Merge Sort generico, desenvolvido seguindo os 3 passos da aula:
 *  - Dividir:    quebra o vetor A[p..r] em duas metades (A[p..q] e A[q+1..r])
 *  - Conquistar: ordena as duas metades recursivamente
 *  - Combinar:   junta as duas metades ordenadas (merge)
 *
 * O Comparator define o criterio (nome, preco, validade...), entao o mesmo
 * algoritmo serve para ordenar por qualquer campo.
 * Complexidade de tempo: O(n log n).
 */
public class Mergesort {

    /** Ponto de entrada: ordena a lista inteira. */
    public static <T> void ordenar(List<T> a, Comparator<T> criterio) {
        if (a.size() < 2) {
            return; // vetor vazio ou com 1 elemento ja esta ordenado
        }
        mergeSort(a, 0, a.size() - 1, criterio);
    }

    // DIVIDIR + CONQUISTAR
    private static <T> void mergeSort(List<T> a, int p, int r, Comparator<T> criterio) {
        if (p < r) { // caso base: p == r (1 elemento) e resolvido diretamente
            int q = (p + r) / 2;              // dividir
            mergeSort(a, p, q, criterio);     // conquistar metade esquerda
            mergeSort(a, q + 1, r, criterio); // conquistar metade direita
            merge(a, p, q, r, criterio);      // combinar
        }
    }

    // COMBINAR
    // Entrada: A[p..q] e A[q+1..r] ja ordenados. Objetivo: ordenar A[p..r].
    private static <T> void merge(List<T> a, int p, int q, int r, Comparator<T> criterio) {
        int n1 = q - p + 1;
        int n2 = r - q;

        // vetores auxiliares L e R (copias das duas metades)
        List<T> L = new ArrayList<>(a.subList(p, q + 1));
        List<T> R = new ArrayList<>(a.subList(q + 1, r + 1));

        int i = 0; // proximo elemento ainda nao explorado de L
        int j = 0; // proximo elemento ainda nao explorado de R

        for (int k = p; k <= r; k++) {
            // compara os dois primeiros de cada metade.
            // (no lugar do "infinito" do pseudocodigo, checamos se uma metade acabou)
            boolean pegaEsquerda = (i < n1) && (j >= n2 || criterio.compare(L.get(i), R.get(j)) <= 0);
            if (pegaEsquerda) {
                a.set(k, L.get(i));
                i++;
            } else {
                a.set(k, R.get(j));
                j++;
            }
        }
    }
}