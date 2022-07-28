package am.main;


/**
 * @Author: Greudyn Velasquez.
 * @FileName: UnaSolucion.java
 * @version: 0.1
 * @Description: Metodo para obtener la primera solucion al problema de las 8 reinas
 */
public class UnaSolucion {
    static final int N = 8;

    void imprimirSolucion(int[][] board) {
        for (int i = 0; i < N; i++) {
            for (int j = 0; j < N; j++)
                System.out.print(" " + board[i][j] + " ");
            System.out.println();
        }
    }

    boolean alternativaValida(int[][] board, int row, int col) {
        int i;
        int j;

        for (i = 0; i < col; i++)
            if (board[row][i] == 1)
                return false;

        for (i = row, j = col; i >= 0 && j >= 0; i--, j--)
            if (board[i][j] == 1)
                return false;

        for (i = row, j = col; j >= 0 && i < N; i++, j--)
            if (board[i][j] == 1)
                return false;

        return true;
    }

    boolean obtenerSiguienteAlternativa(int[][] board, int col) {
        if (col >= N)
            return true;
        for (int i = 0; i < N; i++) {
            if (alternativaValida(board, i, col)) {
                board[i][col] = 1;
                if (obtenerSiguienteAlternativa(board, col + 1))
                    return true;
                board[i][col] = 0;
            }
        }
        return false;
    }

    boolean inicializarAlternativas() {
        int[][] board = { { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 },
                { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, { 0, 0, 0, 0, 0, 0, 0, 0 }, };

        if (!obtenerSiguienteAlternativa(board, 0)) {
            System.out.print("No existe solucion");
            return false;
        }

        imprimirSolucion(board);
        return true;
    }

}
