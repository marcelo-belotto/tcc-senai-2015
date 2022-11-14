/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DAO;

import Clientes.clientes;
import Delivery.ClasseDelivery;
import Funcionario.ClasseFuncionario;
import Main.ClasseVendaMesa;
import Produtos.ClasseProduto;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;

/**
 *
 * @author Aluno
 */
public class ClasseDAO {
    
    private DefaultTableModel model;
    private Connection con;
    private PreparedStatement stm;
    private ResultSet rs;
   // private final String Conexao = "jdbc:mysql://10.190.81.160/pubmanagerdb";
    private final String Conexao = "jdbc:mysql://localhost/pubmanagerdb";
    //private final String User = "senai";
    private final String User = "root";
   // private final String Senha = "senai";
    private final String Senha = "root";
    
    public void conectar() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            con = DriverManager.getConnection(Conexao,User,Senha);
            
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }
    
    public boolean login(ClasseFuncionario f){
        conectar();
        try{
            stm = con.prepareStatement("select * from usuarios where Login = ? and Senha = ?");
            stm.setString(1, f.getLogin());
            stm.setString(2,f.getSenha());
            rs = stm.executeQuery();
            if (rs.next()){
                return true;
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public String retornaLogado(ClasseFuncionario f){
        String x = "";
        conectar();
        try{
            stm = con.prepareStatement("Select nome from funcionarios where idFuncionario ="+
                    " (select idFuncionario from usuarios where Login = ?)");
            stm.setString(1, f.getLogin());
            rs = stm.executeQuery();
            if (rs.next()){
                x = rs.getString("nome");
            }
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return x;
    }
    
    public DefaultTableModel preencheTabela(String x){
        try{
            conectar();
            stm = con.prepareStatement(x);
            rs = stm.executeQuery();
            
            int colunas = rs.getMetaData().getColumnCount();
            
            if (model == null){
                model = new DefaultTableModel(){
                  public boolean isCellEditable(int linha, int coluna){
                        return false;
                  }  
                };
            }
            
            while (model.getRowCount() > 0) {
                model.removeRow(0);
            }
            model.setColumnCount(0);
            
            for(int aux = 1; aux <=colunas; aux++)
                model.addColumn(rs.getMetaData().getColumnName(aux).toUpperCase());
            
            while(rs.next())
            {
                Object[] linha = new Object[colunas];
                for(int i = 1; i<=colunas;i++){
                    linha[i-1] = rs.getObject(i);
                }
                model.insertRow(rs.getRow()-1, linha);
            }
            
            return model;
            
        }catch(Exception e){
            System.out.println(e);
        }
        return model;
    }
    
    public int ultimoID(String x){
        conectar();
        int i = 0;
        try{
            stm = con.prepareStatement(x);
            rs = stm.executeQuery();
            if (rs.next()){
                i = rs.getInt(1);
                i++;
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }
        
        return i;
    }
    
    public void insereProduto(ClasseProduto p){
        conectar();
        try{
            String query = "Insert into produtos (descricao,preco,aliquota,icms,categoria) "+
                "values (?,?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setString(1, p.getDescricao());
            stm.setFloat(2, p.getPreco());
            stm.setString(3,p.getAliquota());
            stm.setFloat(4, p.getICMS());
            stm.setString(5,p.getCategoria());
            
            stm.execute();
            
            stm.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Produto Salvo Com Sucesso","Cadastro de Produtos",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        
    }
    
    public void insereFuncionario(ClasseFuncionario f){
        conectar();
        try{
            String query = "Insert into funcionarios (funcao,nome,cpf,telefone) "+
                "values (?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setString(1, f.getFuncao().toUpperCase());
            stm.setString(2, f.getNome().toUpperCase());
            stm.setString(3, f.getCpf());
            stm.setString(4,f.getTelefone());
            stm.execute();

            stm = con.prepareStatement("Select MAX(IdFuncionario) from funcionarios");
            rs = stm.executeQuery();
            rs.next();
            f.setID(rs.getInt(1));
            
            rs.close();
            stm.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Funcionário Salvo Com Sucesso","Cadastro de Funcionários",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void insereUsuario(ClasseFuncionario f){
        conectar();
        try{
            String query = "Insert into usuarios (Senha,Login,IdFuncionario)"+
                "values (?,?,?)";
            stm = con.prepareStatement(query);
            stm.setString(1, f.getSenha().toUpperCase());
            stm.setString(2, f.getLogin().toUpperCase());
            stm.setInt(3, f.getID());
            
            stm.execute();
            
            stm.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Usuário Salvo Com Sucesso","Cadastro de Funcionários",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
      public void insereDelivery(ClasseDelivery d){
        conectar();
        try{
            String query = "Insert into delivery (idCliente,telefone,obs,endereco,totalPedido) "+
                "values (?,?,?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setInt(1, d.getIdCliente());
            stm.setString(2, d.getTelefone());
            stm.setString(3, d.getObs());
            stm.setString(4, d.getEndereco());
            stm.setString(5, d.getTotalPedido().toUpperCase());
            stm.execute();
            
            stm = con.prepareStatement("Select MAX(idDelivery) from delivery");
            rs = stm.executeQuery();
            rs.next();
            d.setIdDelivery(rs.getInt(1));
            rs.close();
            
            query = "Insert into produtodelivery (precoProduto,qtdProduto,idDelivery,idProdutos) "+
                "values (?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setFloat(1, d.getPrecoProduto());
            stm.setInt(2, d.getQtdProduto());
            stm.setInt(3, d.getIdDelivery());
            stm.setInt(4, d.getIdProduto());
            stm.execute();

            stm.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Cliente Salvo Com Sucesso","Cadastro de Clientes",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void insereCliente(clientes c){
        conectar();
        try{
            String query = "Insert into clientes (nome,endereco,telefone,cpf,cidade) "+
                "values (?,?,?,?,?)";
            stm = con.prepareStatement(query);
            stm.setString(1, c.getNome().toUpperCase());
            stm.setString(2, c.getEndereco().toUpperCase());
            stm.setString(3, c.getTelefone());
            stm.setString(4, c.getCpf());
            stm.setString(5, c.getCidade().toUpperCase());
            stm.execute();

            stm.close();
            con.close();
            JOptionPane.showMessageDialog(null, "Cliente Salvo Com Sucesso","Cadastro de Clientes",
                    JOptionPane.INFORMATION_MESSAGE);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public boolean buscaFuncionario(ClasseFuncionario f){
        conectar();
        try{
            stm = con.prepareStatement("select * from funcionarios where cpf = ?");
            
            stm.setString(1, f.getCpf());
            
            rs = stm.executeQuery();
            if (rs.next()){
                f.setNome(rs.getString("Nome"));
                f.setTelefone(rs.getString("telefone"));
                f.setFuncao(rs.getString("funcao"));
                f.setID(rs.getInt("IdFuncionario"));
                
                stm = con.prepareStatement("Select * from usuarios where IdFuncionario = ?");
            
                stm.setInt(1, f.getID());
                rs = stm.executeQuery();
            
                if(rs.next()){
                   f.setLogin(rs.getString("Login"));
                   f.setSenha(rs.getString("Senha")); 
                }
            
            return true;
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        } 
        return false;
     }
    
    public boolean buscaCliente(clientes c){
        conectar();
        try{
            stm = con.prepareStatement("Select * from clientes where telefone = ?");
            stm.setString(1, c.getCpf());
            
            rs = stm.executeQuery();
            if (rs.next()){
                c.setIdCliente(rs.getInt("IdClientes"));
                c.setNome(rs.getString("Nome"));
                c.setEndereco(rs.getString("endereco"));
                c.setCidade(rs.getString("cidade"));
                c.setCpf(rs.getString("cpf"));
                return true;
            }else{
                JOptionPane.showMessageDialog(null, "Não existe nenhum cliente\n"+
                        "Cadastrado com este Documento","Cadastro De Clientes",JOptionPane.INFORMATION_MESSAGE);
            }
            rs.close();
            stm.close();
            con.close();
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        } 
        return false;
     }
    
    public void alteraFuncionario(ClasseFuncionario f){
        conectar();
        try{
            stm = con.prepareStatement("UPDATE funcionarios "+
                    "set nome = ?,telefone = ?, funcao = ?, cpf = ?"+
                    " where IdFuncionario = ?");
            stm.setString(1, f.getNome());
            stm.setString(2, f.getTelefone());
            stm.setString(3, f.getFuncao());
            stm.setString(4, f.getCpf());
            stm.setInt(5, f.getID());
            
            stm.execute();
            
            stm.close();
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void alteraCliente(clientes c){
        conectar();
        try{
            stm = con.prepareStatement("UPDATE clientes "+
                    "set nome = ?,telefone = ?, endereco = ?, cpf = ?, cidade = ?"+
                    " where idClientes = ?");
            stm.setString(1, c.getNome());
            stm.setString(2, c.getTelefone());
            stm.setString(3, c.getEndereco());
            stm.setString(4, c.getCpf());
            stm.setString(5, c.getCidade());
            stm.setInt(6, c.getIdCliente());
            
            stm.execute();
            
            stm.close();
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void alteraUsuario(ClasseFuncionario f){
        conectar();
        try{
            stm = con.prepareStatement("UPDATE usuarios set login = ?, senha = ? where IdFuncionario = ?");
            stm.setString(1, f.getLogin());
            stm.setString(2, f.getSenha());
            stm.setInt(3, f.getID());
            
            stm.execute();
            
            stm.close();
            con.close();
        }catch(Exception ex){
            System.out.println(ex.getMessage());
        }
    }
    
    public boolean buscaProduto(ClasseProduto p){
        conectar();
        try{
            stm = con.prepareStatement("Select * from produtos where IdProdutos = ?");
            stm.setInt(1, p.getIdProduto());
            rs = stm.executeQuery();
            if (rs.next()){
                p.setDescricao(rs.getString("descricao"));
                p.setAliquota(rs.getString("aliquota"));
                p.setCategoria(rs.getString("categoria"));
                p.setICMS(rs.getFloat("icms"));
                p.setPreco(rs.getFloat("preco"));
                return true;
            }
            
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
        return false;
    }
    
    public void alteraProduto(ClasseProduto p){
        conectar();
        try{
            stm = con.prepareStatement("UPDATE produtos "+
                    "set descricao = ?,categoria = ?, icms = ?, aliquota = ?, preco = ?"+
                    " where IdProdutos = ?");
            stm.setString(1, p.getDescricao());
            stm.setString(2, p.getCategoria());
            stm.setFloat(3, p.getICMS());
            stm.setString(4, p.getAliquota());
            stm.setFloat(5, p.getPreco());
            stm.setInt(6, p.getIdProduto());
            
            stm.execute();
            
            stm.close();
            con.close();
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void excluiFuncionario(ClasseFuncionario f){
      conectar();
      try{
          stm = con.prepareStatement("delete from funcionarios where IdFuncionario = ?");
          stm.setInt(1, f.getID());
          stm.execute();
          stm = con.prepareStatement("delete from usuarios where IdFuncionario = ?");
          stm.setInt(1, f.getID());
          stm.execute();
          JOptionPane.showMessageDialog(null,"Funcionário excluído com sucesso!",
                  "Cadastro de funcionários",JOptionPane.INFORMATION_MESSAGE);
          stm.close();
          con.close();
      }catch(Exception ex){
          JOptionPane.showMessageDialog(null,ex.getMessage(),
                  "Cadastro de funcionários",JOptionPane.INFORMATION_MESSAGE);   
      }
    }
    
    public void excluiCliente(clientes c){
      conectar();
      try{
          stm = con.prepareStatement("delete from clientes where IdClientes = ?");
          stm.setInt(1, c.getIdCliente());
          stm.execute();
          JOptionPane.showMessageDialog(null,"Cliente excluído com sucesso!",
                  "Cadastro de Clientes",JOptionPane.INFORMATION_MESSAGE);
          stm.close();
          con.close();
      }catch(Exception ex){
          JOptionPane.showMessageDialog(null,ex.getMessage(),
                  "Cadastro de Clientes",JOptionPane.INFORMATION_MESSAGE);   
      }
    }
    
    public boolean cadastraVenda(ClasseVendaMesa cvm) {
        try {
            conectar();

            stm = con.prepareStatement("Insert into vendas(mesa,totalVenda,dataVenda) values (?,?,?)");
            stm.setInt(1, cvm.getIdMesa());
            stm.setFloat(2, cvm.getTotalVenda());
            stm.setString(3, cvm.getDataVenda());
            
            stm.executeUpdate();
            
            stm.close();
            
            for (int i = 0; i < cvm.getItensMesa().getRowCount(); i++) {
                stm = con.prepareStatement("Insert Into produtovenda(idVenda,IdProdutos,qtdProdutos,precoProduto)"+
                        " values ((select MAX(idVenda) from vendas),?,?,?)");
                stm.setInt(1, new Integer(cvm.getItensMesa().getValueAt(i, 0).toString()));
                stm.setInt(2, new Integer(cvm.getItensMesa().getValueAt(i, 2).toString()));
                stm.setFloat(3, new Float(cvm.getItensMesa().getValueAt(i, 3).toString()));
                
                stm.executeUpdate();
                stm.close();
            }
            return true;
            
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
            return false;
        }
        
    }
    
    
}
