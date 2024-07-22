package com.ucne.geekmarket.data.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import com.ucne.geekmarket.data.local.entities.ItemEntity
import com.ucne.geekmarket.data.local.entities.PersonaEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface ItemDao {
    @Upsert
    suspend fun save(item: ItemEntity)

    @Delete
    suspend fun delete(pitem: ItemEntity)

    @Query(
        """
            SELECT * 
            FROM Items
            WHERE itemId = :id
            LIMIT 1
        """
    )
    suspend fun find(id: Int): ItemEntity?
    @Query(
        """
            SELECT * 
            FROM Items
            WHERE carritoId = :id
        """
    )
    suspend fun CarritoItem(id: Int): List<ItemEntity>?

    @Query("SELECT * FROM Items")
    fun getAll(): Flow<List<ItemEntity>>

}