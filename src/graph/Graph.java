package graph;

import dictionary.Word;


public interface Graph {
	// Get the number of vertexes.
	public int n();
	// Get the number of edges.
	public int e();
	
	// Get the first edge for the vertex v.
	public Edge first(int v);
	// Get the next edge for the edge e.
	public Edge next(Edge e);

	// True if this is an edge.
	public boolean isEdge(int i, int j);
	// True if this is an edge.
	public boolean isEdge(Edge e);
	
	// Return where the edge comes from.
	public int v1(Edge e);
	// Return where the edge goes to.
	public int v2(Edge e);
	
	// Set an edge from vertex i to vertex j with a word.
	public void setEdge(int i, int j, Word word);
	// Set an edge from vertex i to vertex j with a word.
	public void setEdge(Edge e, Word word);
	
	// Get the word depending on the vertexes.
	public Word getWord(int i, int j);
	// Get the word depending on the edges
	public Word getWord(Edge e);
	
	// Set Mark for v.
	public void setMark(int v, int val);
	// Get Mark for v.
	public int getMark(int v);
}
