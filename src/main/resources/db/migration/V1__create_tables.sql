
CREATE TABLE curso (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT
);

CREATE TABLE usuario (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) UNIQUE NOT NULL,
    senha VARCHAR(255) NOT NULL,
    admin BOOLEAN DEFAULT FALSE,
    curso_id INT REFERENCES curso(id)
);

CREATE TABLE materia (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    descricao TEXT,
    creditos INT NOT NULL
);


CREATE TABLE curso_materia (
    id SERIAL PRIMARY KEY,
    curso_id INT REFERENCES curso(id) ON DELETE CASCADE,
    materia_id INT REFERENCES materia(id) ON DELETE CASCADE
);


CREATE TABLE materia_pre_requisito (
    id SERIAL PRIMARY KEY,
    materia_id INT REFERENCES materia(id) ON DELETE CASCADE,
    pre_requisito_id INT REFERENCES materia(id) ON DELETE CASCADE
);


CREATE TABLE materias_concluidas (
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    materia_id INT REFERENCES materia(id) ON DELETE CASCADE,
    data_conclusao DATE NOT NULL,
    PRIMARY KEY (usuario_id, materia_id)
);


CREATE TABLE materias_em_andamento (
    usuario_id INT REFERENCES usuario(id) ON DELETE CASCADE,
    materia_id INT REFERENCES materia(id) ON DELETE CASCADE,
    data_inicio DATE NOT NULL,
    previsao_conclusao DATE,
    PRIMARY KEY (usuario_id, materia_id)
);
