package br.com.starlog.model;

import java.util.Locale;

/** Representa uma carga e centraliza as regras de validação e seguro. */
public class Carga {
    private final String codigoRastreio;
    private final String categoria;
    private final double pesoKg;
    private final double valorDeclarado;

    public Carga(String codigoRastreio, String categoria, double pesoKg, double valorDeclarado) {
        if (codigoRastreio == null || codigoRastreio.trim().isEmpty()) {
            throw new IllegalArgumentException("O código de rastreio é obrigatório.");
        }
        if (categoria == null || categoria.trim().isEmpty()) {
            throw new IllegalArgumentException("A categoria é obrigatória.");
        }
        if (!Double.isFinite(pesoKg) || pesoKg <= 0) {
            throw new IllegalArgumentException("O peso deve ser maior que zero.");
        }
        if (!Double.isFinite(valorDeclarado) || valorDeclarado <= 0) {
            throw new IllegalArgumentException("O valor declarado deve ser maior que zero.");
        }
        this.codigoRastreio = codigoRastreio.trim();
        this.categoria = categoria.trim().toUpperCase(Locale.ROOT);
        this.pesoKg = pesoKg;
        this.valorDeclarado = valorDeclarado;
    }

    public String getCodigoRastreio() { return codigoRastreio; }
    public String getCategoria() { return categoria; }
    public double getPesoKg() { return pesoKg; }
    public double getValorDeclarado() { return valorDeclarado; }

    public double calcularSeguro() {
        double seguro = valorDeclarado * 0.02;
        if ("PERIGOSA".equals(categoria) || pesoKg > 100) {
            seguro += 150.00;
        }
        return seguro;
    }
}
