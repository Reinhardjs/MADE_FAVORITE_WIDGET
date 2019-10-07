package com.example.made_final.viewmodel

import android.content.ContentValues
import android.content.Context
import android.net.Uri
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.made_final.Contract
import com.example.made_final.Utility
import com.example.made_final.data.entity.FavoriteEntity
import io.reactivex.Single

open class MovieListViewModel : ViewModel() {

    fun getFavorites(context: Context, tableName: String): Single<List<FavoriteEntity>> {

        val work = Single.create<List<FavoriteEntity>> {

            val selectionArgs: Array<String> = arrayOf("-123", tableName)
            val projection = arrayOf(Contract.AUTHORITY)

            val cursor = context.contentResolver.query(
                    Uri.parse(Contract.CONTENT_URI.toString()), projection, null,
                    selectionArgs, null
            )

            Log.d("MYAPP", "START")

            if (cursor != null) {

                val movieList = Utility.favoriteCursorToListConverter(cursor)
                cursor.close()
                it.onSuccess(movieList)

            } else {

                it.onError(Throwable("Cursor Null"))

            }

        }

        return work
    }

    fun getFavorite(context: Context, id: Int): Single<FavoriteEntity> {
        val work = Single.create<FavoriteEntity> {

            val selectionArgs: Array<String> = arrayOf(id.toString())
            val projection = arrayOf(Contract.AUTHORITY)

            val cursor = context.contentResolver.query(
                    Uri.parse(Contract.CONTENT_URI.toString() + "/" + id.toString()), projection, null,
                    selectionArgs, null
            )

            Log.d("MYAPP", "START")

            if (cursor != null) {

                val movieEntity = Utility.favoriteCursorToEntityConverter(cursor)
                cursor.close()

                it.onSuccess(movieEntity)

            } else {

                it.onError(Throwable("Cursor Null"))

            }

        }

        return work
    }

    fun insertFavorite(context: Context, favoriteEntity: FavoriteEntity) : Single<FavoriteEntity> {

        return Single.create {

            val selectionArgs: Array<String> = arrayOf("-123")
            val projection = arrayOf(Contract.AUTHORITY)
            val uri = Uri.parse(Contract.CONTENT_URI.toString())

            val contentResolver = context.contentResolver
            val contentValues = ContentValues()

            contentValues.put("id", favoriteEntity.id)
            contentValues.put("title", favoriteEntity.title)
            contentValues.put("tableName", favoriteEntity.tableName)
            contentValues.put("release_date", favoriteEntity.release_date)
            contentValues.put("overview", favoriteEntity.overview)
            contentValues.put("poster_path", favoriteEntity.poster_path)
            contentValues.put("backdrop_path", favoriteEntity.backdrop_path)
            contentValues.put("vote_count", favoriteEntity.vote_count)
            contentValues.put("vote_average", favoriteEntity.vote_average)
            contentValues.put("popularity", favoriteEntity.popularity)

            contentResolver.insert(uri, contentValues)
            it.onSuccess(favoriteEntity)
        }

    }

    fun deleteFavorite(context: Context, id: Int): Single<Int> {

        return Single.create {

            val selectionArgs: Array<String> = arrayOf(id.toString())
            val projection = arrayOf(Contract.AUTHORITY)
            val uri = Uri.parse(Contract.CONTENT_URI.toString())

            val contentResolver = context.contentResolver

            val affectedCount = contentResolver.delete(uri, null, selectionArgs)

            it.onSuccess(affectedCount)

        }
    }

}