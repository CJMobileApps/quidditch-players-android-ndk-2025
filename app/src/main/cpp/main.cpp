#include <iostream>
#include "src/native-lib.h"
#include "src/com/cjmobileapps/quidditchplayers/model/Status.h"

int main() {
    std::string message = "Hello, World!";  // Initialize with a C-style string
    std::cout << message << std::endl;

    std::string anotherMessage = std::string("Hello Again!");  // Explicit construction
    std::cout << anotherMessage << std::endl;

    //Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();
    blah();

    // // Create a Status object
    // com::cjmobileapps::quidditchplayers::model::Status status("Player123", "Online");
    // //
    // // Output the object's data
    // std::cout << "Player ID: " << status.playerId << std::endl;
    // std::cout << "Status: " << status.status << std::endl;


    return 0;
}
