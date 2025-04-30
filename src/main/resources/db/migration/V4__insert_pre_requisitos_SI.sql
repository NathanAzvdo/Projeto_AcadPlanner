

INSERT INTO materia_pre_requisito (materia_id, pre_requisito_id) VALUES

((SELECT id FROM materia WHERE nome = 'Organização de Computadores I'),
 (SELECT id FROM materia WHERE nome = 'Introdução à Lógica')),

((SELECT id FROM materia WHERE nome = 'Organização de Computadores II'),
 (SELECT id FROM materia WHERE nome = 'Organização de Computadores I')),


((SELECT id FROM materia WHERE nome = 'Gerência de Projetos'),
 (SELECT id FROM materia WHERE nome = 'Organização de Computadores II')),


((SELECT id FROM materia WHERE nome = 'Análise de Sistemas I'),
 (SELECT id FROM materia WHERE nome = 'Noções de Engenharia de Software')),

((SELECT id FROM materia WHERE nome = 'Análise de Sistemas II'),
 (SELECT id FROM materia WHERE nome = 'Análise de Sistemas I')),


((SELECT id FROM materia WHERE nome = 'Projeto de Sistemas I'),
 (SELECT id FROM materia WHERE nome = 'Análise de Sistemas II')),


((SELECT id FROM materia WHERE nome = 'Projeto de Sistemas II'),
 (SELECT id FROM materia WHERE nome = 'Projeto de Sistemas I')),


((SELECT id FROM materia WHERE nome = 'Programação de Computadores III'),
 (SELECT id FROM materia WHERE nome = 'Projeto de Sistemas II')),


((SELECT id FROM materia WHERE nome = 'UX e Desenvolvimento Web'),
 (SELECT id FROM materia WHERE nome = 'Programação de Computadores III')),


((SELECT id FROM materia WHERE nome = 'Desenvolvimento para Dispositivos Móveis'),
 (SELECT id FROM materia WHERE nome = 'UX e Desenvolvimento Web')),


((SELECT id FROM materia WHERE nome = 'Banco de Dados II'),
 (SELECT id FROM materia WHERE nome = 'Banco de Dados I')),


((SELECT id FROM materia WHERE nome = 'Banco de Dados III'),
 (SELECT id FROM materia WHERE nome = 'Banco de Dados II')),


((SELECT id FROM materia WHERE nome = 'Programação de Computadores II'),
 (SELECT id FROM materia WHERE nome = 'Programação de Computadores I')),


((SELECT id FROM materia WHERE nome = 'Estrutura de Dados I'),
 (SELECT id FROM materia WHERE nome = 'Programação de Computadores II')),


((SELECT id FROM materia WHERE nome = 'Estrutura de Dados II'),
 (SELECT id FROM materia WHERE nome = 'Estrutura de Dados I')),

((SELECT id FROM materia WHERE nome = 'Sistemas Operacionais I'),
 (SELECT id FROM materia WHERE nome = 'Estrutura de Dados II')),


((SELECT id FROM materia WHERE nome = 'Sistemas Operacionais II'),
 (SELECT id FROM materia WHERE nome = 'Sistemas Operacionais I')),


((SELECT id FROM materia WHERE nome = 'Empreendedorismo e Gestão Estratégica'),
 (SELECT id FROM materia WHERE nome = 'Sistemas Operacionais II')),


((SELECT id FROM materia WHERE nome = 'Gerência de Sistemas e Projetos'),
 (SELECT id FROM materia WHERE nome = 'Empreendedorismo e Gestão Estratégica')),

((SELECT id FROM materia WHERE nome = 'Trabalho de Conclusão de Curso I'),
 (SELECT id FROM materia WHERE nome = 'Gerência de Sistemas e Projetos')),

((SELECT id FROM materia WHERE nome = 'Trabalho de Conclusão de Curso II'),
 (SELECT id FROM materia WHERE nome = 'Trabalho de Conclusão de Curso I')),

((SELECT id FROM materia WHERE nome = 'Estágio Supervisionado I'),
 (SELECT id FROM materia WHERE nome = 'Trabalho de Conclusão de Curso II')),

((SELECT id FROM materia WHERE nome = 'Estágio Supervisionado II'),
 (SELECT id FROM materia WHERE nome = 'Estágio Supervisionado I')),

((SELECT id FROM materia WHERE nome = 'Pesquisa Operacional I'),
 (SELECT id FROM materia WHERE nome = 'Álgebra Linear')),


((SELECT id FROM materia WHERE nome = 'Gerência de Sistemas e Projetos'),
 (SELECT id FROM materia WHERE nome = 'Pesquisa Operacional I')),

((SELECT id FROM materia WHERE nome = 'Programação de Computadores II'),
 (SELECT id FROM materia WHERE nome = 'Programação de Computadores I')),

((SELECT id FROM materia WHERE nome = 'Programação de Computadores III'),
 (SELECT id FROM materia WHERE nome = 'Programação de Computadores II')),

((SELECT id FROM materia WHERE nome = 'Cálculo Diferencial e Integral B'),
 (SELECT id FROM materia WHERE nome = 'Cálculo Diferencial e Integral A')),


((SELECT id FROM materia WHERE nome = 'Matemática Discreta'),
 (SELECT id FROM materia WHERE nome = 'Cálculo Diferencial e Integral B')),

((SELECT id FROM materia WHERE nome = 'Cálculo Numérico (S.I.)'),
 (SELECT id FROM materia WHERE nome = 'Matemática Discreta')),


((SELECT id FROM materia WHERE nome = 'Inferência Estatística'),
 (SELECT id FROM materia WHERE nome = 'Estatística e Probabilidade')),


((SELECT id FROM materia WHERE nome = 'Trabalho de Conclusão de Curso II'),
 (SELECT id FROM materia WHERE nome = 'Inferência Estatística')),


((SELECT id FROM materia WHERE nome = 'Gestão de Redes'),
 (SELECT id FROM materia WHERE nome = 'Introdução à Redes de Computadores'));
