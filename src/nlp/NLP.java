package nlp;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map.Entry;

import dictionary.Dictionary;
import dictionary.Word;


public class NLP {
	/*
	 * remained : a, ad, an, b, d, i, j, l, m, n, nr, nrfg, nrt, ns, nt, nz, s, t, v, vn, z
	 * not remained : ag(#), c, df, dg(#), e, f, g(#), h, k(#), mg(#), mq, ng(#), o, p, q, r, rg(#), rr, rz,
	 *                         tg(#), u, ud(#), ug(#), uj(#), ul(#), uv(#), uz(#),  vd(?),  vg(#), vi, vq, x(#), y, zg(#)
	 * not remained except # : c, df, e, f, h, mq, o, p, q, r, rr, rz, u, vd, vi, vq, y
	 *                         m, d, 
	 */
//	private static final String[] unuseful = {"c", "df", "e", "f", "h", "mq", "o", "p", "q", "r", "rr", "rz", "u", "vd", "vi", "vq", "y"};
	private static final String[] useful = {"n", "v", "ns", "vn", "nr", "t", "a", "l", "nz", "s", "nt", "i", "j", "b", "ad", "nrt", "an", "z", "nrfg"};
	private static int[] propertyCount = new int[useful.length];
	
	// 最终的分词个数
	private static int total;
	// 最终的分词结果列表
	private static ArrayList<Word> postWordList;
	// 接口函数nlp(String)返回对象，HashMap存储字符串及对应的TF*IDF值的键值对
	private static HashMap<String, Integer> word_TFIDF;
	
	
	/*
	 * 自然语言处理（改编版结巴分词算法）
	 */
	public static HashMap<String, Integer> nlp(String weibo) {
		// 重新创建类成员变量postWordList和word_TFIDF
		postWordList = new ArrayList<Word>();
		word_TFIDF = new HashMap<String, Integer>();
		
		// 生成有向无环图DAG
		DAG.generateDAG(weibo);
		// 计算词频最大分词路径
		DAG.getLongestPath();
		// 进行语句划分
		DAG.partition();
		// 将预分词结果进行进一步的短词语合并
		DAG.merge(postWordList);
		
		// 过滤所有单个字符（包括中文字符和所有非中文字符）
		filter(0);
		// 统计当前的分词总个数
		total = postWordList.size();
		// 进一步过滤不满足词性要求的字符
		filter(1);
		
		// 执行TF-IDF算法
		TF_IDF();

		// 返回HashMap<String, Integer>对象
		return word_TFIDF;
	}
	
	
	/*
	 * 筛选分词合并结果，过滤所有不满足要求的词语
	 * 参数mode选择初次过滤和二次过滤
	 * mode=0时，进行初次过滤，过滤到所有单个字符（包括中文字符和所有非中文字符）
	 * mode=1时，进一步过滤不满足词性要求的字符
	 */
	private static void filter(int mode) {
		// 进行初次过滤，过滤到所有单个字符（包括中文字符和所有非中文字符）
		if (mode == 0) {
			for (int i = 0; i < postWordList.size(); i++) {
				Word word = postWordList.get(i);
				String property = word.getProperty();

				if (property.equals("#")) {
					postWordList.remove(i);
					i--;
				}
			}
		}
		// 进一步过滤不满足词性要求的字符
		else {
			for (int i = 0; i  <postWordList.size(); i++) {
				Word word = postWordList.get(i);
				String property = word.getProperty();
				boolean isFiltered = true;
				
				// 遍历符合要求的词性表useful
				for (int k = 0; k < useful.length; k++) {
					if (property.equals(useful[k])) {
						propertyCount[k]++;
						isFiltered = false;
						break;
					}
				}
				
				// 判断当前词语是否需要被过滤
				if (isFiltered) {
					postWordList.remove(i);
					i--;
				}
			}
		}
	}
	
	
	/*
	 * TF-IDF算法抽取关键词
	 */
	private static void TF_IDF() {
		for (Word word: postWordList) {
			String thisWord = word.getWord();
			// int和double之间的转换因子为10000
			int IDF = (int) (Dictionary.getIDF(thisWord) / total * 10000);
			Integer currentIDF = word_TFIDF.get(thisWord);
			
			// 如果HashMap中尚未包含此词语
			if (currentIDF == null) {
				word_TFIDF.put(thisWord, IDF);
			}
			// 如果HashMap中已经包含此词语
			else {
				word_TFIDF.put(thisWord, currentIDF + IDF);
			}
		}
	}
	
	
	/*
	 * 测试输出
	 */
	public static void outputArrayList() {
		System.out.println("#####################");

		for (Word word: postWordList) {
			System.out.print(word.getWord());
			System.out.print(" ");
			System.out.print(word.getWeight());
			System.out.print(" ");
			System.out.print(word.getProperty());
			System.out.println();
		}

		System.out.println("#####################");
	}
	

	public static void outputHashMap() {
		System.out.println("#####################");

		for (Entry<String, Integer> entry: word_TFIDF.entrySet()) {
			System.out.print(entry.getKey());
			System.out.print(" ");
			System.out.print(entry.getValue());
			System.out.println();
		}

		System.out.println("#####################");
	}
	

	public static void outputPropertyCount() {
		System.out.println("#####################");

		for (int i = 0; i < propertyCount.length; i++) {
			System.out.print(useful[i]);
			System.out.print(" ");
			System.out.print(propertyCount[i]);
			System.out.println();
		}

		System.out.println("#####################");
	}
}
