package Controller.DAObjects;

import Controller.ErrEx;
import Model.Enseignant;
import Model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 05/04/2018.
 */

public class EnseignantDAO {

    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    CmsDBConnection cmsDBConnection = new CmsDBConnection();//connect to  the data base
    ErrEx errEx = new ErrEx();

    public void insertEnseignant(Enseignant enseignant){
        utilisateurDAO.insertUser(enseignant);
        int idUser = utilisateurDAO.readUserId(enseignant);
        String sql = "INSERT INTO `enseignant` (`id_utilisateur`, `id_enseignant`) VALUES (?, ?)";
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            preparedStatement.setInt(1,idUser);
            preparedStatement.setInt(2, enseignant.getIdEnseignant());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //Read Enseiganant
    public ArrayList<Enseignant> readEnseignant(){
        ArrayList<Enseignant> usersE = new ArrayList<>();
        String sqlQuerry = "SELECT * FROM enseignant, utilisateurs WHERE enseignant.id = utilisateurs.id";
        Enseignant enseignant = new Enseignant();
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerry);
                ResultSet resultSet = preparedStatement.executeQuery()
                ){
            while (resultSet.next()){
                enseignant.setNom(resultSet.getString(4));
                enseignant.setPrenom(resultSet.getString(5));
                enseignant.setUsername(resultSet.getString(6));
                enseignant.setEmail(resultSet.getString(7));
                enseignant.setPassword(resultSet.getString(8));
                usersE.add(enseignant);
            }
            return usersE;
        }catch(SQLException e){
            errEx.errMang(e);
        }
        return null;
    }


    public Enseignant readEnseignant(Enseignant enseignant){
        String sqlQuerry = "SELECT * " +
                "FROM enseignant " +
                "WHERE enseignant.id_utilisateur = ? ";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerry);
        ){
            Enseignant e = new Enseignant();
            Utilisateur utilisateur =  new Utilisateur();
            utilisateur = utilisateurDAO.readUser(enseignant);
            e.setId(utilisateur.getId());
            e.setNom(utilisateur.getNom());
            e.setPrenom(utilisateur.getPrenom());
            e.setUsername(utilisateur.getUsername());
            e.setEmail(utilisateur.getEmail());
            e.setPassword(utilisateur.getPassword());
            preparedStatement.setInt(1, e.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                e.setIdEnseignant(resultSet.getInt(2));
            }
            resultSet.close();
            return e;
        }catch(SQLException e){
            errEx.errMang(e);
        }
        return null;
    }



    public Enseignant readEnseignantByIdEnseignant(Enseignant enseignant){
        String sqlQuerry = "SELECT * " +
                "FROM enseignant " +
                "WHERE enseignant.id_enseignant = ? ";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerry);
        ){
            Enseignant e = new Enseignant();
            preparedStatement.setInt(1, enseignant.getIdEnseignant());
            ResultSet resultSet = preparedStatement.executeQuery();
            e.setIdEnseignant(-1);
            while (resultSet.next()){
                e.setId(resultSet.getInt(1));
                e.setIdEnseignant(resultSet.getInt(2));
                System.out.println(resultSet.getInt(2));
            }
            resultSet.close();
            return e;
        }catch(SQLException e){
            errEx.errMang(e);
        }
        return null;
    }


    public Enseignant readEnseignantById(Utilisateur enseignant){
        String sqlQuerry = "SELECT * " +
                "FROM enseignant " +
                "WHERE enseignant.id_utilisateur = ? ";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerry);
        ){
            Enseignant e = new Enseignant();
            Utilisateur utilisateur =  new Utilisateur();
            utilisateur = utilisateurDAO.readUserById(enseignant.getId());
            e.setId(utilisateur.getId());
            e.setNom(utilisateur.getNom());
            e.setPrenom(utilisateur.getPrenom());
            e.setUsername(utilisateur.getUsername());
            e.setEmail(utilisateur.getEmail());
            e.setPassword(utilisateur.getPassword());
            preparedStatement.setInt(1, enseignant.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            e.setIdEnseignant(-1);
            while (resultSet.next()){
                e.setIdEnseignant(resultSet.getInt(2));
                System.out.println(resultSet.getInt(2));
            }
            resultSet.close();
            return e;
        }catch(SQLException e){
            errEx.errMang(e);
        }
        return null;
    }

    //Update Enseignant fix the querry
    public void updateEnseignant(Enseignant enseignant){
        String sqlQuerry ="UPDATE `enseignant` SET `grade` = 'Dr' WHERE `enseignant`.`id` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sqlQuerry);
        ){
            utilisateurDAO.updateUser(enseignant);
            preparedStatement.setInt(1, utilisateurDAO.readUserId(enseignant));
            preparedStatement.executeUpdate();
        }catch(SQLException e){
            errEx.errMang(e);
        }
    }
}
