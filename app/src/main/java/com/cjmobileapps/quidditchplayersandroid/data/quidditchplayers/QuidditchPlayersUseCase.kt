package com.cjmobileapps.quidditchplayersandroid.data.quidditchplayers

import com.cjmobileapps.quidditchplayersandroid.data.model.Error
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.util.onError
import com.cjmobileapps.quidditchplayersandroid.util.onSuccess
import kotlinx.coroutines.flow.collectLatest
import timber.log.Timber
import java.net.HttpURLConnection

class QuidditchPlayersUseCase(
    private val quidditchPlayersRepository: QuidditchPlayersRepository
) {

    private val tag = QuidditchPlayersRepositoryImpl::class.java.simpleName

    suspend fun getHouses(onHousesResponse: (ResponseWrapper<List<House>>) -> Unit) {
        try {
            quidditchPlayersRepository.getAllHousesFlow().collectLatest { houses ->
                onHousesResponse(
                    ResponseWrapper(
                        data = houses,
                        statusCode = HttpURLConnection.HTTP_OK
                    )
                )
            }
        } catch (e: Exception) {
            onHousesResponse(
                ResponseWrapper(
                    error = Error(isError = true, message = e.message),
                    statusCode = HttpURLConnection.HTTP_BAD_REQUEST
                )
            )
        }
    }

    suspend fun fetchHouses(): ResponseWrapper<Boolean> {
        var responseWrapper: ResponseWrapper<Boolean> = ResponseWrapper(statusCode = 999)

        quidditchPlayersRepository.getAllHouses()
            .onSuccess { houses ->
                responseWrapper = try {
                    quidditchPlayersRepository.createAllHousesToDB(houses)
                    ResponseWrapper(data = true, statusCode = HttpURLConnection.HTTP_OK)
                } catch (e: Exception) {
                    Timber.tag(tag).e("quidditchPlayersRepository.getAllHouses() error occurred: $e \\n ${e.message}")
                    ResponseWrapper(
                        error = Error(
                            isError = true,
                            message = e.message
                        ),
                        statusCode = HttpURLConnection.HTTP_BAD_REQUEST
                    )
                }
            }
            .onError { statusCode, error ->
                responseWrapper =
                    ResponseWrapper(
                        error = Error(isError = true, message = error.message),
                        statusCode = statusCode
                    )
            }

        return responseWrapper
    }
}
