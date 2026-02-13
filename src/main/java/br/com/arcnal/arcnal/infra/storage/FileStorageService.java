package br.com.arcnal.arcnal.infra.storage;

import br.com.arcnal.arcnal.domain.exception.ImagemInvalidaException;
import br.com.arcnal.arcnal.infra.config.FileStorageConfig;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class FileStorageService {
    private final FileStorageConfig config;

    public void init() {
        try {
            Path uploadPath = Paths.get(config.getUploadDir());
            if (!Files.exists(uploadPath)) {
                Files.createDirectories(uploadPath);
                log.info("Diretório de upload criado: {}", uploadPath);
            }
        } catch (IOException e) {
            throw new ImagemInvalidaException("Não foi possível criar o diretório de upload");
        }
    }

    public String salvarArquivo(MultipartFile arquivo) {
        validarArquivo(arquivo);

        try {
            String nomeOriginal = arquivo.getOriginalFilename();
            String extensao = obterExtensao(nomeOriginal);
            String nomeUnico = UUID.randomUUID() + extensao;

            Path uploadPath = Paths.get(config.getUploadDir());
            Path caminhoCompleto = uploadPath.resolve(nomeUnico);

            Files.copy(arquivo.getInputStream(), caminhoCompleto, StandardCopyOption.REPLACE_EXISTING);

            log.info("Arquivo salvo: {}", nomeUnico);

            return config.getUploadDir() + "/" + nomeUnico;

        } catch (IOException e) {
            throw new ImagemInvalidaException("Erro ao salvar o arquivo");
        }
    }

    private void validarArquivo(MultipartFile arquivo) {
        if (arquivo.isEmpty()) {
            throw new ImagemInvalidaException("Arquivo está vazio");
        }

        if (arquivo.getSize() > config.getMaxSize()) {
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
