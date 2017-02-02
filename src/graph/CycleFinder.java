/**
 * 
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Sima Mehri
 * @since 1/31/2017
 */
public class CycleFinder {

	private Graph graph;
	private ArrayList<ArrayList<Edge>> cycles;
	private ArrayList<Vertex> visited;
	private ArrayList<Edge> treeEdge;
	private ArrayList<Edge> backEdge;
	
	public CycleFinder(Graph g){
		graph = g;
		cycles = new ArrayList<ArrayList<Edge>>();
		treeEdge = new ArrayList<Edge>();
		backEdge = new ArrayList<Edge>();
		visited = new ArrayList<Vertex>();
	}
	
	/**
	 * Finds the minimal cycles inside a connected undirected graph
	 */
	public ArrayList<ArrayList<Edge>> findCycles() {
		ArrayList<Edge> cycle;
		Edge temp;
		for(Vertex v1: graph.getVertices()){
			if(!visited.contains(v1)) visited.add(v1);
			if(v1.getDegree()>0)
				for(Vertex v2: v1.getadjList() ){
					if(visited.contains(v2)){
						temp = graph.getEdge(v1, v2);
						backEdge.add(temp);
						cycle = new ArrayList<Edge>();
						cycle.add(temp);
						cycle.addAll(findMinPath(v2, v1, temp));
						cycles.add(cycle);
						
					}else{
						temp = graph.getEdge(v1, v2);
						visited.add(v2);
						treeEdge.add(temp);
					}
						
				}
		}
		return cycles;
	}
	
	/**
	 * Finds a path between the given two nodes which consists of fewer edges
	 * @param src Source node
	 * @param dest Destination node
	 * @return A list of edges found in the min path
	 */
	private ArrayList<Edge> findMinPath(Vertex src, Vertex dest, Edge backEdge){
		graph.removeEdge(backEdge); 
		Vertex curr;
		/**
		 * A set including vertices with the shortest path to the source
		 */
		Set<Vertex> settledV = new HashSet<Vertex>();
		/**
		 * A hashmap including vertices visited so far and their distance from the source
		 */
		HashMap<Vertex, Double> srcDists = new HashMap<Vertex, Double>();
		/**
		 * A hashmap including vertices visited so far and the path to them
		 */
		HashMap<Vertex, ArrayList<Edge>> paths = new HashMap<Vertex, ArrayList<Edge>>();
		settledV.add(src);
		curr = src;
		srcDists.put(src, 0.0);
		int loopLength = graph.getVertices().size();
		ArrayList<Edge> temp;
		for(int i=1;  i<= loopLength; i++){
			for(Vertex v: curr.getadjList()){
				//Adding adjacent vertices of the current vertex to visited vertices map
				srcDists.put(v, srcDists.get(curr)+ graph.getEdgeWeight(v, curr) );
				//Adding adjacent vertices of the paths map
				temp = paths.get(curr);
				temp.add(graph.getEdge(v, curr));
				paths.put(v, temp);
			}
			double min = 10000;
			//Finding a visited vertex with the shortest path from the source
			// and putting it into settled vertices (settledV) set
			for( Vertex v: srcDists.keySet()){
				if(!settledV.contains(v))
					if(srcDists.get(v)<= min){
						min = srcDists.get(v);
						curr = v;
					}
			}
			settledV.add(curr);
			//Updating path lengths in the visited vertices to shorter ones
			for( Vertex v: srcDists.keySet()){
				if(!settledV.contains(v))
					if(graph.getEdge(v, curr) != null)
						if( srcDists.get(v) > srcDists.get(curr)+ graph.getEdgeWeight(v, curr)){
							srcDists.put(v, srcDists.get(curr)+ graph.getEdgeWeight(v, curr));
							ArrayList<Edge> temp2 = paths.get(v);
							temp2.add(graph.getEdge(v, curr));
							paths.put(v, temp2);
						}
			}
			//Checks if the destination vertex is among the visited nodes
			if(srcDists.containsKey(dest)){
				return paths.get(dest);
			}
		}
		System.out.println("Destination node not found!");
		return null;
	}
}
