DROP DATABASE IF EXISTS db_api_voll_med;
CREATE DATABASE IF NOT EXISTS db_api_voll_med;
USE db_api_voll_med;

SELECT * FROM tb_usuario;
SELECT * FROM tb_medico;
SELECT * FROM tb_paciente;
SELECT * FROM tb_consulta;

INSERT INTO tb_usuario
values (1, 'vinicius_andrade2010@hotmail.com', '$2a$10$Y50UaMFOxteibQEYLrwuHeehHYfcoafCopUazP12.rqB41bsolF5.');