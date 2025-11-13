package com.example.login.repositories;

import com.example.login.models.Movements;
import com.example.login.models.User;

import java.util.ArrayList;

public class MovementsRepository {

    private static MovementsRepository instancia;
    private static ArrayList<Movements> movements;

    private MovementsRepository() {
        movements = new ArrayList<>();

    }

    /**
     * Obtiene la instancia única del repositorio
     */
    public static MovementsRepository getInstancia() {
        if (instancia == null) {
            instancia = new MovementsRepository();
        }
        return instancia;
    }



    public void add(Movements movement) {
        movements.add(movement);
    }

    public ArrayList<Movements> getAll() {
        return movements;
    }

    public ArrayList<Movements> getByClient(String clientId) {
        ArrayList<Movements> result = new ArrayList<>();
        for (Movements move : movements) {
            if (move.getClientId().equals(clientId)) {
                result.add(move);
            }
        }
        return result;
    }
}