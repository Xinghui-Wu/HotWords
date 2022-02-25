package graph;

public class LList implements List {
	private Link head;
	private Link tail;
	protected Link curr;
	

	public LList() {
		tail = head = curr = new Link(null, null);
	}


	public void clear() {
		head.setNext(null);
		curr = tail = head;
	}


	public void insert(Object item) {
		if (item == null) {
			return;
		}

		curr.setNext(new Link(item, curr.next()));

		if (tail == curr) {
			tail = curr.next();
		}
	}


	public void append(Object item) {
		tail.setNext(new Link(item, null));
		tail = tail.next();
	}


	public Object remove() {
		if (!isInList()) {
			return null;
		}

		Object it = curr.next().getElement();

		if (tail == curr.next()) {
			tail = curr;
		}

		curr.setNext(curr.next().next());

		return it;
	}


	public void setFirst() {
		curr = head;
	}


	public void next() {
		if (curr != null) {
			curr = curr.next();
		}
	}


	public void previous() {
		if (curr == null || curr == head) {
			curr = null;
			return;
		}

		Link temp = head;

		while (temp != null && temp.next() != curr) {
			temp = temp.next();
		}

		curr = temp;
	}


	public int length() {
		int cnt = 0;

		for (Link temp = head.next(); temp != null; temp = temp.next()) {
			cnt++;
		}

		return cnt;
	}


	public void setPos(int pos) {
		curr = head;

		for (int i = 0; curr != null && i < pos; i++) {
			curr = curr.next();
		}
	}


	public void setValue(Object val) {
		if (!isInList()) {
			return;
		}

		curr.next().setElement(val);
	}


	public Object currValue() {
		if (!isInList()) {
			return null;
		}

		return curr.next().getElement();
	}


	public boolean isEmpty() {
		return head.next() == null;
	}


	public boolean isInList() {
		return curr != null && curr.next() != null;
	}


	public void print() {
		if (isEmpty()) {
			System.out.println("Empty List!");
		}
		else {
			System.out.print("(");

			for (setFirst(); isInList(); next()) {
				System.out.print(currValue() + "");
			}

			System.out.println(")");
		}
	}
}
