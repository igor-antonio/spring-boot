SELECT program_name, count(*) AS program_name_count FROM sys.dm_exec_sessions WHERE group_id > 1 AND session_id <> @@SPID GROUP BY program_name;
SELECT * FROM DBSQL42.dbo.utb_control;
SELECT * FROM DBSQL42.dbo.utb_server_list;
SELECT server_name, COUNT(*) AS server_name_count FROM DBSQL42.dbo.utb_sqlserver_start_time GROUP BY server_name ORDER BY server_name;

INSERT INTO DBSQL42.dbo.utb_control(collector_name, is_active, is_running) VALUES('sqlserver_start_time', 1, 1)

UPDATE DBSQL42.dbo.utb_control
SET is_active = 0
WHERE collector_name = 'sqlserver_start_time'

UPDATE DBSQL42.dbo.utb_control
SET is_running = 0
WHERE collector_name = 'sqlserver_start_time'

UPDATE DBSQL42.dbo.utb_control
SET is_active = 1
WHERE collector_name = 'sqlserver_start_time';

UPDATE DBSQL42.dbo.utb_control
SET is_running = 1
WHERE collector_name = 'sqlserver_start_time';

INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql100', '127.0.0.1:14330');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql101', '127.0.0.1:14331');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql102', '127.0.0.1:14332');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql103', '127.0.0.1:14333');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql104', '127.0.0.1:14334');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql105', '127.0.0.1:14335');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql106', '127.0.0.1:14336');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql107', '127.0.0.1:14337');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql108', '127.0.0.1:14338');
INSERT INTO DBSQL42.dbo.utb_server_list(alias, ip_port) VALUES('sql109', '127.0.0.1:14339');
