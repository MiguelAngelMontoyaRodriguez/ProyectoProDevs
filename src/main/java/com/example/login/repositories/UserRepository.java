package com.example.login.repositories;

import com.example.login.models.Admin;
import com.example.login.models.Cashier;
import com.example.login.models.Client;
import com.example.login.models.User;

import java.util.ArrayList;

public class UserRepository {

        private static UserRepository instancia;
        private static ArrayList<User> user;

        private UserRepository() {
            user = new ArrayList<>();

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
     * Obtiene la cantidad de Usuarios
     */
    public int getAmountUsers() {
        return user.size();

    }

    /**
     * Carga algunos productos de ejemplo
     */
    public void cargarDatosEjemplo() {
        user.add(new Admin("RickPichon@gmail.com", "panConQueso","Rick", "1242342342", "dsf34t3g435"));
        user.add(new Cashier("caro@gmail.com", "0606", "0128", "Diurno"));
        user.add(new Client("pablito@gmail.com", "12345","luis", "alexander", "123123", "321","corriente", 5000));
    }

    /*
    *Valida que el usuario este en el arrayList
     */

    public static boolean searchUser(String Email, String password){
        boolean flag = false;
        for(User users : user){
            if(Email.equals(users.getEmail()) && password.equals(users.getPassword())){
                flag= true;
                break;
            }
        }
        return flag;
    }


    /*
    busca el usuario logueado
     */
    public User login(String Email, String password) {
        for (User users : user) {
            if(Email.equals(users.getEmail()) && password.equals(users.getPassword())){
                return users;
            }
        }
        return null;
    }

    /**
     * Busca un Cliente por Correo
     */
    public User searchForEmail(String Email) {
        return user.stream()
                .filter(p -> p.getEmail().equals(Email))
                .findFirst()
                .orElse(null);
    }

    /**
     * Elimina un Usuario
     */
    public boolean eliminateUser(User user) {

        return this.user.remove(user);

    }

    /**
     * Agrega un nuevo Usuario
     */
    public void addUser(User user) {

        this.user.add(user);

    }

    /*
    Obtiene usuario segun el rol
     */
    public ArrayList<User> getByRole(int role) {
        ArrayList<User> result = new ArrayList<>();
        for (User users : user) {
            if (role == users.getRole()){
                result.add(users);
            }
        }
        return result;
    }

    public void updateClient(Client updatedClient) {
        for (int i = 0; i < user.size(); i++) {
            User users = user.get(i);
            if (users.getRole() == 3 && ((Client) users).getId().equals(updatedClient.getId())) {
                user.set(i, updatedClient);
                break;
            }
        }
    }


}
