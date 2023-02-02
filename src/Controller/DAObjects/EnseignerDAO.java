package Controller.DAObjects;

import Model.Enseignant;
import Model.Enseigner;
import Model.Groupe;
import Model.Module;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 27/05/2018.
 */
public class EnseignerDAO {
    private CmsDBConnection cmsDBConnection = new CmsDBConnection();
    private GroupeDAO groupeDAO = new GroupeDAO();
    private EnseignantDAO enseignantDAO = new EnseignantDAO();
    private ModuleDAO moduleDAO = new ModuleDAO();


    public void insertEnseigner (Enseigner enseigner){
        String sql = "INSERT INTO `lancmsdb`.`enseigner` (`id_groupe`, `id_module`, `id_enseignant`) VALUES (?, ?, ?)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, enseigner.getGroupe().getIdGroupe());
            preparedStatement.setInt(2, enseigner.getModule().getId());
            preparedStatement.setInt(3, enseigner.getEnseignant().getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
                    e.printStackTrace();
        }
    }



    public ArrayList<Enseigner> allEnseigners (){
        String sql = "SELECT id, id_groupe, id_module, id_enseignant FROM `lancmsdb`.`enseigner`";
        ArrayList<Enseigner> allEnseignersGroupes = new ArrayList<Enseigner>();
        Enseigner enseigner;
        Groupe groupe;
        Module module;
        Enseignant enseignant;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                enseigner = new Enseigner();
                enseigner.setId(resultSet.getInt(1));
                groupe = new Groupe();
                groupe.setIdGroupe(resultSet.getInt(2));
                enseigner.setGroupe(groupeDAO.readGroupe(groupe));
                module = new Module();
                module.setId(resultSet.getInt(3));
                enseigner.setModule(moduleDAO.readModule(module));
                enseignant = new Enseignant();
                enseignant.setId(resultSet.getInt(4));
                enseigner.setEnseignant(enseignantDAO.readEnseignantById(enseignant));
                allEnseignersGroupes.add(enseigner);
            }
            return allEnseignersGroupes;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enseigner enseignerGroupe (){
        String sql = "SELECT id, id_groupe, id_module, id_enseignant FROM `lancmsdb`.`enseigner`";
        Enseigner enseigner = null;
        Groupe groupe;
        Module module;
        Enseignant enseignant;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
        ){
            while (resultSet.next()){
                enseigner.setId(resultSet.getInt(1));
                enseigner = new Enseigner();
                groupe = new Groupe();
                groupe.setIdGroupe(resultSet.getInt(2));
                enseigner.setGroupe(groupeDAO.readGroupe(groupe));
                module = new Module();
                module.setId(resultSet.getInt(3));
                enseigner.setModule(moduleDAO.readModule(module));
                enseignant = new Enseignant();
                enseignant.setIdEnseignant(resultSet.getInt(4));
                enseigner.setEnseignant(enseignantDAO.readEnseignant(enseignant));
            }
            return enseigner;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Enseigner getEnseigner(Enseigner enseigner){
        String sql = "SELECT `id` FROM `enseigner` WHERE `id_groupe` = ? AND `id_module` = ? AND `id_enseignant` = ?";

        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, enseigner.getGroupe().getIdGroupe());
            preparedStatement.setInt(2, enseigner.getModule().getId());
            preparedStatement.setInt(3, enseigner.getEnseignant().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                enseigner.setId(resultSet.getInt(1));
            }
            return enseigner;
        }catch (SQLException e){
                    e.printStackTrace();
        }
        return null;
    }


    public void updateEnseigner(Enseigner newEnseigner){
        String sql = "UPDATE `lancmsdb`.`enseigner` SET `id_groupe`=?, `id_module`=? WHERE  `id`=?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, newEnseigner.getGroupe().getIdGroupe());
            preparedStatement.setInt(2, newEnseigner.getModule().getId());
            preparedStatement.setInt(3, newEnseigner.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
                    e.printStackTrace();
        }
    }


    public void deleteEnseigner(Enseigner enseigner) {
        String sql = "DELETE FROM `lancmsdb`.`enseigner` WHERE  `id`=?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ) {
            preparedStatement.setInt(1, enseigner.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public int getIdEnseigner(Enseigner enseigner){
        String sql = "select id from enseigner where id_groupe = ? and id_module = ?";
        int id = -1;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
        ) {
            preparedStatement.setInt(1, enseigner.getGroupe().getIdGroupe());
            preparedStatement.setInt(2, enseigner.getModule().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
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
