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
    syslog(LOG_CRIT, "%s", anotherMessage.c_str());

    // std::string hello = "Hello from C++";
    return env->NewStringUTF(anotherMessage.c_str());
}
extern "C" JNIEXPORT void JNICALL
Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2() {
    std::string hello = "HERE_ Hello from C++ stringFromJNI2";
    //std::cout << hello << std::endl;
    syslog(LOG_CRIT, "%s", hello.c_str());
}

void blah() {
    std::string hello = "Hello from C++";
    std::cout << hello << std::endl;
}
