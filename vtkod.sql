CREATE TYPE public.mat AS (
	mid character varying(9),
	comp_id character varying(9),
	typeof character varying(20),
	barcode character varying(12),
	price double precision
);

CREATE FUNCTION public.get_org_numberof_company(cid character varying) RETURNS real
    LANGUAGE plpgsql
    AS $$
declare 
	nmb numeric;
begin
	select count(*) into nmb
	from organizations
	where comp_id=cid
	group by comp_id;
	return nmb;
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
  GROUP BY owned_materials.user_ssn;



CREATE TABLE public.materials (
    mid character varying(9) NOT NULL,
    comp_id character varying(9),
    mtype character varying(20),
    barcode character varying(12) UNIQUE,
    price double precision
);


CREATE TABLE public.organizations (
    oid serial NOT NULL,
    comp_id character varying(9),
    otype character varying(20),
    glimit integer,
    season character varying(15),
    price double precision,
    orgdate integer,
    availability boolean
);



CREATE SEQUENCE public.organizations_oid
    START WITH 1
    INCREMENT BY 1
    NO MINVALUE
    NO MAXVALUE
    CACHE 1;



CREATE TABLE public.users (
    ssn character varying(9) NOT NULL,
    fname character varying(20) NOT NULL,
    lname character varying(20) NOT NULL,
    org_id int,
    pass character varying(20)
);


ALTER TABLE ONLY public.company
    ADD CONSTRAINT company_pkey PRIMARY KEY (cid);



ALTER TABLE ONLY public.materials
    ADD CONSTRAINT materials_pkey PRIMARY KEY (mid);



ALTER TABLE ONLY public.organizations
    ADD CONSTRAINT organizations_pkey PRIMARY KEY (oid);



ALTER TABLE ONLY public.users
    ADD CONSTRAINT users_pkey PRIMARY KEY (ssn);



CREATE TRIGGER trigger1 AFTER UPDATE ON public.materials FOR EACH ROW EXECUTE FUNCTION public.update_material_id();



CREATE TRIGGER trigger2 AFTER UPDATE ON public.organizations FOR EACH ROW EXECUTE FUNCTION public.update_users_organization();
