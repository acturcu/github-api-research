package research;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Authenticate {
    /**
     * Read authentication details from file, such that they remain private
     * @return Authentication token and username
     */
    public static List<String> getAuth(String fileName)  {
        try {
            String path = "src/main/java/research/resources/" + fileName;
            File file = new File(path);

            Scanner reader = new Scanner(file);
            String auth = reader.nextLine();
            String user = reader.nextLine();
            List<String> ret = new ArrayList<String>();
            ret.add(auth);
            ret.add(user);
            return ret;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return new ArrayList<>();
    }

}
