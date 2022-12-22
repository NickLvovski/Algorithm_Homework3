package ru.filatov;

public class Main {
    public static void main(String[] args) {
        Graph graph1 = new Graph();
        int[][] matrix1 = {
                {0,11,0,14,0,0,0,0,0},
                {0,0,4,0,0,0,0,0,0},
                {0,0,0,2,0,8,0,0,0},
                {0,0,0,0,23,0,0,0,0},
                {1,0,0,0,0,0,0,0,0},
                {0,0,0,0,0,0,2,0,0},
                {0,0,0,0,0,0,0,21,0},
                {0,0,0,0,0,0,0,0,3},
                {0,0,7,0,0,10,0,0,0}
        };

        graph1.fillByWeightMatrix(matrix1);
        Vertex vertex0 = graph1.getVertexes().get(0);
        graph1.printCycles(vertex0, 4);
        System.out.println();
        graph1.print();
    }
}
