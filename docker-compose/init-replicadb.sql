CREATE EXTENSION IF NOT EXISTS pglogical;

SELECT pglogical.create_node(
               node_name := 'replica',
               dsn := 'user=ip_geofinder_user password=12345 dbname=ip_geofinder host=standby1 port=5432'
           );