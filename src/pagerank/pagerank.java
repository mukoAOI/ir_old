import java.util.*;

public class pagerank {
    private static final double DAMPING_FACTOR = 0.85;
    private static final double EPSILON = 1e-10;
    public static HashMap<Integer,Double> out_pagerank() {
        double[][] graph = {
                {0.0,0.4,0.0,0.1,0.2,0.3},
                {0.3,0.0,0.2,0.5,0.0,0.0},
                {0.1,0.1,0.0,0.4,0.2,0.1},
                {0.5,0.0,0.0,0.0,0.1,0.4},
                {0.0,0.1,0.8,0.0,0.0,0.1},
                {0.1,0.4,0.0,0.0,0.5,0.0}
        };
        int n = graph.length;
        int[] outDegree = new int[n];
        for (int i = 0; i < n; i++) {
            for (int j = 0; j < n; j++) {
                if (graph[i][j] > 0) {
                    outDegree[i]++;
                }
            }
        }
        double[] pageRank = new double[n];
        Arrays.fill(pageRank, 1.0 / n);
        boolean converged = false;
        int iterations = 0;
        while (!converged && iterations < 100) {
            double[] newPageRank = new double[n];
            double sum = 0;

            for (int j = 0; j < n; j++) {
                for (int i = 0; i < n; i++) {
                    if (graph[i][j] > 0) {
                        newPageRank[j] += pageRank[i] / outDegree[i];
                    }
                }
                newPageRank[j] = (1 - DAMPING_FACTOR) / n + DAMPING_FACTOR * newPageRank[j];
                sum += newPageRank[j];
            }
            for (int i = 0; i < n; i++) {
                newPageRank[i] += (1 - sum) / n;
            }
            converged = true;
            for (int i = 0; i < n; i++) {
                if (Math.abs(pageRank[i] - newPageRank[i]) > EPSILON) {
                    converged = false;
                    break;
                }
            }
            System.arraycopy(newPageRank, 0, pageRank, 0, n);
            iterations++;
        }
        HashMap<Integer,Double> outda=new HashMap<>();
        for (int i = 0; i < n; i++) {
            outda.put(i,pageRank[i]);
            System.out.println("PageRank of node " + i + ": " + pageRank[i]);
        }
        return outda;
    }


}
