package br.com.arcnal.arcnal.infra.storage;

import br.com.arcnal.arcnal.domain.exception.ImagemInvalidaException;
import br.com.arcnal.arcnal.infra.config.FileStorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.PutObjectRequest;

import java.io.IOException;
import java.util.UUID;

@Service
@Slf4j
@RequiredArgsConstructor
public class SupabaseBlobStorageService {
    private final S3Client s3Client;
    private final FileStorageConfig fileStorageConfig;

    public String salvarArquivo(MultipartFile arquivo) {
        validarArquivo(arquivo);

        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = obterExtensao(nomeOriginal);
            String nomeUnico = UUID.randomUUID() + extensao;

            String bucket = fileStorageConfig.getContainerName();

            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket(bucket)
                    .key(nomeUnico)
                    .contentType(arquivo.getContentType())
                    .contentDisposition("inline")
                    .build();

            s3Client.putObject(
                    putObjectRequest,
                    RequestBody.fromBytes(arquivo.getBytes())
            );

            log.info("Arquivo salvo no container blob: {}", nomeUnico);

            return fileStorageConfig.getPublicUrl() + "/" + bucket + "/" + nomeUnico;

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
