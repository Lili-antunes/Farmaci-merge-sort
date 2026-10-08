import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class Mergesort {


    public static <T> void ordenar(List<T> a, Comparator<T> criterio) {
        if (a.size() < 2) {
            return;
        }
        mergeSort(a, 0, a.size() - 1, criterio);
    }

    private static <T> void mergeSort(List<T> a, int p, int r, Comparator<T> criterio) {
        if (p < r) {
            int q = (p + r) / 2;
            mergeSort(a, p, q, criterio);
            mergeSort(a, q + 1, r, criterio);
            merge(a, p, q, r, criterio);
        }
    }

    private static <T> void merge(List<T> a, int p, int q, int r, Comparator<T> criterio) {
        int n1 = q - p + 1;
        int n2 = r - q;

        List<T> L = new ArrayList<>(a.subList(p, q + 1));
        List<T> R = new ArrayList<>(a.subList(q + 1, r + 1));

        int i = 0;
        int j = 0;

        for (int k = p; k <= r; k++) {

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