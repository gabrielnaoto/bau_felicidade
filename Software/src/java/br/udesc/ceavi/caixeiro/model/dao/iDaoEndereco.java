package br.udesc.ceavi.caixeiro.model.dao;
import br.udesc.ceavi.caixeiro.model.Endereco;
import br.udesc.ceavi.core.persistence.Persistible;

/**
 * @author Samuel
 * @version 1.0
 * @created 04-jun-2016 09:51:01
 */
public interface iDaoEndereco extends Persistible<Endereco> {

    public void limpaEnderecos();

}