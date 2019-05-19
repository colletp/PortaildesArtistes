package com.unamur.portaildesartistes.DTO;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class TraitementDTO extends DTO {

    // ******************
    // Champs/propriétés
    // ******************

    private Date dateTrt;
    private String appreciation;
    private UUID roleId;
    private UUID gestId;
    private UUID formId;
    private UUID citoyenPrestId;
    private String typeRole;

    private RoleDTO role;
    private GestionnaireDTO gest;
    private FormulaireDTO form;
    private CitoyenDTO citoyenPrest;

    private List<ReponseDTO> lReponse;

    // ******************
    // Constructeur
    // ******************

    // ******************
    // Setter/Getter
    // ******************

    public Date getDateTrt() { return dateTrt; }
    public void setDateTrt(Date p_date) { this.dateTrt = p_date; }
    public String getAppreciation() { return appreciation; }
    public void setAppreciation( String p_ap) { this.appreciation = p_ap; }
    public UUID getRoleId() { return roleId; }
    public void setRoleId( UUID p_id) { this.roleId = p_id; }
    public UUID getGestId() { return gestId; }
    public void setGestId( UUID p_id) { this.gestId = p_id; }
    public UUID getFormId() { return formId; }
    public void setFormId( UUID p_id) { this.formId = p_id; }
    public UUID getCitoyenPrestId() { return citoyenPrestId; }
    public void setCitoyenPrestId( UUID p_id) { this.citoyenPrestId = p_id; }
    public String getTypeRole() { return typeRole; }
    public void setTypeRole( String p_tr) { this.typeRole = p_tr; }

    public RoleDTO getRole() { return role; }
    public void setRole( RoleDTO p_role) { this.role = p_role; }
    public GestionnaireDTO getGest() { return gest; }
    public void setGest( GestionnaireDTO p_gest) { this.gest = p_gest; }
    public FormulaireDTO getForm() { return form; }
    public void setForm( FormulaireDTO p_form) { this.form = p_form; }
    public CitoyenDTO getCitoyenPrest() { return citoyenPrest; }
    public void setCitoyenPrest( CitoyenDTO p_ut) { this.citoyenPrest = p_ut; }

    public void setReponses(List<ReponseDTO> lRepDTO){ lReponse = lRepDTO; }
    public List<ReponseDTO> getReponses(){ return lReponse; }

    // ******************
    // Fonctions
    // ******************

}