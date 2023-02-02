package Controller.DAObjects;

import Model.Departement;
import Model.Spécialité;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class SpécialitéDAO {
    private DepatementDAO depatementDAO = new DepatementDAO();
    CmsDBConnection cmsDBConnection = new CmsDBConnection();

    public ArrayList<Spécialité> readAllSpécialité(){
        String sql = "SELECT `id`, `abv`, `nom`, `description`, `id_departement` FROM `spécialité`";
        ArrayList<Spécialité> allSpécialité = new ArrayList<>();
        Spécialité spécialité;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                spécialité = new Spécialité();
                spécialité.setId(resultSet.getInt(1));
                spécialité.setAbv(resultSet.getString(2));
                spécialité.setNom(resultSet.getString(3));
                spécialité.setDéscription(resultSet.getString(4));
                spécialité.setDepartement(depatementDAO.readDepartementByID(resultSet.getInt(5)));
                allSpécialité.add(spécialité);
            }
            return allSpécialité;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

//     try(
//    Connection connection = cmsDBConnection.cmsDBConnecting();
//
//                ){
//
//    } catch (SQLException e) {
//        e.printStackTrace();
//    }

    public Spécialité readScpécialité(Spécialité spécialité) {
        String sql = "SELECT `id`, `abv`, `nom`, `description`, `id_departement` " +
                "FROM `spécialité`\n" +
                "WHERE spécialité.abv = ?";
        Spécialité spclt = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setString(1, spécialité.getAbv());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                spclt = new Spécialité();
                spclt.setId(resultSet.getInt(1));
                spclt.setAbv(resultSet.getString(2));
                spclt.setNom(resultSet.getString(3));
                spclt.setDéscription(resultSet.getString(4));
                spclt.setDepartement(depatementDAO.readDepartementByID(resultSet.getInt(5)));
            }
            return spclt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Spécialité readScpécialitéById(int id) {
        String sql = "SELECT id, `abv`, `nom`, `description`, `id_departement` " +
                "FROM `spécialité` " +
                "WHERE spécialité.id = ?";
        Spécialité spclt = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                spclt = new Spécialité();
                spclt.setId(resultSet.getInt(1));
                spclt.setAbv(resultSet.getString(2));
                spclt.setNom(resultSet.getString(3));
                spclt.setDéscription(resultSet.getString(4));
                spclt.setDepartement(depatementDAO.readDepartementByID(resultSet.getInt(5)));
            }
            return spclt;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdSpécialité(Spécialité spécialité){
        String sql = "SELECT `id` FROM `spécialité` WHERE `nom` = ?";
        int idDep = -1;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, spécialité.getNom());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                idDep = resultSet.getInt(1);
            }
            resultSet.close();
            return idDep;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Integer.parseInt(null);
    }

}
