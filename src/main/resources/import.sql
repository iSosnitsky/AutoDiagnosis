INSERT INTO USERS (id,login,username,password,role) VALUES (0,'bakunin','bakunin',123456,'USER');
INSERT INTO USE_TYPES (id, name) VALUES (1,'Перорально');
INSERT INTO USE_TYPES (id, name) VALUES (2,'Парэнтерально');
INSERT INTO SYMPTOMS (id,name) VALUES (1,'Отек слизистой носа (аллергический ринит)');
INSERT INTO SYMPTOMS (id,name) VALUES (2,'Покраснение и боли в области конъюктивы (аллергический конъюктивит)');
INSERT INTO SYMPTOMS (id,name) VALUES (3,'Бронхоспазм');
INSERT INTO SYMPTOMS (id,name) VALUES (4,'"Свистящее" дыхание');
INSERT INTO SYMPTOMS (id,name) VALUES (5,'Одышка');
INSERT INTO SYMPTOMS (id,name) VALUES (6,'Приступы астмы');
INSERT INTO SYMPTOMS (id,name) VALUES (7,'Симметричные отеки нижних конечностей от лодыжек до колен');
INSERT INTO SYMPTOMS (id,name) VALUES (8,'Нарушение когнитивных функций');

INSERT INTO MED_TYPES (id,name) VALUES (1,'Антигистаминное');
INSERT INTO MED_TYPES (id,name) VALUES (2,'Блокатор кальциевых каналов');
INSERT INTO MED_TYPES (id,name) VALUES (3,'Диуретик');

INSERT INTO MEDICINES (id, name, type_id,use_type_id,substance_description,description,price) VALUES (1,'Фенкарол',null ,null,'Хинуклидил-3-дифенилкарбинол гидрохлорид','Белый кристаллический порошок без запаха, горьковатого вкуса. Малорастворим в воде и спирте',272)
INSERT INTO MEDICINES (id, name, type_id,use_type_id,substance_description,description,price) VALUES (2,'Фентанил',null ,null,'Промедол','Опиоидные наркотические анальгетики',422);
INSERT INTO MEDICINES (id, name, type_id,use_type_id,substance_description,description,price) VALUES (3,'Нимотоп',2 ,2,'Нимодипин','Раствор для инъекций, профилактика и лечение ишемических неврологических расстройств, вызванных спазмом сосудов головного мозга на фоне субарахноидального кровоизлияния вследствии разрыва аневризмы',500)
INSERT INTO MEDICINES (id, name, type_id,use_type_id,substance_description,description,price) VALUES (4,'Гипотиазид',3 ,1,'Гидрохлортеазид','',700);


INSERT INTO PATHOLOGIES (id,name,description) VALUES (1,'Аллергичиские реакции','');
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (1,1);
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (1,2);
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (1,3);
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (1,4);
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (1,5);
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (1,6);
INSERT INTO pathology_to_medicine (pathology_id,medicine_id) VALUES (1,1)

INSERT INTO PATHOLOGIES (id,name,description) VALUES (2,'Острая ишемия мозга','Нарушения кровоснабжения головного мозга - провоцирует болезни сердца и сосудов');
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (2,8);
INSERT INTO pathology_to_medicine (pathology_id,medicine_id) VALUES(2,3);
INSERT INTO PATHOLOGIES (id,name,description) VALUES (3,'Сердечные отеки','Отечность нижних конечностей в вечернее время');
INSERT INTO pathology_to_symptoms (pathology_id,symptom_id) VALUES (3,7);
INSERT INTO pathology_to_medicine (pathology_id,medicine_id) VALUES (3,4);

