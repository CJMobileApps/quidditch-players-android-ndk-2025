package com.cjmobileapps.quidditchplayersandroid.data

import com.cjmobileapps.quidditchplayersandroid.data.MockData.mockPositions
import com.cjmobileapps.quidditchplayersandroid.data.model.Error
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.Player
import com.cjmobileapps.quidditchplayersandroid.data.model.PlayerEntity
import com.cjmobileapps.quidditchplayersandroid.data.model.Position
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapper
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrapperUtil
import com.cjmobileapps.quidditchplayersandroid.data.model.ResponseWrappers
import com.cjmobileapps.quidditchplayersandroid.data.model.Status
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersEntities
import kotlinx.coroutines.CompletableDeferred
import retrofit2.Response

object MockDataFromCPP {
    init {
        System.loadLibrary("quidditchplayersandroid")
    }
    /*** players entity ***/

    val mockPlayersEntities: List<PlayerEntity> = getMockAllQuidditchTeams().toPlayersEntities(mockPositions)

    /*** houses data ***/

    external fun getMockHouses(): List<House>

    external fun getMockHousesResponseWrapper(): ResponseWrapper<List<House>>

    external fun getMockHousesGenericErrorResponseWrapper(): ResponseWrapper<List<House>>

    private val mockHousesResponseSuccess: Response<ResponseWrapper<List<House>>> =
        Response.success(getMockHousesResponseWrapper())

    val mockHousesDeferredResponseSuccess = CompletableDeferred(mockHousesResponseSuccess)

    /*** players data ***/

    external fun getMockAllQuidditchTeams(): List<Player>

    external fun getMockAllQuidditchTeamsResponseWrapper(): ResponseWrapper<List<Player>>

    external fun getRavenclawTeam(): List<Player>

    external fun getMockRavenclawPlayersResponseWrapper(): ResponseWrapper<List<Player>>

    private external fun getMockRavenclawGenericErrorResponseWrapper(): ResponseWrapper<List<Player>>

    private val mockRavenclawPlayersResponseSuccess = Response.success(getMockRavenclawPlayersResponseWrapper())

    val mockRavenclawPlayersDeferredResponseSuccess =
        CompletableDeferred(
            mockRavenclawPlayersResponseSuccess,
        )

    /*** positions ***/

    external fun getMockPositions(): Map<Int, Position>

    external fun getMockPositionsResponseWrapper(): ResponseWrapper<Map<Int, Position>>

    private external fun getMockPositionsGenericErrorResponseWrapper(): ResponseWrapper<Map<Int, Position>>

    private val mockPositionsResponseSuccess = Response.success(getMockPositionsResponseWrapper())

    val mockPositionsDeferredResponseSuccess =
        CompletableDeferred(
            mockPositionsResponseSuccess,
        )

    /*** players entity ***/

    val mockRavenclawPlayersEntities = getRavenclawTeam().toPlayersEntities(mockPositions)

    val mockRavenclawPlayersEntitiesResponseWrapper = ResponseWrapperUtil.createResponseWrapperSuccess(mockRavenclawPlayersEntities)

    val mockRavenclawPlayersEntitiesResponseWrapperError =
        ResponseWrapperUtil.createResponseWrapperError<List<PlayerEntity>>(
            error = Error(
                isError = true,
                message = "Some error",
            ),
        )

    /*** players and positions ***/

    val mockRavenclawPlayersAndPositionsResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = getMockRavenclawPlayersResponseWrapper(),
            responseWrapper2 = getMockPositionsResponseWrapper(),
        )

    val mockRavenclawPlayersErrorAndPositionsResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = getMockRavenclawGenericErrorResponseWrapper(),
            responseWrapper2 = getMockPositionsResponseWrapper(),
        )

    val mockRavenclawPlayersAndPositionsErrorResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = getMockRavenclawPlayersResponseWrapper(),
            responseWrapper2 = getMockPositionsGenericErrorResponseWrapper(),
        )

    /*** status ***/

    external fun getResponseWrapperMockStatus(): ResponseWrapper<Status>

    external fun getMockStatus(): Status

    fun getStatus(name: String) = String.format("%s is breaking into the Ministry of Magic %s", name, "\uD83D\uDD2E")

    external fun getMockStatusResponseWrapperGenericError(): ResponseWrapper<Status>

    private val mockStatusResponseSuccess = Response.success(getResponseWrapperMockStatus())

    val mockStatusDeferredResponseSuccess =
        CompletableDeferred(
            mockStatusResponseSuccess,
        )

    /*** Response Wrapper Boolean ***/

    external fun getMockTrueResponseWrapper(): ResponseWrapper<Boolean>

    external fun getMockBooleanResponseWrapperGenericError(): ResponseWrapper<Boolean>
}
