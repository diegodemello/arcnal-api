package br.com.arcnal.arcnal.domain.enums;

public enum Cargo {
    USUARIO("USUARIO"),
    PROFESSOR("PROFESSOR"),
    ADMIN("ADMIN");

    private String cargo;

    Cargo(String cargo) {
        this.cargo = cargo;
    }

    public String getCargo() {
        return cargo;
    }
}
