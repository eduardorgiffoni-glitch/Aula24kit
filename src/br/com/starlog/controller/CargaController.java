package br.com.starlog.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import br.com.starlog.model.Carga;
import br.com.starlog.view.TelaCadastroCarga;

/** Orquestra a interação entre a View e o Model, sem regras de negócio na tela. */
public class CargaController {
    private final TelaCadastroCarga tela;
    private final List<Carga> cargas = new ArrayList<>();

    public CargaController(TelaCadastroCarga tela) {
        this.tela = tela;
        tela.aoCadastrar(this::cadastrarCarga);
        tela.atualizarRelatorio(Collections.unmodifiableList(cargas));
    }

    private void cadastrarCarga() {
        try {
            String codigo = tela.getCodigo();
            String categoria = tela.getCategoria();
            double peso = converterNumero(tela.getPeso());
            double valor = converterNumero(tela.getValor());

            Carga carga = new Carga(codigo, categoria, peso, valor);
            for (Carga existente : cargas) {
                if (existente.getCodigoRastreio().equalsIgnoreCase(carga.getCodigoRastreio())) {
                    throw new IllegalArgumentException("Já existe uma carga com esse código de rastreio.");
                }
            }
            cargas.add(carga);
            tela.atualizarRelatorio(Collections.unmodifiableList(cargas));
            tela.limparCampos();
            tela.exibirMensagemSucesso("Carga cadastrada com sucesso!");
        } catch (NumberFormatException e) {
            tela.exibirMensagemErro("Peso e valor devem ser números válidos. Exemplo: 12,5 ou 12.5.");
        } catch (IllegalArgumentException e) {
            tela.exibirMensagemErro(e.getMessage());
        }
    }

    private double converterNumero(String texto) {
        return Double.parseDouble(texto.trim().replace(',', '.'));
    }
}
