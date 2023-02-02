package Controller.DAObjects;

import Model.TypeFichier;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 29/05/2018.
 */
public class TypeFichierDAO {
    private CmsDBConnection cmsDBConnection = new CmsDBConnection();

    public ArrayList<TypeFichier> readTypeFichiers (){
        String sql = "SELECT * FROM `lancmsdb`.`type_fichier` WHERE `type` != \"projet\"";
        ArrayList<TypeFichier> allTypeFichiers = new ArrayList<TypeFichier>();
        TypeFichier typeFichier;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
                ResultSet resultSet = preparedStatement.executeQuery();
                ){
            while (resultSet.next()){
                typeFichier = new TypeFichier();
                typeFichier.setId(resultSet.getInt(1));
                typeFichier.setType(resultSet.getString(2));
                allTypeFichiers.add(typeFichier);
            }
            return allTypeFichiers;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public TypeFichier readTypeFichier (TypeFichier typeFichier){
        String sql = "SELECT * FROM `lancmsdb`.`type_fichier` WHERE `type` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setString(1, typeFichier.getType());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                typeFichier = new TypeFichier();
                typeFichier.setId(resultSet.getInt(1));
                typeFichier.setType(resultSet.getString(2));
            }
            return typeFichier;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }

    public TypeFichier readTypeFichierById (TypeFichier typeFichier){
        String sql = "SELECT * FROM `lancmsdb`.`type_fichier` WHERE `id` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            preparedStatement.setInt(1, typeFichier.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                typeFichier = new TypeFichier();
                typeFichier.setId(resultSet.getInt(1));
                typeFichier.setType(resultSet.getString(2));
            }
            return typeFichier;
        }catch (SQLException e){
            e.printStackTrace();
        }

        return null;
    }
}
