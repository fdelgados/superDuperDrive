package com.udacity.jwdnd.course1.cloudstorage.services;

import com.udacity.jwdnd.course1.cloudstorage.mapper.CredentialMapper;
import com.udacity.jwdnd.course1.cloudstorage.model.Credential;
import com.udacity.jwdnd.course1.cloudstorage.model.CredentialDto;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;

@Service
public class CredentialService {
    private final CredentialMapper credentialMapper;
    private final EncryptionService encryptionService;

    public CredentialService(CredentialMapper credentialMapper, EncryptionService encryptionService) {
        this.credentialMapper = credentialMapper;
        this.encryptionService = encryptionService;
    }

    public List<CredentialDto> getAllCredentials(Integer userId) {
        List<Credential> credentials = credentialMapper.searchByUser(userId);
        List<CredentialDto> credentialDtos = new ArrayList<>();

        for(Credential credential: credentials) {
            CredentialDto credentialDto = new CredentialDto();
            String plainPassword = encryptionService.decryptValue(credential.getPassword(), credential.getKey());

            credentialDto.setCredentialId(credential.getCredentialId());
            credentialDto.setUserId(credential.getUserId());
            credentialDto.setUrl(credential.getUrl());
            credentialDto.setUsername(credential.getUsername());
            credentialDto.setPassword(credential.getPassword());
            credentialDto.setPlainPassword(plainPassword);

            credentialDtos.add(credentialDto);
        }

        return credentialDtos;
    }

    public void saveCredential(CredentialDto credentialForm) {
        if (credentialForm.credentialExist()) {
            updateCredential(credentialForm);
            return;
        }

        createNewCredential(credentialForm);
    }

    private void updateCredential(CredentialDto credentialDto) {
        Credential credential = credentialMapper.search(credentialDto.getCredentialId());
        String password = encryptionService.encryptValue(credentialDto.getPlainPassword(), credential.getKey());

        credential.setPassword(password);
        credential.setUrl(credentialDto.getUrl());
        credential.setUsername(credentialDto.getUsername());

        credentialMapper.update(credential);
    }

    private void createNewCredential(CredentialDto credentialDto) {
        String key = generateEncryptionKey();
        String password = encryptionService.encryptValue(credentialDto.getPlainPassword(), key);

        Credential credential = new Credential(credentialDto.getCredentialId(),
                credentialDto.getUrl(),
                credentialDto.getUsername(),
                key,
                password,
                credentialDto.getUserId());

        credentialMapper.add(credential);
    }

    private String generateEncryptionKey() {
        SecureRandom random = new SecureRandom();
        byte[] key = new byte[16];
        random.nextBytes(key);

        return Base64.getEncoder().encodeToString(key);
    }
}
