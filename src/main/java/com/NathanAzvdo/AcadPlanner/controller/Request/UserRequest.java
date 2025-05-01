package com.NathanAzvdo.AcadPlanner.controller.Request;

public record UserRequest(String nome,String senha, String email, boolean admin, CursoRequest curso){
}
