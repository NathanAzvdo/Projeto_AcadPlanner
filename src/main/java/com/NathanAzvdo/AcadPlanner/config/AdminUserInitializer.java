package com.NathanAzvdo.AcadPlanner.config;

import com.NathanAzvdo.AcadPlanner.entities.Course;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.entities.UserRole;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import com.NathanAzvdo.AcadPlanner.services.CourseService; // 1. IMPORTAR O SERVICE
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class AdminUserInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final CourseService courseService; // 2. ADICIONAR O SERVICE

    @Value("${APP_ADMIN_EMAIL:admin@acadplanner.com}")
    private String adminEmail;

    @Value("${APP_ADMIN_PASSWORD:admin123}")
    private String adminPassword;


    // 3. INJETAR O SERVICE NO CONSTRUTOR
    public AdminUserInitializer(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                CourseService courseService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.courseService = courseService;
    }

    @Override
    public void run(String... args) throws Exception {

        // --- 4. LÓGICA DO CURSO (USANDO O SERVICE) ---
        String defaultCursoName = "Curso Padrão";
        Optional<Object> cursoOpt = courseService.findByNome(defaultCursoName);

        Course adminCourse;
        if (cursoOpt.isEmpty()) {
            // Se não existir, cria um via service
            System.out.println(">>> Curso '" + defaultCursoName + "' não encontrado, criando...");
            Course novoCourse = Course.builder()
                    .name(defaultCursoName)
                    .description("Curso padrão para usuários administrativos.")
                    .build();
            adminCourse = courseService.save(novoCourse); // Usa o save do service
        } else {
            // Se existir, usa ele
            adminCourse = (Course) cursoOpt.get();
        }
        // --- FIM LÓGICA CURSO ---


        // --- 5. LÓGICA DO ADMIN ---
        // Corrigido: Precisamos da entidade User, não da interface UserDetails
        Optional<UserDetails> adminUserOpt = userRepository.findByEmail(adminEmail);

        if (adminUserOpt.isEmpty()) {
            User adminUser = new User();
            adminUser.setName("Admin Padrão");
            adminUser.setEmail(adminEmail);
            adminUser.setPassword(passwordEncoder.encode(adminPassword));
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setCourse(adminCourse); // 6. SETAR O CURSO ENCONTRADO/CRIADO

            userRepository.save(adminUser);
            System.out.println(">>> Usuário ADMIN '" + adminEmail + "' criado com sucesso!");
        } else {
            System.out.println(">>> Usuário ADMIN '" + adminEmail + "' já existe.");
        }
    }
}