package br.com.arcnal.arcnal.infra.config;

import br.com.arcnal.arcnal.infra.storage.FileStorageService;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class FileStorageInitializer implements CommandLineRunner {

    private final FileStorageService fileStorageService;

    @Override
    public void run(String... args) throws Exception {
        fileStorageService.init();
    }
}
