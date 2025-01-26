#include <iostream>

#include "HttpStatus.h"
#include "src/com/cjmobileapps/quidditchplayers/native-lib.h"
#include "src/com/cjmobileapps/quidditchplayers/data/MockData.h"
#include "src/com/cjmobileapps/quidditchplayers/model/Error.h"
#include "src/com/cjmobileapps/quidditchplayers/model/ResponseWrapper.h"
#include "src/com/cjmobileapps/quidditchplayers/model/Status.h"

int main() {
    std::string message = "Hello, World!";  // Initialize with a C-style string
    std::cout << message << std::endl;

    std::string anotherMessage = std::string("Hello Again!");  // Explicit construction
    std::cout << anotherMessage << std::endl;

    //Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();
//    com::cjmobileapps::quidditchplayers::Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();
    com::cjmobileapps::quidditchplayers::blah();

    // // Create a Status object
    // com::cjmobileapps::quidditchplayers::model::Status status("Player123", "Online");
    const auto status = com::cjmobileapps::quidditchplayers::data::MockData::getMockStatus();

    // //
    // // Output the object's data
    // std::cout << "Player ID: " << status.playerId << std::endl;
    // std::cout << "Status: " << status.status << std::endl;
    // Create an Error object
    com::cjmobileapps::quidditchplayers::model::Error* error = new com::cjmobileapps::quidditchplayers::model::Error(true, "Something went wrong!");

    //Create a ResponseWrapper with an integer and an error
    com::cjmobileapps::quidditchplayers::model::ResponseWrapper response(42, error, 400);  // Status code 400 (Bad Request)

    // Output the data, error, and status code
    std::cout << "Data: " << response.data << std::endl;
    std::cout << "Status Code: " << response.statusCode << std::endl;

    if (response.error) {
        std::cout << "Error: " << response.error->getMessage() << std::endl;
        std::cout << "Is Error: " << (response.error->getIsError() ? "Yes" : "No") << std::endl;
    }

    std::cout << "\n\n" << std::endl;

    //Create a ResponseWrapper with an integer and an error
    com::cjmobileapps::quidditchplayers::model::ResponseWrapper response2(status, error, 400);  // Status code 400 (Bad Request)

    // Output the data, error, and status code
    std::cout << "Data2: " << response2.data.status << std::endl;
    std::cout << "Status Code2: " << response2.statusCode << std::endl;

    if (response.error) {
        std::cout << "Error: " << response2.error->getMessage() << std::endl;
        std::cout << "Is Error: " << (response2.error->getIsError() ? "Yes" : "No") << std::endl;
    }

    // Clean up dynamically allocated error
    delete error;

    std::cout << "getStatus " << com::cjmobileapps::quidditchplayers::data::MockData::getStatus("Harry Potter") << std::endl;

    std::cout << "HttpOk " << com::cjmobileapps::quidditchplayers::network::HttpStatus::HTTP_OK << std::endl;

    auto mockHousesResponseWrapper = com::cjmobileapps::quidditchplayers::data::MockData::getMockHousesResponseWrapper();
    for (const auto& house : mockHousesResponseWrapper.data) {
        std::cout << "House ID: " << house.getHouseId() << std::endl;
        // std::cout << "House Name: " << house.getName() << std::endl;
        std::cout << "House Image URL: " << house.getImageUrl() << std::endl;
        std::cout << "House Emoji: " << house.getEmoji() << std::endl;
        std::cout << "---------------------------------------" << std::endl;
    }
    // std::cout << "getMockHousesResponseWrapper " << mockHousesResponseWrapper.data << std::endl;


    return 0;
}
