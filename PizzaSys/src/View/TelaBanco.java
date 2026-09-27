package View;

import java.awt.EventQueue;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.SystemColor;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;

import Controller.BancoController;
import Model.ConfiguracaoBanco;

public class TelaBanco extends JFrame {

    private static final long serialVersionUID = 1L;

    private JPanel contentPane;

    private JTextField textFieldServidor;
    private JPasswordField passwordFieldSenha;
    private JButton btnSalvar;

    private JLabel lblTeste;
    private JLabel lblUsuario;
    private JTextField textFieldUsuario;

    private JLabel lblBanco;
    private JTextField textFieldBanco;

    public TelaBanco() {

        setTitle("Configuração do Banco - PizzaSys");

        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        setBounds(100, 100, 311, 346);

        contentPane = new JPanel();

        contentPane.setBorder(
                new EmptyBorder(10, 10, 10, 10)
        );

        setContentPane(contentPane);

        GridBagLayout gbl_contentPane =
                new GridBagLayout();

        gbl_contentPane.columnWeights =
                new double[] { 0.0, 1.0 };

        contentPane.setLayout(gbl_contentPane);

        JLabel lblPizzaSys =
                new JLabel("PizzaSys");

        GridBagConstraints gbc_lblPizzaSys =
                new GridBagConstraints();

        gbc_lblPizzaSys.gridwidth = 2;
        gbc_lblPizzaSys.insets =
                new Insets(0, 0, 20, 0);

        gbc_lblPizzaSys.gridx = 0;
        gbc_lblPizzaSys.gridy = 0;

        contentPane.add(
                lblPizzaSys,
                gbc_lblPizzaSys
        );

        JLabel lblServidor =
                new JLabel("Servidor:");

        GridBagConstraints gbc_lblServidor =
                new GridBagConstraints();

        gbc_lblServidor.anchor =
                GridBagConstraints.EAST;

        gbc_lblServidor.insets =
                new Insets(5, 5, 5, 5);

        gbc_lblServidor.gridx = 0;
        gbc_lblServidor.gridy = 1;

        contentPane.add(
                lblServidor,
                gbc_lblServidor
        );

        textFieldServidor =
                new JTextField();

        GridBagConstraints gbc_textFieldServidor =
                new GridBagConstraints();

        gbc_textFieldServidor.fill =
                GridBagConstraints.HORIZONTAL;

        gbc_textFieldServidor.weightx = 1.0;

        gbc_textFieldServidor.insets =
                new Insets(5, 5, 5, 0);

        gbc_textFieldServidor.gridx = 1;
        gbc_textFieldServidor.gridy = 1;

        contentPane.add(
                textFieldServidor,
                gbc_textFieldServidor
        );

        textFieldServidor.setColumns(10);

        lblUsuario =
                new JLabel("Usuário:");

        GridBagConstraints gbc_lblUsuario =
                new GridBagConstraints();

        gbc_lblUsuario.anchor =
                GridBagConstraints.EAST;

        gbc_lblUsuario.insets =
                new Insets(0, 0, 5, 5);

        gbc_lblUsuario.gridx = 0;
        gbc_lblUsuario.gridy = 2;

        contentPane.add(
                lblUsuario,
                gbc_lblUsuario
        );

        textFieldUsuario =
                new JTextField();

        GridBagConstraints gbc_textFieldUsuario =
                new GridBagConstraints();

        gbc_textFieldUsuario.insets =
                new Insets(5, 5, 5, 0);

        gbc_textFieldUsuario.fill =
                GridBagConstraints.HORIZONTAL;

        gbc_textFieldUsuario.gridx = 1;
        gbc_textFieldUsuario.gridy = 2;

        contentPane.add(
                textFieldUsuario,
                gbc_textFieldUsuario
        );

        textFieldUsuario.setColumns(10);

        JLabel lblSenha =
                new JLabel("Senha:");

        GridBagConstraints gbc_lblSenha =
                new GridBagConstraints();

        gbc_lblSenha.anchor =
                GridBagConstraints.EAST;

        gbc_lblSenha.insets =
                new Insets(5, 5, 5, 5);

        gbc_lblSenha.gridx = 0;
        gbc_lblSenha.gridy = 3;

        contentPane.add(
                lblSenha,
                gbc_lblSenha
        );

        passwordFieldSenha =
                new JPasswordField();

        GridBagConstraints gbc_passwordFieldSenha =
                new GridBagConstraints();

        gbc_passwordFieldSenha.fill =
                GridBagConstraints.HORIZONTAL;

        gbc_passwordFieldSenha.weightx = 1.0;

        gbc_passwordFieldSenha.insets =
                new Insets(5, 5, 5, 0);

        gbc_passwordFieldSenha.gridx = 1;
        gbc_passwordFieldSenha.gridy = 3;

        contentPane.add(
                passwordFieldSenha,
                gbc_passwordFieldSenha
        );

        lblBanco =
                new JLabel("Banco:");

        GridBagConstraints gbc_lblBanco =
                new GridBagConstraints();

        gbc_lblBanco.anchor =
                GridBagConstraints.EAST;

        gbc_lblBanco.insets =
                new Insets(0, 0, 5, 5);

        gbc_lblBanco.gridx = 0;
        gbc_lblBanco.gridy = 4;

        contentPane.add(
                lblBanco,
                gbc_lblBanco
        );

        textFieldBanco =
                new JTextField();

        GridBagConstraints gbc_textFieldBanco =
                new GridBagConstraints();

        gbc_textFieldBanco.weightx = 1.0;

        gbc_textFieldBanco.insets =
                new Insets(5, 5, 5, 0);

        gbc_textFieldBanco.fill =
                GridBagConstraints.HORIZONTAL;

        gbc_textFieldBanco.gridx = 1;
        gbc_textFieldBanco.gridy = 4;

        contentPane.add(
                textFieldBanco,
                gbc_textFieldBanco
        );

        textFieldBanco.setColumns(10);

        btnSalvar =
                new JButton("Salvar");

        btnSalvar.addActionListener(e ->
                salvarConfiguracao()
        );

        GridBagConstraints gbc_btnSalvar =
                new GridBagConstraints();

        gbc_btnSalvar.gridwidth = 2;

        gbc_btnSalvar.insets =
                new Insets(20, 5, 5, 0);

        gbc_btnSalvar.gridx = 0;
        gbc_btnSalvar.gridy = 5;

        contentPane.add(
                btnSalvar,
                gbc_btnSalvar
        );

        lblTeste =
                new JLabel("Configuração carregada");

        lblTeste.setForeground(
                SystemColor.textHighlight
        );

        GridBagConstraints gbc_lblTeste =
                new GridBagConstraints();

        gbc_lblTeste.gridwidth = 2;

        gbc_lblTeste.insets =
                new Insets(5, 5, 5, 0);

        gbc_lblTeste.gridx = 0;
        gbc_lblTeste.gridy = 6;

        contentPane.add(
                lblTeste,
                gbc_lblTeste
        );

        carregarConfiguracao();
    }

    private void carregarConfiguracao() {

        ConfiguracaoBanco configuracao =
                BancoController.carregarConfiguracao();

        if (configuracao == null) {

            lblTeste.setText(
                    "Erro ao carregar configuração"
            );

            return;
        }

        textFieldServidor.setText(
                configuracao.getServidor()
        );

        textFieldUsuario.setText(
                configuracao.getUsuario()
        );

        passwordFieldSenha.setText(
                configuracao.getSenha()
        );

        textFieldBanco.setText(
                configuracao.getBanco()
        );
    }

    private void salvarConfiguracao() {

        String servidor =
                textFieldServidor.getText();

        String usuario =
                textFieldUsuario.getText();

        String senha =
                new String(
                        passwordFieldSenha.getPassword()
                );

        String banco =
                textFieldBanco.getText();

        String erro =
                BancoController.salvarConfiguracao(
                        servidor,
                        usuario,
                        senha,
                        banco
                );

        if (erro != null) {

            JOptionPane.showMessageDialog(
                    this,
                    erro,
                    "Erro",
                    JOptionPane.ERROR_MESSAGE
            );

            return;
        }

        lblTeste.setText(
                "Configuração salva"
        );

        JOptionPane.showMessageDialog(
                this,
                "As configurações do banco foram salvas.",
                "PizzaSys",
                JOptionPane.INFORMATION_MESSAGE
        );
    }

    public static void main(String[] args) {

        EventQueue.invokeLater(() -> {

            TelaBanco frame =
                    new TelaBanco();

            frame.setVisible(true);
        });
    }
}