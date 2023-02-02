package Controller.DAObjects;

import Model.Utilisateur;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class UtilisateurDAO {
    private CmsDBConnection cmsDBConnection = new CmsDBConnection();

    public void insertUser(Utilisateur utilisateur){
        String sql = "INSERT INTO `utilisateurs` (`id`, `nom`, `prenom`, `nom_utilisateur`, `email`, `password`) " +
                "VALUES (NULL, ?, ?, ?, ?, ?)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getUsername());
            preparedStatement.setString(4, utilisateur.getEmail());
            preparedStatement.setString(5, utilisateur.getPassword());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void updateUser(Utilisateur utilisateur){
        String sql = "UPDATE `utilisateurs` " +
                "SET `nom` = ?, `prenom` = ?, `nom_utilisateur` = ?, `email` = ?, `password` = ?" +
                "WHERE `utilisateurs`.`nom_utilisateur` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setString(3, utilisateur.getUsername());
            preparedStatement.setString(4, utilisateur.getEmail());
            preparedStatement.setString(5, utilisateur.getPassword());
            preparedStatement.setString(6, utilisateur.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public ArrayList<Utilisateur> readAllUsers(){
        String sql = "SELECT `id`,`nom`,`prenom`,`nom_utilisateur`,`email`,`password` FROM utilisateurs";
        ArrayList<Utilisateur> toutUstilisateur = new ArrayList<>();

        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet =  preparedStatement.executeQuery();
                ){
        while (resultSet.next()){
            Utilisateur utilisateur = new Utilisateur();
            utilisateur.setNom(resultSet.getString(2));
            utilisateur.setPrenom(resultSet.getString(3));
            utilisateur.setUsername(resultSet.getString(4));
            utilisateur.setEmail(resultSet.getString(5));
            utilisateur.setPassword(resultSet.getString(6));
            toutUstilisateur.add(utilisateur);
            return toutUstilisateur;
        }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return  toutUstilisateur;
    }

    public Utilisateur readUser(Utilisateur utilisateur){
        String sql = "SELECT `id`,`nom`,`prenom`,`nom_utilisateur`,`email`,`password` FROM utilisateurs\n" +
                "WHERE `nom_utilisateur` = ?";
        Utilisateur user = null;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, utilisateur.getUsername());
             ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new Utilisateur();
                user.setId(resultSet.getInt(1));
                user.setNom(resultSet.getString(2));
                user.setPrenom(resultSet.getString(3));
                user.setUsername(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setPassword(resultSet.getString(6));
            }
            resultSet.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Utilisateur readUserById(int utilisateur){
        String sql = "SELECT `id`,`nom`,`prenom`,`nom_utilisateur`,`email`,`password` FROM utilisateurs\n" +
                "WHERE id = ?";
        Utilisateur user = null;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, utilisateur);
            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new Utilisateur();
                user.setId(resultSet.getInt(1));
                user.setNom(resultSet.getString(2));
                user.setPrenom(resultSet.getString(3));
                user.setUsername(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setPassword(resultSet.getString(6));
            }
            resultSet.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public Utilisateur readLastUser(){
        String sql = "SELECT `id`,`nom`,`prenom`,`nom_utilisateur`,`email`,`password` " +
                " FROM `utilisateurs`" +
                "ORDER BY utilisateurs.id DESC LIMIT 1";
        Utilisateur user = null;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                user = new Utilisateur();
                user.setNom(resultSet.getString(2));
                user.setPrenom(resultSet.getString(3));
                user.setUsername(resultSet.getString(4));
                user.setEmail(resultSet.getString(5));
                user.setPassword(resultSet.getString(6));
            }
            resultSet.close();
            return user;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }


    public int readLastUserId(){
        String sql = "SELECT `id`,`nom`,`prenom`,`nom_utilisateur`,`email`,`password` " +
                " FROM `utilisateurs`" +
                "ORDER BY utilisateurs.id DESC LIMIT 1";
        int id = -1;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            ResultSet resultSet =  preparedStatement.executeQuery();
            resultSet.next();
            id = resultSet.getInt(1);
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public int readUserId(Utilisateur utilisateur){
        String sql = "SELECT * FROM `utilisateurs` WHERE utilisateurs.nom_utilisateur = ?";
        int id = -1;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, utilisateur.getUsername());
            ResultSet resultSet =  preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
            resultSet.close();
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void deleteUser(Utilisateur utilisateur) {
        String sql = "DELETE FROM `utilisateurs` WHERE `utilisateurs`.`nom_utilisateur`  = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, utilisateur.getUsername());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void deleteUserById(Utilisateur utilisateur) {
        String sql = "DELETE FROM `utilisateurs` WHERE `utilisateurs`.`id`  = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, utilisateur.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void updateUtilisateur(int id, Utilisateur utilisateur){
        String sql = "UPDATE `utilisateurs` SET " +
                "`nom` = ?, " +
                "`prenom` = ? " +
                "WHERE `utilisateurs`.`id` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.setInt(3, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    //TODO student Side

    public void ajouterUtilisateurEtudiant(Utilisateur utilisateur){
        String sql = "INSERT INTO `utilisateurs` (`id`, `nom`, `prenom`, `nom_utilisateur`, `email`, `password`) " +
                "VALUES (NULL, ?, ?, '', '', '')";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ){
            preparedStatement.setString(1, utilisateur.getNom());
            preparedStatement.setString(2, utilisateur.getPrenom());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateUtilisateurEtudiant(int id, Utilisateur utilisateur){
        String sql = "UPDATE `utilisateurs` SET " +
                "`nom_utilisateur` = ?, " +
                "`email` = ?, " +
                "`password` = ? " +
                "WHERE `utilisateurs`.`id` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ){
            preparedStatement.setString(1, utilisateur.getUsername());
            preparedStatement.setString(2, utilisateur.getEmail());
            preparedStatement.setString(3, utilisateur.getPassword());
            preparedStatement.setInt(4, id);
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updatePassword(Utilisateur u){
        String sql = "UPDATE `lancmsdb`.`utilisateurs` SET `password`= ? WHERE  `id`=?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, u.getPassword());
            preparedStatement.setInt(2, u.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
