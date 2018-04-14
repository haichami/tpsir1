/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Compte;
import bean.Operation;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ANASS
 */
@Stateless
public class OperationFacade extends AbstractFacade<Operation> {

    @PersistenceContext(unitName = "tpsir1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public OperationFacade() {
        super(Operation.class);
    }
    public void createOperationDebit(Compte compte, Double montant){
        createOperation(compte, montant, 2);
        
    }
    public void createOperationCredit(Compte compte, Double montant){
        createOperation(compte, montant, 1);
        
    }
    public void createOperation(Compte compte, Double montant,int type){
        Operation operation = new Operation();
        operation.setMontant(montant);
        operation.setDateOperation(new Date());
        operation.setType(type);
        operation.setCompte(compte);
        create(operation);
    }
    public List<Operation> findByCompte(Compte compte){
        return em.createQuery("SELECT op FROM Operation op WHERE op.compte.id='"+compte.getId()+"'").getResultList();
    }
    
}
