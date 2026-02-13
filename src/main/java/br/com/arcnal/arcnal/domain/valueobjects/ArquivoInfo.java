package br.com.arcnal.arcnal.domain.valueobjects;

import br.com.arcnal.arcnal.domain.exception.ImagemInvalidaException;
import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Embeddable
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ArquivoInfo {
    private static final Long TAMANHO_MAXIMO_ARQUIVO = 5 * 1024 * 1024L;

    private String nomeArquivo;
    @Column(nullable = false)
    private String caminho;
    private Long tamanho;

    public ArquivoInfo(String nomeArquivo, String caminho, Long tamanho) {
        validarCaminho(caminho);
        validarTamanhoArquivo(tamanho);
        validarExtesaoArquivo(nomeArquivo);

        this.nomeArquivo = nomeArquivo;
        this.caminho = caminho;
        this.tamanho = tamanho;
    }

    private void validarCaminho(String caminho) {
        if (caminho == null || caminho.isBlank()) {
            throw new ImagemInvalidaException("Caminho do arquivo não pode ser vazio.");
        }
    }

    private void validarTamanhoArquivo(Long tamanho){
        if (tamanho == null || tamanho <= 0) {
            throw new ImagemInvalidaException("Tamanho do arquivo deve ser maior que zero.");
        }
        if(tamanho > TAMANHO_MAXIMO_ARQUIVO){
            throw new ImagemInvalidaException("O tamanho do arquivo excede o limite permitido de 5MB.");
        }
    }

    private void validarExtesaoArquivo(String nomeArquivo){
        if(nomeArquivo != null && !nomeArquivo.toLowerCase().matches(".*\\.(jpg|jpeg|png)$")){
            throw new ImagemInvalidaException("A extensão do arquivo é inválida. Apenas arquivos JPG, JPEG e PNG são permitidos.");
        }
    }
}
