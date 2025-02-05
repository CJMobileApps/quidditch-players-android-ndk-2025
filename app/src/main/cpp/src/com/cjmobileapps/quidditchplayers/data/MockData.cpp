#include "MockData.h"
#include "Status.h"
#include "ResponseWrapper.h"
#include "House.h"
#include <iostream>
#include "HttpStatus.h"
#include "Positions.h"
#include "Player.h"

namespace com::cjmobileapps::quidditchplayers::model {
    class House;
}

namespace com::cjmobileapps::quidditchplayers::data {
    const model::Status &MockData::getMockStatus() {
        const auto name = "Harry Potter";

        static const auto status = model::Status(
                "e7d23c8f-8b80-4355-9d2d-580949cea13d",
                MockData::getStatus(name)
        );
        return status;
    }

    std::string MockData::getStatus(const std::string &name) {
        return name + " is breaking into the Ministry of Magic 💎";
    }

    const std::vector <model::House> &MockData::getMockHouses() {
        static const std::vector mockHouses = {
                model::House(
                        0,
                        model::HouseName::GRYFFINDOR,
                        "https://static.wikia.nocookie.net/harrypotter/images/9/98/Gryffindor.jpg/revision/latest",
                        "🦁"
                ),
                model::House(
                        1,
                        model::HouseName::SLYTHERIN,
                        "https://static.wikia.nocookie.net/harrypotter/images/6/6e/Slytherin.jpg/revision/latest",
                        "🐍"
                ),
                model::House(
                        2,
                        model::HouseName::RAVENCLAW,
                        "https://static.wikia.nocookie.net/harrypotter/images/3/3c/RavenclawCrest.jpg/revision/latest",
                        "🦅"
                ),
                model::House(
                        3,
                        model::HouseName::HUFFLEPUFF,
                        "https://static.wikia.nocookie.net/harrypotter/images/e/e4/Hufflepuff.jpg/revision/latest",
                        "🦡"
                )
        };

        return mockHouses;
    }


    const std::map<int, model::Position> &MockData::getMockPositions() {
        static std::map<int, model::Position> mockPositions = {
                {model::CHASER, model::Position("Chaser")},
                {model::BEATER, model::Position("Beater")},
                {model::KEEPER, model::Position("Keeper")},
                {model::SEEKER, model::Position("Seeker")}
        };

        return mockPositions;
    }

    // static const model::ResponseWrapper<std::vector<model::House>> getMockHousesResponseWrapper() {
    //
    //     com::cjmobileapps::quidditchplayers::model::Error* error = new com::cjmobileapps::quidditchplayers::model::Error(true, "Something went wrong!");
    //
    //     static const model::ResponseWrapper<std::vector<model::House>> responseWrapperHouses(
    //         getMockHouses(),
    //         error,
    //         network::HttpStatus::HTTP_OK
    //     );
    //
    //     return responseWrapperHouses;
    // }

    const model::ResponseWrapper<std::vector < model::House> > &

    MockData::getMockHousesResponseWrapper() {
        //todo delete this use this some where else
        model::Error *error = new model::Error(true, "Something went wrong!");

        const auto houses = getMockHouses();

        static const model::ResponseWrapper responseWrapperHouses(
                houses,
                nullptr,
                network::HttpStatus::HTTP_OK
        );

        return responseWrapperHouses;
    }

    const model::ResponseWrapper<std::map < int, model::Position> > &

    MockData::getMockPositionsResponseWrapper() {
        const auto positions = getMockPositions();

        static const model::ResponseWrapper responseWrapperPositions(
                positions,
                nullptr,
                network::HttpStatus::HTTP_OK
        );

        return responseWrapperPositions;
    }

    //    static const std::vector<model::Player> &getGryffindorTeam();

    const std::vector <model::Player> &MockData::getGryffindorTeam() {
        static const std::vector gryffindorTeam = {
                model::Player(
                        "fd1f2deb-9637-4214-b991-a1b8daf18a7b",
                        "Harry",
                        "Potter",
                        {1991, 1992, 1993, 1994, 1995, 1996, 1997},
                        "Defense Against The Dark Arts",
                        model::SEEKER,
                        "https://static.wikia.nocookie.net/harrypotter/images/9/97/Harry_Potter.jpg/revision/latest?cb=20140603201724",
                        model::HouseName::GRYFFINDOR
                ),
                model::Player(
                        "ef55fa4b-b88f-4623-aaad-7abdcf2ea4f6",
                        "Katie",
                        "Bell",
                        {1991, 1992, 1993, 1994, 1995, 1996, 1997},
                        "Transfiguration",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/3/32/Katie_Bell_infobox.jpg/revision/latest?cb=20170118053940",
                        model::HouseName::GRYFFINDOR
                ),
                model::Player(
                        "c2e851f6-9400-4c9f-89aa-936dfc6de90c",
                        "Angelina",
                        "Johnson",
                        {1990, 1991, 1992, 1993, 1994, 1995, 1996},
                        "Care of Magical Creatures",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/2/24/GOF_promo_Angelina_Johnson.jpg/revision/latest?cb=20100522214321",
                        model::HouseName::GRYFFINDOR
                ),
                model::Player(
                        "841c567f-c8f8-4945-8401-ecb81e7219f2",
                        "Fred",
                        "Weasley",
                        {1990, 1991, 1992, 1993, 1994, 1995, 1996},
                        "Charms",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/9/90/Fred_HS_TDH_promo.jpg/revision/latest/scale-to-width-down/1000?cb=20230526122025",
                        model::HouseName::GRYFFINDOR
                ),
                model::Player(
                        "7891a848-883c-4925-aa43-a6fb620195fa",
                        "George",
                        "Weasley",
                        {1990, 1991, 1992, 1993, 1994, 1995, 1996},
                        "Charms",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/2/2a/DH_promo_front_closeup_George_Weasley.jpg/revision/latest?cb=20161119235305",
                        model::HouseName::GRYFFINDOR
                ),
                model::Player(
                        "b10e1a15-df78-47ab-94b6-78942437b1ad",
                        "Alicia",
                        "Spinnet",
                        {1990, 1991, 1992, 1993, 1994, 1995, 1996},
                        "Charms",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/7/7a/Alicia_Spinnet.png/revision/latest?cb=20160720064800",
                        model::HouseName::GRYFFINDOR
                ),
                model::Player(
                        "a04e1b6f-9b7f-407e-8beb-aaf7b8d34655",
                        "Oliver",
                        "Wood",
                        {1989, 1990, 1991, 1992, 1993, 1994},
                        "Potions",
                        model::KEEPER,
                        "https://static.wikia.nocookie.net/harrypotter/images/2/2f/Oliver_WoodDH2.jpg/revision/latest?cb=20110801072255",
                        model::HouseName::GRYFFINDOR
                )
        };
        return gryffindorTeam;
    }


    const std::vector <model::Player> &MockData::getSlytherinTeam() {
        static const std::vector slytherinTeam = {
                model::Player(
                        "f5272335-7f6f-4aea-b0ba-c5c5dcec4aa5",
                        "Draco",
                        "Malfoy",
                        {1992, 1993, 1994, 1995, 1996, 1997},
                        "Potions",
                        model::SEEKER,
                        "https://static.wikia.nocookie.net/harrypotter/images/7/7e/Draco_Malfoy_TDH.png/revision/latest/scale-to-width-down/1000?cb=20180116013508",
                        model::HouseName::SLYTHERIN
                ),
                model::Player(
                        "d86096a5-9d9b-4dc6-b830-1de5431a1f37",
                        "Miles",
                        "Bletchley",
                        {1991, 1992, 1993, 1994, 1995, 1996},
                        "Study of Ancient Runes",
                        model::KEEPER,
                        "https://static.wikia.nocookie.net/harrypotter/images/4/40/Miles_Bletchley.jpeg/revision/latest?cb=20110810003628",
                        model::HouseName::SLYTHERIN
                ),
                model::Player(
                        "1dd6f764-365f-4013-8bc6-cacab0f45232",
                        "Gregory",
                        "Goyle",
                        {1995, 1996, 1997},
                        "Potions",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/e/e7/Gregory_Goyle_DH2.jpg/revision/latest?cb=20180306163743",
                        model::HouseName::SLYTHERIN
                ),
                model::Player(
                        "87ca2176-a15e-400e-98c2-21a4b7b34785",
                        "Vincent",
                        "Crabbe",
                        {1995, 1996, 1997},
                        "Potions",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/b/ba/Vincent_Crabbe.jpg/revision/latest/scale-to-width-down/1000?cb=20091224183746",
                        model::HouseName::SLYTHERIN
                ),
                model::Player(
                        "add49a74-db89-4c8b-bbcb-89be313442f7",
                        "Cassius",
                        "Warrington",
                        {1993, 1994, 1995, 1996},
                        "History of Magic",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/0/08/Cassius_Warrington_OOTPF.bmp/revision/latest/thumbnail/width/360/height/360?cb=20130416151820",
                        model::HouseName::SLYTHERIN
                ),
                model::Player(
                        "498810f5-e1b1-47ff-865a-22ef7ff72c69",
                        "Adrian",
                        "Pucey",
                        {1995, 1996},
                        "Magical Theory",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/1/13/Adrianpucey-HS.jpg/revision/latest?cb=20101126164937",
                        model::HouseName::SLYTHERIN
                ),
                model::Player(
                        "627efe48-7a10-45ce-b64c-2027926dd71e",
                        "Graham",
                        "Montague",
                        {1993, 1994, 1995, 1996},
                        "Transfiguration",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/c/c3/Graham_montague.jpg/revision/latest?cb=20140701101409&path-prefix=fr",
                        model::HouseName::SLYTHERIN
                )
        };
        return slytherinTeam;
    }

    const std::vector <model::Player> &MockData::getRavenclawTeam() {
        static const std::vector ravenclawTeam = {
                model::Player(
                        "aa7fb66e-827f-42db-9aac-974c87b35504",
                        "Cho",
                        "Chang",
                        {1993, 1994, 1995, 1996},
                        "Apparition",
                        model::SEEKER,
                        "https://static.wikia.nocookie.net/harrypotter/images/1/1e/Cho_Chang.jpg/revision/latest?cb=20180322164130",
                        model::HouseName::RAVENCLAW
                ),
                model::Player(
                        "ef968277-e996-4eca-8f94-1928dde4a979",
                        "Grant",
                        "Page",
                        {1993, 1994},
                        "Charms",
                        model::KEEPER,
                        "https://static.wikia.nocookie.net/harrypotter/images/9/93/GrantPage.png/revision/latest?cb=20130320232028",
                        model::HouseName::RAVENCLAW
                ),
                model::Player(
                        "cdf95045-8df9-4609-bb26-5d4752823022",
                        "Duncan",
                        "Inglebee",
                        {1993, 1994},
                        "Astronomy",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/2/29/Dinglebee.png/revision/latest?cb=20140827133418",
                        model::HouseName::RAVENCLAW
                ),
                model::Player(
                        "870d5078-584d-4d34-9ff9-303db6c03992",
                        "Jason",
                        "Samuels",
                        {1993, 1994},
                        "Transfiguration",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/1/1b/Jasonsamuelsqwc.png/revision/latest?cb=20140827133708",
                        model::HouseName::RAVENCLAW
                ),
                model::Player(
                        "8726e642-65a9-4dd7-b8eb-08f2a5850f4d",
                        "Randolph",
                        "Burrow",
                        {1993, 1994},
                        "Advanced Arithmancy Studies",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/0/07/RandolphBurrow.png/revision/latest?cb=20130320231816",
                        model::HouseName::RAVENCLAW
                ),
                model::Player(
                        "f8f11664-a932-4e93-b93f-1d8ca4c0cf48",
                        "Jeremy",
                        "Stretton",
                        {1993, 1994},
                        "Alchemy",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/0/06/Jeremy_Stretton_Cleansweep_Seven.jpg/revision/latest?cb=20091020205540",
                        model::HouseName::RAVENCLAW
                ),
                model::Player(
                        "c2fe9d3a-140d-439d-9f15-2f48475eee51",
                        "Roger",
                        "Davies",
                        {1993, 1994, 1995, 1996},
                        "Apparition",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/e/e5/Roger_Davies.jpg/revision/latest?cb=20180322052136",
                        model::HouseName::RAVENCLAW
                )
        };
        return ravenclawTeam;
    }

    const std::vector <model::Player> &MockData::getHufflepuffTeam() {
        static const std::vector hufflepuffTeam = {
                model::Player(
                        "4f9b53b6-b1c1-42d6-9217-2a0f39f010e3",
                        "Cedric",
                        "Diggory",
                        {1993, 1994},
                        "Charms",
                        model::SEEKER,
                        "https://static.wikia.nocookie.net/harrypotter/images/9/90/Cedric_Diggory_Profile.png/revision/latest/scale-to-width-down/1000?cb=20161123045136",
                        model::HouseName::HUFFLEPUFF
                ),
                model::Player(
                        "fa680863-997d-4213-b9c3-d8839099bcfb",
                        "Herbert",
                        "Fleet",
                        {1993, 1994},
                        "History of Magic",
                        model::KEEPER,
                        "https://static.wikia.nocookie.net/harrypotter/images/0/04/Herbert_Fleet.png/revision/latest?cb=20170304124757",
                        model::HouseName::HUFFLEPUFF
                ),
                model::Player(
                        "aa7fb66e-827f-42db-9aac-974c87b35504",
                        "Anthony",
                        "Rickett",
                        {1993, 1994},
                        "Muggle Music",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harryalbuspotter/images/e/ea/Anthony_Rickett.PNG/revision/latest?cb=20120107004734",
                        model::HouseName::HUFFLEPUFF
                ),
                model::Player(
                        "adc01148-3d20-4dd6-a421-f87d784e58ac",
                        "Maxine",
                        "O'Flaherty",
                        {1993, 1994},
                        "Muggle Art",
                        model::BEATER,
                        "https://static.wikia.nocookie.net/harrypotter/images/6/64/Maxine_O%27Flaherty.png/revision/latest?cb=20170304123914",
                        model::HouseName::HUFFLEPUFF
                ),
                model::Player(
                        "57b2d3d9-23a4-45ca-84ed-eb1154c34c07",
                        "Tamsin",
                        "Applebee",
                        {1993, 1994},
                        "Advanced Arithmancy Studies",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/4/48/Tamsin_Applebee.png/revision/latest?cb=20170304124301",
                        model::HouseName::HUFFLEPUFF
                ),
                model::Player(
                        "9651d59e-74da-43bd-b738-46a65097959b",
                        "Heidi",
                        "Macavoy",
                        {1993, 1994},
                        "Muggle Art",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/a/af/Heidi_Macavoy.png/revision/latest?cb=20170304123437",
                        model::HouseName::HUFFLEPUFF
                ),
                model::Player(
                        "757c624c-40e3-4e9f-a4a8-40cd09839c8f",
                        "Malcolm",
                        "Preece",
                        {1993, 1994},
                        "Ghoul Studies",
                        model::CHASER,
                        "https://static.wikia.nocookie.net/harrypotter/images/9/92/Malcolm_Preece.png/revision/latest?cb=20170304122953",
                        model::HouseName::HUFFLEPUFF
                )
        };
        return hufflepuffTeam;
    }

    std::vector <model::Player> MockData::getMockAllQuidditchTeams() {
        std::vector <model::Player> allPlayers;

        // Get players from each team and insert them into the combined list
        const auto &gryffindor = getGryffindorTeam();

        std::cout << "Total number of players on gryffindor" << gryffindor.size() << std::endl;

        const auto &slytherin = getSlytherinTeam();
        const auto &ravenclaw = getRavenclawTeam();
        const auto &hufflepuff = getHufflepuffTeam();

        allPlayers.insert(allPlayers.end(), gryffindor.begin(), gryffindor.end());
        allPlayers.insert(allPlayers.end(), slytherin.begin(), slytherin.end());
        allPlayers.insert(allPlayers.end(), ravenclaw.begin(), ravenclaw.end());
        allPlayers.insert(allPlayers.end(), hufflepuff.begin(), hufflepuff.end());

        return allPlayers;
    }
}


//// Access the singleton instance and call its method
//Singleton& instance = Singleton::getInstance();
//instance.doSomething();
//
//// Additional calls will use the same instance
//Singleton::getInstance().doSomething();
