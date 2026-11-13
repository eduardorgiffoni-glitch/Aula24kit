package br.com.starlog.view;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.List;
import java.util.Locale;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import br.com.starlog.model.Carga;

/** Interface Swing: recebe dados, apresenta erros e exibe o relatório. */
public class TelaCadastroCarga extends JFrame {
    private final JTextField campoCodigo = new JTextField(15);
    private final JComboBox<String> campoCategoria = new JComboBox<>(new String[] {
        "NORMAL", "FRAGIL", "PERECIVEL", "PERIGOSA"
    });
    private final JTextField campoPeso = new JTextField(10);
    private final JTextField campoValor = new JTextField(10);
    private final JButton botaoCadastrar = new JButton("Cadastrar carga");
    private final DefaultTableModel modeloTabela = new DefaultTableModel(
        new Object[] {"Rastreio", "Categoria", "Peso (kg)", "Valor (R$)", "Seguro (R$)"}, 0) {
        @Override public boolean isCellEditable(int row, int col) { return false; }
    };
    private final JTextArea relatorio = new JTextArea(7, 50);

    public TelaCadastroCarga() {
        super("StarLog Logistics | Cadastro de Cargas");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel formulario = new JPanel(new GridLayout(4, 2, 8, 8));
        formulario.setBorder(BorderFactory.createTitledBorder("Dados da carga"));
        formulario.add(new JLabel("Código de rastreio:"));
        formulario.add(campoCodigo);
        formulario.add(new JLabel("Categoria:"));
        formulario.add(campoCategoria);
        formulario.add(new JLabel("Peso (kg):"));
        formulario.add(campoPeso);
        formulario.add(new JLabel("Valor declarado (R$):"));
        formulario.add(campoValor);

        JPanel topo = new JPanel(new BorderLayout());
        topo.add(formulario, BorderLayout.CENTER);
        JPanel botoes = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        botoes.add(botaoCadastrar);
        topo.add(botoes, BorderLayout.SOUTH);
        add(topo, BorderLayout.NORTH);

        JTable tabela = new JTable(modeloTabela);
        add(new JScrollPane(tabela), BorderLayout.CENTER);

        relatorio.setEditable(false);
        relatorio.setLineWrap(true);
        relatorio.setWrapStyleWord(true);
        JScrollPane painelRelatorio = new JScrollPane(relatorio);
        painelRelatorio.setBorder(BorderFactory.createTitledBorder("Relatório de despacho"));
        add(painelRelatorio, BorderLayout.SOUTH);

        setSize(820, 570);
        setLocationRelativeTo(null);
    }

    public String getCodigo() { return campoCodigo.getText(); }
    public String getCategoria() { return (String) campoCategoria.getSelectedItem(); }
    public String getPeso() { return campoPeso.getText(); }
    public String getValor() { return campoValor.getText(); }

    public void aoCadastrar(Runnable acao) { botaoCadastrar.addActionListener(e -> acao.run()); }
    public void exibirMensagemErro(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Não foi possível cadastrar", JOptionPane.ERROR_MESSAGE);
    }
    public void exibirMensagemSucesso(String mensagem) {
        JOptionPane.showMessageDialog(this, mensagem, "Cadastro realizado", JOptionPane.INFORMATION_MESSAGE);
    }
    public void limparCampos() {
        campoCodigo.setText("");
        campoPeso.setText("");
        campoValor.setText("");
        campoCategoria.setSelectedIndex(0);
        campoCodigo.requestFocusInWindow();
    }
    public void atualizarRelatorio(List<Carga> cargas) {
        modeloTabela.setRowCount(0);
        StringBuilder texto = new StringBuilder("STARLOG - RELATÓRIO DE DESPACHO\n\n");
        double totalSeguros = 0;
        for (Carga carga : cargas) {
            double seguro = carga.calcularSeguro();
            totalSeguros += seguro;
            modeloTabela.addRow(new Object[] {
                carga.getCodigoRastreio(), carga.getCategoria(),
                String.format(Locale.forLanguageTag("pt-BR"), "%.2f", carga.getPesoKg()),
                String.format(Locale.forLanguageTag("pt-BR"), "%.2f", carga.getValorDeclarado()),
                String.format(Locale.forLanguageTag("pt-BR"), "%.2f", seguro)
            });
            texto.append(String.format(Locale.forLanguageTag("pt-BR"),
                "%s | %s | %.2f kg | Seguro: R$ %.2f%n",
                carga.getCodigoRastreio(), carga.getCategoria(), carga.getPesoKg(), seguro));
        }
        texto.append(String.format(Locale.forLanguageTag("pt-BR"),
            "%nCargas cadastradas: %d%nTotal de seguros: R$ %.2f",
            cargas.size(), totalSeguros));
        relatorio.setText(texto.toString());
    }
}
