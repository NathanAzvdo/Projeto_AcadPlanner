
INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE m.nome IN (
    'Português Instrumental',
    'Fundamentos da Contabilidade',
    'Metodologia da Pesquisa',
    'Filosofia e Ética',
    'Estatística e Probabilidade',
    'Inferência Estatística',
    'Introdução à Lógica',
    'Noções de Engenharia de Software',
    'Gerência de Projetos',
    'Empreendedorismo e Gestão Estratégica'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT 1, m.id
FROM materia m
WHERE m.nome IN (
    'Análise de Sistemas I',
    'Análise de Sistemas II',
    'Projeto de Sistemas I',
    'Projeto de Sistemas II',
    'Programação de Computadores III',
    'Banco de Dados III',
    'Desenvolvimento de Sistemas I',
    'Desenvolvimento de Sistemas II',
    'UX e Desenvolvimento Web',
    'Desenvolvimento para Dispositivos Móveis',
    'Trabalho de Conclusão de Curso I',
    'Trabalho de Conclusão de Curso II',
    'Estágio Supervisionado I',
    'Estágio Supervisionado II'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE c.nome IN ('Sistemas de Informação', 'Engenharia de Produção')
  AND m.nome IN (
    'Noções Básicas de Programação',
    'Programação de Computadores I',
    'Programação de Computadores II',
    'Pesquisa Operacional I'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE c.nome IN ('Sistemas de Informação', 'Matemática')
  AND m.nome IN (
    'Matemática Discreta',
    'Introdução à Redes de Computadores'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE c.nome IN ('Sistemas de Informação', 'Engenharia de Produção', 'Matemática')
  AND m.nome IN (
    'Álgebra Linear'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE c.nome IN ('Sistemas de Informação', 'Engenharia de Produção', 'Administração')
  AND m.nome IN (
    'Gerência de Sistemas e Projetos',
    'Sistemas de Informação Gerenciais'
);


INSERT INTO curso_materia (curso_id, materia_id)
SELECT 1, m.id
FROM materia m
WHERE m.nome IN (
    'Banco de Dados I',
    'Banco de Dados II',
    'Sistemas Operacionais I',
    'Sistemas Operacionais II',
    'Organização de Computadores I',
    'Organização de Computadores II',
    'Qualidade de Software',
    'Gestão de Redes',
    'Trabalho de Conclusão de Curso I',
    'Trabalho de Conclusão de Curso II',
    'Estágio Supervisionado I',
    'Estágio Supervisionado II'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE m.nome IN (
    'Fundamentos da Contabilidade',
    'Filosofia e Ética'
);

INSERT INTO curso_materia (curso_id, materia_id)
SELECT c.id, m.id
FROM curso c, materia m
WHERE m.nome IN (
    'Metodologia da Pesquisa',
    'Português Instrumental',
    'Estatística e Probabilidade'
);
