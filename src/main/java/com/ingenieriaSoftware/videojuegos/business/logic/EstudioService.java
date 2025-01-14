package com.ingenieriaSoftware.videojuegos.business.logic;

import com.ingenieriaSoftware.videojuegos.business.domain.Estudio;
import com.ingenieriaSoftware.videojuegos.business.domain.Videojuego;
import com.ingenieriaSoftware.videojuegos.business.repository.EstudioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.UUID;

/**
 * Servicio de estudio
 * @version 1.0.0
 * @author Tomas Rando
 */
@Service
public class EstudioService {

    private final EstudioRepository estudioRepository;

    @Autowired
    public EstudioService(EstudioRepository estudioRepository) {
        this.estudioRepository = estudioRepository;
    }

    /**
     * Crea el estudio con el nombre especificado
     * @param nombre String
     * @throws ErrorServiceException
     */
    public void crearEstudio(String nombre) throws ErrorServiceException {
        try {

            if (nombre == null || nombre.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el nombre");
            }

            if (estudioRepository.buscarEstudioPorNombre(nombre) != null) {
                throw new ErrorServiceException("El estudio ya existe");
            }

            Estudio estudio = new Estudio();
            estudio.setId(UUID.randomUUID().toString());
            estudio.setNombre(nombre);
            estudio.setActivo(true);

            estudioRepository.save(estudio);
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServiceException("Error de sistemas");
        }
    }

    /**
     * Busca al objeto en la base de datos y lo devuelve
     * @param idEstudio String
     * @return Estudio
     * @throws ErrorServiceException
     */
    public Estudio buscarEstudio(String idEstudio) throws ErrorServiceException {
        Estudio estudio;
        try {
            if (idEstudio == null || idEstudio.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el id");
            }
            Optional optional = estudioRepository.findById(idEstudio);

            if (optional.isPresent()) {
                estudio = (Estudio) optional.get();
            } else {
                throw new ErrorServiceException("El estudio no existe");
            }

            if (!estudio.isActivo()) {
                throw new ErrorServiceException("El estudio no existe");
            }

            return estudio;
        } catch (ErrorServiceException e) {
            throw e;
        } catch (Exception e) {
            throw new ErrorServiceException("Error de sistemas");
        }
    }

    /**
     * Elimina el objeto de la base de datos
     * @param idEstudio String
     * @throws ErrorServiceException
     */
    public void eliminarEstudio(String idEstudio) throws ErrorServiceException {
        try {
            if (idEstudio == null || idEstudio.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el id");
            }
            Estudio estudio = buscarEstudio(idEstudio);
            estudio.setActivo(false);
            estudioRepository.save(estudio);
        } catch (ErrorServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ErrorServiceException("Error de sistemas");
        }
    }

    /**
     * Lista los estudios
     * @return Collection
     * @throws ErrorServiceException
     */
    public Collection<Estudio> listarEstudios() throws ErrorServiceException {
        try {
            return estudioRepository.listarEstudioActivo();
        } catch (Exception ex) {
            throw new ErrorServiceException("Error de sistema");
        }
    }

    /**
     * Modifica el estudio con el nuevo nombre
     * @param id String
     * @param nombre String
     * @throws ErrorServiceException
     */
    public void modificarEstudio(String id, String nombre) throws ErrorServiceException {
        try {
            if (id == null || id.isEmpty()) {
                throw new ErrorServiceException("Debe indicar el id");
            }
            if (nombre == null || nombre.isEmpty()) {
                throw new ErrorServiceException("Debe indicar un nombre");
            }

            Estudio estudio = buscarEstudio(id);

            Estudio estudioAux = estudioRepository.buscarEstudioPorNombre(nombre);
            if (estudioAux != null) {
                if (!estudioAux.getId().equals(id)) {
                    throw new ErrorServiceException("Ya existe un estudio con ese nombre");
                }
            }
            estudio.setNombre(nombre);
            estudioRepository.save(estudio);

        } catch (ErrorServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new ErrorServiceException("Error de sistemas");
        }
    }

    //No se agrega método de verificación por ser pocos atributos
}