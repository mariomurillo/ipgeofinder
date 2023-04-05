CREATE INDEX idx_ip_from_to ON ip_geofinder_schema.ip_data USING btree (ip_from, ip_to);
