package com.unamur.portaildesartistes.webclient.controler;

import com.unamur.portaildesartistes.DTO.CitoyenDTO;
import com.unamur.portaildesartistes.webclient.dataForm.Citoyen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import java.util.UUID;

@Controller
public class CitoyenControler extends Controler< CitoyenDTO , Class< CitoyenDTO >, Citoyen> {
    private static final Logger logger = LoggerFactory.getLogger(CitoyenControler.class);

    CitoyenDTO getById(String cookieValue , UUID itemId, Model model )throws Exception{
        return super.getObj(cookieValue, itemId , new CitoyenDTO(), CitoyenDTO.class,model);
    }
}