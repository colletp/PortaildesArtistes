package com.unamur.portaildesartistes.wsartiste.datalayer;

import com.unamur.portaildesartistes.DTO.AdresseDTO;
import org.skife.jdbi.v2.StatementContext;
import org.skife.jdbi.v2.sqlobject.*;
import org.skife.jdbi.v2.sqlobject.customizers.RegisterMapper;
import org.skife.jdbi.v2.tweak.ResultSetMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.UUID;

@Repository
public class DonneeAdresseImpl extends Donnee<AdresseDTO>{
    private static final Logger logger = LoggerFactory.getLogger(DonneeAdresseImpl.class);

    public List<AdresseDTO> list() { return super.Exec(AdresseSQLs.class).list(); }
    public AdresseDTO       getById(UUID p_id){
                    return super.Exec(AdresseSQLs.class).getById( p_id );
    }
    public UUID             insert(AdresseDTO item){ return UUID.fromString( super.Exec(AdresseSQLs.class).insert(item) ); }
    public void             update(AdresseDTO adr){
        super.Exec(AdresseSQLs.class).update( adr );
    }
    public void             delete(UUID p_id){
            super.Exec(AdresseSQLs.class).delete( p_id );
    }

    @RegisterMapper(AdresseMapper.class)
    private interface AdresseSQLs {
        @SqlQuery("select * from adresses")
        List<AdresseDTO> list();

        @SqlQuery("select * from adresses where adresses_id = :adresses_id")
        AdresseDTO getById(@Bind("adresses_id") UUID p_id);

        @SqlQuery( "insert into adresses (ville,rue,num,boite) values(:ville,:rue,:numero,:boite) RETURNING adresses_id" )
        String insert(@BindBean AdresseDTO adr);

        @SqlUpdate("UPDATE adresses SET ville=:ville,rue=:rue,num=:numero,boite=:boite WHERE adresses_id=:id")
        void update(@BindBean AdresseDTO adr);

        @SqlUpdate("DELETE FROM adresses WHERE adresses_id=:adresses_id ")
        void delete(@Bind("adresses_id") UUID p_id);
    }

    public static class AdresseMapper implements ResultSetMapper<AdresseDTO> {
        AdresseDTO adrDTO;
        @Override
        public AdresseDTO map(final int i, final ResultSet r, final StatementContext statementContext) throws SQLException {
            adrDTO = new AdresseDTO();
            adrDTO.setId((UUID) r.getObject("adresses_id"));
            adrDTO.setRue(r.getString("rue"));
            adrDTO.setNumero(r.getString("num"));
            adrDTO.setBoite(r.getString("boite"));
            adrDTO.setVille(r.getString("ville"));
            return adrDTO;
        }
    }

}

