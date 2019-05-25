package com.unamur.portaildesartistes.wsartiste.Business;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.text.pdf.parser.clipper.Path;
import com.itextpdf.text.pdf.parser.clipper.Paths;
import com.unamur.portaildesartistes.DTO.DocArtisteDTO;
import com.unamur.portaildesartistes.wsartiste.datalayer.DonneeDocArtisteImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.FileOutputStream;
import java.util.List;
import java.util.UUID;

@Component
public class DocArtisteServiceImpl implements IService<DocArtisteDTO> {
    private static final Logger logger = LoggerFactory.getLogger(DocArtisteServiceImpl.class);

    @Autowired
    private DonneeDocArtisteImpl docArtImpl;

    @Transactional
    public List<DocArtisteDTO> getByCitoyenId(UUID citId){
        List<DocArtisteDTO> lDocArtDTO = docArtImpl.getByCitoyenId(citId);
        return lDocArtDTO;
    }

    @Transactional
    public List<DocArtisteDTO> getByReponse(UUID repId){ return docArtImpl.getByReponse(repId); }
    @Transactional
    public List<DocArtisteDTO> listByLang(String lang){ return docArtImpl.listByLang(lang); }
    @Transactional
    public List<DocArtisteDTO> list(){ return docArtImpl.list(); }
    @Transactional
    public DocArtisteDTO getById( UUID uuid ){
        return docArtImpl.getById(uuid);
    }
    @Transactional
    public void update( DocArtisteDTO act )throws Exception{ docArtImpl.update(act); }
    @Transactional
    public UUID insert( DocArtisteDTO act )throws Exception{ return docArtImpl.insert(act); }
    @Transactional
    public void delete( UUID uuid )throws Exception{ docArtImpl.delete(uuid); }

    /*
    public void createPdfTxt()throws Exception{
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextHelloWorld.pdf"));

        document.open();
        Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);
        Chunk chunk = new Chunk("Hello World", font);

        document.add(chunk);
        document.close();
    }
    public void createPdfImg()throws Exception {
        Path path = Paths.get(ClassLoader.getSystemResource("Java_logo.png").toURI());

        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("iTextImageExample.pdf"));
        document.open();
        Image img = Image.getInstance(path.toAbsolutePath().toString());
        document.add(img);

        document.close();
    }*/
}
