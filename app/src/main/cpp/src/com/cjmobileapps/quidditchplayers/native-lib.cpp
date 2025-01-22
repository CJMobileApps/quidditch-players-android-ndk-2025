#include "native-lib.h"
#include <iostream>
#include <jni.h>
#include <syslog.h>

#include "model/Status.h"

namespace com::cjmobileapps::quidditchplayers {
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
        model::Status status("Player123", "Online");
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

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_convertToKotlin(
            JNIEnv *env,
            jobject /* this */,
            jstring playerId,
            jstring status) {

        // Convert jstring to std::string
        const char *playerIdCStr = env->GetStringUTFChars(playerId, nullptr);
        const char *statusCStr = env->GetStringUTFChars(status, nullptr);

        std::string playerIdStr(playerIdCStr);
        std::string statusStr(statusCStr);

        // Release jstring memory
        env->ReleaseStringUTFChars(playerId, playerIdCStr);
        env->ReleaseStringUTFChars(status, statusCStr);

        // Create a C++ Status object
        model::Status cppStatus(playerIdStr, statusStr);

        // Find the Kotlin Status class
        jclass statusClass = env->FindClass("com/cjmobileapps/quidditchplayersandroid/data/model/Status");

        // Get the constructor of the Kotlin Status class
        jmethodID constructor = env->GetMethodID(statusClass, "<init>", "(Ljava/util/UUID;Ljava/lang/String;)V");

        jobject uuidObject = convertCppUuidStringToUuidObject(env, cppStatus.playerId);

        // Create a new Kotlin Status object
        jobject kotlinStatus = env->NewObject(
                statusClass,
                constructor,
                uuidObject,
                env->NewStringUTF(cppStatus.status.c_str())
        );

        return kotlinStatus;
    }

    //TODO move this to util package
    jobject convertCppUuidStringToUuidObject(JNIEnv *env, const std::string& uuidCppString) {
        // Find the Java UUID class
        jclass uuidClass = env->FindClass("java/util/UUID");
        if (uuidClass == nullptr) {
            // Handle error: Class not found
            return nullptr;
        }

        // Get the static method ID for UUID.fromString(String)
        jmethodID fromStringMethod = env->GetStaticMethodID(uuidClass, "fromString", "(Ljava/lang/String;)Ljava/util/UUID;");
        if (fromStringMethod == nullptr) {
            // Handle error: Method not found
            return nullptr;
        }

        // Convert the C++ string to a Java string
        jstring uuidString = env->NewStringUTF(uuidCppString.c_str());
        if (uuidString == nullptr) {
            // Handle error: String creation failed
            return nullptr;
        }

        // Call UUID.fromString(String) to create a UUID object
        jobject uuidObject = env->CallStaticObjectMethod(uuidClass, fromStringMethod, uuidString);
        if (uuidObject == nullptr) {
            // Handle error: UUID object creation failed
            return nullptr;
        }

        return uuidObject;
    }
}
