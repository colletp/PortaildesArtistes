package com.unamur.portaildesartistes.wsartiste.gestionutilisateur;

import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeAdresseImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeRoleImpl;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeCitoyenImpl;
import com.unamur.portaildesartistes.DTO.CitoyenDTO;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UtilistateurServiceImpl implements UtilistateurService {

    @Autowired
    private DonneeCitoyenImpl usrImpl;
    @Autowired
    private DonneeAdresseImpl adrImpl;
    @Autowired
    private DonneeRoleImpl roleImpl;

    private CitoyenDTO usrDTO;

    UtilistateurServiceImpl(){
        usrDTO = new CitoyenDTO();
    }

    @Transactional
    public List<CitoyenDTO> list(){
        List<CitoyenDTO> usrDTOList = usrImpl.list();
        for( CitoyenDTO usr : usrDTOList ){
            usr.setResideAdr( adrImpl.getById( usr.getReside() ) );
            usr.setRoles( roleImpl.getByCitoyenId( usr.getId() ) );
        }
        return usrDTOList;
    }

    @Transactional
    public UUID insertOK(){
        usrDTO.setUserName("log"+new Date().getTime());
        return usrImpl.insert(usrDTO);
    }

    @Transactional
    public void insertAndFail(){
        //authUser.setNomUtilisateur("login"+new Date().getTime());
        //donneeUtilisateur.insert(authUser);
        throw new RuntimeException("Hello this is an error message");
    }

}