package br.com.yaw.prime.service;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

import br.com.yaw.prime.model.Mercadoria;
import br.com.yaw.prime.model.Usuario;

/**
 * Componente EJB que define as operações de negócio da entidade <code>Usuario</code>.
 * 
 * <p>Herda <code>AbstractPersistence</code> para resolver as operações básicas da persistência de <code>Usuario</code>.</p>
 * 
 * @author YaW Tecnologia
 */
@Stateless
public class LoginService extends AbstractPersistence<Usuario, Long>{

	/**
	 * O container injeta a referência para o <code>EntityManager</code>.
	 */
	@PersistenceContext
	private EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	public LoginService() {
		super(Usuario.class);
	}

	public boolean existeUsuario(Usuario usuario) {
		TypedQuery<Usuario> query = em.createQuery("select u from Usuario u where u.nome = :pNome and u.senha = :pSenha", Usuario.class);
		query.setParameter("pNome", usuario.getNome());
		query.setParameter("pSenha", usuario.getSenha());
		
		try {
			Usuario resultado = query.getSingleResult();
		} catch (NoResultException ex) {
			return false;
		}
		
			return true;
	}
	
}
