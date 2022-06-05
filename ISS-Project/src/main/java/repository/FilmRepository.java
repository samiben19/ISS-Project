package repository;

import domain.Film;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import repository.hibernate.HibernateUtil;
import repository.jdbc.JdbcUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class FilmRepository {
    private JdbcUtils dbUtils;

    public FilmRepository(Properties props){
        this.dbUtils = new JdbcUtils(props);
    }

    public void save(Film film){
        Connection con = dbUtils.getConnection();

        try(PreparedStatement prepStmt = con.prepareStatement("insert into Filme (nume, autor, durata) values (?, ?, ?)")){
            prepStmt.setString(1, film.getNume());
            prepStmt.setString(2, film.getAutor());
            prepStmt.setString(3, film.getDurata());
//            con.createStatement().execute("PRAGMA foreign_keys = ON");
//            PreparedStatement pr = con.prepareStatement("PRAGMA foreign_keys;");
//            ResultSet r = pr.executeQuery();
//
//            while(r.next())
//                System.out.println("Foreign key: " + r.getLong(1));

            int status = prepStmt.executeUpdate();
            if(status == 0)
                throw new RepoException("Filmul nu a fost salvat !");
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
    }

    public void update(Film newFilm){
        Connection con = dbUtils.getConnection();

        try(PreparedStatement prepStmt = con.prepareStatement("update Filme set nume = ?, autor = ?, durata = ? where id = ?")){
            prepStmt.setString(1, newFilm.getNume());
            prepStmt.setString(2, newFilm.getAutor());
            prepStmt.setString(3, newFilm.getDurata());
            prepStmt.setLong(4, newFilm.getId());
//            con.createStatement().execute("PRAGMA foreign_keys = ON");
//            PreparedStatement pr = con.prepareStatement("PRAGMA foreign_keys;");
//            ResultSet r = pr.executeQuery();
//
//            while(r.next())
//                System.out.println("Foreign key: " + r.getLong(1));

            int status = prepStmt.executeUpdate();
            if(status == 0)
                throw new RepoException("Filmul nu a fost modificat !");
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
    }

    public void delete(Long id){
        Connection con = dbUtils.getConnection();

        try(PreparedStatement prepStmt = con.prepareStatement("delete from Filme where id = ?")){
            prepStmt.setLong(1, id);
//            con.createStatement().execute("PRAGMA foreign_keys = ON");
//            PreparedStatement pr = con.prepareStatement("PRAGMA foreign_keys;");
//            ResultSet r = pr.executeQuery();
//
//            while(r.next())
//                System.out.println("Foreign key: " + r.getLong(1));

            int status = prepStmt.executeUpdate();
            if(status == 0)
                throw new RepoException("Filmul cu ID-ul + " + id + " nu exista !");
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
    }

    public List<Film> getAll(){
        List<Film> filme = new ArrayList<>();
//        SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
//        Session session = sessionFactory.openSession();
//
//        try{
//            Query<Film> query = session.createQuery("from Filme", Film.class);
//            filme = query.list();
//        }
//        finally {
//            session.close();
//        }
        Connection con = dbUtils.getConnection();

        try(PreparedStatement prepStmt = con.prepareStatement("select * from Filme;")){
            try(ResultSet result = prepStmt.executeQuery()){
                while (result.next()){
                    long id = result.getLong("id");
                    String nume = result.getString("nume");
                    String autor = result.getString("autor");
                    String durata = result.getString("durata");

                    Film film = new Film(id, nume, autor, durata);
                    filme.add(film);
                }
            }
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
        return filme;
    }

    public Film getById(Long idFind){
        Connection con = dbUtils.getConnection();

        try(PreparedStatement prepStmt = con.prepareStatement("select * from Filme where id = ?;")){
            prepStmt.setLong(1, idFind);
            try(ResultSet result = prepStmt.executeQuery()){
                if (result.next()){
                    long id = result.getLong("id");
                    String nume = result.getString("nume");
                    String autor = result.getString("autor");
                    String durata = result.getString("durata");

                    return new Film(id, nume, autor, durata);
                }
            }
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
        return null;
    }
}
