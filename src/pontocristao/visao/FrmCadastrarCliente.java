package pontocristao.visao;

import java.awt.*;
import java.util.ArrayList;
import java.util.Date;
import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.table.DefaultTableModel;
import pontocristao.controle.ControleCliente;
import pontocristao.modelo.Sexo;
import pontocristao.modelo.Cliente;
import pontocristao.modelo.ClientePessoaFisica;
import pontocristao.modelo.ClientePessoaJuridica;
import pontocristao.modelo.Dependente;
import pontocristao.modelo.Endereco;
import pontocristao.util.*;

/**
 *
 * @author Marcondes
 */
public class FrmCadastrarCliente extends javax.swing.JDialog {

    private DefaultTableModel modeloTabela;
    private static Frame frame;
    private ControleCliente controle;
    private Boolean modeloAtualizado = false;
    private java.util.List<Dependente> listaDependentes;

    public Boolean getModeloAtualizado() {
        return modeloAtualizado;
    }

    public Cliente getCliente() {
        return controle.getCliente();
    }

    public FrmCadastrarCliente(java.awt.Frame parent, boolean modal, long id) {
        super(parent, modal);
        initComponents();
        AjustarTabela();

        setLocationRelativeTo(null);

        txtNome.requestFocus();
        txtCodigo.setEnabled(false);

        jcDataCadastro.setDate(new Date());
        jcDataCadastro.setEnabled(false);

        BtnEditar.setEnabled(false);
        BtnExcluir.setEnabled(false);
        txtCnpj.setEnabled(false);

        Utilidades.setMascara("#####-###", txtCep);
        Utilidades.setMascara("##.###.###/####-##", txtCnpj);
        Utilidades.setMascara("(##)####-####", txtTelefone);
        Utilidades.setMascara("(##)####-####", txtCelular);
        Utilidades.setMascara("###.###.###-##", txtCpf);
        Utilidades.setMascara("#########", txtRg);

        InicializarControle(id);
    }

    public static FrmCadastrarCliente Mostrar(java.awt.Frame parent, long id) {
        frame = parent;
        FrmCadastrarCliente frmCadastrarCliente = new FrmCadastrarCliente(parent, true, id);
        frmCadastrarCliente.setVisible(true);
        return frmCadastrarCliente;
    }

    private void InicializarControle(long id) {
        controle = new ControleCliente(true);

        if (id > 0) {
            Exception erro = controle.RecuperarCliente(id);

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {

                if (controle.getCliente().getClass() == ClientePessoaFisica.class) {
                    jRadioPessoaFisica.setSelected(true);
                    AtualizarTelaPessoaFisica();
                } else {
                    jRadioPessoaJuridica.setSelected(true);
                    AtualizarTelaPessoaJuridica();
                }
                jRadioPessoaFisica.setEnabled(false);
                jRadioPessoaJuridica.setEnabled(false);

                AtualizarCampos();
            }
        }
    }

    private void AtualizarCampos() {
        txtCodigo.setText(String.valueOf(controle.getCliente().getId()));
        txtNome.setText(controle.getCliente().getNome());
        txtCelular.setText(controle.getCliente().getCelular());
        txtTelefone.setText(controle.getCliente().getTelefone());
        txtEmail.setText(controle.getCliente().getEmail());
        txtCep.setText(controle.getCliente().getEndereco().getCep());
        txtRua.setText(controle.getCliente().getEndereco().getRua());
        txtNumero.setText(controle.getCliente().getEndereco().getNumero());
        jComboEstado.setSelectedItem(controle.getCliente().getEndereco().getEstado());
        txtBairro.setText(controle.getCliente().getEndereco().getBairro());
        txtCidade.setText(controle.getCliente().getEndereco().getCidade());
        txtComplemento.setText(controle.getCliente().getEndereco().getComplemento());

        ListarDependentes();

        if (controle.getCliente().getClass() == ClientePessoaFisica.class) {
            ClientePessoaFisica cliente = (ClientePessoaFisica) controle.getCliente();

            txtCpf.setText(cliente.getCpf());
            txtRg.setText(cliente.getRg());
            jComboSexo.setSelectedIndex(cliente.getSexo().ordinal());
            jcDataNascimento.setDate(cliente.getDataNascimento());
        } else {
            ClientePessoaJuridica cliente = (ClientePessoaJuridica) controle.getCliente();

            txtCnpj.setText(cliente.getCnpj());
        }
    }

    private void AtualizarModelo() {
        controle.getCliente().setNome(txtNome.getText());
        controle.getCliente().setCelular(txtCelular.getText());
        controle.getCliente().setTelefone(txtTelefone.getText());
        controle.getCliente().setEmail(txtEmail.getText());
        controle.getCliente().getEndereco().setCep(txtCep.getText());
        controle.getCliente().getEndereco().setRua(txtRua.getText());
        controle.getCliente().getEndereco().setNumero(txtNumero.getText());
        controle.getCliente().getEndereco().setEstado(jComboEstado.getSelectedItem().toString());
        controle.getCliente().getEndereco().setBairro(txtBairro.getText());
        controle.getCliente().getEndereco().setCidade(txtCidade.getText());
        controle.getCliente().getEndereco().setComplemento(txtComplemento.getText());

        if (controle.getCliente().getClass() == ClientePessoaFisica.class) {
            ClientePessoaFisica cliente = (ClientePessoaFisica) controle.getCliente();

            cliente.setCpf(txtCpf.getText());
            cliente.setRg(txtRg.getText());
            cliente.setSexo(Sexo.valueOf(jComboSexo.getSelectedItem().toString()));
            cliente.setDataNascimento(jcDataNascimento.getDate());

        } else {
            ClientePessoaJuridica cliente = (ClientePessoaJuridica) controle.getCliente();

            cliente.setCnpj(txtCnpj.getText());
        }

    }

    public Boolean ValidarCampos() {
        Boolean retorno = true;

        if (txtNome.getText().equals("")
                || Utilidades.getValorSemMascara(txtCelular).equals("")
                || Utilidades.getValorSemMascara(txtTelefone).equals("")
                || Utilidades.getValorSemMascara(txtCep).equals("")
                || txtRua.getText().equals("")
                || txtNumero.getText().equals("")
                || jComboEstado.getSelectedItem().equals("")
                || txtBairro.getText().equals("")
                || txtCidade.getText().equals("")) {

            retorno = false;
        }

        if (controle.getCliente().getClass() == ClientePessoaFisica.class) {
            if (Utilidades.getValorSemMascara(txtCpf).equals("")
                    || Utilidades.getValorSemMascara(txtRg).equals("")
                    || jComboSexo.getSelectedItem().equals("")
                    || jcDataNascimento.getDate() == null) {
                retorno = false;
            }

        } else if (Utilidades.getValorSemMascara(txtCnpj).equals("")) {
            retorno = false;
        }

        if (!retorno) {
            JOptionPane.showMessageDialog(null, "Todos os campos com o título em negrito, devem estar preenchidos.");
        }

        return retorno;
    }

    private void AjustarTabela() {
        String[] colunas = new String[]{"Nome", "Telefone", "RG", "CPF"};
        modeloTabela = new DefaultTableModel(null, colunas) {
            @Override
            public boolean isCellEditable(int row, int col) {
                return false;
            }
        };

        jTableDependente.setModel(modeloTabela);
        jTableDependente.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        jTableDependente.getSelectionModel().addListSelectionListener(new ListSelectionListener() {
            public void valueChanged(ListSelectionEvent lse) {
                if (!lse.getValueIsAdjusting()) {
                    if (jTableDependente.getSelectedRow() >= 0) {
                        BtnEditar.setEnabled(true);
                        BtnExcluir.setEnabled(true);
                    } else {
                        BtnEditar.setEnabled(false);
                        BtnExcluir.setEnabled(false);
                    }
                }
            }
        });
    }

    private void AtualizarTabela() {
        while (modeloTabela.getRowCount() > 0) {
            modeloTabela.removeRow(0);
        }

        listaDependentes = new ArrayList<Dependente>();

        for (Dependente dependente : controle.getCliente().getDependentes()) {
            if (!dependente.getExcluido()) {
                listaDependentes.add(dependente);
                AdicionarLinha(dependente);
            }
        }
    }

    private void AdicionarLinha(Dependente dependente) {
        modeloTabela.addRow(RetornarNovaLinha(dependente));
    }

    private Object[] RetornarNovaLinha(Dependente dependente) {
        return new Object[]{
            dependente.getNome(),
            dependente.getTelefone(),
            dependente.getRg(),
            dependente.getCpf()
        };
    }

    public void ListarDependentes() {
        try {
            AtualizarTabela();
        } catch (Exception e) {
            Utilidades.MostrarMensagemErro(e);
        }
    }

    private void AtualizarTelaPessoaFisica() {
        txtCpf.setEnabled(true);
        txtRg.setEnabled(true);
        jComboSexo.setEnabled(true);
        jcDataNascimento.setEnabled(true);

        txtCnpj.setEnabled(false);

        txtCnpj.setText("");

        if (controle.getCliente().getId() == 0) {
            controle.setCliente(new ClientePessoaFisica());
            controle.getCliente().setEndereco(new Endereco());
        }
    }

    private void AtualizarTelaPessoaJuridica() {
        txtCnpj.setEnabled(true);

        txtCpf.setEnabled(false);
        txtRg.setEnabled(false);
        jComboSexo.setEnabled(false);
        jcDataNascimento.setEnabled(false);

        txtRg.setText("");
        txtCpf.setText("");
        jComboSexo.setSelectedIndex(-1);
        jcDataNascimento.setDate(null);

        if (controle.getCliente().getId() == 0) {
            controle.setCliente(new ClientePessoaJuridica());
            controle.getCliente().setEndereco(new Endereco());
        }
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

        jButtonGroupTipoCliente = new javax.swing.ButtonGroup();
        pCadastroCliente = new javax.swing.JPanel();
        pInformacoesPessoais = new javax.swing.JPanel();
        lDataCadastro = new javax.swing.JLabel();
        lTelefone = new javax.swing.JLabel();
        lSexo = new javax.swing.JLabel();
        lCodigo = new javax.swing.JLabel();
        lCpf = new javax.swing.JLabel();
        txtCodigo = new javax.swing.JTextField();
        lNome = new javax.swing.JLabel();
        lEmail = new javax.swing.JLabel();
        jComboSexo = new javax.swing.JComboBox<>();
        lRg = new javax.swing.JLabel();
        lDataNascimento = new javax.swing.JLabel();
        txtNome = new javax.swing.JTextField();
        txtEmail = new javax.swing.JTextField();
        lCelular = new javax.swing.JLabel();
        lCnpj = new javax.swing.JLabel();
        jcDataNascimento = new com.toedter.calendar.JDateChooser();
        jcDataCadastro = new com.toedter.calendar.JDateChooser();
        txtCpf = new javax.swing.JFormattedTextField();
        txtRg = new javax.swing.JFormattedTextField();
        txtCnpj = new javax.swing.JFormattedTextField();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtCelular = new javax.swing.JFormattedTextField();
        jRadioPessoaJuridica = new javax.swing.JRadioButton();
        jRadioPessoaFisica = new javax.swing.JRadioButton();
        pEndereco = new javax.swing.JPanel();
        txtNumero = new javax.swing.JTextField();
        jComboEstado = new javax.swing.JComboBox<>();
        txtComplemento = new javax.swing.JTextField();
        lEstado = new javax.swing.JLabel();
        lNumero = new javax.swing.JLabel();
        lRua = new javax.swing.JLabel();
        txtBairro = new javax.swing.JTextField();
        lComplemento = new javax.swing.JLabel();
        lCep = new javax.swing.JLabel();
        txtCidade = new javax.swing.JTextField();
        lBairro = new javax.swing.JLabel();
        txtRua = new javax.swing.JTextField();
        lCidade = new javax.swing.JLabel();
        BtnConsultarCep = new javax.swing.JButton();
        txtCep = new javax.swing.JFormattedTextField();
        pCadastroDependente = new javax.swing.JPanel();
        BtnNovo = new javax.swing.JButton();
        BtnExcluir = new javax.swing.JButton();
        BtnEditar = new javax.swing.JButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        jTableDependente = new javax.swing.JTable();
        BtnConfirmar = new javax.swing.JButton();
        BtnCancelar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Clientes");
        setResizable(false);

        pCadastroCliente.setBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED));

        pInformacoesPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Pessoais"));

        lDataCadastro.setText("Data do Cadastro");

        lTelefone.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lTelefone.setText("Telefone*");

        lSexo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lSexo.setText("Sexo*");

        lCodigo.setText("Código");

        lCpf.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCpf.setText("CPF*");

        lNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lNome.setText("Nome*");

        lEmail.setText("E-mail");

        jComboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Feminino", "Masculino" }));
        jComboSexo.setSelectedIndex(-1);

        lRg.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lRg.setText("RG*");

        lDataNascimento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lDataNascimento.setText("Data de Nascimento*");

        lCelular.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCelular.setText("Celular*");

        lCnpj.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCnpj.setText("CNPJ*");

        jButtonGroupTipoCliente.add(jRadioPessoaJuridica);
        jRadioPessoaJuridica.setText("Pessoa Jurídica");
        jRadioPessoaJuridica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioPessoaJuridicaActionPerformed(evt);
            }
        });

        jButtonGroupTipoCliente.add(jRadioPessoaFisica);
        jRadioPessoaFisica.setSelected(true);
        jRadioPessoaFisica.setText("Pessoa Física");
        jRadioPessoaFisica.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jRadioPessoaFisicaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pInformacoesPessoaisLayout = new javax.swing.GroupLayout(pInformacoesPessoais);
        pInformacoesPessoais.setLayout(pInformacoesPessoaisLayout);
        pInformacoesPessoaisLayout.setHorizontalGroup(
            pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                        .addContainerGap()
                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pInformacoesPessoaisLayout.createSequentialGroup()
                                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 18, Short.MAX_VALUE)
                                        .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txtCnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 146, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(lCnpj))
                                        .addGap(22, 22, 22)
                                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lSexo)
                                            .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 134, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(160, 160, 160))
                                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                        .addGap(18, 18, 18)
                                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                        .addGap(18, 18, 18)
                                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                                .addComponent(lEmail)
                                                .addGap(0, 0, Short.MAX_VALUE))
                                            .addComponent(txtEmail)))))
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lCodigo)
                                            .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, 92, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, Short.MAX_VALUE)
                                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(lNome)
                                            .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, 510, javax.swing.GroupLayout.PREFERRED_SIZE))
                                        .addGap(18, 18, 18))
                                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pInformacoesPessoaisLayout.createSequentialGroup()
                                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                                            .addComponent(txtCpf, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, pInformacoesPessoaisLayout.createSequentialGroup()
                                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lCpf)
                                                    .addComponent(lTelefone))
                                                .addGap(112, 112, 112)
                                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                                    .addComponent(lRg)
                                                    .addComponent(lCelular))))
                                        .addGap(0, 0, Short.MAX_VALUE)))
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                    .addComponent(lDataCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                    .addComponent(lDataNascimento)
                                    .addComponent(jcDataCadastro, javax.swing.GroupLayout.DEFAULT_SIZE, 142, Short.MAX_VALUE)
                                    .addComponent(jcDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))))
                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                        .addGap(304, 304, 304)
                        .addComponent(jRadioPessoaFisica)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(jRadioPessoaJuridica)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        pInformacoesPessoaisLayout.setVerticalGroup(
            pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jRadioPessoaJuridica)
                    .addComponent(jRadioPessoaFisica))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lNome, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addComponent(lDataNascimento))
                    .addComponent(lCodigo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lCpf)
                        .addComponent(lRg)
                        .addComponent(lCnpj)
                        .addComponent(lDataCadastro)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCnpj))
                    .addComponent(jcDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTelefone)
                    .addComponent(lCelular)
                    .addComponent(lEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail)
                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        pEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));
        jComboEstado.setSelectedIndex(-1);

        lEstado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lEstado.setText("Estado*");

        lNumero.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lNumero.setText("Número*");

        lRua.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lRua.setText("Rua*");

        lComplemento.setText("Complemento");

        lCep.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCep.setText("CEP*");

        lBairro.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lBairro.setText("Bairro*");

        lCidade.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCidade.setText("Cidade*");

        BtnConsultarCep.setText("Consultar");
        BtnConsultarCep.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConsultarCepActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout pEnderecoLayout = new javax.swing.GroupLayout(pEndereco);
        pEndereco.setLayout(pEnderecoLayout);
        pEnderecoLayout.setHorizontalGroup(
            pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addComponent(lCep)
                        .addGap(483, 483, 483)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lNumero)
                            .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lEstado)
                            .addComponent(jComboEstado, 0, 145, Short.MAX_VALUE)))
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lBairro)
                            .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, 176, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lCidade)
                            .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txtComplemento)
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(lComplemento)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(pEnderecoLayout.createSequentialGroup()
                        .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(BtnConsultarCep, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lRua)
                            .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, 290, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );
        pEnderecoLayout.setVerticalGroup(
            pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pEnderecoLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCep)
                    .addComponent(lRua)
                    .addComponent(lNumero)
                    .addComponent(lEstado))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtRua, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnConsultarCep)
                    .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lBairro)
                    .addComponent(lCidade)
                    .addComponent(lComplemento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtBairro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCidade, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtComplemento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );

        javax.swing.GroupLayout pCadastroClienteLayout = new javax.swing.GroupLayout(pCadastroCliente);
        pCadastroCliente.setLayout(pCadastroClienteLayout);
        pCadastroClienteLayout.setHorizontalGroup(
            pCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCadastroClienteLayout.createSequentialGroup()
                .addGroup(pCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pInformacoesPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pEndereco, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        pCadastroClienteLayout.setVerticalGroup(
            pCadastroClienteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, pCadastroClienteLayout.createSequentialGroup()
                .addComponent(pInformacoesPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        pCadastroDependente.setBorder(javax.swing.BorderFactory.createTitledBorder(javax.swing.BorderFactory.createBevelBorder(javax.swing.border.BevelBorder.RAISED), "Dependentes"));

        BtnNovo.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnNovo.png"))); // NOI18N
        BtnNovo.setText("Novo");
        BtnNovo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnNovoActionPerformed(evt);
            }
        });

        BtnExcluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnExcluir.png"))); // NOI18N
        BtnExcluir.setText("Excluir");
        BtnExcluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnExcluirActionPerformed(evt);
            }
        });

        BtnEditar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnEditar.png"))); // NOI18N
        BtnEditar.setText("Editar");
        BtnEditar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnEditarActionPerformed(evt);
            }
        });

        jTableDependente.setModel(new javax.swing.table.DefaultTableModel(
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
        jScrollPane1.setViewportView(jTableDependente);

        javax.swing.GroupLayout pCadastroDependenteLayout = new javax.swing.GroupLayout(pCadastroDependente);
        pCadastroDependente.setLayout(pCadastroDependenteLayout);
        pCadastroDependenteLayout.setHorizontalGroup(
            pCadastroDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCadastroDependenteLayout.createSequentialGroup()
                .addComponent(BtnNovo)
                .addGap(18, 18, 18)
                .addComponent(BtnEditar)
                .addGap(18, 18, 18)
                .addComponent(BtnExcluir)
                .addGap(0, 0, Short.MAX_VALUE))
            .addComponent(jScrollPane1)
        );
        pCadastroDependenteLayout.setVerticalGroup(
            pCadastroDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pCadastroDependenteLayout.createSequentialGroup()
                .addGroup(pCadastroDependenteLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnNovo, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnEditar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnExcluir, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 91, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

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

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(pCadastroCliente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(pCadastroDependente, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(BtnConfirmar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnCancelar)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(pCadastroCliente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pCadastroDependente, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCancelar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(BtnConfirmar, javax.swing.GroupLayout.PREFERRED_SIZE, 50, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnNovoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnNovoActionPerformed
        FrmCadastrarDependente frmCadastrarDependente = FrmCadastrarDependente.Mostrar(frame, new Dependente());

        if (frmCadastrarDependente.getModeloAtualizado()) {
            Dependente dependente = frmCadastrarDependente.getDependente();

            dependente.setCliente(controle.getCliente());

            AdicionarLinha(dependente);
            listaDependentes.add(dependente);
            controle.getCliente().getDependentes().add(dependente);
        }
    }//GEN-LAST:event_BtnNovoActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        Object[] botoes = {"Sim", "Não"};
        int resposta = JOptionPane.showOptionDialog(null,
                "Deseja cancelar o cadastro de Clientes? ",
                "Confirmação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                botoes, botoes[0]);
        if (resposta == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void BtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmarActionPerformed
        if (ValidarCampos()) {
            AtualizarModelo();

            Exception erro = controle.Salvar();

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {
                modeloAtualizado = true;
                dispose();
            }
        }
    }//GEN-LAST:event_BtnConfirmarActionPerformed

    private void jRadioPessoaFisicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioPessoaFisicaActionPerformed
        AtualizarTelaPessoaFisica();
    }//GEN-LAST:event_jRadioPessoaFisicaActionPerformed

    private void jRadioPessoaJuridicaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jRadioPessoaJuridicaActionPerformed
        AtualizarTelaPessoaJuridica();
    }//GEN-LAST:event_jRadioPessoaJuridicaActionPerformed

    private void BtnConsultarCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarCepActionPerformed
        BuscaCep(txtCep.getText());
        txtNumero.requestFocus();
    }//GEN-LAST:event_BtnConsultarCepActionPerformed

    private void BtnEditarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnEditarActionPerformed
        int linhaSelecionada = jTableDependente.getSelectedRow();
        Dependente dependente = listaDependentes.get(linhaSelecionada);
        FrmCadastrarDependente frmCadastrarDependente = FrmCadastrarDependente.Mostrar(frame, dependente);

        dependente = frmCadastrarDependente.getDependente();

        if (frmCadastrarDependente.getModeloAtualizado()) {
            modeloTabela.removeRow(linhaSelecionada);
            modeloTabela.insertRow(linhaSelecionada, RetornarNovaLinha(dependente));
        }
    }//GEN-LAST:event_BtnEditarActionPerformed

    private void BtnExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnExcluirActionPerformed
        Boolean podeExcluir = Utilidades.MostrarMensagemPergunta("Confirmação", "Tem certeza que deseja excluir o dependente?", false);

        if (podeExcluir) {
            int linhaSelecionada = jTableDependente.getSelectedRow();
            Dependente dependente = listaDependentes.get(linhaSelecionada);

            modeloTabela.removeRow(linhaSelecionada);
            listaDependentes.remove(dependente);

            dependente.setExcluido(true);
        }
    }//GEN-LAST:event_BtnExcluirActionPerformed

    public void BuscaCep(String cep) {
        try {

            CepWebService cepWebService = new CepWebService(cep);

            if (cepWebService.getResultado() == 1) {

                txtRua.setText(cepWebService.getLogradouro());
                txtBairro.setText(cepWebService.getBairro());
                txtCidade.setText(cepWebService.getCidade());
                jComboEstado.setSelectedItem(cepWebService.getEstado());
            } else {
                JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o endereço", "Procura do endereço", JOptionPane.INFORMATION_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null, "Não foi possivel encontrar o endereço", "Procura do endereço", JOptionPane.INFORMATION_MESSAGE);
        }
    }

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
            java.util.logging.Logger.getLogger(FrmCadastrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarCliente.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCadastrarCliente dialog = new FrmCadastrarCliente(new javax.swing.JFrame(), true, 0);
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
    private javax.swing.JButton BtnConsultarCep;
    private javax.swing.JButton BtnEditar;
    private javax.swing.JButton BtnExcluir;
    private javax.swing.JButton BtnNovo;
    private javax.swing.ButtonGroup jButtonGroupTipoCliente;
    private javax.swing.JComboBox<String> jComboEstado;
    private javax.swing.JComboBox<String> jComboSexo;
    private javax.swing.JRadioButton jRadioPessoaFisica;
    private javax.swing.JRadioButton jRadioPessoaJuridica;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jTableDependente;
    private com.toedter.calendar.JDateChooser jcDataCadastro;
    private com.toedter.calendar.JDateChooser jcDataNascimento;
    private javax.swing.JLabel lBairro;
    private javax.swing.JLabel lCelular;
    private javax.swing.JLabel lCep;
    private javax.swing.JLabel lCidade;
    private javax.swing.JLabel lCnpj;
    private javax.swing.JLabel lCodigo;
    private javax.swing.JLabel lComplemento;
    private javax.swing.JLabel lCpf;
    private javax.swing.JLabel lDataCadastro;
    private javax.swing.JLabel lDataNascimento;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lEstado;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lNumero;
    private javax.swing.JLabel lRg;
    private javax.swing.JLabel lRua;
    private javax.swing.JLabel lSexo;
    private javax.swing.JLabel lTelefone;
    private javax.swing.JPanel pCadastroCliente;
    private javax.swing.JPanel pCadastroDependente;
    private javax.swing.JPanel pEndereco;
    private javax.swing.JPanel pInformacoesPessoais;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JFormattedTextField txtCnpj;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JFormattedTextField txtRg;
    private javax.swing.JTextField txtRua;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
