/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Produtos;

import DAO.ClasseDAO;


/**
 *
 * @author Aluno
 */
public class ClasseProduto extends ClasseDAO{

    private int idProduto;
    private String descricao;
    private float preco;
    private String categoria;
    private float ICMS;
    private String aliquota;

    public float getICMS() {
        return ICMS;
    }

    public void setICMS(float ICMS) {
        this.ICMS = ICMS;
    }

    public String getAliquota() {
        return aliquota;
    }

    public void setAliquota(String aliquota) {
        this.aliquota = aliquota;
    }
    
    public int getIdProduto() {
        return idProduto;
    }

    public void setIdProduto(int idProduto) {
        this.idProduto = idProduto;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public float getPreco() {
        return preco;
    }

    public void setPreco(float preco) {
        this.preco = preco;
    }

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }
    
    
    
    
    
}
