DO
$$
BEGIN
CREATE SCHEMA IF NOT EXISTS ip_geofinder_schema;
ALTER SCHEMA ip_geofinder_schema
  OWNER TO ip_geofinder_user;
  GRANT ALL PRIVILEGES ON SCHEMA ip_geofinder_schema TO ip_geofinder_user;
  GRANT ALL PRIVILEGES ON SCHEMA public TO ip_geofinder_user;
  GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA ip_geofinder_schema TO ip_geofinder_user;
  GRANT ALL PRIVILEGES ON ALL SEQUENCES IN SCHEMA ip_geofinder_schema TO ip_geofinder_user;
END
$$;