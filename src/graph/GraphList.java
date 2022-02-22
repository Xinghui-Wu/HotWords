package graph;

public class GraphList extends LList {
	public Link currLink() {
		return this.curr;
	}
	
	public void setCurr(Link link) {
		this.curr = link;
	}
}
