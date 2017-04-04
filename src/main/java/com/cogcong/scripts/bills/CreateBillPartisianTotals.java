package com.cogcong.scripts.bills;

import org.bson.Document;

import com.cogcong.model.Bill;
import com.cogcong.mongo.MongoFacade;
import com.cogcong.mongo.MongoFacade.Party;

public class CreateBillPartisianTotals {

	public static void main(String args[]){
	
		MongoFacade mongo = MongoFacade.getInstance();
		Document stats = mongo.db.getCollection("Stats").find(new Document("type", "bill")).first();
		
		int r = 0;
		int d = 0;
		int i = 0;
		
		int rco = 0;
		int dco = 0;
		int ico = 0;

		for(Bill bill : mongo.queryAllBills()){
			Party p = bill.getSponsorParty();
			switch(p){
				case REPUBLICAN:

					break;
				case DEMOCRAT:

					break;
				case INDEPENDENT:

					break;
				default:
					break;
			}
		}
	}
}
