package repository;

import domain.Cont;
import domain.Film;
import repository.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Properties;

public class ConturiRepository {
    private JdbcUtils dbUtils;

    public ConturiRepository(Properties props){
        this.dbUtils = new JdbcUtils(props);
    }

    public void save(Cont cont) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("insert into Conturi (nume, prenume, email, telefon, rol, parola) values (?, ?, ?, ?, ?, ?)")) {
            prepStmt.setString(1, cont.getNume());
            prepStmt.setString(2, cont.getPrenume());
            prepStmt.setString(3, cont.getEmail());
            prepStmt.setString(4, cont.getTelefon());
            prepStmt.setString(5, cont.getRol());
            prepStmt.setString(6, cont.getParola());

            int status = prepStmt.executeUpdate();
            if(status == 0)
                throw new RepoException("Contul nu a fost inregistrat !");
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
            throw new RepoException("Contul deja existent !");
        }
    }

    public Cont login(String email, String parola){
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("select * from Conturi where email = ?;")) {
            prepStmt.setString(1, email);

            try(ResultSet result = prepStmt.executeQuery()){
                if (result.next()){
                    Long id = result.getLong("id");
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");
                    String emailDB = result.getString("email");
                    String telefon = result.getString("telefon");
                    String rol = result.getString("rol");
                    String parolaDB = result.getString("parola");

                    if(parola.equals(parolaDB))
                        return new Cont(id, nume, prenume, email, telefon, rol, "");
                }
            }
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
        return null;
    }

    public Cont getById(Long id){
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("select * from Conturi where id = ?;")) {
            prepStmt.setLong(1, id);

            try(ResultSet result = prepStmt.executeQuery()){
                if (result.next()){
                    String nume = result.getString("nume");
                    String prenume = result.getString("prenume");
                    String email = result.getString("email");
                    String telefon = result.getString("telefon");
                    String rol = result.getString("rol");

                    return new Cont(id, nume, prenume, email, telefon, rol, "");
                }
            }
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
        return null;
    }
}
