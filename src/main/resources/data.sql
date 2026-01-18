-- 1. Información Personal
INSERT INTO personal_info (first_name, last_name, title, profile_description, profile_image_url, years_of_experience, email, phone, linkedin_url, github_url) VALUES
    ('Angel', 'Iglesias', 'Full Stack Developer Intern', 'Estudiante de Desarrollo de Aplicaciones Web apasionado por el ecosistema Java y Spring Boot. Enfocado en construir arquitecturas backend robustas y escalables, con una base sólida en tecnologías frontend.', 'img/dev-angel.png', 0, 'angel.iglesias@example.com', '+34 600 000 000', 'https://linkedin.com/in/angeliglesias', 'https://github.com/angeliglesias');

-- 2. Tus Habilidades
INSERT INTO skills (name, level_percentage, icon_class, personal_info_id) VALUES
                                                                              ('Java', 85, 'img/logos/java-icon.png', 1),
                                                                              ('Spring Boot', 75, 'img/logos/springboot-icon.png', 1),
                                                                              ('PostgreSQL', 70, 'img/logos/postgre-icon.png', 1),
                                                                              ('HTML5', 90, 'img/logos/html5-icon.png', 1),
                                                                              ('CSS3', 85, 'img/logos/css-icon.png', 1),
                                                                              ('JavaScript', 65, '/img/logos/js-icon.png', 1);

-- 3. Tu Educación (Actualizado: IES Teis)
INSERT INTO educations (degree, institution, start_date, end_date, description, personal_info_id) VALUES
                                                                                                      ('Grado Superior en Desarrollo de Aplicaciones Web (DAW)', 'IES Teis', '2025-09-15', '2027-06-20', 'Formación especializada en desarrollo de software, diseño de interfaces, gestión de bases de datos y despliegue de aplicaciones web.', 1),
                                                                                                      ('Especialización en Spring Boot', 'Curso DevTalles', '2025-10-01', '2026-01-03', 'Desarrollo de microservicios, persistencia de datos con JDBC y despliegue en la nube.', 1);

-- 4. Tu Experiencia
INSERT INTO experiences (job_title, company_name, start_date, end_date, description, personal_info_id) VALUES
    ('Desarrollador Backend Junior (Proyectos)', 'Freelance / Aprendizaje Autónomo', '2025-06-01', NULL, 'Creación de aplicaciones web con Spring Boot, integración de APIs REST y manejo de bases de datos relacionales en la nube.', 1);

-- 5. Proyects
INSERT INTO projects (title, description, image_url, project_url, personal_info_id) VALUES
                                                                                        ('Portfolio Personal', 'Un portafolio web para mostrar mis habilidades y proyectos.', 'img/projects/project2.jpg', 'https://github.com/myusername/my-portfolio', 1),
                                                                                        ('Aplicación de E-commerce', 'Plataforma de comercio electrónico con carrito de compras y pasarela de pago.', 'img/projects/project1.jpg', 'https://github.com/myusername/ecommerce-app', 1);