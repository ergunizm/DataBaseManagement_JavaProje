CREATE TYPE public.mat AS (
	mid character varying(9),
	comp_id character varying(9),
	typeof character varying(20),
	barcode character varying(12),
	price double precision
);

CREATE FUNCTION public.add_org_to_user(org integer, user_ssn character varying) RETURNS text
    LANGUAGE plpgsql
    AS $$
declare 
begin
	update organizations set availability = false where oid = org;
	update users set org_id = org where ssn=user_ssn;
	
	return org::TEXT || ' is added to ' || user_ssn;
end;
	
$$;

CREATE FUNCTION public.material_priceof_user(ssn character varying) RETURNS double precision
    LANGUAGE plpgsql
    AS $$
declare 
	total_price double precision;
begin
	select sum(price) into total_price
	from owned_materials, materials
	where user_ssn=ssn and material_id=mid;
	return total_price;
end;
$$;

CREATE FUNCTION public.materialsof_user(ssn character varying) RETURNS public.mat[]
    LANGUAGE plpgsql
    AS $$
declare 
	matsofuser_cursor cursor for select mid, comp_id, mtype,barcode,price from owned_materials,materials where user_ssn=ssn and material_id=mid;
	mats mat[];
	i integer;
begin
	i := 1; 
	for tmpmat in matsofuser_cursor loop
			mats[i] = tmpmat; 
			i := i + 1;
	end loop;
	return mats;
end;
$$;

CREATE FUNCTION public.update_material_id() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
	if new.mid <> old.mid then
        UPDATE owned_materials
        SET material_id = new.mid
        WHERE material_id = old.mid;
    end if;
	raise notice 'Material ID guncellendi';
	
	return new;
END;
$$;

CREATE FUNCTION public.update_users_organization() RETURNS trigger
    LANGUAGE plpgsql
    AS $$
BEGIN
    IF TG_OP = 'UPDATE' or TG_OP='DELETE' THEN
        UPDATE users
        SET org_id = NULL
        WHERE org_id = OLD.oid;
    END IF;
	
	RAISE NOTICE 'Deleted from users who own this organization!';

    RETURN NEW;
END;
$$;

CREATE TABLE public.company (
    cid character varying(9) NOT NULL,
    cname character varying(20)
);

CREATE TABLE public.owned_materials (
    user_ssn character varying(9),
    material_id character varying(9)
);

CREATE VIEW public.materialnumberofusers AS
 SELECT owned_materials.user_ssn,
    count(*) AS count
   FROM public.owned_materials
  GROUP BY owned_materials.user_ssn
 HAVING (count(*) > 0);


CREATE TABLE public.materials (
    mid character varying(9) NOT NULL,
    comp_id character varying(9),
    mtype character varying(20),
    barcode character varying(12),
    price double precision
);

CREATE TABLE public.organizations (
    oid integer NOT NULL,
    comp_id character varying(9),
    otype character varying(20),
    glimit integer,
    season character varying(15),
    price double precision,
    orgdate integer,
    availability boolean,
    CONSTRAINT chk_year CHECK ((orgdate > 2023))
);

CREATE SEQUENCE public.organizations_oid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE SEQUENCE public.organizations_oid_seq
    AS integer
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;


CREATE TABLE public.users (
    ssn character varying(9) NOT NULL,
    fname character varying(20) NOT NULL,
    lname character varying(20) NOT NULL,
    org_id integer,
    pass character varying(20)
);


ALTER TABLE ONLY public.organizations ALTER COLUMN oid SET DEFAULT nextval('public.organizations_oid_seq'::regclass);



COPY public.company (cid, cname) FROM stdin;
hasanmu	Hasan Mustan
sesegel	Sese Doğru
eew	Enterprise E Win
ups	Uf Program System
trendyol	Popüler Yol
getir	Getir Yemek
erenkoca	Eren Kocabaş Anonim
furkani	Furkan Bayraklı
uygurlar	uygurlar
upg	upgp gpg
\.



COPY public.materials (mid, comp_id, mtype, barcode, price) FROM stdin;
sus1	upg	sus	12345	50
sus2	erenkoca	sus	12346	55
sus3	getir	sus	12348	70
sus4	erenkoca	sus	12349	20.5
sus5	ups	sus	12340	60.5
sapka1	ups	sapka	12370	60.5
sapka2	erenkoca	sapka	12370	70.5
sapka3	hasanmu	sapka	12376	20.5
m1	hasanmu	mesale	12379	50.5
m2	hasanmu	mesale	12378	120.7
\.



COPY public.organizations (oid, comp_id, otype, glimit, season, price, orgdate, availability) FROM stdin;
5	ups	birthday	200	spring	1000	2026	t
8	erenkoca	birthday	250	spring	2500	2025	t
10	furkani	birthday	200	spring	900	2024	t
11	upg	wedding	600	summer	1500	2024	t
1	hasanmu	wedding	1000	summer	1000	2024	t
2	sesegel	birthday	500	winter	1000	2026	f
4	eew	graduation	200	fall	800	2024	f
6	trendyol	birthday	1000	winter	5000	2024	f
7	getir	graduation	1000	summer	1000	2025	f
9	uygurlar	graduation	500	spring	500	2024	f
\.



COPY public.owned_materials (user_ssn, material_id) FROM stdin;
ergunizm	sapka1
ergunizm	sapka2
ergunizm	sus1
abyilmaz	sus2
abyilmaz	sapka2
abyilmaz	m1
furkan	sus1
furkan	sus5
vtoroytom	sus4
yusufis	m2
\.



COPY public.users (ssn, fname, lname, org_id, pass) FROM stdin;
eren123	eren	colak	0	123456
turabi123	turabi	çamkıran	0	1234
yusufis	yusuf	guney	0	1234
abyilmaz	ahmet	buyuk	2	12345
ahmeteren	ahmet	eren	4	1234
erenayde	eren	kocabaş	6	0000
ergun1234	ergun	izmirli	11	12345
ergunizm	Ergün	İzmirlioğlu	7	1234
furkanb	Furkan	Bayraklı	9	1234
vtoroytom	selin	cirak	0	1234
\.



SELECT pg_catalog.setval('public.organizations_oid', 1, false);



SELECT pg_catalog.setval('public.organizations_oid_seq', 11, true);



ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_pkey PRIMARY KEY (cid);



ALTER TABLE ONLY public.materials
    ADD CONSTRAINT materials_pkey PRIMARY KEY (mid);



ALTER TABLE ONLY public.organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (oid);


ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (ssn);
	
ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_fkey FOREIGN KEY (cid) REFERENCES organizations(comp_id) 
	ON DELETE CASCADE ON UPDATE SET NULL;


CREATE TRIGGER trigger1 AFTER UPDATE ON public.materials FOR EACH ROW EXECUTE FUNCTION public.update_material_id();



CREATE TRIGGER trigger2 AFTER UPDATE ON public.organizations FOR EACH ROW EXECUTE FUNCTION public.update_users_organization();
