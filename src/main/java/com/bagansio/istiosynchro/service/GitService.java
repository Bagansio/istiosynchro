package com.bagansio.istiosynchro.service;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class GitService {

    @Value("${git.token}") // Inject the Git token from application.properties or environment
    private String gitToken;

    @Value("${git.url}") // Inject the Git URL from application.properties or environment
    private String gitUrl;

    public void commitAndPushYamlToRepo(String branch, String yamlContent) throws IOException, GitAPIException {
        // Create a temporary directory for the repository
        File localPath = createTempDirectory();

        try {
            // Clone the repository using the Git token
            cloneRepository(gitUrl, branch, localPath);

            // Write the YAML content to a file in the /cluster subfolder
            writeYamlToFile(localPath, "service-entry2.yaml", yamlContent);

            // Commit and push the changes
            commitAndPushChanges(localPath);
        } finally {
            // Clean up the temporary directory
            deleteDirectory(localPath);
        }
    }

    private File createTempDirectory() throws IOException {
        File tempDir = File.createTempFile("git-repo", "");
        if (!tempDir.delete() || !tempDir.mkdir()) {
            throw new IOException("Failed to create temporary directory for Git repository.");
        }
        return tempDir;
    }

    private void cloneRepository(String repoUrl, String branch, File localPath) throws GitAPIException {
        Git.cloneRepository()
                .setURI(repoUrl)
                .setDirectory(localPath)
                .setBranch(branch)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitToken, ""))  // Use injected token
                .call();
    }

    private void writeYamlToFile(File repoDir, String fileName, String yamlContent) throws IOException {
        // Define the folder where the YAML file will be written (e.g., "cluster")
        File clusterFolder = new File(repoDir, "cluster");

        // Create the folder if it doesn't exist
        if (!clusterFolder.exists()) {
            if (!clusterFolder.mkdirs()) {
                throw new IOException("Failed to create directory: " + clusterFolder.getAbsolutePath());
            }
        }

        // Create the YAML file inside the /cluster folder
        File yamlFile = new File(clusterFolder, fileName);
        try (FileWriter writer = new FileWriter(yamlFile)) {
            writer.write(yamlContent);
        }
    }

    private void commitAndPushChanges(File repoDir) throws IOException, GitAPIException {
        try (Git git = Git.open(repoDir)) {
            // Add the file in the /cluster subfolder
            git.add().addFilepattern("cluster/service-entry2.yaml").call();

            // Commit the changes
            git.commit().setMessage("Add ServiceEntry YAML").call();

            // Push the changes
            git.push()
                    .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitToken, ""))  // Use injected token
                    .call();
        }
    }

    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}