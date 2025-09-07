package com.ductram.learningspring.service;

import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.time.Instant;
import java.util.Base64;
import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.client.RestTemplate;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;

@Service
public class GithubAppService {

    @Value("${github.app-id}")
    private String appId;
    @Value("${github.installation-id}")
    private String installationId;
    @Value("${github.private-key}")
    private Resource prevateKeyResource;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private final RestTemplate restTemplate = new RestTemplate();

    // Step 1: Create JWT, just alive in 10 mins
    private String generateJWT() throws Exception {
        String privateKeyPem = new String(FileCopyUtils.copyToByteArray(prevateKeyResource.getInputStream()),
                StandardCharsets.UTF_8);
        privateKeyPem = privateKeyPem.replace("-----BEGIN PRIVATE KEY-----", "")
                .replace("-----END PRIVATE KEY-----", "")
                .replaceAll("\\s", "");
        System.out.println("[DUC_LOG] privateKeyPem = " + privateKeyPem);
        byte[] pkcs8EncodedBytes = Base64.getDecoder().decode(privateKeyPem);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(pkcs8EncodedBytes);
        PrivateKey privateKey = KeyFactory.getInstance("RSA").generatePrivate(pkcs8EncodedKeySpec);

        Instant now = Instant.now();

        return Jwts.builder()
                .issuedAt(Date.from(now.minusSeconds(60)))
                .expiration(Date.from(now.plusSeconds(600))) // 10 phút
                .issuer(appId)
                // .signWith(privateKey, SignatureAlgorithm.RS256)
                .signWith(privateKey, Jwts.SIG.RS256)
                .compact();
    }

    // Create installation access token
    private String getInstallationToken() throws Exception {
        String jwt = generateJWT();
        String url = "https://api.github.com/app/installations/" + installationId + "/access_tokens";

        var headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "Bearer " + jwt);
        headers.add("Accept", "application/vnd.github+json");

        var requestEntity = new org.springframework.http.HttpEntity<>("", headers);

        var response = restTemplate.postForEntity(url, requestEntity, String.class);
        JsonNode json = objectMapper.readTree(response.getBody());

        return json.get("token").asText();
    }

    public JsonNode getContents(String url) throws Exception {
        String token = getInstallationToken();
//        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";

        var headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "token " + token);
        headers.add("Accept", "application/vnd.github+json");

        var requestEntity = new org.springframework.http.HttpEntity<>("", headers);

        var response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, requestEntity, String.class);
        Map<String, Object> map = objectMapper.readValue(response.getBody(), Map.class);
//        System.out.println(Base64.getDecoder().decode(map.get("content").toString()));
        return objectMapper.readTree(response.getBody());
    }

    // Use installation token to get API
    public JsonNode listIssues(String owner, String repo) throws Exception {
        String token = getInstallationToken();
        String url = "https://api.github.com/repos/" + owner + "/" + repo + "/issues";

        var headers = new org.springframework.http.HttpHeaders();
        headers.add("Authorization", "token " + token);
        headers.add("Accept", "application/vnd.github+json");

        var requestEntity = new org.springframework.http.HttpEntity<>("", headers);

        var response = restTemplate.exchange(url, org.springframework.http.HttpMethod.GET, requestEntity, String.class);

        return objectMapper.readTree(response.getBody());
    }
}
