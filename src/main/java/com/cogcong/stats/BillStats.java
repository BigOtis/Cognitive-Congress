package com.cogcong.stats;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import org.bson.Document;

import com.cogcong.mongo.MongoFacade;

public class BillStats {

	private MongoFacade mongo = MongoFacade.getInstance();
	
	Document stats;
	
	public BillStats(){
		// Load bill stats doc
		stats = mongo.db.getCollection("Stats")
				.find(new Document("type", "bill")).first();
	}
	
	public List<String> getTopWords(){
		return (List<String>) stats.get("topWords");
	}
	
	public Integer getWordCount(String word){
		Document statDoc = (Document) stats.get("topWordsCount");
		return statDoc.getInteger(word);
	}
	
	public List<String> getTopSubjects(){
		Document statDoc = (Document) stats.get("topSubjects");
		List<String> subjects = new ArrayList<>(statDoc.keySet());
		Collections.sort(subjects, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return getSubjectCount(s2).compareTo(getSubjectCount(s1));
			}
		});
		return subjects;
	}
	
	public Integer getSubjectCount(String subject){
		Document statDoc = (Document) stats.get("topSubjects");
		return statDoc.getInteger(subject);
	}
	
	/**
	 * Prints out basic bill stats info
	 * @param args
	 */
	public static void main(String args[]){
		BillStats bs = new BillStats();
		
		System.out.println("Top subjects: ");
		for(String ts : bs.getTopSubjects()){
			System.out.println(ts + ":\t" + bs.getSubjectCount(ts));
		}
		
		System.out.println("Top keywords: ");
		for(String tw : bs.getTopWords()){
			System.out.println(tw + ":\t" + bs.getWordCount(tw));
		}
	}
	
}
