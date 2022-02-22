package graph;

import dictionary.Word;

public class GraphL implements Graph {
	private GraphList[] vertex;
	private int numEdge;
	private int[] Mark;
	
	public GraphL(int n) {
		this.vertex = new GraphList[n];
		for(int i=0; i<n; i++)
			vertex[i] = new GraphList();
		this.numEdge = 0;
		this.Mark = new int[n];
	}

	public int n() {
		return vertex.length;
	}

	public int e() {
		return numEdge;
	}

	public Edge first(int v) {
		vertex[v].setFirst();
		if( vertex[v].currValue() == null )
			return null;
		return new EdgeL(v, ((Word)vertex[v].currValue()).getJ(), vertex[v].currLink());
	}

	public Edge next(Edge w) {
		vertex[w.v1()].setCurr( ((EdgeL)w).getLink() );
		vertex[w.v1()].next();
		if( vertex[w.v1()].currValue() == null )
			return null;
		return new EdgeL(w.v1(), ((Word)vertex[w.v1()].currValue()).getJ(), vertex[w.v1()].currLink());
	}

	public boolean isEdge(int i, int j) {
		GraphList temp = vertex[i];
		for( temp.setFirst(); (temp.currValue() != null)  &&  (((Word)temp.currValue()).getJ() < j); temp.next() );
		return (temp.currValue()!=null) && (((Word)temp.currValue()).getJ() == j);
	}
	
	public boolean isEdge(Edge w) {
		if(w == null)
			return false;
		vertex[w.v1()].setCurr( ((EdgeL)w).getLink() );
		if( !vertex[w.v1()].isInList() )
			return false;
		return ((Word)vertex[w.v1()].currValue()).getJ() == w.v2();
	}

	public int v1(Edge w) {
		return w.v1();
	}

	public int v2(Edge w) {
		return w.v2();
	}

	public void setEdge(int i, int j, Word word) {
		word.setI(i);
		word.setJ(j);
		if( isEdge(i, j) )
			vertex[i].setValue(word);
		else{
			vertex[i].insert(word);
			numEdge++;
		}
	}

	public void setEdge(Edge w, Word word) {
		if(w != null)
			setEdge(w.v1(), w.v2(), word);
	}

	public Word getWord(int i, int j) {
		if( isEdge(i, j) )
			return (Word) vertex[i].currValue();
		else
			return null;
	}

	public Word getWord(Edge w) {
		if( isEdge(w) )
			return (Word) vertex[w.v1()].currValue();
		else
			return null;
	}

	public void setMark(int v, int val) {
		Mark[v] = val;
	}

	public int getMark(int v) {
		return Mark[v];
	}

}
