package com.cogcong.scripts;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.cogcong.model.Legislator;
import com.cogcong.mongo.MongoFacade;
import com.mongodb.client.MongoCollection;

public class CreateLegislatorSimilarCache {
	
	public static void main(String [] args) throws ParseException{
		
		MongoFacade mongo = new MongoFacade();
		MongoCollection<Document> legislators = mongo.db.getCollection("Legislators");
		MongoCollection<Document> stats = mongo.db.getCollection("LegislatorStats");		

		for(Document legDoc : legislators.find()){
		
			String id = ((Document) legDoc.get("id")).getString("bioguide");
			Legislator leg = new Legislator(id);
			System.out.println("Processing: " + leg.getName());
			List<Legislator> sim = leg.getTop10Similiar();
			List<Legislator> diff = leg.getTop10Different();
			List<String> simStr = new ArrayList<>();
			List<String> diffStr = new ArrayList<>();
			for(Legislator l : sim){
				simStr.add(l.getBioguide_id());
			}
			for(Legislator l : diff){
				diffStr.add(l.getBioguide_id());
			}
			leg.legislatorStats.append("topSimilar", simStr);
			leg.legislatorStats.append("topDifferent", diffStr);
			stats.findOneAndReplace(new Document("_id", leg.legislatorStats.get("_id")), leg.legislatorStats);
		}
	}
}


