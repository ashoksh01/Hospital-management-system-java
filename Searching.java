package Hospital;

import java.util.ArrayList;
import java.util.Vector;

// ============================== Searching==========================

public class Searching {

	static ArrayList<Vector<String>> res;
	static int pos = 0;

	public static ArrayList<Vector<String>> search(ArrayList<Vector<String>> data, String query, int pos) {
		res = new ArrayList<Vector<String>>();
		for (Vector<String> i : data) {
			if (i.get(pos).equals(query)) {
				res.add(i);
			}

		}
		return res;

	}

}
