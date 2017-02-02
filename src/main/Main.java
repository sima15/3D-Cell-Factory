package main;

import java.util.ArrayList;

import data.DataRetrieval;
import data.WriteToFile;
import graph.Edge;
import graph.Graph;
import graph.Pruner;
import graph.Vertex;
import graph.CycleFinder;
import utils.XMLParser;

/**
 * A class that creates a 3D graph from a given .xml file and prunes and simplifies it 
 * to get a very simple, small graph. 
 * @author Sima Mehri
 *
 */
public class  Main {
	
	private static final String path = "C:\\Sima\\simulationimagesandagentstate\\agent_State\\agent_State\\";
	public static String agentFilePath = "agent_State(120).xml";
	
	public static void main(String[] args){

		XMLParser agentFileParser = new XMLParser(path + agentFilePath);
		DataRetrieval.extractAgentDetails(agentFileParser);
	 
        Graph primGraph = new Graph();
        ArrayList<Vertex> vertices = primGraph.createVertices();
        ArrayList<Edge> edges = primGraph.createEdges(vertices);
        new WriteToFile( primGraph, "data\\Graph.wrl");

        Graph pruned = new Pruner().startPruning(primGraph);
        new WriteToFile( pruned, "GraphPruned.wrl");
        
        CycleFinder cycleFinder = new CycleFinder(pruned);
        ArrayList<ArrayList<Edge>> cycles = cycleFinder.findCycles();
	 }

}
