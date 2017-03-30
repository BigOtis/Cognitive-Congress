package com.cogcong.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONObject;

import com.cogcong.mongo.MongoFacade;
import com.cogcong.mongo.MongoFacade.Party;

/**
 * I'm just a bill...
 * @author pgl57
 *
 */
public class Bill {

	private Document billDoc;
	
	public Bill(Document doc){
		this.billDoc = doc;
	}
	
	public Bill (JSONObject billJSON){
		this.billDoc = Document.parse(billJSON.toString());
	}
	
	public String getBillID(){
		return billDoc.getString("bill_id");
	}
	
	public int getCongressNum(){
		return billDoc.getInteger("congress");
	}
	
	public String getTopSubject(){
		try{
			return billDoc.getString("subjects_top_term");
		}
		catch(Exception e){
			return "";
		}
	}
	
	public String getTitle(){
		return billDoc.getString("official_title");
	}
	
	public String getPopularTitle(){
		return billDoc.getString("popular_title");
	}
	
	public List<String> getSubjects(){
		List<String> ary = (List<String>) billDoc.get("subjects");
		List<String> strAry = new ArrayList<>();
		for(String subject : ary){
			strAry.add(subject);
		}
		return strAry;
	}
	
	public String getSummaryText(){
		try{
			return ((Document) billDoc.get("summary")).getString("text");
		}
		catch(Exception e){
			System.err.println("No summary text");
			return null;
		}
	}
	
	public String getIntroducedDate(){
		return billDoc.getString("introduced_at");
	}
	
	public int getNumRepublicanSponsors(){
		return billDoc.getInteger("rep_sponsors");
	}
	
	public int getNumDemocraticSponsors(){
		return billDoc.getInteger("dem_sponsors");

	}
	
	public Party getSponsorParty(){
		return MongoFacade.getParty(billDoc.getString("sponsor_party"));
	}
}
