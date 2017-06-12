package pontocristao.visao;

import java.awt.*;
import java.util.Arrays;
import java.util.Date;
import javax.swing.JOptionPane;
import pontocristao.controle.ControleFuncionario;
import pontocristao.modelo.Funcionario;
import pontocristao.modelo.Sexo;
import pontocristao.util.CepWebService;
import pontocristao.util.Utilidades;

/**
 *
 * @author Marcondes
 */
public class FrmCadastrarFuncionario extends javax.swing.JDialog {

    private static Frame frame;
    private ControleFuncionario controle;
    private Boolean modeloAtualizado = false;

    public Boolean getModeloAtualizado() {
        return modeloAtualizado;
    }

    public Funcionario getFuncionario() {
        return controle.getFuncionario();
    }

    public FrmCadastrarFuncionario(java.awt.Frame parent, boolean modal, long id) {
        super(parent, modal);
        initComponents();

        this.setLocationRelativeTo(null);

        txtLogin.requestFocus();
        txtCodigo.setEnabled(false);

        jcDataCadastro.setDate(new Date());
        jcDataCadastro.setEnabled(false);

        Utilidades.setMascara("#####-###", txtCep);
        Utilidades.setMascara("###.#####.##-#", txtCarteiraTrabalho);
        Utilidades.setMascara("(##)####-####", txtTelefone);
        Utilidades.setMascara("(##)####-####", txtCelular);
        Utilidades.setMascara("###.###.###-##", txtCpf);
        Utilidades.setMascara("#########", txtRg);

        InicializarControle(id);
    }

    public static FrmCadastrarFuncionario Mostrar(java.awt.Frame parent, long id) {
        frame = parent;
        FrmCadastrarFuncionario frmCadastrarFuncionario = new FrmCadastrarFuncionario(parent, true, id);
        frmCadastrarFuncionario.setVisible(true);
        return frmCadastrarFuncionario;
    }

    private void InicializarControle(long id) {
        this.controle = new ControleFuncionario();

        if (id > 0) {
            Exception erro = this.controle.RecuperarFuncionario(id);

            if (erro != null) {
                Utilidades.MostrarMensagemErro(erro);
            } else {
                AtualizarCampos();
            }
        }
    }

    private void AtualizarCampos() {
        txtLogin.setText(controle.getFuncionario().getLogin());
        jcDataAdmissao.setDate(controle.getFuncionario().getDataAdmissao());
        jcDataCadastro.setDate(controle.getFuncionario().getDataCadastro());
        txtCodigo.setText(String.valueOf(controle.getFuncionario().getId()));
        txtNome.setText(controle.getFuncionario().getNome());
        txtCpf.setText(controle.getFuncionario().getCpf());
        txtRg.setText(controle.getFuncionario().getRg());
        txtCarteiraTrabalho.setText(controle.getFuncionario().getCarteiraTrabalho());
        jComboSexo.setSelectedIndex(controle.getFuncionario().getSexo().ordinal());
        jcDataNascimento.setDate(controle.getFuncionario().getDataNascimento());
        txtTelefone.setText(controle.getFuncionario().getTelefoneResidencial());
        txtCelular.setText(controle.getFuncionario().getCelular());
        txtEmail.setText(controle.getFuncionario().getEmail());
        txtCep.setText(controle.getFuncionario().getEndereco().getCep());
        txtRua.setText(controle.getFuncionario().getEndereco().getRua());
        txtNumero.setText(controle.getFuncionario().getEndereco().getNumero());
        jComboEstado.setSelectedItem(controle.getFuncionario().getEndereco().getEstado());
        txtBairro.setText(controle.getFuncionario().getEndereco().getBairro());
        txtCidade.setText(controle.getFuncionario().getEndereco().getCidade());
        txtComplemento.setText(controle.getFuncionario().getEndereco().getComplemento());
    }

    private void AtualizarModelo() {
        controle.getFuncionario().setLogin(txtLogin.getText());
        controle.getFuncionario().setDataAdmissao(jcDataAdmissao.getDate());
        controle.getFuncionario().setNome(txtNome.getText());
        controle.getFuncionario().setCpf(txtCpf.getText());
        controle.getFuncionario().setRg(txtRg.getText());
        controle.getFuncionario().setCarteiraTrabalho(txtCarteiraTrabalho.getText());
        controle.getFuncionario().setSexo(Sexo.valueOf(jComboSexo.getSelectedItem().toString()));
        controle.getFuncionario().setDataNascimento(jcDataNascimento.getDate());
        controle.getFuncionario().setTelefoneResidencial(txtTelefone.getText());
        controle.getFuncionario().setCelular(txtCelular.getText());
        controle.getFuncionario().setEmail(txtEmail.getText());
        controle.getFuncionario().getEndereco().setCep(txtCep.getText());
        controle.getFuncionario().getEndereco().setRua(txtRua.getText());
        controle.getFuncionario().getEndereco().setNumero(txtNumero.getText());
        controle.getFuncionario().getEndereco().setEstado(jComboEstado.getSelectedItem().toString());
        controle.getFuncionario().getEndereco().setBairro(txtBairro.getText());
        controle.getFuncionario().getEndereco().setCidade(txtCidade.getText());
        controle.getFuncionario().getEndereco().setComplemento(txtComplemento.getText());

        if (txtSenha.getPassword().length > 0) {
            controle.getFuncionario().setSenha(String.valueOf(txtSenha.getPassword()));
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        pInformacoesSistema = new javax.swing.JPanel();
        lLogin = new javax.swing.JLabel();
        txtLogin = new javax.swing.JTextField();
        lSenha = new javax.swing.JLabel();
        txtSenha = new javax.swing.JPasswordField();
        lConfirmacaoSenha = new javax.swing.JLabel();
        txtConfirmacaoSenha = new javax.swing.JPasswordField();
        lDataAdmissao = new javax.swing.JLabel();
        jcDataAdmissao = new com.toedter.calendar.JDateChooser();
        lDataCadastro = new javax.swing.JLabel();
        jcDataCadastro = new com.toedter.calendar.JDateChooser();
        pInformacoesPessoais = new javax.swing.JPanel();
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
        jcDataNascimento = new com.toedter.calendar.JDateChooser();
        txtCpf = new javax.swing.JFormattedTextField();
        txtRg = new javax.swing.JFormattedTextField();
        txtTelefone = new javax.swing.JFormattedTextField();
        txtCelular = new javax.swing.JFormattedTextField();
        lCarteiraTrabalho = new javax.swing.JLabel();
        txtCarteiraTrabalho = new javax.swing.JFormattedTextField();
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
        BtnCancelar = new javax.swing.JButton();
        BtnConfirmar = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("Cadastro de Funcionários");

        pInformacoesSistema.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações do Sistema"));

        lLogin.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lLogin.setText("Login*");

        lSenha.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lSenha.setText("Senha*");

        lConfirmacaoSenha.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lConfirmacaoSenha.setText("Confirmação de Senha*");

        lDataAdmissao.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lDataAdmissao.setText("Data de Admissão*");

        lDataCadastro.setText("Data de Cadastro");

        javax.swing.GroupLayout pInformacoesSistemaLayout = new javax.swing.GroupLayout(pInformacoesSistema);
        pInformacoesSistema.setLayout(pInformacoesSistemaLayout);
        pInformacoesSistemaLayout.setHorizontalGroup(
            pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInformacoesSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lLogin, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtConfirmacaoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lConfirmacaoSenha))
                .addGap(18, 18, 18)
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lDataCadastro)
                    .addComponent(jcDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 127, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        pInformacoesSistemaLayout.setVerticalGroup(
            pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInformacoesSistemaLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                        .addComponent(lDataAdmissao, javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(lLogin)
                            .addComponent(lSenha)))
                    .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(lConfirmacaoSenha)
                        .addComponent(lDataCadastro)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pInformacoesSistemaLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(txtLogin, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtConfirmacaoSenha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcDataAdmissao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcDataCadastro, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pInformacoesPessoais.setBorder(javax.swing.BorderFactory.createTitledBorder("Informações Pessoais"));

        lTelefone.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lTelefone.setText("Telefone*");

        lSexo.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lSexo.setText("Sexo*");

        lCodigo.setText("Código");

        lCpf.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCpf.setText("CPF*");

        lNome.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lNome.setText("Nome*");

        lEmail.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lEmail.setText("E-mail*");

        jComboSexo.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Feminino", "Masculino" }));

        lRg.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lRg.setText("RG*");

        lDataNascimento.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lDataNascimento.setText("Data de Nascimento*");

        lCelular.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCelular.setText("Celular*");

        lCarteiraTrabalho.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lCarteiraTrabalho.setText("Carteira de Trabalho*");

        javax.swing.GroupLayout pInformacoesPessoaisLayout = new javax.swing.GroupLayout(pInformacoesPessoais);
        pInformacoesPessoais.setLayout(pInformacoesPessoaisLayout);
        pInformacoesPessoaisLayout.setHorizontalGroup(
            pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                        .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(txtEmail))
                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addComponent(lCodigo)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addComponent(txtCodigo))
                        .addGap(18, 18, 18)
                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addComponent(lNome)
                                .addGap(501, 501, 501))
                            .addComponent(txtNome)))
                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lCpf)
                                    .addComponent(lTelefone))
                                .addGap(112, 112, 112)
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lCelular)
                                    .addComponent(lRg))))
                        .addGap(18, 18, 18)
                        .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addComponent(lEmail)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lCarteiraTrabalho)
                                    .addComponent(txtCarteiraTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, 138, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addGap(18, 18, 18)
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 139, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(lSexo))
                                .addGap(18, 18, 18)
                                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                                        .addComponent(lDataNascimento)
                                        .addGap(0, 65, Short.MAX_VALUE))
                                    .addComponent(jcDataNascimento, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))))
                .addContainerGap())
        );
        pInformacoesPessoaisLayout.setVerticalGroup(
            pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(pInformacoesPessoaisLayout.createSequentialGroup()
                .addContainerGap()
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(lNome)
                    .addComponent(lCodigo, javax.swing.GroupLayout.Alignment.TRAILING))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txtCodigo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtNome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lCpf)
                    .addComponent(lRg)
                    .addComponent(lSexo, javax.swing.GroupLayout.PREFERRED_SIZE, 14, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(lCarteiraTrabalho)
                    .addComponent(lDataNascimento))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jComboSexo, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCpf, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtRg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(txtCarteiraTrabalho, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addComponent(jcDataNascimento, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lTelefone)
                    .addComponent(lCelular)
                    .addComponent(lEmail))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(pInformacoesPessoaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txtEmail, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtCelular, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txtTelefone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pEndereco.setBorder(javax.swing.BorderFactory.createTitledBorder("Endereço"));

        jComboEstado.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "AC", "AL", "AP", "AM", "BA", "CE", "DF", "ES", "GO", "MA", "MT", "MS", "MG", "PA", "PB", "PR", "PE", "PI", "RJ", "RN", "RS", "RO", "RR", "SC", "SP", "SE", "TO" }));

        lEstado.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        lEstado.setText("Estado");

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
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(txtCep, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(BtnConsultarCep, javax.swing.GroupLayout.PREFERRED_SIZE, 90, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(pEnderecoLayout.createSequentialGroup()
                                        .addComponent(lRua)
                                        .addGap(0, 0, Short.MAX_VALUE))
                                    .addComponent(txtRua))
                                .addGap(18, 18, 18)
                                .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(lNumero)
                                    .addComponent(txtNumero, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(lCep)
                                .addGap(0, 0, Short.MAX_VALUE)))
                        .addGap(18, 18, 18)
                        .addGroup(pEnderecoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lEstado)
                            .addComponent(jComboEstado, javax.swing.GroupLayout.PREFERRED_SIZE, 115, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addContainerGap())
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
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(lComplemento)
                                .addGap(0, 0, Short.MAX_VALUE))
                            .addGroup(pEnderecoLayout.createSequentialGroup()
                                .addComponent(txtComplemento)
                                .addContainerGap())))))
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

        BtnCancelar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnCancelar.png"))); // NOI18N
        BtnCancelar.setText("Cancelar");
        BtnCancelar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnCancelarActionPerformed(evt);
            }
        });

        BtnConfirmar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/pontocristao/icones/BtnConfirmar.png"))); // NOI18N
        BtnConfirmar.setText("Confirmar");
        BtnConfirmar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                BtnConfirmarActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(BtnConfirmar)
                        .addGap(18, 18, 18)
                        .addComponent(BtnCancelar))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(pInformacoesSistema, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pInformacoesPessoais, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(pEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(pInformacoesSistema, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pInformacoesPessoais, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(pEndereco, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(BtnCancelar)
                    .addComponent(BtnConfirmar))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        pack();
    }// </editor-fold>//GEN-END:initComponents

    private void BtnConsultarCepActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConsultarCepActionPerformed
        BuscaCep(txtCep.getText().toString());
        txtNumero.requestFocus();
    }//GEN-LAST:event_BtnConsultarCepActionPerformed

    private void BtnCancelarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnCancelarActionPerformed
        Object[] botoes = {"Sim", "Não"};
        int resposta = JOptionPane.showOptionDialog(null,
                "Deseja cancelar o cadastro de Funcionário? ",
                "Confirmação",
                JOptionPane.DEFAULT_OPTION, JOptionPane.QUESTION_MESSAGE, null,
                botoes, botoes[0]);
        if (resposta == 0) {
            this.dispose();
        }
    }//GEN-LAST:event_BtnCancelarActionPerformed

    private void BtnConfirmarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_BtnConfirmarActionPerformed
        if (ValidaCampos()) {
            if (ValidaSenha()) {

                AtualizarModelo();

                Exception erro = controle.Salvar();

                if (erro != null) {
                    Utilidades.MostrarMensagemErro(erro);
                } else {
                    modeloAtualizado = true;
                    this.dispose();
                }
            }
        }
    }//GEN-LAST:event_BtnConfirmarActionPerformed

    public Boolean ValidaSenha() {
        Boolean retorno = false;
        if (Arrays.equals(txtSenha.getPassword(), txtConfirmacaoSenha.getPassword())) {
            retorno = true;
        } else {
            JOptionPane.showMessageDialog(null, "As senhas digitadas devem ser iguais.");
        }
        return retorno;
    }

    public Boolean ValidaCampos() {
        //Campos Obrigatórios
        Boolean retorno = true;

        if (txtLogin.getText().equals("")
                || jcDataAdmissao.getDate() == null
                || txtNome.getText().equals("")
                || Utilidades.getValorSemMascara(txtCpf).equals("")
                || Utilidades.getValorSemMascara(txtRg).equals("")
                || Utilidades.getValorSemMascara(txtCarteiraTrabalho).equals("")
                || jcDataNascimento.getDate() == null
                || Utilidades.getValorSemMascara(txtTelefone).equals("")
                || Utilidades.getValorSemMascara(txtCelular).equals("")
                || Utilidades.getValorSemMascara(txtCep).equals("")
                || txtRua.getText().equals("")
                || txtNumero.getText().equals("")
                || txtCidade.getText().equals("")
                || txtBairro.getText().equals("")
                || jComboSexo.getSelectedItem().equals("")
                || jComboEstado.getSelectedItem().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos os campos em negrito devem estar preenchidos.");
            retorno = false;
        } else if (controle.getFuncionario().getId() == 0 && txtSenha.getPassword().toString().equals("")) {
            JOptionPane.showMessageDialog(null, "Todos os campos em negrito devem estar preenchidos.");
            retorno = false;
        }

        return retorno;
    }

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

    @Override
    public void dispose() {
        if (controle != null) {
            controle.Dispose();
        }

        super.dispose();
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
            java.util.logging.Logger.getLogger(FrmCadastrarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(FrmCadastrarFuncionario.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the dialog */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                FrmCadastrarFuncionario dialog = new FrmCadastrarFuncionario(new javax.swing.JFrame(), true, 0);
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
    private javax.swing.JComboBox<String> jComboEstado;
    private javax.swing.JComboBox<String> jComboSexo;
    private com.toedter.calendar.JDateChooser jcDataAdmissao;
    private com.toedter.calendar.JDateChooser jcDataCadastro;
    private com.toedter.calendar.JDateChooser jcDataNascimento;
    private javax.swing.JLabel lBairro;
    private javax.swing.JLabel lCarteiraTrabalho;
    private javax.swing.JLabel lCelular;
    private javax.swing.JLabel lCep;
    private javax.swing.JLabel lCidade;
    private javax.swing.JLabel lCodigo;
    private javax.swing.JLabel lComplemento;
    private javax.swing.JLabel lConfirmacaoSenha;
    private javax.swing.JLabel lCpf;
    private javax.swing.JLabel lDataAdmissao;
    private javax.swing.JLabel lDataCadastro;
    private javax.swing.JLabel lDataNascimento;
    private javax.swing.JLabel lEmail;
    private javax.swing.JLabel lEstado;
    private javax.swing.JLabel lLogin;
    private javax.swing.JLabel lNome;
    private javax.swing.JLabel lNumero;
    private javax.swing.JLabel lRg;
    private javax.swing.JLabel lRua;
    private javax.swing.JLabel lSenha;
    private javax.swing.JLabel lSexo;
    private javax.swing.JLabel lTelefone;
    private javax.swing.JPanel pEndereco;
    private javax.swing.JPanel pInformacoesPessoais;
    private javax.swing.JPanel pInformacoesSistema;
    private javax.swing.JTextField txtBairro;
    private javax.swing.JFormattedTextField txtCarteiraTrabalho;
    private javax.swing.JFormattedTextField txtCelular;
    private javax.swing.JFormattedTextField txtCep;
    private javax.swing.JTextField txtCidade;
    private javax.swing.JTextField txtCodigo;
    private javax.swing.JTextField txtComplemento;
    private javax.swing.JPasswordField txtConfirmacaoSenha;
    private javax.swing.JFormattedTextField txtCpf;
    private javax.swing.JTextField txtEmail;
    private javax.swing.JTextField txtLogin;
    private javax.swing.JTextField txtNome;
    private javax.swing.JTextField txtNumero;
    private javax.swing.JFormattedTextField txtRg;
    private javax.swing.JTextField txtRua;
    private javax.swing.JPasswordField txtSenha;
    private javax.swing.JFormattedTextField txtTelefone;
    // End of variables declaration//GEN-END:variables
}
