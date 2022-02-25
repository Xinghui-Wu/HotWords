package trie;


/*
 * 三向单词查找树
 */
public class TST<Value> {
	// 私有结点类
	private class Node {
		// 字符
		char c;
		// 左中右子三向单词查找树
		Node left, mid, right;
		// 和字符串相关联的值
		Value val;
	}
	
	// 三向单词查找树的根结点
	private Node root;
	

	/*
	 * 三向单词查找树中的查找操作
	 */
	public Value get(String key) {
		Node x = get(root, key, 0);
		
		if(x == null) {
			return null;
		}
		
		return (Value) x.val;
	}


	private Node get(Node x, String key, int d) {
		if (x == null) {
			return null;
		}

		char c = key.charAt(d);

		if (c < x.c) {
			return get(x.left, key, d);
		}
		else if (c > x.c) {
			return get(x.right, key, d);
		}
		else if (d < key.length() - 1) {
			return get(x.mid, key, d + 1);
		}
		else {
			return x;
		}
	}
	
	
	/*
	 * 三向单词查找树中的插入操作
	 */
	public void put(String key, Value val) {
		root = put(root, key, val, 0);
	}
	

	private Node put(Node x, String key, Value val, int d) {
		char c = key.charAt(d);

		if (x == null) {
			x = new Node();
			x.c = c;
		}

		if (c < x.c) {
			x.left = put(x.left, key, val, d);
		}
		else if (c > x.c) {
			x.right = put(x.right, key, val, d);
		}
		else if (d < key.length() - 1) {
			x.mid = put(x.mid, key, val, d+1);
		}
		else {
			x.val = val;
		}
		
		return x;
	}
}
