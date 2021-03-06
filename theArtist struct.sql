PGDMP     /    6                w        	   TheArtist    11.2    11.2 T    �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            �           1262    41590 	   TheArtist    DATABASE     �   CREATE DATABASE "TheArtist" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_Belgium.1252' LC_CTYPE = 'French_Belgium.1252';
    DROP DATABASE "TheArtist";
          
   usrArtiste    false                        3079    42049    pgcrypto 	   EXTENSION     <   CREATE EXTENSION IF NOT EXISTS pgcrypto WITH SCHEMA public;
    DROP EXTENSION pgcrypto;
                  false            �           0    0    EXTENSION pgcrypto    COMMENT     <   COMMENT ON EXTENSION pgcrypto IS 'cryptographic functions';
                       false    3                        3079    42086 	   uuid-ossp 	   EXTENSION     ?   CREATE EXTENSION IF NOT EXISTS "uuid-ossp" WITH SCHEMA public;
    DROP EXTENSION "uuid-ossp";
                  false            �           0    0    EXTENSION "uuid-ossp"    COMMENT     W   COMMENT ON EXTENSION "uuid-ossp" IS 'generate universally unique identifiers (UUIDs)';
                       false    2            �           1247    42098 
   etat_prest    TYPE     �   CREATE TYPE public.etat_prest AS ENUM (
    'Nouvelle',
    'Validée par le commenditaire',
    'Validé par le gestionaire',
    'Exécutée',
    'Annulée par l artiste',
    'Annulé par le commenditaire',
    'Annulé par le gestionnaire'
);
    DROP TYPE public.etat_prest;
       public    
   usrArtiste    false            �           1247    42114    langue    TYPE     :   CREATE TYPE public.langue AS ENUM (
    'FR',
    'EN'
);
    DROP TYPE public.langue;
       public    
   usrArtiste    false            �           1247    42120    statut_legal    TYPE     u   CREATE TYPE public.statut_legal AS ENUM (
    'Association de fait',
    'SA',
    'SPRL',
    'ASBL',
    'SARL'
);
    DROP TYPE public.statut_legal;
       public    
   usrArtiste    false            �           1247    42132    type_doc_artiste    TYPE     Y   CREATE TYPE public.type_doc_artiste AS ENUM (
    'Carte artiste',
    'Visa artiste'
);
 #   DROP TYPE public.type_doc_artiste;
       public    
   usrArtiste    false            �           1247    42138 
   type_roles    TYPE     �   CREATE TYPE public.type_roles AS ENUM (
    'Gestionnaire de formulaire',
    'Gestionnaire de prestation',
    'Gestionnaire du personnel'
);
    DROP TYPE public.type_roles;
       public    
   usrArtiste    false                       1255    42145 %   doc_artiste_activite_check_activite()    FUNCTION       CREATE FUNCTION public.doc_artiste_activite_check_activite() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
		IF NOT EXISTS( SELECT da.citoyen_id
							FROM doc_artiste da
			  				JOIN formulaires f ON f.citoyen_id = da.citoyen_id
							JOIN form_activite fa ON fa.form_id=f.form_id 
							WHERE da.doc_artiste_id=NEW.doc_artiste_id
							  AND fa.activite_id=NEW.activite_id
			) THEN
            RAISE EXCEPTION 'Doc_Activite as an activite not in a form';
		END IF;
        RETURN NEW;
    END;
$$;
 <   DROP FUNCTION public.doc_artiste_activite_check_activite();
       public       postgres    false                       1255    42146    prestations_activite_check()    FUNCTION     m  CREATE FUNCTION public.prestations_activite_check() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
    BEGIN
		IF NOT EXISTS(  SELECT * FROM doc_artiste_activite daa WHERE daa.activite_id=NEW.activite_id AND daa.doc_artiste_id=NEW.doc_artiste_id ) THEN
            RAISE EXCEPTION 'Prest_Activite is not in a doc_artist';
		END IF;
        RETURN NEW;
    END;
$$;
 3   DROP FUNCTION public.prestations_activite_check();
       public       postgres    false            �            1259    42147    activite    TABLE     �   CREATE TABLE public.activite (
    activite_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    nom_activite character varying(255) NOT NULL,
    description text NOT NULL,
    secteur_id uuid NOT NULL
);
    DROP TABLE public.activite;
       public      
   usrArtiste    false    2            �            1259    42154    adresses    TABLE     �   CREATE TABLE public.adresses (
    adresses_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    ville character varying(64) NOT NULL,
    rue character varying(255) NOT NULL,
    num character varying(16) NOT NULL,
    boite character varying(16)
);
    DROP TABLE public.adresses;
       public      
   usrArtiste    false    2            �            1259    42158    citoyen    TABLE     �  CREATE TABLE public.citoyen (
    citoyen_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    nom character varying(64) NOT NULL,
    prenom character varying(64) NOT NULL,
    date_naissance date,
    tel character varying(64),
    gsm character varying(64),
    mail character varying(255),
    nrn character varying(12) NOT NULL,
    nation character varying(64) NOT NULL,
    login character varying(16) NOT NULL,
    password text NOT NULL,
    reside uuid NOT NULL
);
    DROP TABLE public.citoyen;
       public      
   usrArtiste    false    2            �            1259    42165    commanditaire    TABLE     2  CREATE TABLE public.commanditaire (
    com_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    entreprise_id uuid,
    citoyen_id uuid,
    CONSTRAINT commanditaire_check_xor CHECK ((((entreprise_id IS NOT NULL) AND (citoyen_id IS NULL)) OR ((entreprise_id IS NULL) AND (citoyen_id IS NOT NULL))))
);
 !   DROP TABLE public.commanditaire;
       public      
   usrArtiste    false    2            �            1259    42170    doc_artiste    TABLE       CREATE TABLE public.doc_artiste (
    doc_artiste_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    reponse_id uuid NOT NULL,
    no_doc character varying(12) NOT NULL,
    date_peremption date NOT NULL,
    type_doc_artiste public.type_doc_artiste NOT NULL
);
    DROP TABLE public.doc_artiste;
       public      
   usrArtiste    false    2    655            �            1259    42174    doc_artiste_activite    TABLE     �   CREATE TABLE public.doc_artiste_activite (
    doc_artiste_activite_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    doc_artiste_id uuid NOT NULL,
    activite_id uuid NOT NULL
);
 (   DROP TABLE public.doc_artiste_activite;
       public      
   usrArtiste    false    2            �            1259    42178 
   entreprise    TABLE     )  CREATE TABLE public.entreprise (
    entreprise_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    contact_id uuid NOT NULL,
    siege_id uuid NOT NULL,
    bce character varying(12) NOT NULL,
    denomination character varying(255) NOT NULL,
    statut_legal public.statut_legal NOT NULL
);
    DROP TABLE public.entreprise;
       public      
   usrArtiste    false    2    671            �            1259    42182    form_activite    TABLE     �   CREATE TABLE public.form_activite (
    form_activite_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    activite_id uuid NOT NULL,
    form_id uuid NOT NULL
);
 !   DROP TABLE public.form_activite;
       public      
   usrArtiste    false    2            �            1259    42186    formulaires    TABLE       CREATE TABLE public.formulaires (
    form_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    citoyen_id uuid,
    date_demande timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    cursus_ac character varying(255)[],
    ex_pro character varying(255)[],
    ressources character varying(255)[],
    langue public.langue NOT NULL,
    carte boolean DEFAULT false NOT NULL,
    visa boolean DEFAULT false NOT NULL,
    a_traiter boolean DEFAULT true NOT NULL,
    nom_artiste character varying(255)
);
    DROP TABLE public.formulaires;
       public      
   usrArtiste    false    2    668            �            1259    42197    gestionnaire    TABLE     �   CREATE TABLE public.gestionnaire (
    gest_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    citoyen_id uuid NOT NULL,
    matricule character varying(16) NOT NULL,
    bureau character varying(16) NOT NULL,
    travaille uuid NOT NULL
);
     DROP TABLE public.gestionnaire;
       public      
   usrArtiste    false    2            �            1259    42201    gestionnaire_roles    TABLE     �   CREATE TABLE public.gestionnaire_roles (
    gest_roles_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    gest_id uuid,
    roles_id uuid
);
 &   DROP TABLE public.gestionnaire_roles;
       public      
   usrArtiste    false    2            �            1259    42205    prestations    TABLE     �  CREATE TABLE public.prestations (
    prest_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    date_prest timestamp without time zone NOT NULL,
    duree integer NOT NULL,
    montant numeric(10,2) NOT NULL,
    etat public.etat_prest NOT NULL,
    commanditaire_id uuid NOT NULL,
    doc_artiste_id uuid NOT NULL,
    activite_id uuid NOT NULL,
    se_deroule_id uuid NOT NULL
);
    DROP TABLE public.prestations;
       public      
   usrArtiste    false    2    665            �            1259    42209    reponse    TABLE     �   CREATE TABLE public.reponse (
    reponse_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    trt_id uuid NOT NULL,
    date_reponse timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    reponse text,
    rep_positive boolean
);
    DROP TABLE public.reponse;
       public      
   usrArtiste    false    2            �            1259    42217    roles    TABLE     �   CREATE TABLE public.roles (
    roles_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    langue public.langue,
    type_roles public.type_roles
);
    DROP TABLE public.roles;
       public      
   usrArtiste    false    2    668    658            �            1259    42221    secteur    TABLE     �   CREATE TABLE public.secteur (
    secteur_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    nom_secteur character varying(255)
);
    DROP TABLE public.secteur;
       public      
   usrArtiste    false    2            �            1259    42225    traitements    TABLE     '  CREATE TABLE public.traitements (
    trt_id uuid DEFAULT public.uuid_generate_v4() NOT NULL,
    date_trt timestamp without time zone DEFAULT CURRENT_TIMESTAMP NOT NULL,
    appreciation character varying(255) NOT NULL,
    gest_id uuid NOT NULL,
    form_id uuid,
    citoyen_prest_id uuid
);
    DROP TABLE public.traitements;
       public      
   usrArtiste    false    2            �            1259    42230 	   trt_prest    TABLE     �   CREATE TABLE public.trt_prest (
    trt_prest_id uuid DEFAULT public.uuid_generate_v4(),
    trt_id uuid NOT NULL,
    prest_id uuid NOT NULL
);
    DROP TABLE public.trt_prest;
       public      
   usrArtiste    false    2                       2606    42235    activite activite_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.activite
    ADD CONSTRAINT activite_pkey PRIMARY KEY (activite_id);
 @   ALTER TABLE ONLY public.activite DROP CONSTRAINT activite_pkey;
       public      
   usrArtiste    false    198                       2606    42237    adresses adresses_pkey 
   CONSTRAINT     ]   ALTER TABLE ONLY public.adresses
    ADD CONSTRAINT adresses_pkey PRIMARY KEY (adresses_id);
 @   ALTER TABLE ONLY public.adresses DROP CONSTRAINT adresses_pkey;
       public      
   usrArtiste    false    199                       2606    42239    citoyen citoyen_login_key 
   CONSTRAINT     U   ALTER TABLE ONLY public.citoyen
    ADD CONSTRAINT citoyen_login_key UNIQUE (login);
 C   ALTER TABLE ONLY public.citoyen DROP CONSTRAINT citoyen_login_key;
       public      
   usrArtiste    false    200                       2606    42241    citoyen citoyen_nrn_key 
   CONSTRAINT     Q   ALTER TABLE ONLY public.citoyen
    ADD CONSTRAINT citoyen_nrn_key UNIQUE (nrn);
 A   ALTER TABLE ONLY public.citoyen DROP CONSTRAINT citoyen_nrn_key;
       public      
   usrArtiste    false    200            !           2606    42243    citoyen citoyen_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.citoyen
    ADD CONSTRAINT citoyen_pkey PRIMARY KEY (citoyen_id);
 >   ALTER TABLE ONLY public.citoyen DROP CONSTRAINT citoyen_pkey;
       public      
   usrArtiste    false    200            #           2606    42245     commanditaire commanditaire_pkey 
   CONSTRAINT     b   ALTER TABLE ONLY public.commanditaire
    ADD CONSTRAINT commanditaire_pkey PRIMARY KEY (com_id);
 J   ALTER TABLE ONLY public.commanditaire DROP CONSTRAINT commanditaire_pkey;
       public      
   usrArtiste    false    201            )           2606    42247 .   doc_artiste_activite doc_artiste_activite_pkey 
   CONSTRAINT     �   ALTER TABLE ONLY public.doc_artiste_activite
    ADD CONSTRAINT doc_artiste_activite_pkey PRIMARY KEY (doc_artiste_activite_id);
 X   ALTER TABLE ONLY public.doc_artiste_activite DROP CONSTRAINT doc_artiste_activite_pkey;
       public      
   usrArtiste    false    203            %           2606    42249 "   doc_artiste doc_artiste_no_doc_key 
   CONSTRAINT     _   ALTER TABLE ONLY public.doc_artiste
    ADD CONSTRAINT doc_artiste_no_doc_key UNIQUE (no_doc);
 L   ALTER TABLE ONLY public.doc_artiste DROP CONSTRAINT doc_artiste_no_doc_key;
       public      
   usrArtiste    false    202            '           2606    42251    doc_artiste doc_artiste_pkey 
   CONSTRAINT     f   ALTER TABLE ONLY public.doc_artiste
    ADD CONSTRAINT doc_artiste_pkey PRIMARY KEY (doc_artiste_id);
 F   ALTER TABLE ONLY public.doc_artiste DROP CONSTRAINT doc_artiste_pkey;
       public      
   usrArtiste    false    202            +           2606    42253    entreprise entreprise_bce_key 
   CONSTRAINT     W   ALTER TABLE ONLY public.entreprise
    ADD CONSTRAINT entreprise_bce_key UNIQUE (bce);
 G   ALTER TABLE ONLY public.entreprise DROP CONSTRAINT entreprise_bce_key;
       public      
   usrArtiste    false    204            -           2606    42255    entreprise entreprise_pkey 
   CONSTRAINT     c   ALTER TABLE ONLY public.entreprise
    ADD CONSTRAINT entreprise_pkey PRIMARY KEY (entreprise_id);
 D   ALTER TABLE ONLY public.entreprise DROP CONSTRAINT entreprise_pkey;
       public      
   usrArtiste    false    204            /           2606    42257 3   form_activite form_activite_activite_id_form_id_key 
   CONSTRAINT     ~   ALTER TABLE ONLY public.form_activite
    ADD CONSTRAINT form_activite_activite_id_form_id_key UNIQUE (activite_id, form_id);
 ]   ALTER TABLE ONLY public.form_activite DROP CONSTRAINT form_activite_activite_id_form_id_key;
       public      
   usrArtiste    false    205    205            1           2606    42259     form_activite form_activite_pkey 
   CONSTRAINT     l   ALTER TABLE ONLY public.form_activite
    ADD CONSTRAINT form_activite_pkey PRIMARY KEY (form_activite_id);
 J   ALTER TABLE ONLY public.form_activite DROP CONSTRAINT form_activite_pkey;
       public      
   usrArtiste    false    205            3           2606    42261    formulaires formulaires_pkey 
   CONSTRAINT     _   ALTER TABLE ONLY public.formulaires
    ADD CONSTRAINT formulaires_pkey PRIMARY KEY (form_id);
 F   ALTER TABLE ONLY public.formulaires DROP CONSTRAINT formulaires_pkey;
       public      
   usrArtiste    false    206            5           2606    42263 (   gestionnaire gestionnaire_citoyen_id_key 
   CONSTRAINT     i   ALTER TABLE ONLY public.gestionnaire
    ADD CONSTRAINT gestionnaire_citoyen_id_key UNIQUE (citoyen_id);
 R   ALTER TABLE ONLY public.gestionnaire DROP CONSTRAINT gestionnaire_citoyen_id_key;
       public      
   usrArtiste    false    207            7           2606    42265    gestionnaire gestionnaire_pkey 
   CONSTRAINT     a   ALTER TABLE ONLY public.gestionnaire
    ADD CONSTRAINT gestionnaire_pkey PRIMARY KEY (gest_id);
 H   ALTER TABLE ONLY public.gestionnaire DROP CONSTRAINT gestionnaire_pkey;
       public      
   usrArtiste    false    207            9           2606    42267 *   gestionnaire_roles gestionnaire_roles_pkey 
   CONSTRAINT     s   ALTER TABLE ONLY public.gestionnaire_roles
    ADD CONSTRAINT gestionnaire_roles_pkey PRIMARY KEY (gest_roles_id);
 T   ALTER TABLE ONLY public.gestionnaire_roles DROP CONSTRAINT gestionnaire_roles_pkey;
       public      
   usrArtiste    false    208            ;           2606    42269    prestations prestations_pkey 
   CONSTRAINT     `   ALTER TABLE ONLY public.prestations
    ADD CONSTRAINT prestations_pkey PRIMARY KEY (prest_id);
 F   ALTER TABLE ONLY public.prestations DROP CONSTRAINT prestations_pkey;
       public      
   usrArtiste    false    209            =           2606    42271    reponse reponse_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.reponse
    ADD CONSTRAINT reponse_pkey PRIMARY KEY (reponse_id);
 >   ALTER TABLE ONLY public.reponse DROP CONSTRAINT reponse_pkey;
       public      
   usrArtiste    false    210            ?           2606    42273    roles roles_pkey 
   CONSTRAINT     T   ALTER TABLE ONLY public.roles
    ADD CONSTRAINT roles_pkey PRIMARY KEY (roles_id);
 :   ALTER TABLE ONLY public.roles DROP CONSTRAINT roles_pkey;
       public      
   usrArtiste    false    211            A           2606    42275    secteur secteur_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.secteur
    ADD CONSTRAINT secteur_pkey PRIMARY KEY (secteur_id);
 >   ALTER TABLE ONLY public.secteur DROP CONSTRAINT secteur_pkey;
       public      
   usrArtiste    false    212            C           2606    42277    traitements traitements_pkey 
   CONSTRAINT     ^   ALTER TABLE ONLY public.traitements
    ADD CONSTRAINT traitements_pkey PRIMARY KEY (trt_id);
 F   ALTER TABLE ONLY public.traitements DROP CONSTRAINT traitements_pkey;
       public      
   usrArtiste    false    213            E           2606    42279    trt_prest trt_prest_pkey 
   CONSTRAINT     Z   ALTER TABLE ONLY public.trt_prest
    ADD CONSTRAINT trt_prest_pkey PRIMARY KEY (trt_id);
 B   ALTER TABLE ONLY public.trt_prest DROP CONSTRAINT trt_prest_pkey;
       public      
   usrArtiste    false    214            G           2606    42281 '   trt_prest trt_prest_trt_id_prest_id_key 
   CONSTRAINT     n   ALTER TABLE ONLY public.trt_prest
    ADD CONSTRAINT trt_prest_trt_id_prest_id_key UNIQUE (trt_id, prest_id);
 Q   ALTER TABLE ONLY public.trt_prest DROP CONSTRAINT trt_prest_trt_id_prest_id_key;
       public      
   usrArtiste    false    214    214            b           2620    42282 8   doc_artiste_activite doc_artiste_activite_check_activite    TRIGGER     �   CREATE TRIGGER doc_artiste_activite_check_activite BEFORE INSERT OR UPDATE ON public.doc_artiste_activite FOR EACH ROW EXECUTE PROCEDURE public.doc_artiste_activite_check_activite();
 Q   DROP TRIGGER doc_artiste_activite_check_activite ON public.doc_artiste_activite;
       public    
   usrArtiste    false    273    203            c           2620    42283 &   prestations prestations_activite_check    TRIGGER     �   CREATE TRIGGER prestations_activite_check BEFORE INSERT OR UPDATE ON public.prestations FOR EACH ROW EXECUTE PROCEDURE public.prestations_activite_check();
 ?   DROP TRIGGER prestations_activite_check ON public.prestations;
       public    
   usrArtiste    false    209    274            H           2606    42284 !   activite activite_secteur_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.activite
    ADD CONSTRAINT activite_secteur_id_fkey FOREIGN KEY (secteur_id) REFERENCES public.secteur(secteur_id);
 K   ALTER TABLE ONLY public.activite DROP CONSTRAINT activite_secteur_id_fkey;
       public    
   usrArtiste    false    198    212    2881            I           2606    42289    citoyen citoyen_reside_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.citoyen
    ADD CONSTRAINT citoyen_reside_fkey FOREIGN KEY (reside) REFERENCES public.adresses(adresses_id);
 E   ALTER TABLE ONLY public.citoyen DROP CONSTRAINT citoyen_reside_fkey;
       public    
   usrArtiste    false    2843    199    200            J           2606    42294 +   commanditaire commanditaire_citoyen_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.commanditaire
    ADD CONSTRAINT commanditaire_citoyen_id_fkey FOREIGN KEY (citoyen_id) REFERENCES public.citoyen(citoyen_id);
 U   ALTER TABLE ONLY public.commanditaire DROP CONSTRAINT commanditaire_citoyen_id_fkey;
       public    
   usrArtiste    false    200    201    2849            K           2606    42299 .   commanditaire commanditaire_entreprise_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.commanditaire
    ADD CONSTRAINT commanditaire_entreprise_id_fkey FOREIGN KEY (entreprise_id) REFERENCES public.entreprise(entreprise_id);
 X   ALTER TABLE ONLY public.commanditaire DROP CONSTRAINT commanditaire_entreprise_id_fkey;
       public    
   usrArtiste    false    201    204    2861            M           2606    42304 :   doc_artiste_activite doc_artiste_activite_activite_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doc_artiste_activite
    ADD CONSTRAINT doc_artiste_activite_activite_id_fkey FOREIGN KEY (activite_id) REFERENCES public.activite(activite_id);
 d   ALTER TABLE ONLY public.doc_artiste_activite DROP CONSTRAINT doc_artiste_activite_activite_id_fkey;
       public    
   usrArtiste    false    203    198    2841            N           2606    42309 =   doc_artiste_activite doc_artiste_activite_doc_artiste_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doc_artiste_activite
    ADD CONSTRAINT doc_artiste_activite_doc_artiste_id_fkey FOREIGN KEY (doc_artiste_id) REFERENCES public.doc_artiste(doc_artiste_id);
 g   ALTER TABLE ONLY public.doc_artiste_activite DROP CONSTRAINT doc_artiste_activite_doc_artiste_id_fkey;
       public    
   usrArtiste    false    203    2855    202            L           2606    42319 '   doc_artiste doc_artiste_reponse_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.doc_artiste
    ADD CONSTRAINT doc_artiste_reponse_id_fkey FOREIGN KEY (reponse_id) REFERENCES public.reponse(reponse_id);
 Q   ALTER TABLE ONLY public.doc_artiste DROP CONSTRAINT doc_artiste_reponse_id_fkey;
       public    
   usrArtiste    false    202    210    2877            O           2606    42324 %   entreprise entreprise_contact_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.entreprise
    ADD CONSTRAINT entreprise_contact_id_fkey FOREIGN KEY (contact_id) REFERENCES public.citoyen(citoyen_id);
 O   ALTER TABLE ONLY public.entreprise DROP CONSTRAINT entreprise_contact_id_fkey;
       public    
   usrArtiste    false    204    2849    200            P           2606    42329 #   entreprise entreprise_siege_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.entreprise
    ADD CONSTRAINT entreprise_siege_id_fkey FOREIGN KEY (siege_id) REFERENCES public.adresses(adresses_id);
 M   ALTER TABLE ONLY public.entreprise DROP CONSTRAINT entreprise_siege_id_fkey;
       public    
   usrArtiste    false    2843    199    204            Q           2606    42334 ,   form_activite form_activite_activite_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.form_activite
    ADD CONSTRAINT form_activite_activite_id_fkey FOREIGN KEY (activite_id) REFERENCES public.activite(activite_id);
 V   ALTER TABLE ONLY public.form_activite DROP CONSTRAINT form_activite_activite_id_fkey;
       public    
   usrArtiste    false    198    2841    205            R           2606    42339 (   form_activite form_activite_form_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.form_activite
    ADD CONSTRAINT form_activite_form_id_fkey FOREIGN KEY (form_id) REFERENCES public.formulaires(form_id);
 R   ALTER TABLE ONLY public.form_activite DROP CONSTRAINT form_activite_form_id_fkey;
       public    
   usrArtiste    false    205    2867    206            S           2606    42344 '   formulaires formulaires_citoyen_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.formulaires
    ADD CONSTRAINT formulaires_citoyen_id_fkey FOREIGN KEY (citoyen_id) REFERENCES public.citoyen(citoyen_id);
 Q   ALTER TABLE ONLY public.formulaires DROP CONSTRAINT formulaires_citoyen_id_fkey;
       public    
   usrArtiste    false    206    200    2849            T           2606    42349 )   gestionnaire gestionnaire_citoyen_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gestionnaire
    ADD CONSTRAINT gestionnaire_citoyen_id_fkey FOREIGN KEY (citoyen_id) REFERENCES public.citoyen(citoyen_id);
 S   ALTER TABLE ONLY public.gestionnaire DROP CONSTRAINT gestionnaire_citoyen_id_fkey;
       public    
   usrArtiste    false    200    2849    207            V           2606    42354 2   gestionnaire_roles gestionnaire_roles_gest_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gestionnaire_roles
    ADD CONSTRAINT gestionnaire_roles_gest_id_fkey FOREIGN KEY (gest_id) REFERENCES public.gestionnaire(gest_id);
 \   ALTER TABLE ONLY public.gestionnaire_roles DROP CONSTRAINT gestionnaire_roles_gest_id_fkey;
       public    
   usrArtiste    false    208    207    2871            W           2606    42359 3   gestionnaire_roles gestionnaire_roles_roles_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gestionnaire_roles
    ADD CONSTRAINT gestionnaire_roles_roles_id_fkey FOREIGN KEY (roles_id) REFERENCES public.roles(roles_id);
 ]   ALTER TABLE ONLY public.gestionnaire_roles DROP CONSTRAINT gestionnaire_roles_roles_id_fkey;
       public    
   usrArtiste    false    211    208    2879            U           2606    42364 (   gestionnaire gestionnaire_travaille_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.gestionnaire
    ADD CONSTRAINT gestionnaire_travaille_fkey FOREIGN KEY (travaille) REFERENCES public.adresses(adresses_id);
 R   ALTER TABLE ONLY public.gestionnaire DROP CONSTRAINT gestionnaire_travaille_fkey;
       public    
   usrArtiste    false    199    2843    207            X           2606    42369 (   prestations prestations_activite_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.prestations
    ADD CONSTRAINT prestations_activite_id_fkey FOREIGN KEY (activite_id) REFERENCES public.activite(activite_id);
 R   ALTER TABLE ONLY public.prestations DROP CONSTRAINT prestations_activite_id_fkey;
       public    
   usrArtiste    false    198    2841    209            Y           2606    42374 -   prestations prestations_commanditaire_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.prestations
    ADD CONSTRAINT prestations_commanditaire_id_fkey FOREIGN KEY (commanditaire_id) REFERENCES public.commanditaire(com_id);
 W   ALTER TABLE ONLY public.prestations DROP CONSTRAINT prestations_commanditaire_id_fkey;
       public    
   usrArtiste    false    2851    201    209            Z           2606    42379 +   prestations prestations_doc_artiste_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.prestations
    ADD CONSTRAINT prestations_doc_artiste_id_fkey FOREIGN KEY (doc_artiste_id) REFERENCES public.doc_artiste(doc_artiste_id);
 U   ALTER TABLE ONLY public.prestations DROP CONSTRAINT prestations_doc_artiste_id_fkey;
       public    
   usrArtiste    false    2855    209    202            [           2606    42384 *   prestations prestations_se_deroule_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.prestations
    ADD CONSTRAINT prestations_se_deroule_id_fkey FOREIGN KEY (se_deroule_id) REFERENCES public.adresses(adresses_id);
 T   ALTER TABLE ONLY public.prestations DROP CONSTRAINT prestations_se_deroule_id_fkey;
       public    
   usrArtiste    false    2843    209    199            \           2606    42389    reponse reponse_trt_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.reponse
    ADD CONSTRAINT reponse_trt_id_fkey FOREIGN KEY (trt_id) REFERENCES public.traitements(trt_id);
 E   ALTER TABLE ONLY public.reponse DROP CONSTRAINT reponse_trt_id_fkey;
       public    
   usrArtiste    false    2883    213    210            ]           2606    42394 -   traitements traitements_citoyen_prest_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.traitements
    ADD CONSTRAINT traitements_citoyen_prest_id_fkey FOREIGN KEY (citoyen_prest_id) REFERENCES public.citoyen(citoyen_id);
 W   ALTER TABLE ONLY public.traitements DROP CONSTRAINT traitements_citoyen_prest_id_fkey;
       public    
   usrArtiste    false    200    213    2849            ^           2606    42399 $   traitements traitements_form_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.traitements
    ADD CONSTRAINT traitements_form_id_fkey FOREIGN KEY (form_id) REFERENCES public.formulaires(form_id);
 N   ALTER TABLE ONLY public.traitements DROP CONSTRAINT traitements_form_id_fkey;
       public    
   usrArtiste    false    213    2867    206            _           2606    42404 $   traitements traitements_gest_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.traitements
    ADD CONSTRAINT traitements_gest_id_fkey FOREIGN KEY (gest_id) REFERENCES public.gestionnaire(gest_id);
 N   ALTER TABLE ONLY public.traitements DROP CONSTRAINT traitements_gest_id_fkey;
       public    
   usrArtiste    false    207    213    2871            `           2606    42409 !   trt_prest trt_prest_prest_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.trt_prest
    ADD CONSTRAINT trt_prest_prest_id_fkey FOREIGN KEY (prest_id) REFERENCES public.prestations(prest_id);
 K   ALTER TABLE ONLY public.trt_prest DROP CONSTRAINT trt_prest_prest_id_fkey;
       public    
   usrArtiste    false    209    2875    214            a           2606    42414    trt_prest trt_prest_trt_id_fkey    FK CONSTRAINT     �   ALTER TABLE ONLY public.trt_prest
    ADD CONSTRAINT trt_prest_trt_id_fkey FOREIGN KEY (trt_id) REFERENCES public.traitements(trt_id);
 I   ALTER TABLE ONLY public.trt_prest DROP CONSTRAINT trt_prest_trt_id_fkey;
       public    
   usrArtiste    false    214    213    2883           