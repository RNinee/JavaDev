package com.springboot.devconnector.services;

import com.springboot.devconnector.dto.github.GitHubRepo;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.client.HttpClientErrorException;

import java.util.Arrays;
import java.util.List;

@Service
public class GitHubService {

    private static final String GITHUB_API_URL = "https://api.github.com/users/{username}/repos";

    public List<GitHubRepo> getUserRepos(String username) {
        try {
            RestTemplate restTemplate = new RestTemplate();

            // Add query parameters for sorting and limiting results
            String url = GITHUB_API_URL + "?sort=created&direction=desc&per_page=5";

            GitHubRepo[] repos = restTemplate.getForObject(
                    url,
                    GitHubRepo[].class,
                    username
            );

            if (repos == null) {
                throw new RuntimeException("No GitHub profile found");
            }

            return Arrays.asList(repos);
        } catch (HttpClientErrorException.NotFound e) {
            throw new RuntimeException("No GitHub profile found");
        } catch (Exception e) {
            throw new RuntimeException("Error fetching GitHub repos: " + e.getMessage());
        }
    }
}
