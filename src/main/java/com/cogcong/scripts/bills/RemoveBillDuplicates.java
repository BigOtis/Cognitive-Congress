package com.cogcong.scripts.bills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.cogcong.mongo.MongoFacade;
import com.mongodb.client.MongoCollection;


public class RemoveBillDuplicates {

	public static void main(String args[]){
		
		MongoFacade mongo = new MongoFacade();
		MongoCollection<Document> wbills = mongo.db.getCollection("WatsonBills");
		
		Map<String, Integer> wordCount = new HashMap<>();	
		int i = 0;
		
		for(Document doc : wbills.find()){
			if((i++) % 1000 == 0){
				System.out.println("At bill " + (i-1));
			}
			List<Document> keywords = (List<Document>) doc.get("keywords");
			List<Document> cleanedWords = new ArrayList<>();
			List<String> words = new ArrayList<>();
			for(Document kw : keywords){
				String word = kw.getString("text");
				if(!words.contains(word)){
					words.add(word);
					cleanedWords.add(kw);
				}
			}
			doc.replace("keywords", cleanedWords);
			wbills.findOneAndReplace(new Document("_id", doc.get("_id")), doc);
		}
		
		for(String key : new ArrayList<String>(wordCount.keySet())){
			if(wordCount.get(key) < 50){
				wordCount.remove(key);
			}
		}
		
		List<String> words = new ArrayList<>(wordCount.keySet());
		Collections.sort(words, new Comparator<String>() {
			@Override
			public int compare(String o1, String o2) {
				return wordCount.get(o1).compareTo(wordCount.get(o2));
			}
		});
		
		
		Collections.reverse(words);
		Document billStats = mongo.db.getCollection("Stats").find(new Document("type", "bill")).first();
		billStats.append("topWordsCount", wordCount);
		billStats.append("topWords", words);
		mongo.db.getCollection("Stats").replaceOne(new Document("_id", billStats.get("_id")), billStats);
	}
}
