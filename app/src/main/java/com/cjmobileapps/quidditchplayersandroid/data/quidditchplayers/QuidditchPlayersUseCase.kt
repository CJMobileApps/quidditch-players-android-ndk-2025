package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.model.Error
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.util.onError
import com.cjmobileapps.quidditchplayersandroid.util.onSuccess

class QuidditchPlayersUseCase(
    private val quidditchPlayersRepository: QuidditchPlayersRepository
) {

    suspend fun fetchHouses(): ResponseWrapper<Boolean> {
        var responseWrapper: ResponseWrapper<Boolean> = ResponseWrapper()

        quidditchPlayersRepository.getAllHouses()
            .onSuccess { houses ->
                responseWrapper = try {
                    //duckItRepository.addDuckItPostsToDB(posts)
                    ResponseWrapper(data = true)
                } catch (e: Exception) {
                    ResponseWrapper(
                        error = Error(
                            isError = true,
                            message = e.message
                        )
                    )
                }
            }
            .onError { _, error ->
                responseWrapper =
                    ResponseWrapper(error = Error(isError = true, message = error.message))
            }

        return responseWrapper
    }
}
