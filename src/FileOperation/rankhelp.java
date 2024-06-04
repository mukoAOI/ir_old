import java.util.HashMap;

public class rankhelp {
    HashMap<String,Integer> tf;
    public rankhelp(){
        tf=new HashMap<>();
    }

    public void setTf(String filename) {
            if (tf.get(filename)==null)
                tf.put(filename,1);
            else
            {
                tf.put(filename,tf.get(filename)+1);
            }
    }

    public HashMap<String, Integer> getTf() {
        return tf;
    }


}
