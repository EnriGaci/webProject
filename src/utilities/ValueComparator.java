package utilities;

import java.util.Comparator;
import java.util.HashMap;

class ValueComparator implements Comparator<Long>{
	 
	HashMap<Long, Integer> map = new HashMap<Long, Integer>();
 
	public ValueComparator(HashMap<Long, Integer> map){
		this.map.putAll(map);
	}
 
	@Override
	public int compare(Long s1, Long s2) {
		if(map.get(s1) >= map.get(s2)){
			return -1;
		}else{
			return 1;
		}	
	}
}
