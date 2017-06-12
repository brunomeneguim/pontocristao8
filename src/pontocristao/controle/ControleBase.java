package pontocristao.controle;

import org.hibernate.*;
import pontocristao.modelo.*;
import pontocristao.util.HibernateUtil;

/**
 *
 * @author Marcondes
 */
public abstract class ControleBase {

    private Session sessao;

    public Session getSessao() {
        return sessao;
    }

    public void setSessao(Session novaSessao) {
        if (sessao != null) {
            if (sessao.isOpen()) {
                sessao.close();
            }
        }
        sessao = novaSessao;
    }

    private ModeloBase modelo;

    public ModeloBase getModelo() {
        return modelo;
    }

    public void setModelo(ModeloBase modelo) {
        this.modelo = modelo;
    }

    public ControleBase() {
        try {
            sessao = HibernateUtil.getSessionFactory().openSession();
        } catch (Exception e) {
            Exception erro = e;
        }
    }

    public Exception Salvar(ModeloBase modelo) {
        Exception erro = null;

        try {

            Transaction transacao = sessao.getTransaction();
            transacao.begin();

            sessao.saveOrUpdate(modelo);

            transacao.commit();

        } catch (Exception e) {
            erro = e;
        } finally {
            return erro;
        }
    }

    public void Dispose() {
        if (sessao != null) {
            if (sessao.isOpen()) {
                sessao.close();
            }
        }
    }

}
