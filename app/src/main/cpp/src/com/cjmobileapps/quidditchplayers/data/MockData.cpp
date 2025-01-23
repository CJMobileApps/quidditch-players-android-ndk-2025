#include "MockData.h"
#include "Status.h"
#include "ResponseWrapper.h"
#include <iostream>

namespace com::cjmobileapps::quidditchplayers::data {
    const model::Status &MockData::getStatus() {
        static const auto status = model::Status(
            "PlayerId: 1",
            "Player 1 Status online"
        );
        return status;
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
