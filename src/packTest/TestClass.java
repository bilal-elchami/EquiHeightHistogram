package packTest;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;

import packEquiHeight.EquiHeightTable;
import packUtils.ConnectToDB;

public class TestClass {

	public static void main(String[] args) {
		try {
			
			
			/* ********************************************************
			 * Check the ConnectToDB class for database configuration *
			 **********************************************************/
			
			/* We create an instance of the class and we identify the attribute type
			 * In our case it's an Integer */
			EquiHeightTable<Integer> equiHeightTable = new EquiHeightTable<Integer>();
			
			Statement st = ConnectToDB.getStatement();
			String attributeName = "an_attribute";
			String query = "SELECT * FROM test_table;";
			ResultSet rs = st.executeQuery(query);

			Map<Integer, Integer> map = equiHeightTable.createCountMap(rs, attributeName);
			//System.out.println(map);
			
			/* We can set the maxValue directly to the max value in the map
			 * Here we're starting from the very beginning */
			int maxValue = 0;
			/*
			 * The maximum accepted error here is 1, but sometimes this can't be feasible
			 * I guess this part should be calculated according to the data
			 */
			int maxAcceptedError = 1;
			
			int intervalSize = equiHeightTable.calculateHistogramHeight(map, maxValue,maxAcceptedError);

			System.out.println();
			System.out.println();
			System.out.println();
			System.out.println("The Interval Value is: " + intervalSize);
			System.out.println("Acceptable values between: " 
					+ (intervalSize-maxAcceptedError) 
					+ " & " 
					+ (intervalSize+maxAcceptedError));

			equiHeightTable.printOriginalHistogram(map);

		} catch (SQLException e) {
			e.printStackTrace();
		}finally {
			ConnectToDB.closeConnection();
		}
	}

}
