package br.com.arcnal.arcnal.infra.storage;

import br.com.arcnal.arcnal.domain.exception.ImagemInvalidaException;
import br.com.arcnal.arcnal.infra.config.FileStorageConfig;
import com.azure.storage.blob.BlobClient;
import com.azure.storage.blob.BlobContainerClient;
import com.azure.storage.blob.BlobServiceClient;
import com.azure.storage.blob.implementation.util.BlobHeadersAndQueryParameters;
import com.azure.storage.blob.models.BlobHttpHeaders;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.UUID;

@Service
@ConditionalOnProperty(name = "spring.cloud.azure.storage.blob.enabled", havingValue = "true", matchIfMissing = true)
@Slf4j
@RequiredArgsConstructor
public class AzureBlobStorageService {
    private final BlobServiceClient blobServiceClient;
    private final FileStorageConfig fileStorageConfig;

    public void init(){
        try {
            BlobContainerClient containerClient = blobServiceClient
                    .getBlobContainerClient(fileStorageConfig.getContainerName());

            if (!containerClient.exists()) {
                containerClient.create();
                log.info("Container criado: {}", fileStorageConfig.getContainerName());
            }
        } catch (Exception e) {
            log.error("Erro ao criar container: {}", e.getMessage());
            throw new ImagemInvalidaException("Não foi possível iniciar o container no Azure");
        }
    }

    public String salvarArquivo(MultipartFile arquivo) {
        validarArquivo(arquivo);

        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = obterExtensao(nomeOriginal);
            String nomeUnico = UUID.randomUUID() + extensao;

            BlobContainerClient containerClient = blobServiceClient
                    .getBlobContainerClient(fileStorageConfig.getContainerName());

            BlobClient blobClient = containerClient.getBlobClient(nomeUnico);

            BlobHttpHeaders headers = new BlobHttpHeaders()
                    .setContentType(arquivo.getContentType())
                            .setContentDisposition("inline");

            blobClient.uploadWithResponse(
                    arquivo.getInputStream(),
                    arquivo.getSize(),
                    null,
                    headers,
                    null,
                    null,
                    null,
                    null,
                    null
            );

            // blobClient.upload(arquivo.getInputStream(), arquivo.getSize(), true);

            log.info("Arquivo salvo no container blob: {}", nomeUnico);

            return blobClient.getBlobUrl();

        } catch (IOException e) {
            log.error("Erro ao fazer upload: {}", e.getMessage());
            throw new ImagemInvalidaException("Erro ao salvar o arquivo no Blob Storage");
        }
    }

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo.isEmpty()) {
            throw new ImagemInvalidaException("Arquivo está vazio");
        }

        if (arquivo.getSize() > fileStorageConfig.getMaxSize()) {
            throw new ImagemInvalidaException("Arquivo excede o tamanho máximo permitido de 5MB");
        }

        String nomeArquivo = arquivo.getOriginalFilename();
        if (nomeArquivo == null || !nomeArquivo.toLowerCase().matches(".*\\.(jpg|jpeg|png)$")) {
            throw new ImagemInvalidaException("Apenas arquivos JPG, JPEG e PNG são permitidos");
        }
    }

    private String obterExtensao(String nomeArquivo) {
        if (nomeArquivo == null) {
            return "";
        }
        int ultimoPonto = nomeArquivo.lastIndexOf('.');
        return ultimoPonto > 0 ? nomeArquivo.substring(ultimoPonto) : "";
    }
}
