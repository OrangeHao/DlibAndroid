

cmake_minimum_required(VERSION 3.4.1)


# 设置native目录
set(NATIVE_DIR ${CMAKE_SOURCE_DIR}/src/main/cpp)

# 设置dlib
include(${NATIVE_DIR}/dlib/cmake)



add_library(
        dlib_native
        SHARED
        src/main/cpp/java_models.h
        src/main/cpp/dlib_native.cpp )



find_library( # Sets the name of the path variable.
        log-lib

        # Specifies the name of the NDK library that
        # you want CMake to locate.
        log)



target_link_libraries( # Specifies the target library.
        dlib_native
        dlib
        jnigraphics
        # Links the target library to the log library
        # included in the NDK.
        ${log-lib} )

# 指定release编译选项
set(CMAKE_C_FLAGS_RELEASE "${CMAKE_C_FLAGS_RELEASE} -s -O3 -Wall")
set(CMAKE_CXX_FLAGS_RELEASE "${CMAKE_CXX_FLAGS_RELEASE} -s -O3 -Wall")