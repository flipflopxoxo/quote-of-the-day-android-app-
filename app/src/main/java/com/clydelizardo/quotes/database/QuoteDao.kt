package com.clydelizardo.quotes.database

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Transaction
import com.clydelizardo.quotes.database.model.Quote
import com.clydelizardo.quotes.database.model.QuoteWithTag
import com.clydelizardo.quotes.database.model.Tag
import kotlinx.coroutines.flow.Flow

@Dao
interface QuoteDao {
    @Query("SELECT * from quote WHERE id = :id")
    suspend fun getQuote(id: String): Quote

    @Transaction
    @Query("SELECT * FROM quote ORDER BY saveTimestamp")
    fun observableQuoteWithTag(): Flow<List<QuoteWithTag>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun saveQuote(quote: Quote)

    @Delete
    suspend fun deleteQuote(quote: Quote)

    @Delete
    suspend fun deleteQuoteList(quote: List<Quote>)

    @Insert
    suspend fun saveTags(tags: List<Tag>)

    @Delete
    suspend fun deleteTags(tags: List<Tag>)

    @Transaction
    suspend fun saveQuoteWithTag(quote: Quote, tags: List<Tag>) {
        saveQuote(quote)
        saveTags(tags)
    }

    @Transaction
    suspend fun deleteQuoteAndTags(quoteId: Int) {
        deleteQuotesWithId(quoteId)
        deleteTagsWithId(quoteId)
    }

    @Query("DELETE from quote WHERE id in (:idList)")
    fun deleteQuotesWithId(vararg idList: Int)

    @Query("DELETE from tag WHERE quoteId in (:idList)")
    fun deleteTagsWithId(vararg idList: Int)


    @Transaction
    suspend fun deleteQuoteAndTagList(
        quoteIdList: List<Int>,
    ) {
        val idList = quoteIdList.toIntArray()
        deleteQuotesWithId(* idList)
        deleteTagsWithId(* idList)
    }
}