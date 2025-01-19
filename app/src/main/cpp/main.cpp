#include <iostream>
#include "native-lib.h"


int main() {
    std::string message = "Hello, World!";  // Initialize with a C-style string
    std::cout << message << std::endl;

    std::string anotherMessage = std::string("Hello Again!");  // Explicit construction
    std::cout << anotherMessage << std::endl;

    //Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();
    blah();

    return 0;
}
