package Models;

import java.io.UnsupportedEncodingException;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.InvalidParameterSpecException;
import java.sql.*;
import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;


public class Usuario{

	private int usuario_Codigo;
	private String usuario_Nombre;
	private String usuario_app;
        private String usuario_apm;
	private String usuario_User;
	private String usuario_Clave;
        private String Direcion;
        private String telefono;
	private int usuario_Privilegio;

	public Usuario(){

	}

  
    public Usuario verificarUsuario(String user, String clave){
        Usuario u=null;
        Connection cn=null;
        PreparedStatement pr=null;
        ResultSet rs=null;
        try{
            cn=Conexion.getConexion();
            String sql="SELECT * FROM Usuario WHERE NameUser=? AND contraseña=?";
            pr=cn.prepareStatement(sql);
            pr.setString(1, user);
            pr.setString(2, clave);
            rs=pr.executeQuery();
            while(rs.next()){
                u=new Usuario();
                
                u.setUsuario_nombre(rs.getString("Nombre"));
                u.setUsuario_app(rs.getString("App"));
                u.setUsuario_apm(rs.getString("Apm"));
                u.setUsuario_user(rs.getString("NameUser"));
                //u.setUsuario_direcion(rs.getString("Direcion"));
                
                u.setUsuario_telefono(rs.getString("Telefono"));
                u.setUsuario_clave(rs.getString("Contraseña"));
                u.setUsuario_privilegio(rs.getInt("Privilegio"));
                
                
                break;
            }
        }catch(SQLException ex){
            ex.printStackTrace();
            u=null;
        }finally{
            try{
                rs.close();
                pr.close();
                rs.close();
            }catch(SQLException ex){

            }
        }
        return u;
    }

	public int getUsuario_codigo(){
		 return this.usuario_Codigo;
	}
	public String getUsuario_nombre() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException{
                String masterPassword = "password";
            Aes a=new Aes();
            String nombre=this.usuario_Nombre;
            nombre=a.decrypt(this.usuario_Nombre,masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
        
		 return nombre;
	}
        public String getUsuario_Direcion()throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException{
                String masterPassword = "password";
            Aes a=new Aes();
            String dire=this.Direcion;
            
            dire=a.decrypt(this.Direcion,masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
		 return dire;
	}
        
        public String getUsuario_Telefono() throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException{
            String masterPassword = "password";
            Aes a=new Aes();
            String te=this.telefono;
            te=a.decrypt(this.telefono,masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
            return te;
	}
	public String getUsuario_app()throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException{
                String masterPassword = "password";
            Aes a=new Aes();
            String app=this.usuario_app;
            app=a.decrypt(this.usuario_app,masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
		 return app;
	}
        public String getUsuario_apm()throws NoSuchAlgorithmException, InvalidKeySpecException, NoSuchPaddingException, InvalidParameterSpecException, IllegalBlockSizeException, BadPaddingException, UnsupportedEncodingException, InvalidKeyException, InvalidAlgorithmParameterException{
                String masterPassword = "password";
            Aes a=new Aes();
            String apm=this.usuario_apm;
            apm=a.decrypt(this.usuario_apm,masterPassword, "2��\"trh�)6�|R", "q40mTVuvoo1XpEGU");
		 return apm;
	}
	public String getUsuario_user(){
		 return this.usuario_User;
	}
	public String getUsuario_clave(){
		 return this.usuario_Clave;
	}
	public int getUsuario_privilegio(){
		 return this.usuario_Privilegio;
	}

	/** Metodos SET de la clase usuario */

	public void setUsuario_codigo(int usuario_Codigo){
		 this.usuario_Codigo=usuario_Codigo;
	}
	public void setUsuario_nombre(String usuario_Nombre){
		 this.usuario_Nombre=usuario_Nombre;
	}
        public void setUsuario_direcion(String direcion){
		 this.Direcion=usuario_Nombre;
	}
        public void setUsuario_telefono(String telefono){
		 this.telefono=telefono;
	}
	public void setUsuario_app(String usuario_Apellido){
		 this.usuario_app=usuario_Apellido;
	}
        public void setUsuario_apm(String usuario_Apellido){
		 this.usuario_apm=usuario_Apellido;
	}
	public void setUsuario_user(String usuario_User){
		 this.usuario_User=usuario_User;
	}
	public void setUsuario_clave(String usuario_Clave){
		 this.usuario_Clave=usuario_Clave;
	}
	public void setUsuario_privilegio(int usuario_Privilegio){
		 this.usuario_Privilegio=usuario_Privilegio;
	}

    public Boolean RegistrarUsuario(String nombre, String appat, String apmat, String user, String phone, String clave,int privi) {
               
        Connection cn=null;
        PreparedStatement pr=null;
        Boolean a;
        try{
            cn=Conexion.getConexion();
            String sql="insert into Usuario (NameUser,Nombre,App,Apm,Telefono,contraseña,Privilegio) values (?,?,?,?,?,?,?)";
            pr=cn.prepareStatement(sql);  
            pr.setString(1,user);
            pr.setString(2,nombre);    
            pr.setString(3,appat); 
            pr.setString(4,apmat);    
            pr.setString(5,phone); 
            pr.setString(6,clave);    
            pr.setInt(7,privi); 
            pr.executeUpdate();
            return a=true;
            
        }catch(SQLException ex){
            ex.printStackTrace();
            return a=false;
        }finally{
            try{
               
                pr.close();
                return a=false;
            }catch(SQLException ex){
            return a=false;
            }
        }
            
        
    }
    
    public void CambiarContraseña(String user, String Clave) {
        Usuario u = null;
        Connection cn = null;
        PreparedStatement pr = null;
        ResultSet rs = null;
        try {
            cn = Conexion.getConexion();
            String sql = "UPDATE Usuario SET Usuario.contraseña='" + Clave + "' WHERE usuario.NameUser='" + user + "' ";
            pr = cn.prepareStatement(sql);
            pr.executeUpdate();

        } catch (SQLException ex) {
            ex.printStackTrace();
        } finally {
            try {
                pr.close();
            } catch (SQLException ex) {

            }
        }

    }

}

