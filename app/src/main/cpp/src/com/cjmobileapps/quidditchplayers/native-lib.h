#ifndef QUIDDITCH_PLAYERS_H
#define QUIDDITCH_PLAYERS_H

#include <jni.h>
#include <string>
#include "Player.h"

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
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockStatusResponseWrapperGenericError(
        JNIEnv *env,
        jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHouses(
        JNIEnv *env,
        jobject /* this */
    );

    std::string convertHouseNameToString(model::HouseName houseName);

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHousesResponseWrapper(
        JNIEnv *env,
        jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockHousesGenericErrorResponseWrapper(
        JNIEnv *env,
        jobject thisJobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositions(JNIEnv *env, jobject);

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositionsResponseWrapper(
        JNIEnv *env,
        jobject);

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockPositionsGenericErrorResponseWrapper(
        JNIEnv *env,
        jobject);

    jobject convertPlayerCppToKotlinObject(JNIEnv *env, model::Player player);

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockAllQuidditchTeams(
        JNIEnv *env,
        jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockAllQuidditchTeamsResponseWrapper(
        JNIEnv *env,
        jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getRavenclawTeam(
        JNIEnv *env,
        jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockRavenclawPlayersResponseWrapper(
        JNIEnv *env,
        jobject thisJObject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockRavenclawGenericErrorResponseWrapper(
        JNIEnv *env,
        jobject thisJObject /* this */
    );

    jobject createJavaBoolean(JNIEnv *env, bool value);

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockTrueResponseWrapper(
        JNIEnv *env,
        jobject /* this */
    );

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_getMockBooleanResponseWrapperGenericError(
        JNIEnv *env,
        jobject thisJObject /* this */
    );
} // namespace com::cjmobileapps::quidditchplayers

#ifdef __cplusplus
}
#endif

#endif // QUIDDITCH_PLAYERS_H
