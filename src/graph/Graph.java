package graph;

import dictionary.Word;

public interface Graph {
	public int n();																	//get the number of vertexes
	public int e();																	//get the number of edges
	
	public Edge first(int v);													//get the first edge for the vertex v
	public Edge next(Edge e);												//get the next edge for the edge e

	public boolean isEdge(int i, int j);									//true if this is an edge
	public boolean isEdge(Edge e);										//true if this is an edge
	
	public int v1(Edge e);														//where the edge comes from
	public int v2(Edge e);														//where the edge goes to
	
	public void setEdge(int i, int j, Word word);				//set an edge from vertex i to vertex j with a word
	public void setEdge(Edge e, Word word);					//set an edge with a word
	
	public Word getWord(int i, int j);									//get the word depending on the vertexes
	public Word getWord(Edge e);										//get the word depending on the edges
	
	public void setMark(int v, int val);									//set Mark for v
	public int getMark(int v);												//get Mark for v
}
