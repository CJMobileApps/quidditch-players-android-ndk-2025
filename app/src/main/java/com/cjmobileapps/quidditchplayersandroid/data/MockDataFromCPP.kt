package com.cjmobileapps.quidditchplayersandroid.data

import com.cjmobileapps.quidditchplayersandroid.data.MockData.mockPositions
import com.cjmobileapps.quidditchplayersandroid.data.model.Error
import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
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
import java.net.HttpURLConnection
import java.util.UUID

object MockDataFromCPP {
    init {
        System.loadLibrary("quidditchplayersandroid")
    }

    //todo delete
    external fun stringFromJNI(): String

    //todo delete
    external fun stringFromJNI2()

    //todo delete
    external fun convertToKotlin(
        playerId: String,
        status: String,
    ): Status

    /*** players entity ***/

    val mockPlayersEntities: List<PlayerEntity> = getMockAllQuidditchTeams().toPlayersEntities(mockPositions)

    /*** houses data ***/

    external fun getMockHouses(): List<House>

    external fun getMockHousesResponseWrapper(): ResponseWrapper<List<House>>

    val mockHousesGenericErrorResponseWrapper: ResponseWrapper<List<House>> =
        ResponseWrapperUtil.createResponseWrapperError(
            Error(isError = true, message = "Some error"),
        )

    private val mockHousesResponseSuccess: Response<ResponseWrapper<List<House>>> =
        Response.success(getMockHousesResponseWrapper())

    val mockHousesDeferredResponseSuccess = CompletableDeferred(mockHousesResponseSuccess)

    /*** players data ***/

    external fun getMockAllQuidditchTeams(): List<Player>

    external fun getMockAllQuidditchTeamsResponseWrapper(): ResponseWrapper<List<Player>>

    val mockRavenclawPlayersResponseWrapper =
        ResponseWrapper(
            data = ravenclawTeam(),
            statusCode = HttpURLConnection.HTTP_OK,
        )

    private val mockRavenclawPGenericErrorResponseWrapper: ResponseWrapper<List<Player>> =
        ResponseWrapperUtil.createResponseWrapperError(
            Error(isError = true, message = "Some error"),
        )

    private val mockRavenclawPlayersResponseSuccess = Response.success(mockRavenclawPlayersResponseWrapper)

    val mockRavenclawPlayersDeferredResponseSuccess =
        CompletableDeferred(
            mockRavenclawPlayersResponseSuccess,
        )

    /*** all players data ***/


    fun ravenclawTeam() =
        listOf(
            Player(
                id = UUID.fromString("aa7fb66e-827f-42db-9aac-974c87b35504"),
                firstName = "Cho",
                lastName = "Chang",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                    1995,
                    1996,
                ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/1/1e/Cho_Chang.jpg/revision/latest?cb=20180322164130",
                position = SEEKER,
                house = HouseName.RAVENCLAW,
                favoriteSubject = "Apparition",
            ),
            Player(
                id = UUID.fromString("ef968277-e996-4eca-8f94-1928dde4a979"),
                firstName = "Grant",
                lastName = "Page",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/93/GrantPage.png/revision/latest?cb=20130320232028",
                position = KEEPER,
                favoriteSubject = "Charms",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("cdf95045-8df9-4609-bb26-5d4752823022"),
                firstName = "Duncan",
                lastName = "Inglebee",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/2/29/Dinglebee.png/revision/latest?cb=20140827133418",
                position = BEATER,
                favoriteSubject = "Astronomy",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("870d5078-584d-4d34-9ff9-303db6c03992"),
                firstName = "Jason",
                lastName = "Samuels",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                ),
                position = BEATER,
                favoriteSubject = "Transfiguration",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/1/1b/Jasonsamuelsqwc.png/revision/latest?cb=20140827133708",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("8726e642-65a9-4dd7-b8eb-08f2a5850f4d"),
                firstName = "Randolph",
                lastName = "Burrow",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                ),
                position = CHASER,
                favoriteSubject = "Advanced Arithmancy Studies",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/0/07/RandolphBurrow.png/revision/latest?cb=20130320231816",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("f8f11664-a932-4e93-b93f-1d8ca4c0cf48"),
                firstName = "Jeremy",
                lastName = "Stretton",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                ),
                position = CHASER,
                favoriteSubject = "Alchemy",
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/0/06/Jeremy_Stretton_Cleansweep_Seven.jpg/revision/latest?cb=20091020205540",
                house = HouseName.RAVENCLAW,
            ),
            Player(
                id = UUID.fromString("c2fe9d3a-140d-439d-9f15-2f48475eee51"),
                firstName = "Roger",
                lastName = "Davies",
                yearsPlayed =
                listOf(
                    1993,
                    1994,
                    1995,
                    1996,
                ),
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/e/e5/Roger_Davies.jpg/revision/latest?cb=20180322052136",
                position = CHASER,
                favoriteSubject = "Apparition",
                house = HouseName.RAVENCLAW,
            ),
        )



    /*** positions ***/

    private const val CHASER = 1
    private const val BEATER = 2
    private const val KEEPER = 3
    private const val SEEKER = 4

    external fun getMockPositions(): Map<Int, Position>

    external fun getMockPositionsResponseWrapper(): ResponseWrapper<Map<Int, Position>>

    private val mockPositionsResponseWrapper =
        ResponseWrapper(
            data = mockPositions,
            statusCode = HttpURLConnection.HTTP_OK,
        )

    private val mockPositionsGenericErrorResponseWrapper: ResponseWrapper<Map<Int, Position>> =
        ResponseWrapperUtil.createResponseWrapperError(
            Error(isError = true, message = "Some error"),
        )

    private val mockPositionsResponseSuccess = Response.success(mockPositionsResponseWrapper)

    val mockPositionsDeferredResponseSuccess =
        CompletableDeferred(
            mockPositionsResponseSuccess,
        )

    /*** players entity ***/

    val mockRavenclawPlayersEntities = ravenclawTeam().toPlayersEntities(mockPositions)

    val mockRavenclawPlayersEntitiesResponseWrapper = ResponseWrapperUtil.createResponseWrapperSuccess(mockRavenclawPlayersEntities)

    val mockRavenclawPlayersEntitiesResponseWrapperError =
        ResponseWrapperUtil.createResponseWrapperError<List<PlayerEntity>>(
            error =
            Error(
                isError = true,
                message = "Some error",
            ),
        )

    /*** players and positions ***/

    val mockRavenclawPlayersAndPositionsResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = mockRavenclawPlayersResponseWrapper,
            responseWrapper2 = mockPositionsResponseWrapper,
        )

    val mockRavenclawPlayersErrorAndPositionsResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = mockRavenclawPGenericErrorResponseWrapper,
            responseWrapper2 = mockPositionsResponseWrapper,
        )

    val mockRavenclawPlayersAndPositionsErrorResponseWrappers =
        ResponseWrappers(
            responseWrapper1 = mockRavenclawPlayersResponseWrapper,
            responseWrapper2 = mockPositionsGenericErrorResponseWrapper,
        )

    /*** status ***/

    external fun getResponseWrapperMockStatus(): ResponseWrapper<Status>

    external fun getMockStatus(): Status

    fun getStatus(name: String) = String.format("%s is breaking into the Ministry of Magic %s", name, "\uD83D\uDD2E")

    //todo update in cpp
    fun mockStatus(): Status {
        val player = ravenclawTeam().first()
        val name = "${player.firstName} ${player.lastName}"
        return Status(
            playerId = player.id,
            status = getStatus(name),
        )
    }

    val mockStatusResponseWrapper =
        ResponseWrapper(
            data = mockStatus(),
            statusCode = HttpURLConnection.HTTP_OK,
        )

    private val mockStatusResponseSuccess = Response.success(mockStatusResponseWrapper)

    val mockStatusResponseWrapperGenericError =
        ResponseWrapperUtil.createResponseWrapperError<Status>(
            error =
            Error(
                isError = true,
                message = "Some error",
            ),
        )

    val mockStatusDeferredResponseSuccess =
        CompletableDeferred(
            mockStatusResponseSuccess,
        )

    /*** Response Wrapper Boolean ***/

    val mockTrueResponseWrapper = ResponseWrapperUtil.createResponseWrapperSuccess(true)

    val mockBooleanResponseWrapperGenericError =
        ResponseWrapperUtil.createResponseWrapperError<Boolean>(
            error =
            Error(
                isError = true,
                message = "Some error",
            ),
        )
}

