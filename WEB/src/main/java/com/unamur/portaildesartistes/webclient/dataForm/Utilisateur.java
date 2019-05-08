package com.unamur.portaildesartistes.webclient.dataForm;

import com.unamur.portaildesartistes.DTO.UtilisateurDTO;

import java.text.ParseException;

public class Utilisateur extends DataForm<UtilisateurDTO> {
    private String login;
    private String password;

    // ******************
    // Setter/Getter
    // ******************
    public String getUsername() {
        return login;
    }
    public void setUsername(String p_login) { login = p_login; }
    public String getPassword() {
        return password;
    }
    public void setPassword(String p_password) { password = p_password; }

    public void setFromDTO(final UtilisateurDTO objDTO){
        setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );
        setUsername(objDTO.getUsername());
    }
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
        if( getId()!=null && !getId().isEmpty() )
            usr.setId( convertUUID(getId()) );

        hasLengthMin( getUsername(),4 );
        usr.setUsername( getUsername() );

        hasLengthMin( getPassword(),4 );
        isComplexPassword(getPassword());
        usr.setPassword( getPassword() );
        return usr;
    }

}
