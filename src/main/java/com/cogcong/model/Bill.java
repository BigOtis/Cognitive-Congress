package com.cogcong.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;

import com.cogcong.compare.CompareBills;
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
			String subject = billDoc.getString("subjects_top_term");
			if(subject == null){
				return "None";
			}
			return subject;
		}
		catch(Exception e){
			return "None";
		}
	}
	
	public String getTitle(){
		return billDoc.getString("official_title");
	}
	
	public String getPopularTitle(){
		String title = billDoc.getString("popular_title");
		if(title == null){
			return "";
		}
		return title;
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
	
	public Double getKeywordConfidence(String keyword){
		
		List<Document> keywordsList = (List<Document>) watsonBill.get("keywords");
		for(Document doc : keywordsList){
			if(doc.getString("text").equals(keyword)){
				Double val = doc.getDouble("relevance");
				val = val * 1000;
				Double dRound = ((double) val.intValue()) / 1000;
				return dRound;
			}
		}
		return 0.0;
	}
	
	public Legislator getSponsor(){
		Document sponsor = (Document) billDoc.get("sponsor");
		Document leg = mongo.getLegislatorByBioID(sponsor.getString("bioguide_id"));
		return new Legislator(leg);
	}
	
	public List<Legislator> getCosponsors(){
		List<Legislator> cosponsors = new ArrayList<>();
		List<Document> docs = (List<Document>) billDoc.get("cosponsors");
		if(docs != null){
			for(Document doc : docs){
				cosponsors.add(new Legislator(
				mongo.getLegislatorByBioID(doc.getString("bioguide_id"))));
			}
		}
		return cosponsors;
	}
	
	public Document getEmotion(){
		return (Document) ((Document) watsonBill.get("emotion")).get("emotion");
	}
	
	public String getSentimentLabel(){
		Document sentiment = (Document) watsonBill.get("sentiment");
		return sentiment.getString("label");
	}
	
	public Double getSentimentScore(){
		Document sentiment = (Document) watsonBill.get("sentiment");
		return sentiment.getDouble("score");
	}
	
	public List<String> getSimilarBills(){
		List<String> sim = (List<String>) billDoc.get("similarBills");
		if(sim != null){
			sim.remove(0);
		}
		return sim;
	}
	
	public Double distanceTo(Bill that){
		
		Double distance = 0.0;
		List<String> thatKW = that.getKeywords();
		for(String keyword : this.getKeywords()){
			if(thatKW.contains(keyword)){
				distance += 5;
			}
			else{
				distance -= .1;
			}
		}
		
		return distance;
	}
}
