package Models;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Types;
import java.util.ArrayList;

// @autor Henry Joe Wong Uruqiza
// Archivo: ProductoBD.java
// Creado: 24FEBRERO2011 12:39:08 PM
public class RecompezaBD {
    //Metodo utilizado para insertar un Producto a nuestra Base de datos

    public static synchronized boolean insertarProducto(Recompeza varproducto) {
        Connection cn = null;
        CallableStatement cl = null;
        boolean rpta = false;
        try {
            //Nombre del procedimiento almacenado y como espera tres parametros
            //le ponemos 3 interrogantes
            String call = "{CALL spI_producto(?,?,?,?)}";
            //Obtenemos la conexion
            cn = Conexion.getConexion();
            //Decimos que vamos a crear una transaccion
            cn.setAutoCommit(false);
            //Preparamos la sentecia
            cl = cn.prepareCall(call);
            //Como el codigo se autogenera y es del tipo OUT en el procedimiento
            //almacenado le decimos que es OUT y el del tipo Integer en Java
            cl.registerOutParameter(1, Types.INTEGER);
            //El siguiente parametro del procedimiento almacenado es el nombre
            cl.setString(2, varproducto.getNombre());
            //Y por ultimo el precio 
            cl.setDouble(3, varproducto.getPrecio());
            
          
            //Ejecutamos la sentencia y si nos devuelve el valor de 1 es porque
            //registro de forma correcta los datos
            rpta = cl.executeUpdate() == 1 ? true : false;
            if (rpta) {
                //Confirmamos la transaccion
                cn.commit();
            } else {
                //Negamos la transaccion
                Conexion.deshacerCambios(cn);
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (SQLException e) {
            e.printStackTrace();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (Exception e) {
            e.printStackTrace();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        return rpta;
    }

    //Metodo utilizado para insertar un Producto a nuestra Base de datos
    public static synchronized boolean actualizarProducto(Recompeza varproducto) {
        Connection cn = null;
        CallableStatement cl = null;
        boolean rpta = false;
        try {
            //Nombre del procedimiento almacenado y como espera tres parametros
            //le ponemos 3 interrogantes
            String call = "{CALL spU_producto(?,?,?,?)}";
            //Obtenemos la conexion
            cn = Conexion.getConexion();
            //Decimos que vamos a crear una transaccion
            cn.setAutoCommit(false);
            //Preparamos la sentecia
            cl = cn.prepareCall(call);
            //El primer parametro del procedimiento almacenado es el codigo
            cl.setInt(1, varproducto.getCodigoProducto());
            //El siguiente parametro del procedimiento almacenado es el nombre
            cl.setString(2, varproducto.getNombre());
            //Y por ultimo el precio
            cl.setDouble(3, varproducto.getPrecio());
            //Ejecutamos la sentencia y si nos devuelve el valor de 1 es porque
            //registro de forma correcta los datos
            rpta = cl.executeUpdate() == 1 ? true : false;
            if (rpta) {
                //Confirmamos la transaccion
                cn.commit();
            } else {
                //Negamos la transaccion
                Conexion.deshacerCambios(cn);
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (SQLException e) {
            e.printStackTrace();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (Exception e) {
            e.printStackTrace();
            Conexion.deshacerCambios(cn);
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        return rpta;
    }
    //Metodo utilizado para obtener todos los productos de nuestra base de datos

    public static synchronized ArrayList<Recompeza> obtenerProducto() {
        //El array que contendra todos nuestros productos
        ArrayList<Recompeza> lista = new ArrayList<Recompeza>();
        Connection cn = null;
        CallableStatement cl = null;
        ResultSet rs = null;
        try {
            //Nombre del procedimiento almacenado
            String call = "{CALL spF_producto_all()}";
            cn = Conexion.getConexion();
            cl = cn.prepareCall(call);
            //La sentencia lo almacenamos en un resulset
            rs = cl.executeQuery();
            //Consultamos si hay datos para recorrerlo
            //e insertarlo en nuestro array
            while (rs.next()) {
                Recompeza p = new Recompeza();
                //Obtenemos los valores de la consulta y creamos
                //nuestro objeto producto
               
                p.setCodigoProducto(rs.getInt("codigoProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
               
                //Lo adicionamos a nuestra lista
                lista.add(p);
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (SQLException e) {
            e.printStackTrace();
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (Exception e) {
            e.printStackTrace();
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        return lista;
    }

    public boolean actualizarStocks(ArrayList<Recompeza> vp){
        boolean actualizo=false;
        Connection cn=null;
        PreparedStatement pr=null;
        try{
            cn=Conexion.getConexion();
            for(Recompeza prod : vp){
                String sql="UPDATE Producto SET ";
                sql+=" Stock=? ";
                sql+=" WHERE codigoProducto=?";
                pr=cn.prepareStatement(sql);
                pr.setInt(2, prod.getCodigoProducto());
                if(pr.executeUpdate()==1){
                    actualizo=true;
                }else{
                    actualizo=false;
                    break;
                }
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            actualizo=false;
        }finally{
            try{
                pr.close();
                cn.close();
            }catch(SQLException ex){

            }
        }
        return actualizo;
    }
    
    
    //Metodo utilizado para obtener todos los productos de nuestra base de datos
    public static synchronized Recompeza obtenerProducto(int codigo) {
        Recompeza p = new Recompeza();
        Connection cn = null;
        CallableStatement cl = null;
        ResultSet rs = null;
        try {
            //Nombre del procedimiento almacenado
            String call = "{CALL spF_producto_one(?)}";
            cn = Conexion.getConexion();
            cl = cn.prepareCall(call);
            cl.setInt(1, codigo);
            //La sentencia lo almacenamos en un resulset
            rs = cl.executeQuery();
            //Consultamos si hay datos para recorrerlo
            //e insertarlo en nuestro array
            while (rs.next()) {
                //Obtenemos los valores de la consulta y creamos
                //nuestro objeto producto
                p.setCodigoProducto(rs.getInt("codigoProducto"));
                p.setNombre(rs.getString("nombre"));
                p.setPrecio(rs.getDouble("precio"));
                
            }
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (SQLException e) {
            e.printStackTrace();
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        } catch (Exception e) {
            e.printStackTrace();
            Conexion.cerrarCall(cl);
            Conexion.cerrarConexion(cn);
        }
        return p;
    }
}