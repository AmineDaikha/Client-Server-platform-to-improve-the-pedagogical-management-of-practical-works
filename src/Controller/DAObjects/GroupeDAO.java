package Controller.DAObjects;

import Controller.ErrEx;
import Model.Groupe;
import Model.Module;
import Model.Spécialité;
import Model.Values;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
//import java.util.Iterator;

/**
 * Created by MedTaki on 29/03/2018.
 */

//TODO implement the groupe DAO methodes
public class GroupeDAO{
    UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    ModuleDAO moduleDAO = new ModuleDAO();
    SpécialitéDAO spécialitéDAO = new SpécialitéDAO();
    EnseignantDAO enseignantDAO = new EnseignantDAO();

    private ErrEx errEx = new ErrEx();
    CmsDBConnection cmsDBConnection = new CmsDBConnection();

    public void insertGroupe(Groupe groupe){
        String sql = "INSERT INTO `groupe` (`id`, `num_groupe`) " +
                "VALUES (NULL, ?);" ;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, groupe.getNumGroupe());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public void updateGroupe(Groupe groupe){
        String sql = "UPDATE `groupe` " +
                "SET `num_groupe` = ? WHERE `groupe`.`id` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, groupe.getNumGroupe());
            preparedStatement.setInt(2, groupe.getIdGroupe());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public ArrayList<Groupe> readAllGroupes(){
        String sql = "SELECT `id`, `num_groupe` FROM `groupe`";
        ArrayList<Groupe> allGroupes = new ArrayList<>();
        Groupe groupe;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                groupe = new Groupe();
                groupe.setIdGroupe(resultSet.getInt(1));
                groupe.setNumGroupe(resultSet.getInt(2));
                allGroupes.add(groupe);
            }
            return allGroupes;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public Groupe readGroupe(Groupe groupe){
        String sql = "SELECT `id`, `num_groupe` FROM `groupe` WHERE groupe.id = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, groupe.getIdGroupe());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                groupe.setIdGroupe(resultSet.getInt(1));
                groupe.setNumGroupe(resultSet.getInt(2));
            }
            return groupe;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getIdGroupe(Groupe groupe, Module module){
        String sql = "SELECT groupe.id FROM groupe, enseigner, module, spécialité WHERE\n" +
                "groupe.id = enseigner.id_groupe \n" +
                "AND module.id = enseigner.id_module\n" +
                "AND spécialité.id = module.id_spécialité\n" +
                "AND groupe.num_groupe = ?\n" +
                "AND `spécialité`.id = ?";
        int id = -1;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ) {
            preparedStatement.setInt(1, groupe.getNumGroupe());
            preparedStatement.setInt(2, module.getSpécialité().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
            return id;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public void deleteGroupe(Groupe groupe){
        String sql = "DELETE FROM groupe WHERE groupe.id = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement =connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, groupe.getIdGroupe());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            errEx.errMang(e);
        }
    }

    public void refreshGroupes(){
        String sql = "delete from groupe where id not in (select id_groupe from enseigner)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement =connection.prepareStatement(sql)
        ) {
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            errEx.errMang(e);
        }
    }

    public int getLastGroupe() {
        String sql = "select id from groupe order by id desc LIMIT 1";
        int id = -1;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ) {
            while (resultSet.next()){
                id = resultSet.getInt(1);
            }
            return id;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return id;
    }
}
