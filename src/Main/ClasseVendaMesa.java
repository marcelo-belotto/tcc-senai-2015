/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package Main;

import DAO.ClasseDAO;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aluno
 */
public class ClasseVendaMesa extends ClasseDAO {
    private int idMesa;
    private float TotalVenda;
    private String dataVenda;

    public String getDataVenda() {
        return dataVenda;
    }

    public void setDataVenda(String dataVenda) {
        this.dataVenda = dataVenda;
    }
    
    private DefaultTableModel itensMesa;

    public int getIdMesa() {
        return idMesa;
    }

    public void setIdMesa(int idMesa) {
        this.idMesa = idMesa;
    }

    public float getTotalVenda() {
        return TotalVenda;
    }

    public void setTotalVenda(float TotalVenda) {
        this.TotalVenda = TotalVenda;
    }

    public DefaultTableModel getItensMesa() {
        return itensMesa;
    }

    public void setItensMesa(DefaultTableModel itensMesa) {
        this.itensMesa = itensMesa;
    }
    
    
    
    
    
}
