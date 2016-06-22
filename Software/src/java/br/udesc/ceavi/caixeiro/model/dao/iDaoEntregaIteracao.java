/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.caixeiro.model.dao;

import br.udesc.ceavi.caixeiro.model.Entrega;
import br.udesc.ceavi.caixeiro.model.EntregaIteracao;
import br.udesc.ceavi.core.persistence.Persistible;

/**
 *
 * @author Ricardo Augusto Küstner
 */
public interface iDaoEntregaIteracao extends Persistible<EntregaIteracao> {

    public Iterable<EntregaIteracao> getIretacaoEntrega(Entrega entrega);

}
