package com.NathanAzvdo.AcadPlanner.controller.Request;

public record UserRequest(Long id, String nome,String senha, String email, boolean admin, CursoRequest curso){
}
