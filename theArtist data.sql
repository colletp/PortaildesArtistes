PGDMP         7                w        	   TheArtist    11.2    11.2     �           0    0    ENCODING    ENCODING        SET client_encoding = 'UTF8';
                       false            �           0    0 
   STDSTRINGS 
   STDSTRINGS     (   SET standard_conforming_strings = 'on';
                       false            �           0    0 
   SEARCHPATH 
   SEARCHPATH     8   SELECT pg_catalog.set_config('search_path', '', false);
                       false            �           1262    41590 	   TheArtist    DATABASE     �   CREATE DATABASE "TheArtist" WITH TEMPLATE = template0 ENCODING = 'UTF8' LC_COLLATE = 'French_Belgium.1252' LC_CTYPE = 'French_Belgium.1252';
    DROP DATABASE "TheArtist";
          
   usrArtiste    false            �          0    42221    secteur 
   TABLE DATA               :   COPY public.secteur (secteur_id, nom_secteur) FROM stdin;
    public    
   usrArtiste    false    212   �       �          0    42147    activite 
   TABLE DATA               V   COPY public.activite (activite_id, nom_activite, description, secteur_id) FROM stdin;
    public    
   usrArtiste    false    198   �       �          0    42154    adresses 
   TABLE DATA               G   COPY public.adresses (adresses_id, ville, rue, num, boite) FROM stdin;
    public    
   usrArtiste    false    199   ~       �          0    42158    citoyen 
   TABLE DATA               �   COPY public.citoyen (citoyen_id, nom, prenom, date_naissance, tel, gsm, mail, nrn, nation, login, password, reside) FROM stdin;
    public    
   usrArtiste    false    200   v       �          0    42178 
   entreprise 
   TABLE DATA               j   COPY public.entreprise (entreprise_id, contact_id, siege_id, bce, denomination, statut_legal) FROM stdin;
    public    
   usrArtiste    false    204   [       �          0    42165    commanditaire 
   TABLE DATA               J   COPY public.commanditaire (com_id, entreprise_id, citoyen_id) FROM stdin;
    public    
   usrArtiste    false    201   x       �          0    42186    formulaires 
   TABLE DATA               �   COPY public.formulaires (form_id, citoyen_id, date_demande, cursus_ac, ex_pro, ressources, langue, carte, visa, a_traiter, nom_artiste) FROM stdin;
    public    
   usrArtiste    false    206   �       �          0    42197    gestionnaire 
   TABLE DATA               Y   COPY public.gestionnaire (gest_id, citoyen_id, matricule, bureau, travaille) FROM stdin;
    public    
   usrArtiste    false    207   ((       �          0    42225    traitements 
   TABLE DATA               i   COPY public.traitements (trt_id, date_trt, appreciation, gest_id, form_id, citoyen_prest_id) FROM stdin;
    public    
   usrArtiste    false    213   �(       �          0    42209    reponse 
   TABLE DATA               Z   COPY public.reponse (reponse_id, trt_id, date_reponse, reponse, rep_positive) FROM stdin;
    public    
   usrArtiste    false    210   D       �          0    42170    doc_artiste 
   TABLE DATA               l   COPY public.doc_artiste (doc_artiste_id, reponse_id, no_doc, date_peremption, type_doc_artiste) FROM stdin;
    public    
   usrArtiste    false    202   :H       �          0    42174    doc_artiste_activite 
   TABLE DATA               d   COPY public.doc_artiste_activite (doc_artiste_activite_id, doc_artiste_id, activite_id) FROM stdin;
    public    
   usrArtiste    false    203   I       �          0    42182    form_activite 
   TABLE DATA               O   COPY public.form_activite (form_activite_id, activite_id, form_id) FROM stdin;
    public    
   usrArtiste    false    205   �I       �          0    42217    roles 
   TABLE DATA               =   COPY public.roles (roles_id, langue, type_roles) FROM stdin;
    public    
   usrArtiste    false    211   ^Q       �          0    42201    gestionnaire_roles 
   TABLE DATA               N   COPY public.gestionnaire_roles (gest_roles_id, gest_id, roles_id) FROM stdin;
    public    
   usrArtiste    false    208   R       �          0    42205    prestations 
   TABLE DATA               �   COPY public.prestations (prest_id, date_prest, duree, montant, etat, commanditaire_id, doc_artiste_id, activite_id, se_deroule_id) FROM stdin;
    public    
   usrArtiste    false    209   �R       �          0    42230 	   trt_prest 
   TABLE DATA               C   COPY public.trt_prest (trt_prest_id, trt_id, prest_id) FROM stdin;
    public    
   usrArtiste    false    214   zS       �   %  x���Q1D�3Ql���e�Hq��X��N��̇��861�~���V������$�q�H���l��\�m��m��ڄ�;逽)��
�8r1ߛ���i��U�?�G�8:�;l!��A�Q}�
/����K���\��k4�沦�2<O�v�-u�1a�3X?&�s�B�Iqw���z5ݪ�66Vg�B��g��ؠ��HҐj���[ݎ��Y=Q/Z �HiR�Y�bR����e��>�z=O6J>|�/��@D��a殚J,ǯ{�.�Ӻ�<�?���bqq�      �   x  x��WKr7]C���7�~��*�x��*�l�@R�I�t�9MvIΡ��Q����ܣM̀����蚫$�!$����6J���c�I]���p�������������z���r��S+�y��S=�֧�E��}?�Sݽt���qVE���z�98�5t����-��B0���Tl�BՌH^�N�%�^]���GW��s�lի��p��*Y�}�Q[�we��v��Tno��߯��&n�ܾ8�x1��M�/�s$��b��r���Sʾ�s��蜪�ꗻ��E�4�ܓuu�����ξ�d�n>�[h�-ez�ۼU6&[Z4t�GJ��]N݌V�Udf㼥�S���(��ș&�7ݝ��zS�;�e��wn�gw��E0�N����x����\�ۛVG���tC��H�8&��n�Æ�XM�ѦU�jj��T���᜖�:�>fO&0���1��|�UD���'M�1�f4�ɣ�.�Ԋ}�TGDsA_�`j�jm�(̓��A�zz�:\��I�R5�d�|&	��w�U����#�Q�ˇC�w��2=ٕdbF�r�Y�#[�\d�N����N�5�Z��j�}78߭_nkݾ\���j�M�ݣ;<�Dz�ԡЉmVb�q�Kd}�c܌���������2�&Ą Zꩣ=$:J�;�F��Ħ|�bƙ���0��떼���G�7��|�%{�э2�P�Z�c��b�|H��3.i��d��(P)fPm!���Z!S�����[�Y�ͳ5�!�  �U�Q12�
�`�R�qO��xY:�j5P��Tt�fʰ�Z���%\rt�5Yf��&B6a>y�-a�S��[�p���LO�¨g��F��r� �U ���a��ZBP�Q>ޜ��(c�V�b��7��c%��j�:`��k���,)]0b83��ט4�A$�.t6��,��1���t�@R�gtQB�j,�9���.�y�ζ`B!�x1R� Lv���0ɨ7����/)e8S��{��TxDi��u���>;�~������b#,�!-�KZ7� ����h\MdX�Ct�̓fTv��f���b\J�3�Ե��'d�1%�����#�
���\~�YؓջB&-�c�	T�� 8�G1'�/g��)�̱�۪M	V \ľ%�]2����,����-���&N;#̛�Ф6���|(�ʁ!�-����9�0�fc_Rj���~.+3�f"���q0��B��KC{h�����]��Y�Ys�V��-؎����|��@[�DU�L��\䄞�$���	|*k�d������-�f>t�k;�7�q7���ۮ�}R�R��@4JU7Oְ����%_u�UP�n�C���E�}'�FmSӢ�����J/��eML� ���%5 NJ���>2�Ϙ�9]��o?\]]��ó      �   �  x���Mn1��3��F��q�-,��bٍ�8�ң�^U�+q.FZ�`o�(J"���V�ph�7��A��Y��Ў2h���qyz���]�q����h�������1�-<���P�*3��������n�u�^����1�F;�����[�� �`���'Q�՝y@Gj��+����Q���>ھ^�������67:�]B;�@�~Њ�1�+��``���x��u:"A�`�������@���m{-T|D���
�v��<Y)���5ז�ȧ�8�hFѵ�,w�m�y�ⶓtc���([s�BBL3O��Jo&`�����2[J��y[1o����ZfFT�l:�D�R�NzW��+�&wP]RV䩤\���$�m7��"	�s��|�yt�#}i�iZ��!�p�,���i�n)��Y��)휑9=���:�}卹.act[���O��
ϣ�f1i�ǫAQx��č����������_�����s�Vp      �   �  x����n�H���s�mCU��}7 ˄�7}��;�鷵�Dh��i[��v������$�< �
�p)�h�^�A#Z�\��y~
��d튍9d�`0Cʸ�p����0{NA��֐C�.Chڐ�0*�Y'l��=(�F�7�o꺷>�~@9mm�z{^����w�m��s�+ܤ�����U���%+�E3�5h�	�y :�&��s#
�&��-M늧��h�,�"bd�r�R�c��~!D`����P|�}��^�y�W�|��O*������'[����=����iu������ʁ�@��H�ל(�R�A�-������z�i
�F#D�5&|e~��}�x�k<
�O~�=�R�}�z��ͧ�f5�F�۬}�/g��+�b5-������p�
��6��6'A$2LN45�*�+�ѠnP4�A8�3IE��fN+��"zny��|�G�3>�O�����Ow7�01���ڌ��r0n���������ն��ֻ�<f(��+B�:�2�Dq͈6�I��1��M����,U��H�M6��>��0��? �Ve�Dk�ۢ�mC�/����.+/7�Q+^������f�|>�ۧ�,8i�I�,2��.!g�a���	�i#�O��{�(K.FD�Ij<>��+�-�jOF��W���V!��a�ڢ�vF��Z��O���~;uu4�ysQ,F��v��8��oR��O�y��s�o3�Y*(aI"���&���h�LP�0J���K�� H�\+��i!}���Q��{d�B��ܚ����aR�P��21�:���ŭy|2u]]onÛ�94��;�]�d�y�lcy؟�7��L+m��@$�d��L*Nrc��4�}#�@���.�	�k#��C����5��#[}�����a�	���a��iQ\�i+o�ڮSC?i�S=m���C�f���[L{6K�G�Wz<l�Bƃ!�Tfކ`Rr��h�:�x�7��?�*      �      x������ � �      �   M   x���� �w��2"��D*������s�}ҫ��kX&nM�ʐ����	���'�
�1+�d���Q���4^"�U|      �   C  x��Y;�$���O1Xf @u �2d�ï���镡�]��Z)F]idv�LL��H����N^�*�c� �V����k_<�qٛ[u�e�DA�4�2���G�5p��A�����������/{�x[����wr�qY�@e��Sk(T%����V���G�"�_W�:=�Րr� s��ZL�V"�2yE�\�����{V{�#�D(��J����{�|�yy����N�L�ȇ2���mOϽIi�� �^g�aT�:R��r�2���n;���ʥ��K�`.y?TB�:�;�:r����pB���){��}|�u���]&��y���Ԅv	#͘�Zk\�xD��Q�p��;O��ګłT���c�@�%�y�i���b̯+/+�&��y�(,v+D�\s�� '�+�#ك�^�S�/�?��͋��ݖ	�F������T��*�n"<��${9�9ul3���b�2C3iA�iF^˺ճ�͉U^���c�ҳSl�5�n)^�Òu2���B`�#���W2{V�S�Z�e�+���<N�o5�� �T��Fg��N5��c���7��{\Q���Z��? �'���]EE:�Kq��Ch>�n��@���0ml�n�k���d�Uq�6�c���%ꚲ(]GA�
t�`�H�O��J�ְ�qbO���2)�E�nv�Fw���x)��۶�*���#c��(u�b��&a��-+[�)}A&�46��)V�+� MbX@�ĺFӫ���s�G5f���/G�Y��' �y�A��Cl0o(�[�A���h�2�g(��f�a�j�5�VW�Ә���g�B�����٭�ꪁ���z�
���q����:� ��/R��?��;�;R�P��]k�OJ�A�VZӃPYR<�v*P��sp�̭�Tl!�D<&r�X*s��c���3�G�4�$ ��F��ӺV�2����>���p%Ϟ���'���mg*R�Ӫ>����v�|��Ҫ�SX�PY�k:oVW�q�To��i�#)��gu�#[�0��=Ty�8�N]�����~���A�̭7V͎�lls�=  q�������<t����
�ղǠYеb�n�¢F����nŻ�`�R"<�h�vN��=o8_�5*�i=�8j����d�ĝ�H����4<Ѵ��4��A�5/�-6�7Dß*-Ϯϒ��O$�
C��_�{.T@(
#x����t��TO6+u�2�v��*#�N��z��rb�;,n�:���Zr�m:�����!Q~��[4�v98#��}B~[�DZ6�w��X���z�%���9'������z�ό��-���4Do�8P�#���k��T�!Lp}y�'�� ��IS�Q�3(��zlIQ~.�O'�eHe�@�x���#��CY�f}e����2Gf�'aZL��!�5�����9L��N���+��Qَ�-��v؄��y%`-�z�3Ell��@��'�6�Y#�cֽl&M`�{�f.-�%��l�A�C�#Ľ�<D��7��3Mp8�ez[�c?ƍ����.�`H��Q����� I�##�38���\���B@�p.t��l���!�X�>P����g�۔[�E����!@�b����!c�����r��\je��c��#��n����5�0ڂHc ��O��V��\�ҽ[��D#":�rlS����	8+�f��s����ZnqD�P�t^|$�0_�'}�.��*�[l�;V|�~c�y߀CD�>���Ĺ�w��ncX�x+e��M)Ӹu� 2�~ V;��H^�ցq7iذ�aKZa���Ԓ܈z�w�ʡ^������ĝl�"��Z(�,Z�����q�lS��e��0G�"W/Xo�'��q�wvC��~�l+���b�O��屄�B�#�Uc �-��0�G��4�l?��=�ϋ�ѵֵo�t��u��)�"��r*WOZ����1����y��+�?��ϻ#�b��b��w��������{�n����a���;�x����kc��[�i�v���AA�6��Kc�*>/J#?�b)���x������(q�*�kl-�0����5�W� �Ȟ��|F �?����~�����������:��O      �   c   x��� ѳ�B�� �K.(���u��P�TR�M����inU�/���V�c�P���¨�\�c���h�/mJ�")f�x��9���п�����      �      x��}[ve7��7k@�. @�_@���_��gJ)�TK����i�&�7��乗)J:��')&S<�p_ �w �>E���\�3Q�=��i�6آ�=PL"�D����=�xIT8��O�ǟ�_���?�>�n?}��w��pk�Jl܂�ia�Vj�����S�fX�q�k���LA�tK���>������1G�;�J�W���f	15�6s���'��c�O\�q�L���y���zl㧯�����q|���~�����u�\�����o�]�����?���������~x�v=�����߶�X�������O���uor��Υ���]B[q��h��}	��ɞk�8p�O��GL�4�BqK�}�b�VJ���M�Y�V�2��d2�L��i�f�ǶoR���Hz������q�� �����ώ�u`�����@�,�j,#���)<ڈW��)2��$ �,��֦�YĦ��>�r�ZbI?�zع(����b�">�7LUu��3�+X��,�VX��0�:Ði��lf9��O/l�a��6jm1S��6�C��2���d���TV�`s�q��}c�0g��mW���˪�o�J�XQ��_�_'��R�C�9܃���
"cUͽɎG����Bc,���J�̳���pɼ�
V��e}��g4�({����+������r&��=�(OdO�.�*��ᛏ�������o>>~�����o?���?<~�x���q,��Ȍ)ۡuF��5����++����*��W�z|���
炳H�iD��,�<w��k�Q���%D�5?|��������O?�����߹(�*�m5D��X�+�+Y�,xP��:���ӭ�����֘�# ���;��6�S��MJG�`>�BIb�=?�����~z�p�����:mἕ��=��%����+�+�9EЧ��R������7�M��
������g�uƪ1󰉩ց�yP�M�d=�ć��Lb��o��_:pa?�_?~5��j�� U��?�Lw����J�������O�.pY	Ѳݻ΅H��IC.�+Li*�J-{��η�^��{@E3�Pm��3GWk�V,��=�bu�Z�����ׯ��4�����V�4E�М{���=gt��)�����>!��A<����i��@e��>g����#\ � <�턹�1}
=x [�D���y�.��ra�o�C�]q�n�!�q10�6�չւL:��OQ����S;y�՞s��6��$�ZW�Ķ	T�}o�]�5~s|�R�B��l�@�_�h���y���g>�Xz��_�N�Us�q�c�J. *k�v^c?�&�'�lG��n�s�E�g��P=���J�W�v���7��)A���J߾�:x�e��""n,����JX�m䝤���̞$yl�D�������uFh�r�}��%bI4�δE��d��2jyAG�L�����vݳps�
��9�"S�%G����Q��/w���.���2�Y�x��1o�y� ,�'rJ���p��lgH���#$�h!7��a9�Ah�TH�w@�
�
��o��[�v	������9B�O�<G ���=�0�I��9&�N"�`��C�i��G���/�5����K���<!"6Ġ��ki�ዲԒ���z���.5!��`��Q�j���^
d� [+�R�v�7��҅
e���<9D6�UR���8��+�e�9�\Uo�:c�$O|�ü{����=E0�� A&=��9��F4�쀑�'Y��F��0y2X8��GX�pD�$Tj#4|�,���k�Nh�/ћ�խ��ܻ�˪�zH3�����X|�!��t?1�;3���̩�J
�87Mi�U�lxKN���I���@��GK������p�Qv`c*%��Yʕ�'c��d#�.��*m�����op�=	��f��,q1�uS�	��}�i=w�4xpwR\�-�a�6�e��}fS���&.������d���]�ؘ��U���=��Q7Af'qQk�w9���)����t�y��T��@X���K��T��~�:9��5���l�M�.<�����TҴ�o����E��M�-�zv�-��C��i���G����*`�߿|�=�q"0<���w�=AC�I�gKG������on��M^x�Xs��x:[.s�\����-���w��.V�����	s%#�n޳!�9���q�j�@��V5gF�z������Q�8)�IЙ���盨W�,O�/Bp�����u2���{B\��pN߼,�ل<b�6�q�-�'g��"o?�6�ZlP��S�f3�k�mXY����S�=Z��㗮�]��u�!,�?��-W�g��B.�D���?9�eB�7���`D�By�3�L��p�����K*�l��R<�s�Ƕa�5���`�g�f<��U}n*ٹ��rI�����} ���Y�s�kX��nV��v��n-`���DW1�F�M,D���i� ��� �k���+S@x��ߴ_[0ֳ ��<,��R�>���
 ��w�K�BZ_��8#K�\|c��%��J:�1�Iy��r6-����sN�w�����yQ�L������a�	Gp��BQ�oV��EWِ\����e�б�m��{Ө�9'|��|�߿����DY����2N����q�OY/OR��_�NO��U	�06f�\Iچ�ܻ��x�I9BI�K�Mo��\t@�/�^#�S�Q<,z<��]}��1f�#O���l����n���o�1ΥO;B����a��8��M��9�%���@@&�2D7q|t�[j�N���\�X���e��)^�Ds ��c���=�N��Lwt̫�̫��hY4
��b�#��
��g#�h�Ɏ^�	��鈗��F�յ�-�dp1�	�Z�rBd�a`�Ϸ�>TOS���f.N���	T5�Fp��&Ob���),���wO.������s�AĴ*���*"L��5$�!b�"��_-����!_R!D�SZ��嗵zu���;S�%���ԗ
��aܿNf�t�9�m��ZVRf�]{��kڎLBʵ�F{����A8Î��S=k�	�&���7�B^JE�yW�Ӗ�{�A��a啯Em-O1����o�gկx�������s�%�9bވ^��<��jAl6���K�2�k���}k� ��ghV3�[j���"��	]�n�f��/&oG��2,�Y�tB�+�o�0#�\����Y������^�7?[Pαb}�Mk�
f���B�]2���I���j��XOf�����	j��K�{꠳��R7�? ŷ.E|���Փ#��'�����H�f��rz��SD�sOS Tސ���A�� M���5xYy�ۢ�2�8��,O�/�"=�e0�8M�z=��p�l٧8��#"�~.L���=؄�y;2�kո����TH�0�ɷZ���2,9�����n��vx�a�O���$軖�i#������,�]���$��ot��N+�n����@��H��j^<��4�&�C�Uug�N,fZ
'�1�\a�Q��/�Z���U%PU����Z� z�q4"T1g�`���c+"I&e;b�c���ճao/D�fn��ً��C�aBcH���<byf^"%^��L�~:^gkM�ڧ��d��!�k\3���$�Gx��Q�N�^�=���7�@O�������Y��,�����ų	�N���\��X@��|���%N��qg��Gx��m�<|�曏w��x�����y�r0��13����[y.�|s�R�ϑ��A�֠:��F'�`r�@\'n�"�V@�^��
�}��#?],X�xξ��[��#�5����u�N��%)��q�Nv8�E�o�����LQmiֆ�lu���w�9�r�=:]+AC맪�p�(�ԢI�u����}R�&�ONUǁ Q앜z��d�X��|�7~�B�kTy�/�]'�:i��^�W��W�
��r�e��㈬z��;�UM'{�V�9��
&��v��~.��ǵ�0 e  D��*���_MV{n�+��]G�=gE�׶E�Ѯ��%R�wF�d.�9ͽQ �a�^f�+88��ݏE�����W����=�z�DkX�"�x�]�������/d�� ���lh`�.-#pT���yL9r.���dĆG�;�ɛ$X���r)�+jA��H�V{t��{�^�H�)Yy��׮�I�N�+�	t����0�'�&'�s�A��֋A�/t�8]��z[F�^���0��0u@�cE�5�0����$?��+�c�$�m�w�#���8�J�#��k�����Ҵ7�Yi;�a�b��1�b�
��"S�%;�$�8� �%���Y���|��J��C*��dA�vO����1����/1�0�1<Hʸ]"��.�xA������ƌ��G��&U�¯;�i��	z���@��Yb�c�G6�=<�@��ܹ��5U'kQ����x�r���
�zu��v�#4��T�n<�H�7Q̥@�a���*���V��R���R}�	'�r;�'�p�Z*y��_�[�H�ܓ�'��k� �P�`WD�{���Xb�O�x�\f�#�f&X����h�~̒.
~�t�����8A�G@fOH ۣ�83(�T��x�)Q]�}�s�G�.�~�yv�?��y�<�h[��lr� �cO�䓷�&�Yd�6		X��bN���z9dDcx#O��� ?\q���^�5i�S�F(���$�#:qGk6y����d�<va�˕�Ǽ�	 �yB�-��k���v�YNQ_(�=95�G�M���2`�^��Db��K�Ƥ׶�D��X�ͺZ޼s�.�y���JbF +�#ه�+ ~j��@�pr�7ax�&O���Cd?�~�ԩĔ�L�v@���kZ�W���.�0�dg?<�?�;�`l3kG��(����Z�zP	><��|��8B��*�6\�|_�4D��V�j}���A�+��9�k?3���`��@A��H�O�Ԍ����Ov� ���4��-�S��2g�S�Q�K��%{B��C�'O~�&'��AA�]t�(V��CҨ7 �'�}k���Ԝӄ���Z��|���v�Ҥ�(�짫/p���O�-�C��Ã��s-��J4��M���F��@�2?�G\7����Jp�.���"A�g���[�&��	���z�.��&^Zk*�h��#d��(��~��,-�D�������Tz��n��Q�;@�/�4�/��3�h�<B�i�n?,�O{;݊��@?$T������ǧ��Q)�3�o�6�}� ��
������M�|�����vJ��澭o/=�]�v͚owu��!���Dy����m�������V�u��aùj(�<�����ޚ ��'�φ�"!|�͑K�,�	���=.�Lb/���"/��z��5���0��x.5t�޶�[�s,��*�
��s0'[QT��9�������U�[�<�0:l__Oi�^K�A�������h7X��ak�Z�A�AA6�G�Udr<�nwJ�L��p��(&����7P���\^gR��hF����J�^�Ag�p���\�W/��&��$I�,�M���m�k��[sw���iԠӫ��٪rFT�9���_�k��GE�!kd1�6c�0����]�SM�s�j��z�G����a�V7�{y7�g�N6Ӏ�w�E�f�k^˼��; �2�VLp�&�F�~q�[[ŶV���v|B(�b���; �����m�"�9yOl��V��<β\iz�~�����}ȎeV��T������K}�D��0��w��Tr�*,t7�dK�1o����q��X�H�hJmqG������E�{r�J��Og��̭x"=�t.������(�^C�!rB�2�fi�
y���g�ۊ���J�1���/�>�@z7(��vy�D�w���\��|�?�d�c�g�a�|��dx����(:���	��M�(��]�h�~V�#E�Wv�z�LQ���;�Uy7�f���U���g	?WO�RK�/f��h�����{�{��unn]�㿦��b���|�^<'zv�ߪi�O�@�~*8�n~pt�^�^�%�.AҘ�;���h�����!��ü�����cWm���'����R2h��Yq�?�AZ;H�2F}v�(�9��}�>�)���H�dx<�tn���!g��	φ��5b�y�'�Ӑ���������+/��_)�~dS��_y�v��D�'��#������E#
�F���[׳=��P:o� �^� �-�\�l`$_n����|r�����C�D޴=���y�$s�y�`]�^w�1�w9�9Q;�
0p�EO�C�ȥ�ז��Wl��U����?ϟ�b[.ioj��'�C+�\�\���>��؄0f�?�{0��a&�ڴ��롘�=�=l�.�}�/��S���-'�p�X�oյ�m�,Y�U|xR�b�~6��N���~ %�ۛ{��H���L�S'��L��uS����Zs��Wv9�HQ����xi񅮝r-k�[��5�D/4y��ĴVT�0�@����e�+�7�RĐu�z�4iX��k�pO�źSx�e����z&
˵�α��ڸŊP�y����:�����x��bh�"�~L���y�^�ZGRb�{{�g�.���M֚H���C��D/�f�_7t���A�u�;Y�l�<�w`�.��èΆ�Zl�ZSٷ�|�%Q��-�~C�Ho��T����aXI�J��;����"��@�Pʤ�?��?�h����/AK9N���|��&o� ��캀a��~���O)�#�!&�(�\��M��	B4�kM�6�mx�*���ʕ#��ν8��/mƾM�%�<X����`�	��V�������g�ŋ���xS���?ۖ�|�ӟ���|Q�      �     x��UۍG���b��l�� �~���ΐeA:�r��5�֧�`���LWu�X�.kFuJ܍Le��i��>|�Z�ac�2礑W॔��Td�>�9'?�I�$��t�|�����)~{�˻��1.�Ç��?~���?]�����..����������+vgv�bA�,�d[o[�.��kv�H@4w�>&^*!5����?�2%��M�[�k�l��9�V�]K���y���)���b\����,�L���1��܄��eIk�����ƀ�W�����O���%ֳ��&.0+�	�ZDG���m�n=�zKdM+u�`Ҵ��3�;h�׫���c�N	B�B�e@gQj�&)˨��nm���u�F��Nj+e}d)l���ܔ�9�j�u�����Y��)Mм,tٍ���Q8��G4	O�	��W�����g�{
�z� r��Bz�D?�6�%�F��\��hޔǑ�Vj	��~�7�m�2�*yζv�Zn|��&��x�� V��(�~20�%�vO��2���Ń)�0DX��`�����[^�L�M�ZO��� l���S�ۧ�'x5����È�,��P��4;�Wt�5�]=�a��L��+�q��r^�;f'9[o^Nf��)]O�ߩ���Cz�ڐ=���&RҨ~ZPЄ�I��_+���&�N�����P�D��sH}6.�`͔@�̣>��.*��d⎄�M�78s�>�m��Ǚ3z�cG�@4��G�W���#1����#����[�*�<2� �{;���{~`SZ�v�b�#�Z��T��^�4G>�6~K|=�;��g��W�.��l�57!�;��8,��܇"��*F��=
D�`P*	C��l���HKi\���R�R �W��X:�:!�ZY�bY���s�G��*�$�RH1�r�O�&���:����Tf9~}�N���>i׍(J�/O����!QWKHe���)�ͱˠ��a_���{z�v�P)=~�N&���v!ނ�\a�ɜ3��c["��:����OL�XT� ��s�?g�oҐ�W�)��k�������7O�m�      �   �   x�u�1NF1��Sp��$Mҗ�k��M"�!��/
B'۟c�����Nwe��S��[�N�s80~�:���e��|��V#�������x�ӱ��G^��n�g��cD.�9;	SKĸ� ���.���P��$sW{���c��%���u�IP��憅�a�sW�D6���]X4/��@u�tƠ�V?����|]�DLV�      �   �   x����!��K//���^r1�/!���F��J�"��D�Jhi�C���Y%"a[y���aG�R�5�/͘帢CV7��^|�Ç��߄).	)����LJ�����0$ǂ�<$�|T��'S�~Zk?&�6�      �   �  x����q%1�E����. 	�2? �M���1@��B/� /����2[:��$�wr]=��v���r�f�a�4��dב�ԝt�%�s��ϣ�2�?%����쒺ٸ]��1~�5뙅WeQ$$���d������G�#y��$�$5����ݳi���r�wk��TE���Uj����o����+E�t��Iִ�m�s�o7G�j�|�HRkK�f锜�;w�哲�z=i��I'���z�F����K���[Gs*=t�n��T~:cFsZ��9�:O�{N2FK���حع��7F�2MR�ߖ��|RW5��#��Z_#i0[)Z�U[���?�rv��n�d��w�궞u��V^uʞ)�*c�}�=�Rs�u�~r@���"΁O�I���6�F��[f��Y�v��ȑl��s��Ӏ�[ܓ7-��8�Q��1�zW�;�������u ^@X�A:��F�g�nQ�Ϥ��z�D���4���-#�֙�}�\�4г	}��k5���F �O��U��0r��tԁ�ӷ�V�.�ժ٭��N�F�^4�=�Uw�v?��� H�H�R�Ӻ�y�g��uW 1��3�<A�z�jy��{v�%�p�h���֜׹I�����W���y�CtJ{�]]�^U�SZ�ؚ:� �>R��Ӛ1��=����(vcQn]�Zl��bW���n/_.M�e�sf�{��}g&q.g�o�c��3��4vۣ�7�/�)���7�J1X��'��ᩯ@����� �
N1���J����pn��S^���X��+�|��~���:���Kb�!�Aw^�W��(��A�3��Pp����5�S�'��N3T� �`n��=VH��cr�Hj�w����޷�kL��0�(�]�*зe�>E�+@�o����x=��(0g�Do�@��n/� cEOw��
O�f� ζ_�A�j}>��B{j�������eM��u��ث�����������R�e�X�(� �b������ٵ�+4�sn�y��r�Գ��ϟ|E<�bc�j�Hǅ�G��2 ����.�D�`�-���U��B�鿦+zc��s���IZ�(�![訞��:�H&��L��qz�~l}� �w��2c��	��Y}�8�?D4&)
U^l��65'�d��e|�����kƮ̐p/�o�o��>�٭[�!��!z�	啳�);Q��*F��z����'m�+�ë�.w,6 :������ߦ�g��
��T�,��cL�:W��Wj�'��N�Q�\{�g2�o7����֖7;���d��{��W���rUNzll--����i|r�����I{�\Ǳ���=2���g���*�Ҝ�:�1k������*��<44?�9�_�c������Z�V��jq!��7q����U�qw}M����\�x	;����(KiES����{�D�=�;�!=$�rE�����{gy&�iu���C��Q��li����{��2Z��|��29�Bg�%,���t����ۋd�_�G��e��H.f��p\���ܐ�E��㐖k��(���鰆�KV����n莲zy;q�Z�8Y�r1 U�	��%��5NT������~f�mC��.ɀр��*�d7�}�k�FxUMo���r_r悖��C��&�AB��x�&F���N�|����xmF@�Д��rn�ý�������%���3�</�6��Ό�3��׷��O�������Ba{����r�m��p��u0|�dk��t�oc�Îqa�=��2��[9 ̄�BP2GF�6Nj�5�-��M��Xf��El�[V[�F��8�.t�\��;砪2���V`?��O����;	�8/�Z�-'�(=q}�KZc`W�s`�uڱ`O#���'�$���7���{}�Y����{�tc�~�S������g�n��ke�����������!      �   �   x�}�1�1 ��+�>��ر �QP�8�#!q����,5�L1ҰW�H���TAKPI�XS���������j������˛BO�����2�:*��j�����L�÷D���6��#B�1�z����3�H��3q_7ao����
����Bj���[��(Q�L<绯��N?!�'Q�      �   �   x����C!E�u셌(O��l@�����w��Ŵ��I`�q��5%�	=�1�!I\���i[�i���uҬ#���$p'�2rsa힣�s��6�|�r����ěmő�O�z�A�l%�ߢ(�<X(�.�1P��n�}rT�      �   �   x�ιq�0@�B	k`/��@^�dI�v;�]5�����Cb�D{s�*B� ˗������+� �D�����?�]�I�$����	�PAs+ur�5�,#�L�#�q�fwW�(����g��`�-�C��w�6�J�ʹ�@��E��ʨ9�����������74      �      x������ � �     