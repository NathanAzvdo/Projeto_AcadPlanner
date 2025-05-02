package com.NathanAzvdo.AcadPlanner.controller.Request;

import com.NathanAzvdo.AcadPlanner.entity.UserRole;

public record UserRequest(String nome, String senha, String email, CursoRequest curso){
}
