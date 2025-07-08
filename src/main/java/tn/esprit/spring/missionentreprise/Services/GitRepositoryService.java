package tn.esprit.spring.missionentreprise.Services;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.ListBranchCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.util.io.DisabledOutputStream;
import org.springframework.stereotype.Service;

import java.net.URI;

import org.springframework.beans.factory.annotation.Value;
import java.net.http.HttpRequest;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Service
public class GitRepositoryService {

    @Value("${github.username}")
    private String githubUsername;

    @Value("${github.token}")
    private String githubToken;

    private final String REPO_BASE_PATH = "C:/Users/mahdi/Desktop/git-repos"; // Change this path

    // DTO class for repository information
    public static class RepositoryInfo {
        private String name;
        private String description;
        private boolean isPrivate;

        public RepositoryInfo(String name, String description, boolean isPrivate) {
            this.name = name;
            this.description = description;
            this.isPrivate = isPrivate;
        }

        // Getters and setters
        public String getName() { return name; }
        public void setName(String name) { this.name = name; }
        
        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }
        
        public boolean isPrivate() { return isPrivate; }
        public void setPrivate(boolean isPrivate) { this.isPrivate = isPrivate; }
    }

    // Create a new repository
    public String createGitHubRepository(String repoName, String description, boolean isPrivate) throws IOException {
        String apiUrl = "https://api.github.com/user/repos";
        String json = String.format(
            "{ \"name\": \"%s\", \"description\": \"%s\", \"private\": %s }",
            repoName,
            description != null ? description : "",
            isPrivate
        );

        CloseableHttpClient client = HttpClients.createDefault();
        HttpPost post = new HttpPost(apiUrl);
        post.setHeader("Authorization", "token " + githubToken);
        post.setHeader("Accept", "application/vnd.github.v3+json");
        post.setEntity(new StringEntity(json));

        HttpResponse response = client.execute(post);
        String responseBody = EntityUtils.toString(response.getEntity());

        if (response.getStatusLine().getStatusCode() == 201) {
            return "✅ Repository created on GitHub: " + repoName;
        } else {
            return "❌ Failed to create repo: " + responseBody;
        }
    }

    // Clone a remote repository
    public String cloneRepository(String remoteUrl, String localName) throws GitAPIException {
        File localPath = new File(REPO_BASE_PATH + "/" + localName);
        Git.cloneRepository()
                .setURI(remoteUrl)
                .setDirectory(localPath)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(githubUsername, githubToken))
                .call();
        return "Cloned repository to: " + localPath.getAbsolutePath();
    }

    // Commit and push changes to GitHub
    public String commitAndPushChanges(String repoName, String message) throws IOException, GitAPIException {
        File repoPath = new File(REPO_BASE_PATH + "/" + repoName);
        try (Git git = Git.open(repoPath)) {
            git.add().addFilepattern(".").call();
            git.commit().setMessage(message).call();

            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(githubUsername, githubToken))
                    .call();

            return "✅ Changes committed and pushed with message: " + message;
        }
    }

    // List branches
    public List<String> listBranches(String repoName) throws IOException, GitAPIException {
        List<String> branches = new ArrayList<>();
        try (Git git = Git.open(new File(REPO_BASE_PATH + "/" + repoName))) {
            List<Ref> refs = git.branchList()
                    .setListMode(ListBranchCommand.ListMode.ALL) // includes remote branches
                    .call();
            for (Ref ref : refs) {
                branches.add(ref.getName());
            }
        }
        return branches;
    }

    public List<RepositoryInfo> listLocalRepositories() {
        List<RepositoryInfo> repos = new ArrayList<>();
    File baseDir = new File(REPO_BASE_PATH);
        
        if (!baseDir.exists() || !baseDir.isDirectory()) {
            return repos;
        }
        
        String[] repoNames = baseDir.list((current, name) -> new File(current, name).isDirectory());
        
        if (repoNames != null) {
            for (String repoName : repoNames) {
                try {
                    // Try to get repository info from GitHub API
                    RepositoryInfo repoInfo = getRepositoryInfoFromGitHub(repoName);
                    if (repoInfo != null) {
                        repos.add(repoInfo);
                    } else {
                        // Fallback: create basic info if GitHub API fails
                        repos.add(new RepositoryInfo(repoName, "Local repository", false));
                    }
                } catch (Exception e) {
                    // Fallback: create basic info if there's an error
                    repos.add(new RepositoryInfo(repoName, "Local repository", false));
                }
            }
        }
        
        return repos;
    }

    // Helper method to get repository info from GitHub API
    private RepositoryInfo getRepositoryInfoFromGitHub(String repoName) throws IOException {
        String apiUrl = "https://api.github.com/repos/" + githubUsername + "/" + repoName;
        
        CloseableHttpClient client = HttpClients.createDefault();
        org.apache.http.client.methods.HttpGet get = new org.apache.http.client.methods.HttpGet(apiUrl);
        get.setHeader("Authorization", "token " + githubToken);
        get.setHeader("Accept", "application/vnd.github.v3+json");
        
        HttpResponse response = client.execute(get);
        
        if (response.getStatusLine().getStatusCode() == 200) {
            String responseBody = EntityUtils.toString(response.getEntity());
            // Parse JSON response to extract description and private status
            // For simplicity, we'll use basic string parsing
            // In a production environment, you might want to use a JSON parser like Jackson
            
            boolean isPrivate = responseBody.contains("\"private\":true");
            String description = extractDescription(responseBody);
            
            return new RepositoryInfo(repoName, description, isPrivate);
}

        return null;
    }

    // Helper method to extract description from JSON response
    private String extractDescription(String jsonResponse) {
        // Simple JSON parsing for description field
        int descIndex = jsonResponse.indexOf("\"description\":");
        if (descIndex != -1) {
            int startQuote = jsonResponse.indexOf("\"", descIndex + 15);
            if (startQuote != -1) {
                int endQuote = jsonResponse.indexOf("\"", startQuote + 1);
                if (endQuote != -1) {
                    return jsonResponse.substring(startQuote + 1, endQuote);
                }
            }
        }
        return "No description available";
    }

    // Compare last 2 commits (diff)
    public List<String> compareLastTwoCommits(String repoName) throws Exception {
        List<String> diffs = new ArrayList<>();
        try (Git git = Git.open(new File(REPO_BASE_PATH + "/" + repoName))) {
            Iterable<RevCommit> commits = git.log().setMaxCount(2).call();
            RevCommit[] commitsArray = new RevCommit[2];
            int i = 0;
            for (RevCommit commit : commits) {
                commitsArray[i++] = commit;
            }

            if (commitsArray[1] == null) {
                throw new IllegalStateException("Not enough commits to compare.");
            }

            AbstractTreeIterator oldTreeIter = prepareTreeParser(git.getRepository(), commitsArray[1]);
            AbstractTreeIterator newTreeIter = prepareTreeParser(git.getRepository(), commitsArray[0]);

            DiffFormatter formatter = new DiffFormatter(DisabledOutputStream.INSTANCE);
            formatter.setRepository(git.getRepository());
            List<DiffEntry> diffEntries = formatter.scan(oldTreeIter, newTreeIter);
            for (DiffEntry entry : diffEntries) {
                diffs.add(entry.getChangeType() + ": " + entry.getOldPath() + " -> " + entry.getNewPath());
            }
        }
        return diffs;
    }

    private AbstractTreeIterator prepareTreeParser(Repository repository, RevCommit commit) throws IOException {
        try (var walk = new org.eclipse.jgit.revwalk.RevWalk(repository)) {
            RevCommit parsedCommit = walk.parseCommit(commit);
            var tree = walk.parseTree(parsedCommit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (var reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();
            return treeParser;
        }
    }
}
