/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package br.udesc.ceavi.produto.model.entidade.state;

/**
 *
 * @author camargo
 */
public class CestaEmAndamento implements CestaState {

    @Override
    public CestaState verificaEstado() {
        System.out.println("A cesta está em Andamento");
        return this;
    }

    @Override
    public CestaState trocaEstado() {
        return new CestaFinalizada();
    }

}
