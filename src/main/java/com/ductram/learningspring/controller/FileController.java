package com.ductram.learningspring.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ductram.learningspring.service.GithubAppService;
import com.fasterxml.jackson.databind.JsonNode;

@RestController
public class FileController {

    private final GithubAppService gitHubAppService;

    public FileController(GithubAppService gitHubAppService) {
        this.gitHubAppService = gitHubAppService;
    }

    @GetMapping("/issues")
    public JsonNode getIssues() throws Exception {
        return gitHubAppService.listIssues("tramhuuducvn", "codility-learning");

    }

    @GetMapping("/content")
    public JsonNode getContents(@RequestParam String path) throws Exception {
        return gitHubAppService.getContents(String.format("https://api.github.com/repos/tramhuuducvn/%s", path));
    }


}
