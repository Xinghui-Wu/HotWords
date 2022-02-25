package dictionary;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;


public class Dictionary {
	// HashMap存储汉语词典
	private static HashMap<String, Word> dictionary = new HashMap<String, Word>();
	// HashMap存储TF-IDF预设词典
	private static HashMap<String, Double> IDF = new HashMap<String, Double>();
	// 汉语词典中的词语个数
	private static int id = 0;
	
	
	/*
	 * 系统启动初始化时预加载汉语词典和TF-IDF预设词典
	 */
	public static void initialize() {
		try {
			BufferedReader in;
			String line;
			
			// 读取汉语词典文件dictionary.txt
			in = new BufferedReader(new FileReader("dictionary.txt"));

			while ((line = in.readLine()) != null) {
				id++;
				String[] partition = line.split("\\s+");
				String string = partition[0];
				int weight = Integer.parseInt(partition[1]);
				String property = partition[2];
				
				// 向HashMap词典中插入一个词语
				Word word = new Word(id, string, weight, property);
				dictionary.put(string, word);
			}

			in.close();
			
			// 读取预设TF-IDF预设词典文件IDF.txt
			in = new BufferedReader(new FileReader("IDF.txt"));
			
			while ((line = in.readLine()) != null) {
				String[] partition = line.split("\\s+");
				String keyWord = partition[0];
				double idf = Double.parseDouble(partition[1]);
				IDF.put(keyWord, idf);
			}

			in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/*
	 * 在HashMap对象dictionary中查询词语
	 * 若词典中存在被查询词语，则返回一个Word类对象（需要对象克隆，而不能公共一个Word对象）
	 * 否则，返回null
	 */
	public static Word search(String string) {
		// 需要对象克隆
		Word result = dictionary.get(string);

		if (result != null) {
			Word word = new Word(result.getId(), string, result.getWeight(), result.getProperty());
			return word;
		}
		else {
			return null;
		}
	}


	/*
	 * 在HashMap对象IDF中查询关键词的IDF值
	 * 若IDF词典中存在被查找关键词，则返回对应的IDF值
	 * 否则，返回一个比IDF词典中最大IDF值稍大的值（max=13.900677652， min=2.81755097213）
	 */
	public static double getIDF(String word) {
		Double idf = IDF.get(word);

		if (idf != null) {
			return idf;
		}
		else {
			return 15.0;
		}
	}
}
