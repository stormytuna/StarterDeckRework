package starterdeckrework.util;

import java.lang.reflect.Type;
import java.util.ArrayList;

public class GeneralUtils {
    public static String arrToString(Object[] arr) {
        if (arr == null)
            return null;
        if (arr.length == 0)
            return "";

        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < arr.length - 1; ++i) {
            sb.append(arr[i]).append(", ");
        }
        sb.append(arr[arr.length - 1]);
        return sb.toString();
    }

    public static ArrayList<String> swapAllInstances(ArrayList<String> arrayList, String a, String b) {
        for (int i = 0; i < arrayList.size(); i++) {
            if (arrayList.get(i) == a) {
                arrayList.set(i, b);
            }
        }

        return arrayList;
    }
}
