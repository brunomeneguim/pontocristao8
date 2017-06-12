package pontocristao.visao;

import java.awt.*;
import java.text.NumberFormat;
import java.util.Date;
import java.util.Locale;
import javax.swing.JOptionPane;
import javax.swing.text.NumberFormatter;
import pontocristao.controle.*;
import pontocristao.modelo.*;
import pontocristao.util.Utilidades;

/**
 *
 * @author Marcondes
 */
public class FrmCadastrarProduto extends javax.swing.JDialog {

    private static Frame frame;
    private ControleProduto controle;
    private Boolean modeloAtualizado = false;
    private java.util.List<Fornecedor> listaFornecedores;
    private java.util.List<TipoProduto> listaTiposProduto;

    public Boolean getModeloAtualizado() {
        return modeloAtualizado;
    }

    public Produto getProduto() {
        return controle.getProduto();
    }

    public FrmCadastrarProduto(java.awt.Frame parent, boolean modal, long id) {
        super(parent, modal);
        initComponents();

        setLocationRelativeTo(null);

        txtCodigo.setEnabled(false);

        jcDataCadastro.setDate(new Date());
        jcDataCadastro.setEnabled(false);

        txtNomeProduto.requestFocus();

        InicializarControle(id);
    }

    public static FrmCadastrarProduto Mostrar(java.awt.Frame parent, long id) {
        frame = parent;
        FrmCadastrarProduto frm = new FrmCadastrarProduto(parent, true, id);
        frm.setVisible(true);
        return frm;
    }

    private void InicializarControle(long id) {
        controle = new ControleProduto();

        listaFornecedores = controle.RetornarFornecedores();
        listaTiposProduto = controle.RetornarTiposProduto();

        for (Fornecedor fornecedor : listaFornecedores) {
            jComboFornecedor.addItem(RetornarDescricaoFornecedor(fornecedor));
        }

        for (TipoProduto tipoProduto : listaTiposProduto) {
            jComboTipoProduto.addItem(RetornarDescricaoTipoProduto(tipoProduto));
        }

        jComboFornecedor.setSelectedIndex(-1);
        jComboTipoProduto.setSelectedIndex(-1);

        if (id > 0) {
            Exception erro = controle.RecuperarProduto(id);

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {
                AtualizarCampos();
            }
        }
    }

    private String RetornarDescricaoFornecedor(Fornecedor fornecedor) {
        return fornecedor.getId() + " - " + fornecedor.getNomeFantasia() + " - " + fornecedor.getCnpj();
    }

    private String RetornarDescricaoTipoProduto(TipoProduto tipoProduto) {
        return tipoProduto.getId() + " - " + tipoProduto.getDescricao();
    }

    private void AtualizarCampos() {
        txtCodigo.setText(String.valueOf(controle.getProduto().getId()));
        jcDataCadastro.setDate(controle.getProduto().getDataCadastro());
        txtNomeProduto.setText(controle.getProduto().getNome());
        jspValor.setValue(controle.getProduto().getValorVenda());
        jspQuantidade.setValue(controle.getProduto().getQuantidade());
        jComboFornecedor.setSelectedItem(RetornarDescricaoFornecedor(controle.getProduto().getFornecedor()));
        jComboTipoProduto.setSelectedItem(RetornarDescricaoTipoProduto(controle.getProduto().getTipoProduto()));
    }

    private void AtualizarModelo() {
        controle.getProduto().setNome(txtNomeProduto.getText());
        controle.getProduto().setValorVenda((Double) jspValor.getValue());
        controle.getProduto().setQuantidade((Integer) jspQuantidade.getValue());

        String descricaoFornecedor = jComboFornecedor.getSelectedItem().toString();
        String descricaoTipoProduto = jComboTipoProduto.getSelectedItem().toString();

        for (Fornecedor fornecedor : listaFornecedores) {
            if (RetornarDescricaoFornecedor(fornecedor).equals(descricaoFornecedor)) {
                controle.getProduto().setFornecedor(fornecedor);
            }
        }

        for (TipoProduto tipoProduto : listaTiposProduto) {
            if (RetornarDescricaoTipoProduto(tipoProduto).equals(descricaoTipoProduto)) {
                controle.getProduto().setTipoProduto(tipoProduto);
            }
        }
    }

    public Boolean ValidaCampos() {
        Boolean retorno = true;

        if (txtNomeProduto.getText().equals("")
                || jspValor.getValue().toString().equals("")
                || jspQuantidade.getValue().toString().equals("")
                || jComboFornecedor.getSelectedIndex() < 0
                || jComboTipoProduto.getSelectedIndex() < 0) {
            retorno = false;
            JOptionPane.showMessageDialog(null, "Todos os campos em negrito devem estar preenchidos.");
        }

        return retorno;
    }

    @Override
    public void dispose() {
        if (controle != null) {
            controle.Dispose();
        }

        super.dispose();
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        lCodigoProduto = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lDataCadastro = new javax.swing.JLabel();
        jcDataCadastro = new com.toedter.calendar.JDateChooser();
        lNomeProduto = new javax.swing.JLabel();
        txtNomeProduto = new javax.swing.JTextField();
        lValorVenda = new javax.swing.JLabel();
        lQuantidade = new javax.swing.JLabel();
        jComboTipoProduto = new javax.swing.JComboBox<>();
        lTipoProduto = new javax.swing.JLabel();
        lFornecedor = new javax.swing.JLabel();
        BtnConfirmar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();
        jComboFornecedor = new javax.swing.JComboBox<>();
        jspQuantidade = new javax.swing.JSpinner();
        jspValor = new javax.swing.JSpinner();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Produtos");

        lCodigoProduto.setText("Código do Produto");

        txtCodigo.setEditable(false);
        txtCodigo.setBackground(new java.awt.Color(255, 255, 255));
        txtCodigo.setEnabled(false);

        lDataCadastro.setText("Data de Cadastro");

        lNomeProduto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lNomeProduto.setText("Nome do Produto*");

        lValorVenda.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lValorVenda.setText("Valor de Venda*");

        lQuantidade.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lQuantidade.setText("Quantidade*");

        lTipoProduto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lTipoProduto.setText("Tipo do Produto*");

        lFornecedor.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lFornecedor.setText("Fornecedor*");

        BtnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnConfirmar.png"))); // NOI18N
        BtnConfirmar.setText("Confirmar");
        BtnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConfirmarActionPerformed(evt);
            }
        });

        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnCancelar.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });

        jspQuantidade.setModel(new javax.swing.SpinnerNumberModel(0, 0, null, 1));

        jspValor.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        jspValor.setEditor(new javax.swing.JSpinner.NumberEditor(jspValor, "R$ ###,###.##"));

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addGroup(layout.createSequentialGroup()
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(lCodigoProduto))
                            .addGap(186, 186, 186)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(lDataCadastro)
                                .addComponent(jcDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addComponent(lNomeProduto)
                        .addComponent(txtNomeProduto))
                    .addComponent(lFornecedor)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lValorVenda)
                            .addComponent(jspValor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lQuantidade)
                            .addComponent(jspQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, 81, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jComboTipoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, 218, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(lTipoProduto)))
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(layout.createSequentialGroup()
                            .addComponent(BtnConfirmar)
                            .addGap(18, 18, 18)
                            .addComponent(BtnCancelar))
                        .addComponent(jComboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, 485, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCodigoProduto)
                    .addComponent(lDataCadastro))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lNomeProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txtNomeProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lValorVenda)
                    .addComponent(lQuantidade)
                    .addComponent(lTipoProduto))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jComboTipoProduto, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspQuantidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lFornecedor)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jComboFornecedor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCancelar)
                    .addComponent(BtnConfirmar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmarActionPerformed
        if (ValidaCampos()) {
            AtualizarModelo();

            Exception erro = controle.Salvar();

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {
                modeloAtualizado = true;
                this.dispose();
            }
        }
    }//GEN-LAST:event_BtnConfirmarActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        Object[] botoes = {"Sim", "Não"};
        int resposta = JOptionPane.showOptionDialog(null,
                "Deseja cancelar o cadastro de Produto? ",
                "Confirmação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                botoes, botoes[0]);
        if (resposta == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_BtnCancelarActionPerformed

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarProduto.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCadastrarProduto dialog = new FrmCadastrarProduto(new javax.swing.JFrame(), true, 0);
                dialog.addWindowListener(new java.awt.event.WindowAdapter() {
                    @Override
                    public void windowClosing(java.awt.event.WindowEvent e) {
                        System.exit(0);
                    }
                });
                dialog.setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnConfirmar;
    private javax.swing.JComboBox<String> jComboFornecedor;
    private javax.swing.JComboBox<String> jComboTipoProduto;
    private com.toedter.calendar.JDateChooser jcDataCadastro;
    private javax.swing.JSpinner jspQuantidade;
    private javax.swing.JSpinner jspValor;
    private javax.swing.JLabel lCodigoProduto;
    private javax.swing.JLabel lDataCadastro;
    private javax.swing.JLabel lFornecedor;
    private javax.swing.JLabel lNomeProduto;
    private javax.swing.JLabel lQuantidade;
    private javax.swing.JLabel lTipoProduto;
    private javax.swing.JLabel lValorVenda;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtNomeProduto;
    // End of variables declaration//GEN-END:variables
}
