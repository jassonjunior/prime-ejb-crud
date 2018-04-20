package br.com.yaw.prime.mb;

import static javax.faces.context.FacesContext.getCurrentInstance;

import java.io.Serializable;
import java.util.List;
import java.util.ResourceBundle;

import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import javax.faces.bean.SessionScoped;
import javax.faces.bean.ViewScoped;

import br.com.yaw.prime.model.Mercadoria;
import br.com.yaw.prime.model.Usuario;
import br.com.yaw.prime.service.LoginService;
import br.com.yaw.prime.service.MercadoriaService;

/**
 * Componente responsável por integrar o front-end (páginas JSF) c/ camada de serviço (EJB), para resolver o <code>Login</code>.
 * 
 * <p>Trata-se de um <code>Managed Bean</code>, ou seja, as instâncias dessa classe são controladas pelo <code>JSF</code>. Um objeto é criado ao carregar alguma página do cadastro (Lista / Novo / Editar). Enquanto a página permanecer aberta no browser, o objeto <code>MercadoriaMB</code> permanece no servidor.</p>
 * 
 * <p>Esse componente atua com um papel parecido com o <code>Controller</code> de outros frameworks <code>MVC</code>, ele resolve o fluxo de navegação e liga os componentes visuais com os dados.</p>
 * 
 * @author YaW Tecnologia
 */
@ManagedBean(name="loginMB")
@ViewScoped
public class LoginMB implements Serializable {

	/**
	 * Container injeta a referencia p/ o ejb LoginService
	 */
	@EJB
	private LoginService service;
	
	private Usuario usuario = new Usuario();
	
	
	public LoginMB() {
	}
	
	public Usuario getUsuario() {
		return usuario;
	}
	
	/**
	 * @param key
	 * @return Recupera a mensagem do arquivo properties <code>ResourceBundle</code>.
	 */
	private String getMessageFromI18N(String key) {
		ResourceBundle bundle = ResourceBundle.getBundle("messages_labels", getCurrentInstance().getViewRoot().getLocale());
		return bundle.getString(key);
	}
	
	/**
	 * Adiciona um mensagem no contexto do Faces (<code>FacesContext</code>).
	 * @param summary
	 * @param detail
	 */
	private void addMessage(String summary, String detail) {
		getCurrentInstance().addMessage(null, new FacesMessage(summary, summary.concat("<br/>").concat(detail)));
	}
	
	public String efetuaLogin() {
		System.out.println("Fazendo login do usuário - " + this.usuario.getNome());
		
		boolean existeUsuario = service.existeUsuario(usuario);
		if(existeUsuario) {
			return "listaMercadorias";
		}else {
			addMessage(getMessageFromI18N("msg.erro.login"), "Tentar novamente");
			return "";
		}
	}
	
}
