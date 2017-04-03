package com.cogcong.scripts;

import java.text.ParseException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.cogcong.model.Bill;
import com.cogcong.model.Legislator;
import com.cogcong.mongo.MongoFacade;
import com.mongodb.client.MongoCollection;

public class CreateLegislatorStats {
	
	public static void main(String [] args) throws ParseException{
		
		MongoFacade mongo = new MongoFacade();
		MongoCollection<Document> legislators = mongo.db.getCollection("Legislators");
		MongoCollection<Document> stats = mongo.db.getCollection("LegislatorStats");

		boolean tr = false;
		if(tr){
			for(Document legDoc : legislators.find()){
				String id = ((Document) legDoc.get("id")).getString("bioguide");
				Legislator leg = new Legislator(id);
				List<String> words = leg.getTopNSubjects(8);
				if(words != null){
					System.out.println(leg.getName() + ":\t" + words);
				}
				else{
					System.out.println("Deleting: " + leg.getName());
					legislators.findOneAndDelete(new Document("_id", legDoc.get("_id")));
				}
			}
			return;
		}
		

		for(Document legDoc : legislators.find()){
			String id = ((Document) legDoc.get("id")).getString("bioguide");
			Legislator leg = new Legislator(id);
			List<String> bills = leg.getSponsoredBills();
			if(bills != null){
				Map<String, Integer> subjects = new HashMap<>();
				Map<String, Integer> keywords = new HashMap<>();
				System.out.println("Adding bill keywords for: " + leg.getName());
				for(String bill_id : bills){
					Bill bill = new Bill(bill_id);
					String subject = bill.getTopSubject();
					if(subjects.containsKey(subject)){
						subjects.put(subject, subjects.get(subject)+1);
					}
					else{
						if(subject != null){
							subjects.put(subject, 1);
						}
					}
					for(String keyword : bill.getKeywords()){
						if(!keywords.containsKey(keyword)){
							keywords.put(keyword, 1);
						}
						else{
							keywords.put(keyword, keywords.get(keyword)+1);
						}
					}
				}
				leg.updateKeywords(keywords);
				leg.updateSubjects(subjects);
			}
		}
	}
}


