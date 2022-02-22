package nlp;

import java.util.ArrayList;

import dictionary.Dictionary;
import dictionary.Word;
import graph.Edge;
import graph.GraphL;

public class DAG {
	private static int n;														//微博中字符个数
	private static GraphL dag;												//有向无环图DAG
	
	private static int[] partition;											//微博的划分位置（每个词的词首位置）
	private static int count;													//微博的划分个数
	
	private static ArrayList<Word> preWordList;				//中文语句预分词结果列表
	
	
	/*
	 * 构建有向无环图DAG
	 */
	public static void generateDAG(String weibo) {
		n = weibo.length();							//微博中的字符个数
		dag = new GraphL(n+1);					//额外添加句末结束标记
		
		/*
		 * n个字符，标号0~n-1
		 * 外层for循环，i从0遍历到n-2，即从句中第一个字符至倒数第二个字符
		 */
		for(int i=0; i<n-1; i++) {
			//单个字符：建立当前字符至下一个字符的一条有向边（0→1、1→2、...、n-2→n-1）
			Word character = new Word(0, weibo.substring(i, i+1), 0, "#");
			dag.setEdge(i, i+1, character);
			
			/*
			 * 固定起始位置i，结束位置j，遍历后续子字符串进行组词
			 * 内层for循环，j取自i+1至n-1，即字符i的下一个字符至最后一个字符
			 */
			for(int j=i+1; j<n; j++) {
				String subString = weibo.substring(i, j+1);					//从i到j截取weibo字符串
				Word word = Dictionary.search(subString);					//在预设字典中查询词组，得到Word类对象word
				//若截取的子字符串可以构成词组，即word != null时，在DAG中建立一条从i到j+1的有向边（当前词的词首指向下一个词的词首），该边存储word对象
				if(word != null)
					dag.setEdge(i, j+1, word);
			}
		}
		
		//最后一个单个字符：建立最后一个字符至空结点的一条有向边，完成整个有向无环图
		Word character = new Word(0, weibo.substring(n-1, n), 0, "#");
		dag.setEdge(n-1, n, character);
	}
	
	
	
	/*
	 * 在有向无环图DAG中寻找词频最大路径
	 * 一个句子中的所有字符结点自然构成拓扑序
	 * 按顺序对拓扑序列中的每个结点u执行以下算法：
	 * 对u的每个邻接点，即(u,v)是DAG中的一条有向边
	 * if(distance[u] + weight(u,v) > distance[v])
	 *     dag.setMark(v, u);
	 * 		distance[v] = distance[u] + weight(u,v)
	 * 算法复杂度为O(|V|+|E|)
	 */
	public static int getLongestPath() {
		//初始化distance数组
		int[] distance = new int[n+1];
		for(int i=1; i<n+1; i++)
			distance[i] = Integer.MIN_VALUE;
		
		//遍历有向无环图DAG的结点
		for(int u=0; u<n; u++) {
			for(Edge e=dag.first(u); e!=null; e=dag.next(e)) {
				int v = e.v2();
				int weight = dag.getWord(u, v).getWeight();
				if(distance[u] + weight > distance[v]) {
					dag.setMark(v, u);
					distance[v] = distance[u] + weight;
				}
			}
		}
		return distance[n];
	}
	
	
	
	/*
	 * 构建并保存语句划分
	 * 根据划分位置，划分语句至若干个词
	 */
	public static void partition() {
		preWordList = new ArrayList<Word>();
		
		//构建并保存语句划分
		partition = new int[n+1];
		count = 0;
		partition[0] = n;
		for(int i=dag.getMark(n); i>0; i=dag.getMark(i)) {
			partition[count+1] = i;
			count++;
		}
		partition[count+1] = 0;
		count++;
		
		//根据划分位置划分语句至若干个词
		for(int i=count; i>0; i--) {
			Word word = dag.getWord(partition[i], partition[i-1]);
			preWordList.add(word);
		}
	}
	
	
	
	/*
	 * 将预分词结果进行进一步的短词语合并
	 */
	public static void merge(ArrayList<Word> postWordList) {
		for(int i=0; i<count; ) {
			Word thisWord = preWordList.get(i);
			String[] longerString = new String[count-i];
			boolean isMerged = false;
			
			longerString[0] = thisWord.getWord();
			for(int j=i+1; j<count; j++)
				longerString[j-i] = longerString[j-i-1] + preWordList.get(j).getWord();
			for(int j=count-1; j>i; j--) {
				Word longerWord = Dictionary.search(longerString[j-i]);
				if(longerWord != null) {
					postWordList.add(longerWord);
					isMerged = true;
					i = j + 1;
					break;
				}
			}
			if(!isMerged) {
				postWordList.add(thisWord);
				i++;
			}
		}
//			String longerString = thisWord.getWord();
//			for(int j=i+1; j<count; j++) {
//				longerString = longerString + preWordList.get(j).getWord();
//				Word longerWord = Dictionary.search(longerString);
//				//当前词语可以和后续词语合并
//				if(longerWord != null) {
//					thisWord = longerWord;
//					if(j == count-1) {
//						postWordList.add(thisWord);
//						i = count;
//						break;
//					}
//				}
//				//当前词语不能和后续词语合并
//				else {
//					postWordList.add(thisWord);
//					if(j == count-1) {
//						postWordList.add(preWordList.get(j));
//						i = count;
//					}
//					else
//						i = j;
//					break;
//				}
//			}
	}
	
	
	
	
	
	/*
	 * for test
	 */
	public static void outputDAG() {
		for(int i=0; i<n; i++) {
			for(int j=i+1; j<n+1; j++) {
				Word word = dag.getWord(i, j);
				if(word != null) {
					System.out.print(i);
					System.out.print(" & ");
					System.out.print(j);
					System.out.print(" = ");
					System.out.print(word.getWord());
					System.out.print(word.getWeight());
					System.out.print("      ");
				}
			}
			System.out.println();
		}
	}
	
	public static void outputMark() {
		for(int i=0; i<n+1; i++) {
			System.out.print(i);
			System.out.print("  ");
			System.out.print(dag.getMark(i));
			System.out.print("  ");
			System.out.print(partition[i]);
			System.out.println();
		}
	}
	
	public static void outputWordList(int option) {
		if(option == 0) {
			for(Word word: preWordList) {
				System.out.print(word.getWord());
				System.out.print(" ");
				System.out.print(word.getWeight());
				System.out.print(" ");
				System.out.print(word.getProperty());
				System.out.println();
			}
		}
	}

}
