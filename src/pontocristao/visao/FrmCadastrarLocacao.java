package pontocristao.visao;

import java.awt.*;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableModel;
import org.hibernate.Transaction;
import pontocristao.controle.*;
import pontocristao.modelo.*;
import pontocristao.util.AutoComboBox;
import pontocristao.util.Utilidades;

/**
 *
 * @author Marcondes
 */
public class FrmCadastrarLocacao extends javax.swing.JDialog {

    private static Frame frame;
    private ControleLocacao controle;
    private Boolean modeloAtualizado = false;
    private java.util.List<Cliente> listaClientes;
    private java.util.List<Filme> listaFilmes;
    private DefaultTableModel modeloTabela;

    public Boolean getModeloAtualizado() {
        return modeloAtualizado;
    }

    public Locacao getLocacao() {
        return controle.getLocacao();
    }

    public FrmCadastrarLocacao(java.awt.Frame parent, boolean modal, long id) {
        super(parent, modal);
        initComponents();

        AutoComboBox.enable(cbxCliente);
        AutoComboBox.enable(cbxFilme);

        AjustarTabela();

        setLocationRelativeTo(null);

        jcDataLocacao.setDate(new Date());
        jcDataLocacao.setEnabled(false);

        cbxCliente.requestFocus();

        InicializarControle(id);
    }

    public static FrmCadastrarLocacao Mostrar(java.awt.Frame parent, long id) {
        frame = parent;
        FrmCadastrarLocacao frm = new FrmCadastrarLocacao(parent, true, id);
        frm.setVisible(true);
        return frm;
    }

    private void AjustarTabela() {
        String[] colunas = new String[]{"Nome", "Tipo de filme", "Valor da locação"};
        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        tblFilmes.setModel(modeloTabela);
        tblFilmes.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    }

    private void InicializarControle(long id) {
        controle = new ControleLocacao();

        listaClientes = controle.RetornarClientes();
        listaFilmes = controle.RetornarFilmes();

        for (Cliente cliente : listaClientes) {
            cbxCliente.addItem(RetornarDescricaoCliente(cliente));
        }

        for (Filme filme : listaFilmes) {
            cbxFilme.addItem(RetornarDescricaoFilme(filme));
        }

        txtFuncionario.setEnabled(false);
        jcDataLocacao.setEnabled(false);
        jspValor.setEnabled(false);

        cbxCliente.setSelectedIndex(-1);
        cbxFilme.setSelectedIndex(-1);

        if (id > 0) {
            Exception erro = controle.RecuperarLocacao(id);

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {
                AtualizarCampos();
            }
        } else {
            getLocacao().setFuncionario(ControleSistema.getFuncionarioLogado(controle.getSessao()));
            getLocacao().setData(new Date());
        }

        AtualizarCampoDataDevolucaoFilme();

        txtFuncionario.setText(getLocacao().getFuncionario().getNome());
        jcDataLocacao.setDate(getLocacao().getData());
    }

    private void AtualizarCampoDataDevolucaoFilme() {
        Calendar calendario = Calendar.getInstance();
        calendario.setTime(getLocacao().getData());

        if (calendario.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            //Adiciona 2 dias por causa do Domingo
            calendario.add(Calendar.DATE, 2);
        } else {
            //adiciona só um dia como padrão
            calendario.add(Calendar.DATE, 1);
        }

        jcDataDevolucaoFilme.setDate(calendario.getTime());
    }

    private String RetornarDescricaoCliente(Cliente cliente) {
        return cliente.getNome();
    }

    private String RetornarDescricaoFilme(Filme filme) {
        return filme.getNome();
    }

    private void AtualizarTabela() {
        while (modeloTabela.getRowCount() > 0) {
            modeloTabela.removeRow(0);
        }

        for (ItemLocacao item : getLocacao().getItensLocacao()) {
            AdicionarLinha(item);
        }
    }

    private void AdicionarLinha(ItemLocacao item) {
        modeloTabela.addRow(RetornarNovaLinha(item));
    }

    private Object[] RetornarNovaLinha(ItemLocacao item) {
        return new Object[]{
            item.getFilme().getNome(),
            item.getFilme().getTipoFilme().getDescricao(),
            controle.getValorLocacao(item.getFilme())
        };
    }

    private void AtualizarCampos() {
        AtualizarTabela();
        cbxCliente.setSelectedItem(getLocacao().getCliente().getNome());
        txtFuncionario.setText(getLocacao().getFuncionario().getNome());
        jcDataLocacao.setDate(getLocacao().getData());
        jspValor.setValue(getLocacao().getValorTotal());
    }

    private void AtualizarModelo() {
        getLocacao().setCliente(listaClientes.get(cbxCliente.getSelectedIndex()));
    }

    public Boolean ValidaCampos() {
        Boolean retorno = true;

        if (getLocacao().getItensLocacao().isEmpty()) {
            Utilidades.MostrarMensagem("Cadastro inválido", "Não foi adicionado nenhum filme na locação.");
            retorno = false;
        } else if (cbxCliente.getSelectedIndex() < 0) {
            Utilidades.MostrarMensagem("Cadastro inválido", "O cliente não foi selecionado.");
            retorno = false;
        }
        return retorno;
    }

    private void AtualizarValorLocacao() {
        double valor = 0;

        for (ItemLocacao item : getLocacao().getItensLocacao()) {
            valor += controle.getValorLocacao(item.getFilme());
        }

        jspValor.setValue(valor);
        getLocacao().setValorTotal(valor);
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

        jspValor = new javax.swing.JSpinner();
        BtnCancelar = new javax.swing.JButton();
        BtnConfirmar1 = new javax.swing.JButton();
        lValorLocacao = new javax.swing.JLabel();
        lNomeProduto = new javax.swing.JLabel();
        jcDataLocacao = new com.toedter.calendar.JDateChooser();
        lDataLocacao = new javax.swing.JLabel();
        lFuncionario = new javax.swing.JLabel();
        txtFuncionario = new javax.swing.JTextField();
        jPanel1 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tblFilmes = new javax.swing.JTable();
        BtnAdicionarFilme = new javax.swing.JButton();
        cbxCliente = new javax.swing.JComboBox<>();
        cbxFilme = new javax.swing.JComboBox<>();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jcDataDevolucaoFilme = new com.toedter.calendar.JDateChooser();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Locação");

        jspValor.setModel(new javax.swing.SpinnerNumberModel(0.0d, 0.0d, null, 0.1d));
        jspValor.setEditor(new javax.swing.JSpinner.NumberEditor(jspValor, "R$ ###,###.##"));

        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnCancelar.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });

        BtnConfirmar1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnConfirmar.png"))); // NOI18N
        BtnConfirmar1.setText("Confirmar");
        BtnConfirmar1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConfirmar1ActionPerformed(evt);
            }
        });

        lValorLocacao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lValorLocacao.setText("Valor de Locação*");

        lNomeProduto.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lNomeProduto.setText("Nome do Cliente*");

        lDataLocacao.setText("Data");

        lFuncionario.setText("Funcionário");

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Filmes"));

        tblFilmes.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Title 1", "Title 2", "Title 3", "Title 4"
            }
        ));
        jScrollPane1.setViewportView(tblFilmes);

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 308, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 10, Short.MAX_VALUE))
        );

        BtnAdicionarFilme.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnNovo.png"))); // NOI18N
        BtnAdicionarFilme.setText("Adicionar Filme");
        BtnAdicionarFilme.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnAdicionarFilmeActionPerformed(evt);
            }
        });

        jLabel1.setText("Filme");

        jLabel2.setText("Data de Devolução");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(cbxCliente, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnConfirmar1)
                        .addGap(18, 18, 18)
                        .addComponent(BtnCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lNomeProduto)
                            .addGroup(layout.createSequentialGroup()
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txtFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, 326, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lFuncionario))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lDataLocacao)
                                    .addComponent(jcDataLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE))))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lValorLocacao)
                            .addComponent(jspValor, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(67, 67, 67))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(cbxFilme, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGap(18, 18, 18))
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addGap(417, 417, 417)))
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jcDataDevolucaoFilme, javax.swing.GroupLayout.PREFERRED_SIZE, 158, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel2)))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(BtnAdicionarFilme, javax.swing.GroupLayout.PREFERRED_SIZE, 217, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(453, 453, 453)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lDataLocacao)
                    .addComponent(lFuncionario)
                    .addComponent(lValorLocacao))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtFuncionario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcDataLocacao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jspValor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(lNomeProduto)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(cbxCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel1))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(cbxFilme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcDataDevolucaoFilme, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(BtnAdicionarFilme, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(BtnCancelar)
                    .addComponent(BtnConfirmar1))
                .addContainerGap())
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        Object[] botoes = {"Sim", "Não"};
        int resposta = JOptionPane.showOptionDialog(null,
                "Deseja cancelar o cadastro de Locação? ",
                "Confirmação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                botoes, botoes[0]);
        if (resposta == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void BtnConfirmar1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmar1ActionPerformed
        if (ValidaCampos()) {
            AtualizarModelo();

            Exception erro = null;

            try {

                Transaction transacao = controle.getSessao().getTransaction();
                transacao.begin();

                double totalPago = 0;

                for (PagamentoLocacao pagamento : getLocacao().getPagamentos()) {
                    totalPago += pagamento.getValor();
                }

                if (totalPago >= getLocacao().getValorTotal()) {
                    getLocacao().setPago(true);
                } else {
                    getLocacao().setPago(false);
                }

                if (getLocacao().getId() <= 0) {
                    getLocacao().setData(new Date());
                    getLocacao().setDevolvido(false);
                }
                controle.getSessao().saveOrUpdate(getLocacao());

                Cliente cliente = controle.getLocacao().getCliente();

                for (ItemLocacao item : getLocacao().getItensLocacao()) {
                    if (item.getId() == 0) {
                        cliente.setTotalLocacoes(cliente.getTotalLocacoes() + 1);
                    }

                    controle.getSessao().saveOrUpdate(item);
                }

                controle.getSessao().saveOrUpdate(cliente);

                transacao.commit();

            } catch (Exception e) {
                erro = e;
            }

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {

                modeloAtualizado = true;
                this.dispose();
            }
        }
    }//GEN-LAST:event_BtnConfirmar1ActionPerformed

    private void BtnAdicionarFilmeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnAdicionarFilmeActionPerformed
        if (cbxFilme.getSelectedIndex() < 0) {
            Utilidades.MostrarMensagemErro(new Exception("Você não selecionou nenhum filme para adicionar."));
        } else if (getLocacao().getData().after(jcDataDevolucaoFilme.getDate())) {
            Utilidades.MostrarMensagemErro(new Exception("A data de devolução do filme deve ser maior que a data da locação."));
        } else {
            ItemLocacao item = new ItemLocacao();
            item.setDataPrevisaoEntrega(jcDataDevolucaoFilme.getDate());
            item.setFilme(listaFilmes.get(cbxFilme.getSelectedIndex()));
            item.setLocacao(getLocacao());

            getLocacao().getItensLocacao().add(item);

            AdicionarLinha(item);

            AtualizarValorLocacao();

            cbxFilme.setSelectedIndex(-1);
            AtualizarCampoDataDevolucaoFilme();
        }
    }//GEN-LAST:event_BtnAdicionarFilmeActionPerformed

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
            java.util.logging.Logger.getLogger(FrmCadastrarLocacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarLocacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarLocacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarLocacao.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCadastrarLocacao dialog = new FrmCadastrarLocacao(new javax.swing.JFrame(), true, 0);
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
    private javax.swing.JButton BtnAdicionarFilme;
    private javax.swing.JButton BtnCancelar;
    private javax.swing.JButton BtnConfirmar1;
    private javax.swing.JComboBox<String> cbxCliente;
    private javax.swing.JComboBox<String> cbxFilme;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private com.toedter.calendar.JDateChooser jcDataDevolucaoFilme;
    private com.toedter.calendar.JDateChooser jcDataLocacao;
    private javax.swing.JSpinner jspValor;
    private javax.swing.JLabel lDataLocacao;
    private javax.swing.JLabel lFuncionario;
    private javax.swing.JLabel lNomeProduto;
    private javax.swing.JLabel lValorLocacao;
    private javax.swing.JTable tblFilmes;
    private javax.swing.JTextField txtFuncionario;
    // End of variables declaration//GEN-END:variables
}
