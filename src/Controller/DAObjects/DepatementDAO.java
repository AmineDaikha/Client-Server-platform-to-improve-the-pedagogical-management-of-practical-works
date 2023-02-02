package Controller.DAObjects;

import Model.Departement;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class DepatementDAO{
    CmsDBConnection cmsDBConnection = new CmsDBConnection();
    public ArrayList<Departement> readDepartement(){
        ArrayList<Departement> allDep = new ArrayList<>();
        String sql = "SELECT `id`, `nom`, `description` FROM `departement`";
        Departement departement = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                departement = new Departement();
                departement.setNom(resultSet.getString(2));
                departement.setDescription(resultSet.getString(3));
                allDep.add(departement);
            }
            return allDep;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Departement readDepartement(Departement dep){
        String sql = "SELECT `id`, `nom`, `description` FROM `departement` WHERE `nom` = ?";
        Departement departement = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setString(1, dep.getNom());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                departement = new Departement();
                departement.setNom(resultSet.getString(2));
                departement.setDescription(resultSet.getString(3));
            }
            resultSet.close();
            return departement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Departement readDepartementByID(int id){
        String sql = "SELECT `id`, `nom`, `description` FROM `departement` WHERE `id` = ?";
        Departement departement = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                departement = new Departement();
                departement.setNom(resultSet.getString(2));
                departement.setDescription(resultSet.getString(3));
            }
            resultSet.close();
            return departement;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }



    public int getIdDepartement(Departement dep){
        String sql = "SELECT `id` FROM `departement` WHERE `nom` = ?";
        int idDep = -1;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, dep.getNom());
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
