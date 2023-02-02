package Controller;

import Controller.DAObjects.SpécialitéDAO;
import Model.Spécialité;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by MedTaki on 29/03/2018.
 */
public class test {
    public String username;
    public String msg;

    public test(String username, String msg) {
        this.username = username;
        this.msg = msg;
    }
    public static String hashpw(String pw, String algo){
        try {
            MessageDigest messageDigest = MessageDigest.getInstance("SHA-512");
            messageDigest.update(pw.getBytes());
            byte [] b = messageDigest.digest();
            StringBuffer sb = new StringBuffer("");
            for (byte b1:b){
                sb.append(Integer.toHexString(b1 & 0xff)).toString();
            }
            return sb.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
    public test() {
    }

    public static void main(String []args){
//        String password = "passwordhello";
//        System.out.println(hashpw(password, ""));//hashing the user Password using SHA algo
//        String str = hashpw(password, "");
//        System.out.println(str.toString().equals(hashpw(password, "")));
        SpécialitéDAO spécialitéDAO = new SpécialitéDAO();
        ArrayList<Spécialité> all = spécialitéDAO.readAllSpécialité();
        Iterator<Spécialité> index = all.iterator();
        while (index.hasNext()){
            System.out.println(index.next());
        }
    }
}
