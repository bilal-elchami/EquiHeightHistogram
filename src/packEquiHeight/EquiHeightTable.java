package packEquiHeight;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class EquiHeightTable <T> {

	/**
	 * @param rs the ResultSet obtained by the execution of the query
	 * @param attributeName represents the name of the attribute in the database
	 * @return a map containing the attribute value as a key and the count/occurrence as a value
	 */
	public Map<T, Integer> createCountMap(ResultSet rs, String attributeName){
		Map<T, Integer> map = new HashMap<T, Integer>();
		int maxCount = 0;
		T maxCountAttributeId = null;
		try {
			while(rs.next()){
				@SuppressWarnings("unchecked")
				T attributeId = (T) rs.getObject(attributeName);
				Integer attributeCount = map.get(attributeId);
				int count = 1;
				if(attributeCount!=null){
					count = attributeCount+1;
					if(maxCount < count){
						maxCount = count;
						maxCountAttributeId = attributeId;
					}
				}
				map.put(attributeId, count);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		System.out.println("Max Count Attribute Id: " + maxCountAttributeId + ", count = " + maxCount);
		return map;
	}

	/**
	 * Calculates the Equi-Height Histogram height value
	 * @param map containing the attribute value as a key and the count/occurrence as a value
	 * @param maxValue for now its going to be 0
	 * @param maxAcceptedError represents the acceptable number of errors in the histogram
	 */
	public int calculateHistogramHeight(Map<T, Integer> map, int maxValue, int maxAcceptedError) {
		int returnSum = -1;
		boolean found = false;
		boolean newMaxNeeded = false;
		while(!found){
			int sum = 0;
			boolean firstTurn = true;
			for(T i : map.keySet()){
				Integer currentAttributeCount = map.get(i);
				sum += currentAttributeCount;
				if ((sum > maxValue+maxAcceptedError)) {
					found = false;
					if(!firstTurn){
						newMaxNeeded = true;
						break;
					}
					newMaxNeeded = false;
					maxValue = sum;
					break;
				}
				if(((sum==maxValue) || (sum==maxValue+maxAcceptedError) || (sum==maxValue-maxAcceptedError)) && (!newMaxNeeded)){
					returnSum = sum;
					sum = 0;
					firstTurn = false;
				}
				found = true;
			}
		}
		printEquiHeightHistogram(map, maxValue,maxAcceptedError);
		return returnSum;
	}

	/**
	 * Prints the original histogram using asterisks
	 * @param map containing the attribute value as a key and the count/occurrence as a value
	 * @param maxValue calculated from {@link EquiHeightTable#calculateHistogramHeight}
	 * @param maxAcceptedError represents the acceptable number of errors in the histogram
	 */
	private void printEquiHeightHistogram(Map<T, Integer> map, int maxValue, int maxAcceptedError) {
		
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Equi-Height Histogram");
		System.out.println();
		int sum = 0;
		for(T i : map.keySet()){
			Integer currentAttributeCount = map.get(i);
			sum += currentAttributeCount;
			if(((sum==maxValue) || (sum==maxValue+maxAcceptedError) || (sum==maxValue-maxAcceptedError))){
				System.out.print(i + "\t");
				for (int j = 0; j < sum; j++) {
					System.out.print("■");
				}
				System.out.print(" ]" + sum);
				System.out.println();
				sum = 0;
			}
		}
	}

	/**
	 * Prints the original histogram using asterisks
	 * @param map containing the attribute value as a key and the count/occurrence as a value
	 */
	public void printOriginalHistogram(Map<T, Integer> map) {
		System.out.println();
		System.out.println();
		System.out.println();
		System.out.println("Original Histogram");
		System.out.println();
		for(T i : map.keySet()){
			Integer currentAttributeCount = map.get(i);
			System.out.print(i + "\t");
			for (int j = 0; j < currentAttributeCount; j++) {
				System.out.print("■");
			}
			System.out.print(" ]" + currentAttributeCount);
			System.out.println();
		}
	}
	
}
