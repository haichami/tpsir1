/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package service;

import bean.Compte;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author ANASS
 */
@Stateless
public class CompteFacade extends AbstractFacade<Compte> {

     @EJB
    OperationFacade operationFacade;
     
    @PersistenceContext(unitName = "tpsir1PU")
    private EntityManager em;

    @Override
    protected EntityManager getEntityManager() {
        return em;
    }

    public CompteFacade() {
        super(Compte.class);
    }
   
    
    public int save(Compte compte){
        Compte loadedCompte = find(compte.getId());
        if (loadedCompte!=null){
            return -1;
        }else if (compte.getSolde()<100){
            return -2;
        }else{
            create(compte);
            operationFacade.createOperation(compte, compte.getSolde(),1);
            return 1;

        }
    }
    public int debiter(Compte compte,Double montant){
        Compte loadedCompte =find(compte.getId());
        if(loadedCompte==null){
            return -1;
        }else{
            compte.setSolde(compte.getSolde()+montant);
            edit(compte);
            operationFacade.createOperationDebit(compte, montant);
            return 1;
        }
    }
    public int crediter(Compte compte,Double montant){
        Compte loadedCompte = find(compte.getId());
        if (loadedCompte==null){
            return -1;
        }else if (compte.getSolde()-montant<0){
            return -2;
        }else{
            compte.setSolde(compte.getSolde()+montant);
            edit(compte);
            operationFacade.createOperationCredit(compte, montant);
            return 1;
        }
    }
    
}
