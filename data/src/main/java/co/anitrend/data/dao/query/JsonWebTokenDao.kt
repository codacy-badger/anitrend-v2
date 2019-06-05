package co.anitrend.data.dao.query

import androidx.room.Dao
import androidx.room.Query
import co.anitrend.data.auth.model.JsonWebToken
import io.wax911.support.data.dao.ISupportQuery

@Dao
interface JsonWebTokenDao: ISupportQuery<JsonWebToken?> {

    @Query("select count(id) from JsonWebToken")
    fun count(): Int

    @Query("select * from JsonWebToken order by id asc limit 1")
    fun findLatest(): JsonWebToken?

    @Query("select * from JsonWebToken limit :limit offset :offset")
    fun findAll(offset: Int, limit: Int): List<JsonWebToken>?

    @Query("delete from JsonWebToken")
    fun clearTable()
}