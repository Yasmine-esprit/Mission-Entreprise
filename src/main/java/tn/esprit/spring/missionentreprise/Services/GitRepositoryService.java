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

    // Create a new repository
    public String createGitHubRepository(String repoName) throws IOException {
        String apiUrl = "https://api.github.com/user/repos";
        String json = "{ \"name\": \"" + repoName + "\", \"private\": false }";

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

    public List<String> listLocalRepositories() {
    File baseDir = new File(REPO_BASE_PATH);
    String[] repos = baseDir.list((current, name) -> new File(current, name).isDirectory());
    return repos != null ? List.of(repos) : List.of();
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
