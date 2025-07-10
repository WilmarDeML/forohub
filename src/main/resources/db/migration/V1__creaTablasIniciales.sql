create table usuarios (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    correo_electronico varchar(100) not null unique,
    contrasena varchar(255) not null,

    primary key(id)
);

create table cursos (
    id bigint not null auto_increment,
    nombre varchar(100) not null,
    categoria varchar(100) not null,

    primary key(id)
);

create table topicos (
    id bigint not null auto_increment,
    titulo varchar(100) not null,
    mensaje varchar(100) not null,
    fecha_creacion datetime not null,
    status tinyint not null,
    usuario_id bigint not null,
    curso_id bigint not null,

    primary key(id),
    constraint fk_topicos_usuario_id foreign key(usuario_id) references usuarios(id),
    constraint fk_topicos_curso_id foreign key(curso_id) references cursos(id)
);

create table respuestas (
    id bigint not null auto_increment,
    mensaje varchar(100) not null,
    topico_id bigint not null,
    fecha_creacion datetime not null,
    usuario_id bigint not null,
    solucion tinyint not null,

    primary key(id),
    constraint fk_respuestas_topico_id foreign key(topico_id) references topicos(id),
    constraint fk_respuestas_usuario_id foreign key(usuario_id) references usuarios(id)
);

create table perfiles (
    id bigint not null auto_increment,
    nombre varchar(100) not null unique,

    primary key(id)
);