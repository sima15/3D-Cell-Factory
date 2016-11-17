package graph;

public class Edge {

	int id;
	Vertex startV;
    Vertex destV;
    double weight;

    /**
     * Builds an Edge for a graph
     * @param id Unique ID of an edge in the graph
     * @param s The starting vertex
     * @param d The ending vertex
     */
    public Edge(int id, Vertex s, Vertex d){
        this.id = id;
    	startV = s;
        destV = d;
        calWeight();
        startV.addEdge(this);
    }
    
    
    @Override
    public String toString(){
    	return id + " v1: "+startV + " v2: "+ destV+ " Weight: "+ weight + ", " ;
    }
    
    public int getID(){
    	return id;
    }
    
    public Vertex getStartV(){
    	return startV;
    }
    
    public Vertex getDestV(){
    	return destV;
    }
    
    public void calWeight(){
    	weight = new Graph().calDistance(this.getStartV(), this.getDestV());
    }
    
    public void calWeight(Graph g){
    	weight = g.calDistance(this.getStartV(), this.getDestV());
    }
    
    public double getWeight(){
    	return weight;
    }
    
    public void setWeight(double w){
    	weight = w;
    }
    
}
