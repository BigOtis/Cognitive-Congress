package com.cogcong.scripts.bills;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.cogcong.compare.CompareBills;
import com.cogcong.model.Bill;
import com.cogcong.mongo.MongoFacade;
import com.mongodb.client.MongoCollection;


public class CacheSimilarBills {

	public static void main(String args[]){
		
		MongoFacade mongo = new MongoFacade();
		MongoCollection<Document> sbills = mongo.db.getCollection("SenateBills");
		
		Map<String, Integer> wordCount = new HashMap<>();	
		int i = 0;
		
		for(Document doc : sbills.find()){
			Bill b = new Bill(doc);
			if(b.getSimilarBills() == null){
				CompareBills cb = new CompareBills(b);
				if((i++) % 200 == 0){
					System.out.println("At bill " + (i-1));
				}
				
				List<Bill> sim = cb.getTopSortedBills(30);
				List<String> names = new ArrayList<>();
				for(Bill bs : sim){
					names.add(bs.getBillID());
				}
				doc.append("similarBills", names);
				sbills.findOneAndReplace(new Document("_id", doc.get("_id")), doc);
			}
			else{
				if((i++) % 200 == 0){
					System.out.println("At bill (cached)" + (i-1));
				}
			}
		}
		
		for(String key : new ArrayList<String>(wordCount.keySet())){
			if(wordCount.get(key) < 50){
				wordCount.remove(key);
			}
		}
	}
}
