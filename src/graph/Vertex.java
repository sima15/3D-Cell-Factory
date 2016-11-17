package graph;

import java.util.ArrayList;
import java.util.Arrays;

public class Vertex {

 	int id;
 	int degree;
 	boolean isPipeCell;
 	
    double x;
    double y;
    double z;

    ArrayList<Edge> edges = new ArrayList<>();
    ArrayList<Vertex> adjList = new ArrayList<>();

    public Vertex(int id){
        this.id = id;
    }

    public Vertex(int id, double x, double y, double z){
        this.id = id;
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void setCoord(double x, double y, double z){
        this.x = x;
        this.y = y;
        this.z = z;

    }

    public void addEdge(Edge edge){
        edges.add(edge);
        degree++;
        addAdjVertex(getOpposite(this, edge), edge);
        getOpposite(this, edge).setDegree(getOpposite(this, edge).getDegree()+1);
        ArrayList<Edge> e = getOpposite(this, edge).getEdges();
        e.add(edge);
        getOpposite(this, edge).setEdges(e);
        getOpposite(this, edge).addAdjVertex(this, edge);
    }
    
    public void delEdge(Edge e){
    	Vertex opp = getOpposite(this, e);
    	if(!opp.equals(null)) {
    		//decrease opposite vertex's degree by 1
    		if (opp.getDegree()>0) opp.setDegree(opp.getDegree()-1);
    		//remove this node from the adjacent vertex's adjacency list
    		opp.getadjList().remove(this);
    		ArrayList<Edge> ed = opp.getEdges();
	    	//remove this edge from the opposite vertex's edge list
	    	if(null != ed){
	    		ed.remove(e);
	    		opp.setEdges(ed);
	    	}
    	}
		//decrease this vertex's degree by 1
    	if (getDegree()>0) setDegree(getDegree()-1);
		//remove this node from the this vertex's adjacency list
    	getadjList().remove(opp);
    	//remove this edge from the this vertex's edge list
    	if (!getEdges().isEmpty() && null != e) getEdges().remove(e);
    	e = null;
    }
    
    public Vertex getOpposite(Vertex v, Edge e){
    	if(e.getStartV().equals(v))
    		return e.getDestV();
    	else if(e.getDestV().equals(v))
    		return e.getStartV();
    	System.out.println("No opposite vertex found!");
    	return null;
    }

    public ArrayList<Edge> getEdges(){
        return edges;
    }
    
    public void setEdges(ArrayList<Edge> e){
        edges = e;
    }

    /**
     * Finds an edge between the current and the given vertex
     * @param end The vertex we need an edge from 
     * @return The edge between this and the given vertex
     */
    public Edge getEdge(Vertex end){
    	if(null != end.getEdges())
    	{for( Edge e: end.getEdges()){
    		if(this.equals(e.getStartV()) || this.equals(e.getDestV()))
    			return e;
    	}}
    	System.out.println("No such edge found. ");
    	return null;
    }
    
    public void addAdjVertex(Vertex v, Edge e){
        adjList.add(v);
    }
    
    public ArrayList<Vertex> getadjList(){
    	return adjList;
    }

    public double[] getcoord(){
        return new double[]{x, y, z};
    }

    public int getDegree(){
    	return degree;
    }
    
    public void setDegree(int d){
    	degree = d;
    }
    
    public int getId(){
        return id;
    }
    
    public void setPipeCell(){
    	isPipeCell = true;
    }
    
    public boolean isPipeCell(){
    	return isPipeCell;
    }
    
    @Override
    public String toString(){
    	return String.valueOf(id)+ " degree: "+ getDegree();
    }
}
