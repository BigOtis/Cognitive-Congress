package com.cogcong.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang3.StringUtils;
import org.bson.Document;
import org.json.JSONArray;
import org.json.JSONObject;

import com.mongodb.client.MongoCollection;

import com.cogcong.mongo.MongoFacade;


public class Vote {
	
	public static final String[] voteTypeNames = new String[]{"Yea", "Nay", "Present", "Not Voting"};

	private Document voteJSON;
	private Bill bill = null;
	private MongoFacade mongo = MongoFacade.getInstance();
	private Map<CharSequence, Integer> billWordFrequency = null;
	
	public Vote(Document voteBSON){
		this.voteJSON = voteBSON;
	}
	
	public Vote(JSONObject voteJSON){
		this.voteJSON = Document.parse(voteJSON.toString());
	}
	
	public String getBillName(){
		Document bill;
		try{
			bill = (Document) voteJSON.get("bill");
		}
		catch(Exception e){
			// Not a bill
			return null;
		}
		return getChamber() + bill.getInteger("number") + "-" + bill.getString("congress");
	}
	
	public Bill getBill(){
		if(bill != null){
			return bill;
		}
		Bill b = mongo.queryBill(getCongressName(), getBillNumber(), getChamber());
		bill = b;
		return b;
	}
	
	public String getBillNumber(){
		Document bill;
		try{
			bill = (Document) voteJSON.get("bill");
		}
		catch(Exception e){
			// Not a bill
			return null;
		}
		return bill.getInteger("number") + "";
	}
	
	public String getChamber(){
		return voteJSON.getString("chamber");
	}
	
	public int getVoteNumber(){
		return voteJSON.getInteger("number");
	}
	
	public int getCongressNumber(){
		return voteJSON.getInteger("congress");
	}
	
	public String getDate(){
		return voteJSON.getString("date");
	}
	
	public String getVoteID(){
		return voteJSON.getString("vote_id");
	}
	
	public List<IndividualVote> getIndividualVotes(){
		
		List<IndividualVote> votesArray = new ArrayList<>();
		Document individualVotes = (Document) voteJSON.get("votes");
		for(String type : voteTypeNames){
			List<Document> ary = (List<Document>) individualVotes.get(type);
			for(int i = 0; i < ary.size(); i++){
				votesArray.add(new IndividualVote(this, type, (Document) ary.get(i)));
			}
		}
		return votesArray;
	}
	
	public String getSession(){
		return voteJSON.getString("session");
	}
	
	public String getCongressName(){
		return voteJSON.getString("chamber") + getCongressNumber();
	}
	
	public String getCategory(){
		return voteJSON.getString("category");
	}
	
	public boolean hasAssociatedBillText(){
		
		MongoCollection<Document> billText = mongo.db.getCollection("SenateBillText");
		String bill_id = getChamber() + getBillNumber() + "-" + getCongressNumber();
		return billText.count(new Document().append("bill_id", bill_id)) > 0;

	}
}
