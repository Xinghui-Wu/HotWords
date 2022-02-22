package graph;

public interface List {
	public void clear();
	public void insert(Object item);
	public void append(Object item);
	public Object remove();
	public void setFirst();
	public void next();
	public void previous();
	public int length();
	public void setPos(int pos);
	public void setValue(Object val);
	public Object currValue();
	public boolean isEmpty();
	public boolean isInList();
	public void print();
}
