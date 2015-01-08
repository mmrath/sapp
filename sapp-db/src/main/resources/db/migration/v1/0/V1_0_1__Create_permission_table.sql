CREATE TABLE t_permission 
  ( 
     id              BIGINT NOT NULL, 
     access_level_id INTEGER NOT NULL, 
     module_id       INTEGER NOT NULL, 
     NAME            VARCHAR(255) NOT NULL, 
     PRIMARY KEY (id) 
  ); 

ALTER TABLE t_permission 
  ADD CONSTRAINT uk_permission_01 UNIQUE (module_id, access_level_id); 

ALTER TABLE t_permission 
  ADD CONSTRAINT uk_permission_02 UNIQUE (NAME); 
