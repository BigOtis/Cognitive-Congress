package com.cogcong.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.cogcong.model.Bill;
import com.cogcong.mongo.MongoFacade;

public class CompareBills {

	
	private Bill mainBill;
	private static List<Bill> bills = null;
	
	/**
	 * Constructor
	 * @param mainLegislator
	 * @param legislators
	 */
	public CompareBills(Bill mainBill){
		this.mainBill = mainBill;
		if(bills == null){
			bills = MongoFacade.getInstance().queryAllBills();
		}
	}
	
	public List<Bill> getTopSortedBills(int count){
		List<Bill> sorted = getSortedBills();
		return sorted.subList(0, Math.min(count, sorted.size()));
	}
	
	public List<Bill> getSortedBills(){
		
		List<Bill> sorted = new ArrayList<>(bills);
		sorted.remove(mainBill);
		Collections.sort(sorted, new Comparator<Bill>() {
			@Override
			public int compare(Bill b1, Bill b2) {
				Double b1Dist = mainBill.distanceTo(b1);
				Double b2Dist = mainBill.distanceTo(b2);
				return b2Dist.compareTo(b1Dist);
			}
		});
		return sorted;
		
	}
}
