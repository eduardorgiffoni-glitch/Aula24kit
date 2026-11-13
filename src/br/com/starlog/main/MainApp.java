package br.com.starlog.main;

import javax.swing.SwingUtilities;
import br.com.starlog.controller.CargaController;
import br.com.starlog.view.TelaCadastroCarga;

public final class MainApp {
    private MainApp() { }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            TelaCadastroCarga tela = new TelaCadastroCarga();
            new CargaController(tela);
            tela.setVisible(true);
        });
    }
}
