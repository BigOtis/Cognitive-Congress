package com.cogcong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
	private Document watsonBill;
	private MongoFacade mongo = MongoFacade.getInstance();
	
	public Bill(String bill_id){
		this.billDoc = mongo.getBill(bill_id);
		this.watsonBill = mongo.getWatsonBill(bill_id);
	}
	
	public Bill(Document doc){
		this.billDoc = doc;
		this.watsonBill = mongo.getWatsonBill(getBillID());
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
	
	@SuppressWarnings("unchecked")
	public List<String> getKeywords(){
		
		List<Document> keywordsList = (List<Document>) watsonBill.get("keywords");
		List<String> keywords = new ArrayList<>();
		for(Document keyword : keywordsList){
			keywords.add(keyword.getString("text"));
		}
		return keywords;
	}
}
