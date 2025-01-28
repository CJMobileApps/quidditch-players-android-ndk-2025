#ifndef QUIDDITCH_PLAYERS_H
#define QUIDDITCH_PLAYERS_H

#include <jni.h>
#include <string>

#ifdef __cplusplus
extern "C" {
#endif

namespace com::cjmobileapps::quidditchplayers {
    extern "C" JNIEXPORT jstring JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI(
            JNIEnv *env,
            jobject /*this*/
    );

    extern "C" JNIEXPORT void JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2(
            JNIEnv *env,
            jobject /*this*/
    );

    void blah();

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_convertToKotlin(
            JNIEnv *env,
            jobject /* this */,
            jstring playerId,
            jstring status
    );

    jobject convertCppUuidStringToUuidObject(JNIEnv *env, const std::string &uuidCppString);

    //todo add getMockStatus

    extern "C" JNIEXPORT jstring JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getStatus(
            JNIEnv *env,
            jobject /* this */,
            jstring name
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockStatus(
            JNIEnv *env,
            jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getResponseWrapperMockStatus(
            JNIEnv *env,
            jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHouses(
            JNIEnv *env,
            jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHousesResponseWrapper(
            JNIEnv *env,
            jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositions(JNIEnv *env, jobject);
} // namespace com::cjmobileapps::quidditchplayers

#ifdef __cplusplus
}
#endif

#endif // QUIDDITCH_PLAYERS_H
