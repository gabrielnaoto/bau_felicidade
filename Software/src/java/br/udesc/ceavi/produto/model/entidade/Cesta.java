package br.udesc.ceavi.produto.model.entidade;

import br.udesc.ceavi.produto.model.entidade.state.CestaEmAndamento;
import br.udesc.ceavi.produto.model.entidade.state.CestaState;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Cesta implements Serializable, SampleEntity {

    private int id;
    private Date data;
    private String tema;
    private double valorMaximo;
    private int peso;
    private List<Categoria> categorias;
    private List<Produto> produtos;
    protected CestaState estadoAtual = new CestaEmAndamento();;

    public Cesta() {
        produtos = new ArrayList<Produto>();
        categorias = new ArrayList<Categoria>();
        estadoAtual = new CestaEmAndamento();
    }

    public Cesta(Date data, String tema, double valorMaximo) {
        this.data = data;
        this.tema = tema;
        this.valorMaximo = valorMaximo;
    }

    public Cesta(int id, Date data, String tema, double valorMaximo, int peso) {
        this.id = id;
        this.peso = peso;
        this.tema = tema;
        this.data = data;
        this.valorMaximo = valorMaximo;
    }

    public List<Categoria> getCategorias() {
        return categorias;
    }

    public void setCategorias(List<Categoria> categorias) {
        this.categorias = categorias;
    }

    public void addCategoria(Categoria c) {
        categorias.add(c);
    }

    @Override
    public Integer getCodigo() {
        return id;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getPeso() {
        return peso;
    }

    public void setPeso(int peso) {
        this.peso = peso;
    }

    public String getTema() {
        return tema;
    }

    public void setTema(String tema) {
        this.tema = tema;
    }

    public Date getData() {
        return data;
    }

    public void setData(Date data) {
        this.data = data;
    }

    public double getValorMaximo() {
        return valorMaximo;
    }

    public void setValorMaximo(double valorMaximo) {
        this.valorMaximo = valorMaximo;
    }

    public List<Produto> getProdutos() {
        return produtos;
    }

    public void setProdutos(List<Produto> produtos) {
        this.produtos = produtos;
    }

    public void addProduto(Produto p) {
        produtos.add(p);
    }

    public void finalizarCesta() {
        estadoAtual = estadoAtual.trocaEstado();
        estadoAtual.verificaEstado();
    }

}
