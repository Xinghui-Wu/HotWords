package graph;

public class Link {
	private Object element;
	private Link next;
	

	public Link(Object it, Link next) {
		this.element = it;
		this.next = next;
	}
	

	public Link next() {
		return this.next;
	}
	

	public Link setNext(Link next) {
		return this.next = next;
	}
	

	public Object getElement() {
		return this.element;
	}
	
	
	public Object setElement(Object it) {
		return this.element = it;
	}
}
