package com.example.login.repositories;

import com.example.login.models.User;

import java.util.ArrayList;

public class UserRepository {

        private static UserRepository instancia;
        private static ArrayList<User> user;

        private UserRepository() {
            user = new ArrayList<>();
            cargarDatosEjemplo();
        }

    /**
     * Obtiene la instancia única del repositorio
     */
    public static UserRepository getInstancia() {
        if (instancia == null) {
            instancia = new UserRepository();
        }
        return instancia;
    }

    /**
     * Carga algunos productos de ejemplo
     */
    private void cargarDatosEjemplo() {
        user.add(new User("pablito.com", "12345", "2"));
        user.add(new User("azul.com", "67890", "3"));
    }

    /**
     * Obtiene todos los Usuarios
     */
    public ArrayList<User> getUser() {

        return user;
    }

    /**
     * Agrega un nuevo Usuario
     */
    public void addUser(User user) {

        this.user.add(user);

    }

    /**
     * Elimina un Usuario
     */
    public boolean eliminateUser(User user) {

        return this.user.remove(user);

    }

    /**
     * Busca un Usuario por Correo
     */
    public User buscarPorCorreo(String Email) {
        return user.stream()
                .filter(p -> p.getEmail().equals(Email))
                .findFirst()
                .orElse(null);
    }



    /**
     * Obtiene la cantidad de Usuarios
     */
    public int getAmountUsers() {
        return user.size();

    }

    public static int searchUser(String Email, String password){
        int i=0;
        for(User users : user){
            if(Email.equals(users.getEmail())){
                if(password.equals(users.getPassword())){
                    i = 1;
                }
            }
        }
        return i;
    }
}
