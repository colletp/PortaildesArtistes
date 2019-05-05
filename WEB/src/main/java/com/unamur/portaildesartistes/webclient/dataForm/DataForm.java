package com.unamur.portaildesartistes.webclient.dataForm;

import com.sun.org.apache.xpath.internal.operations.Bool;
import com.unamur.portaildesartistes.DTO.DTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.IllegalFormatConversionException;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class DataForm<T extends DTO> implements Serializable {
    public static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.corelayer.LoginControler.class);

    private String id;
    // ******************
    // Setter/Getter
    // ******************

    protected void setFromDTO( T objDTO ){setId( objDTO.getId().toString() );}

    public String getId(){return id;}
    public void setId( String p_id){this.id = p_id;}
    // ******************
    // A implémenter
    // ******************

    public abstract T getDTO()throws ParseException;
    /*protected <T> String toString( T obj, java.lang.Class<T> clazz )throws NotFoundException {
        return "";
    }*/

    // ******************
    // Tests de validité interne aux descendants
    // ******************
    protected Boolean isNotEmpty(String s)throws IllegalArgumentException{
        if(s==null || s.isEmpty())
            throw new IllegalArgumentException("Valeur vide");
        return true;
    }

    protected Boolean containsOnlyLetters(String toValidate)throws IllegalArgumentException{
        for(int i=0;i<toValidate.length();i++){
            int a=Character.getNumericValue(toValidate.charAt(i));
            if(a<=9&&a>=0)
                throw new IllegalArgumentException("Contient autre chose que des lettres");
        }
        return true;
    }

    protected Boolean containsOnlyNumbers(String toValidate)throws IllegalArgumentException {
        for (int i = 0; i <toValidate.length(); i++) {
            int a = Character.getNumericValue(toValidate.charAt(i));
            if (a > 9 || a < 0) {
                throw new IllegalArgumentException("Numéro de rue format incorrect");
            }
        }
        return true;
    }

    protected Boolean isEmail(String toValidate)throws IllegalArgumentException{
        //Vérification du format du mail
        if(!toValidate.isEmpty()) {
            Pattern patternMail = Pattern.compile("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}$");
            Matcher testMail = patternMail.matcher(toValidate);
            if (!testMail.matches())
                throw new IllegalArgumentException("Adresse mail format incorrect");
        }
        return true;
    }
/*    protected Boolean isTel(String s)throws IllegalArgumentException{
        if(s.isEmpty())
            throw new IllegalArgumentException("Valeur vide");
        return true;
    }*/
    protected Boolean isURL(String s)throws IllegalArgumentException{
        if(s.isEmpty())
            throw new IllegalArgumentException("Valeur vide");
        return true;
    }
    protected Boolean hasLengthMin(String s , int size)throws IllegalArgumentException{
        if( s==null || s.isEmpty() || s.length()<size)
            throw new IllegalArgumentException("Taille minimum de "+size+" pas respectée" );
        return true;
    }

    // ******************
    // Conversion String vers objet ( a caster selon U )
    // ******************
    protected <U> Object convert( String toValidate, java.lang.Class<U> clazz ){
        switch( clazz.getSimpleName() ){
            case "Date":
                Date date;
                try {
                    date = new SimpleDateFormat("dd/MM/yyyy").parse(toValidate);
                }catch(ParseException e){
                    try {
                        date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(toValidate);
                    }catch(ParseException e2) {
                        throw new IllegalArgumentException("Format de date incorrect (dd/MM/yyyy) : " + e2.getMessage());
                    }
                }
                return date;
            case "UUID":
                return UUID.fromString(toValidate);
            case "Integer":
                return Integer.valueOf(toValidate);
            case "Double":
                return Double.valueOf(toValidate);
            default:
                throw new IllegalFormatConversionException( 'A'//(clazz.getSimpleName()+" : format non géré")
                        , clazz.getClass() );
        }
        //TODO: Vérifier si possible de vérifier que n'importe quel objet parsé est de type T
        /*try {
            Class<T> classe = (Class<T>) Class.forName(NOM_CLASSE);
            T instance = classe.newInstance();
            instance = T.fromString(toValidate);
        } catch (ClassNotFoundException cnfe) {

            logger.error( "La classe " + NOM_CLASSE + " n'existe pas", cnfe);
        } catch (InstantiationException ie) {
            logger.error("La classe " + NOM_CLASSE + " n'est pas instanciable", ie);
        } catch (IllegalAccessException iae) {
            logger.error("La classe " + NOM_CLASSE + " n'est pas accessible", iae);
        }        
        return true;*/
    }
    protected Date convertDate( String toValidate ){
		Date date;
		try {
			date = new SimpleDateFormat("dd/MM/yyyy").parse(toValidate);
		}catch(ParseException e){
			try {
				date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(toValidate);
			}catch(ParseException e2) {
				throw new IllegalArgumentException("Format de date incorrect (dd/MM/yyyy) : " + e2.getMessage());
			}
		}
		return date;
    }
    protected String convertDate(Date date){return new SimpleDateFormat("dd/MM/yyyy").format(date);}
    protected String convertDateTime(Date date){return new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(date);}

    protected UUID convertUUID( String toValidate ){
		return UUID.fromString(toValidate);
    }
    protected Integer convertInt( String toValidate ){
		return Integer.valueOf(toValidate);
    }
    protected Double convertDouble( String toValidate ){
		return Double.valueOf(toValidate);
	}
	
}
