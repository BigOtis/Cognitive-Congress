package com.cogcong.scripts.bills;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.cogcong.model.Bill;
import com.cogcong.mongo.MongoFacade;
import com.cogcong.mongo.MongoFacade.Party;

public class CreateBillPartisianStats {

	public static void main(String args[]){
	
		MongoFacade mongo = MongoFacade.getInstance();
		Document stats = mongo.db.getCollection("Stats").find(new Document("type", "bill")).first();
		
		Map<String, Integer> rSubjects = new HashMap<>();
		Map<String, Integer> dSubjects = new HashMap<>();
		Map<String, Integer> iSubjects = new HashMap<>();

		Map<String, Integer> rWords = new HashMap<>();
		Map<String, Integer> dWords = new HashMap<>();
		Map<String, Integer> iWords = new HashMap<>();

		
		for(Bill bill : mongo.queryAllBills()){
			Party p = bill.getSponsorParty();
			Map<String, Integer> subjectsMap = null;
			Map<String, Integer> keywordsMap = null;
			switch(p){
				case REPUBLICAN:
					subjectsMap = rSubjects;
					keywordsMap = rWords;
					break;
				case DEMOCRAT:
					subjectsMap = dSubjects;
					keywordsMap = dWords;
					break;
				case INDEPENDENT:
					subjectsMap = iSubjects;
					keywordsMap = iWords;
					break;
				default:
					System.out.println("MISSING SPONSOR: " + bill.getBillID());
					break;
			}
			
			for(String word : bill.getKeywords()){
				if(keywordsMap.containsKey(word)){
					keywordsMap.put(word, keywordsMap.get(word) + 1);
				}
				else{
					keywordsMap.put(word, 1);
				}
			}
			
			for(String word : bill.getSubjects()){
				word = word.replaceAll("[^a-zA-Z\\s]", " ");
				if(subjectsMap.containsKey(word)){
					subjectsMap.put(word, subjectsMap.get(word) + 1);
				}
				else{
					subjectsMap.put(word, 1);
				}
			}
		}
		
		System.out.println("Appending partisian stats...");
		removeLessThan(rSubjects, 50);
		removeLessThan(dSubjects, 50);
		removeLessThan(iSubjects, 5);
		removeLessThan(rWords, 50);
		removeLessThan(dWords, 50);
		removeLessThan(iWords, 5);

		stats.append("rsubjects", rSubjects);
		stats.append("dsubjects", dSubjects);
		stats.append("isubjects", iSubjects);
		stats.append("rkeywords", rWords);
		stats.append("dkeywords", dWords);
		stats.append("ikeywords", iWords);
		mongo.db.getCollection("Stats").findOneAndReplace(new Document("_id", stats.get("_id")), stats);
	}
	
	public static void removeLessThan(Map<String, Integer> map, int count){
		for(String key : new ArrayList<String>(map.keySet())){
			if(map.get(key) < count){
				map.remove(key);
			}
		}
	}
}
