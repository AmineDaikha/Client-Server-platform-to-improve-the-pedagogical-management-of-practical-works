package Controller.DAObjects;

import Controller.ErrEx;
import Controller.test;
import Model.Message;
import Model.Utilisateur;
import Model.Values;

import java.sql.*;
import java.util.ArrayList;

/**
 * Created by MedTaki on 09/04/2018.
 */
public class MessageDAO {
    CmsDBConnection cmsDBConnection = new CmsDBConnection();
    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();

    public void insertMessage(Message message){
        String sql = "INSERT INTO `message` (`id`, `destination`, `source`, `date_time`, `contenue`) " +
                "VALUES (NULL, ?, ?, CURRENT_TIME(), ?)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, utilisateurDAO.readUserId(message.getDestination()));
            preparedStatement.setInt(2, utilisateurDAO.readUserId(message.getSource()));
            preparedStatement.setString(3, message.getCentenu());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public ArrayList<Message> readAllMessages(Utilisateur utilisateur){
        ArrayList<Message> allMessages = new ArrayList<>();
        String sql = "SELECT  `source`, `date_time`, `contenue` " +
                "FROM `message` " +
                "WHERE message.contenue != NULL OR contenue != \"\" " +
                "AND message.source = ? OR message.destination = ?";
        Message message;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            int id = utilisateurDAO.readUserId(utilisateur);
            preparedStatement.setInt(1, utilisateur.getId());
            preparedStatement.setInt(2, utilisateur.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                message = new Message();
                Utilisateur utilisateur1 = utilisateurDAO.readUserById(resultSet.getInt(1));
                message.setSource(utilisateur1);
                message.setCurrentTime(resultSet.getTimestamp(2));
                message.setCentenu(resultSet.getString(3));
                allMessages.add(message);
            }
            return allMessages;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allMessages;
    }


    public void insertMessageFichier(Message message) {
        String sql = "INSERT INTO `lancmsdb`.`message` (`source`, `id_fichier`, `date_time`) VALUES (?, ?, CURRENT_TIME)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, message.getSource().getId());
            preparedStatement.setInt(2, message.getFichier().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }
}
