package repository;

import domain.Film;
import domain.Loc;
import domain.Program;
import repository.jdbc.JdbcUtils;

import java.sql.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;

public class ProgramRepository {
    private JdbcUtils dbUtils;

    public ProgramRepository(Properties props) {
        this.dbUtils = new JdbcUtils(props);
    }

    private String fromList(List<Loc> list){
        String locuriString = "";
        for(Loc l : list)
            locuriString += l.getRand() + "." + l.getColoana() + "." + l.getProprietarID() + ";";
        return locuriString;
    }

    private List<Loc> fromString(String string){
        List<Loc> locuri = new ArrayList<>();
        for(String e : string.split(";")){
            String[] indexes = e.split("\\.");
            if(indexes.length == 3)
                locuri.add(new Loc(Integer.parseInt(indexes[0]), Integer.parseInt(indexes[1]), Long.parseLong(indexes[2])));
        }
        return locuri;
    }

    public void save(Program program) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("insert into Programe (idFilm, data, startTime, locuriOcupate) values (?, ?, ?, ?)")) {
            prepStmt.setLong(1, program.getIdFilm());
            prepStmt.setTimestamp(2, Timestamp.valueOf(program.getDate()));
            prepStmt.setString(3, program.getStartTime());
            prepStmt.setString(4, fromList(program.getLocuriOcupate()));

            int status = prepStmt.executeUpdate();
            if (status == 0)
                throw new RepoException("Programul nu a fost salvat !");
        } catch (SQLException ex) {
            System.out.println("Error DB, " + ex);
        }
    }

    public void update(Program newProgram) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("update Programe set idFilm = ?, data = ?, startTime = ?, locuriOcupate = ? where id = ?")) {
            prepStmt.setLong(1, newProgram.getIdFilm());
            prepStmt.setTimestamp(2, Timestamp.valueOf(newProgram.getDate()));
            prepStmt.setString(3, newProgram.getStartTime());
            prepStmt.setString(4, fromList(newProgram.getLocuriOcupate()));
            prepStmt.setLong(5, newProgram.getId());

            int status = prepStmt.executeUpdate();
            if (status == 0)
                throw new RepoException("Programul nu a fost modificat !");
        } catch (SQLException ex) {
            System.out.println("Error DB, " + ex);
        }
    }

    public void delete(Long id) {
        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("delete from Programe where id = ?")) {
            prepStmt.setLong(1, id);

            int status = prepStmt.executeUpdate();
            if (status == 0)
                throw new RepoException("Programul cu ID-ul + " + id + " nu exista !");
        } catch (SQLException ex) {
            System.out.println("Error DB, " + ex);
        }
    }

    public List<Program> getAll() {
        List<Program> programe = new ArrayList<>();

        Connection con = dbUtils.getConnection();

        try (PreparedStatement prepStmt = con.prepareStatement("select * from Programe;")) {
            try (ResultSet result = prepStmt.executeQuery()) {
                while (result.next()) {
                    long id = result.getLong("id");
                    long idFilm = result.getLong("idFilm");
                    LocalDateTime data = result.getTimestamp("data").toLocalDateTime();
                    String startTime = result.getString("startTime");
                    List<Loc> locuriOcupate = fromString(result.getString("locuriOcupate"));


                    Program program = new Program(id, idFilm, data, startTime, locuriOcupate);
                    programe.add(program);
                }
            }
        } catch (SQLException ex) {
            System.out.println("Error DB, " + ex);
        }
        return programe;
    }

    public Program getById(Long idFind){
        Connection con = dbUtils.getConnection();

        try(PreparedStatement prepStmt = con.prepareStatement("select * from Programe where id = ?;")){
            prepStmt.setLong(1, idFind);
            try(ResultSet result = prepStmt.executeQuery()){
                if (result.next()){
                    long id = result.getLong("id");
                    long idFilm = result.getLong("idFilm");
                    LocalDateTime data = result.getTimestamp("data").toLocalDateTime();
                    String startTime = result.getString("startTime");
                    List<Loc> locuriOcupate = fromString(result.getString("locuriOcupate"));

                    return new Program(id, idFilm, data, startTime, locuriOcupate);
                }
            }
        }
        catch(SQLException ex){
            System.out.println("Error DB, " + ex);
        }
        return null;
    }
}
