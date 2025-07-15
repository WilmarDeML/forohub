insert into usuarios (id, nombre, correo_electronico, contrasena)
    values (1, 'Test', 'test@gmail.com', '$2a$10$GUst8KFGFiHqmkOg4GOopeY9N56b2hy0CukwM.3JvcuQSaTLL/Tui');

insert into usuarios_perfiles (id, usuario_id, perfil_id)
    values (1, 1, 1), (2, 1, 2), (3, 1, 3);