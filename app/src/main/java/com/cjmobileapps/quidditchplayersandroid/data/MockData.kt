package com.cjmobileapps.quidditchplayersandroid.data

import com.cjmobileapps.quidditchplayersandroid.data.model.House
import com.cjmobileapps.quidditchplayersandroid.data.model.HouseName
import com.cjmobileapps.quidditchplayersandroid.data.model.Player
import com.cjmobileapps.quidditchplayersandroid.data.model.Position
import com.cjmobileapps.quidditchplayersandroid.data.model.toPlayersEntities
import java.util.UUID

object MockData {
    /*** houses data ***/

    // Used in Preview Ui
    val mockHouses =
        listOf(
            House(
                houseId = 0,
                name = HouseName.GRYFFINDOR,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/9/98/Gryffindor.jpg/revision/latest",
                emoji = "🦁",
            ),
            House(
                houseId = 1,
                name = HouseName.SLYTHERIN,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/6/6e/Slytherin.jpg/revision/latest",
                emoji = "🐍",
            ),
            House(
                houseId = 2,
                name = HouseName.RAVENCLAW,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/3/3c/RavenclawCrest.jpg/revision/latest",
                emoji = "🦅",
            ),
            House(
                houseId = 3,
                name = HouseName.HUFFLEPUFF,
                imageUrl = "https://static.wikia.nocookie.net/harrypotter/images/e/e4/Hufflepuff.jpg/revision/latest",
                emoji = "🦡",
            ),
        )

    /*** all players data ***/

    // Used in Preview Ui
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

    // Used in Preview Ui
    val mockPositions =
        mapOf(
            1 to Position(positionName = "Chaser"),
            2 to Position(positionName = "Beater"),
            3 to Position(positionName = "Keeper"),
            4 to Position(positionName = "Seeker"),
        )

    /*** players entity ***/

    // Used in Preview Ui
    val mockRavenclawPlayersEntities = ravenclawTeam().toPlayersEntities(mockPositions)

}
