package utilities;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeMap;
import java.util.TreeSet;

import db.BidDAOImpl;
import db.ItemDAOImpl;
import db.UserDAOImpl;
import entities.Bid;
import entities.Item;
import entities.User;


public class Recommentation {
	
	Map<Long,Integer> indexOfUser;
	Map<Long,Integer> indexOfItem;
	private int PlhthosUsers;
	private int PlhthosItems;
	public List<List<Long>> UsersRecommendedItems;
	
	public Recommentation()
	{
		indexOfUser = new HashMap<Long,Integer>();
		indexOfItem = new HashMap<Long,Integer>();
	}
	
	//taksinomish similarities
	static <K,V extends Comparable<? super V>>
	SortedSet<Map.Entry<K,V>> entriesSortedByValue(Map<K,V> map) {
		SortedSet<Map.Entry<K,V>> sortedEntries = new TreeSet<Map.Entry<K,V>>(new Comparator<Map.Entry<K,V>>() {
            @Override public int compare(Map.Entry<K,V> e1, Map.Entry<K,V> e2) {
                int res = ((Double) e1.getValue()).compareTo((Double) e2.getValue());
                return res != 0 ? res : 1;
            }
        }
		);
		sortedEntries.addAll(map.entrySet());
		return sortedEntries;
	}

	public static Object getKeyFromValue(Map hm, Object value) {
    	for (Object o : hm.keySet()) {
     		if (hm.get(o).equals(value)) {
        		return o;
      		}
    	}
    	return null;
  	}
	
	//GENERATE USER VECTORS
	//vhma 1: Fere Ola ta bids apo thn vash
	//vhma 2: Ftiakse gia kathe user ena dianysma me stoixeia to zeugos (item_id,amount)
	//vhma 3: Taksinomhse ayta ta dianysmata ws pros to item_id
	//vhma 4: (Proeraitiko) Omalopoihse ta amount diairontas olo to dianysma me to Max_Amount
	public double[][] GenerateUsersVectors()
	{
		Integer user_count,item_count;
		
		//fere mou to plhthos twn items
		BidDAOImpl bidd = new BidDAOImpl();
		List<Bid> bids = bidd.findAll();		
		item_count = 0;
		user_count = 0;
		
		System.out.println("arxikopoiw ta maps");
		// vres to plh8os twn diaforetikwn bidder kai item
		for(Bid bid:bids){
			
			if(indexOfItem.get(bid.getItem().getItemId()) == null)
			{
				indexOfItem.put(bid.getItem().getItemId(),item_count);
				item_count++;
			}
			
			if(indexOfUser.get(bid.getUser().getUserId()) == null)
			{
				indexOfUser.put(bid.getUser().getUserId(),user_count);
				user_count++;
			}
			
		}
		System.out.println("TELOS arxikopoihshs ta maps");
		
		PlhthosItems = item_count;
		PlhthosUsers = user_count;
		
		//ftiakse to dianysma me ta amount tou kathe user
		double[][] UsersVectors = new double[PlhthosUsers][PlhthosItems];		
		int i,j;
		for(i=0; i<PlhthosUsers; i++)
			for(j=0; j<PlhthosItems; j++)
				UsersVectors[i][j] = 0.0;
		
		Integer user_position = null, item_position = null;
		
		System.out.println("Ftiaxnw to pinaka usevectors");
		for(Bid bid: bids)
		{
			//			if( indexOfUser.containsKey(bid.getUser().getUserId()) )
			user_position = indexOfUser.get(bid.getUser().getUserId());
			//			if(indexOfItem.containsKey(bid.getItem().getItemId()))
			item_position = indexOfItem.get(bid.getItem().getItemId());

			String ammount = bid.getAmount();
			ammount = ammount.replace(",", "");
			ammount = ammount.replace("$", "");

			UsersVectors[user_position][item_position] = Double.parseDouble(ammount);
		}
		
		System.out.println("TELOS Ftiaxnw to pinaka usevectors");

		
		return UsersVectors;
	}
	
	//KNN
	//vhma 5:Vres ta similarity tou kathe user me olous tous user(cosine_similarity)
	//vhma 6:Ftiakse gia kathe user mia lista me stoixeia to zeugos (user_id(neighbor),similarity)
	//vhma 7:Omalopoihse ayta ta similarity sto (0,1)
	//vhma 8:Taksinomiseta ws pros to similarity
	//vhma 9:Krata ta K megalytera kai epestrepse mia lista me ta neighbors_id tou kathe user
	public List<ArrayList<Integer>> KNN(double[][] usersVectors , int K)
	{	
		Integer user,neighbor,item;
		db.RecommendedItemDAOImpl recd = new db.RecommendedItemDAOImpl();
		List<ArrayList<Integer>> UsersNeighbors = new ArrayList<ArrayList<Integer>>() ;

		System.out.println(" users = " + PlhthosUsers + " Items = " + PlhthosItems);
		
		for(user=0; user<PlhthosUsers; user++)
		{

			if(user%100 == 0 )
			{
				System.out.println(((double)user/(double)PlhthosUsers)*100 + "%");
				System.out.println("user = " + user);
			}

			HashMap<Integer,Double> similarities = new HashMap<Integer,Double>();

			for(neighbor=0; neighbor<PlhthosUsers; neighbor++)
			{
				double sum1 = 0;
				double sum2 = 0;
				double sum3 = 0;
				Double similarity = 0.0;

				if(neighbor != user)
				{
					for(item=0; item<PlhthosItems; item++)
					{
						sum1 += usersVectors[user][item] * usersVectors[neighbor][item];
						sum2 += usersVectors[user][item] * usersVectors[user][item];
						sum3 += usersVectors[neighbor][item] * usersVectors[neighbor][item];
					}

					if (sum1 == 0 && (sum2 == 0 || sum3 == 0))
						similarity = -1.0;
					else
						similarity = sum1 / (Math.sqrt(sum2) * Math.sqrt(sum3));

					similarity = (similarity+1)/2;
					//					System.out.println("similarity = " + similarity + " sum1 = " +sum1 + " sum2 = " + sum2 + " sum3 = " + sum3);

					similarities.put(neighbor, similarity);
				}
			}

			TreeMap<Integer, Double> sim = sortMapByValue2(similarities);
			
			// map apo itemId -> frequency of item
			HashMap<Long,Integer> itemFrequencyMap = new HashMap<Long,Integer>();

			int j = 0;

			// vres to key pou exei authn thn apostash
			for( Map.Entry<Integer, Double> entry : sim.entrySet())
			{
				if( j == K ) break;
				Integer itemPos;
				Integer neighbourId = entry.getKey();

				// gia ka8e item 
				for(Long item_id: indexOfItem.keySet() )
				{
					itemPos = indexOfItem.get(item_id);
					/* ypologise ta frequency twn item apo tous geitones
					 */
					if(usersVectors[user][itemPos] == 0.0 && usersVectors[neighbourId][itemPos] > 0.0)
					{
						if( itemFrequencyMap.get(item_id) == null)
							itemFrequencyMap.put(item_id, 1);
						else
							itemFrequencyMap.put(item_id,new Integer(itemFrequencyMap.get(item_id)+1));

					}
				}
				j++;
			}

			TreeMap<Long, Integer> sortedMap = sortMapByValue(itemFrequencyMap);

			System.out.println("sorted map = " + sortedMap);

			// vale ta top 5 item sth vash
//			int i = 0;
//			for (Map.Entry<Long, Integer> entry : sortedMap.entrySet()) {
//
//				if( i == 5 )
//					break;
//
//				Long user_id = (Long) getKeyFromValue(indexOfUser,user);
//
//				//vale sthn vash
//				//to item_id kai to user_id
//				entities.RecommendedItem rec = new entities.RecommendedItem();
//				rec.setUserId(user_id);
//				rec.setItemId(entry.getKey());
//				recd.insert(rec);
//				
//				i++;
//
//			}
		}
		
		return  UsersNeighbors;
	}
	
	public static TreeMap<Long, Integer> sortMapByValue(HashMap<Long, Integer> map){
		Comparator<Long> comparator = new ValueComparator(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<Long, Integer> result = new TreeMap<Long, Integer>(comparator);
		result.putAll(map);
		return result;
	}
	
	public static TreeMap<Integer, Double> sortMapByValue2(HashMap<Integer, Double> map){
		Comparator<Integer> comparator = new ValueComparator2(map);
		//TreeMap is a map sorted by its keys. 
		//The comparator is used to sort the TreeMap by keys. 
		TreeMap<Integer, Double> result = new TreeMap<Integer, Double>(comparator);
		result.putAll(map);
		return result;
	}

	
	//FIND RECOMMENDED ITEMS
	
	//vhma 10: Xrhsimopoihse thn lista pou ftiaksame sto vhma 2 pou afora mono tous geitones tou kathe user
	//vhma 11: Vale ola ta item_id se mia lista gia ta opoia isxyei h synthikh (userAmount == 0 && neighborAmount > 0)
	//vhma 12: Vale sthn vash, sto table Recommentation ta (user_id,item_id)
	public void FindRecommendedItems(List<ArrayList<Integer>> neighbors,double[][] usersVectors){
		
		db.RecommendedItemDAOImpl recd = new db.RecommendedItemDAOImpl();
		
//		UsersRecommendedItems = null;
		Integer user = 0;
		for(List<Integer> userIndex:neighbors)
		{
			List<Long> UserRecommendedItems = new ArrayList<Long>();
			for(Integer neighbor:userIndex)
			{
				Integer item = 0;
				for(Long item_id: indexOfItem.keySet())
				{
					if(usersVectors[user][item] == 0.0 && usersVectors[neighbor][item] > 0.0)
					{
						UserRecommendedItems.add(item_id);
						Long user_id = (Long) getKeyFromValue(indexOfUser,user);
						
						//vale sthn vash
						//to item_id kai to user_id
						entities.RecommendedItem rec = new entities.RecommendedItem();
						rec.setUserId(user_id);
						rec.setItemId(item_id);
						recd.insert(rec);
						
					}
					item++;
				}
				
			}
			UsersRecommendedItems.add(UserRecommendedItems);
			user++;
		}
		
		return ;
	}
	
//	public List<Long> getRecommendedItems(String username)
//	{
//		GenerateUsersVectors(); //Make Users Vectors with Amounts
//
//		int K = 5;
//		List<List<Integer>> neighbors = KNN(UsersVectors,K); //find neighbors with KNN ( K=5 )
//
//		FindRecommendedItems(neighbors); //Find Users Recommended items 
//		
//		UserDAOImpl userd = new UserDAOImpl();
//		entities.User user = userd.getByUserName(username);
//
//		Integer indexUser = indexOfUser.get(user.getUserId());
//		List<Long> RecommendedItems = UsersRecommendedItems.get(indexUser);
//		return RecommendedItems;
//		
//	}
}
