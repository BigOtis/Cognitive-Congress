package com.cogcong.compare;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import com.cogcong.model.Legislator;
import com.cogcong.mongo.MongoFacade;

public class CompareLegislators {

	
	private Legislator mainLegislator;
	private static List<Legislator> legislators = null;
	
	/**
	 * Constructor
	 * @param mainLegislator
	 * @param legislators
	 */
	public CompareLegislators(Legislator mainLegislator){
		this.mainLegislator = mainLegislator;
		if(legislators == null){
			legislators = MongoFacade.getInstance().queryAllLegislators();
		}
	}
	
	public List<Legislator> getTopSortedLegislators(int count){
		List<Legislator> sorted = getSortedLegislators();
		return sorted.subList(0, Math.min(count, sorted.size()));
	}
 
	public List<Legislator> getBottomSortedLegislators(int count){
		List<Legislator> sorted = getSortedLegislators();
		Collections.reverse(sorted);
		return sorted.subList(0, Math.min(count, sorted.size()));
	}
	
	public List<Legislator> getSortedLegislators(){
		
		List<Legislator> sorted = new ArrayList<>(legislators);
		sorted.remove(mainLegislator);
		Collections.sort(sorted, new Comparator<Legislator>() {
			@Override
			public int compare(Legislator l1, Legislator l2) {
				Double l1Dist = mainLegislator.distanceTo(l1);
				Double l2Dist = mainLegislator.distanceTo(l2);
				return l2Dist.compareTo(l1Dist);
			}
		});
		return sorted;
		
	}
}
