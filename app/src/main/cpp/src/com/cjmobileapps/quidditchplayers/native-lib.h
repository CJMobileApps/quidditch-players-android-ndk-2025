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
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_stringFromJNI2();

    void blah();

    extern "C" JNIEXPORT jobject JNICALL
    Java_com_cjmobileapps_quidditchplayersandroid_data_MockDataFromCPP_convertToKotlin(
        JNIEnv *env,
        jobject /* this */,
        jstring playerId,
        jstring status
    );

    jobject convertCppUuidStringToUuidObject(JNIEnv *env, const std::string& uuidCppString);

} // namespace com::cjmobileapps::quidditchplayers

#ifdef __cplusplus
}
#endif

#endif // QUIDDITCH_PLAYERS_H
