#include "MockData.h"
#include "Status.h"
#include "ResponseWrapper.h"
#include "House.h"
#include <iostream>
#include "HttpStatus.h"
#include "Positions.h"
//#include "../network/HttpStatus.h"


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

    const std::vector<model::House> &MockData::getMockHouses() {
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

    const model::ResponseWrapper<std::vector<model::House> > &MockData::getMockHousesResponseWrapper() {
        //todo delete this
        model::Error *error = new model::Error(true, "Something went wrong!");

        auto houses = getMockHouses();

        static const model::ResponseWrapper responseWrapperHouses(
            houses,
            nullptr,
            network::HttpStatus::HTTP_OK
        );

        return responseWrapperHouses;
    }
}


//const std::vector<House>& MockData::getMockHouses() {
//    static const std::vector<House> mockHouses = {
//        House(0, HouseName::GRYFFINDOR, "https://static.wikia.nocookie.net/harrypotter/images/9/98/Gryffindor.jpg/revision/latest", "🦁"),
//        House(1, HouseName::SLYTHERIN, "https://static.wikia.nocookie.net/harrypotter/images/6/6e/Slytherin.jpg/revision/latest", "🐍"),
//        House(2, HouseName::RAVENCLAW, "https://static.wikia.nocookie.net/harrypotter/images/3/3c/RavenclawCrest.jpg/revision/latest", "🦅"),
//        House(3, HouseName::HUFFLEPUFF, "https://static.wikia.nocookie.net/harrypotter/images/e/e4/Hufflepuff.jpg/revision/latest", "🦡")
//    };
//    return mockHouses;
//}

//// Access the singleton instance and call its method
//Singleton& instance = Singleton::getInstance();
//instance.doSomething();
//
//// Additional calls will use the same instance
//Singleton::getInstance().doSomething();
