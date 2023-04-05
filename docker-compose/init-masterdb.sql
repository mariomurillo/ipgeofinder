CREATE EXTENSION IF NOT EXISTS pglogical;

SELECT pglogical.create_node(
               node_name := 'master',
               dsn := 'user=ip_geofinder_user password=12345 dbname=ip_geofinder host=master_bd port=5432'
           );
