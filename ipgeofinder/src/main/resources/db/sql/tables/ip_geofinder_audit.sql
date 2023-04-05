-- object: ip_geofinder_schema.audit_log | type: TABLE --
-- DROP TABLE IF EXISTS ip_geofinder_schema.audit_log CASCADE;
CREATE TABLE ip_geofinder_schema.audit_log (
                                             id uuid NOT NULL,
                                             event_timestamp timestamp NOT NULL,
                                             source varchar(255) NOT NULL,
                                             event_type varchar(255) NOT NULL,
                                             event_data jsonb NOT NULL,
                                             CONSTRAINT audit_log_pkey PRIMARY KEY (id)
);
-- ddl-end --
ALTER TABLE ip_geofinder_schema.audit_log OWNER TO ip_geofinder_user;
-- ddl-end --
