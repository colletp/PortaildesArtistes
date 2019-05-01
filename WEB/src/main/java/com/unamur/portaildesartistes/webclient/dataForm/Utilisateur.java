package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.text.ParseException;
import java.util.UUID;

public class Utilisateur extends DataForm {
    private String id;
    private String login;
    private String password;

    // ******************
    // Setter/Getter
    // ******************
    public UUID getId() { return (UUID)convert(id,UUID.class); }
    public void setId(String p_id) { this.id = p_id; }
    public String getUsername() {
        hasLengthMin( login,4 );
        return login;
    }
    public void setUsername(String p_login) { this.login = p_login; }
    public String getPassword() {
        hasLengthMin( login,4 );
        isComplexPassword(password);
        return password;
    }
    public void setPassword(String p_password) { this.password = p_password; }

    // ******************
    // Fonctions
    // ******************
    Boolean isComplexPassword(String toValidate){
        //Verifie que le mots de passe n'est pas composé de chiffre ou de lettre qui se suivent ou qui sont identiques
        int intArray[]=new int[toValidate.length()];
        for (int i=0;i<toValidate.length();i++)
            intArray[i]=Character.getNumericValue(toValidate.charAt(i));
        int score=1;
        for(int i=0;i<intArray.length-1;i++){
            if(intArray[i]==intArray[i+1]){
                score++;
            }else if (intArray[i]==intArray[i+1]+1||intArray[i]==intArray[i+1]-1){
                score++;
            }
        }
        if(score==intArray.length)
            throw new IllegalArgumentException("Mots de passe trop faible");
        if(toValidate.length()>12)
            throw new IllegalArgumentException("Mots de passe trop long");
        return true;

    }
    public UtilisateurDTO getDTO()throws ParseException {
        UtilisateurDTO usr = new UtilisateurDTO();
        usr.setId( getId() );
        usr.setUsername( getUsername() );
        usr.setPassword(getPassword());
        return usr;
    }

}
