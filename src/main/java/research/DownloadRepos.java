package research;

import org.kohsuke.github.GHRepository;
import org.kohsuke.github.GitHub;
import org.kohsuke.github.GitHubBuilder;

import java.io.*;

import java.net.URL;
import java.net.URLConnection;
import java.util.List;
import java.util.Scanner;

public class DownloadRepos {
    /**
     * Download repos and authenticate
     * In order to authenticate, create an auth.txt file. It should have 2 lines: first is the personal access token and second one is the username
     */
    public static void main(String[] args) throws IOException {
        // authenticate first
        String authFileName = "auth.txt";
        List<String> auth = Authenticate.getAuth(authFileName);
        GitHub gitHub = new GitHubBuilder().withOAuthToken(auth.get(0), auth.get(1)).build();
        // download selected repos
        String reposFileName = "repos.txt";
        downloadRepos(reposFileName, gitHub);

    }

    /**
     * Download all the specified repos in resources/repos.txt
     * repos here should actually be owner/repo_name, see the file for examples
     * @param fileName - name of repos file
     * @param gitHub - authentication details
     */
    private static void downloadRepos(String fileName, GitHub gitHub) {
        try {
            String path = "src/main/java/research/resources/" + fileName;
            File file = new File(path);

            Scanner reader = new Scanner(file);
            int counter = 0;
            while(reader.hasNextLine()) {
                downloadRepo(reader.nextLine(), gitHub, counter);
                counter ++;
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * Download each repo from the list
     */
    private static void downloadRepo(String repo, GitHub gitHub, int counter) {
        try {
//            actually this method does not require authentication to download public repos
//            GHRepository repository = gitHub.getRepository(repo);
            URL zipFileUrl = new URL("https://github.com/" + repo + "/archive/master.zip");

            URLConnection urlConnection = zipFileUrl.openConnection();
            InputStream inputStream = new BufferedInputStream(urlConnection.getInputStream());

            FileOutputStream fileOutputStream = new FileOutputStream("downloads/repo" + counter + ".zip");
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = inputStream.read(buffer)) != -1) {
                fileOutputStream.write(buffer, 0, bytesRead);
            }
            fileOutputStream.close();
            inputStream.close();
            System.out.println("Repository downloaded successfully!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
