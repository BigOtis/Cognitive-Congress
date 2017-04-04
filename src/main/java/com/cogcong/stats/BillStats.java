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
	
	/*
	 * TOP SUBJECTS PER PARTY
	 *
	 */
	public List<String> getTopRepSubjects(){
		return getTopFromMap("rsubjects");
	}
	
	public Integer getSubjectRepCount(String subject){
		Document statDoc = (Document) stats.get("rsubjects");
		return statDoc.getInteger(subject);
	}
	
	public List<String> getTopDemSubjects(){
		return getTopFromMap("dsubjects");
	}
	
	public Integer getSubjectDemCount(String subject){
		Document statDoc = (Document) stats.get("dsubjects");
		return statDoc.getInteger(subject);
	}
	
	public List<String> getTopIndSubjects(){
		return getTopFromMap("isubjects");
	}
	
	public Integer getSubjectIndCount(String subject){
		Document statDoc = (Document) stats.get("isubjects");
		return statDoc.getInteger(subject);
	}
	
	/*
	 * TOP KEYWORDS PER PARTY
	 */
	public List<String> getTopRepKeywords(){
		return getTopFromMap("rkeywords");
	}
	
	public Integer getKeywordsRepCount(String subject){
		Document statDoc = (Document) stats.get("rkeywords");
		return statDoc.getInteger(subject);
	}
	
	public List<String> getTopDemKeywords(){
		return getTopFromMap("dkeywords");
	}
	
	public Integer getKeywordsDemCount(String subject){
		Document statDoc = (Document) stats.get("dkeywords");
		return statDoc.getInteger(subject);
	}
	
	public List<String> getTopIndKeywords(){
		return getTopFromMap("ikeywords");
	}
	
	
	public Integer getKeywordsIndCount(String subject){
		Document statDoc = (Document) stats.get("ikeywords");
		return statDoc.getInteger(subject);
	}
	
	/*
	 * COMBINED TOP WORDS
	 */
	@SuppressWarnings("unchecked")
	public List<String> getTopWords(){
		return (List<String>) stats.get("topWords");
	}
	
	public Integer getWordCount(String word){
		Document statDoc = (Document) stats.get("topWordsCount");
		return statDoc.getInteger(word);
	}
	
	public List<String> getTopSubjects(){
		return getTopFromMap("topSubjects");
	}
	
	private List<String> getTopFromMap(String mapkey){
		Document statDoc = (Document) stats.get(mapkey);
		List<String> subjects = new ArrayList<>(statDoc.keySet());
		Collections.sort(subjects, new Comparator<String>() {
			@Override
			public int compare(String s1, String s2) {
				return getSubjectCount(s2, statDoc).compareTo(getSubjectCount(s1, statDoc));
			}
		});
		return subjects;
	}
	
	public Integer getSubjectCount(String subject){
		Document statDoc = (Document) stats.get("topSubjects");
		return getSubjectCount(subject, statDoc);
	}
	
	public Integer getSubjectCount(String subject, Document statDoc){
		return statDoc.getInteger(subject);
	}
	
}
