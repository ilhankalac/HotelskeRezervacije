
package RepoPattern;

import Models.Datumi;
import Models.Rezervacija;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Ilhan Kalac
 */
public class RezervacijaRepo {
    Connection con;
    public RezervacijaRepo() {
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(HotelRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        String URL =  "jdbc:mysql://localhost:3306/hotelskerezervacije", USER = "root", PASS = "";
        try {
            con = DriverManager.getConnection(URL, USER, PASS);
        } catch (SQLException ex) {
            Logger.getLogger(HotelRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        
    }
    public Integer insert(Rezervacija rezervacija) throws SQLException{
        Integer LastID = 0;
        String insert = "INSERT INTO `Rezervacije`( `DatumDolaska`, `DatumOdlaska`, `Novac`, "
                + "`BrojOdraslih`, `BrojDece`, `SobaID`, `KlijentID`, `VremeOdlaska`, `StatusRezervacije`)"
                + " VALUES (?,?,?,?,?,?,?,?,?)";
        try {
           
            PreparedStatement pst = con.prepareStatement(insert, Statement.RETURN_GENERATED_KEYS);
            
            pst.setString(1,rezervacija.getDatumDolaska());
            pst.setString(2,rezervacija.getDatumOdlaska());
            pst.setDouble(3, rezervacija.getNovac());
            pst.setInt(4, rezervacija.getBrojOdraslih());
            pst.setInt(5, rezervacija.getBrojDece());
            pst.setInt(6, rezervacija.getSobaID());
            pst.setInt(7, rezervacija.getKlijentID());
            pst.setString(8, rezervacija.getVremeOdlaska());
            pst.setBoolean(9, rezervacija.getStatusRezervacije());

            pst.executeUpdate();

            //sa ovim se uzima ID poslednjeg inserta u tabeli rezervacije
            ResultSet rs = pst.getGeneratedKeys();

            while(rs.next())
                LastID  = rs.getInt(1);
            
           
            return LastID;
        } catch (SQLException ex) {
            return 0;
        }
        finally{
            con.close();
        }
    }
    public boolean  updateStatusRezervacije(String Id) throws SQLException{
         String update = "update rezervacije  set StatusRezervacije = 1 where Id = ?";
          try {
            PreparedStatement pst = con.prepareStatement(update);
            pst.setString(1, Id);
            
            
            pst.executeUpdate();
            return true;
          } catch (SQLException e) {
          return false;
        }
    }
    public ArrayList<Rezervacija>  aktivneRezervacije(String Id){
        
        
        ArrayList<Rezervacija> rezervacije = new ArrayList<>();
        Statement st;
        try {
            String upit = "select Id, datumOdlaska, datumDolaska, VremeOdlaska, StatusRezervacije from  rezervacije "
                    + "where StatusRezervacije = 1 and datumDolaska > now() and sobaId = "+Id;
            
            st = con.createStatement();
            ResultSet rs = st.executeQuery(upit);
            
            while(rs.next()){
                Rezervacija  rezervacija = new Rezervacija();
                rezervacija.setRezervacijaId(rs.getInt("Id"));
                rezervacija.setDatumOdlaska(rs.getString("datumOdlaska"));
                rezervacija.setDatumDolaska(rs.getString("datumDolaska"));
                rezervacija.setVremeOdlaska(rs.getString("VremeOdlaska"));
                rezervacija.setStatusRezervacije(rs.getBoolean("StatusRezervacije"));
                rezervacije.add(rezervacija);
             }
        
        } catch (SQLException ex) {
            Logger.getLogger(RezervacijaRepo.class.getName()).log(Level.SEVERE, null, ex);
        }
        return rezervacije;
    }
    public boolean  dostupna(Rezervacija rezervacija){
        
        
        Statement st;
         try {
             String upit = "select id "
                    + "from rezervacije "
                    + " where DatumDolaska = "+rezervacija.getDatumDolaska()
                    + " and DatumOdlaska = "+rezervacija.getDatumOdlaska();

             if(logickiUnosDatuma(rezervacija) && (proveraDostupnihTermina(rezervacija, listaRezervisanihDatuma(rezervacija)))){
                 
                 
                st = con.createStatement();
                ResultSet rs = st.executeQuery(upit);

                while(rs.next()){
                    return false;
                }
                
                return  true;
             }
             else
                 return  false;
         } catch (SQLException ex) {
             
             Logger.getLogger(RezervacijaRepo.class.getName()).log(Level.SEVERE, null, ex);
             return false;
         }           
    }
    public boolean logickiUnosDatuma(Rezervacija rezervacija){
        
        String timeStamp = new SimpleDateFormat("yyyy-MM-dd").format(Calendar.getInstance().getTime());
        
        // proverava se da li je korisnik uneo negativan datum
        if (rezervacija.getDatumDolaska().compareTo(timeStamp) < 0)
            return false;
        
        int godinaDolaska =Integer.parseInt(rezervacija.getDatumDolaska().substring(0, 4));
        int mesecDolaska =Integer.parseInt( rezervacija.getDatumDolaska().substring(5, 7));
        int danDolaska = Integer.parseInt(rezervacija.getDatumDolaska().substring(8, 10));
        
        int godinaOdlaska =Integer.parseInt( rezervacija.getDatumOdlaska().substring(0, 4));
        int mesecOdlaska = Integer.parseInt(rezervacija.getDatumOdlaska().substring(5, 7));
        int danOdlaska =Integer.parseInt( rezervacija.getDatumOdlaska().substring(8, 10));
        
        if (godinaDolaska > godinaOdlaska)
            return  false;
        
        if(godinaDolaska == godinaOdlaska)
            if(mesecDolaska > mesecOdlaska)
                return  false;
        
        
        if(mesecDolaska == mesecOdlaska)
            if(danDolaska > danOdlaska)
                return false;
        
        return true;
    }
    
    public ArrayList<Datumi> listaRezervisanihDatuma(Rezervacija rezervacija){
        
        Statement st;
         try {
             String upit = "select DatumDolaska, DatumOdlaska "
                    + "from rezervacije where StatusRezervacije = 1";
             ArrayList<Datumi> datumi = new ArrayList<>();
             
             
             if(logickiUnosDatuma(rezervacija)){
                 
                st = con.createStatement();
                ResultSet rs = st.executeQuery(upit);

                while(rs.next()){
                   Datumi datum = new Datumi(rs.getString("DatumDolaska"), rs.getString("DatumOdlaska"));
                   datumi.add(datum);
                }
                
                return  datumi;
             }
             else
                 return  null;
         } catch (SQLException ex) {
             
             Logger.getLogger(RezervacijaRepo.class.getName()).log(Level.SEVERE, null, ex);
             return null;
         }           

    }
    public boolean proveraDostupnihTermina(Rezervacija rezervacija, ArrayList<Datumi> listaRezervisanihDatuma){
        
        if(listaRezervisanihDatuma.size()== 0)
            return true;
        
        if(listaRezervisanihDatuma.size()==1){
            if((rezervacija.getDatumDolaska().compareTo(listaRezervisanihDatuma.get(0).getDatumDolaska()) < 0
               && rezervacija.getDatumOdlaska().compareTo(listaRezervisanihDatuma.get(0).getDatumDolaska()) < 0 )
               || (rezervacija.getDatumDolaska().compareTo(listaRezervisanihDatuma.get(0).getDatumOdlaska()) > 0 ))
                return true;
            else
                return false;
        }
        
        
        for(int i = 0; i < listaRezervisanihDatuma.size(); i++){
            if(i == listaRezervisanihDatuma.size()-1)
                if(rezervacija.getDatumDolaska().compareTo(listaRezervisanihDatuma.get(i).getDatumOdlaska()) > 0)
                    return true;
                else
                    return false;

            if( rezervacija.getDatumDolaska().compareTo(listaRezervisanihDatuma.get(i).getDatumOdlaska()) > 0
                && rezervacija.getDatumOdlaska().compareTo(listaRezervisanihDatuma.get(i+1).getDatumDolaska()) < 0 )  
                   
            return true;
        }
        return false;
    }
    
    
}
