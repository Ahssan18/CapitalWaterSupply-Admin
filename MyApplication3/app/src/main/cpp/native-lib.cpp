#include <jni.h>
#include <string>

extern "C" JNIEXPORT jstring JNICALL
Java_uk_co_falcona_myapplication_MainActivity_stringFromJNI(
        JNIEnv *env,
        jobject /* this */) {
    const char *hello = "Hello from C++";
    return env->NewStringUTF(hello);
}

extern "C"
JNIEXPORT jint JNICALL
Java_uk_co_falcona_myapplication_MainActivity_sumFromJNI(
        JNIEnv *env,
        jobject, jint a, jint b /* this */) {
    std::string hello = "Hello from C++";
    std::int16_t i = 1;
    return (a + b);
}
extern "C"
JNIEXPORT jint JNICALL
Java_uk_co_falcona_myapplication_MainActivity_getAgeFromJNI(
        JNIEnv *env,
        jobject, jobject jobject1 /* this */) {
    jclass jclass1 = (*env).GetObjectClass(jobject1);
    jmethodID jmethodId = (*env).GetMethodID(jclass1, "getAge", "()I");
    jint age = (*env).CallIntMethod(jobject1, jmethodId);
    return age;
}

/*
extern "C"
JNIEXPORT jstring JNICALL
Java_uk_co_falcona_myapplication_MainActivity_getNameFromJNI(
        JNIEnv *env,
        jobject, jobject jobject1 */
/* this *//*
) {
    jclass jclass1 = (*env).GetObjectClass(jobject1);
    jmethodID jmethodId = (*env).GetMethodID(jclass1, "getName", "()Ljava/lang/String;");
    const char age = (*env).Call(jobject1, jmethodId);
    return env->NewStringUTF(age);
}*/
