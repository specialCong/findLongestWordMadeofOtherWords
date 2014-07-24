package longestWord;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class LongestWord {
	
	// index of array which indicates the words can be constructed
	private static int k = -1;
	private static ArrayList<Integer> arrIndex = new ArrayList<Integer>();
	
	static class LengthComparator implements Comparator<String> {
		public int compare(String o1, String o2) {
			return o2.length() - o1.length(); // sort array of words by length from max to min
		}
	}
	
	public static int countWord(String arr[]) {
		int count = 0;
		HashMap<String, Boolean> map = new HashMap<String, Boolean>();
		for (String str : arr) {
			map.put(str, true);
		}
		Arrays.sort(arr, new LengthComparator()); // Sort by length

		for (String s : arr) {
			k += 1;
			if (canBuildWord(s, true, map)) {
				 arrIndex.add(k);
				 count += 1;
			}
		}
		return count;
	}
	
	// DFS
	public static boolean canBuildWord(String str, boolean isOriginalWord,
			HashMap<String, Boolean> map) {
		if (map.containsKey(str) && !isOriginalWord) {
			return map.get(str);
		}
		for (int i = 1; i < str.length(); i++) {
			String left = str.substring(0, i);
			String right = str.substring(i);
			if (map.containsKey(left) && map.get(left) == true
					&& canBuildWord(right, false, map)) {
				return true;
			}
		}
		map.put(str, false); // flag that str cannot be constructed by other words
		return false;
	}

	
	public static void main(String[] args) {
		String[] Words = null;
		try {
			Words = createGiantArray();
			// start time
			long startTime = System.currentTimeMillis();
			int n = countWord(Words);
			
			// Print the longest word and the number of words made of other words
			System.out.println("1st longest word in the file that can be constructed  :  "
							+ Words[arrIndex.get(0)]);
			if(n > 1)
				System.out.println("2st longest word in the file that can be constructed  :  "
						+ Words[arrIndex.get(1)]);
			else
				System.out.println("2st longest word in the file that can be constructed "
						+ "has not been found.");
			System.out.println("Total number of words that can be made of other words :  "
							+ n);
			
			// end time
			long endTime = System.currentTimeMillis();

			// Print the time taken by the system for this program
			System.out.println("Runtime  : " + (endTime - startTime) + " ms");

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public static String[] createGiantArray() throws FileNotFoundException {
		List<String> list = new ArrayList<String>();
		
		//Path of input file 
		BufferedReader br = new BufferedReader(new FileReader(
				"E:/wordsforproblem.txt"));
		String str = null;
		try {
			while ((str = br.readLine()) != null && !"\n".equals(str)) {
				list.add(new String(str));
			}
			if (br != null)
				br.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		String[] Words = new String[list.size()];
		list.toArray(Words);
		return Words;
	}
}