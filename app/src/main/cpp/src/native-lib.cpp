#include  "native-lib.h"
#include <iostream>
#include <jni.h>
#include <syslog.h>

extern "C" JNIEXPORT jstring JNICALL
Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI(
        JNIEnv *env,
        jobject /*this*/
) {
    // std::string message = "Hello, World!";
    std::string anotherMessage = "Hello Again from C++!";
    syslog(LOG_DEBUG, "%s", anotherMessage.c_str());

    // std::string hello = "Hello from C++";
    return env->NewStringUTF(anotherMessage.c_str());
}
extern "C" JNIEXPORT void JNICALL
Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2() {
    std::string hello = "HERE_ Hello from C++ stringFromJNI2";
    //std::cout << hello << std::endl;
    syslog(LOG_DEBUG, "%s", hello.c_str());

    // // Create a Status object
    com::cjmobileapps::quidditchplayers::model::Status status("Player123", "Online");
    //
    // Output the object's data
    syslog(LOG_DEBUG, "Player ID: %s", status.playerId.c_str());
    syslog(LOG_DEBUG, "Status:: %s", status.status.c_str());

    std::cout << "Player ID: " << status.playerId << std::endl;
    std::cout << "Status: " << status.status << std::endl;
}

void blah() {
    std::string hello = "Hello from C++";
    std::cout << hello << std::endl;
}
