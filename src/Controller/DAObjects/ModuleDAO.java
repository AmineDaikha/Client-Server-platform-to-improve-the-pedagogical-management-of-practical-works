package Controller.DAObjects;

import Model.Module;
import Model.Spécialité;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class ModuleDAO {

    private SpécialitéDAO spécialitéDAO = new SpécialitéDAO();

    CmsDBConnection cmsDBConnection = new CmsDBConnection();

    public ArrayList<Module> readAllModules(){
        String sql = "SELECT `id`, `nom`, `description`, id_spécialité FROM `module`";
        ArrayList<Module> allModules = new ArrayList<>();
        Module module;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                module = new Module();
                module.setId(resultSet.getInt(1));
                module.setNom(resultSet.getString(2));
                module.setDéscription(resultSet.getString(3));
                Spécialité spécialité = new Spécialité();
                spécialité.setId(resultSet.getInt(4));
                spécialité = spécialitéDAO.readScpécialitéById(spécialité.getId());
                module.setDéscription(resultSet.getString(3));
                module.setSpécialité(spécialité);
                allModules.add(module);
            }
            return allModules;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public ArrayList<Module> readAllModules(Module mdl){
        String sql = "SELECT `id`, `nom`, `description`, id_spécialité FROM `module` WHERE id_spécialité = ?";
        ArrayList<Module> allModules = new ArrayList<>();
        Module module;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);

        ){
            preparedStatement.setInt(1, mdl.getSpécialité().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                module = new Module();
                module.setId(resultSet.getInt(1));
                module.setNom(resultSet.getString(2));
                module.setDéscription(resultSet.getString(3));
                Spécialité spécialité = new Spécialité();
                spécialité.setId(resultSet.getInt(4));
                spécialité = spécialitéDAO.readScpécialitéById(spécialité.getId());
                module.setDéscription(resultSet.getString(3));
                module.setSpécialité(spécialité);
                allModules.add(module);
            }
            return allModules;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public Module readModule(Module module){
        String sql = "SELECT `id`, `nom`, `description`, id_spécialité FROM `module` WHERE module.id = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)

        ){
            preparedStatement.setInt(1, module.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                module = new Module();
                module.setId(resultSet.getInt(1));
                module.setNom(resultSet.getString(2));
                Spécialité spécialité = new Spécialité();
                spécialité.setId(resultSet.getInt(4));
                spécialité = spécialitéDAO.readScpécialitéById(spécialité.getId());
                module.setDéscription(resultSet.getString(3));
                module.setSpécialité(spécialité);
            }
            return module;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Module readModuleByNomSpécialité(Module module){
        String sql = "SELECT `id`, `nom`, `description`, id_spécialité FROM `module` WHERE module.nom = ? AND id_spécialité = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)

        ){
            preparedStatement.setString(1, module.getNom());
            preparedStatement.setInt(2, module.getSpécialité().getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                module.setId(resultSet.getInt(1));
                module.setNom(resultSet.getString(2));
                module.setDéscription(resultSet.getString(3));
            }
            return module;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }
}
