package com.cogcong.scripts;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bson.Document;

import com.cogcong.model.Bill;
import com.cogcong.model.Legislator;
import com.cogcong.mongo.MongoFacade;
import com.mongodb.client.MongoCollection;

public class ExportLegislatorTable {
	
	public static void main(String [] args) throws ParseException, FileNotFoundException{
		
		MongoFacade mongo = new MongoFacade();
		MongoCollection<Document> legislators = mongo.db.getCollection("Legislators");
		MongoCollection<Document> stats = mongo.db.getCollection("LegislatorStats");
		
		PrintWriter nodes = new PrintWriter("nodes.csv");
		nodes.println("id,label,timeset,party");
		PrintWriter edges = new PrintWriter("edges.csv");
		edges.println("Source,Target,Type,id,label,timeset,weight");
		//0,1,Undirected,0,,,1.0


		List<Legislator> legs = new ArrayList<>();
		List<String> names = new ArrayList<>();		
		
		for(Document legDoc : legislators.find()){
			String id = ((Document) legDoc.get("id")).getString("bioguide");
			Legislator leg = new Legislator(id);
			legs.add(leg);
			names.add(leg.getName());
		}

		int ec = 0;
		int id = 0;		
		for(Legislator leg : legs){
			System.out.println("Adding: " + leg.getName());
			int idx = names.indexOf(leg.getName());
			nodes.println(idx + "," + leg.getName() + ",," + leg.getLatestPartySymbol());
			List<Legislator> friends = leg.getTop10Similiar();
			for(Legislator friend : friends){
				ec++;
				edges.println(idx + "," + names.indexOf(friend.getName()) + ",Undirected," + id + ",,,1.0" );
			}
		}
		System.out.println("Edges: " + ec);
		nodes.flush();
		edges.flush();
		nodes.close();
		edges.close();
	}
}


