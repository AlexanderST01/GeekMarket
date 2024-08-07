package com.ucne.geekmarket.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ucne.geekmarket.data.local.entities.CarritoEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CarritoDao {
    @Upsert
    suspend fun save( carrito: CarritoEntity)

    @Delete
    suspend fun delete(carrito: CarritoEntity)

    @Query(
        """
            SELECT * 
            FROM Carritos
            WHERE carritoId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): CarritoEntity?

    @Query(
        """
            SELECT * 
            FROM Carritos 
            WHERE personaId = (SELECT personaId FROM Personas WHERE email = :email) 
            ORDER BY carritoId DESC 
            LIMIT 1;

        """
    )
    suspend fun getCarritoByPersonaEmail(email: String): CarritoEntity

    @Query("SELECT * FROM carritos WHERE pagado = 0 ORDER BY carritoId DESC LIMIT 1")
    suspend fun getLastCarrito(): CarritoEntity?

    @Query(
        """
            SELECT *
            FROM carritos 
            WHERE pagado = 0 AND personaId = :personaId 
            ORDER BY carritoId DESC 
            LIMIT 1
        """
    )
    suspend fun getLastCarritoByPersona(personaId: Int): CarritoEntity?


    @Query(
        """
           SELECT *
            FROM carritos 
            WHERE personaId in (SELECT personaId FROM personas WHERE email like :email)
            ORDER BY carritoId DESC 
            LIMIT 1;
        """
    )
    fun getLastCarritoByEmail(email: String): Flow<CarritoEntity>

    @Query("SELECT * FROM carritos")
    fun getAll(): Flow<List<CarritoEntity>>

    @Query("SELECT * FROM carritos Where personaId = :personaId")
    fun getAllByPersona(personaId: Int): Flow<List<CarritoEntity>>?


}