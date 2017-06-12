package pontocristao.modelo;

import java.util.*;
import javax.persistence.*;

/**
 *
 * @author Marcondes
 */
@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Cliente extends ModeloBase {

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false)
    private String telefone;

    private String celular;

    @Column(nullable = false)
    private Date dataCadastro;

    private String email;

    @Column(nullable = false)
    private Integer totalLocacoes = 0;

    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    private Endereco endereco;

    @OneToMany(mappedBy = "cliente", fetch = FetchType.EAGER)
    private Set<Dependente> dependentes = new HashSet<Dependente>(0);

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getCelular() {
        return celular;
    }

    public void setCelular(String celular) {
        this.celular = celular;
    }

    public Date getDataCadastro() {
        return dataCadastro;
    }

    public void setDataCadastro(Date dataCadastro) {
        this.dataCadastro = dataCadastro;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getTotalLocacoes() {
        return totalLocacoes;
    }

    public void setTotalLocacoes(Integer totalLocacoes) {
        this.totalLocacoes = totalLocacoes;
    }

    public Endereco getEndereco() {
        return endereco;
    }

    public void setEndereco(Endereco endereco) {
        this.endereco = endereco;
    }

    public Set<Dependente> getDependentes() {
        return dependentes;
    }

    public void setDependentes(Set<Dependente> dependentes) {
        this.dependentes = dependentes;
    }

}
