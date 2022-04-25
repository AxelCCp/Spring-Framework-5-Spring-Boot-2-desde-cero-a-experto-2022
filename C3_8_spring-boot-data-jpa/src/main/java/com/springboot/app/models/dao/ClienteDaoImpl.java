package com.springboot.app.models.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.springboot.app.models.entity.Cliente;

@Repository
public class ClienteDaoImpl implements IClienteDao{

	@SuppressWarnings("unchecked")
	@Transactional(readOnly=true)
	@Override
	public List<Cliente> findAll() {
		
		return em.createQuery("from Cliente").getResultList();
	}
	
	
	@Override
	@Transactional
	public void save(Cliente cliente) {
		// TODO Auto-generated method stub
		if(cliente.getId() != null && cliente.getId() > 0) {
			em.merge(cliente);
		}else {
			em.persist(cliente);
		}
	}
	
	@Override
	@Transactional(readOnly=true)
	public Cliente findOne(Long id) {
		// TODO Auto-generated method stub
		return em.find(Cliente.class, id);
	}
	
	@Override
	@Transactional
	public void delete(Long id) {
		// TODO Auto-generated method stub
		Cliente cliente  = findOne(id);
		em.remove(cliente);
	}
	
	
	@PersistenceContext  //INYECTA EL ENTITY MANAGER SEGÚN LA CONFIGURACIÓN DE LA UNIDAD DE PERSISTENCIA QUE CONTIENE EL PROVEEDOR JPA.
	private EntityManager em;
	

}
