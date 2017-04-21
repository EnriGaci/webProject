package utilities;

import java.util.Comparator;
import java.util.HashMap;

class ValueComparator2 implements Comparator<Integer>{
	 
	HashMap<Integer, Double> map = new HashMap<Integer, Double>();
 
	public ValueComparator2(HashMap<Integer, Double> map){
		this.map.putAll(map);
	}
 
	@Override
	public int compare(Integer s1, Integer s2) {
		if(map.get(s1) >= map.get(s2)){
			return -1;
		}else{
			return 1;
		}	
	}
}