#include "native-lib.h"
#include <iostream>
#include <jni.h>
#include <syslog.h>

#include "HttpStatus.h"
#include "MockData.h"
#include "Positions.h"
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
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2(
        JNIEnv *env,
        jobject /*this*/
    ) {
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
        jclass statusClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/Status");

        // Get the constructor of the Kotlin Status class
        jmethodID constructor = env->GetMethodID(statusClass, "<init>",
                                                 "(Ljava/util/UUID;Ljava/lang/String;)V");

        jobject uuidObject = convertCppUuidStringToUuidObject(env, cppStatus.playerId);

        // Create a new Kotlin Status object
        jobject kotlinStatus = env->NewObject(
            statusClass,
            constructor,
            uuidObject,
            env->NewStringUTF(cppStatus.status.c_str())
        );

        //todo return response wrapper status

        return kotlinStatus;
    }

    extern "C" JNIEXPORT jstring JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getStatus(
        JNIEnv *env,
        jobject /* this */,
        jstring name
    ) {
        const char *nameStr = env->GetStringUTFChars(name, nullptr);
        const auto status = data::MockData::getStatus(nameStr);
        jstring statusJString = env->NewStringUTF(status.c_str());
        return statusJString;
    }

    //TODO move this to util package
    jobject convertCppUuidStringToUuidObject(JNIEnv *env, const std::string &uuidCppString) {
        // Find the Java UUID class
        jclass uuidClass = env->FindClass("java/util/UUID");
        if (uuidClass == nullptr) {
            // Handle error: Class not found
            return nullptr;
        }

        // Get the static method ID for UUID.fromString(String)
        jmethodID fromStringMethod = env->GetStaticMethodID(uuidClass, "fromString",
                                                            "(Ljava/lang/String;)Ljava/util/UUID;");
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


    //TODO rewrite this
    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getResponseWrapperMockStatus(
        JNIEnv *env, jobject) {
        // Create a Status object in C++
        com::cjmobileapps::quidditchplayers::model::Status status =
                com::cjmobileapps::quidditchplayers::data::MockData::getMockStatus();

        // Convert C++ Status to Java/Kotlin equivalent
        jclass statusClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/Status");
        jmethodID statusConstructor = env->
                GetMethodID(statusClass, "<init>", "(Ljava/util/UUID;Ljava/lang/String;)V");

        jstring playerId = env->NewStringUTF("bcd59c9c-7c22-418c-81a8-4f2a722af352");
        jstring statusStr = env->NewStringUTF(status.status.c_str());

        jobject playerIdObject = convertCppUuidStringToUuidObject(env,
                                                                  "bcd59c9c-7c22-418c-81a8-4f2a722af352");


        jobject statusObject = env->NewObject(statusClass, statusConstructor, playerIdObject,
                                              statusStr);

        // Create an Error object for ResponseWrapper
        jclass errorClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/Error");
        jmethodID errorConstructor = env->GetMethodID(errorClass, "<init>",
                                                      "(ZLjava/lang/String;)V");

        jboolean isError = JNI_FALSE; // Example: No error
        jstring errorMessage = env->NewStringUTF(""); // Empty error message

        jobject errorObject = env->NewObject(errorClass, errorConstructor, isError, errorMessage);

        // Create a ResponseWrapper object
        jclass responseWrapperClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/ResponseWrapper");
        jmethodID responseWrapperConstructor = env->GetMethodID(responseWrapperClass, "<init>",
                                                                "(Ljava/lang/Object;Lcom/cjmobileapps/quidditchplayersandroid/data/model/Error;I)V");

        jint statusCode = 200; // Example: HTTP 200 OK

        jobject responseWrapperObject = env->NewObject(responseWrapperClass,
                                                       responseWrapperConstructor, statusObject,
                                                       errorObject, statusCode);

        // Release local references
        env->DeleteLocalRef(playerId);
        env->DeleteLocalRef(statusStr);
        env->DeleteLocalRef(errorMessage);

        return responseWrapperObject;
    }

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockStatus(
        JNIEnv *env,
        jobject /* this */
    ) {
        model::Status status = data::MockData::getMockStatus();

        jclass statusClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/Status");
        jmethodID statusConstructor = env->
                GetMethodID(statusClass, "<init>", "(Ljava/util/UUID;Ljava/lang/String;)V");

        jstring statusStr = env->NewStringUTF(status.status.c_str());
        jobject playerIdObject = convertCppUuidStringToUuidObject(env, status.playerId);
        jobject statusObject = env->NewObject(statusClass, statusConstructor, playerIdObject,
                                              statusStr);

        return statusObject;
    }

    extern "C" {
    JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHouses(
        JNIEnv *env,
        jobject) {
        auto mockHouses = data::MockData::getMockHouses();

        // Prepare Kotlin ArrayList class and its constructor
        jclass arrayListClass = env->FindClass("java/util/ArrayList");
        jmethodID arrayListConstructor = env->GetMethodID(arrayListClass, "<init>", "()V");
        jobject arrayList = env->NewObject(arrayListClass, arrayListConstructor);

        // Get add() method for ArrayList
        jmethodID arrayListAdd = env->GetMethodID(arrayListClass, "add", "(Ljava/lang/Object;)Z");

        // Prepare HouseName and House classes
        jclass houseNameClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/HouseName");
        jmethodID houseNameValueOf = env->GetStaticMethodID(houseNameClass, "valueOf",
                                                            "(Ljava/lang/String;)Lcom/cjmobileapps/quidditchplayersandroid/data/model/HouseName;");

        jclass houseClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/House");
        jmethodID houseConstructor = env->GetMethodID(houseClass, "<init>",
                                                      "(ILcom/cjmobileapps/quidditchplayersandroid/data/model/HouseName;Ljava/lang/String;Ljava/lang/String;)V");

        // Populate the ArrayList with House objects
        for (const auto &house: mockHouses) {
            // Convert C++ HouseName to Kotlin's enum HouseName
            const char *houseNameStr = nullptr;
            switch (house.getName()) {
                case model::HouseName::GRYFFINDOR:
                    houseNameStr = "GRYFFINDOR";
                    break;
                case model::HouseName::SLYTHERIN:
                    houseNameStr = "SLYTHERIN";
                    break;
                case model::HouseName::RAVENCLAW:
                    houseNameStr = "RAVENCLAW";
                    break;
                case model::HouseName::HUFFLEPUFF:
                    houseNameStr = "HUFFLEPUFF";
                    break;
                default:
                    houseNameStr = "UNKNOWN";
                    break;
            }

            jobject houseName = env->CallStaticObjectMethod(houseNameClass, houseNameValueOf,
                                                            env->NewStringUTF(houseNameStr));

            // Create a House object
            jobject kotlinHouse = env->NewObject(houseClass, houseConstructor,
                                                 house.getHouseId(),
                                                 houseName,
                                                 env->NewStringUTF(house.getImageUrl().c_str()),
                                                 env->NewStringUTF(house.getEmoji().c_str()));

            // Add the House object to the ArrayList
            env->CallBooleanMethod(arrayList, arrayListAdd, kotlinHouse);
        }

        return arrayList;
    }
    } /* end extern "C" */


    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHousesResponseWrapper(
        JNIEnv *env,
        jobject thisJobject /* this */
    ) {
        // Create a Status object in C++
        auto housesResponseWrapper =
                com::cjmobileapps::quidditchplayers::data::MockData::getMockHousesResponseWrapper();

        // Convert C++ Status to Java/Kotlin equivalent
        jobject housesObject = Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHouses(
            env, thisJobject);

        //todo user this later to return error
        // Create an Error object for ResponseWrapper
        //        jclass errorClass = env->FindClass(
        //                "com/cjmobileapps/quidditchplayersandroid/data/model/Error");
        //        jmethodID errorConstructor = env->GetMethodID(errorClass, "<init>",
        //                                                      "(ZLjava/lang/String;)V");
        //
        //        jboolean isError = JNI_FALSE; // Example: No error
        //        jstring errorMessage = env->NewStringUTF(""); // Empty error message
        //
        //        jobject errorObject = env->NewObject(errorClass, errorConstructor, isError, errorMessage);

        // Create a ResponseWrapper object
        jclass responseWrapperClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/ResponseWrapper");
        jmethodID responseWrapperConstructor = env->GetMethodID(
            responseWrapperClass, "<init>",
            "(Ljava/lang/Object;Lcom/cjmobileapps/quidditchplayersandroid/data/model/Error;I)V"
        );

        jint statusCode = housesResponseWrapper.statusCode;

        jobject errorObject = nullptr;

        jobject responseWrapperObject = env->NewObject(
            responseWrapperClass,
            responseWrapperConstructor,
            housesObject,
            errorObject,
            statusCode
        );

        return responseWrapperObject;
    }

    //     #include "Position.h"
    // #include <jni.h>
    // #include <string>
    // #include <map>

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositions(JNIEnv *env, jobject) {
        // Find Java HashMap class and its constructor
        jclass hashMapClass = env->FindClass("java/util/HashMap");
        if (!hashMapClass) return nullptr;

        jmethodID hashMapConstructor = env->GetMethodID(hashMapClass, "<init>", "()V");
        if (!hashMapConstructor) return nullptr;

        jobject hashMap = env->NewObject(hashMapClass, hashMapConstructor);
        if (!hashMap) return nullptr;

        // Find the put method in HashMap
        jmethodID hashMapPut = env->GetMethodID(hashMapClass, "put",
                                                "(Ljava/lang/Object;Ljava/lang/Object;)Ljava/lang/Object;");
        if (!hashMapPut) return nullptr;

        // Find the Position class and its constructor
        jclass positionClass = env->FindClass("com/cjmobileapps/quidditchplayersandroid/data/model/Position");
        if (!positionClass) return nullptr;

        jmethodID positionConstructor = env->GetMethodID(positionClass, "<init>", "(Ljava/lang/String;)V");
        if (!positionConstructor) return nullptr;

        // Iterate through the mockPositions map and populate the HashMap
        for (const auto &[key, position]: data::MockData::getMockPositions()) {
            // Create a Java Integer for the key
            jobject keyObject = env->NewObject(env->FindClass("java/lang/Integer"),
                                               env->GetMethodID(env->FindClass("java/lang/Integer"), "<init>", "(I)V"),
                                               key);

            // Create a Java Position object for the value
            jstring positionName = env->NewStringUTF(position.positionName.c_str());
            jobject positionObject = env->NewObject(positionClass, positionConstructor, positionName);

            // Put the key-value pair into the HashMap
            env->CallObjectMethod(hashMap, hashMapPut, keyObject, positionObject);

            // Clean up local references
            env->DeleteLocalRef(keyObject);
            env->DeleteLocalRef(positionName);
            env->DeleteLocalRef(positionObject);
        }

        return hashMap;
    }

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositionsResponseWrapper(
        JNIEnv *env,
        jobject thisJobject
    ) {
        // Create a Status object in C++
        //todo this is wrong delete

        // Convert C++ Status to Java/Kotlin equivalent
        const jobject positionsObject = Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositions(env, thisJobject);

        //todo user this later to return error
        // Create an Error object for ResponseWrapper
        //        jclass errorClass = env->FindClass(
        //                "com/cjmobileapps/quidditchplayersandroid/data/model/Error");
        //        jmethodID errorConstructor = env->GetMethodID(errorClass, "<init>",
        //                                                      "(ZLjava/lang/String;)V");
        //
        //        jboolean isError = JNI_FALSE; // Example: No error
        //        jstring errorMessage = env->NewStringUTF(""); // Empty error message
        //
        //        jobject errorObject = env->NewObject(errorClass, errorConstructor, isError, errorMessage);

        // Create a ResponseWrapper object
        jclass responseWrapperClass = env->FindClass(
            "com/cjmobileapps/quidditchplayersandroid/data/model/ResponseWrapper");
        jmethodID responseWrapperConstructor = env->GetMethodID(
            responseWrapperClass, "<init>",
            "(Ljava/lang/Object;Lcom/cjmobileapps/quidditchplayersandroid/data/model/Error;I)V"
        );

        jint statusCode = network::HttpStatus::HTTP_OK;

        jobject errorObject = nullptr;

        jobject responseWrapperObject = env->NewObject(
            responseWrapperClass,
            responseWrapperConstructor,
            positionsObject,
            errorObject,
            statusCode
        );

        return responseWrapperObject;
    }

    // static const model::ResponseWrapper<std::map<int, model::Position> > &mockPositionsResponseWrapper();
}
