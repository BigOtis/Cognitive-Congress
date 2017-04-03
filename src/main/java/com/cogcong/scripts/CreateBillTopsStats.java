package com.cogcong.scripts;

import java.util.HashMap;
import java.util.Map;

import org.bson.Document;

import com.cogcong.model.Bill;
import com.cogcong.mongo.MongoFacade;

public class CreateBillTopsStats {
	
	public static void main(String args[]){
		
		MongoFacade mongo = MongoFacade.getInstance();
		Document stats = mongo.db.getCollection("Stats").find(new Document("type", "bill")).first();
		
		Map<String, Integer> subjects = new HashMap<>();
		for(Bill bill : mongo.queryAllBills()){
			String subject = bill.getTopSubject();
			if(subjects.containsKey(subject)){
				subjects.put(subject, subjects.get(subject)+1);
			}
			else{
				if(subject != null){
					subjects.put(subject, 1);
				}
			}
		}
		
		stats.append("topSubjectsCount", subjects);
		mongo.db.getCollection("Stats").findOneAndReplace(new Document("_id", stats.get("_id")), stats);
		
		
	}

}
