package ru.filatov;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Graph {
    private List<Vertex> vertexes;
    private List<Edge> edges;

    public Graph() {
        vertexes = new ArrayList<>();
        edges = new ArrayList<>();
    }

    public Graph(List<Vertex> vertexes, List<Edge> edges) {
        this.vertexes = vertexes;
        this.edges = edges;
    }

    public List<Vertex> getVertexes() {
        return vertexes;
    }

    public void setVertexes(List<Vertex> vertexes) {
        this.vertexes = vertexes;
    }

    public List<Edge> getEdges() {
        return edges;
    }

    public void setEdges(List<Edge> edges) {
        this.edges = edges;
    }

    public int First(Vertex vertex){
        for(Edge e : edges){
            if(e.getFrom() == vertex){
                int number = e.getTo().getNumber();
                return number;
            }
        }
        return 0;
    }

    public int Next(Vertex vertex, int i){
        boolean checked = false;

        for (Edge e : edges){
            if (e.getFrom() == vertex && checked)
                return e.getTo().getNumber();
            if (e.getFrom() == vertex && i == e.getTo().getNumber())
                checked = true;
        }
        return 0;
    }

    public List<Vertex> Vertex(Vertex vertex){
        List<Vertex> vertexes = new LinkedList<>();

        for(Edge edge : edges){
            if(edge.getFrom() == vertex)
                vertexes.add(edge.getTo());
        }
        return vertexes;
    }

    public void addVertex(Vertex vertex){
        vertexes.add(vertex);
    }

    public void addEdge(Edge edge){
        edges.add(edge);
    }

    public void deleteVertex(String name){
        for (int i = 0; i < vertexes.size(); i++) {
            if(vertexes.get(i).getName().equals(name)){
                for (int j = 0; j < edges.size(); j++) {
                    if(vertexes.get(i) == edges.get(i).getTo() && vertexes.get(i) == edges.get(j).getFrom())
                        edges.remove(j);
                    vertexes.remove(i);
                }
            }
        }
    }

    public void deleteEdge(Vertex to, Vertex from){
        for (int i = 0; i < edges.size(); i++) {
            if(to == edges.get(i).getTo() && from == edges.get(i).getFrom())
                edges.remove(i);
        }
    }

    public void editVertex(String name, int number){
        for (int i = 0; i < vertexes.size(); i++) {
            if(vertexes.get(i).getName().equals(name))
                vertexes.get(i).setNumber(number);
        }
    }

    public void editEdge(Vertex to, Vertex from, int weight){
        for (int i = 0; i < edges.size(); i++) {
            if(to == edges.get(i).getTo() && from == edges.get(i).getFrom())
                edges.get(i).setWeight(weight);
        }
    }

    public void fillByWeightMatrix(int[][] weights){
        for(int i = 0; i < weights.length; i++){
            addVertex(new Vertex(String.valueOf(i), i));
        }
        for(int i = 0; i < weights.length; i++) {
            for(int j = 0; j < weights.length; j++){
                if (weights[i][j] != 0){
                    Edge edge = new Edge(vertexes.get(i), vertexes.get(j), weights[i][j]);
                    addEdge(edge);
                }
            }
        }
    }

    public List<Vertex> BFS(Vertex start){
        List<Vertex> list = privateBFS(start);

        for (var item : vertexes){
            if (!list.contains(item))
                list.addAll(privateBFS(item));
        }
        return list;
    }

    @NotNull
    private List<Vertex> privateBFS(Vertex start){
        List<Vertex> list = new ArrayList<>();
        Vertex vertex = start;
        list.add(vertex);

        for(int i = 0; i < list.size(); i++){
            vertex = list.get(i);
            for(var item : Vertex(vertex))
                if (!list.contains(item))
                    list.add(item);
        }

        return list;
    }

    public void print() {
        for (Vertex vertex : vertexes){
            System.out.printf("%s: ", vertex.getName());
            for (Edge edge : edges){
                if(edge.getFrom() == vertex)
                    System.out.printf("-%d ", edge.getWeight());
                else if (edge.getTo() == vertex)
                    System.out.printf("%d ", edge.getWeight());
                else
                    System.out.print("0 ");
            }
            System.out.println();
        }
    }

    public void printCycles(Vertex start, int length){
        boolean hasCycles = false;
        if (length < 0)
            System.out.println("none");
        List<Vertex> neighbors = Vertex(start);
        String startNumber = String.format("%d", start.getNumber());

        for(Vertex neighbor : neighbors){
            String result = findCycles(start, neighbor, length-1, startNumber);

            if (!result.contains("none")){
                hasCycles = true;
                System.out.printf(result + "-%d\n",start.getNumber());
            }
        }
        if(!hasCycles)
            System.out.println("none");
    }

    private String findCycles(Vertex origin, Vertex start, int length, String result){
        if (length < 0)
            return "none";

        result += String.format("-%d", start.getNumber());
        List<Vertex> neighbors = Vertex(start);
        if (neighbors.isEmpty())
            return "none";

        for(Vertex neighbor : neighbors){
            if (neighbor == origin){
                if (length == 0)
                    return result;
                else
                    return "none";
            }
            return findCycles(origin, neighbor, length-1, result);
        }
        return " ";
    }
}
