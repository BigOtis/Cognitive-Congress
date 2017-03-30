package com.cogcong.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.cogcong.mongo.MongoFacade;
import com.cogcong.mongo.MongoFacade.Party;

/**
 * I'm just a bill...
 * @author pgl57
 *
 */
public class Bill {

	private Document billJSON;
	
	public Bill(Document doc){
		this.billJSON = doc;
	}
	
	public Bill (JSONObject billJSON){
		this.billJSON = Document.parse(billJSON.toString());
	}
	
	public String getBillID(){
		return billJSON.getString("bill_id");
	}
	
	public int getCongressNum(){
		return billJSON.getInteger("congress");
	}
	
	public String getTopSubject(){
		try{
			return billJSON.getString("subjects_top_term");
		}
		catch(Exception e){
			return "";
		}
	}
	
	public String getTitle(){
		return billJSON.getString("official_title");
	}
	
	public String getPopularTitle(){
		return billJSON.getString("popular_title");
	}
	
	public List<String> getSubjects(){
		List<String> ary = (List<String>) billJSON.get("subjects");
		List<String> strAry = new ArrayList<>();
		for(int i = 0; i < ary.size(); i++){
			strAry.add(ary.get(i));
		}
		return strAry;
	}
	
	public String getSummaryText(){
		try{
			return ((Document) billJSON.get("summary")).getString("text");
		}
		catch(Exception e){
			System.err.println("No summary text");
			return null;
		}
	}
	
	public String getIntroducedDate(){
		return billJSON.getString("introduced_at");
	}
	
	public int getNumRepublicanSponsors(){
		return billJSON.getInteger("rep_sponsors");
	}
	
	public int getNumDemocraticSponsors(){
		return billJSON.getInteger("dem_sponsors");

	}
	
	public Party getSponsorParty(){
		return MongoFacade.getParty(billJSON.getString("sponsor_party"));
	}
}
