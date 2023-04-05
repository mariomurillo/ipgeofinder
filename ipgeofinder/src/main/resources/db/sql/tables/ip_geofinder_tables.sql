-- object: ip_geofinder_schema.country | type: TABLE --
-- DROP TABLE IF EXISTS ip_geofinder_schema.country CASCADE;
CREATE TABLE ip_geofinder_schema.country (
                                             id uuid NOT NULL,
                                             code varchar(2),
                                             name varchar(255),
                                             CONSTRAINT country_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ip_geofinder_schema.country OWNER TO ip_geofinder_user;
-- ddl-end --

-- object: ip_geofinder_schema.region | type: TABLE --
-- DROP TABLE IF EXISTS ip_geofinder_schema.region CASCADE;
CREATE TABLE ip_geofinder_schema.region (
                                            id uuid NOT NULL,
                                            name varchar,
                                            country_id uuid,
                                            CONSTRAINT region_pkey PRIMARY KEY (id),
                                            CONSTRAINT region_country_fk FOREIGN KEY (country_Id) REFERENCES ip_geofinder_schema.country(id)
);
-- ddl-end --
ALTER TABLE ip_geofinder_schema.region OWNER TO ip_geofinder_user;
-- ddl-end --

-- object: ip_geofinder_schema.city | type: TABLE --
-- DROP TABLE IF EXISTS ip_geofinder_schema.city CASCADE;
CREATE TABLE ip_geofinder_schema.city (
                                          id uuid NOT NULL,
                                          name varchar(50),
                                          region_id uuid,
                                          CONSTRAINT citi_pkey PRIMARY KEY (id),
                                          CONSTRAINT city_region_fk FOREIGN KEY (region_id) REFERENCES ip_geofinder_schema.region (id)
);
-- ddl-end --
ALTER TABLE ip_geofinder_schema.city OWNER TO ip_geofinder_user;
-- ddl-end --

-- object: ip_geofinder_schema.location | type: TABLE --
-- DROP TABLE IF EXISTS ip_geofinder_schema.location CASCADE;
CREATE TABLE ip_geofinder_schema.location (
                                              id uuid NOT NULL,
                                              city_id uuid,
                                              latitude numeric(11,8),
                                              longitude numeric(11,8),
                                              isp varchar(255),
                                              CONSTRAINT location_pkey PRIMARY KEY (id),
                                              CONSTRAINT location_city_fk FOREIGN KEY (city_id) REFERENCES ip_geofinder_schema.city (id)
);
-- ddl-end --
ALTER TABLE ip_geofinder_schema.location OWNER TO ip_geofinder_user;
-- ddl-end --

-- object: ip_geofinder_schema.ip_data | type: TABLE --
-- DROP TABLE IF EXISTS ip_geofinder_schema.ip_data CASCADE;
CREATE TABLE ip_geofinder_schema.ip_data (
                                             id uuid NOT NULL,
                                             ip_from bigint,
                                             ip_to bigint,
                                             location_id uuid,
                                             CONSTRAINT ip_data_pkey PRIMARY KEY (id),
                                             CONSTRAINT ip_data_location_fk FOREIGN KEY (location_id) REFERENCES ip_geofinder_schema.location (id)
);
-- ddl-end --
ALTER TABLE ip_geofinder_schema.ip_data OWNER TO ip_geofinder_user;
-- ddl-end --