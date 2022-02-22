package dictionary;

public class Word {
	private int i, j;								//有向无环图DAG中词语对应边的起点和终点标号
	
	private int id;								//词语在汉语词典中的编号
	private String word;					//词语字符串
	private int weight;						//词语的词频
	private String property;			//词语的词性
	
	public Word(int id, String word, int weight, String property) {
		this.id = id;
		this.word = word;
		this.weight = weight;
		this.property = property;
	}

	public int getI() {
		return i;
	}

	public void setI(int i) {
		this.i = i;
	}

	public int getJ() {
		return j;
	}

	public void setJ(int j) {
		this.j = j;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

	public String getProperty() {
		return property;
	}

	public void setProperty(String property) {
		this.property = property;
	}

}
