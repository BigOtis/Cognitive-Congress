package com.cogcong.model;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import org.json.JSONObject;

import com.cogcong.mongo.MongoFacade;
import com.mongodb.client.MongoCollection;

/**
 * Wrapper for the MongoDB SenateVote Object
 * 
 * Contains the full outcome of a vote
 * 
 * @author Phil
 *
 */
public class Vote {
	
	public static final String[] voteTypeNames = new String[]{"Yea", "Nay", "Present", "Not Voting"};

	private Document voteDoc;
	private Bill bill = null;
	private MongoFacade mongo = MongoFacade.getInstance();
	
	public Vote(Document voteBSON){
		this.voteDoc = voteBSON;
	}
	
	public Vote(JSONObject voteJSON){
		this.voteDoc = Document.parse(voteJSON.toString());
	}
	
	public String getBillName(){
		Document bill;
		try{
			bill = (Document) voteDoc.get("bill");
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
			bill = (Document) voteDoc.get("bill");
		}
		catch(Exception e){
			// Not a bill
			return null;
		}
		return bill.getInteger("number") + "";
	}
	
	public String getChamber(){
		return voteDoc.getString("chamber");
	}
	
	public int getVoteNumber(){
		return voteDoc.getInteger("number");
	}
	
	public int getCongressNumber(){
		return voteDoc.getInteger("congress");
	}
	
	public String getDate(){
		return voteDoc.getString("date");
	}
	
	public String getVoteID(){
		return voteDoc.getString("vote_id");
	}
	
	public List<IndividualVote> getIndividualVotes(){
		
		List<IndividualVote> votesArray = new ArrayList<>();
		Document individualVotes = (Document) voteDoc.get("votes");
		for(String type : voteTypeNames){
			List<Document> ary = (List<Document>) individualVotes.get(type);
			for(int i = 0; i < ary.size(); i++){
				votesArray.add(new IndividualVote(this, type, (Document) ary.get(i)));
			}
		}
		return votesArray;
	}
	
	public String getSession(){
		return voteDoc.getString("session");
	}
	
	public String getCongressName(){
		return voteDoc.getString("chamber") + getCongressNumber();
	}
	
	public String getCategory(){
		return voteDoc.getString("category");
	}
	
	public boolean hasAssociatedBillText(){
		
		MongoCollection<Document> billText = mongo.db.getCollection("SenateBillText");
		String bill_id = getChamber() + getBillNumber() + "-" + getCongressNumber();
		return billText.count(new Document().append("bill_id", bill_id)) > 0;

	}
}
