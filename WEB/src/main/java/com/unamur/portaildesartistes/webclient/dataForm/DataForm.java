package com.unamur.portaildesartistes.webclient.dataForm;

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
    public static final Logger logger = LoggerFactory.getLogger(com.unamur.portaildesartistes.webclient.controler.LoginControler.class);

    private String id;

    // ******************
    // Constructor
    // ******************
    public DataForm(){}
    public DataForm(T formDTO){ this.setFromDTO(formDTO); }

    // ******************
    // Setter/Getter
    // ******************

    //protected void setFromDTO( DTO objDTO ){setId( (objDTO.getId()==null?"":objDTO.getId().toString()) );}
    public abstract void setFromDTO( T objDTO);

    public String getId(){return id;}
    public void setId( String p_id){id = p_id;}
    // ******************
    // A implémenter
    // ******************

    public abstract T getDTO();

    // ******************
    // Tests de validité interne aux descendants
    // ******************
    protected Boolean isNotEmpty(String s)throws IllegalArgumentException{
        if(s==null || s.isEmpty())
            throw new IllegalArgumentException("valeurvide");
        return true;
    }
    protected Boolean isNotEmpty(Object obj)throws IllegalArgumentException{
        if( obj==null )
            throw new IllegalArgumentException("valeurvide");
        return true;
    }

    protected Boolean containsOnlyLetters(String toValidate)throws IllegalArgumentException{
        for(int i=0;i<toValidate.length();i++){
            int a=Character.getNumericValue(toValidate.charAt(i));
            if(a<=9&&a>=0)
                throw new IllegalArgumentException("autrechosequelettre");
        }
        return true;
    }

    protected Boolean containsOnlyNumbers(String toValidate)throws IllegalArgumentException {
        for (int i = 0; i <toValidate.length(); i++) {
            int a = Character.getNumericValue(toValidate.charAt(i));
            if (a > 9 || a < 0) {
                throw new IllegalArgumentException("rueincorrecte");
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
                throw new IllegalArgumentException("mailko");
        }
        return true;
    }

    protected Boolean isURL(String s)throws IllegalArgumentException{
        if(!s.isEmpty()) {
                Pattern patternUrl = Pattern.compile("^(https?|ftp|file)://[-a-zA-Z0-9+&@#/%?=~_|!:,.;]*[-a-zA-Z0-9+&@#/%=~_|]$");
                Matcher testUrl = patternUrl.matcher(s);
                if (!testUrl.matches())
                    throw new IllegalArgumentException("urlko");
        }
        return true;
    }

    protected Boolean hasLengthMin(String s , int size)throws IllegalArgumentException{
        if( s==null || s.isEmpty() || s.length()<size) {
            logger.error("Taille minimum de " + size + " pas respectée");
            throw new IllegalArgumentException("tailleko");
        }
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
                        logger.error("Format de date incorrect (dd/MM/yyyy) : " + e2.getMessage());
                        throw new IllegalArgumentException("formatdateko");
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


    /*
    protected Date convertDate( String toValidate ){
		Date date;
		try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(toValidate);
		} catch(ParseException e){
			try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(toValidate);
			} catch(ParseException e2) {
				throw new IllegalArgumentException("Format de date incorrect (dd/MM/yyyy [HH:mm:ss]) : " + e2.getMessage());
			}
		}
		return date;
    }*/


    protected Date convertDate( String toValidate ) {
        if(toValidate==null || toValidate.isEmpty())return null;

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        dateFormat.setLenient(false);
        Date date = null;

        SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
        dateFormat2.setLenient(false);

        try {
            date = dateFormat.parse(toValidate);
        } catch(ParseException  e) {

            try {
                date = dateFormat2.parse(toValidate);
            } catch (ParseException e2) {
                logger.error("Format de date incorrect (dd/MM/yyyy [HH:mm:ss]) : " + e2.getMessage());
                throw new IllegalArgumentException("formatdateko");
            }

        }
        /*try {
            date = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").parse(toValidate);
        } catch(ParseException e){
            try {
                date = new SimpleDateFormat("dd/MM/yyyy").parse(toValidate);
            } catch(ParseException e2) {
                throw new IllegalArgumentException("Format de date incorrect (dd/MM/yyyy [HH:mm:ss]) : " + e2.getMessage());
            }
        }*/
        return date;

    }

    protected String convertDate(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        return sdf.format(date);
        //return (new SimpleDateFormat("dd/MM/yyyy")).format(date);
    }
    protected String convertDateTime(Date date){
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        return sdf.format(date);
        //return (new SimpleDateFormat("dd/MM/yyyy HH:mm:ss")).format(date);
    }

    protected UUID convertUUID( String toValidate ){
		if(toValidate==null || toValidate.isEmpty())return null;
        return UUID.fromString(toValidate);
    }
    protected Integer convertInt( String toValidate ){
		return Integer.valueOf(toValidate);
    }
    protected Double convertDouble( String toValidate ){
		return Double.valueOf(toValidate);
	}
	
}
