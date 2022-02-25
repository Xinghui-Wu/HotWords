package graph;


public class EdgeL implements Edge {
	private int vertex1, vertex2;
	private Link itself;
	

	public EdgeL(int v1, int v2, Link it) {
		this.vertex1 = v1;
		this.vertex2 = v2;
		this.itself = it;
	}


	public int v1() {
		return vertex1;
	}


	public int v2() {
		return vertex2;
	}

	
	public Link getLink() {
		return itself;
	}
}
