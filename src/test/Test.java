package test;

import dictionary.Dictionary;
import nlp.DAG;
import nlp.NLP;


public class Test {
	public static void main(String[] args) {
		// 系统启动初始化时预加载汉语词典
		Dictionary.initialize();

		String weibo = "去西安交通大学学习";
		
		NLP.nlp(weibo);
		DAG.outputWordList(0);
		NLP.outputArrayList();
		NLP.outputHashMap();
		NLP.outputPropertyCount();
		
//		double idf = Dictionary.getIDF("@@@");
//		System.out.println("idf = " + idf);
		
//		Word word = Dictionary.search("西北工业");
//		if(word == null)
//			System.out.println("null");
//		else
//			System.out.println(word.getWord() + "    &    id = " + word.getId());
		
//		long start = System.nanoTime();
//		long end = System.nanoTime();
//		double duration = (end-start) / 1000000.0;
//		System.out.println("duration = " + duration);
	}
}
