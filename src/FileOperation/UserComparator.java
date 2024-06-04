import java.util.Comparator;
import java.util.Map;

public class UserComparator implements Comparator<String> {
    Map<String, index> map;

    public UserComparator(Map<String, index> map) {
        this.map = map;
    }

    public int compare(String o1, String o2) {
        if (map.get(o2).getFreq() > map.get(o1).getFreq())
            return 1;
        else {
            if (map.get(o2).getFreq() < map.get(o1).getFreq())
                return -1;
            else
                return o1.compareTo(o2);

        }
    }
}
