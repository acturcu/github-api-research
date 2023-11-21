package research;

import org.kohsuke.github.GHContent;
import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Scanner;


/**
 * Class used to write the readme of the selected repositories into a different folder
 */
public class GetReadMe {
    public static void main(String[] args) throws IOException {
        String authFileName = "auth.txt";
        List<String> auth = Authenticate.getAuth(authFileName);
        GitHub gitHub = new GitHubBuilder().withOAuthToken(auth.get(0), auth.get(1)).build();
        String reposFileName = "repos.txt";
        getAllReadMe(reposFileName, gitHub);
    }

    public static void getAllReadMe(String fileName, GitHub gitHub){
        try {
            String path = "src/main/java/research/resources/" + fileName;
            File file = new File(path);

            Scanner reader = new Scanner(file);
            int counter = 0;
            while(reader.hasNextLine()) {
                getReadMe(reader.nextLine(), gitHub, counter);
                counter ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public static void getReadMe(String repoLink, GitHub gitHub, int counter) {
        try {
            GHRepository repo = gitHub.getRepository(repoLink);
            GHContent readme = repo.getReadme();
            String readmeString = null;
            try (Scanner scanner = new Scanner(readme.read(), StandardCharsets.UTF_8.name())) {
                readmeString = scanner.useDelimiter("\\A").next();
            }
            String filePath = "documentation/readme/readme" + counter;

            byte[] byteContent = readmeString.getBytes();

            Path path = Paths.get(filePath);

            Files.write(path, byteContent);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
