package Controller.DAObjects;

import Model.Etudiant;
import Model.Groupe;
import Model.Utilisateur;
import Model.Values;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Created by MedTaki on 07/05/2018.
 */
public class EtudiantDAO {
    private  CmsDBConnection cmsDBConnection = new CmsDBConnection();
    private UtilisateurDAO utilisateurDAO = new UtilisateurDAO();
    private SpécialitéDAO spécialitéDAO = new SpécialitéDAO();
    private GroupeDAO groupeDAO = new GroupeDAO();

    //insert etudiant
    public void insertEtudiant(Etudiant etudiant){
        String sql ="INSERT INTO `etudiant` (`num_carte`, `id_utilisateur`, `id_groupe`) " +
                "VALUES (?, ?, ?)";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            utilisateurDAO.ajouterUtilisateurEtudiant(etudiant);
            preparedStatement.setInt(1, etudiant.getNumCarte());
            preparedStatement.setInt(2, utilisateurDAO.readLastUserId());
            preparedStatement.setInt(3, Values.getEnseigner().getGroupe().getIdGroupe());
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    //read etudiant
    public ArrayList<Etudiant> readetudiantGroupe (){
        String sql = "SELECT num_carte, id_utilisateur, id_groupe, espace_de_travail FROM `lancmsdb`.`etudiant` WHERE id_groupe = ? ORDER BY `id_utilisateur` ASC";
        ArrayList<Etudiant> allEtds = new ArrayList<Etudiant>();
        Etudiant etudiant;
        Utilisateur utilisateur;
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql)
                ){
            preparedStatement.setInt(1, Values.getEnseigner().getGroupe().getIdGroupe());
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                etudiant = new Etudiant();
                etudiant.setNumCarte(resultSet.getInt(1));
                etudiant.setId(resultSet.getInt(2));
                etudiant.setGroupe(Values.getEnseigner().getGroupe());
                utilisateur = utilisateurDAO.readUserById(etudiant.getId());
                etudiant.setNom(utilisateur.getNom());
                etudiant.setPrenom(utilisateur.getPrenom());
                etudiant.setEmail(utilisateur.getEmail());
                etudiant.setUsername(utilisateur.getUsername());
                etudiant.setPassword(utilisateur.getPassword());
                etudiant.setEspaceDeTravail(resultSet.getString(4));
                allEtds.add(etudiant);
            }
            return allEtds;
        }catch (SQLException e){
            e.printStackTrace();
        }
        return allEtds;
    }

    public void updateEtudiant(Etudiant etudiant){
        String sql = "UPDATE `etudiant` SET " +
                "`num_carte` = ? " +
                "WHERE `etudiant`.`id_utilisateur` = ?";
        try(
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ){
            utilisateurDAO.updateUtilisateur(etudiant.getId(), etudiant);
            preparedStatement.setInt(1, etudiant.getNumCarte());
            preparedStatement.setInt(2, etudiant.getId());
            preparedStatement.executeUpdate();
        }catch (SQLException e){
            e.printStackTrace();
        }
    }

    public Etudiant readEtudiantByNumCarte(Etudiant etudiant) {
        String sql = "SELECT * FROM `lancmsdb`.`etudiant` WHERE etudiant.num_carte = ?";
        Etudiant e = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, etudiant.getNumCarte());
            ResultSet resultSet = preparedStatement.executeQuery();
            e = null;
            while (resultSet.next()) {
                e = new Etudiant();
                e.setNumCarte(resultSet.getInt(1));
                Groupe groupe = new Groupe();
                groupe.setIdGroupe(resultSet.getInt(3));
                e.setGroupe(groupeDAO.readGroupe(groupe));
                Utilisateur utilisateur = new Utilisateur();
                utilisateur = utilisateurDAO.readUserById(resultSet.getInt(2));
                e.setId(utilisateur.getId());
                e.setNom(utilisateur.getNom());
                e.setPrenom(utilisateur.getPrenom());
                e.setUsername(utilisateur.getUsername());
                e.setEmail(utilisateur.getEmail());
                e.setPassword(utilisateur.getPassword());
            }
            return e;
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return e;
    }


    public Etudiant readEtudiant(Utilisateur etudiant) {
        String sql = "SELECT * FROM `lancmsdb`.`etudiant` WHERE etudiant.id_utilisateur = ?";
        Etudiant e = null;
        try (
                Connection connection = cmsDBConnection.cmsDBConnecting();
                PreparedStatement preparedStatement = connection.prepareStatement(sql);
        ) {
            preparedStatement.setInt(1, etudiant.getId());
            ResultSet resultSet = preparedStatement.executeQuery();
            e = null;
            while (resultSet.next()) {
                e = new Etudiant();
                e.setNumCarte(resultSet.getInt(1));
                Groupe groupe = new Groupe();
                groupe.setIdGroupe(resultSet.getInt(3));
                e.setGroupe(groupeDAO.readGroupe(groupe));
                System.out.println(e.getGroupe());
                Utilisateur utilisateur = new Utilisateur();
                utilisateur = utilisateurDAO.readUserById(resultSet.getInt(2));
                e.setId(utilisateur.getId());
                e.setNom(utilisateur.getNom());
                e.setPrenom(utilisateur.getPrenom());
                e.setUsername(utilisateur.getUsername());
                e.setEmail(utilisateur.getEmail());
                e.setPassword(utilisateur.getPassword());
            }
            return e;
        } catch (SQLException e2) {
            e2.printStackTrace();
        }
        return e;
    }
}
