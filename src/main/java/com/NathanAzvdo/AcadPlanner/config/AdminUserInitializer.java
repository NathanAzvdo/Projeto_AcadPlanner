package com.NathanAzvdo.AcadPlanner.config;

import com.NathanAzvdo.AcadPlanner.entities.Curso;
import com.NathanAzvdo.AcadPlanner.entities.User;
import com.NathanAzvdo.AcadPlanner.entities.UserRole;
import com.NathanAzvdo.AcadPlanner.repositories.UserRepository;
import com.NathanAzvdo.AcadPlanner.services.CursoService; // 1. IMPORTAR O SERVICE
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
    private final CursoService cursoService; // 2. ADICIONAR O SERVICE

    @Value("${APP_ADMIN_EMAIL:admin@acadplanner.com}")
    private String adminEmail;

    @Value("${APP_ADMIN_PASSWORD:admin123}")
    private String adminPassword;


    // 3. INJETAR O SERVICE NO CONSTRUTOR
    public AdminUserInitializer(UserRepository userRepository,
                                PasswordEncoder passwordEncoder,
                                CursoService cursoService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.cursoService = cursoService;
    }

    @Override
    public void run(String... args) throws Exception {

        // --- 4. LÓGICA DO CURSO (USANDO O SERVICE) ---
        String defaultCursoName = "Curso Padrão";
        Optional<Object> cursoOpt = cursoService.findByNome(defaultCursoName);

        Curso adminCurso;
        if (cursoOpt.isEmpty()) {
            // Se não existir, cria um via service
            System.out.println(">>> Curso '" + defaultCursoName + "' não encontrado, criando...");
            Curso novoCurso = Curso.builder()
                    .nome(defaultCursoName)
                    .descricao("Curso padrão para usuários administrativos.")
                    .build();
            adminCurso = cursoService.save(novoCurso); // Usa o save do service
        } else {
            // Se existir, usa ele
            adminCurso = (Curso) cursoOpt.get();
        }
        // --- FIM LÓGICA CURSO ---


        // --- 5. LÓGICA DO ADMIN ---
        // Corrigido: Precisamos da entidade User, não da interface UserDetails
        Optional<UserDetails> adminUserOpt = userRepository.findByEmail(adminEmail);

        if (adminUserOpt.isEmpty()) {
            User adminUser = new User();
            adminUser.setNome("Admin Padrão");
            adminUser.setEmail(adminEmail);
            adminUser.setSenha(passwordEncoder.encode(adminPassword));
            adminUser.setRole(UserRole.ADMIN);
            adminUser.setCurso(adminCurso); // 6. SETAR O CURSO ENCONTRADO/CRIADO

            userRepository.save(adminUser);
            System.out.println(">>> Usuário ADMIN '" + adminEmail + "' criado com sucesso!");
        } else {
            System.out.println(">>> Usuário ADMIN '" + adminEmail + "' já existe.");
        }
    }
}