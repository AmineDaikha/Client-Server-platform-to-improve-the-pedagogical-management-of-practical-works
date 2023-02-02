package Controller.DAObjects;

import Model.*;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class FichierDAO {
    CmsDBConnection cmsDBConnection = new CmsDBConnection();
    TypeFichierDAO typeFichierDAO = new TypeFichierDAO();
    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private MessageDAO messageDAO = new MessageDAO();
    public void insertFichierEnseignant(Message message){
        String sql = "INSERT INTO `lancmsdb`.`fichier` (`nom`, `chemin`, id_enseigner, `id_type_fichier`) " +
                "VALUES (?, ?, ?, ?)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setString(1, message.getFichier().getNom());
            preparedStatement.setString(2, message.getFichier().getChemain());
            preparedStatement.setInt(3, Values.getEnseigner().getId());
            preparedStatement.setInt(4, message.getFichier().getTypeFichier().getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Message> readAllFichier(){
        String sql =  "SELECT fichier.`id`, fichier.`nom`, message.`date_time`, fichier.`chemin`, fichier.`id_type_fichier`" +
                "FROM `lancmsdb`.`fichier`, message " +
                "WHERE message.id_fichier = fichier.id AND fichier.id_enseigner = ? AND id_type_fichier != 1";
        ArrayList<Message> allFiles = new ArrayList<>();
        Fichier fichier;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, Values.getEnseigner().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                fichier = new Fichier();
                fichier.setId(resultSet.getInt(1));
                fichier.setNom(resultSet.getString(2));
                TypeFichier typeFichier = new TypeFichier();
                fichier.setChemain(resultSet.getString(4));
                typeFichier.setId(resultSet.getInt(5));
                fichier.setTypeFichier(typeFichierDAO.readTypeFichierById(typeFichier));
                Message message = new Message();
                message.setFichier(fichier);
                allFiles.add(message);
            }
            return allFiles;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Message> readProjects(){
        String sql =  "SELECT fichier.`id`, fichier.`nom`, message.`date_time`, fichier.`chemin`, " +
                "fichier.`id_type_fichier`, fichier.id_tp, message.source " +
                "FROM `lancmsdb`.`fichier`, message " +
                "WHERE message.id_fichier = fichier.id AND fichier.id_enseigner = ? AND id_type_fichier = 1";
        ArrayList<Message> allProjects = new ArrayList<>();
        Fichier fichier;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, Values.getEnseigner().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                fichier = new Fichier();
                fichier.setId(resultSet.getInt(1));
                fichier.setNom(resultSet.getString(2));
                TypeFichier typeFichier = new TypeFichier();
                fichier.setChemain(resultSet.getString(4));
                typeFichier.setId(resultSet.getInt(5));
                fichier.setTypeFichier(typeFichierDAO.readTypeFichierById(typeFichier));
                Fichier tp = new Fichier();
                tp.setId(resultSet.getInt(6));
                System.out.println("id tp :"+tp.getId());
                fichier.setFichier(readFichier(tp));
                Message message = new Message();
                message.setCurrentTime(resultSet.getTimestamp(3));
                message.setFichier(fichier);
                message.setSource(utilisateurDAO.readUserById(resultSet.getInt(7)));
                allProjects.add(message);
            }
            return allProjects;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public void deletFichier(Fichier fichier) {
        String sql = "DELETE FROM `fichier` WHERE `fichier`.id = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, fichier.getId());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public int lastFileId(){
        String sql = "SELECT id FROM fichier ORDER BY id DESC LIMIT 1";
        int id = -1;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
            return id;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }

    public Fichier readFichier(Fichier fichier) {
        String sql ="SELECT * FROM fichier WHERE id = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, fichier.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            Fichier tp = null;
            while (resultSet.next()){
                tp = new Fichier();
                tp.setNom(resultSet.getString("nom"));
                tp.setChemain(resultSet.getString("chemin"));
                tp.setId(resultSet.getInt(1));
            }
            return tp;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
